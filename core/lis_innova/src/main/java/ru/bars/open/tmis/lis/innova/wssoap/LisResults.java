package ru.bars.open.tmis.lis.innova.wssoap;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.bars.open.tmis.lis.innova.logic.DbTakenTissueJournalBeanLocal;
import ru.bars.open.tmis.lis.innova.wssoap.entites.AnalysisResult;
import ru.bars.open.tmis.lis.innova.wssoap.entites.ResearchResult;
import ru.korus.tmis.core.database.DbStaffBeanLocal;
import ru.korus.tmis.core.database.common.DbActionPropertyBeanLocal;
import ru.korus.tmis.core.database.common.DbCustomQueryLocal;
import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.entity.model.ActionStatus;
import ru.korus.tmis.core.entity.model.Staff;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.EJB;
import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Интерфейс для взаимодействия ЛИС и новой МИС.
 */
@WebService(
        targetNamespace = "ru.bars.open.integration.lis.innova",
        name = "service-lis-results",
        wsdlLocation = "service-lis-results.wsdl")
@HandlerChain(file = "lis-soap-handler-chain.xml")
public class LisResults {

    private static final Logger log = LoggerFactory.getLogger("LIS_INNOVA");

    private static final AtomicLong counter = new AtomicLong();

    @EJB
    private DbTakenTissueJournalBeanLocal dbTTJ;

    @EJB
    private DbStaffBeanLocal dbStaff;

    @EJB
    private DbActionPropertyBeanLocal dbActionProperty;

    @EJB
    private DbCustomQueryLocal dbCustomQuery;

    /**
     * Получение уникального идентифкатора заявки для логиирования
     *
     * @param requestId        ИД заявки из ЛИС
     * @param barCode          Баркод заявки из ЛИС
     * @param orderPrefBarCode Префикс баркода заявки из ЛИС
     * @return Строка с уникальным идентифкатором заявки
     */
    private static String getRequestUID(int requestId, int barCode, int orderPrefBarCode) {
        return "#" + counter.incrementAndGet() + " # " + requestId + '-' + orderPrefBarCode + barCode;
    }

    /**
     * Запрос для передачи результатов анализов из ЛИС в МИС.
     *
     * @param requestId          идентификатор направления на анализы
     * @param biomaterialDefects браки биоматериала
     * @paran results                   результаты анализов
     */
    @WebMethod
    public int setAnalysisResults(
            @WebParam(name = "orderMisId", targetNamespace = "ru.bars.open.integration.lis.innova") int requestId,
            @WebParam(name = "orderBarCode", targetNamespace = "ru.bars.open.integration.lis.innova") int barCode,
            @WebParam(name = "orderPrefBarCode", targetNamespace = "ru.bars.open.integration.lis.innova") int orderPrefBarCode,
            @WebParam(name = "biomaterialDefects", targetNamespace = "ru.bars.open.integration.lis.innova") String biomaterialDefects,
            @WebParam(name = "researches", targetNamespace = "ru.bars.open.integration.lis.innova") List<ResearchResult> researches
    ) throws CoreException {
        final String requestUID = getRequestUID(requestId, barCode, orderPrefBarCode);
        log.info(
                "{} Receive results from LIS: orderMisId={}, orderBarCode={}, OrderPrefBarCode={},  biomaterialDefects='{}'",
                requestUID,
                requestId,
                barCode,
                orderPrefBarCode,
                biomaterialDefects
        );
        if (researches == null || researches.isEmpty()) {
            log.error("{} End. Results is empty - return 1", requestUID);
            return 1;
        }
        log.info("{} Result.size={}", requestUID, researches.size());
        /*for (AnalysisResult analysisResult : results) {
            log.debug("{} {}", requestUID, analysisResult);
        }
        final TakenTissue ttj = requestId > 0 ? dbTTJ.get(requestId) : null;
        if (ttj == null) {
            log.error("{} End. TTJ[{}] not found - return 2", requestUID, requestId);
            return 2;
        }
        final String INNOVA_LAB_CODE = LisInnovaSettings.getLabCode();
        final List<Action> actionList = dbTTJ.getActionsByTTJAndLaboratoryCode(ttj.getId(), INNOVA_LAB_CODE);
        if (actionList.isEmpty()) {
            log.warn("{} End. TTJ[{}]: No Action found for Laboratory[{}] - return 3", requestUID, ttj.getId(), INNOVA_LAB_CODE);
            return 3;
        }
        final Staff lisDoctor = mapDoctorFromLis(doctorLisId, requestUID);
        log.info("{} Found {} actions for Laboratory[{}]", requestUID, actionList.size(), INNOVA_LAB_CODE);
        for (Action action : actionList) {
            applyLisResultsToAction(action, results, lisDoctor, lastPiece, biomaterialDefects, requestUID);
        }
        log.info("{} End. Success - return 0", requestUID);
        */
        return 0;
    }

