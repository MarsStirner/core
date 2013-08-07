package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.ClientPolicy;
import ru.korus.tmis.core.entity.model.Patient;
import ru.korus.tmis.core.entity.model.Staff;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;
import java.util.Date;

@Local
public interface DbClientPolicyBeanLocal {

    Iterable<ClientPolicy> getAllPolicies(int patientId)
            throws CoreException;

    ClientPolicy getClientPolicyById(int id)
            throws CoreException;

    ClientPolicy insertOrUpdateClientPolicy(int id,
                                            int rbPolicyTypeId,
                                            int insurerId,
                                            String number,
                                            String serial,
                                            Date startDate,
                                            Date endDate,
                                            String name,
                                            String note,
                                            Patient patient,
                                            Staff sessionUser)
            throws CoreException;

    void deleteClientPolicy(int id, Staff sessionUser) throws CoreException;

    Boolean checkPolicyNumber(String number, String serial, int typeId) throws CoreException;

    ClientPolicy findBySerialAndNumberAndType(String serial, String number, int typeId);
}
