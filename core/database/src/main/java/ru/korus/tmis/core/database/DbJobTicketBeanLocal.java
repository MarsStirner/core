package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.entity.model.JobTicket;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;
import java.util.Date;
import java.util.Map;

/**
 * Методы для работы с JobTicket
 * Author: idmitriev Systema-Soft
 * Date: 2/13/13 2:30 PM
 * Since: 1.0.0.64
 */
@Local
public interface DbJobTicketBeanLocal {

    Map<Action,JobTicket> getDirectionsWithJobTicketsBetweenDate(Date beginDate, Date endDate) throws CoreException;
}
