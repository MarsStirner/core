package ru.korus.tmis.laboratory.business;

import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.entity.model.ActionType;
import ru.korus.tmis.core.entity.model.Event;
import ru.korus.tmis.core.entity.model.TakenTissue;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.laboratory.data.lis.accept.AnalysisResult;
import ru.korus.tmis.laboratory.data.request.BiomaterialInfo;
import ru.korus.tmis.laboratory.data.request.DiagnosticRequestInfo;
import ru.korus.tmis.laboratory.data.request.OrderInfo;

import javax.ejb.Local;
import java.util.List;


@Local
public interface LaboratoryBeanLocal {
    /**
     * Отправить в ЛИС запрос анализа
     *
     * @param actionId идентификатор действия, соответствующего назначенному анализу
     */
    void sendLisAnalysisRequest(int actionId) throws CoreException;

    void sendLis2AnalysisRequest(int actionId) throws CoreException;

    /**
     * Сохранить результаты анализа в БД ТМИС
     *
     * @param requestId          идентификатор направления (Action.id)
     * @param results            результаты анализа
     * @param biomaterialDefects дефекты биоматериала
     * @throws CoreException
     */
    int setLisAnalysisResults(
            int requestId,
            boolean lastPiece,
            List<AnalysisResult> results,
            String biomaterialDefects) throws CoreException;

    int setLis2AnalysisResults(
            int requestId,
            int barCode,
            int period,
            boolean lastPiece,
            List<ru.korus.tmis.laboratory.data.lis2.accept.AnalysisResult> results,
            String biomaterialDefects) throws CoreException;


    //    TEMP FIXME...
    DiagnosticRequestInfo getDiagnosticRequestInfo(Action a);

    BiomaterialInfo getBiomaterialInfo(Action action, TakenTissue takenTissue);

    OrderInfo getOrderInfo(Action a, ActionType at);

    scala.Tuple2<String, String> getDiagnosis(Event e);
}
