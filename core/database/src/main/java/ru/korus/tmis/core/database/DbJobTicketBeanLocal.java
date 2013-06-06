package ru.korus.tmis.core.database;

import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.data.TakingOfBiomaterialRequesDataFilter;
import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.entity.model.ActionTypeTissueType;
import ru.korus.tmis.core.entity.model.Job;
import ru.korus.tmis.core.entity.model.JobTicket;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Методы для работы с JobTicket
 * @author idmitriev Systema-Soft
 * @since 1.0.0.64
 */
@Local
public interface DbJobTicketBeanLocal {

    JobTicket getJobTicketById(int id) throws CoreException;

    Object getDirectionsWithJobTicketsBetweenDate(String sortQuery, TakingOfBiomaterialRequesDataFilter filter) throws CoreException;

    boolean modifyJobTicketStatus(int id, int status, AuthData auth) throws CoreException;

    /**
     * Метод создания или редактирование тикета
     * @param id Идентификатор тикета.
     * @param action Действие, для которого будем создавать тикет.
     * @param job Работа, для которой создаем тикет.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws CoreException
     * @see CoreException
     */
    JobTicket insertOrUpdateJobTicket(int id, Action action, Job job) throws CoreException;

    /**
     * Поиск работы для действия
     * @param action Действие Action, для которого будет производиться поиск работы Job
     * @return Работа как Job
     * @throws CoreException
     * @see Action
     * @see Job
     * @see CoreException
     */
    Object getJobTicketAndTakenTissueForAction(int eventId, int atId, Date date) throws CoreException;

    List<Action> getActionsForJobTicket(int jobTicketId) throws CoreException;
}
