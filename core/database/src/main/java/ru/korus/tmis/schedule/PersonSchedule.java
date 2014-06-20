package ru.korus.tmis.schedule;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.entity.model.*;

import java.util.*;

/**
 * Author: Upatov Egor <br>
 * Date: 09.06.2014, 20:33 <br>
 * Company: Korus Consulting IT <br>
 * Description:  расписание врача на один день<br>
 */
public class PersonSchedule {
    //Logger
    private static final Logger logger = LoggerFactory.getLogger("ru.korus.tmis.communication");
    private static final String CITO_CODE = "CITO";
    private static final String EXTRA_CODE = "extra";
    /**
     * Врач, к которму приписан этот прием пациентов
     */
    private final Staff doctor;

    /**
     * Дата приема врача
     */
    private final LocalDate scheduleDate;

    /**
     * Ограничения по времени на прием врача для заданного типа квоты
     */
    private List<QuotingByTime> quotingByTimeConstraints;

    /**
     * Сформированный список талончиков (отсортированный по времени начала талона)
     */
    private final ArrayList<ScheduleTicketExtended> tickets;

    /**
     * Офис в котором будет происходить прием пациентов
     */
    private final Set<String> office;

    /**
     * Время начала приема врача
     */
    private LocalTime begTime;

    /**
     * Время окончания приема врача
     */
    private LocalTime endTime;

    /**
     * План (насколько я понял - это ожидаемое количетсво талончиков = times.size())
     */
    private int plan;

    /*
     * Количество записей из регистратуры
     */
    private short fromRegistryCount = 0;
    /**
     * Количество записей на повторный прием
     */
    private int secondaryVisitCount = 0;
    /**
     * Количество записей с портала
     */
    private int fromPortalCount;
    /**
     * Количество записей из других ЛПУ
     */
    private int otherLPUCount;
    /**
     * Количество записей между кабинетами
     */
    private int betweenCabinetCount;

    /**
     * Признак доступность записи на прием к врачу в этот день
     */
    private boolean available;

    /**
     * количество записанных экстренно
     */
    private short emergencyPatientCount = 0;

    /**
     * Количество записанных вне очереди
     */
    private short citoCount = 0;

    /**
     * Количество записанных сверх плана
     */
    private short extraCount = 0;


    public PersonSchedule(final LocalDate date, final Staff person, final List<Schedule> scheduleList) {
        this.scheduleDate = date;
        this.doctor = person;
        this.office = new HashSet<String>(scheduleList.size());
        this.tickets = new ArrayList<ScheduleTicketExtended>();
        for (Schedule currentSchedule : scheduleList) {
            if (currentSchedule.getReasonOfAbsence() != null) {
                logger.debug("Schedule[{}] has {}", currentSchedule.getId(), currentSchedule.getReasonOfAbsence());
                continue;
            }
            final LocalTime currentScheduleBegTime = new LocalTime(currentSchedule.getBegTime());
            final LocalTime currentScheduleEndTime = new LocalTime(currentSchedule.getEndTime());
            //время начала приема
            if (begTime == null || begTime.isAfter(currentScheduleBegTime)) {
                begTime = currentScheduleBegTime;
            }
            //время окончания приема
            if (endTime == null || endTime.isBefore(currentScheduleEndTime)) {
                endTime = currentScheduleEndTime;
            }
            //План приема (общее кол-во талонов)
            plan += currentSchedule.getNumTickets();
            //Офис
            if (currentSchedule.getOffice() != null) {
                office.add(currentSchedule.getOffice().getName());
            }
            //талоны
            for (ScheduleTicket currentTicket : currentSchedule.getActiveTicketList()) {
                tickets.add(new ScheduleTicketExtended(currentTicket, true));
            }
        }
        tickets.trimToSize();
        Collections.sort(tickets);
        //заполнение количества занятых талонов по типу
        fillCounts();
    }

