package ru.korus.tmis.core.entity.model.communication;

import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.entity.model.Patient;
import ru.korus.tmis.core.entity.model.ScheduleClientTicket;
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
    @JoinColumn(name = "scheduleClientTicket_id")
    private ScheduleClientTicket ticket;

    @Column(name = "status")
    private String status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "lastModificationDate")
    private Date lastModificationDate;


    public QueueTicket() {
    }

     public String getInfoString() {
        return new StringBuilder("QueueTicket[ id=").append(id)
                .append(" STATUS=").append(status)
                .append(" ticket=").append(ticket.getId())
                .append("]").toString();
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
        SENDED("SND");

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

    public ScheduleClientTicket getTicket() {
        return ticket;
    }

    public void setTicket(ScheduleClientTicket ticket) {
        this.ticket = ticket;
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

