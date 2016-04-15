package ru.korus.tmis.laboratory.across.business;

import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.entity.model.ActionType;
import ru.korus.tmis.core.entity.model.TakenTissue;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.laboratory.across.accept2.AnalysisResult;


import javax.ejb.Local;
import java.util.List;


@Local
public interface AcrossBusinessBeanLocal {
    /**
     * Отправить в ЛИС запрос анализа
     *
     * @param actionId идентификатор действия, соответствующего назначенному анализу
     */
    void sendAnalysisRequestToAcross(int actionId) throws CoreException;

    /**
     * Сохранить результаты анализа в БД ТМИС
     *
     * @param requestId          идентификатор направления (Action.id)
     * @param results            результаты анализа
     * @param biomaterialDefects дефекты биоматериала
     * @throws ru.korus.tmis.core.exception.CoreException
     *
     */
    int setLis2AnalysisResults(
            int requestId,
            int barCode,
            int period,
            boolean lastPiece,
            List<AnalysisResult> results,
            String biomaterialDefects) throws CoreException;


    //todo Временный хак, нужен для быстрого запуска
//    DiagnosticRequestInfo getDiagnosticRequestInfo(Action a);
//    BiomaterialInfo getBiomaterialInfo(Action action, TakenTissue takenTissue);
//    OrderInfo getOrderInfo(Action a, ActionType at);
}
