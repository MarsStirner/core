package ru.korus.tmis.core.entity.model;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Author: Upatov Egor <br>
 * Date: 03.06.2014, 20:50 <br>
 * Company: Korus Consulting IT <br>
 * Description: Расписание врача (dbtool 175) <br>
 */
@Entity
@Table(name = "Schedule")
@NamedQueries(
        {
                @NamedQuery(name = "Schedule.findAll",
                        query = "SELECT s FROM Schedule s"),
                @NamedQuery(name = "Schedule.findAllUndeleted",
                        query = "SELECT s FROM Schedule s WHERE s.deleted = false"),
                @NamedQuery(name = "Schedule.findByDateAndPerson",
                        query = "SELECT s FROM Schedule s WHERE s.deleted = false AND s.date = :date AND s.person = :person"),
                @NamedQuery(name = "Schedule.findByDateIntervalAndPerson",
                        query = "SELECT s FROM Schedule s WHERE s.deleted = false AND s.date BETWEEN :startDate AND :endDate AND s.person = :person ORDER BY s.date ASC")
        })
public class Schedule {
    private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("YYYY-MM-dd");
    private static final SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
    private static final SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");
    //Идентификатор записи
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    //Идентификатор сотрудника, которому назначен график
    @ManyToOne
    @JoinColumn(name = "person_id", nullable = true)
    private Staff person;

