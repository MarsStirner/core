package ru.korus.tmis.core.database;

import ru.korus.tmis.core.data.ClientContactContainer;
import ru.korus.tmis.core.entity.model.ClientRelation;
import ru.korus.tmis.core.entity.model.Patient;
import ru.korus.tmis.core.entity.model.Staff;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;

@Local
public interface DbClientRelationBeanLocal {
    Iterable<ClientRelation> getAllClientRelations(int patientId)
            throws CoreException;

    ClientRelation getClientRelationById(int id)
            throws CoreException;

    ClientRelation insertOrUpdateClientRelation(
            int id,
            int rbRelationTypeId,
            String firstName,
            String lastName,
            String middleName,
            Iterable<ClientContactContainer> contacts,
            Patient patient,
            Staff sessionUser)
            throws CoreException;

    void deleteClientRelation(int id,
                              Staff sessionUser)
            throws CoreException;

}