    private void applyLisResultsToAction(
            final Action a,
            final List<AnalysisResult> analysisResults,
            final Staff doctor,
            final Boolean lastPiece,
            final String defects,
            final String requestUID
    ) {
        log.info("{} Process Action[{}][{}] - \'{}\'", requestUID, a.getId(), a.getActionType().getCode(), a.getActionType().getName());
        if (ActionStatus.FINISHED.getCode() == a.getStatus()) {
            log.warn(
                    "{} Action[{}] has status = FINISHED. That means changes may not be applied. But will be processed", requestUID, a.getId()
            );
        }
        final Date currentDateTime = new Date();
        a.setModifyDatetime(currentDateTime);
        if (StringUtils.isNotEmpty(defects)) {
            a.setNote("ЛИС сообщает о дефекте биоматериала: " + defects);
        }
        a.setEndDate(currentDateTime);
        if (lastPiece) {
            a.setStatus(ActionStatus.FINISHED.getCode());
        }
        if (doctor != null) {
            a.setExecutor(doctor);
        }
    }
        /*
        //Маппим AP и результаты из ЛИС
        for (ActionProperty ap : a.getActionProperties()) {
            //Фильтруем AP которые не удалены, и имеют тест (ap.getType().getTest())
            if (!ap.getDeleted() && ap.getType() != null && ap.getType().getTest() != null) {
                final RbTest apTest = ap.getType().getTest();
                for (AnalysisResult res : analysisResults) {
                    if (StringUtils.equalsIgnoreCase(apTest.getCode(), res.getCode())) {
                        log.info(
                                "{} Writing AP[{}] to \'{}\'. Mapped by RbTest.code=\'{}\'", requestUID, ap.getId(), res.getTextValue(), res.getCode()
                        );
                        applyAnalysisResultToActionProperty(ap, res, requestUID);
                        break;
                    } else if (StringUtils.equalsIgnoreCase(apTest.getName(), res.getName())) {
                        log.info(
                                "{} Writing AP[{}] to \'{}\'. Mapped by RbTest.name=\'{}\'", requestUID, ap.getId(), res.getTextValue(), res.getName()
                        );
                        applyAnalysisResultToActionProperty(ap, res, requestUID);
                        break;
                    }
                }
            }
        }

    }

    private void applyAnalysisResultToActionProperty(final ActionProperty ap, final AnalysisResult result, final String requestUID) {
        if (StringUtils.isNotEmpty(result.getNorm())) {
            ap.setNorm(result.getNorm());
            if (log.isTraceEnabled()) {
                log.trace("{} AP[{}] set norm to \'{}\'", requestUID, ap.getId(), result.getNorm());
            }
        }
        if (StringUtils.isNotEmpty(result.getUnitCode())) {
            final RbUnit unit = dbCustomQuery.getUnitByCode(result.getUnitCode());
            if (unit != null) {
                ap.setUnit(unit);
                if (log.isTraceEnabled()) {
                    log.trace("{} AP[{}] set unit to \'{}\'[{}]", requestUID, ap.getId(), result.getUnitCode(), unit.getId());
                }
            }
        }
        if(result.getEndDate() != null) {
            ap.setModifyDatetime(result.getEndDate());
        }
        switch (result.getValueType()){
            case TEXT:{
                try {
                    dbActionProperty.setActionPropertyValue(ap, result.getTextValue(), 0);
                } catch (CoreException e) {
                    log.error("{} Exception while set AP[{}] value to \'{}\'", requestUID, ap.getId(), result.getTextValue(), e);
                }
                break;
            }
            case IMAGE:{
                break;
            }
            default:{
                break;
            }
        }

    }
    */

    private Staff mapDoctorFromLis(final Integer doctorId, final String requestUID) {
        final Staff result = dbStaff.getStaffById(doctorId);
        if (result == null) {
            log.warn("{} Cannot map LIS Doctor[{}] to Person: not found by ID", requestUID, doctorId);
            return null;
        } else if (result.getDeleted()) {
            log.warn("{} LIS Doctor map to DELETED {}. But will be use this person.", requestUID, result.getInfoString());
            return result;
        } else {
            log.info("{} LIS Doctor map to {}", requestUID, result.getInfoString());
            return result;
        }
    }
}