    //Дата графика  (без времени)
    @Column(name = "date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date date;

    //Время начала работы
    @Column(name = "begTime", nullable = false)
    @Temporal(TemporalType.TIME)
    private Date begTime;

    //Время окончания работы
    @Column(name = "endTime", nullable = false)
    @Temporal(TemporalType.TIME)
    private Date endTime;

    // Количество талонов на прием
    @Column(name = "numTickets", nullable = true)
    private Integer numTickets;

    //кабинет
    @ManyToOne
    @JoinColumn(name = "office_id", nullable = true)
    private Office office;


    //Причина отсутствия
    @ManyToOne
    @JoinColumn(name = "reasonOfAbsence_id", nullable = true)
    private RbReasonOfAbsence reasonOfAbsence;

    //Тип приема
    @ManyToOne
    @JoinColumn(name = "receptionType_id", nullable = true)
    private RbReceptionType receptionType;

    //Датавремя создания записи в БД
    @Column(name = "createDatetime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDateTime;

    // Ид создателя записи   (Lazy)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "createPerson_id", nullable = true)
    private Staff createPerson;

    //Датавремя последней модификации записи в БД
    @Column(name = "modifyDatetime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifyDateTime;

    // Ид последнего модификатора записи   (Lazy)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modifyPerson_id", nullable = true)
    private Staff modifyPerson;

    @Column(name = "deleted", nullable = false)
    private boolean deleted;

    ////////////////////////////
    // Custom Mappings
    ////////////////////////////
    @OneToMany(mappedBy = "schedule", targetEntity = ScheduleTicket.class, fetch = FetchType.LAZY)
    private List<ScheduleTicket> ticketList = new ArrayList<ScheduleTicket>();
    // Хранение уже сформированного сортированного списка неудаленных талонов
    @Transient
    private ArrayList<ScheduleTicket> activeTicketList = new ArrayList<ScheduleTicket>();
    @Transient
    private boolean activeTicketListIsFormed = false;

    /**
     * Получение всех неудаленных талонов расписания
     * @return Отсортированные по времени (asc) неудаленные талоны \ Пустой список если таких талонов нет
     */
    public List<ScheduleTicket> getActiveTicketList() {
        if (!activeTicketListIsFormed) {
            activeTicketList = new ArrayList<ScheduleTicket>(ticketList.size());
            for (ScheduleTicket current : ticketList) {
                if (!current.isDeleted()) {
                    activeTicketList.add(current);
                }
            }
            activeTicketList.trimToSize();
            Collections.sort(activeTicketList);
            activeTicketListIsFormed = true;
        }
        return activeTicketList;
    }

    /**
     * Проверяет входит ли заданная датавремя в интервал действия расписания (между (date+begTime) включительно и (date+endTime) исключительно)
     *
     * @param requestedDate датавремя, которое надо проверить на вхождение в интервал
     * @return true - входит \ false - не входит
     */
    public boolean dateTimeIntervalsContainsDate(final Date requestedDate) {
        final LocalDate currentScheduleDate = new LocalDate(date);
        final LocalDateTime currentScheduleBegDateTime = currentScheduleDate.toLocalDateTime(new LocalTime(begTime));
        final LocalDateTime currentScheduleEndDateTime = currentScheduleDate.toLocalDateTime(new LocalTime(endTime));
        final LocalDateTime requested = new LocalDateTime(requestedDate);
        return (requested.equals(currentScheduleBegDateTime) || requested.isAfter(currentScheduleBegDateTime)) && requested.isBefore(currentScheduleEndDateTime);
    }

    /**
     * Ищет неудаленный талон на прием в указанное время (Если талон занят - то все равно вернет)
     * @param time время начала талона
     * @return талон \ null елси на это время талона нет
     */
    public ScheduleTicket getAvailableTicketToTime(final LocalTime time) {
        for(ScheduleTicket current : getActiveTicketList()){
            //Искомое время совпадает со временем начала талона
            if(time.equals(new LocalTime(current.getBegTime()))){
                return current;
            }
        }
        return null;
    }

    //No-arg constructor
    public Schedule() {
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Schedule[");
        sb.append(id);
        sb.append("]{ person=").append(person.getId());
        sb.append(", date=").append(dateFormatter.format(date));
        sb.append(", begTime=").append(timeFormatter.format(begTime));
        sb.append(", endTime=").append(timeFormatter.format(endTime));
        sb.append(", numTickets=").append(numTickets);
        sb.append(", office='").append(office).append('\'');
        sb.append(", reasonOfAbsence=").append(reasonOfAbsence != null ? reasonOfAbsence.getCode() : null);
        sb.append(", receptionType=").append(receptionType != null ? receptionType.getCode() : null);
        sb.append(", createDateTime=").append(dateTimeFormatter.format(createDateTime));
        if (createPerson != null) {
            sb.append(", createPerson=").append(createPerson.getId());
        }
        sb.append(", modifyDateTime=").append(dateTimeFormatter.format(modifyDateTime));
        if (modifyPerson != null) {
            sb.append(", modifyPerson=").append(modifyPerson);
        }
        sb.append(", deleted=").append(deleted);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Schedule schedule = (Schedule) o;

        if (deleted != schedule.deleted) return false;
        if (!date.equals(schedule.date)) return false;
        if (!id.equals(schedule.id)) return false;
        if (!person.equals(schedule.person)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + person.hashCode();
        result = 31 * result + date.hashCode();
        result = 31 * result + (deleted ? 1 : 0);
        return result;
    }

    //////////////GET & SET

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Staff getPerson() {
        return person;
    }

    public void setPerson(Staff person) {
        this.person = person;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getBegTime() {
        return begTime;
    }

    public void setBegTime(Date begTime) {
        this.begTime = begTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getNumTickets() {
        return numTickets;
    }

    public void setNumTickets(Integer numTickets) {
        this.numTickets = numTickets;
    }

    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }

    public RbReasonOfAbsence getReasonOfAbsence() {
        return reasonOfAbsence;
    }

    public void setReasonOfAbsence(RbReasonOfAbsence reasonOfAbsence) {
        this.reasonOfAbsence = reasonOfAbsence;
    }

    public RbReceptionType getReceptionType() {
        return receptionType;
    }

    public void setReceptionType(RbReceptionType receptionType) {
        this.receptionType = receptionType;
    }

    public Date getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(Date createDateTime) {
        this.createDateTime = createDateTime;
    }

    public Staff getCreatePerson() {
        return createPerson;
    }

    public void setCreatePerson(Staff createPerson) {
        this.createPerson = createPerson;
    }

    public Date getModifyDateTime() {
        return modifyDateTime;
    }

    public void setModifyDateTime(Date modifyDateTime) {
        this.modifyDateTime = modifyDateTime;
    }

    public Staff getModifyPerson() {
        return modifyPerson;
    }

    public void setModifyPerson(Staff modifyPerson) {
        this.modifyPerson = modifyPerson;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public List<ScheduleTicket> getTicketList() {
        return ticketList;
    }

    public void setTicketList(List<ScheduleTicket> ticketList) {
        this.ticketList = ticketList;
    }



}
