package ru.korus.tmis.core.entity.model;

import ru.korus.tmis.core.transmit.Transmittable;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * The persistent class for the ActionToODVD database table.
 * 
 */
@Entity
@Table(name="NotificationAction")
@NamedQueries(
        {
                @NamedQuery(name = "NotificationAction.ToSend", query = "SELECT e FROM NotificationAction e WHERE e.sendTime < :now ORDER BY e.actionId")
        }
)
public class NotificationAction implements Serializable, Transmittable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="action_id", unique=true, nullable=false)
	private int actionId;

	@Column(nullable=false)
	private int errCount;

	@Column(length=1024)
	private String info;

	@Column(nullable=false)
	private Timestamp sendTime;

	//bi-directional one-to-one association to ActionTst
	@OneToOne
	@JoinColumn(name="action_id", nullable=false, insertable=false, updatable=false)
	private Action action;

	public NotificationAction() {
	}

	public int getActionId() {
		return this.actionId;
	}

	public void setActionId(int actionId) {
		this.actionId = actionId;
	}

    @Override
    public Integer getId() {
        return getActionId();
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
		this.info = info == null ? null : info.substring(0, Math.min(info.length(), 1024));
	}

	public Timestamp getSendTime() {
		return this.sendTime;
	}

	public void setSendTime(Timestamp sendTime) {
		this.sendTime = sendTime;
	}

	public Action getAction() {
		return this.action;
	}

	public void setAction(Action action) {
		this.action = action;
	}



}