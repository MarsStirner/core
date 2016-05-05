package ru.korus.tmis.hsct;

import com.google.common.collect.ImmutableSet;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import ru.korus.tmis.core.database.DbClientContactBeanLocal;
import ru.korus.tmis.core.database.DbClientRelationBeanLocal;
import ru.korus.tmis.core.database.common.DbActionBeanLocal;
import ru.korus.tmis.core.database.common.DbActionPropertyBeanLocal;
import ru.korus.tmis.core.database.common.DbCustomQueryLocal;
import ru.korus.tmis.core.database.common.DbEventBeanLocal;
import ru.korus.tmis.core.database.hsct.DbQueueHsctRequestBean;
import ru.korus.tmis.core.entity.model.*;
import ru.korus.tmis.core.entity.model.Patient;
import ru.korus.tmis.core.entity.model.hsct.QueueHsctRequest;
import ru.korus.tmis.core.entity.model.hsct.Status;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.hsct.external.*;
import ru.korus.tmis.scala.util.ConfigManager;

import javax.ejb.*;
import javax.ejb.Schedule;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Author: Upatov Egor <br>
 * Date: 23.12.2015, 16:40 <br>
 * Company: hitsl (Hi-Tech Solutions) <br>
 * Description: <br>
 */
@Stateless
public class HsctBean {
    private static final String LOG_MSG_SETTED_FROM = "Set {} from {}";
    private static final Logger LOGGER = LoggerFactory.getLogger("HSCT");
    private static final AtomicInteger PULL_COUNTER = new AtomicInteger(0);
    private static final AtomicInteger REST_COUNTER = new AtomicInteger(0);
    private static final String CHECK_MESSAGE_FOR_REQUIRED_FIELD = "Не указано обязательное поле - \"%s\"";

    private static ObjectMapper mapper;

    @PersistenceContext(unitName = "s11r64")
    private EntityManager em;
    @EJB
    private DbActionBeanLocal dbAction;
    @EJB
    private DbActionPropertyBeanLocal dbActionProperty;
    @EJB
    private DbClientRelationBeanLocal dbClientRelation;
    @EJB
    private DbQueueHsctRequestBean dbQueueHsctRequest;
    @EJB
    private DbEventBeanLocal dbEvent;
    @EJB
    private DbClientContactBeanLocal dbClientContact;

    @EJB
    private DbCustomQueryLocal dbCustomQuery;


    /**
     * Записывает Action в очередь отправки в ТГСК
     *
     * @param actionId идентификатор экшена
     * @param user     Данные пользователя, производящего запись в очередь отправки в ТГСК
     * @return структура для сериализации в JSON ответа по поставновке экшена в очередь.
     * this.getError() - успешность постановки, this.getErrorMessage - причина отказа \ отладочная информация
     */
    public HsctResponse enqueueAction(final int actionId, final Staff user) throws CoreException {
        final int num = REST_COUNTER.incrementAndGet();
        LOGGER.info("#{} Call enqueueAction({}, {})", num, actionId, user);
        if (!checkSendConfiguration()) {
            LOGGER.warn("#{} End of enqueueAction. Integration with HSCT is not configured properly or disabled.", num);
            return createErrorResponse("Hsct integration is disabled. Action will not be queued.");
        }
        final Action action = dbAction.getById(actionId);
        final String checkResult = checkAction(action, actionId);
        if (StringUtils.isNotEmpty(checkResult)) {
            // Проверка неудачна, возвращаем описание ошибки
            LOGGER.error("#{} End of enqueueAction. Action is not valid: {}", num, checkResult);
            return createErrorResponse(checkResult);
        }
        LOGGER.debug("#{} Action[{}] is valid", num, actionId);
        updateHsctResultInAction(action, "Заявка в очереди на отправку");
        lockAction(action);
        QueueHsctRequest queueEntry = dbQueueHsctRequest.getRequestByAction(action);
        if (queueEntry == null) {
            LOGGER.debug("#{} Action[{}] is not in queue and need to be inserted", num, action.getId());
            queueEntry = dbQueueHsctRequest.insertNewRequest(action, user);
            LOGGER.info("#{} End of enqueueAction. Inserted in queue {}", num, queueEntry);
            return createSuccessResponse(queueEntry.toString());
        } else {
            LOGGER.debug("#{} Action[{}] is in queue[{}] and need to be modified", num, action.getId(), queueEntry);
            dbQueueHsctRequest.markActive(queueEntry, user);
            LOGGER.info("#{} End of enqueueAction. Modified in queue {}", num, queueEntry);
            return createSuccessResponse(queueEntry.toString());
        }
    }

    private void lockAction(final Action action) {
        action.setEndDate(new Date());
        action.setStatus((short) 2);
        action.setModifyDatetime(new Date());
        dbAction.updateAction(action);
    }

    private void unlockAction(final Action action) {
        action.setEndDate(null);
        action.setStatus((short) 1);
        action.setModifyDatetime(new Date());
        dbAction.updateAction(action);
    }

