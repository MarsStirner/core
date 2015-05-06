package ru.korus.tmis.vmp.entities.s11r64;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the rbAcheResult database table.
 * 
 */
@Entity(name = "rbAcheResult")
public class RbAcheResult implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String code;

	private String name;

	//bi-directional many-to-one association to Diagnostic
	@OneToMany(mappedBy="rbAcheResult")
	private List<Diagnostic> diagnostics;

	//bi-directional many-to-one association to Event
	@OneToMany(mappedBy="rbAcheResult")
	private List<Event> events;

	//bi-directional many-to-one association to RbEventTypePurpose
	@ManyToOne
	@JoinColumn(name="eventPurpose_id")
	private RbEventTypePurpose rbEventTypePurpose;

	public RbAcheResult() {
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

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Diagnostic> getDiagnostics() {
		return this.diagnostics;
	}

	public void setDiagnostics(List<Diagnostic> diagnostics) {
		this.diagnostics = diagnostics;
	}

	public List<Event> getEvents() {
		return this.events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	public RbEventTypePurpose getRbEventTypePurpose() {
		return this.rbEventTypePurpose;
	}

	public void setRbEventTypePurpose(RbEventTypePurpose rbEventTypePurpose) {
		this.rbEventTypePurpose = rbEventTypePurpose;
	}

}