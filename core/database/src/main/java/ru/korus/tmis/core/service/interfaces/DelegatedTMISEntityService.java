package ru.korus.tmis.core.service.interfaces;

/**
 * Интерфейс для сервиса (т.е. DAO) с
 * поддержкой делегирования более общему объекту
 *
 * @author anosov
 * Date: 29.08.13 0:02
 */
public interface DelegatedTMISEntityService<T, S extends TMISEntityService> extends TMISEntityService<T> {

    S delegate();
}
