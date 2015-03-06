package ru.korus.tmis.core.entity.model;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name="Payment_LocalContract")
@NamedQueries(
        {
                @NamedQuery(name = "PaymentLocalContract.findByCodeContract", query = "SELECT plc FROM PaymentLocalContract plc WHERE plc.eventLocalContract.numberContract = :code")
        }
)
public class PaymentLocalContract implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int id;

	//bi-directional many-to-one association to Event_LocalContract
	@ManyToOne
	@JoinColumn(name="localContract_id", nullable=false)
	private EventLocalContract eventLocalContract;

	//bi-directional many-to-one association to Event_Payment
	@ManyToOne
	@JoinColumn(name="payment_id", nullable=false)
	private EventPayment eventPayment;

	public PaymentLocalContract() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public EventLocalContract getEventLocalContract() {
		return this.eventLocalContract;
	}

	public void setEventLocalContract(EventLocalContract eventLocalContract) {
		this.eventLocalContract = eventLocalContract;
	}

	public EventPayment getEventPayment() {
		return this.eventPayment;
	}

	public void setEventPayment(EventPayment eventPayment) {
		this.eventPayment = eventPayment;
	}

}