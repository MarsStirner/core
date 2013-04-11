package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.entity.model.Job;
import ru.korus.tmis.core.entity.model.JobTicket;
import ru.korus.tmis.core.entity.model.OrgStructure;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;

/**
 * Методы для работы с Job
 * Author: mmakankov Systema-Soft
 * Date: 2/13/13 2:30 PM
 * Since: 1.0.0.69
 */
@Local
public interface DbJobBeanLocal {
    /**
     * Запрос на работу Job по идентификатору
     * @param id идентификатор работы Job, по которому будет производиться поиск
     * @return Работа как Job
     * @throws CoreException
     * @see Job
     * @see CoreException
     */
    Job getJobById(int id) throws CoreException;;

    /**
     * Создание или редактирование работы Job
     * @param id идентификатор работы Job, по которому будет производиться поиск
     * @param action Действие Action, для которого будет создаваться работа Job
     * @return Работа как Job
     * @throws CoreException
     * @see Action
     * @see Job
     * @see CoreException
     */
    Job insertOrUpdateJob(int id, Action action, OrgStructure department) throws CoreException;
}