    /**
     * Отменяет постановку Action в очередь отправки в ТГСК
     *
     * @param actionId идентификатор экшена
     * @param user     Данные пользователя, производящего выемку Action из очереди отправки в ТГСК
     * @return структура для сериализации в JSON ответа по поставновке экшена в очередь.
     * this.getError() - успешность отмены, this.getErrorMessage - причина отказа \ отладочная информация
     */
    public HsctResponse dequeueAction(final int actionId, final Staff user) {
        final int num = REST_COUNTER.incrementAndGet();
        LOGGER.info("#{} Call dequeueAction({}, {})", num, actionId, user);
        QueueHsctRequest queueEntry = dbQueueHsctRequest.getRequestByActionId(actionId);
        if (queueEntry != null) {
            final boolean canceled = dbQueueHsctRequest.markCanceled(queueEntry, user);
            LOGGER.debug("#{} Cancelled result={}. Entity = {}", num, canceled, queueEntry);
            final String message = String.format("Action[%d] canceled in queue", actionId);
            LOGGER.info("#{} End of dequeue. Return { success } \'{}\'", num, message);
            return createSuccessResponse(message);
        } else {
            final String message = String.format("Action[%d] not in queue", actionId);
            LOGGER.info("#{} End of dequeue. Return { success } \'{}\'", num, message);
            return createSuccessResponse(message);
        }
    }

    private HsctResponse createSuccessResponse(final String message) {
        return createResponse(false, message);
    }

    private HsctResponse createResponse(final boolean errorFlag, final String message) {
        final HsctResponse result = new HsctResponse();
        result.setError(errorFlag);
        result.setErrorMessage(message);
        return result;
    }

    private HsctResponse createErrorResponse(final String message) {
        return createResponse(true, message);
    }

    private String checkAction(final Action action, final int actionId) {
        if (action == null) {
            return String.format("Action[%d] not found", actionId);
        } else if (action.getDeleted()) {
            return String.format("Action[%d] is deleted", actionId);
        } else if (!StringUtils.equals(Constants.ACTION_TYPE_CODE, action.getActionType().getCode())) {
            return String.format("Action[%d] has invalid ActionType.code=\'%s\'", actionId, action.getActionType().getCode());
        }
        return null;
    }

    /**
     * Проверяет поставлен ли запращиваемый Action в очередь отправки в ТГСК
     *
     * @param actionId идентифкатор Action
     * @return Структура для сериализцаии в JSON с ответом.
     */
    public QueueEntry isInQueue(final int actionId) {
        final int num = REST_COUNTER.incrementAndGet();
        LOGGER.info("#{} Call isInQueue({})", num, actionId);
        QueueHsctRequest queueEntry = dbQueueHsctRequest.getRequestByActionId(actionId);
        if (queueEntry != null) {
            final QueueEntry response = new QueueEntry(queueEntry);
            LOGGER.info("#{} End of isInEnqueue({}). Response = {}", num, actionId, response);
            return response;
        } else {
            final QueueEntry response = new QueueEntry();
            response.setActionId(0);
            response.setInfo("NOT EXISTS");
            response.setStatus("NOT EXISTS");
            LOGGER.info("#{} End of isInQueue({}). Response = {}", num, actionId, response);
            return response;
        }
    }

    /**
     * Возвращает список из всех заявок, которые будут отправляться в ТГСК (стоят в очереди)
     *
     * @return Структура для сериализации в JSON со списком подстурктур - заявок
     */
    public QueueContainer getCurrentActive() {
        final int num = REST_COUNTER.incrementAndGet();
        LOGGER.debug("#{} Call getCurrentActive()", num);
        final QueueContainer response = new QueueContainer();
        final List<QueueHsctRequest> entries = dbQueueHsctRequest.getAllActive();
        for (QueueHsctRequest entry : entries) {
            response.addToEntries(new QueueEntry(entry));
        }
        LOGGER.debug("#{} End of getCurrentActive(). Response = {}", num, response);
        return response;
    }


