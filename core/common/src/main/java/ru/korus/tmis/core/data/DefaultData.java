package ru.korus.tmis.core.data;

import ru.korus.tmis.core.exception.CoreException;

/**
 * Единый интерфейс для json-контейнеров
 * @author idmitriev Systema-Soft
 */
public interface DefaultData {

    String dataToString () throws CoreException;

    DefaultData unwrap();
}
