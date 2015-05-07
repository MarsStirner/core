package ru.korus.tmis.core.ext.entities.s11r64;

import ru.korus.tmis.core.ext.entities.s11r64.vmp.VMPQuotaDetail;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the rbTreatment database table.
 * 
 */
@Entity
@Table(name = "rbTreatment")
public class RbTreatment implements Serializable, ReferenceBook {
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String code;

	@Lob
	private String name;

	//bi-directional many-to-one association to VMPQuotaDetail
	@OneToMany(mappedBy="rbTreatment")
	private List<VMPQuotaDetail> vmpquotaDetails;

	//bi-directional many-to-one association to RbTreatmentType
	@ManyToOne
	@JoinColumn(name="treatmentType_id")
	private RbTreatmentType rbTreatmentType;

	public RbTreatment() {
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

	public RbTreatmentType getRbTreatmentType() {
		return this.rbTreatmentType;
	}

	public void setRbTreatmentType(RbTreatmentType rbTreatmentType) {
		this.rbTreatmentType = rbTreatmentType;
	}

}