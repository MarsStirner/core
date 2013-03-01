package ru.korus.tmis.core.filter;

import ru.korus.tmis.core.data.QueryDataStructure;

/**
 * Абстрактный класс для фильтров
 * @author idmitriev Systema-Soft
 */
public abstract class AbstractListDataFilter implements ListDataFilter {

    @Override
    public abstract String toSortingString (String sortingField, String sortingMethod);

    @Override
    public abstract QueryDataStructure toQueryStructure();

    @Override
    public ListDataFilter unwrap() {
        return this;
    }
}
