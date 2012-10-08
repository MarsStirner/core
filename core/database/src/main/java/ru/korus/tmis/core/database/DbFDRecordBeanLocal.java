package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.fd.FDRecord;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;

@Local
public interface DbFDRecordBeanLocal {

    FDRecord getFDRecordById(int recordId) throws CoreException;

    FDRecord getFDRecordByIdWithOutDetach(int recordId) throws CoreException;

    Object getIdValueFDRecordByEventTypeId(int flatDirectoryId, int eventTypeId) throws CoreException;
}
