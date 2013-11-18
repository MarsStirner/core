package ru.korus.tmis.laboratory.altey;

import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.laboratory.data.lis.accept.AnalysisResult;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.util.List;

/**
 * Интерфейс для взаимодействия ЛИС и новой МИС.
 */
@WebService(
        targetNamespace = "http://korus.ru/tmis/tmis-laboratory-integration",
        name = "tmis")
public interface AlteyResultsService {

    /**
     * Запрос для передачи результатов анализов из ЛИС в МИС.
     *
     * @param requestId          идентификатор направления на анализы
     * @param biomaterialDefects браки биоматериала
     * @paran results                   результаты анализов
     */
    @WebMethod
    @WebResult(targetNamespace = "http://korus.ru/tmis/tmis-laboratory-integration")
    int setAnalysisResults(
            @WebParam(name = "orderMisId", targetNamespace = "http://korus.ru/tmis/tmis-laboratory-integration")
            String requestId,
            @WebParam(name = "referralIsFinished", targetNamespace = "http://korus.ru/tmis/tmis-laboratory-integration")
            boolean finished,
            @WebParam(name = "results", targetNamespace = "http://korus.ru/tmis/tmis-laboratory-integration")
            List<AnalysisResult> results,
            @WebParam(name = "biomaterialDefects", targetNamespace = "http://korus.ru/tmis/tmis-laboratory-integration")
            String biomaterialDefects) throws CoreException;
}
