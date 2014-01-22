package ru.korus.tmis.core.database.common;

import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;
import java.util.Collection;

@Local
public interface DbManagerBeanLocal {

    <T> void persistAll(Collection<T> entities)
            throws CoreException;

    <T> void persist(T entity)
            throws CoreException;

    <T> T merge(T entity)
            throws CoreException;

    @java.lang.Deprecated
    <T> T persistOrMerge(T entity)
            throws CoreException;

    @java.lang.Deprecated
    <T> Collection<T> persistOrMergeAll(Collection<T> entities)
            throws CoreException;

    <T> Collection<T> mergeAll(Collection<T> entities)
            throws CoreException;

    <T> T detach(T entity)
            throws CoreException;

    <T> Collection<T> detachAll(Collection<T> entities)
            throws CoreException;

    <T> void removeAll(Collection<T> entities)
            throws CoreException;

    <T> void refresh(T entity) throws CoreException;

    <T> void rollbackTransaction() throws CoreException;
}
