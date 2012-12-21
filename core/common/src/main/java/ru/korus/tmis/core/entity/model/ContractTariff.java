package ru.korus.tmis.core.entity.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;

@Entity
@Table(name = "Contract_Tariff", catalog = "", schema = "")
@NamedQueries(
        {
                @NamedQuery(name = "ContractTariff.findAll", query = "SELECT ct FROM ContractTariff ct")
        })
@XmlType(name = "service")
@XmlRootElement(name = "service")
public class ContractTariff
        implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Column(name = "deleted")
    private short deleted;

    @Column(name = "master_id" )
    private Integer masterId;

    @Column(name = "eventType_id" )
    private Integer eventTypeId;

    @Column(name = "tariffType" )
    private short tariffType;

    @Column(name = "service_id" )
    private Integer serviceId;

    @Column(name = "tariffCategory_id" )
    private Integer tariffCategoryId;

    @Column(name = "begDate" )
    @Temporal(TemporalType.TIMESTAMP)
    private Date begDate;

    @Column(name = "endDate" )
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @Column(name = "sex" )
    private short sex;

    @Column(name = "age" )
    private String age;

    @Column(name = "age_bu" )
    private short ageBu;

    @Column(name = "age_bc" )
    private short ageBc;

    @Column(name = "age_eu" )
    private short ageEu;

    @Column(name = "age_ec" )
    private short ageEc;

    @Column(name = "unit_id" )
    private Integer unitId;

    @Column(name = "amount" )
    private double amount;

    @Column(name = "uet" )
    private double uet;

    @Column(name = "price" )
    private double price;

    @Column(name = "limitationExceedMode" )
    private Integer limitationExceedMode;

    @Column(name = "limitation" )
    private double limitation;

    @Column(name = "priceEx" )
    private double priceEx;

    @Column(name = "MKB" )
    private String mkb;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public short getDeleted() {
        return deleted;
    }

    public void setDeleted(short deleted) {
        this.deleted = deleted;
    }

    public Integer getMasterId() {
        return masterId;
    }

    public void setMasterId(Integer masterId) {
        this.masterId = masterId;
    }

    public Integer getEventTypeId() {
        return eventTypeId;
    }

    public void setEventTypeId(Integer eventTypeId) {
        this.eventTypeId = eventTypeId;
    }

    public short getTariffType() {
        return tariffType;
    }

    public void setTariffType(short tariffType) {
        this.tariffType = tariffType;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public Integer getTariffCategoryId() {
        return tariffCategoryId;
    }

    public void setTariffCategoryId(Integer tariffCategoryId) {
        this.tariffCategoryId = tariffCategoryId;
    }

    public Date getBegDate() {
        return begDate;
    }

    public void setBegDate(Date begDate) {
        this.begDate = begDate;
    }

    public short getSex() {
        return sex;
    }

    public void setSex(short sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public short getAgeBu() {
        return ageBu;
    }

    public void setAgeBu(short ageBu) {
        this.ageBu = ageBu;
    }

    public short getAgeBc() {
        return ageBc;
    }

    public void setAgeBc(short ageBc) {
        this.ageBc = ageBc;
    }

    public short getAgeEu() {
        return ageEu;
    }

    public void setAgeEu(short ageEu) {
        this.ageEu = ageEu;
    }

    public short getAgeEc() {
        return ageEc;
    }

    public void setAgeEc(short ageEc) {
        this.ageEc = ageEc;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getUet() {
        return uet;
    }

    public void setUet(double uet) {
        this.uet = uet;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Integer getLimitationExceedMode() {
        return limitationExceedMode;
    }

    public void setLimitationExceedMode(Integer limitationExceedMode) {
        this.limitationExceedMode = limitationExceedMode;
    }

    public double getLimitation() {
        return limitation;
    }

    public void setLimitation(double limitation) {
        this.limitation = limitation;
    }

    public double getPriceEx() {
        return priceEx;
    }

    public void setPriceEx(double priceEx) {
        this.priceEx = priceEx;
    }

    public String getMkb() {
        return mkb;
    }

    public void setMkb(String mkb) {
        this.mkb = mkb;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ContractTariff)) {
            return false;
        }

        ContractTariff other = (ContractTariff) object;

        if (this.id == null && other.id == null && this != other) {
            return false;
        }

        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }

        return true;
    }


    @Override
    public String toString() {
        return "ru.korus.tmis.core.entity.model.Action[id=" + id + "]";
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
