package ru.korus.tmis.core.filter;

import ru.korus.tmis.core.data.QueryDataStructure;
import ru.korus.tmis.core.exception.CoreException;

/**
 * Единый интерфейс для фильтров
 * @author idmitriev Systema-Soft
 */
public interface ListDataFilter {

    /**
     * Строка в запрос для сортировки
     * @param sortingField Поле для сортировки
     * @param sortingMethod Метод сортировки
     * @return Строка сортировки
     * @throws CoreException
     */
    String toSortingString (String sortingField, String sortingMethod) throws CoreException;

    /**
     * Структура с данными о фильтрах
     * @return QueryDataStructure
     * @throws CoreException
     */
    QueryDataStructure toQueryStructure() throws CoreException;

    ListDataFilter unwrap();
}