    private void fillCounts() {
        for (ScheduleTicketExtended item : tickets) {
            ScheduleTicket ticket = item.getTicket();
            if (ticket.getAttendanceType() != null) {
                if (CITO_CODE.equals(ticket.getAttendanceType().getCode())) {
                    //Вне очереди
                    citoCount++;
                } else if (EXTRA_CODE.equals(ticket.getAttendanceType().getCode())) {
                    //Сверх плана
                    extraCount++;
                }
            }
            if (!ticket.isFree()) {
                for (ScheduleClientTicket currentClientTicket : ticket.getActiveClientTicketList()) {
                    //Пациент записан с пометкой срочно
                    if (currentClientTicket.getUrgent() != null && currentClientTicket.getUrgent()) {
                        emergencyPatientCount++;
                    }
                    //Проверяем откуда записан пациент
                    if (currentClientTicket.getTimeQuotingType() != null) {
                        final String quotingTypeCode = currentClientTicket.getTimeQuotingType().getCode();
                        if (RbTimeQuotingType.FROM_REGISTRY_QUOTING_TYPE_CODE.equals(quotingTypeCode)) {
                            fromRegistryCount++;
                        } else if (RbTimeQuotingType.SECOND_VISIT_QUOTING_TYPE_CODE.equals(quotingTypeCode)) {
                            secondaryVisitCount++;
                        } else if (RbTimeQuotingType.BETWEEN_CABINET_QUOTING_TYPE_CODE.equals(quotingTypeCode)) {
                            betweenCabinetCount++;
                        } else if (RbTimeQuotingType.FROM_OTHER_LPU_QUOTING_TYPE_CODE.equals(quotingTypeCode)) {
                            otherLPUCount++;
                        } else if (RbTimeQuotingType.FROM_PORTAL_QUOTING_TYPE_CODE.equals(quotingTypeCode)) {
                            fromPortalCount++;
                        }
                    }
                }
            }
        }
    }

    public Staff getDoctor() {
        return doctor;
    }

    public LocalDate getScheduleDate() {
        return scheduleDate;
    }

    public List<QuotingByTime> getQuotingByTimeConstraints() {
        return quotingByTimeConstraints;
    }

    public List<ScheduleTicketExtended> getTickets() {
        return tickets;
    }

    public String getOffice() {
        if (!office.isEmpty()) {
            final StringBuilder result = new StringBuilder();
            final Iterator<String> iterator = office.iterator();
            while (iterator.hasNext()) {
                result.append(iterator.next());
                if (iterator.hasNext()) {
                    result.append(", ");
                }
            }
            return result.toString();
        } else {
            return "";
        }
    }

