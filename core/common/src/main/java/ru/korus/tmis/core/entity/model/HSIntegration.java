package ru.korus.tmis.core.entity.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * The persistent class for the HSIntegration database table.
 * 
 */
@Entity
@Table(name = "HSIntegration")
public class HSIntegration implements Serializable {
    private static final long serialVersionUID = 1L;

    public static enum Status {
        NEW,
        SENDED,
        ERROR;
    }

    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "event_id", unique = true, nullable = false)
    private int eventId;

    @Column(length = 1024)
    private String info;

    @Basic(optional = false)
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @OneToOne
    @JoinColumn(name = "event_id", nullable = false, insertable = false, updatable = false)
    private Event event;

    public HSIntegration() {
    }

    /*
     * public int getEventId() { return this.eventId; }
     * 
     * public void setEventId(int eventId) { this.eventId = eventId; }
     */

    public String getInfo() {
        return this.info;
    }

    public void setInfo(String info) {
        this.info = info.substring(0, Math.min(info.length(), 1023));
    }

    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Event getEvent() {
        return this.event;
    }

    public void setEvent(Event event) {
        this.event = event;
        eventId = event.getId();
    }

}