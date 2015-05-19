package ru.korus.tmis.core.ext.entities.s11r64;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the rbMesSpecification database table.
 * 
 */
@Entity
@Table(name = "rbMesSpecification")
public class RbMesSpecification implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String code;

	private byte done;

	private String name;

	private String regionalCode;

	//bi-directional many-to-one association to Event
	@OneToMany(mappedBy="rbMesSpecification")
	private List<Event> events;

	public RbMesSpecification() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public byte getDone() {
		return this.done;
	}

	public void setDone(byte done) {
		this.done = done;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRegionalCode() {
		return this.regionalCode;
	}

	public void setRegionalCode(String regionalCode) {
		this.regionalCode = regionalCode;
	}

	public List<Event> getEvents() {
		return this.events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

}