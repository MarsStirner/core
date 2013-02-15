package ru.korus.tmis.core.entity.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * The persistent class for the trfuorderissueresult database table.
 * 
 */
@Entity
@Table(name="TrfuOrderIssueResult")
public class TrfuOrderIssueResult implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(name="comp_number", length=40)
	private String compNumber;

	@Column(name="trfu_blood_comp")
	private Integer trfuCompId;

	@Column(name="trfu_donor_id")
	private Integer trfuDonorId;
	
	@Column(name="dose_count")
	private Double doseCount;

	private Integer volume;

	@ManyToOne
	@JoinColumn(name="action_id", nullable=false)
	private Action action;

	@ManyToOne
	@JoinColumn(name="comp_type_id")
	private RbTrfuBloodComponentType rbBloodComponentType;

	@ManyToOne
	@JoinColumn(name="blood_type_id")
	private RbBloodType rbBloodType;

	public TrfuOrderIssueResult() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCompNumber() {
		return this.compNumber;
	}

	public void setCompNumber(String compNumber) {
		this.compNumber = compNumber;
	}

	public int getTrfuCompId() {
		return this.trfuCompId;
	}

	public void setTrfuCompId(Integer trfuCompId) {
		this.trfuCompId = trfuCompId;
	}

	public Double getDoseCount() {
		return this.doseCount;
	}

	public void setDoseCount(Double doseCount) {
		this.doseCount = doseCount;
	}

	public Integer getVolume() {
		return this.volume;
	}

	public void setVolume(Integer volume) {
		this.volume = volume;
	}

	public Action getAction() {
		return this.action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public RbTrfuBloodComponentType getRbBloodComponentType() {
		return this.rbBloodComponentType;
	}

	public void setRbBloodComponentType(RbTrfuBloodComponentType rbBloodComponentType) {
		this.rbBloodComponentType = rbBloodComponentType;
	}

	public RbBloodType getRbBloodType() {
		return this.rbBloodType;
	}

	public void setRbBloodType(RbBloodType rbBloodType) {
		this.rbBloodType = rbBloodType;
	}

    /**
     * @return the trfuDonorId
     */
    public Integer getTrfuDonorId() {
        return trfuDonorId;
    }

    /**
     * @param trfuDonorId the trfuDonorId to set
     */
    public void setTrfuDonorId(Integer trfuDonorId) {
        this.trfuDonorId = trfuDonorId;
    }
	
	

}
