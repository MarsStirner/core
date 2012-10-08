package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.ClientSocStatus;
import ru.korus.tmis.core.entity.model.Patient;
import ru.korus.tmis.core.entity.model.Staff;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;
import java.util.Date;


@Local
public interface DbClientSocStatusBeanLocal {


    ClientSocStatus getClientSocStatusById(int id) throws CoreException;

    ClientSocStatus insertOrUpdateClientSocStatus(
            int id,
            int socStatusId,
            int socStatusTypeId,
            int documentId,
            Date begDate,
            Date endDate,
            Patient patient,
            int benefitCategoryId,
            String note,
            Staff sessUser) throws CoreException;

    void deleteClientSocStatus(int id, Staff sessionUser) throws CoreException;
}
