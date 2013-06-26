package ru.korus.tmis.core.entity.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the rlsPacking database table.
 * 
 */
@Entity
@Table(name="rlsPacking")
public class RlsPacking implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(nullable=false)
	private boolean disabledForPrescription;

	@Column(length=128)
	private String name;

	public RlsPacking() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean getDisabledForPrescription() {
		return this.disabledForPrescription;
	}

	public void setDisabledForPrescription(boolean disabledForPrescription) {
		this.disabledForPrescription = disabledForPrescription;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}