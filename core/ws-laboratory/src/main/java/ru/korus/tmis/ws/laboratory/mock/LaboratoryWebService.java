package ru.korus.tmis.ws.laboratory.mock;

import ru.korus.tmis.laboratory.AnalysisRequestException;
import ru.korus.tmis.laboratory.data.lis.mock.BiomaterialInfo;
import ru.korus.tmis.laboratory.data.lis.mock.DiagnosticRequestInfo;
import ru.korus.tmis.laboratory.data.lis.mock.OrderInfo;
import ru.korus.tmis.laboratory.data.lis.mock.PatientInfo;
import ru.korus.tmis.util.CompileTimeConfigManager;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * Интерфейс для взаимодействия ЛИС и новой МИС.
 */
@WebService(
  targetNamespace = CompileTimeConfigManager.Laboratory.Namespace,
  name = CompileTimeConfigManager.Laboratory.ServiceName)
public interface LaboratoryWebService {

    @WebMethod
    void queryAnalysis(
            @WebParam(name = "patientInfo")
            PatientInfo patientInfo,
            @WebParam(name = "requestInfo")
            DiagnosticRequestInfo requestInfo,
            @WebParam(name = "biomaterialInfo")
            BiomaterialInfo biomaterialInfo,
            @WebParam(name = "orderInfo")
            OrderInfo orderInfo
    ) throws AnalysisRequestException;
}
