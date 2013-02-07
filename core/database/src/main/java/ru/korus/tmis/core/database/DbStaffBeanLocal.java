package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.entity.model.Staff;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;
import java.util.Date;
import java.util.List;

/**
 * Интерфейс для работы с персоналом.
 */
@Local
public interface DbStaffBeanLocal {

    /**
     * Получить служащего по имени пользователя.
     *
     * @param login имя пользователя
     * @return объект персонала
     */
    Staff getStaffByLogin(String login)
            throws CoreException;

    Staff getStaffById(int id)
            throws CoreException;

    Staff getStaffByIdWithoutDetach(int id)
            throws CoreException;

    List<Staff> getAllPersons()
            throws CoreException;

    List<Staff> getAllPersonsByRequest(int limit, int page, String sortField, String sortMethod, Object filter)
            throws CoreException;

    List<Staff> getEmptyPersonsByRequest(int limit, int page, String sortField, String sortMethod, Object filter)
            throws CoreException;

    long getCountAllPersonsWithFilter(Object filter)
            throws CoreException;

    /**
     * Получение действия(Action) по заданному типу, времени и владельцу
     *
     * @param personId   Владелец действия
     * @param date       Дата на момент которой ищется действие
     * @param actionType Тип искомого действия
     * @return Найденое действие
     * @throws CoreException Если действие не найдено
     */
    Action getPersonActionsByDateAndType(int personId, Date date, String actionType)
            throws CoreException;

}
