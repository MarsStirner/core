package ru.korus.tmis.ws.transfusion.order;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.database.common.DbActionBeanLocal;
import ru.korus.tmis.core.database.common.DbActionPropertyBeanLocal;
import ru.korus.tmis.core.database.common.DbActionPropertyTypeBeanLocal;
import ru.korus.tmis.core.database.common.DbEventBeanLocal;
import ru.korus.tmis.core.database.dbutil.Database;
import ru.korus.tmis.core.entity.model.*;
import ru.korus.tmis.ws.transfusion.Constants;
import ru.korus.tmis.ws.transfusion.efive.*;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        16.01.2013, 11:45:31 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */

/**
 * Передача новых требований на выдачу КК в ТРФУ
 */
@Stateless
public class SendOrderBloodComponents {

    /**
     * Тип трансфузии - Плановая
     */
    public static final String TRANSFUSION_TYPE_PLANED = "Плановая";
    /**
     * Тип трансфузии - Экстренная
     */
    public static final String TRANSFUSION_TYPE_EMERGENCY = "Экстренная";

    public static final String MSG_CANT_SEND_MUST_RESEND_LATER = "Ошибка: Не удалось передать заявку в ТРФУ. Заявка будет переотправлена автоматически.";
    public static final String MSG_CANT_SEND_CHANGE_AND_SEND_MANUALLY = "Ошибка: Не удалось отправить заявку в ТРФУ. Исправьте ошибки и отправьте вручную.";

    private static final Logger log = LoggerFactory.getLogger(SendOrderBloodComponents.class);
    /**
     * Размер кода группы крови ("1+", "1-", и т.д.)
     */
    private static final int BLOOD_CODE_LENGHT = 2;
    /**
     * Идентификатор группы крови, соответсвующий группе 0(I)
     */
    private static final int BLOOD_GROUP_MIN = 1;
    /**
     * Идентификатор группы крови, соответсвующий группе AB(IV)
     */
    private static final int BLOOD_GROUP_MAX = 4;
    /**
     * Символ, соответсвующий положительному резус-фактору
     */
    private static final char RHESUS_FACTOR_POS = '+';
    /**
     * Символ, соответсвующий отрицательному резус-фактору
     */
    private static final char RHESUS_FACTOR_NEGATIVE = '-';

    @EJB
    private DbActionBeanLocal dbAction;

    @EJB
    private DbActionPropertyBeanLocal dbActionProperty;

    @EJB
    private DbActionPropertyTypeBeanLocal dbActionPropertyType;

    @EJB
    private DbEventBeanLocal dbEvent;

    @PersistenceContext(unitName = "s11r64")
    private EntityManager em;


    @TransactionAttribute(value = TransactionAttributeType.REQUIRES_NEW)
    public void pullDB(final TransfusionMedicalService service) {
        log.info("Start sending OrderBloodComponents...");
        final List<Action> orderActions = dbAction.getNewActionByFlatCode(Constants.TRANSFUSION_ACTION_FLAT_CODE);
        log.info("Actions to send: {}", orderActions.size());
        int send = 0;
        for (final Action action : orderActions) {
            try {
                send += sendOrderBloodComponent(action, service);
            } catch (final Exception e) {
                log.error("Exception while send Action[{}]", action.getId(), e);
            }
        }
        log.info("End sending OrderBloodComponents. Send {} / {}", send, orderActions.size());
    }

