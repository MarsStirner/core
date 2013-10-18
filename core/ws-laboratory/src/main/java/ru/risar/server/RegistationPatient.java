package ru.risar.server;

import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.util.CompileTimeConfigManager;
import ru.risar.bean.RegistrationPatient;
import ru.risar.data.Container;
import ru.risar.data.Patient;

import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import static ru.korus.tmis.laboratory.bak.ws.server.model.hl7.HL7Specification.NAMESPACE;

/**
 * @author anosov
 *         Date: 27.07.13 19:17
 */
@WebService(
        endpointInterface = "ru.risar.server.RegistartionPatientWS",
        targetNamespace = CompileTimeConfigManager.Laboratory.Namespace,
        serviceName = RegistartionPatientWS.SERVICE_NAME,
        portName = RegistartionPatientWS.PORT_NAME,
        name = RegistartionPatientWS.PORT_NAME)
public class RegistationPatient implements RegistartionPatientWS {

    @EJB
    RegistrationPatient registrationPatientBean;

    @Override
    @WebMethod(operationName = "registrationPatient")
    @WebResult(name = RESULT, targetNamespace = NAMESPACE, partName = "Body")
    public int registrationPatient(
            @WebParam(name = "container", targetNamespace = "http://korus.ru/tmis/ws/sda")
            final Container container) throws CoreException {

        final Patient patient = container.getPatient();
        if (patient != null) {
            final boolean isSaved = registrationPatientBean.register(patient);
            if (isSaved) {
                return 1;
            }
            return 0;
        } else {
            throw new CoreException("Tag patient not found");
        }
    }
}
