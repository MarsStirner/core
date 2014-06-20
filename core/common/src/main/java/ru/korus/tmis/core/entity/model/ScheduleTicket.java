package ru.korus.tmis.core.entity.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Author: Upatov Egor <br>
 * Date: 04.06.2014, 14:39 <br>
 * Company: Korus Consulting IT <br>
 * Description: Таблица талонов врача. Талоны формируются в рамках графика врача. (dbtool 175) <br>
 */
@Entity
@Table(name = "ScheduleTicket")
@NamedQueries({
        @NamedQuery(name = "ScheduleTicket.findAll", query = "SELECT st FROM ScheduleTicket st"),
        @NamedQuery(name = "ScheduleTicket.findBySchedule",
                query="SELECT st FROM ScheduleTicket st WHERE st.schedule = :schedule AND st.deleted = false")
})

public class ScheduleTicket implements Comparable<ScheduleTicket>{

   private static final SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    //Расписание, которому принадлежит этот талон
    @ManyToOne
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule;

    // Датавремя начала талона
    @Column(name = "begTime", nullable = true)
    @Temporal(TemporalType.TIME)
    private Date begTime;

    // Датавремя окончания талона
    @Column(name = "endTime", nullable = true)
    @Temporal(TemporalType.TIME)
    private Date endTime;

    // тип талона
    @ManyToOne
    @JoinColumn(name = "attendanceType_id", nullable = false)
    private RbAttendanceType attendanceType;

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

    @OneToMany(mappedBy = "ticket", targetEntity = ScheduleClientTicket.class, fetch = FetchType.LAZY)
    private List<ScheduleClientTicket> clientTicketList = new ArrayList<ScheduleClientTicket>(2);

    @Transient
    private ArrayList<ScheduleClientTicket> activeClientTicketList = new ArrayList<ScheduleClientTicket>(1);
    @Transient
    private boolean activeClientTicketListIsFormed = false;

    public List<ScheduleClientTicket> getActiveClientTicketList(){
        if (!activeClientTicketListIsFormed) {
            for (ScheduleClientTicket current : clientTicketList) {
                if (!current.isDeleted()) {
                    activeClientTicketList.add(current);
                }
            }
            activeClientTicketList.trimToSize();
            activeClientTicketListIsFormed = true;
        }
        return activeClientTicketList;
    }

    /**
     * Возвращает признак свободности талона от записей пациентов (если нету ни одной неудаленной записи пациента то талон считается свободным)
     * @return true - свободен \ false - занят
     */
    public boolean isFree() {
        return getActiveClientTicketList().isEmpty();
    }

    //No-arg constructor
    public ScheduleTicket() {
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ScheduleTicket[");
        sb.append(id);
        sb.append("]{ schedule=").append(schedule.getId());
        sb.append(", begDateTime=").append(timeFormatter.format(begTime));
        sb.append(", endDateTime=").append(timeFormatter.format(endTime));
        sb.append(", attendanceType=").append(attendanceType != null ? attendanceType.getCode() : "NULL");
        sb.append(", deleted=").append(deleted);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScheduleTicket that = (ScheduleTicket) o;
        if (!attendanceType.equals(that.attendanceType)) return false;
        if (begTime != null ? !begTime.equals(that.begTime) : that.begTime != null) return false;
        if (endTime != null ? !endTime.equals(that.endTime) : that.endTime != null) return false;
        if (!id.equals(that.id)) return false;
        if (!schedule.equals(that.schedule)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + schedule.hashCode();
        result = 31 * result + (begTime != null ? begTime.hashCode() : 0);
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        result = 31 * result + attendanceType.hashCode();
        return result;
    }

    ///////////GET & SET

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
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

    public RbAttendanceType getAttendanceType() {
        return attendanceType;
    }

    public void setAttendanceType(RbAttendanceType attendanceType) {
        this.attendanceType = attendanceType;
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

    public List<ScheduleClientTicket> getClientTicketList() {
        return clientTicketList;
    }

    public void setClientTicketList(List<ScheduleClientTicket> clientTicketList) {
        this.clientTicketList = clientTicketList;
    }

    @Override
    public int compareTo(@NotNull final ScheduleTicket o) {
        return begTime.compareTo(o.getBegTime());
    }


}
