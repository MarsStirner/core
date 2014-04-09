package ru.korus.tmis.core.entity.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the rbAcheResult database table.
 * 
 */
@Entity
@Table(name="rbAcheResult")
public class RbAcheResult implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(nullable=false, length=3)
	private String code;

	@Column(nullable=false)
	private int eventPurpose_id;

	@Column(nullable=false, length=64)
	private String name;

	public RbAcheResult() {
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

	public int getEventPurpose_id() {
		return this.eventPurpose_id;
	}

	public void setEventPurpose_id(int eventPurpose_id) {
		this.eventPurpose_id = eventPurpose_id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}