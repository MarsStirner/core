package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.APValueTime;
import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.entity.model.ActionProperty;
import ru.korus.tmis.core.entity.model.Staff;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.core.filter.ListDataFilter;
import scala.Function1;

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

    List<Staff> getAllPersonsByRequest(int limit,
                                       int page,
                                       String sorting,
                                       ListDataFilter filter,
                                       Function1<Long, Boolean> setRecCount)
            throws CoreException;

    java.util.HashMap<Staff, java.util.LinkedList<APValueTime>> getEmptyPersonsByRequest(int limit, int page, String sorting, ListDataFilter filter, int citoActionsCount)
            throws CoreException;

    ActionProperty getActionPropertyForPersonByRequest(ListDataFilter filter) throws CoreException;

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

    Staff getDoctorByClientAmbulatoryAction(Action queueAction);

    /**
     * Получение расписаний врача
     * @param personId идентификатор врача
     * @param begDate дата начала интервала за который ищем приемы врача
     * @param endDate дата окончания интервала за который ищем приемы врача
     * @return список жействий с приемами врача
     */
    List<Action> getPersonShedule(final int personId, final Date begDate, final Date endDate);

    /**
     * Пользоваиель ядро
     * @return
     */
    Staff getCoreUser();
}
