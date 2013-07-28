package ru.sda.server;

import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.util.CompileTimeConfigManager;
import ru.sda.bean.SavePatientInfo;
import ru.sda.data.Patient;

import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import static ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.HL7Specification.NAMESPACE;

/**
 * @author anosov
 *         Date: 27.07.13 19:17
 */
@WebService(
        endpointInterface = "ru.sda.server.ISavePatientInfoWS",
        targetNamespace = CompileTimeConfigManager.Laboratory.Namespace,
        serviceName = ISavePatientInfoWS.SERVICE_NAME,
        portName = ISavePatientInfoWS.PORT_NAME,
        name = ISavePatientInfoWS.PORT_NAME)
public class SavePatientInfoWS implements ISavePatientInfoWS {

    @EJB
    SavePatientInfo savePatientInfoBean;

    @Override
    @WebMethod(operationName = "savePatientInfoToMIS")
    @WebResult(name = RESULT, targetNamespace = NAMESPACE, partName = "Body")
    public int savePatientInfo(
            @WebParam(name = "Patient", targetNamespace = "http://korus.ru/tmis/ws/sda")
            final Patient patient) throws CoreException {

        final boolean isSaved = savePatientInfoBean.save(patient);
        if (isSaved) {
            return 1;
        }
        return 0;
    }
}
