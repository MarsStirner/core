package ru.korus.tmis.core.entity.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the rbScene database table.
 * 
 */
@Entity
@Table(name="rbScene")
public class RbScene implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(nullable=false, length=8)
	private String code;

	@Column(nullable=false, length=64)
	private String name;

	@Column(nullable=false, length=128)
	private String serviceModifier;

	public RbScene() {
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

	public String getServiceModifier() {
		return this.serviceModifier;
	}

	public void setServiceModifier(String serviceModifier) {
		this.serviceModifier = serviceModifier;
	}

}