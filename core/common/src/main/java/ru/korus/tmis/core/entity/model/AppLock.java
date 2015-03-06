package ru.korus.tmis.core.entity.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * The persistent class for the AppLock database table.
 * 
 */
@Entity
@Table(name="AppLock")
public class AppLock implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false, updatable = false)
	private Integer id;

	@Column(nullable=false, length=255, updatable = false)
	private String addr;

	@Column(nullable=false, updatable = false)
	private int connectionId;

	@Column(nullable=false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
	private Date lockTime;

    @ManyToOne
    @JoinColumn(name="person_id", updatable = false)
	private Staff person;

	@Column(nullable=false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
	private Date retTime;

	public AppLock() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAddr() {
		return this.addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public int getConnectionId() {
		return this.connectionId;
	}

	public void setConnectionId(int connectionId) {
		this.connectionId = connectionId;
	}

	public Date getLockTime() {
		return this.lockTime;
	}

	public void setLockTime(Date lockTime) {
		this.lockTime = lockTime;
	}

	public Staff getPerson() {
		return this.person;
	}

	public void setPerson(Staff person) {
		this.person = person;
	}

	public Date getRetTime() {
		return this.retTime;
	}

	public void setRetTime(Date retTime) {
		this.retTime = retTime;
	}

}