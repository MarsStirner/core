package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.kladr.Kladr;
import ru.korus.tmis.core.entity.model.kladr.Street;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;

@Local
public interface DbSchemeKladrBeanLocal {

    long getCountOfKladrRecordsWithFilter(Object filter)
            throws CoreException;

    java.util.LinkedList<Object> getAllKladrRecordsWithFilter(int page, int limit, String sortingField, String sortingMethod, Object filter)
            throws CoreException;

    Kladr getKladrByCode(String code) throws CoreException;

    Street getStreetByCode(String code) throws CoreException;
}
