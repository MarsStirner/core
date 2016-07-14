package ru.bars.open.tmis.lis.innova.wssoap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.bars.open.tmis.lis.innova.config.LisInnovaSettings;
import ru.bars.open.tmis.lis.innova.logic.DbTakenTissueJournalBeanLocal;
import ru.bars.open.tmis.lis.innova.wssoap.entites.AnalysisResult;
import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.entity.model.TakenTissue;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.EJB;
import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static ru.korus.tmis.util.CompileTimeConfigManager.Laboratory.Namespace;

/**
 * Интерфейс для взаимодействия ЛИС и новой МИС.
 */
@WebService(
        targetNamespace = "http://www.korusconsulting.ru",
        name = "service-lis-results")
@HandlerChain(file = "lis-soap-handler-chain.xml")
public class LisResultsService {

    private static final Logger log = LoggerFactory.getLogger("LIS_INNOVA");

    private static final AtomicLong counter = new AtomicLong();

    @EJB
    private DbTakenTissueJournalBeanLocal dbTTJ;


    /**
     * Запрос для передачи результатов анализов из ЛИС в МИС.
     *
     * @param requestId          идентификатор направления на анализы
     * @param biomaterialDefects браки биоматериала
     * @paran results                   результаты анализов
     */
    @WebMethod
    public int setAnalysisResults(
            @WebParam(name = "orderMisId", targetNamespace = Namespace) int requestId,
            @WebParam(name = "orderBarCode", targetNamespace = Namespace) int barCode,
            @WebParam(name = "orderPrefBarCode", targetNamespace = Namespace) int orderPrefBarCode,
            @WebParam(name = "referralIsFinished", targetNamespace = Namespace) Boolean lastPiece,
            @WebParam(name = "results", targetNamespace = Namespace) List<AnalysisResult> results,
            @WebParam(name = "biomaterialDefects", targetNamespace = Namespace) String biomaterialDefects,
            @WebParam(name = "ResultDoctorLisId", targetNamespace = Namespace) Integer doctorLisId
    ) throws CoreException {
        final String requestUID = new StringBuilder("#").append(counter.incrementAndGet()).append(" # ").append(requestId).append('-').append(
                orderPrefBarCode
        ).append(barCode).toString();
        log.info("{} Receive results from LIS: orderMisId={}, orderBarCode={}", requestUID, requestId, barCode);

        log.debug(
                "{} OrderPrefBarCode={}, referralIsFinished={}, biomaterialDefects=\'{}\', ResultDoctorLisId={}",
                requestUID,
                orderPrefBarCode,
                lastPiece,
                biomaterialDefects,
                doctorLisId
        );
        if (results == null || results.isEmpty()) {
            log.error("{} End. Results is empty - return 1", requestUID);
            return 1;
        }
        log.info("{} Result.size={}", requestUID, results.size());
        for (AnalysisResult analysisResult : results) {
            log.debug("{} {}", requestUID, analysisResult);
        }
        final TakenTissue ttj = requestId > 0 ? dbTTJ.get(requestId) : null;
        if(ttj == null){
            log.error("{} End. TTJ[{}] not found - return 2", requestUID, requestId);
            return 2;
        }
        final String INNOVA_LAB_CODE = LisInnovaSettings.getLabCode();
        final List<Action> actionList = dbTTJ.getActionsByTTJAndLaboratoryCode(ttj.getId(), INNOVA_LAB_CODE);
        if (actionList.isEmpty()) {
            log.warn("{} End. TTJ[{}]: No Action found for Laboratory[{}] - return 3", requestUID, ttj.getId(), INNOVA_LAB_CODE);
            return 3;
        }
        log.info("{} Found {} actions for Laboratory[{}]", requestUID, actionList.size(), INNOVA_LAB_CODE);
        for (Action action : actionList) {
            log.info("{} Action[{}] - \'{}\'", requestUID, action.getId(), action.getActionType().getName());
        }

        log.info("{} End. Success - return 0", requestUID);
        return 0;
    }
}
