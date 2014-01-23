package ru.korus.tmis.core.database.common;

import ru.korus.tmis.core.data.DictionaryListData;
import ru.korus.tmis.core.data.ListDataRequest;
import ru.korus.tmis.core.entity.model.RbBloodType;
import ru.korus.tmis.core.entity.model.RbContactType;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.core.filter.ListDataFilter;

import javax.ejb.Local;

@Local
public interface DbRbBloodTypeBeanLocal {
    Iterable<RbBloodType> getAllRbBloodTypes()
            throws CoreException;

    RbBloodType getRbBloodTypeById(int id)
            throws CoreException;

    java.util.LinkedList<Object> getAllBloodTypesData()
            throws CoreException;

    long getCountOfBloodTypesWithFilter(Object filter)
            throws CoreException;

    java.util.LinkedList<Object> getAllBloodTypesWithFilter(int page, int limit, String sorting, ListDataFilter filter)
            throws CoreException;
}
