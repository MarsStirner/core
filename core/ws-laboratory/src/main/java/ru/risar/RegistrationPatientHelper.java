package ru.risar;

import ru.korus.tmis.core.database.DbClientPolicyBeanLocal;
import ru.korus.tmis.core.database.DbPatientBeanLocal;
import ru.korus.tmis.core.entity.model.ClientPolicy;
import ru.korus.tmis.core.entity.model.RbPolicyType;
import ru.korus.tmis.core.exception.CoreException;
import ru.risar.data.Patient;
import ru.risar.data.PatientNumber;

import java.util.Date;

/**
 * @author anosov
 *         Date: 03.08.13 15:54
 */
public class RegistrationPatientHelper {
    public static ru.korus.tmis.core.entity.model.Patient getPatientFromDb(DbPatientBeanLocal dbPatientBean,
                                                                           Patient patient,
                                                                           Integer patientDbId) throws CoreException {
        return dbPatientBean.insertOrUpdatePatient(
                patientDbId,
                patient.getName().getGivenName(),
                patient.getName().getMiddleName(),
                patient.getName().getFamilyName(),
                patient.getBirthTime(),
                "", patient.getGender().codeName().toLowerCase(),
                "", "", null, null,
                -1, "", "",
                null, -1
        );
    }


    public static ClientPolicy getClientPolicy(DbClientPolicyBeanLocal dbClientPolicyBean,
                                               ru.korus.tmis.core.entity.model.Patient patientDb,
                                               PatientNumber patientNumber,
                                               RbPolicyType typeOMS,
                                               int policyId) throws CoreException {
        return dbClientPolicyBean.insertOrUpdateClientPolicy(
                policyId, typeOMS.getId(), -1,
                patientNumber.getNumber(),
                patientNumber.getPolicySerial(),
                new Date(), new Date(),
                "", "", patientDb, null);
    }

}
