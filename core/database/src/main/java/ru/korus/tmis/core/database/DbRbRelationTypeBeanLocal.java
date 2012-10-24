package ru.korus.tmis.core.database;

import ru.korus.tmis.core.data.DictionaryListData;
import ru.korus.tmis.core.data.ListDataRequest;
import ru.korus.tmis.core.entity.model.RbRelationType;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;

@Local
public interface DbRbRelationTypeBeanLocal {
    Iterable<RbRelationType> getAllRbRelationTypes()
            throws CoreException;

    RbRelationType getRbRelationTypeById(int id)
            throws CoreException;

    java.util.LinkedList<Object> getAllRbRelationTypesData()
            throws CoreException;

    long getCountOfRelationsWithFilter(Object filter)
            throws CoreException;

    java.util.LinkedList<Object> getAllRelationsWithFilter(int page, int limit, String sortingField, String sortingMethod, Object filter)
            throws CoreException;
}
