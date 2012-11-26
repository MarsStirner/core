package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.RbQuotaStatus;
import ru.korus.tmis.core.exception.CoreException;

/**
 * Класс с методами для работы с таблицей s11r64.RbQuotaStatus
 * @author mmakankov
 * @since 1.0.0.48
 * @see DbRbQuotaStatusBean
 */
public interface DbRbQuotaStatusBeanLocal {

    //RbQuotaStatus getRbQuotaStatusById(int id)
    //        throws CoreException;

    long getCountOfRbQuotaStatusWithFilter(Object filter)
            throws CoreException;

    java.util.LinkedList<Object> getAllRbQuotaStatusWithFilter(int page, int limit, String sortingField, String sortingMethod, Object filter)
            throws CoreException;
}
