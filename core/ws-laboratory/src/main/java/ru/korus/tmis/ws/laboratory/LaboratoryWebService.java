package ru.korus.tmis.ws.laboratory;

import ru.korus.tmis.lis.data.*;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * Интерфейс для взаимодействия ЛИС и новой МИС.
 */
@WebService(
        targetNamespace = "http://korus.ru/laboratory/labisws",
        name = "labisws")
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
