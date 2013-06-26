package ru.korus.tmis.core.entity.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the rlsTradeName database table.
 * 
 */
@Entity
@Table(name="rlsTradeName")
public class RlsTradeName implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(length=255)
	private String latName;

	@Column(length=255)
	private String name;

	public RlsTradeName() {
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

}