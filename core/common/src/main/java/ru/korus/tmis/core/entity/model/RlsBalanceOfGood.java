package ru.korus.tmis.core.entity.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the rlsBalanceOfGoods database table.
 * 
 */
@Entity
@Table(name="rlsBalanceOfGoods")
@NamedQueries(
        { @NamedQuery(name = "RlsBalanceOfGood.findByCodeAndStore",
            query = "SELECT b FROM RlsBalanceOfGood b WHERE b.rlsNomen.id = :code AND b.storageUuid = :uuid AND b.bestBefore = :date")
        })
public class RlsBalanceOfGood implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int id;

	@Temporal(TemporalType.DATE)
	@Column(nullable=false)
	private Date bestBefore;

	@Column(nullable=false)
	private byte disabled;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updateDateTime;

	@Column(nullable=false)
	private double value;

    @Column(name = "storage_uuid", nullable=false)
	private String  storageUuid;

	//bi-directional many-to-one association to RlsNomen
	@ManyToOne
	@JoinColumn(name="rlsNomen_id", nullable=false)
	private RlsNomen rlsNomen;

	public RlsBalanceOfGood() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getBestBefore() {
		return this.bestBefore;
	}

	public void setBestBefore(Date bestBefore) {
		this.bestBefore = bestBefore;
	}

	public byte getDisabled() {
		return this.disabled;
	}

	public void setDisabled(byte disabled) {
		this.disabled = disabled;
	}

	public Date getUpdateDateTime() {
		return this.updateDateTime;
	}

	public void setUpdateDateTime(Date updateDateTime) {
		this.updateDateTime = updateDateTime;
	}

	public double getValue() {
		return this.value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public RlsNomen getRlsNomen() {
		return this.rlsNomen;
	}

	public void setRlsNomen(RlsNomen rlsNomen) {
		this.rlsNomen = rlsNomen;
	}

    public String getStorageUuid() {
        return storageUuid;
    }

    public void setStorageUuid(String storageUuid) {
        this.storageUuid = storageUuid;
    }
}