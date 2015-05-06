package ru.korus.tmis.vmp.entities.s11r64;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the rbPacientModel database table.
 * 
 */
@Entity(name = "rbPacientModel")
public class RbPacientModel implements Serializable, ReferenceBook {
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String code;

	@Lob
	private String name;

	//bi-directional many-to-one association to VMPQuotaDetail
	@OneToMany(mappedBy="rbPacientModel")
	private List<VMPQuotaDetail> vmpquotaDetails;

	//bi-directional many-to-one association to RbTreatment
	@OneToMany(mappedBy="rbPacientModel")
	private List<RbTreatment> rbTreatments;

	public RbPacientModel() {
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

	public List<VMPQuotaDetail> getVmpquotaDetails() {
		return this.vmpquotaDetails;
	}

	public void setVmpquotaDetails(List<VMPQuotaDetail> vmpquotaDetails) {
		this.vmpquotaDetails = vmpquotaDetails;
	}

	public List<RbTreatment> getRbTreatments() {
		return this.rbTreatments;
	}

	public void setRbTreatments(List<RbTreatment> rbTreatments) {
		this.rbTreatments = rbTreatments;
	}

}