package ru.korus.tmis.laboratory.across;

import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.laboratory.across.accept2.AnalysisResult;


import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.List;

import static ru.korus.tmis.util.CompileTimeConfigManager.Laboratory.Namespace;

/**
 * Интерфейс для взаимодействия ЛИС и новой МИС.
 */
@WebService(
        targetNamespace = "http://www.korusconsulting.ru",
        name = "service-across-results")
public interface AcrossResultsService {

    /**
     * Запрос для передачи результатов анализов из ЛИС в МИС.
     *
     * @param requestId          идентификатор направления на анализы
     * @param biomaterialDefects браки биоматериала
     * @paran results                   результаты анализов
     */
    @WebMethod
    int setAnalysisResults(
            @WebParam(name = "orderMisId", targetNamespace = Namespace)
            String requestId,
            @WebParam(name = "orderBarCode", targetNamespace = Namespace)
            int orderBarCode,
            @WebParam(name = "orderPrefBarCode", targetNamespace = Namespace)
            int orderPrefBarCode,
            @WebParam(name = "referralIsFinished", targetNamespace = Namespace)
            Boolean finished,
            @WebParam(name = "results", targetNamespace = Namespace)
            List<AnalysisResult> results,
            @WebParam(name = "biomaterialDefects", targetNamespace = Namespace)
            String biomaterialDefects,
            @WebParam(name = "ResultDoctorLisId", targetNamespace = Namespace)
            Integer doctorLisId) throws CoreException;
}