    public Response setRequestStatusFromExternalSystem(final String userName, final String token, final HsctExternalRequestForComplete request) {
        final int num = REST_COUNTER.incrementAndGet();
        LOGGER.info("#{} Call setHsctRequestStatusFromExternalSystem (user_name=\'{}\', user_token=\'{}\') DATA: {}", num, userName, token, request);
        if (!ConfigManager.HsctProp().isReceiveActive()) {
            LOGGER.info("#{} Integration is disabled for RECEIVE events. End with 503", num);
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity("Integration disabled").build();
        } else if (request == null) {
            LOGGER.info("#{} Request is null or not deserialize. End with 400", num);
            return Response.status(Response.Status.BAD_REQUEST).build();
        } else if (!StringUtils.equals(userName, ConfigManager.HsctProp().ReceiveUser()) || !StringUtils.equalsIgnoreCase(
                token, ConfigManager.HsctProp().ReceivedPassword()
        )) {
            LOGGER.info("#{} Authentication failure. End with 403", num);
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        try {
            final Action action = dbAction.getById(Integer.parseInt(request.getRequest().getMisId()));
            if (action == null) {
                LOGGER.info("#{} Action[{}] not found. End with 400", num, request.getRequest().getMisId());
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            final String result = request.getRequest().isCompleted() ? "Заявка одобрена" : "Заявка отклонена";
            for (ActionProperty ap : action.getActionProperties()) {
                if (Constants.APT_CODE_IS_COMPLETED.equals(ap.getType().getCode())) {
                    final APValue apValue = dbActionProperty.setActionPropertyValue(ap, result, 0);
                    em.merge(apValue);
                    LOGGER.debug("#{} IS_COMPLETED AP[{}]", num, apValue);
                    break;
                }
            }
            LOGGER.info("#{} Request finished {}. End with 200 OK", num, result);
            return Response.ok().build();
        } catch (Exception e) {
            LOGGER.error("#{} Internal Error ", num, e);
            return Response.serverError().build();
        }
    }


    /***********************************************************************************************
     * END OF REST
     **********************************************************************************************/

    /**
     * Пуллинг очереди для периодической отправки всех скопившихся заявок
     */
    @Schedule(hour = "*", minute = "*/2", second = "50", persistent = false)
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void pullQueue() {
        final int number = PULL_COUNTER.incrementAndGet();
        if (!checkSendConfiguration()) {
            LOGGER.warn("#{} integration with HSCT is not configured properly or disabled");
        }
        final String serviceUrl = ConfigManager.HsctProp().ServiceUrl();
        LOGGER.info("#{} start pull", number);
        final List<QueueHsctRequest> requests = dbQueueHsctRequest.getAllActive();
        LOGGER.info("#{} Request to send : {}", number, requests.size());
        if (requests.isEmpty()) {
            LOGGER.info("#{} end pull", number);
            return;
        }
        LOGGER.debug("################## Start Sending to \'{}\'", serviceUrl);
        int rowNumber = 0;
        for (QueueHsctRequest i : requests) {
            rowNumber++;
            LOGGER.debug("#{}-{} Start processing {}", number, rowNumber, i.logRow());
            try {
                sendToHsct(i, number, rowNumber, serviceUrl);
            } catch (Exception e) {
                LOGGER.error("#{}-{} Error while sending {} to HSCT. Caused by", number, rowNumber, i.logRow(), e);
            }
            LOGGER.debug("#{}-{} End processing {}", number, rowNumber, i.logRow());
        }
        LOGGER.info("#{} end pull.", number);
    }

    /**
     * Отправка одной заявки во внешюю подсистему
     *
     * @param request    заявка из очереди
     * @param num        Порядковый номер пуллинга для логгирования  (передавай 0 если ничего не понятно)
     * @param rowNum     Порядковый номер заявки в очереди для логгирования  (передавай 0 если ничего не понятно)
     * @param serviceUrl Адрес внешней подсистемы, куда будут отправляться заявки (логин\пароль будут позже посдтавлены в URL)
     * @throws CoreException Когда что-то пошло не так, принудительно никогда не бросается
     */
    private void sendToHsct(final QueueHsctRequest request, final int num, final int rowNum, final String serviceUrl) throws CoreException {
        final Action action = dbAction.getById(request.getActionId());
        if (checkAction(action, request.getActionId()) != null) {
            //Проверка Action провалена, в заявке кривой Action, надо вычистить его из очереди
            LOGGER.error(
                    "#{}-{} CRITICAL_ERROR: Action[{}] is incorrect or malformed. QueueEntry will be DELETED. Entry info = {}",
                    num,
                    rowNum,
                    request.getActionId(),
                    request
            );
            em.remove(request);
            return;
        }
        updateRequestStatusWithFlush(request, Status.IN_PROGRESS, true, null);
        try {
            final HsctExternalRequest externalRequest = constructExternalHsctRequest(action, request.getPerson());
            final String checkExternalRequestResult = checkExternalRequest(externalRequest);
            if (StringUtils.isNotEmpty(checkExternalRequestResult)) {
                LOGGER.error("#{}-{} Check after construction of externalRequest failed. Message = \'{}\'", num, rowNum, checkExternalRequestResult);
                updateRequestStatusWithFlush(request, Status.CANCELED, false, checkExternalRequestResult);
                updateHsctResultInAction(action, checkExternalRequestResult);
                unlockAction(action);
                return;
            }
            final HsctExternalResponse externalResponse = sendToExternalSystem(externalRequest, serviceUrl, num, rowNum);
            if (externalResponse.getRequest() != null && externalResponse.getRequest().getId() != null) {
                final String resultString = String.format(
                        "Успешно отправлено в ТГСК, получен идентифкатор заявки = %d",
                        externalResponse.getRequest().getId()
                );
                LOGGER.info("#{}-{} Successful integration with HSCT. Result is ={}", num, rowNum, resultString);
                final String jsonRepresentation = getExternalSystemSerializer().writeValueAsString(externalRequest);
                updateRequestStatusWithFlush(request, Status.FINISHED, false, resultString.concat(" :::: ").concat(jsonRepresentation));
                updateHsctResultInAction(action, resultString);
                lockAction(action);
            } else {
                final String resultString = externalResponse.getErrorMessage();
                LOGGER.info("#{}-{} Failed integration with HSCT. Result is ={}", num, rowNum, resultString);
                final String jsonRepresentation = getExternalSystemSerializer().writeValueAsString(externalRequest);
                updateRequestStatusWithFlush(
                        request,
                        externalResponse.isRemoveFromQueue() ? Status.CANCELED : Status.ERROR,
                        false,
                        resultString.concat(" :::: ").concat(jsonRepresentation)
                );
                updateHsctResultInAction(action, resultString);
                if (externalResponse.isRemoveFromQueue()) {
                    unlockAction(action);
                }
            }
        } catch (Exception e) {
            //Ощибка при конструировании запроса для внешней подсистемы
            LOGGER.error("#{}-{} Unknown error: {}", num, rowNum, e);
            final String resultString = String.format("Неизвестная ошибка: \'%s\'", e.getMessage());
            updateRequestStatusWithFlush(request, Status.ERROR, false, resultString);
            updateHsctResultInAction(action, resultString);
            unlockAction(action);
        }
    }

    private void updateHsctResultInAction(final Action action, final String message) throws CoreException {
        for (ActionProperty ap : action.getActionProperties()) {
            if (Constants.APT_CODE_RESULT.equals(ap.getType().getCode())) {
                final APValue apValue = dbActionProperty.setActionPropertyValue(ap, message, 0);
                em.merge(apValue);
                return;
            }
        }
    }

    private void updateRequestStatusWithFlush(
            final QueueHsctRequest request, final Status status, final boolean incrementAttempts, final String message
    ) {
        request.setStatus(status);
        request.setSendDateTime(new Date());
        if (incrementAttempts) {
            request.setAttempts(request.getAttempts() + 1);
        }
        if (message != null) {
            request.setInfo(message);
        }
        em.merge(request);
        em.flush();
    }

    private String checkExternalRequest(final HsctExternalRequest request) {
        final StringBuilder checkResult = new StringBuilder();
        boolean hasErrors = checkField(request.getDiseaseStatus(), "Статус болезни", checkResult, false);
        hasErrors = checkField(request.getDiagnosis(), "Клинический диагноз", checkResult, hasErrors);
        hasErrors = checkField(request.getDiagnosisIcdCode(), "Клинический диагноз по МКБ", checkResult, hasErrors);
        hasErrors = checkField(request.getDiagnosisDate(), "Дата установки клинического диагноза", checkResult, hasErrors);
        /*
          From: Старичкова Юлия Викторовна [mailto:julia.starichkova@fccho-moscow.ru]
            Sent: Friday, February 26, 2016 12:33 PM
            To: Алена hitsl. Карасева; Екатерина hitsl. Воронина; Тарас hitsl. Баранюк
            Cc: Павлюк Алексей Владимирович; Мария Уварова; Инна Ашенбреннер; Воронин Кирилл Александрович
            Subject: RE: интеграция МИС-ТГСК
            Добрый день.              Коллеги, обсудили с коллегами из ТГСК документ «Заявка на ТГСК».
            Появились замечания к документу «заявка на ТГСК»:
            1.       Сделать поля необязательными:
                a.       «Осложнения»
                b.      «Сопутствующие диагнозы»
        //hasErrors = checkField(request.getComplications(), "Осложнения", checkResult, hasErrors);
        //hasErrors = checkField(request.getSecondaryDiagnoses(), "Сопутствующие диагнозы", checkResult, hasErrors);
        */
        hasErrors = checkField(request.getAntiCmvIgG(), "Анти-CMV IgG", checkResult, hasErrors);
        hasErrors = checkField(request.getIndications(), "Показания к ТГСК", checkResult, hasErrors);
        hasErrors = checkField(request.getIndicationsDate(), "Дата установления показаний", checkResult, hasErrors);
        hasErrors = checkField(request.getHsctOptimalDate(), "Оптимальный срок ТГСК", checkResult, hasErrors);
        hasErrors = checkField(request.getHsctTypeCode(), "Вид ТГСК", checkResult, hasErrors);
        hasErrors = checkField(request.getDepartmentCode(), "Обращение: Отделение пребывания", checkResult, hasErrors);
        final ru.korus.tmis.hsct.external.Patient patient = request.getPatient();
        hasErrors = checkField(patient.getMisId(), "Пациент: Идентифкатор", checkResult, hasErrors);
        hasErrors = checkField(patient.getLastName(), "Пациент: Фамилия", checkResult, hasErrors);
        hasErrors = checkField(patient.getFirstName(), "Пациент: Имя", checkResult, hasErrors);
        hasErrors = checkField(patient.getBirthDate(), "Пациент: Дата рождения", checkResult, hasErrors);
        final Doctor doctor = request.getDoctor();
        hasErrors = checkField(doctor.getMisId(), "Врач: Идентифкатор", checkResult, hasErrors);
        hasErrors = checkField(doctor.getFirstName(), "Врач: Имя", checkResult, hasErrors);
        hasErrors = checkField(doctor.getLastName(), "Врач: Фамилия", checkResult, hasErrors);
        hasErrors = checkField(doctor.getDepartmentCode(), "Врач: Отделение", checkResult, hasErrors);
        if(request.getSiblings() == null){
            checkResult.append(String.format(CHECK_MESSAGE_FOR_REQUIRED_FIELD, "Наличие сиблингов"));
            hasErrors = true;
        }
        return hasErrors ? checkResult.toString() : null;
    }


    private HsctExternalRequest constructExternalHsctRequest(final Action action, final Staff person) throws CoreException {
        final HsctExternalRequest result = new HsctExternalRequest();
        final Event event = action.getEvent();
        final Patient patient = event.getPatient();
        processDepatment(result, event);
        result.setMisId(action.getId().toString());
        processActionProperties(action, result);
        processPatient(result, patient);
        processDoctor(result, person);
        processSpokesman(result, patient);
        processJournals(result, action.getEvent());
        processWeight(result, action.getEvent());
        return result;
    }

    private void processWeight(final HsctExternalRequest result, final Event event) throws CoreException {
        final List<Action> actions = dbAction.getActionsByTypeCodeAndEventId(Constants.THERAPY_AT_CODES, event.getId(), "a.begDate DESC");
        if (actions == null || actions.isEmpty()) {
            LOGGER.debug("No WEIGHT-actions for event[{}]", event);
            return;
        } else {
            LOGGER.debug("Founded {} WEIGHT-actions", actions.size());
        }
        //Пербираем все журналы
        for (Action action : actions) {
            LOGGER.debug("Process WEIGHT-Action[{}]", action.getId());
            //перебираем список свойст текущего журнала со значениями ( по списку кодов типов свойств)
            final Map<ActionProperty, List<APValue>> map = dbActionProperty.getActionPropertiesByActionIdAndActionPropertyTypeCodes(
                    action.getId(), Constants.WEIGHT_APT_CODES
            );
            if (map != null && !map.isEmpty()) {
                for (Map.Entry<ActionProperty, List<APValue>> entry : map.entrySet()) {
                    if (Constants.APT_WEIGHT.equals(entry.getKey().getType().getCode())) {
                        final List<APValue> valueList = entry.getValue();
                        if (valueList != null && !valueList.isEmpty()) {
                            result.setWeight(Double.valueOf(valueList.get(0).getValueAsString()));
                            LOGGER.debug(LOG_MSG_SETTED_FROM, Constants.APT_WEIGHT, action.getId());
                            return;
                        }
                    }
                }
            }
        }
    }

    private void processDepatment(final HsctExternalRequest result, final Event event) throws CoreException {
        OrgStructure lastOrgStructure = null;
        try {
            lastOrgStructure = dbEvent.getOrgStructureForEvent(event.getId());
        } catch (Exception e) {
            LOGGER.warn("Cannot get OrgStructure from movings for Event[{}]", event.getId());
        }
        if (lastOrgStructure == null) {
            final Map<Event, ActionProperty> mapAP = dbCustomQuery.getOrgStructureByReceivedActionByEvents(
                    Collections.singletonList(
                            event
                    )
            );
            if (!mapAP.isEmpty() && mapAP.values().iterator().hasNext()) {
                final ActionProperty ap = mapAP.values().iterator().next();
                final List<APValue> apValues = dbActionProperty.getActionPropertyValue(ap);
                if (apValues != null && !apValues.isEmpty()) {
                    LOGGER.debug("Getting OrgStructure from received Action[{}]-{}", ap.getAction().getId(), ap.getId());
                    final APValue value = apValues.iterator().next();
                    lastOrgStructure = (OrgStructure) value.getValue();

                }
            }
        }
        if (lastOrgStructure != null) {
            result.setDepartmentCode(lastOrgStructure.getCode());
        }
    }

    private void processJournals(final HsctExternalRequest result, final Event event) throws CoreException {
        final List<Action> journals = dbAction.getActionsByTypeCodeAndEventId(Constants.THERAPY_AT_CODES, event.getId(), "a.begDate DESC");
        if (journals == null || journals.isEmpty()) {
            LOGGER.debug("No journals for event[{}]", event);
            return;
        } else {
            LOGGER.debug("Founded {} journals", journals.size());
        }
        //Пербираем все журналы
        for (Action action : journals) {
            if (processJournal(action, result)) {
                LOGGER.debug("TherapyProtocol filled from Action[{}] ", action.getId());
                break;
            }
        }
    }

    private boolean processJournal(final Action action, final HsctExternalRequest result) throws CoreException {
        LOGGER.debug("Process journal Action[{}]", action.getId());
        //перебираем список свойст текущего журнала со значениями ( по списку кодов типов свойств)
        final Map<ActionProperty, List<APValue>> map = dbActionProperty.getActionPropertiesByActionIdAndActionPropertyTypeCodes(
                action.getId(), Constants.THERAPY_APT_CODES
        );
        //Если у журнала есть  APT_JOURNAL_THERAPY_TITLE заполненный, то работаем только с этим журналом, все остальные -> /dev/null/
        for (ActionProperty property : map.keySet()) {
            if (Constants.APT_JOURNAL_THERAPY_TITLE.equals(property.getType().getCode())) {
                final List<APValue> valueList = map.get(property);
                if (valueList == null || valueList.isEmpty() || StringUtils.isEmpty(valueList.get(0).toString())) {
                    LOGGER.debug(
                            "Action[{}] has null/empty AP[{}] with code=\'{}\'. Skip journal Action",
                            action.getId(),
                            property.getId(),
                            Constants.APT_JOURNAL_THERAPY_TITLE
                    );
                    return false;
                } else {
                    LOGGER.debug(
                            "Action[{}] has AP[{}] with code=\'{}\'. Process it!!!", action.getId(), property.getId(), Constants.APT_JOURNAL_THERAPY_TITLE
                    );
                    break;
                }
            }
        }
        final SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
        //Терапия найдена - дербаним по свойствам
        for (Map.Entry<ActionProperty, List<APValue>> entry : map.entrySet()) {
            final List<APValue> values = entry.getValue();
            if (!values.isEmpty()) {
                switch (entry.getKey().getType().getCode()) {
                    case Constants.APT_JOURNAL_THERAPY_TITLE:
                        result.setProtocolCode(values.get(0).getValueAsString());
                        LOGGER.debug(LOG_MSG_SETTED_FROM, Constants.APT_JOURNAL_THERAPY_TITLE, action.getId());
                        break;
                    case Constants.APT_JOURNAL_THERAPY_BEG_DATE:
                        result.setProtocolStartDate(sdf.format(values.get(0).getValue()));
                        LOGGER.debug(LOG_MSG_SETTED_FROM, Constants.APT_JOURNAL_THERAPY_BEG_DATE, action.getId());
                        break;
                    case Constants.APT_JOURNAL_THERAPY_END_DATE:
                        result.setProtocolEndDate(sdf.format(values.get(0).getValue()));
                        LOGGER.debug(LOG_MSG_SETTED_FROM, Constants.APT_JOURNAL_THERAPY_END_DATE, action.getId());
                        break;
                    case Constants.APT_JOURNAL_PHASE_TITLE:
                        result.setProtocolStageCode(values.get(0).getValueAsString());
                        LOGGER.debug(LOG_MSG_SETTED_FROM, Constants.APT_JOURNAL_PHASE_TITLE, action.getId());
                        break;
                    case Constants.APT_JOURNAL_PHASE_BEG_DATE:
                        result.setProtocolStageStartDate(sdf.format(values.get(0).getValue()));
                        LOGGER.debug(LOG_MSG_SETTED_FROM, Constants.APT_JOURNAL_PHASE_BEG_DATE, action.getId());
                        break;
                    case Constants.APT_JOURNAL_PHASE_END_DATE:
                        result.setProtocolStageEndDate(sdf.format(values.get(0).getValue()));
                        LOGGER.debug(LOG_MSG_SETTED_FROM, Constants.APT_JOURNAL_PHASE_END_DATE, action.getId());
                        break;
                    default:
                        break;
                }
            }
        }
        return true;
    }

    private void processSpokesman(final HsctExternalRequest result, final ru.korus.tmis.core.entity.model.Patient patient) {
        final List<ClientRelation> relations = dbClientRelation.getAllClientRelations(patient.getId());
        if (relations != null && !relations.isEmpty()) {
            final ru.korus.tmis.core.entity.model.Patient lastRelative = relations.get(0).getRelative();
            final Spokesman spokesman = new Spokesman();
            spokesman.setFirstName(lastRelative.getFirstName());
            spokesman.setLastName(lastRelative.getLastName());
            spokesman.setPatrName(lastRelative.getPatrName());
            final List<ClientContact> contacts = dbClientContact.getActiveClientContacts(lastRelative.getId());
            LOGGER.debug("Client contacts size = {}", contacts.size());
            ImmutableSet<String> phoneTypes = ImmutableSet.of("01", "02", "03");
            boolean setPhone = false;
            boolean setMail = false;
            for (ClientContact contact : contacts) {
                if (!setMail && "04".equals(contact.getContactType().getCode())) {
                    spokesman.setEmail(contact.getContact());
                    LOGGER.debug("set email from {}", contact);
                    setMail = true;
                }
                if (!setPhone && phoneTypes.contains(contact.getContactType().getCode())) {
                    spokesman.setPhone(contact.getContact());
                    LOGGER.debug("set phone from {}", contact);
                    setPhone = true;
                }
                if (setPhone && setMail) {
                    break;
                }
            }
            result.setSpokesman(spokesman);
        }
    }

    private void processDoctor(final HsctExternalRequest result, final Staff person) {
        final Doctor doctor = new Doctor();
        doctor.setFirstName(person.getFirstName());
        doctor.setLastName(person.getLastName());
        doctor.setPatrName(person.getPatrName());
        doctor.setMisId(String.valueOf(person.getId()));
        doctor.setDepartmentCode(person.getOrgStructure().getCode());
        result.setDoctor(doctor);
    }

    private void processPatient(final HsctExternalRequest result, final Patient patient) {
        final ru.korus.tmis.hsct.external.Patient entry = new ru.korus.tmis.hsct.external.Patient();
        entry.setFirstName(patient.getFirstName());
        entry.setLastName(patient.getLastName());
        entry.setPatrName(patient.getPatrName());
        entry.setMisId(String.valueOf(patient.getId()));
        entry.setBirthDate(new SimpleDateFormat(Constants.DATE_FORMAT).format(patient.getBirthDate()));
        switch (patient.getSex()) {
            case 1:
                entry.setSex("m");
                break;
            case 2:
                entry.setSex("f");
                break;
            default:
                LOGGER.warn("Patient sex [{}] cannot be mapped to HSCT enum. Set \'u\' as undefined", patient.getSex());
                entry.setSex("u");
                break;
        }
        result.setPatient(entry);
        result.setBloodTypeCode(
                patient.getBloodType() == null || StringUtils.isEmpty(patient.getBloodType().getCode()) ? null : patient.getBloodType().getCode()
        );
    }

    private void processActionProperties(final Action action, final HsctExternalRequest result) throws CoreException {
        final SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
        final Map<ActionProperty, List<APValue>> map = dbActionProperty.getActionPropertiesByActionIdAndActionPropertyTypeCodes(
                action.getId(), Constants.HSCT_REQUEST_APT_CODES
        );
        for (Map.Entry<ActionProperty, List<APValue>> entry : map.entrySet()) {
            final List<APValue> values = entry.getValue();
            if (!values.isEmpty()) {
                switch (entry.getKey().getType().getCode()) {
                    case Constants.APT_CODE_DISEASE_STATUS:
                        result.setDiseaseStatus(values.get(0).getValueAsString());
                        break;
                    case Constants.APT_CODE_DIAGNOSIS:
                        result.setDiagnosis(values.get(0).getValueAsString());
                        break;
                    case Constants.APT_CODE_DIAGNOSIS_ICD_CODE:
                        result.setDiagnosisIcdCode(((Mkb) values.get(0).getValue()).getDiagID());
                        break;
                    case Constants.APT_CODE_DIAGNOSIS_DATE:
                        result.setDiagnosisDate(sdf.format(values.get(0).getValue()));
                        break;
                    case Constants.APT_CODE_COMPLICATIONS:
                        result.setComplications(values.get(0).getValueAsString());
                        break;
                    case Constants.APT_CODE_SECONDARY_DIAGNOSES:
                        result.setSecondaryDiagnoses(values.get(0).getValueAsString());
                        break;
                    case Constants.APT_CODE_ANTI_CVM_IGG_CODE:
                        result.setAntiCmvIgG(values.get(0).getValueAsString());
                        break;
                    case Constants.APT_CODE_INDICATIONS:
                        result.setIndications(values.get(0).getValueAsString());
                        break;
                    case Constants.APT_CODE_INDICATIONS_DATE:
                        result.setIndicationsDate(sdf.format(values.get(0).getValue()));
                        break;
                    case Constants.APT_CODE_OPTIMAL_HSCT_DATE:
                        result.setHsctOptimalDate(new SimpleDateFormat(Constants.MONTH_FORMAT).format(values.get(0).getValue()));
                        break;
                    case Constants.APT_CODE_HSCT_TYPE_CODE:
                        result.setHsctTypeCode(values.get(0).getValueAsString());
                        break;
                    case Constants.APT_CODE_HAS_SIBLINGS:
                        final String strValue = values.get(0).getValueAsString();
                        if("Есть".equals(strValue)){
                            result.setSiblings(true);
                        } else if("Нет".equals(strValue)){
                            result.setSiblings(false);
                        }
                        break;
                    default:
                        LOGGER.warn("AP[{}] has unknown APT.code=\'{}\'", entry.getKey(), entry.getKey().getType().getCode());
                        break;
                }
            }
        }
    }


    private boolean checkField(final String fieldValue, final String fieldName, final StringBuilder checker, final boolean hasErrors) {
        if (StringUtils.isEmpty(fieldValue)) {
            if (hasErrors) {
                checker.append("\n");
            }
            checker.append(String.format(CHECK_MESSAGE_FOR_REQUIRED_FIELD, fieldName));
            return true;
        }
        return hasErrors;
    }


    private boolean checkSendConfiguration() {
        if (!ConfigManager.HsctProp().isSendActive()) {
            LOGGER.warn("HsctProp.sendActive is not \'on\' or is empty");
            return false;
        } else if (StringUtils.isEmpty(ConfigManager.HsctProp().ServiceUrl())) {
            LOGGER.warn("HsctProp.serviceUrl is empty");
            return false;
        } else if (StringUtils.isEmpty(ConfigManager.HsctProp().SendUser())) {
            LOGGER.warn("HsctProp.sendUser is empty");
            return false;
        } else if (StringUtils.isEmpty(ConfigManager.HsctProp().SendPassword())) {
            LOGGER.warn("HsctProp.sendPassword is empty");
            return false;
        } else {
            return true;
        }
    }


    private HsctExternalResponse sendToExternalSystem(
            final HsctExternalRequest externalRequest, final String serviceUrl, final int num, final int rowNum
    ) throws IOException {
        final String fullUrl = constructHsctUrl(serviceUrl);
        final HttpPost post = new HttpPost(fullUrl);
        final ObjectMapper externalMapper = getExternalSystemSerializer();
        final String jsonRepresentation = externalMapper.writeValueAsString(externalRequest);
        final StringEntity input = new StringEntity(jsonRepresentation, MediaType.APPLICATION_JSON, HTTP.UTF_8);
        post.setEntity(input);
        LOGGER.debug(
                "#{}-{} HTTP {} {} HEADERS=\'{}\' PAYLOAD = \'{}\'",
                num,
                rowNum,
                post.getMethod(),
                post.getURI(),
                post.getAllHeaders(),
                jsonRepresentation
        );

        final HttpResponse response = new DefaultHttpClient().execute(post);
        final HttpStatus httpStatus = HttpStatus.valueOf(response.getStatusLine().getStatusCode());
        final String responseBody = readFully(response.getEntity().getContent(), StandardCharsets.UTF_8);
        LOGGER.info("#{}-{} Status : {} RAW = \"{}\"", num, rowNum, httpStatus, responseBody);
        /**
         * Три вида ошибок:
         1. Неверный логин и пароль. Это код 401, тело ответа - отсутствует. Нужно вывести: Проблема при передаче данных в систему ТГСК. Оставить заявку в очереди.
         2. Любая ошибка с сервером планирования или где-то на пути. Это коды 5xx, тело ответа - что угодно. Вывести: Проблема при передаче данных в систему ТГСК. Оставить заявку в очереди.
         3. Проблема в заявке. Это коды 4xx (кроме 401), тело - объект: {errorMessage: "Строка которую нужно вывести в строке Результат"}. Удалить заявку из очереди на отправку.
         */
        HsctExternalResponse result;
        try {
            if (HttpStatus.UNAUTHORIZED.equals(httpStatus)) { //401
                LOGGER.error("#{}-{} 401 - UNAUTHORIZED | return with error message", num, rowNum);
                result = new HsctExternalResponse();
                result.setErrorMessage("Проблема при передаче данных в систему ТГСК");
                result.setRemoveFromQueue(false);
            } else if (httpStatus.is5xxServerError()) {
                LOGGER.error("#{}-{} 5XX - SERVER_ERROR | return with error message", num, rowNum);
                result = new HsctExternalResponse();
                result.setErrorMessage("Проблема при передаче данных в систему ТГСК");
                result.setRemoveFromQueue(false);
            } else if (httpStatus.is4xxClientError() && !HttpStatus.UNAUTHORIZED.equals(httpStatus)) {
                LOGGER.error("#{}-{} 4XX - CLIENT_ERROR | return mapped message. Remove from queue", num, rowNum);
                result = getExternalSystemSerializer().readValue(responseBody, HsctExternalResponse.class);
                result.setRemoveFromQueue(true);
            } else if (HttpStatus.OK.equals(httpStatus)) {
                LOGGER.info("#{}-{} 200 - OK | Successfully interacted. Return parsed", num, rowNum);
                result = getExternalSystemSerializer().readValue(responseBody, HsctExternalResponse.class);
                result.setRemoveFromQueue(true);
            } else {
                LOGGER.error("#{}-{} UNKNOWN STATUS_CODE | Return error", num, rowNum);
                result = new HsctExternalResponse();
                result.setRemoveFromQueue(false);
                result.setErrorMessage("Невозможно распознать код ответа ТГСК. Обратитесь к администратору");
            }
        } catch (JsonMappingException e) {
            LOGGER.error("Cannot parse entity", e);
            result = new HsctExternalResponse();
            result.setRemoveFromQueue(false);
            result.setErrorMessage("Невозможно преобразовать ответ от ТГСК. Обратитесь к администратору");
        }
        return result;
    }

    private ObjectMapper getExternalSystemSerializer() {
        if (mapper == null) {
            mapper = new ObjectMapper();
            mapper.configure(SerializationConfig.Feature.WRAP_ROOT_VALUE, true);
            //mapper.configure(DeserializationConfig.Feature.UNWRAP_ROOT_VALUE, true);
            mapper.configure(SerializationConfig.Feature.INDENT_OUTPUT, true);
            mapper.configure(SerializationConfig.Feature.WRITE_NULL_MAP_VALUES, true);
        }
        return mapper;
    }

    private String constructHsctUrl(final String serviceUrl) {
        return serviceUrl.replaceFirst("\\{user_name\\}", ConfigManager.HsctProp().SendUser()).replaceFirst(
                "\\{user_token\\}", ConfigManager.HsctProp().SendPassword()
        );

    }

    public String readFully(InputStream inputStream, Charset encoding) throws IOException {
        if (inputStream == null) {
            return null;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[2048];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            baos.write(buffer, 0, length);
        }
        return baos.toString(encoding.name());
    }
}
