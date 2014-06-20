package ru.korus.tmis.schedule;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.entity.model.*;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;
import java.util.*;

/**
 * Author: Upatov Egor <br>
 * Date: 04.06.2014, 16:29 <br>
 * Company: Korus Consulting IT <br>
 * Description: <br>
 */
@Stateless
public class ScheduleBean implements ScheduleBeanLocal {

    private static final Logger LOGGER = LoggerFactory.getLogger("SCHEDULE");


    @PersistenceContext(unitName = "s11r64")
    EntityManager em;

    /**
     * Ищет и возвращает из БД расписания врача на указанную дату
     *
     * @param date   Дата, на момент которой нужно найти расписания. Можно передавать и дату со временем, в этом случае время будет отсечено (не по ссылке)
     * @param person Врач, расписания которого требуется найти
     * @return Список расписаний заданного врача на указанный день (Может быть несколько\ пустой список если нету ни одного расписания)
     */
    @Override
    public List<Schedule> getScheduleOnDateAndPerson(final Date date, final Staff person) {
        final Date onlyDatePart = new LocalDate(date).toDate();
        LOGGER.debug("OnlyDatePart = {}", onlyDatePart);
        return em.createNamedQuery("Schedule.findByDateAndPerson", Schedule.class)
                .setParameter("date", onlyDatePart, TemporalType.DATE)
                .setParameter("person", person)
                .getResultList();
    }

    /**
     * Ищет и возвращает все расписания заданного врача за интервал времени
     *
     * @param startDate Дата начала поиска расписаний (IFNULL-> NOW)
     * @param endDate   Дата конца поиска расписаний  (IFNULL-> startDate + 1 Month)
     * @param person    Врач, расписания которого требуется найти
     * @return Список расписаний заданного врача на указанный интервал времени (пустой список, если нету ни одного расписания)
     */
    @Override
    public List<Schedule> getScheduleOnDateIntervalAndPerson(final Date startDate, final Date endDate, final Staff person) {
        Date startDateOnlyDatePart;
        Date endDateOnlyDatePart;
        if (startDate == null) {
            startDateOnlyDatePart = new LocalDate().toDate();
        } else {
            startDateOnlyDatePart = new LocalDate(startDate).toDate();
        }
        if (endDate == null) {
            endDateOnlyDatePart = new LocalDate(startDateOnlyDatePart).plusMonths(1).toDate();
        } else {
            endDateOnlyDatePart = new LocalDate(endDate).toDate();
        }
        LOGGER.debug("OnlyDatePart [{}] TO [{}]", startDateOnlyDatePart, endDateOnlyDatePart);
        return em.createNamedQuery("Schedule.findByDateIntervalAndPerson", Schedule.class)
                .setParameter("startDate", startDateOnlyDatePart, TemporalType.DATE)
                .setParameter("endDate", endDateOnlyDatePart, TemporalType.DATE)
                .setParameter("person", person)
                .getResultList();
    }

    /**
     * Возвращает список талонов, заднного расписания
     *
     * @param schedule расписание врача, на которое надо получить список талонов
     * @return список талонов (пустой список, если у расписания нету талонов)
     */
    @Override
    public List<ScheduleTicket> getScheduleTickets(final Schedule schedule) {
        return em.createNamedQuery("ScheduleTicket.findBySchedule", ScheduleTicket.class)
                .setParameter("schedule", schedule)
                .getResultList();
    }

    /**
     * Возвращает список записей пациентов на заданный список талонов
     *
     * @param ticketList Список талонов на запись
     * @return список записей \ пустой список если не найдено ни одной записи. Запись можно сопоставить с талоном по полю ScheduleClientTicket.ticket
     */
    @Override
    public List<ScheduleClientTicket> getScheduleClientTickets(final List<ScheduleTicket> ticketList) {
        List<Integer> ticketIdList = new ArrayList<Integer>(ticketList.size());
        for (ScheduleTicket current : ticketList) {
            ticketIdList.add(current.getId());
        }
        return em.createNamedQuery("ScheduleClientTicket.findByScheduleTicketList", ScheduleClientTicket.class)
                .setParameter("ticketList", ticketIdList)
                .getResultList();
    }

    /**
     * Возвращает список свободных талонов на прием к заданному врачу, время начала которых попадает в заданный интервал
     *
     * @param person    Врач, у которого мы ищем свободные талоны
     * @param startDate Начало интервала, в который должна попадать датавремя начала талона
     * @param endDate   Конец интервала, в который должна попадать датавремя начала талона
     * @return Список свободных талонов заданного врача в указанном интервале \ Пустой список, если не было найдено ни одного талона
     */
    @Override
    public List<ScheduleTicket> getFreeTicketsToPersonWithDateInterval(Staff person, Date startDate, Date endDate) {
        return em.createNamedQuery("ScheduleTicket.findFreeByPersonAndInDateInterval", ScheduleTicket.class)
                .setParameter(1, person.getId())
                .setParameter(2, startDate, TemporalType.TIMESTAMP)
                .setParameter(3, endDate, TemporalType.TIMESTAMP)
                .getResultList();
    }

