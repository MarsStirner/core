package ru.korus.tmis.core.entity.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@Entity
@Table(name = "Contract_Tariff")
@NamedQueries(
        {
                @NamedQuery(name = "ContractTariff.findAll", query = "SELECT ct FROM ContractTariff ct"),
                @NamedQuery(name = "ContractTariff.removeBeforeDate",
                        query = "DELETE FROM ContractTariff ct WHERE ct.endDate < :removeDate")
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

    @Column(name = "master_id")
    private Integer masterId;

    @Column(name = "eventType_id")
    private Integer eventTypeId;

    @Column(name = "tariffType")
    private short tariffType;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private RbService service;

    @Column(name = "tariffCategory_id")
    private Integer tariffCategoryId;

    @Column(name = "begDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date begDate;

    @Column(name = "endDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @Column(name = "sex")
    private short sex;

    @Column(name = "age")
    private String age;

    @Column(name = "age_bu")
    private short ageBu;

    @Column(name = "age_bc")
    private short ageBc;

    @Column(name = "age_eu")
    private short ageEu;

    @Column(name = "age_ec")
    private short ageEc;

    @ManyToOne
    @JoinColumn(name = "unit_id")
    private RbMedicalAidUnit unit;

    @Column(name = "amount")
    private double amount;

    @Column(name = "uet")
    private double uet;

    @Column(name = "price")
    private double price;

    @Column(name = "limitationExceedMode")
    private int limitationExceedMode;

    @Column(name = "limitation")
    private double limitation;

    @Column(name = "priceEx")
    private double priceEx;

    @Column(name = "MKB")
    private String mkb;

    @ManyToOne
    @JoinColumn(name = "rbServiceFinance_id")
    private RbServiceFinance serviceFinance;

    @Basic(optional = false)
    @Column(name = "createDatetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDatetime;

    @ManyToOne
    @JoinColumn(name = "createPerson_id")
    private Staff createPerson;

    @Basic(optional = false)
    @Column(name = "modifyDatetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifyDatetime;

    @ManyToOne
    @JoinColumn(name = "modifyPerson_id")
    private Staff modifyPerson;

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

    public RbService getService() {
        return service;
    }

    public void setService(RbService service) {
        this.service = service;
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

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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

    public RbMedicalAidUnit getUnit() {
        return unit;
    }

    public void setUnit(RbMedicalAidUnit unit) {
        this.unit = unit;
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

    public RbServiceFinance getServiceFinance() {
        return serviceFinance;
    }

    public void setServiceFinance(RbServiceFinance serviceFinance) {
        this.serviceFinance = serviceFinance;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public Staff getCreatePerson() {
        return createPerson;
    }

    public void setCreatePerson(Staff createPerson) {
        this.createPerson = createPerson;
    }

    public Date getModifyDatetime() {
        return modifyDatetime;
    }

    public void setModifyDatetime(Date modifyDatetime) {
        this.modifyDatetime = modifyDatetime;
    }

    public Staff getModifyPerson() {
        return modifyPerson;
    }

    public void setModifyPerson(Staff modifyPerson) {
        this.modifyPerson = modifyPerson;
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

    public String getInfo() {
        StringBuilder info = new StringBuilder("ContractTariff[id=").append(id != null ? id : -1)
                .append(", master_id=").append(masterId)
                .append(", eventType=").append(eventTypeId)
                .append(", tariffType=").append(tariffType)
                .append(", service=").append(service != null ? service.getId() : "NULL")
                .append(", begDate=").append(begDate)
                .append(", endDate=").append(endDate)
                .append(", sex=").append(sex)
                .append(", age=").append(age)
                .append(", unit=").append(unit != null ? unit.getId() : "NULL")
                .append(", amount=").append(amount)
                .append(", uet=").append(uet)
                .append(", price=").append(price)
                .append(", amount=").append(amount)
                .append(", mkb=").append(mkb)
                .append(", serviceFinance_id = ").append(serviceFinance != null ? serviceFinance.getId(): "NULL")
                .append("]");
        return info.toString();    }
}