    /**
     * Отправка одного экшена-заявки на кровь в ТРФУ
     *
     * @param action  заявка на кровь из МИС
     * @param service сервис, для отправки в ТРФУ
     * @return 1- отправлено, 0 - не отправлено
     */
    public int sendOrderBloodComponent(final Action action, final TransfusionMedicalService service) {
        log.info("Start sending Action[{}]", action.getId());
        final List<String> validationErrors = validateAction(action);
        if (!validationErrors.isEmpty()) {
            log.warn("Action[{}] not valid cause: {}", action.getId(), validationErrors);
            setIntegrationResult(
                    action, ActionStatus.WAITING.getCode(), MSG_CANT_SEND_CHANGE_AND_SEND_MANUALLY + " Ошибки: " + validationErrors.toString()
            );
            log.info("End Action[{}]: \'{}\'", action.getId(), MSG_CANT_SEND_CHANGE_AND_SEND_MANUALLY + " Ошибки: " + validationErrors.toString());
            return 0;
        }
        final PatientCredentials patientCredentials = constructPatientCredentials(action);
        final OrderInformation orderInformation = constructOrderInformation(action);
        final OrderResult result;
        try {
            result = service.orderBloodComponents(patientCredentials, orderInformation);
            log.info("Action[{}]: TRFU response is {}", action.getId(), result);
            switch (result.getResultCode()) {
                case OrderResult.RESULT_CODE_SUCCESS: {
                    setIntegrationResult(action, ActionStatus.WAITING.getCode(), result.getResultInfo());
                    log.info("End Action[{}]: \'{}\'", action.getId(), result.getResultInfo());
                    return 1;
                }
                case OrderResult.RESULT_CODE_ALREADY_PROCESSED: {
                    setIntegrationResult(action, ActionStatus.FINISHED.getCode(), result.getResultInfo());
                    log.info("End Action[{}]: \'{}\'", action.getId(), result.getResultInfo());
                    return 1;
                }
                case OrderResult.RESULT_CODE_WRONG_DATA: {
                    setIntegrationResult(action, ActionStatus.WAITING.getCode(), result.getResultInfo());
                    log.info("End Action[{}]: \'{}\'", action.getId(), result.getResultInfo());
                    return 0;
                }
                case OrderResult.RESULT_CODE_INTERNAL_ERROR: {
                    setIntegrationResult(action, ActionStatus.STARTED.getCode(), result.getResultInfo());
                    log.info("End Action[{}]: \'{}\'", action.getId(), result.getResultInfo());
                    return 0;
                }
                default:{
                    setIntegrationResult(action, ActionStatus.STARTED.getCode(), "Адвокаааат! [Спрашивайте админа, что-то пошло не так]");
                    log.info("End Action[{}]: \'{}\'", action.getId(), "Адвокаааат! [Спрашивайте админа, что-то пошло не так]");
                    return 0;
                }
            }
        } catch (final Exception e) {
            log.error("Action[{}] : Exception while send to TRFU. ", action.getId(), e);
            setIntegrationResult(action, ActionStatus.STARTED.getCode(), MSG_CANT_SEND_MUST_RESEND_LATER);
            log.info("End Action[{}]: \'{}\'", action.getId(), MSG_CANT_SEND_MUST_RESEND_LATER);
            return 0;
        }
    }

    private OrderInformation constructOrderInformation(final Action action) {
        final OrderInformation result = new OrderInformation();
        result.setNumber("");
        result.setId(action.getId());
        final Staff assigner = action.getAssigner();
        final Event event = action.getEvent();
        final OrgStructure orgStructure = dbEvent.getOrgStructureForEvent(event.getId());
        result.setDivisionId(orgStructure.getId());
        result.setIbNumber(event.getExternalId());
        result.setDoseCount(0.0);
        result.setPlanDate(Database.toXMLGregorianCalendar(action.getPlannedEndDate()));
        result.setRegistrationDate(Database.toXMLGregorianCalendar(new Date()));
        result.setAttendingPhysicianId(assigner.getId());
        result.setAttendingPhysicianFirstName(assigner.getFirstName());
        result.setAttendingPhysicianLastName(assigner.getLastName());
        result.setAttendingPhysicianMiddleName(assigner.getPatrName());
        try {
            final Map<ActionProperty, List<APValue>> map = dbActionProperty.getActionPropertiesByActionIdAndActionPropertyTypeCodes(
                    action.getId(), Constants.TRANSFUSION_THERAPY_APT_FOR_SEND
            );
            for (Map.Entry<ActionProperty, List<APValue>> entry : map.entrySet()) {
                final List<APValue> values = entry.getValue();
                if (!values.isEmpty()) {
                    final String valueAsString = values.get(0).getValueAsString();
                    switch (entry.getKey().getType().getCode()) {
                        case Constants.APT_CODE_DIAGNOSIS:
                            result.setDiagnosis(valueAsString);
                            break;
                        case Constants.APT_CODE_BLOOD_COMP_TYPE:
                            final RbTrfuBloodComponentType trfuBloodComponentType = (RbTrfuBloodComponentType) values.get(0).getValue();
                            result.setComponentTypeId(trfuBloodComponentType.getTrfuId());
                            break;
                        case Constants.APT_CODE_VOLUME:
                            result.setVolume((Integer) values.get(0).getValue());
                            break;
                        case Constants.APT_CODE_ROOT_CAUSE:
                            result.setIndication(valueAsString);
                            break;
                        case Constants.APT_CODE_TYPE:
                            result.setTransfusionType(StringUtils.containsIgnoreCase(valueAsString, TRANSFUSION_TYPE_EMERGENCY) ? 1 : 0);
                            break;
                        case Constants.APT_CODE_REQ_BLOOD_TYPE:
                            final List<String> groups = Arrays.asList(
                                    "0(I)Rh-", "0(I)Rh+", "A(II)Rh-", "A(II)Rh+", "B(III)Rh-", "B(III)Rh+", "AB(IV)Rh-", "AB(IV)Rh+"
                            );
                            if (groups.contains(valueAsString)) {
                                result.setBloodGroupId(groups.indexOf(valueAsString) / 2 + 1);
                                result.setRhesusFactorId(valueAsString.endsWith("-") ? 1 : 0);
                            } else {
                                result.setBloodGroupId(-1);
                                result.setRhesusFactorId(-1);
                            }
                            break;
                        default:
                            log.warn("AP[{}] has unknown APT.code=\'{}\'", entry.getKey(), entry.getKey().getType().getCode());
                            break;
                    }
                }
            }
        } catch (Exception e) {
            log.error("{}", e);
        }


        result.setLastModifyDateTime(Database.toXMLGregorianCalendar(action.getModifyDatetime()));
        return result;
    }

