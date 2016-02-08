package ru.korus.tmis.core.entity.model.hsct;

import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.entity.model.Staff;

import javax.persistence.*;
import java.util.Date;

/**
 * Author: Upatov Egor <br>
 * Date: 03.02.2016, 18:49 <br>
 * Company: hitsl (Hi-Tech Solutions) <br>
 * Description: Сущность для реализации очереди на отправку заявок в ТГСК <br>
 */

@Entity
@Table(name = "queueHsctRequest")
@NamedQueries({@NamedQuery(name = "QueueHsctRequest.findAllByStatuses", query = "SELECT i FROM QueueHsctRequest i WHERE i.status IN :statusList")})
public class QueueHsctRequest {

    /**
     * Идентифкатор заявки , а одновременно с этим и идентифкатор Action
     */
    @Id
    @Basic(optional = false)
    @Column(name = "action_id")
    private Integer actionId;

    /**
     * Врач, переводивший заявку в очередь
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "person_id", nullable = false)
    private Staff person;

    /**
     * Текущий статус заявки в очереди
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    /**
     * Время отправки заявки из очереди \планируемое время отправки  ( в зависимости от статуса )
     */
    @Column(name = "sendDateTime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date sendDateTime;

    /**
     * Попыток отправки
     */
    @Column(name = "attempts", nullable = false)
    private int attempts;

    /**
     * Текст с информацией о последней попытке отправки
     */
    @Column(name = "info", nullable = true)
    private String info;


    public QueueHsctRequest() {
        //Default constructor
    }

    public QueueHsctRequest(final Action action, final Staff person) {
        this.actionId = action.getId();
        this.person = person;
        this.attempts = 0;
        this.sendDateTime = new Date();
        this.status = Status.NEW;
    }

    public Integer getActionId() {
        return actionId;
    }

    public void setActionId(final Integer actionId) {
        this.actionId = actionId;
    }

    public Staff getPerson() {
        return person;
    }

    public void setPerson(final Staff person) {
        this.person = person;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(final Status status) {
        this.status = status;
    }

    public Date getSendDateTime() {
        return sendDateTime;
    }

    public void setSendDateTime(final Date sendDateTime) {
        this.sendDateTime = sendDateTime;
    }

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(final int attempts) {
        this.attempts = attempts;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(final String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("QueueHsctRequest{");
        sb.append("actionId=").append(actionId);
        sb.append(", person=").append(person);
        sb.append(", status=").append(status);
        sb.append(", sendDateTime=").append(sendDateTime);
        sb.append(", attempts=").append(attempts);
        sb.append(", info='").append(info).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
