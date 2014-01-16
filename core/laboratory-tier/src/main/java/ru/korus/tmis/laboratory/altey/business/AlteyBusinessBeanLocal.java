package ru.korus.tmis.laboratory.altey.business;

import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.laboratory.altey.accept2.AnalysisResult;

import javax.ejb.Local;
import java.util.List;


@Local
public interface AlteyBusinessBeanLocal {
    /**
     * Отправить в ЛИС запрос анализа
     *
     * @param actionId идентификатор действия, соответствующего назначенному анализу
     */
    void sendLisAnalysisRequest(int actionId) throws CoreException;

    /**
     * Сохранить результаты анализа в БД ТМИС
     *
     * @param requestId          идентификатор направления (Action.id)
     * @param results            результаты анализа
     * @param biomaterialDefects дефекты биоматериала
     * @throws ru.korus.tmis.core.exception.CoreException
     *
     */
    int setLisAnalysisResults(
            int requestId,
            boolean lastPiece,
            List<AnalysisResult> results,
            String biomaterialDefects) throws CoreException;

}
