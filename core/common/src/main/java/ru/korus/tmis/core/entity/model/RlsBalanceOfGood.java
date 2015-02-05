package ru.korus.tmis.core.entity.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the rlsBalanceOfGoods database table.
 * 
 */
@Entity
@Table(name = "rlsBalanceOfGoods")
@NamedQueries(
{ @NamedQuery(name = "RlsBalanceOfGood.findByCodeAndStore",
        query = "SELECT b FROM RlsBalanceOfGood b WHERE b.rlsNomen.id = :code AND b.rbStorage.uuid = :uuid AND b.bestBefore = :date"),
  @NamedQuery(name = "RlsBalanceOfGood.findByCode",
        query = "SELECT b FROM RlsBalanceOfGood b WHERE b.rlsNomen.id = :code ")
})
public class RlsBalanceOfGood implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private int id;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date bestBefore;

    @Column(nullable = false)
    private byte disabled;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDateTime;

    @Column(nullable = false)
    private double value;

    // bi-directional many-to-one association to RlsNomen
    @ManyToOne
    @JoinColumn(name = "rlsNomen_id", nullable = false)
    private RlsNomen rlsNomen;

    @ManyToOne
    @JoinColumn(name = "storage_id")
    private RbStorage rbStorage;

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

    public RbStorage getRbStorage() {
        return rbStorage;
    }

    public void setRbStorage(RbStorage rbStorage) {
        this.rbStorage = rbStorage;
    }
}