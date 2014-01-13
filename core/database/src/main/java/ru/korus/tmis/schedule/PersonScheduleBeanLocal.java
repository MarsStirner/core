package ru.korus.tmis.schedule;

import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.entity.model.Patient;
import ru.korus.tmis.core.entity.model.RbReasonOfAbsence;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;
import java.util.Date;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        23.12.13, 12:41 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Local
public interface PersonScheduleBeanLocal {

    PersonScheduleBean.PersonSchedule newInstanceOfPersonSchedule(final Action ambulatoryAction);

    /**
     * Проверка на наличие у врача "Причины отсутствия в указанную дату"
     *
     * @return true - У врача есть причина отсутствия
     */
    RbReasonOfAbsence getReasonOfAbsence(final PersonScheduleBean.PersonSchedule personSchedule);

    /**
     * Создание списка талончиков к врачу
     * Выборка свойств расписания из свойств действия
     * и формирование талончиков из двух списков (times и queue)
     */
    void formTickets(final PersonScheduleBean.PersonSchedule personSchedule) throws CoreException;

    /**
     * Выбирает и применяет ограничения по времени
     * (QuotingByTime врача)
     * на возвращаемый набор талончиков
     * (result.tickets)
     */
    void takeConstraintsOnTickets(final PersonScheduleBean.PersonSchedule personSchedule, final TypeOfQuota quotingType);

    /**
     * Возвращает первый свободный и доступный талончик врача поле заданного вермени
     *
     * @param checkDateTime время после которого ищется талончик
     * @return null если не найдено
     */
    Ticket getFirstFreeTicketAfterDateTime(final PersonScheduleBean.PersonSchedule personSchedule, final long checkDateTime);

    boolean checkQuotingBySpeciality(final PersonScheduleBean.PersonSchedule personSchedule, final String hospitalUidFrom);

    Ticket getTicketByQueueIndex(final PersonScheduleBean.PersonSchedule personSchedule, final int queueIndex);

    EnqueuePatientResult enqueuePatientToTime(final PersonScheduleBean.PersonSchedule personSchedule,
                                              final Date paramsDateTime,
                                              final Patient patient,
                                              final QueueActionParam queueActionParam);

    /**
     * Отмена записи на прием к врачу
     * @param queueAction запись, которую необходимо отменить
     */
    boolean dequeuePatient(final Action queueAction);

}
