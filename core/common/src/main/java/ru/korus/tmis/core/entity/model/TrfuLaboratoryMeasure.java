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
 * The persistent class for the trfulaboratorymeasure database table.
 * 
 */
@Entity
@Table(name="trfuLaboratoryMeasure")
public class TrfuLaboratoryMeasure implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(length=255)
	private String afterOperation;

	@Column(length=255)
	private String beforeOperation;

	@Column(length=255)
	private String duringOperation;

	@Column(length=255)
	private String inProduct;

	//bi-directional many-to-one association to RbTrfuLaboratoryMeasureType
	@ManyToOne
	@JoinColumn(name="trfu_lab_measure_id")
	private RbTrfuLaboratoryMeasureTypes rbTrfuLaboratoryMeasureType;

	//bi-directional many-to-one association to Action
	@ManyToOne
	@JoinColumn(name="action_id", nullable=false)
	private Action action;

	public TrfuLaboratoryMeasure() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAfterOperation() {
		return this.afterOperation;
	}

	public void setAfterOperation(String afterOperation) {
		this.afterOperation = afterOperation;
	}

	public String getBeforeOperation() {
		return this.beforeOperation;
	}

	public void setBeforeOperation(String beforeOperation) {
		this.beforeOperation = beforeOperation;
	}

	public String getDuringOperation() {
		return this.duringOperation;
	}

	public void setDuringOperation(String duringOperation) {
		this.duringOperation = duringOperation;
	}

	public String getInProduct() {
		return this.inProduct;
	}

	public void setInProduct(String inProduct) {
		this.inProduct = inProduct;
	}

	public RbTrfuLaboratoryMeasureTypes getRbtrfulaboratorymeasuretype() {
		return this.rbTrfuLaboratoryMeasureType;
	}

	public void setRbTrfulaboratoryMeasureTypes(RbTrfuLaboratoryMeasureTypes type) {
		this.rbTrfuLaboratoryMeasureType = type;
	}

	public Action getAction() {
		return this.action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

}