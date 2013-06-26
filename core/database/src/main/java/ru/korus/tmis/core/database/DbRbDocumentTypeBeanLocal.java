package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.RbDocumentType;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.core.filter.ListDataFilter;

import javax.ejb.Local;

@Local
public interface DbRbDocumentTypeBeanLocal {

    Iterable<RbDocumentType> getAllRbDocumentTypes()
            throws CoreException;

    RbDocumentType getRbDocumentTypeById(int id)
            throws CoreException;

    java.util.LinkedList<Object> getAllRbDocumentTypesData()
            throws CoreException;

    long getCountOfDocumentTypesWithFilter(Object filter)
            throws CoreException;

    java.util.LinkedList<Object> getAllDocumentTypesWithFilter(int page, int limit, String sorting, ListDataFilter filter)
            throws CoreException;
}