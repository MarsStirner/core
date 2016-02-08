package ru.korus.tmis.hsct;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.database.common.DbActionBeanLocal;
import ru.korus.tmis.core.database.hsct.DbQueueHsctRequestBean;
import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.entity.model.hsct.QueueHsctRequest;
import ru.korus.tmis.hsct.external.*;
import ru.korus.tmis.scala.util.ConfigManager;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Author: Upatov Egor <br>
 * Date: 23.12.2015, 16:40 <br>
 * Company: hitsl (Hi-Tech Solutions) <br>
 * Description: <br>
 */
@Stateless
public class HsctBean {
    private final static Logger LOGGER = LoggerFactory.getLogger("HSCT");

    @EJB
    private DbActionBeanLocal dbAction;

    @EJB
    private DbQueueHsctRequestBean dbQueueHsctRequestBean;

    private HsctExternalRequest constructExternalHsctRequest(final Action action) {
        final SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
        final HsctExternalRequest result = new HsctExternalRequest();
        result.setDiseaseStatus("Статус болезни");
        result.setDiagnosisDate("2015-11-11");
        result.setAntiCmvIgG("val_antiCmvIgG");
        result.setIndications("Показания к ТГСК");
        result.setIndicationsDate("2015-11-29");
        result.setHsctOptimalDate("2015-12");
        result.setHsctTypeCode("1");
        result.setSiblings(true);
        //
        result.setMisId(action.getId().toString());
        final Patient patient = new Patient();
        patient.setFirstName("Имя");
        patient.setLastName("Фамилия");
        patient.setPatrName("Отчетсво");
        patient.setMisId("999");
        patient.setBirthDate(sdf.format(new Date()));
        result.setPatient(patient);
        result.setDepartmentCode("01");
        final Doctor doctor = new Doctor();
        doctor.setFirstName("2Имя");
        doctor.setLastName("2Фамилия");
        doctor.setPatrName("2Отчетсво");
        doctor.setMisId("999");
        doctor.setDepartmentCode("02");
        result.setDoctor(doctor);
        final Spokesman spokesman = new Spokesman();
        spokesman.setFirstName("3Имя");
        spokesman.setLastName("3Фамилия");
        spokesman.setPatrName("3Отчетсво");
        spokesman.setEmail("999@mail.ru");
        spokesman.setPhone("+78121112202");
        result.setSpokesman(spokesman);
        result.setDiagnosis("Болеет");
        result.setDiagnosisIcdCode("F.00");
        final List<Item> complications = new ArrayList<>(2);
        complications.add(new Item("Осложнение 1", "F.01"));
        complications.add(new Item("Осложнение 2", "F.02"));
        result.setComplications(complications);

        final List<Item> secondaryDiagnosis = new ArrayList<>(2);
        secondaryDiagnosis.add(new Item("Сопуствующий 1", "S.01"));
        secondaryDiagnosis.add(new Item("Сопуствующий 2", "S.02"));
        result.setSecondaryDiagnoses(secondaryDiagnosis);

        result.setProtocolCode("099900");
        result.setProtocolStartDate("2000-01-01");
        result.setProtocolEndDate("2000-01-02");

        result.setProtocolStageCode("stage00");
        result.setProtocolStartDate("2000-01-03");
        result.setProtocolStageEndDate("2000-01-04");
        result.setWeight(82.4);
        result.setBloodTypeCode("4+");
        return result;
    }

    private HsctResponse createErrorResponse(final String message) {
        final HsctResponse result = new HsctResponse();
        result.setError(true);
        result.setErrorMessage(message);
        return result;
    }

    private HsctResponse checkAction(final Action action, final int actionId) {
        if (action == null || action.getDeleted()) {
            LOGGER.error("Action[{}] not found or has deleted=1", actionId);
            return createErrorResponse("Action[" + actionId + "] not found");
        }

        if (!StringUtils.equals(Constants.ACTION_TYPE_CODE, action.getActionType().getCode())) {
            LOGGER.error(
                    "Action[{}] has invalid ActionType.code=\'{}\' valid is \'{}\'",
                    actionId,
                    action.getActionType().getCode(),
                    Constants.ACTION_TYPE_CODE
            );
            return createErrorResponse("Action[" + actionId + "] has invalid ActionType.code=\'" + action.getActionType().getCode() + '\'');
        }
        LOGGER.debug("Action[{}] is valid", actionId);
        return null;
    }

