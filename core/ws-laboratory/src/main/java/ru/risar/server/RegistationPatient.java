package ru.risar.server;

import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.util.CompileTimeConfigManager;
import ru.risar.bean.RegistrationPatient;
import ru.risar.data.Container;
import ru.risar.data.Patient;
import ru.risar.data.RegistrationPatientResponse;
import ru.risar.exception.RisarCoreException;

import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import static ru.korus.tmis.lis.data.model.hl7.HL7Specification.NAMESPACE;

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
    public RegistrationPatientResponse registrationPatient(
            @WebParam(name = "container", targetNamespace = "http://korus.ru/tmis/ws/sda")
            final Container container) throws CoreException {

        final Patient patient = container.getPatient();
        if (patient != null) {
            try {
                final boolean isSaved = registrationPatientBean.register(patient);
                if (isSaved) {
                    final RegistrationPatientResponse response = new RegistrationPatientResponse();
                    response.setCode("0");
                    response.setDescription("Данные переданы без ошибок");
                    return response;
                }
            } catch (RisarCoreException ex) {
                final RegistrationPatientResponse response = new RegistrationPatientResponse();
                response.setCode("1");
                response.setDescription(ex.description);
                return response;
            }
        } else {
            throw new CoreException("Tag patient not found");
        }
        final RegistrationPatientResponse response = new RegistrationPatientResponse();
        response.setCode("1");
        response.setDescription("Данные переданы с ошибоками");
        return response;
    }
}