    /**
     * Построение структуры для обмена с ТРФУ
     *
     * @param action Экшен - источник данных для построения структуры
     * @return PatientCredentials
     */
    private PatientCredentials constructPatientCredentials(final Action action) {
        final PatientCredentials result = new PatientCredentials();
        final Event event = action.getEvent();
        final Patient patient = event.getPatient();
        result.setId(patient.getId());
        result.setLastName(patient.getLastName());
        result.setFirstName(patient.getFirstName());
        result.setMiddleName(patient.getPatrName());
        result.setBirth(Database.toXMLGregorianCalendar(patient.getBirthDate()));
        final String clientBloodTypeCode = patient.getBloodType().getCode();
        int bloodGroupId;
        try {
            bloodGroupId = Integer.parseInt(clientBloodTypeCode.substring(0, 1));
            if (bloodGroupId < BLOOD_GROUP_MIN || bloodGroupId > BLOOD_GROUP_MAX) {
                bloodGroupId = -1;
            }
        } catch (NumberFormatException e) {
            bloodGroupId = -1;
        }

        int rhesusFactorId;
        if (clientBloodTypeCode.charAt(1) == RHESUS_FACTOR_POS) {
            rhesusFactorId = 0;
        } else if (clientBloodTypeCode.charAt(1) == RHESUS_FACTOR_NEGATIVE) {
            rhesusFactorId = 1;
        } else {
            rhesusFactorId = -1;
        }

        result.setBloodGroupId(bloodGroupId);
        result.setRhesusFactorId(rhesusFactorId);

        result.setBloodKell(patient.getBloodKell().equals(BloodKell.NOT_DEFINED) ? null : BloodKell.POSITIVE.equals(patient.getBloodKell()));
        if (patient.getRbBloodPhenotype() != null) {
            for (String type : new String[]{"D", "C", "E", "c", "e"}) {
                BloodPhenotype bloodPhenotype = new BloodPhenotype();
                bloodPhenotype.setPhenotype(type);
                bloodPhenotype.setValue(patient.getRbBloodPhenotype().getCode().contains(type + "+"));
                result.getBloodPhenotype().add(bloodPhenotype);
            }
        }
        return result;
    }