    public HsctResponse enqueueAction(final int actionId, final AuthData authData) {
        LOGGER.debug("Call enqueueAction({}, {})", actionId, authData);
        if (!ConfigManager.HsctProp().isSendActive()) {
            LOGGER.warn("Hsct integration is disabled. Action[{}] will not be queued.", actionId);
            return createErrorResponse("Hsct integration is disabled. Action will not be queued.");
        }
        final Action action = dbAction.getById(actionId);
        HsctResponse response = checkAction(action, actionId);
        if (response != null) {
            // Проверка неудачна, возвращаем описание ошибки
            LOGGER.debug("End of enqueueAction({}, {}). Response = {}", actionId, authData.getUserId(), response);
            return response;
        } else {
            //Проверка удачна, создаем каркас ответа
            response = new HsctResponse();
        }
        QueueHsctRequest queueEntry = dbQueueHsctRequestBean.getRequestByAction(action);
        if (queueEntry == null) {
            LOGGER.debug("Action[{}] is not in queue and need to be inserted", action.getId());
            queueEntry = dbQueueHsctRequestBean.insertNewRequest(action, authData.getUser());
            response.setError(false);
            response.setErrorMessage(queueEntry.toString());
        } else {
            LOGGER.debug("Action[{}] is in queue[{}] and need to be modified", action.getId(), queueEntry);
            response.setError(dbQueueHsctRequestBean.markActive(queueEntry, authData.getUser()));
            response.setErrorMessage(queueEntry.toString());
        }
        LOGGER.debug("End of enqueueAction({}, {}). Response = {}", actionId, authData.getUserId(), response);
        return response;
    }


    public HsctResponse dequeueAction(final int actionId, final AuthData authData) {
        LOGGER.debug("Call dequeueAction({}, {})", actionId, authData);
        QueueHsctRequest queueEntry = dbQueueHsctRequestBean.getRequestByActionId(actionId);
        final HsctResponse response = new HsctResponse();
        if (queueEntry != null) {
            response.setError(dbQueueHsctRequestBean.markCanceled(queueEntry, authData.getUser()));
            response.setErrorMessage(String.format("Action[%d] removed from queue :: ", actionId).concat(queueEntry.toString()));
        } else {
            response.setError(true);
            response.setErrorMessage(String.format("Action[%d] not in queue", actionId));
        }
        LOGGER.debug("End of dequeueAction({}, {}). Response = {}", actionId, authData.getUserId(), response);
        return response;
    }

    public QueueEntry isInEnqueue(final int actionId) {
        LOGGER.debug("Call isInEnqueue({})", actionId);
        QueueHsctRequest queueEntry = dbQueueHsctRequestBean.getRequestByActionId(actionId);
        if (queueEntry != null) {
            final QueueEntry response = new QueueEntry(queueEntry);
            LOGGER.debug("End of isInEnqueue({}). Response = {}", actionId, response);
            return response;
        } else {
            final QueueEntry response = new QueueEntry();
            response.setActionId(0);
            response.setInfo("NOT EXISTS");
            response.setStatus("NOT EXISTS");
            LOGGER.debug("End of isInEnqueue({}). Response = {}", actionId, response);
            return response;
        }
    }

    public QueueContainer getCurrentActive() {
        LOGGER.debug("Call getCurrentActive()");
       final QueueContainer response = new QueueContainer();
        final List<QueueHsctRequest> entries = dbQueueHsctRequestBean.getAllActive();
        for (QueueHsctRequest entry : entries) {
            response.addToEntries(new QueueEntry(entry));
        }
        LOGGER.debug("End of getCurrentActive(). Response = {}", response);
        return response;
    }
}