    public LocalTime getBegTime() {
        return begTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public Integer getPlan() {
        return plan;
    }

    public boolean isAvailable() {
        return available;
    }

    public short getEmergencyPatientCount() {
        return emergencyPatientCount;
    }

    public void takeQuotingByTimeConstraintsToTickets(final List<QuotingByTime> quotingList) {
        //Вывод квот по времени в лог
        if (logger.isDebugEnabled()) {
            logger.debug("Quoting By Time");
            for (QuotingByTime currentQuoting : quotingList) {
                logger.debug("[{}] DATE={} FROM {} TO {}",
                        currentQuoting.getId(),
                        currentQuoting.getQuotingDate(),
                        currentQuoting.getQuotingTimeStart(),
                        currentQuoting.getQuotingTimeEnd()
                );
            }
        }

        for (ScheduleTicketExtended currentTicketExtended : tickets) {
            boolean available = false;
            ScheduleTicket currentTicket = currentTicketExtended.getTicket();
            for (QuotingByTime currentQuoting : quotingList) {
                //Проверяется не весь интервал, а только начало талончика
                //Потому что "так было в 6098" бебебе
                final LocalTime currentQuotingBegTime = new LocalTime(currentQuoting.getQuotingTimeStart());
                final LocalTime currentQuotingEndTime = new LocalTime(currentQuoting.getQuotingTimeEnd());
                if (currentQuotingBegTime.getMillisOfDay() != 0 && currentQuotingEndTime.getMillisOfDay() != 0) {
                    if ((currentTicketExtended.getBegTime().equals(currentQuotingBegTime) || currentTicketExtended.getBegTime().isAfter(currentQuotingBegTime))
                            && (currentTicketExtended.getBegTime().equals(currentQuotingEndTime) || currentTicketExtended.getBegTime().isBefore(currentQuotingEndTime))
                            && currentTicketExtended.isAvailable()) {
                        available = true;
                        break;
                    }
                }
            }
            currentTicketExtended.setAvailable(available);
        }
    }

    public void takePersonQuotingConstraintsToTickets(final RbTimeQuotingType quotingType) {
        int quota = 0;  //Размер квоты доступной для врача
        int quotedTickets = 0; //Количество талонов занятых этой квотой
        int quoteAvailable = 0; // Количество еще доступных для записи талонов
        final String quotingTypeCode = quotingType.getCode();
        if (RbTimeQuotingType.FROM_REGISTRY_QUOTING_TYPE_CODE.equals(quotingTypeCode)) {
            quota = doctor.getPrimaryQuota();
            quotedTickets = fromRegistryCount;
        } else if (RbTimeQuotingType.SECOND_VISIT_QUOTING_TYPE_CODE.equals(quotingTypeCode)) {
            quota = doctor.getOwnQuota();
            quotedTickets = secondaryVisitCount;
        } else if (RbTimeQuotingType.BETWEEN_CABINET_QUOTING_TYPE_CODE.equals(quotingTypeCode)) {
            quota = quota = doctor.getConsultancyQuota();
            quotedTickets = betweenCabinetCount;
        } else if (RbTimeQuotingType.FROM_PORTAL_QUOTING_TYPE_CODE.equals(quotingTypeCode)) {
            quota = doctor.getExternalQuota();
            quotedTickets = fromPortalCount + otherLPUCount;
        } else if (RbTimeQuotingType.FROM_OTHER_LPU_QUOTING_TYPE_CODE.equals(quotingTypeCode)) {
            quota = doctor.getExternalQuota();
            quotedTickets = fromPortalCount + otherLPUCount;
        } else {
            quota = 100;
            quotedTickets = 0;
        }
        logger.info("Quota={} quotedTickets={} unit={}", quota, quotedTickets, doctor.getQuoteUnit());
        if (doctor.getQuoteUnit() != null) {
            if (doctor.getQuoteUnit() == 0) {
                //%
                quoteAvailable = quota - quotedTickets * 100 / tickets.size();
            } else {
                //Абсолютное количество
                quoteAvailable = quota - quotedTickets;
            }
            if (quoteAvailable < 1) {
                setAllTicketsAvailabilityToFalse();
            }
        } else {
            logger.warn("Doctor quoteUnit is NULL, skip doctor quoting.");
        }
    }

    private void setAllTicketsAvailabilityToFalse() {
        for (ScheduleTicketExtended currentTicket : tickets) {
            currentTicket.setAvailable(false);
        }
    }

    public boolean takeQuotingBySpecialityConstraintsToTickets(final List<QuotingBySpeciality> quotingBySpecialityList) {
        switch (quotingBySpecialityList.size()) {
            case 0: {
                logger.debug("QuotingBySpecialityList is empty. All tickets available set to FALSE");
                setAllTicketsAvailabilityToFalse();
                return false;
            }
            case 1: {
                //Квота должна быть единственной
                final QuotingBySpeciality quoting = quotingBySpecialityList.iterator().next();
                logger.debug("QuotingBySpeciality[{}] founded. RemainingCoupons= {}", quoting.getId(), quoting.getCouponsRemaining());
                if (quoting.getCouponsRemaining() > 0) {
                    logger.debug("QuotingBySpeciality[{}]. OK", quoting.getId());
                    return true;
                } else {
                    logger.debug("QuotingBySpeciality[{}] is out of free coupons. All tickets available set to FALSE", quoting.getId());
                    setAllTicketsAvailabilityToFalse();
                    return false;
                }
            }
            default: {
                logger.error("There are many QuotingBySpeciality. Default:: Set all tickets available to FALSE");
                for (QuotingBySpeciality quoting : quotingBySpecialityList) {
                    logger.error("QuotingBySpeciality[{}] {speciality={} organisation = {} (infis={})}",
                            quoting.getId(),
                            quoting.getSpeciality().getId(),
                            quoting.getOrganisation().getId(),
                            quoting.getOrganisation().getInfisCode()
                    );
                }
                setAllTicketsAvailabilityToFalse();
                return false;
            }
        }
    }

    public ScheduleTicket getFirstFreeTicketAfterDateTime(final LocalDateTime dateTime) {
        //Проверка по времени нужна только для расписания в тот-же день
        final boolean timeCheckIsNeeded = scheduleDate.equals(dateTime.toLocalDate());
        for (ScheduleTicketExtended current : tickets) {
            if (current.isAvailable() && current.isFree()) {
                if (timeCheckIsNeeded) {
                    if (current.getBegTime().isAfter(dateTime.toLocalTime()) || current.getBegTime().equals(dateTime.toLocalTime())) {
                        return current.getTicket();
                    }
                } else {
                    return current.getTicket();
                }
            }
        }
        return null;

    }

    public ScheduleTicketExtended getTicketToTime(final LocalTime time) {
        for(ScheduleTicketExtended current : tickets){
            //Искомое время совпадает со временем начала талона
            if(time.equals(current.getBegTime())){
                return current;
            }
        }
        return null;
    }

    /**
     * Проверка не является записан ли указанный пациент в указанном приеме      *
     * @param patient пациент, для которого выполняется проверка
     * @return true - пациент записан в запрошенный прием, false - еще не записан
     */
    public boolean checkRepetitionEnqueue(final Patient patient) {
        for (ScheduleTicketExtended currentTicket : tickets) {
            if (patient.equals(currentTicket.getPatient())) {
                logger.info("Repetition enqueue with ScheduleTicket[{}].", currentTicket.getTicket().getId());
                return true;
            }
        }
        return false;
    }
}
