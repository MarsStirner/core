package ru.rorus.tmis.schedule;

import ru.korus.tmis.core.entity.model.*;
import ru.rorus.tmis.schedule.Ticket;

import javax.ejb.Local;
import java.util.Date;
import java.util.List;

/**
 * Author: Upatov Egor <br>
 * Date: 18.11.13, 16:42 <br>
 * Company: Korus Consulting IT <br>
 * Description: Бин для работы с записями на прием к врачу<br>
 */
@Local
public interface PatientQueueBeanLocal {
    /**
     * Получение списка занятых пациентом талончиков
     * @param patient  Пациент
     * @param beginDate начало интервала за который ищем талончики
     * @param endDate  конец интервала за который ищем талочики
     * @return список талончиков
     */
    List<Ticket> getPatientTickets(Patient patient, Date beginDate, Date endDate);

    /**
     * Проверка нету ли у пациента в указанном диапозоне времен записей на прием к врачу
     *
     * @param patient  пациент, для которого производиться проверка
     * @return true - пациент имеет запись на прием к врачу в указанном интервале,
     * false - не имеет записей на прием в указанном интервале
     */
    boolean checkPatientQueueByDateTime(final Patient patient, final Date date, final Date begTime, final Date endTime);

    /**
     * Получение Типа Обращения (Запись на прием к врачу)
     * @return Тип обращения, соответсвующий записи на прием к врачу \ NULL если не удалось найти
     */
    EventType getQueueEventType();

    /**
     * Получение Типа Действия (Запись на прием к врачу)
     * @return Тип действия, соответсвующий записи на прием к врачу  \ NULL если не удалось найти
     */
    ActionType getQueueActionType();


    ActionPropertyType getQueueActionPropertyType();
}
