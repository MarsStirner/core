package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.Staff;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;

/**
 * Интерфейс для работы с персоналом.
 */
@Local
public interface DbStaffBeanLocal {

    /**
     * Получить служащего по имени пользователя.
     *
     * @param login имя пользователя
     *
     * @return объект персонала
     */
    Staff getStaffByLogin(String login)
            throws CoreException;
}
