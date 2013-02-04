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

    Action getPersonActionsByDateAndType(int personId, Date date, String actionType)
            throws CoreException;

}
