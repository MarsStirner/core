package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.Patient;
import ru.korus.tmis.core.entity.model.Staff;
import ru.korus.tmis.core.entity.model.fd.ClientFDProperty;
import ru.korus.tmis.core.entity.model.fd.ClientFlatDirectory;
import ru.korus.tmis.core.entity.model.fd.FDRecord;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;

@Local
public interface DbClientFlatDirectoryBeanLocal {

    ClientFlatDirectory insertOrUpdateClientFlatDirectory(int fdClientId,
                                                          int fdPropertyId,
                                                          int fdRecordId,
                                                          Patient patient,
                                                          Staff sessionUser)
            throws CoreException;

    ClientFlatDirectory getClientFlatDirectoryById(int fdClientId)
            throws CoreException;

    void deleteClientFlatDirectory(int fdClientId,
                                   Staff sessionUser)
            throws CoreException;

    ClientFDProperty getClientFDPropertyById(int fdPropertyId)
            throws CoreException;

    FDRecord getFDRecordById(int fdRecordId)
            throws CoreException;
}
