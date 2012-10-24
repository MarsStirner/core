package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.ClientWork;
import ru.korus.tmis.core.entity.model.Patient;
import ru.korus.tmis.core.entity.model.Staff;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;
import java.util.Date;

@Local
public interface DbClientWorkBeanLocal {


    ClientWork getClientWorkById(int id) throws CoreException;

    ClientWork insertOrUpdateClientWork(
            int id,
            Patient patient,
            String freeInput,
            String post,
            int rankId,
            int armId,
            Staff sessUser) throws CoreException;

    void deleteClientWork(int id, Staff sessionUser) throws CoreException;
}
