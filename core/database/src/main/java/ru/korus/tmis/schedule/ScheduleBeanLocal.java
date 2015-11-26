package ru.korus.tmis.schedule;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import ru.korus.tmis.core.entity.model.*;

import javax.ejb.Local;
import java.util.Date;
import java.util.List;

/**
 * Author: Upatov Egor <br>
 * Date: 04.06.2014, 16:16 <br>
 * Company: Korus Consulting IT <br>
 * Description: <br>
 */
@Local
public interface ScheduleBeanLocal {
    /**
     * Ищет и возвращает из БД расписания врача на указанную дату
     * @param date  Дата, на момент которой нужно найти расписания
     * @param person  Врач, расписания которого требуется найти
     * @return Список расписаний заданного врача на указанный день (Может быть несколько\ пустой список если нету ни одного расписания)
     */
    public List<Schedule> getScheduleOnDateAndPerson(final Date date, final Staff person);

    /**
     * Ищет и возвращает все расписания заданного врача за интервал времени
     * @param startDate Дата начала поиска расписаний
     * @param endDate Дата конца поиска расписаний
     * @param person  Врач, расписания которого требуется найти
     * @return Список расписаний заданного врача на указанный интервал времени (пустой список, если нету ни одного расписания)
     */
    public List<Schedule> getScheduleOnDateIntervalAndPerson(final Date startDate, final Date endDate, final Staff person);

    /**
     * Возвращает список талонов, заднного расписания
     * @param schedule  расписание врача, на которое надо получить список талонов
     * @return  список талонов (пустой список, если у расписания нету талонов)
     */
    public List<ScheduleTicket> getScheduleTickets(final Schedule schedule);

    /**
     * Возвращает список записей пациентов на заданный список талонов
     * @param ticketList Список талонов на запись
     * @return   список записей \ пустой список если не найдено ни одной записи. Запись можно сопоставить с талоном по полю ScheduleClientTicket.ticket
     */
    public List<ScheduleClientTicket> getScheduleClientTickets(final List<ScheduleTicket> ticketList);

    /**
     * Возвращает список свободных талонов на прием к заданному врачу, время начала которых попадает в заданный интервал
     * @param person  Врач, у которого мы ищем свободные талоны
     * @param startDate  Начало интервала, в который должна попадать датавремя начала талона
     * @param endDate    Конец интервала, в который должна попадать датавремя начала талона
     * @return  Список свободных талонов заданного врача в указанном интервале \ Пустой список, если не было найдено ни одного талона
     */
    public List<ScheduleTicket> getFreeTicketsToPersonWithDateInterval(final Staff person, final Date startDate, final Date endDate);

    /**
     * Записать пациента на талон
     * @param ticket  Талон, на который надо записать пациента
     * @param patient  Пациент, которого необходимо записать
     * @param note  Примечание к записи (NULLable)
     * @param organisation  Организация, из которой производится запись (ЛПУ из которой записывают при записи между ЛПУ) (NULLable)
     * @return  Созданный талон пациента на прием
     */
    public ScheduleClientTicket enqueuePatientToScheduleTicket(final ScheduleTicket ticket, final Patient patient, final String note, final Organisation organisation);

    /**
     * Получение записи пациента на прием по идентификатору записи
     * @param clientTicketId идентификатор талона пациента
     * @return    талон пациента на прием к врачу
     */
    public ScheduleClientTicket getScheduleClientTicketById(final int clientTicketId);

    /**
     * Отменить запись пациента к врачу (deleted = 1)
     * @param ticket талон пациента, который нужно отменить
     * @return  успешность отмены (true - отменена \ false - отмена не удалась)
     */
    public boolean dequeueScheduleClientTicket(final ScheduleClientTicket ticket);

    /**
     * Получение всех талонов на приемы для заданного пациента (отмененные талоны не попадут в список)
     * @param patient  пациент, талоны которого надо получить
     * @return  список талонов пациента \ пустой список если ничего не найдено
     */
    public List<ScheduleClientTicket> getScheduleClientTicketsForPatient(final Patient patient);

    /**
     * Получение всех квотирований по времени на врача и дату
     * @param person  врач, для которого нужно найти квотирования
     * @param date Дата, на которую нужно найти квотирования
     * @return  список всех квотирований \ пустой список если ничего не найдено
     */
    public List<QuotingByTime> getQuotingByTimeToPersonAndDate(final Staff person, final Date date);

    /**
     * Получение всех квотирований по времени заданного типа на врача и дату
     *
     * @param person врач, для которого нужно найти квотирования
     * @param date   Дата, на которую нужно найти квотирования
     * @return список всех квотирований \ пустой список если ничего не найдено
     */
    public List<QuotingByTime> getQuotingByTimeToPersonAndDateAndType(final Staff person, final LocalDate date, final RbTimeQuotingType quotingType);

    /**
     * Получение всех типов квот по времени
     * @return  Список всех квот по времени
     */
    public List<RbTimeQuotingType> getQuotingTypeList();

    /**
     * Ищет и возвращает все расписания заданного врача за интервал времени
     * @param startDateTime Дата начала поиска расписаний   (время опускается)
     * @param endDateTime Дата конца поиска расписаний      (время опускается)
     * @param person  Врач, расписания которого требуется найти
     * @return Список расписаний заданного врача на указанный интервал времени (пустой список, если нету ни одного расписания)
     */
    public List<PersonSchedule> getPersonScheduleOnDateInterval(final Staff person, final LocalDateTime startDateTime, final LocalDateTime endDateTime);
}
