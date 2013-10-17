package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.RbPolicyType;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.core.filter.ListDataFilter;

public interface DbRbPolicyTypeBeanLocal {
    Iterable<RbPolicyType> getAllRbPolicyTypes()
            throws CoreException;

    RbPolicyType getRbPolicyTypeById(int id)
            throws CoreException;

    long getCountOfRbPolicyTypeWithFilter(Object filter)
            throws CoreException;

    java.util.LinkedList<Object> getAllRbPolicyTypeWithFilter(int page, int limit, String sorting, ListDataFilter filter)
            throws CoreException;

    /**
     * Поиск типа полиса по коду типа
     * @param policyTypeCode код типа полиса
     * @return тип полиса \ null (если не найдено)
     */
    RbPolicyType findByCode(final String policyTypeCode);
}