    /**
     * Выставить резульатат интеграции для Экшена
     *
     * @param action       экшен чей резульатт надо установить
     * @param actionStatus статус который надо выставить в экшене
     * @param message      строка, которую надо установить в свойство-результат интеграции экшена
     */
    private void setIntegrationResult(final Action action, final short actionStatus, final String message) {
        action.setStatus(actionStatus);
        try {
            dbAction.updateAction(action);
            for (ActionProperty ap : action.getActionProperties()) {
                if (Constants.APT_CODE_ORDER_RESULT.equals(ap.getType().getCode())) {
                    final APValue apValue = dbActionProperty.setActionPropertyValue(ap, message, 0);
                    em.merge(apValue);
                    return;
                }
            }
            log.warn("Action[{}]: NOT FOUND AP[code=\'{}\']. So create it", action.getId(), Constants.APT_CODE_ORDER_RESULT);
            final ActionPropertyType actionPropertyType = dbActionPropertyType.getActionPropertyTypeByActionTypeIdAndTypeCode(
                    action.getActionType().getId(), Constants.APT_CODE_ORDER_RESULT, false
            );
            final ActionProperty ap = dbActionProperty.createActionProperty(action, actionPropertyType.getId(), null);
            final APValue apValue = dbActionProperty.setActionPropertyValue(ap, message, 0);
            em.persist(apValue);
        } catch (Exception e) {
            log.error("Action[{}]: Result store Error (need to store {status={}, message=\'{}\'} )", action.getId(), actionStatus, message, e);
        }
    }


    /**
     * Проверка экшена перед отправкой
     *
     * @param action экшен для проверки
     * @return пустой список если экшен валиден, иначе - строки с описанием ошибок
     */
    private List<String> validateAction(final Action action) {
        final List<String> result = new ArrayList<>(3);
        if (action.getDeleted()) {
            log.warn("Action[{}]: is deleted", action.getId());
            result.add("Action marked as deleted. Contact your administrator");
            return result;
        }
        final ActionType actionType = action.getActionType();
        if (!StringUtils.equalsIgnoreCase(Constants.TRANSFUSION_ACTION_FLAT_CODE, actionType.getFlatCode())) {
            log.warn(
                    "Action[{}]: is not \'{}\' as expected. And has ActionType[{}][flatCode=\'{}\']",
                    action.getId(),
                    Constants.TRANSFUSION_ACTION_FLAT_CODE,
                    actionType.getId(),
                    actionType.getFlatCode()
            );
            result.add("Передаваемый элемент не является заявкой");
            return result;
        }
        final Event event = action.getEvent();
        final Action latestMove = dbAction.getLatestMove(event.getId());
        if (latestMove == null) {
            log.warn("Action[{}]: Event[{}] hasn't \'moving\' actions ", action.getId(), event.getId());
            result.add("Пациент снят с койки");
        }
        final OrgStructure orgStructure = dbEvent.getOrgStructureForEvent(event.getId());
        if(orgStructure == null){
            log.warn("Action[{}]: Event[{}] hasn't OrgStructure from \'movings\' or received Action ", action.getId(), event.getId());
            result.add("Не задано отделение пребывания (как среди движений, так и в поступлении)");
        }
        final Patient patient = event.getPatient();
        if (patient.getBirthDate() == null) {
            log.warn("Action[{}]: Client[{}] has null or empty BirthDate", action.getId(), patient.getId());
            result.add("Не установлена дата рождения у пациента");
        }
        final RbBloodType clientBloodType = patient.getBloodType();
        if (clientBloodType == null || RbBloodType.getEmptyBloodType().equals(clientBloodType) || StringUtils.isEmpty(clientBloodType.getCode())) {
            log.warn("Action[{}]: Client[{}] has null or empty BloodType", action.getId(), patient.getId());
            result.add("Не установлена группа крови у пациента");
        } else if (BLOOD_CODE_LENGHT != clientBloodType.getCode().length()) {
            log.warn("Action[{}]: Client[{}] has strange BloodType \'{}\'", action.getId(), patient.getId(), clientBloodType.getCode());
            result.add("Невозможно преобразовать группу крови пациента");
        }
        if (action.getAssigner() == null) {
            log.warn("Action[{}]: Assigner not set", action.getId());
            result.add("Ошибка: Не задан врач, назначивший трансфузию");
        }
        if (action.getPlannedEndDate() == null) {
            log.warn("Action[{}]: plannedEndDate not set", action.getId());
            result.add("Ошибка: Не задана планируемая дата трансфузии");
        }
        if (StringUtils.isEmpty(event.getExternalId())) {
            log.warn("Action[{}]: Event[{}].externalId not set", action.getId(), event.getId());
            result.add("Ошибка: Для выбранного пациента нет номера истории болезни");
        }
        return result;
    }
}
