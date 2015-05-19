package ru.korus.tmis.core.ext.entities.s11r64;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the rbTreatmentType database table.
 * 
 */
@Entity
@Table(name = "rbTreatmentType")
public class RbTreatmentType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String code;

	private String name;

	//bi-directional many-to-one association to RbTreatment
	@OneToMany(mappedBy="rbTreatmentType")
	private List<RbTreatment> rbTreatments;

	public RbTreatmentType() {
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

	public List<RbTreatment> getRbTreatments() {
		return this.rbTreatments;
	}

	public void setRbTreatments(List<RbTreatment> rbTreatments) {
		this.rbTreatments = rbTreatments;
	}

}