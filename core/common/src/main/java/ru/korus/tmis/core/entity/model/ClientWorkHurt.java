package ru.korus.tmis.core.entity.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ClientWorkHurt database table.
 * 
 */
@Entity
@Table(name="ClientWork_Hurt")
public class ClientWorkHurt implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int id;

	@JoinColumn(name = "hurtType_id")
	private RbHurtType hurtType;

    @ManyToOne
    @JoinColumn(name="master_id", nullable=false)
	private ClientWork clientWork;

	@Column(nullable=false)
	private byte stage;

	public ClientWorkHurt() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public RbHurtType getHurtType() {
		return this.hurtType;
	}

	public void setHurtType(RbHurtType hurtType_id) {
		this.hurtType = hurtType;
	}

	public ClientWork getClientWork() {
		return this.clientWork;
	}

	public void setClientWork(ClientWork clientWork) {
		this.clientWork = clientWork;
	}

	public byte getStage() {
		return this.stage;
	}

	public void setStage(byte stage) {
		this.stage = stage;
	}

}