    @Override
    public ScheduleClientTicket enqueuePatientToScheduleTicket(final ScheduleTicket ticket, final Patient patient, final String note, final Organisation organisation) {
        if (ticket.isFree()) {
            final ScheduleClientTicket clientTicket = new ScheduleClientTicket();
            final Date creationDate = new Date();
            clientTicket.setClient(patient);
            clientTicket.setDeleted(false);
            if (note != null) {
                clientTicket.setNote(note);
            }
            if (organisation != null) {
                clientTicket.setInfisFrom(organisation.getInfisCode());
            }
            clientTicket.setTicket(ticket);
            clientTicket.setAppointmentType(null);
            clientTicket.setCreateDateTime(creationDate);
            clientTicket.setModifyDateTime(creationDate);
            em.persist(clientTicket);
            em.flush();
            return clientTicket;
        } else {
            return null;
        }
    }

    @Override
    public ScheduleClientTicket getScheduleClientTicketById(final int clientTicketId) {
        return em.find(ScheduleClientTicket.class, clientTicketId);
    }

    @Override
    public boolean dequeueScheduleClientTicket(final ScheduleClientTicket ticket) {
        final Date modifyDate = new Date();
        ticket.setDeleted(true);
        ticket.setModifyDateTime(modifyDate);
        if (ticket.getNote() != null && !ticket.getNote().isEmpty()) {
            ticket.setNote("#Cancelled:".concat(ticket.getNote()));
        } else {
            ticket.setNote("#Cancelled");
        }
        final ScheduleClientTicket merged = em.merge(ticket);
        return merged.isDeleted();
    }

    /**
     * Получение всех талонов на приемы для заданного пациента  (удаленные не попадут в список)
     *
     * @param patient пациент, талоны которого надо получить
     * @return список талонов пациента \ пустой список если ничего не найдено
     */
    @Override
    public List<ScheduleClientTicket> getScheduleClientTicketsForPatient(final Patient patient) {
        return em.createNamedQuery("ScheduleClientTicket.findActiveByPatient", ScheduleClientTicket.class)
                .setParameter("patient", patient)
                .getResultList();
    }

    /**
     * Получение всех квотирований по времени на врача и дату
     *
     * @param person врач, для которого нужно найти квотирования
     * @param date   Дата, на которую нужно найти квотирования
     * @return список всех квотирований \ пустой список если ничего не найдено
     */
    @Override
    public List<QuotingByTime> getQuotingByTimeToPersonAndDate(final Staff person, final Date date) {
        return em.createNamedQuery("QuotingByTime.findByPersonAndDate", QuotingByTime.class)
                .setParameter("person", person)
                .setParameter("date", date, TemporalType.DATE)
                .getResultList();
    }

    /**
     * Получение всех квотирований по времени заданного типа на врача и дату
     *
     * @param person врач, для которого нужно найти квотирования
     * @param date   Дата, на которую нужно найти квотирования
     * @return список всех квотирований \ пустой список если ничего не найдено
     */
    @Override
    public List<QuotingByTime> getQuotingByTimeToPersonAndDateAndType(final Staff person, final LocalDate date, final RbTimeQuotingType quotingType) {
        return em.createNamedQuery("QuotingByTime.findByPersonAndDateAndType", QuotingByTime.class)
                .setParameter("person", person)
                .setParameter("date", date.toDate(), TemporalType.DATE)
                .setParameter("quotingType", quotingType)
                .getResultList();
    }

    /**
     * Получение всех типов квот по времени
     * @return  Список всех квот по времени
     */
    @Override
    public List<RbTimeQuotingType> getQuotingTypeList(){
        return em.createNamedQuery("RbTimeQuotingType.findAll", RbTimeQuotingType.class).getResultList();
    }

    /**
     * Ищет и возвращает все расписания заданного врача (сгруппированные в один день) за интервал времени
     *
     * @param startDateTime Дата начала поиска расписаний   (время опускается)
     * @param endDateTime   Дата конца поиска расписаний      (время опускается)
     * @param person        Врач, расписания которого требуется найти
     * @return Список расписаний заданного врача на указанный интервал времени (пустой список, если нету ни одного расписания)
     */
    @Override
    public List<PersonSchedule> getPersonScheduleOnDateInterval(Staff person, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        final List<Schedule> scheduleList = em.createNamedQuery("Schedule.findByDateIntervalAndPerson", Schedule.class)
                .setParameter("startDate", startDateTime.toDate(), TemporalType.DATE)
                .setParameter("endDate", endDateTime.toDate(), TemporalType.DATE)
                .setParameter("person", person)
                .getResultList();
        if(LOGGER.isDebugEnabled()){
            for(Schedule current : scheduleList){
                LOGGER.debug(current.toString());
            }
        }
        final Map<LocalDate, List<Schedule>> groupedList = new LinkedHashMap<LocalDate, List<Schedule>>(scheduleList.size());
        for (Schedule currentSchedule : scheduleList) {
            final LocalDate key = new LocalDate(currentSchedule.getDate());
            if (!groupedList.containsKey(key)) {
                final List<Schedule> newList = new ArrayList<Schedule>(2);
                newList.add(currentSchedule);
                groupedList.put(key, newList);
            } else {
                groupedList.get(key).add(currentSchedule);
            }
        }
        final List<PersonSchedule> personScheduleList = new ArrayList<PersonSchedule>(groupedList.size());
        for (Map.Entry<LocalDate, List<Schedule>> entry : groupedList.entrySet()) {
            personScheduleList.add(new PersonSchedule(entry.getKey(), person, entry.getValue()));
        }
        return personScheduleList;
    }
}
