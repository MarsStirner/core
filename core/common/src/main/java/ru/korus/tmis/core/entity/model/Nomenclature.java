package ru.korus.tmis.core.entity.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "vNomen", catalog = "", schema = "rls")
@NamedQueries(
        {
                @NamedQuery(name = "Nomenclature.findAll", query = "SELECT n FROM Nomenclature n")
        })
public class Nomenclature
        implements Serializable, DbEnumerable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private int id;

    @Basic(optional = false)
    @Column(name = "code")
    private int code;

    @Column(name = "tradeName_id")
    private Integer tradeNameId;

    @Column(name = "INPName_id")
    private Integer inpNameId;

    @Column(name = "form_id")
    private Integer formId;

    @Column(name = "dosage_id")
    private Integer dosageId;

    @Column(name = "filling_id")
    private Integer fillingId;

    @Column(name = "packing_id")
    private Integer packingId;

    @Column(name = "regDate")
    @Temporal(TemporalType.DATE)
    private Date regDate;

    @Column(name = "annDate")
    @Temporal(TemporalType.DATE)
    private Date annDate;

    @Column(name = "tradeName")
    private String tradeName;

    @Column(name = "tradeNameLat")
    private String tradeNameLat;

    @Column(name = "INPName")
    private String iNPName;

    @Column(name = "INPNameLat")
    private String iNPNameLat;

    @Column(name = "form")
    private String form;

    @Column(name = "dosage")
    private String dosage;

    @Column(name = "filling")
    private String filling;

    @Column(name = "packing")
    private String packing;

    @Column(name = "disabledForPrescription")
    private Integer disabledForPrescription;

    public Nomenclature() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Integer getTradeNameId() {
        return tradeNameId;
    }

    public void setTradeNameId(Integer tradeNameId) {
        this.tradeNameId = tradeNameId;
    }

    public Integer getINPNameId() {
        return inpNameId;
    }

    public void setINPNameId(Integer inpNameId) {
        this.inpNameId = inpNameId;
    }

    public Integer getFormId() {
        return formId;
    }

    public void setFormId(Integer formId) {
        this.formId = formId;
    }

    public Integer getDosageId() {
        return dosageId;
    }

    public void setDosageId(Integer dosageId) {
        this.dosageId = dosageId;
    }

    public Integer getFillingId() {
        return fillingId;
    }

    public void setFillingId(Integer fillingId) {
        this.fillingId = fillingId;
    }

    public Integer getPackingId() {
        return packingId;
    }

    public void setPackingId(Integer packingId) {
        this.packingId = packingId;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public Date getAnnDate() {
        return annDate;
    }

    public void setAnnDate(Date annDate) {
        this.annDate = annDate;
    }

    public String getTradeName() {
        return tradeName;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

    public String getTradeNameLat() {
        return tradeNameLat;
    }

    public void setTradeNameLat(String tradeNameLat) {
        this.tradeNameLat = tradeNameLat;
    }

    public String getINPName() {
        return iNPName;
    }

    public void setINPName(String iNPName) {
        this.iNPName = iNPName;
    }

    public String getINPNameLat() {
        return iNPNameLat;
    }

    public void setINPNameLat(String iNPNameLat) {
        this.iNPNameLat = iNPNameLat;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getFilling() {
        return filling;
    }

    public void setFilling(String filling) {
        this.filling = filling;
    }

    public String getPacking() {
        return packing;
    }

    public void setPacking(String packing) {
        this.packing = packing;
    }

    public Integer getDisabledForPrescription() {
        return disabledForPrescription;
    }

    public void setDisabledForPrescription(Integer disabledForPrescription) {
        this.disabledForPrescription = disabledForPrescription;
    }

    private static List<Nomenclature> cache = new ArrayList<Nomenclature>();

    public static List<Nomenclature> getCachedRlsList() {
        return cache;
    }

    @Override
    public void loadEnums(final Collection<Object> enums) {
        List<Nomenclature> newCache = new ArrayList<Nomenclature>(enums.size());
        for (Object e : enums) {
            if (e instanceof Nomenclature) {
                Nomenclature n = (Nomenclature) e;
                newCache.add(n);
            }
        }
        cache = newCache;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += id;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Nomenclature)) {
            return false;
        }
        Nomenclature other = (Nomenclature) object;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.korus.tmis.core.entity.model.Nomenclature[id=" + id + "]";
    }
}
