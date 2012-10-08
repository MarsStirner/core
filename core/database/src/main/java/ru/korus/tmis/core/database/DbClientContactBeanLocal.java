package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.ClientContact;
import ru.korus.tmis.core.entity.model.Patient;
import ru.korus.tmis.core.entity.model.Staff;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;

@Local
public interface DbClientContactBeanLocal {
    Iterable<ClientContact> getAllContacts(int patientId)
            throws CoreException;

    ClientContact getClientContactById(int id)
            throws CoreException;

    ClientContact insertOrUpdateClientContact(int id,
                                              int rbContactTypeId,
                                              String contact,
                                              String notes,
                                              Patient patient,
                                              Staff sessionUser)
            throws CoreException;

    void deleteClientContact(int id, Staff sessionUser) throws CoreException;
}
