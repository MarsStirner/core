package ru.korus.tmis.core.database;

import ru.korus.tmis.core.exception.CoreException;

import java.util.Collection;
import javax.ejb.Local;

@Local
public interface DbManagerBeanLocal {

    <T> void persistAll(Collection<T> entities)
            throws CoreException;

    <T> Collection<T> mergeAll(Collection<T> entities)
            throws CoreException;

    <T> Collection<T> detachAll(Collection<T> entities)
            throws CoreException;

    <T> void removeAll(Collection<T> entities)
            throws CoreException;
}
