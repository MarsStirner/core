package ru.korus.tmis.core.indicators;

import java.util.Date;

/**
 * Класс для представления значений индикаторов.
 */
public class IndicatorValue<T> {

    private final int id;
    private final String name;
    private final T value;
    private final Date date;

    public IndicatorValue(final int id,
                          final String name,
                          final T value,
                          final Date date) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public T getValue() {
        return value;
    }

    public Date getDate() {
        return date;
    }
}
