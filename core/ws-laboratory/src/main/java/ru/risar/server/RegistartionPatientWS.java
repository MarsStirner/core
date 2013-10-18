package ru.risar.server;

import ru.korus.tmis.core.exception.CoreException;
import ru.risar.data.Container;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import static ru.korus.tmis.util.CompileTimeConfigManager.Laboratory.Namespace;
import static ru.korus.tmis.laboratory.bak.ws.server.model.hl7.HL7Specification.NAMESPACE;

/**
 * @author anosov
 *         Date: 27.07.13 19:46
 */
@WebService(
        targetNamespace = Namespace,
        name = RegistartionPatientWS.SERVICE_NAME)
public interface RegistartionPatientWS {


    @WebMethod(operationName = "registrationPatient")
    @WebResult(name = RESULT, targetNamespace = NAMESPACE, partName = "Body")
    int registrationPatient(
            @WebParam(name = "container", targetNamespace = "http://korus.ru/tmis/ws/sda")
            final Container container) throws CoreException;


    static final String RESULT = "result";
    static final String SERVICE_NAME = "registartionPatient";
    static final String PORT_NAME = "registartionPatientPortType";
}
