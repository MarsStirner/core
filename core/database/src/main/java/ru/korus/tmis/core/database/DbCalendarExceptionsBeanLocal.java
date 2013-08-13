package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.CalendarExceptions;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: mmakankov
 * Date: 13.08.13
 * Time: 13:34
 * To change this template use File | Settings | File Templates.
 */

@Local
public interface DbCalendarExceptionsBeanLocal {

    /**
     * Получение праздничного(выходного) дня по идентификатору
     * @param id Идентификатор
     * @return Список диагностик как CalendarExceptions
     * @throws ru.korus.tmis.core.exception.CoreException
     */
    CalendarExceptions getCalendarExceptionById(int id) throws CoreException;

    CalendarExceptions getHolideyByDate (Date id) throws CoreException;

    CalendarExceptions getPerenosByDate (Date id) throws CoreException;

}
