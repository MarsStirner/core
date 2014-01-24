package ru.korus.tmis.core.service.interfaces;

/**
 * Интерйес DAO для работы с сущностями проекта
 *
 * @author anosov
 *         Date: 28.08.13 23:34
 */
public interface TMISEntityService<T> {

    public T save(T object);

    public T get(Long id);

    public void delete(T object);

    public void delete(T object, boolean flush);

    /**
     * Класс для описания запросов
     */
    public static abstract class Query{}
}
