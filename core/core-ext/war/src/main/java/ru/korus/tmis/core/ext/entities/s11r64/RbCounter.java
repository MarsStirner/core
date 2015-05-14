package ru.korus.tmis.core.ext.entities.s11r64;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the rbCounter database table.
 * 
 */
@Entity
@Table(name = "rbCounter")
public class RbCounter implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String code;

	private String name;

	private String prefix;

	private int reset;

	@Temporal(TemporalType.TIMESTAMP)
	private Date resetDate;

	private String separator;

	private byte sequenceFlag;

	@Temporal(TemporalType.TIMESTAMP)
	private Date startDate;

	private int value;

	//bi-directional many-to-one association to EventType
	@OneToMany(mappedBy="rbCounter")
	private List<EventType> eventTypes;

	public RbCounter() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrefix() {
		return this.prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public int getReset() {
		return this.reset;
	}

	public void setReset(int reset) {
		this.reset = reset;
	}

	public Date getResetDate() {
		return this.resetDate;
	}

	public void setResetDate(Date resetDate) {
		this.resetDate = resetDate;
	}

	public String getSeparator() {
		return this.separator;
	}

	public void setSeparator(String separator) {
		this.separator = separator;
	}

	public byte getSequenceFlag() {
		return this.sequenceFlag;
	}

	public void setSequenceFlag(byte sequenceFlag) {
		this.sequenceFlag = sequenceFlag;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public int getValue() {
		return this.value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public List<EventType> getEventTypes() {
		return this.eventTypes;
	}

	public void setEventTypes(List<EventType> eventTypes) {
		this.eventTypes = eventTypes;
	}

}