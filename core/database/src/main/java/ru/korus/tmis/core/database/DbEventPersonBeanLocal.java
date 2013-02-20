package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.Event;
import ru.korus.tmis.core.entity.model.EventPerson;
import ru.korus.tmis.core.entity.model.Staff;
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

    /**
     * Создание/редактирование ответственного за ивент.
     * @param id идентификатор записи.
     * @param event идентификатор записи.
     * @param sessionUser пользователь, создающий EventPerson.
     * @return
     * @throws CoreException
     */
    EventPerson insertOrUpdateEventPerson(int id, Event event, Staff sessionUser, boolean withFlash) throws CoreException;

    /**
     * Запрос на тип EventPerson по идентификатору ивента.
     * @param eventId идентификатор ивента.
     * @return
     * @throws CoreException
     */
    EventPerson getLastEventPersonForEventId(int eventId) throws CoreException;

    /**
     * Проверка, является ли пользователь ответственным за ивент
     * @param eventId идентификатор ивента.
     * @param user пользователь как Staff.
     * @return
     * @throws CoreException
     */
    void checkEventPerson (int eventId, Staff user) throws CoreException;
}
