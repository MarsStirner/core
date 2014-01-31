package ru.korus.tmis.core.database.common;

import ru.korus.tmis.core.entity.model.RbCounter;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;

@Local
public interface DbRbCounterBeanLocal {
    Iterable<RbCounter> getAllRbCounters()
            throws CoreException;

    RbCounter getRbCounterById(int id)
            throws CoreException;

    RbCounter setRbCounterValue(RbCounter rbCounter, int value)
            throws CoreException;
}

