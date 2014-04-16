package ru.korus.tmis.core.entity.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the rbCashOperation database table.
 * 
 */
@Entity
@Table(name="rbCashOperation")
public class RbCashOperation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(nullable=false, length=16)
	private String code;

	@Column(nullable=false, length=64)
	private String name;

	//bi-directional many-to-one association to EventPayment
	@OneToMany(mappedBy="rbCashOperation")
	private List<EventPayment> eventPayments;

	public RbCashOperation() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
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

	public List<EventPayment> getEventPayments() {
		return this.eventPayments;
	}

	public void setEventPayments(List<EventPayment> eventPayments) {
		this.eventPayments = eventPayments;
	}

}