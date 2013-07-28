package ru.sda.server;

import ru.korus.tmis.core.exception.CoreException;
import ru.sda.data.Patient;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import static ru.korus.tmis.util.CompileTimeConfigManager.Laboratory.Namespace;
import static ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.HL7Specification.NAMESPACE;

/**
 * @author anosov
 *         Date: 27.07.13 19:46
 */
@WebService(
        targetNamespace = Namespace,
        name = ISavePatientInfoWS.PORT_NAME)
public interface ISavePatientInfoWS {


    @WebMethod(operationName = "savePatientInfoToMIS")
    @WebResult(name = RESULT, targetNamespace = NAMESPACE, partName = "Body")
    int savePatientInfo(
            @WebParam(name = "Patient", targetNamespace = "http://korus.ru/tmis/ws/sda")
            final Patient patient) throws CoreException;


    static final String RESULT = "result";
    static final String SERVICE_NAME = "SAVE_PAT_MIS";
    static final String PORT_NAME = "SAVE_PAT_MIS_PORT_NAME";
}
