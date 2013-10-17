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

    Iterable<RbDocumentType> findAllRbDocumentTypes()
            throws CoreException;

    RbDocumentType findByName(String name) throws CoreException;

    /**
     * Ищет тип документа по значению кода типа документа
     * @param code код типа документа
     * @return тип документа, которому соответствует код \ null в случае если нету типа с таким кодом.
     * Если найдено больше одного типа документа, то возвращает первый попавшийся (ORDER BY id DESC).
     */
    RbDocumentType findByCode(String code);
}