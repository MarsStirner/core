package ru.korus.tmis.core.entity.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the EventsToODVD database table.
 * 
 */
@Entity
@Table(name="EventsToODVD")
@NamedQueries(
        {
                @NamedQuery(name = "EventsToODVD.ToSend", query = "SELECT e FROM EventsToODVD e WHERE e.sendTime < :now ORDER BY e.eventId")
        }
)
public class EventsToODVD implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="event_id", unique=true, nullable=false)
	private int eventId;

	@Column(nullable=false)
	private int errCount;

	@Column(length=1024)
	private String info;

	@Column(nullable=false)
	private Timestamp sendTime;

	//bi-directional one-to-one association to Event
	@OneToOne
	@JoinColumn(name="event_id", nullable=false, insertable=false, updatable=false)
	private Event event;

	public EventsToODVD() {
	}

	public int getEventId() {
		return this.eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public int getErrCount() {
		return this.errCount;
	}

	public void setErrCount(int errCount) {
		this.errCount = errCount;
	}

	public String getInfo() {
		return this.info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Timestamp getSendTime() {
		return this.sendTime;
	}

	public void setSendTime(Timestamp sendTime) {
		this.sendTime = sendTime;
	}

	public Event getEvent() {
		return this.event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

}