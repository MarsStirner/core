package ru.korus.tmis.core.entity.model.communication;

import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.entity.model.Patient;
import ru.korus.tmis.core.entity.model.Staff;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Author: Upatov Egor <br>
 * Date: 28.10.13, 16:07 <br>
 * Company: Korus Consulting IT <br>
 * Description: Таблица для сохранения всех записей на прием к врачу (также созданных не через КС (компонента связи))<br>
 */
@Entity
@Table(name = "EPGUTickets")
@NamedQueries(
        {
                @NamedQuery(name = "QueueTicket.findAll", query = "SELECT t FROM QueueTicket t"),
                @NamedQuery(name = "QueueTicket.changeStatus",
                        query = "UPDATE QueueTicket t " +
                                "SET t.status = :status, t.lastModificationDate = :modifyDateTime " +
                                "WHERE t.id = :ticketId"),
                @NamedQuery(name = "QueueTicket.getChanges",
                        query = "SELECT t FROM QueueTicket t" +
                                " WHERE t.status IN :statusList ORDER BY t.lastModificationDate ASC")
        })
public class QueueTicket implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "personId")
    private Staff person;

    @ManyToOne
    @JoinColumn(name = "clientId")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "queueId")
    private Action queueAction;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "begDateTime")
    private Date begDateTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "endDateTime")
    private Date endDateTime;

    @Column(name = "office")
    private String office;

    @Column(name = "status")
    private String status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "lastModificationDate")
    private Date lastModificationDate;


    public QueueTicket() {
    }

    public QueueTicket(Staff person, Patient patient, Action queueAction, Date begDateTime, Date endDateTime, String office) {
        this.person = person;
        this.patient = patient;
        this.queueAction = queueAction;
        this.begDateTime = begDateTime;
        this.endDateTime = endDateTime;
        this.office = office;
        this.status = QueueTicket.Status.NEW.toString();
        this.lastModificationDate = new Date();
    }

    public String getInfoString() {
        return new StringBuilder("QueueTicket[ id=").append(id)
                .append(" STATUS=").append(status)
                .append(" personId=").append(person != null ? person.getId() : "null")
                .append(" patientId=").append(patient != null ? patient.getId() : "null")
                .append(" actionId=").append(queueAction != null ? queueAction.getId() : "null")
                .append(" begTime=").append(begDateTime != null ? begDateTime.toString() : "null")
                .append(" endTime=").append(endDateTime != null ? endDateTime.toString() : "null")
                .append(" office=").append(office).append("]").toString();
    }

    /**
     * Перечисление статусов талончика
     */
    public static enum Status {
        /**
         * Новый талончик (новая запись на прием)
         */
        NEW("NEW"),
        /**
         * Отмена записи на прием (новая отмена)
         */
        CANCELLED("CNC"),
        /**
         * Отправлено на ГП
         */
        SENDED("SND"),
        /**
         * Удалено (дубликат [успели записать и отменить запись до отправки на ГП])
         */
        DELETED("DEL");

        private Status(final String text) {
            this.text = text;
        }

        private final String text;

        @Override
        public String toString() {
            return text;
        }
    }


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

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Action getQueueAction() {
        return queueAction;
    }

    public void setQueueAction(Action queueAction) {
        this.queueAction = queueAction;
    }

    public Date getBegDateTime() {
        return begDateTime;
    }

    public void setBegDateTime(Date begDateTime) {
        this.begDateTime = begDateTime;
    }

    public Date getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Date endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getLastModificationDate() {
        return lastModificationDate;
    }

    public void setLastModificationDate(Date lastModificationDate) {
        this.lastModificationDate = lastModificationDate;
    }
}

