package ru.korus.tmis.ws.laboratory.business;

import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.lis.data.AnalysisResult;

import javax.ejb.Local;
import java.util.List;

@Local
public interface LaboratoryBeanLocal {
    /**
     * Отправить в ЛИС запрос анализа
     *
     * @param actionId идентификатор действия, соответствующего назначенному анализу
     */
    void sendAnalysisRequest(int actionId) throws CoreException;

    /**
     * Сохранить результаты анализа в БД ТМИС
     *
     * @param requestId          идентификатор направления (Action.id)
     * @param results            результаты анализа
     * @param biomaterialDefects дефекты биоматериала
     * @throws CoreException
     */
    void setAnalysisResults(
            int requestId,
            List<AnalysisResult> results,
            String biomaterialDefects) throws CoreException;
}
