package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.RbSocStatusType;
import ru.korus.tmis.core.exception.CoreException;

@javax.ejb.Local
public interface DbRbSocTypeBeanLocal {


    RbSocStatusType getSocStatusTypeById(int id);

    long getCountOfSocStatusTypesWithFilter(Object filter)
            throws CoreException;

    java.util.LinkedList<Object> getAllSocStatusTypesWithFilter(int page, int limit, String sortingField, String sortingMethod, Object filter)
            throws CoreException;

}
