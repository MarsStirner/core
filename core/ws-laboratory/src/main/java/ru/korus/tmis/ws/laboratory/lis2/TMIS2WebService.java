package ru.korus.tmis.ws.laboratory.lis2;

import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.laboratory.data.lis2.accept.AnalysisResult;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.List;

import static ru.korus.tmis.util.CompileTimeConfigManager.Laboratory.Namespace;
/**
 * Интерфейс для взаимодействия ЛИС и новой МИС.
 */
@WebService(
  targetNamespace = Namespace,
  name = "tmis2")
public interface TMIS2WebService {

    /**
     * Запрос для передачи результатов анализов из ЛИС в МИС.
     * @param requestId                 идентификатор направления на анализы
     * @paran results                   результаты анализов
     * @param biomaterialDefects        браки биоматериала
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
