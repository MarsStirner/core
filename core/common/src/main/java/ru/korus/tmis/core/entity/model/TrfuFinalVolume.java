package ru.korus.tmis.core.entity.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the trfufinalvolume database table.
 * 
 */
@Entity
@Table(name="trfuFinalVolume")
public class TrfuFinalVolume implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(name="anticoagulantInCollect")
	private double anticoagulantInCollect;

	@Column(name="anticoagulantInPlasma")
	private double anticoagulantInPlasma;

	@Column(name="anticoagulantVolume")
	private double anticoagulantVolume;

	@Column(name="inletVolume")
	private double inletVolume;
	
	@Column(name="collectVolume")
	private double collectVolume;

	@Column(name="plasmaVolume")
	private double plasmaVolume;

	@Column(name="time")
	private double time;

	//bi-directional many-to-one association to Action
	@ManyToOne
	@JoinColumn(name="action_id", nullable=false)
	private Action action;

	public TrfuFinalVolume() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getAnticoagulantInCollect() {
		return this.anticoagulantInCollect;
	}

	public void setAnticoagulantInCollect(double anticoagulantInCollect) {
		this.anticoagulantInCollect = anticoagulantInCollect;
	}

	public double getAnticoagulantInPlasma() {
		return this.anticoagulantInPlasma;
	}

	public void setAnticoagulantInPlasma(double anticoagulantInPlasma) {
		this.anticoagulantInPlasma = anticoagulantInPlasma;
	}

	public double getAnticoagulantVolume() {
		return this.anticoagulantVolume;
	}

	public void setAnticoagulantVolume(double anticoagulantVolume) {
		this.anticoagulantVolume = anticoagulantVolume;
	}

	public double getInletVolume() {
		return this.inletVolume;
	}

	public void setInletVolume(double inletVolume) {
		this.inletVolume = inletVolume;
	}

	public double getPlasmaVolume() {
		return this.plasmaVolume;
	}

	public void setPlasmaVolume(double plasmaVolume) {
		this.plasmaVolume = plasmaVolume;
	}

	public double getTime() {
		return this.time;
	}

	public void setTime(double time) {
		this.time = time;
	}

	public Action getAction() {
		return this.action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

    /**
     * @return the collectVolume
     */
    public double getCollectVolume() {
        return collectVolume;
    }

    /**
     * @param collectVolume the collectVolume to set
     */
    public void setCollectVolume(double collectVolume) {
        this.collectVolume = collectVolume;
    }

	
}