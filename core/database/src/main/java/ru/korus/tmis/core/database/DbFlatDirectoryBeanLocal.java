package ru.korus.tmis.core.database;

import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.data.FlatDirectoryRequestData;
import ru.korus.tmis.core.entity.model.fd.FDFieldValue;
import ru.korus.tmis.core.entity.model.fd.FDRecord;
import ru.korus.tmis.core.entity.model.fd.FlatDirectory;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;

@Local
public interface DbFlatDirectoryBeanLocal {

    java.util.List<FlatDirectory> getFlatDirectories(int page, int limit, String sortField, String sortMethod, Object filter, AuthData userData)
            throws CoreException;

    java.util.LinkedHashMap<FlatDirectory, java.util.LinkedHashMap<FDRecord, java.util.LinkedList<FDFieldValue>>>
    getFlatDirectoriesWithFilterRecords(int page, int limit, java.util.LinkedHashMap<Integer, Integer> sorting, Object filter, FlatDirectoryRequestData request, AuthData userData)
            throws CoreException;
}
