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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the rlsNomen database table.
 * 
 */
@Entity
@Table(name = "rlsNomen")
public class RlsNomen implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private int id;

    @Temporal(TemporalType.DATE)
    private Date annDate;

    @Column(nullable = false)
    private int code;

    @Column(nullable = false)
    private boolean disabledForPrescription;

    @ManyToOne
    @JoinColumn(name = "dosage_id")
    private RlsDosage rlsDosage;

    @ManyToOne
    @JoinColumn(name = "filling_id")
    private RlsFilling rlsFilling;

    @ManyToOne
    @JoinColumn(name = "form_id")
    private RlsForm rlsForm;

    @ManyToOne
    @JoinColumn(name = "INPName_id")
    private RlsInpName rlsInpName;

    @ManyToOne
    @JoinColumn(name = "packing_id")
    private RlsPacking rlsPacking;

    @Temporal(TemporalType.DATE)
    private Date regDate;

    @ManyToOne
    @JoinColumn(name = "tradeName_id")
    private RlsTradeName rlsTradeName;

    @Column(nullable=false)
    @JoinColumn(name = "version")
    private Integer version = 0;


    public RlsNomen() {
    }

    /**
     * @return the rlsDosage
     */
    public RlsDosage getRlsDosage() {
        return rlsDosage;
    }

    /**
     * @param rlsDosage
     *            the rlsDosage to set
     */
    public void setRlsDosage(RlsDosage rlsDosage) {
        this.rlsDosage = rlsDosage;
    }

    /**
     * @return the rlsFilling
     */
    public RlsFilling getRlsFilling() {
        return rlsFilling;
    }

    /**
     * @param rlsFilling
     *            the rlsFilling to set
     */
    public void setRlsFilling(RlsFilling rlsFilling) {
        this.rlsFilling = rlsFilling;
    }

    /**
     * @return the rlsForm
     */
    public RlsForm getRlsForm() {
        return rlsForm;
    }

    /**
     * @param rlsForm
     *            the rlsForm to set
     */
    public void setRlsForm(RlsForm rlsForm) {
        this.rlsForm = rlsForm;
    }

    /**
     * @return the rlsInpName
     */
    public RlsInpName getRlsInpName() {
        return rlsInpName;
    }

    /**
     * @param rlsInpName
     *            the rlsInpName to set
     */
    public void setRlsInpName(RlsInpName rlsInpName) {
        this.rlsInpName = rlsInpName;
    }

    /**
     * @return the rlsPacking
     */
    public RlsPacking getRlsPacking() {
        return rlsPacking;
    }

    /**
     * @param rlsPacking
     *            the rlsPacking to set
     */
    public void setRlsPacking(RlsPacking rlsPacking) {
        this.rlsPacking = rlsPacking;
    }

    /**
     * @return the rlsTradeName
     */
    public RlsTradeName getRlsTradeName() {
        return rlsTradeName;
    }

    /**
     * @param rlsTradeName
     *            the rlsTradeName to set
     */
    public void setRlsTradeName(RlsTradeName rlsTradeName) {
        this.rlsTradeName = rlsTradeName;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getAnnDate() {
        return this.annDate;
    }

    public void setAnnDate(Date annDate) {
        this.annDate = annDate;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean getDisabledForPrescription() {
        return this.disabledForPrescription;
    }

    public void setDisabledForPrescription(boolean disabledForPrescription) {
        this.disabledForPrescription = disabledForPrescription;
    }

    public Date getRegDate() {
        return this.regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

}