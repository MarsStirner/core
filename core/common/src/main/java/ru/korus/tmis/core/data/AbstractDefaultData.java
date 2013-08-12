package ru.korus.tmis.core.data;

/**
 * Абстрактный класс для json-контейнеров
 * @author idmitriev Systema-Soft
 */
public abstract class AbstractDefaultData implements DefaultData {

    @Override
    public abstract String dataToString ();

    @Override
    public DefaultData unwrap() {
        return this;
    }
}
