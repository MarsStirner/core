package ru.korus.tmis.core.entity.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the rlsNomen database table.
 * 
 */
@Entity
@Table(name="rlsNomen")
@NamedQueries(
        {
                @NamedQuery(name = "RlsNomen.findByCode", query = "SELECT n FROM RlsNomen n WHERE n.id.id = :code ORDER BY n.id.version DESC")
        })
public class RlsNomen implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private RlsNomenPK id;

	@Temporal(TemporalType.DATE)
	private Date annDate;

	private Double dosageValue;

	@Temporal(TemporalType.DATE)
	private Date regDate;

	//uni-directional many-to-one association to RbUnit
	@ManyToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="unit_id")
	private RbUnit unit;

	//uni-directional many-to-one association to RbUnit
	@ManyToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="dosageUnit_id")
	private RbUnit dosageUnit;

	//uni-directional many-to-one association to RlsFilling
	@ManyToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="filling_id")
	private RlsFilling rlsFilling;

	//bi-directional many-to-one association to RlsForm
	@ManyToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="form_id")
	private RlsForm rlsForm;

	//bi-directional many-to-one association to RlsPacking
	@ManyToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="packing_id")
	private RlsPacking rlsPacking;

	//bi-directional many-to-one association to RlsTradeName
	@ManyToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="tradeName_id", nullable=false)
	private RlsTradeName rlsTradeName;

	public RlsNomen() {
	}

	public RlsNomenPK getId() {
		return this.id;
	}

	public void setId(RlsNomenPK id) {
		this.id = id;
	}

	public Date getAnnDate() {
		return this.annDate;
	}

	public void setAnnDate(Date annDate) {
		this.annDate = annDate;
	}

	public Double getDosageValue() {
		return this.dosageValue;
	}

	public void setDosageValue(Double dosageValue) {
		this.dosageValue = dosageValue;
	}

	public Date getRegDate() {
		return this.regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public RbUnit getUnit() {
		return this.unit;
	}

	public void setUnit(RbUnit unit) {
		this.unit = unit;
	}

	public RbUnit getDosageUnit() {
		return this.dosageUnit;
	}

	public void setDosageUnit(RbUnit dosageUnit) {
		this.dosageUnit = dosageUnit;
	}

	public RlsFilling getRlsFilling() {
		return this.rlsFilling;
	}

	public void setRlsFilling(RlsFilling rlsFilling) {
		this.rlsFilling = rlsFilling;
	}

	public RlsForm getRlsForm() {
		return this.rlsForm;
	}

	public void setRlsForm(RlsForm rlsForm) {
		this.rlsForm = rlsForm;
	}

	public RlsPacking getRlsPacking() {
		return this.rlsPacking;
	}

	public void setRlsPacking(RlsPacking rlsPacking) {
		this.rlsPacking = rlsPacking;
	}

	public RlsTradeName getRlsTradeName() {
		return this.rlsTradeName;
	}

	public void setRlsTradeName(RlsTradeName rlsTradeName) {
		this.rlsTradeName = rlsTradeName;
	}


}