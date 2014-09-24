package ru.korus.tmis.core.entity.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

/**
 * The persistent class for the rlsNomen database table.
 * 
 */
@Entity
@Table(name = "rlsNomen")

public class RlsNomen implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(unique = true, nullable = false)
    private Integer id;

    @Column(length = 128)
    private String dosageValue;

    @Temporal(TemporalType.DATE)
    private Date regDate;

    // bi-directional many-to-one association to RlsBalanceOfGood
    @OneToMany(mappedBy = "rlsNomen")
    private List<RlsBalanceOfGood> rlsBalanceOfGoods;

    // uni-directional many-to-one association to RbUnit
	@ManyToOne(cascade=CascadeType.PERSIST)
    @JoinColumn(name = "unit_id")
    private RbUnit unit;

    // uni-directional many-to-one association to RbUnit
	@ManyToOne(cascade=CascadeType.PERSIST)
    @JoinColumn(name = "dosageUnit_id")
    private RbUnit dosageUnit;

    // bi-directional many-to-one association to RlsActMatter
    @ManyToOne(cascade=CascadeType.PERSIST)
    @JoinColumn(name = "actMatters_id")
    private RlsActMatter rlsActMatter;

	//uni-directional many-to-one association to RlsFilling
	@ManyToOne(cascade=CascadeType.PERSIST)
    @JoinColumn(name = "filling_id")
    private RlsFilling rlsFilling;

    // bi-directional many-to-one association to RlsForm
	@ManyToOne(cascade=CascadeType.PERSIST)
    @JoinColumn(name = "form_id")
    private RlsForm rlsForm;

    // bi-directional many-to-one association to RlsPacking
	@ManyToOne(cascade=CascadeType.PERSIST)
    @JoinColumn(name = "packing_id")
    private RlsPacking rlsPacking;

    // bi-directional many-to-one association to RlsTradeName
	@ManyToOne(cascade=CascadeType.PERSIST)
    @JoinColumn(name = "tradeName_id", nullable = false)
    private RlsTradeName rlsTradeName;

    @Column(nullable = true)
    private Integer drugLifetime;

    public RlsNomen() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDosageValue() {
        return this.dosageValue;
    }

    public void setDosageValue(String dosageValue) {
        this.dosageValue = dosageValue;
    }

    public Integer getDrugLifetime() {
        return this.drugLifetime;
    }

    public void setDrugLifetime(Integer drugLifetime) {
        this.drugLifetime = drugLifetime;
    }

    public Date getRegDate() {
        return this.regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public List<RlsBalanceOfGood> getRlsBalanceOfGoods() {
        return this.rlsBalanceOfGoods;
    }

    public void setRlsBalanceOfGoods(List<RlsBalanceOfGood> rlsBalanceOfGoods) {
        this.rlsBalanceOfGoods = rlsBalanceOfGoods;
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

    public RlsActMatter getRlsActMatter() {
        return this.rlsActMatter;
    }

    public void setRlsActMatter(RlsActMatter rlsActMatter) {
        this.rlsActMatter = rlsActMatter;
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