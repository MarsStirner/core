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
            query = "SELECT b FROM RlsBalanceOfGood b WHERE b.rlsNomen.id.id = :code AND b.orgStructure.id = :orgStructureId AND b.bestBefore = :date ORDER BY b.rlsNomen.id.version DESC")
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

    @Column(nullable=false)
    private double value;

    //uni-directional many-to-one association to RlsNomen
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name="rlsNomen_id", referencedColumnName="id", nullable=false),
            @JoinColumn(name="rlsNomen_version", referencedColumnName="version", nullable=false)
    })
    private RlsNomen rlsNomen;

    //bi-directional many-to-one association to OrgStructure
    @ManyToOne
    @JoinColumn(name="orgStructure_id", nullable=false)
    private OrgStructure orgStructure;

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

    public OrgStructure getOrgStructure() {
        return this.orgStructure;
    }

    public void setOrgStructure(OrgStructure orgStructure) {
        this.orgStructure = orgStructure;
    }

}