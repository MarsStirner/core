package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.RbPolicyType;
import ru.korus.tmis.core.exception.CoreException;

public interface DbRbPolicyTypeBeanLocal {
    Iterable<RbPolicyType> getAllRbPolicyTypes()
            throws CoreException;

    RbPolicyType getRbPolicyTypeById(int id)
            throws CoreException;

    long getCountOfRbPolicyTypeWithFilter(Object filter)
            throws CoreException;

    java.util.LinkedList<Object> getAllRbPolicyTypeWithFilter(int page, int limit, String sortingField, String sortingMethod, Object filter)
            throws CoreException;

}
