package ru.korus.tmis.core.entity.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Author: Upatov Egor <br>
 * Date: 04.06.2014, 15:40 <br>
 * Company: Korus Consulting IT <br>
 * Description: Записи пациентов на талоны (dbtool 175) <br>
 */
@Entity
@Table(name = "ScheduleClientTicket")
@NamedQueries({
        @NamedQuery(name = "ScheduleClientTicket.findByScheduleTicketList",
                query = "SELECT sct FROM ScheduleClientTicket sct WHERE sct.ticket.id IN :ticketList AND sct.deleted = false"),
        @NamedQuery(name = "ScheduleClientTicket.findActiveByPatient",
                query = "SELECT sct FROM ScheduleClientTicket sct WHERE sct.deleted = false AND sct.client = :patient")
})
public class ScheduleClientTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    //Пациент, которому принадлежит этот талон
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Patient client;

    //Ссылка на талон из расписания
    @ManyToOne
    @JoinColumn(name = "ticket_id", nullable = false)
    private ScheduleTicket ticket;

    @Column(name = "isUrgent", nullable = true)
    private Boolean urgent;

    //Примечание
    @Column(name = "note", nullable = true)
    private String note;

    @ManyToOne
    @JoinColumn(name = "appointmentType_id", nullable = true)
    private RbAppointmentType appointmentType;

    //организация из которой была произведена запись
    @Column(name = "infisFrom", nullable = true)
    private String infisFrom;

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

    //'Событие, созданное на основе талона (если пациент таки пришёл)'
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = true)
    private Event event;

    //'Квота, по которой записан пациент'
    @ManyToOne
    @JoinColumn(name= "timeQuotingType_id", nullable = true)
    private RbTimeQuotingType timeQuotingType;

    public ScheduleClientTicket() {
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ScheduleClientTicket[");
        sb.append(id);
        sb.append("] {client=").append(client.getId());
        sb.append(", ticket=").append(ticket.getId());
        sb.append(", urgent=").append(urgent);
        sb.append(", note='").append(note).append('\'');
        if (appointmentType != null) {
            sb.append(", appointmentType=").append(appointmentType.getCode());
        }
        if(infisFrom != null){
            sb.append(", infisFrom=").append(infisFrom);
        }
        sb.append(", deleted=").append(deleted);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ScheduleClientTicket that = (ScheduleClientTicket) o;

        if (!client.equals(that.client)) return false;
        if (!id.equals(that.id)) return false;
        if (!ticket.equals(that.ticket)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + client.hashCode();
        result = 31 * result + ticket.hashCode();
        return result;
    }

    /////////GET & SET

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Patient getClient() {
        return client;
    }

    public void setClient(Patient client) {
        this.client = client;
    }

    public ScheduleTicket getTicket() {
        return ticket;
    }

    public void setTicket(ScheduleTicket ticket) {
        this.ticket = ticket;
    }

    public Boolean getUrgent() {
        return urgent;
    }

    public void setUrgent(Boolean urgent) {
        this.urgent = urgent;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public RbAppointmentType getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(RbAppointmentType appointmentType) {
        this.appointmentType = appointmentType;
    }

    public String getInfisFrom() {
        return infisFrom;
    }

    public void setInfisFrom(String infisFrom) {
        this.infisFrom = infisFrom;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public RbTimeQuotingType getTimeQuotingType() {
        return timeQuotingType;
    }

    public void setTimeQuotingType(RbTimeQuotingType timeQuotingType) {
        this.timeQuotingType = timeQuotingType;
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
}
