package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.EventPerson;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;

/**
 * Created with IntelliJ IDEA.
 * User: mmakankov
 * Date: 25.12.12
 * Time: 16:15
 * To change this template use File | Settings | File Templates.
 */

@Local
public interface DbEventPersonBeanLocal {
    /**
     * Запрос на тип EventPerson по идентификатору.
     * @param id идентификатор записи.
     * @return
     * @throws CoreException
     */
    EventPerson getEventPersonById(int id) throws CoreException;
}
