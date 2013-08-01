package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.RbContactType;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.core.filter.ListDataFilter;

import javax.ejb.Local;

@Local
public interface DbRbContactTypeBeanLocal {
    //Iterable<RbContactType> getAllRbContactTypes()
    //        throws CoreException;

    long getCountOfAllRbContactTypesWithFilter(Object filter)
            throws CoreException;

    java.util.LinkedList<Object> getAllRbContactTypesWithFilter(int page, int limit, String sorting, ListDataFilter filter)
            throws CoreException;

    RbContactType getRbContactTypeById(int id)
            throws CoreException;

    RbContactType findByName(String name) throws CoreException;
}
