package ru.korus.tmis.core.entity.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the rlsInpName database table.
 * 
 */
@Entity
@Table(name="rlsInpName")
public class RlsInpName implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(length=255)
	private String latName;

	@Column(length=255)
	private String name;

	@Column(length=255)
	private String rawName;

	public RlsInpName() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLatName() {
		return this.latName;
	}

	public void setLatName(String latName) {
		this.latName = latName;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRawName() {
		return this.rawName;
	}

	public void setRawName(String rawName) {
		this.rawName = rawName;
	}

}