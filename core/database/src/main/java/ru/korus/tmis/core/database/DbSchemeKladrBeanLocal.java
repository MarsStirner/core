package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.kladr.Kladr;
import ru.korus.tmis.core.entity.model.kladr.Street;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.core.filter.ListDataFilter;

import javax.ejb.Local;

@Local
public interface DbSchemeKladrBeanLocal {

    long getCountOfKladrRecordsWithFilter(Object filter)
            throws CoreException;

    java.util.List<Object> getAllKladrRecordsWithFilter(int page, int limit, String sorting, ListDataFilter filter)
            throws CoreException;

    Kladr getKladrByCode(String code) throws CoreException;

    Street getStreetByCode(String code) throws CoreException;
}
