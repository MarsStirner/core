package ru.korus.tmis.core.filter;

import org.codehaus.jackson.annotate.JsonSubTypes;
import org.codehaus.jackson.annotate.JsonTypeInfo;
import ru.korus.tmis.core.data.*;

/**
 * Абстрактный класс для фильтров
 * @author idmitriev Systema-Soft
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = DefaultListDataFilter.class, name = "DefaultListDataFilter"),
        @JsonSubTypes.Type(value = DictionaryListRequestDataFilter.class, name = "DictionaryListRequestDataFilter"),
        @JsonSubTypes.Type(value = MKBListRequestDataFilter.class, name = "MKBListRequestDataFilter"),
        @JsonSubTypes.Type(value = ThesaurusListRequestDataFilter.class, name = "ThesaurusListRequestDataFilter"),
        @JsonSubTypes.Type(value = QuotaTypesListRequestDataFilter.class, name = "QuotaTypesListRequestDataFilter"),
        @JsonSubTypes.Type(value = EventTypesListRequestDataFilter.class, name = "EventTypesListRequestDataFilter"),
        @JsonSubTypes.Type(value = ActionTypesListRequestDataFilter.class, name = "ActionTypesListRequestDataFilter")
})
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
