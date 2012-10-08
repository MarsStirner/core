package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.RbContactType;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;

@Local
public interface DbRbContactTypeBeanLocal {
    Iterable<RbContactType> getAllRbContactTypes()
            throws CoreException;

    RbContactType getRbContactTypeById(int id)
            throws CoreException;
}
