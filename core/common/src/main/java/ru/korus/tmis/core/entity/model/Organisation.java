package ru.korus.tmis.core.entity.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@Entity
@Table(name = "Organisation")
@XmlType(name = "organisation")
@XmlRootElement(name = "organisation")
public class Organisation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

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

    @Basic(optional = false)
    @Column(name = "deleted")
    private boolean deleted;

    @Basic(optional = false)
    @Column(name = "fullName")
    private String fullName;

    @Basic(optional = false)
    @Column(name = "shortName")
    private String shortName;

    @Basic(optional = false)
    @Column(name = "title")
    private String title;

    @Column(name = "net_id")
    private Integer netId;

    @Basic(optional = false)
    @Column(name = "infisCode")
    private String infisCode;

    @Basic(optional = false)
    @Column(name = "obsoleteInfisCode")
    private String obsoleteInfisCode;

    @Basic(optional = false)
    @Column(name = "OKVED")
    private String okved;

    @Basic(optional = false)
    @Column(name = "INN")
    private String inn;

    @Basic(optional = false)
    @Column(name = "KPP")
    private String kpp;

    @Basic(optional = false)
    @Column(name = "OGRN")
    private String ogrn;

    @Basic(optional = false)
    @Column(name = "OKATO")
    private String okato;

    @Basic(optional = false)
    @Column(name = "OKPF_code")
    private String okpfCode;

    @Column(name = "OKPF_id")
    private Integer okpfId;

    @Basic(optional = false)
    @Column(name = "OKFS_code")
    private int okfsCode;

    @Column(name = "OKFS_id")
    private Integer okfsId;

    @Basic(optional = false)
    @Column(name = "OKPO")
    private String okpo;

    @Basic(optional = false)
    @Column(name = "FSS")
    private String fss;

    @Basic(optional = false)
    @Column(name = "region")
    private String region;

    @Basic(optional = false)
    @Column(name = "Address")
    private String address;

    @Basic(optional = false)
    @Column(name = "chief")
    private String chief;

    @Basic(optional = false)
    @Column(name = "phone")
    private String phone;

    @Basic(optional = false)
    @Column(name = "accountant")
    private String accountant;

    @Basic(optional = false)
    @Column(name = "isInsurer")
    private boolean isInsurer;

    @Basic(optional = false)
    @Column(name = "compulsoryServiceStop")
    private boolean compulsoryServiceStop;

    @Basic(optional = false)
    @Column(name = "voluntaryServiceStop")
    private boolean voluntaryServiceStop;

    @Basic(optional = false)
    @Column(name = "area")
    private String area;

    @Basic(optional = false)
    @Column(name = "isHospital")
    private boolean isHospital;

    @Basic(optional = false)
    @Column(name = "notes")
    private String notes;

    @Column(name = "head_id")
    private Integer headId;

    @Basic(optional = false)
    @Column(name = "miacCode")
    private String miacCode;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "uuid_id")
//    @Transient
    private UUID uuid;

    @Column(name = "OID")
    private String oid;


    public Organisation() {
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Organisation(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getNetId() {
        return netId;
    }

    public void setNetId(Integer netId) {
        this.netId = netId;
    }

    public String getInfisCode() {
        return infisCode;
    }

    public void setInfisCode(String infisCode) {
        this.infisCode = infisCode;
    }

    public String getObsoleteInfisCode() {
        return obsoleteInfisCode;
    }

    public void setObsoleteInfisCode(String obsoleteInfisCode) {
        this.obsoleteInfisCode = obsoleteInfisCode;
    }

    public String getOkved() {
        return okved;
    }

    public void setOkved(String okved) {
        this.okved = okved;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public String getKpp() {
        return kpp;
    }

    public void setKpp(String kpp) {
        this.kpp = kpp;
    }

    public String getOgrn() {
        return ogrn;
    }

    public void setOgrn(String ogrn) {
        this.ogrn = ogrn;
    }

    public String getOkato() {
        return okato;
    }

    public void setOkato(String okato) {
        this.okato = okato;
    }

    public String getOKPFcode() {
        return okpfCode;
    }

    public void setOKPFcode(String okpfCode) {
        this.okpfCode = okpfCode;
    }

    public Integer getOKPFid() {
        return okpfId;
    }

    public void setOKPFid(Integer okpfId) {
        this.okpfId = okpfId;
    }

    public int getOKFScode() {
        return okfsCode;
    }

    public void setOKFScode(int okfsCode) {
        this.okfsCode = okfsCode;
    }

    public Integer getOKFSid() {
        return okfsId;
    }

    public void setOKFSid(Integer okfsId) {
        this.okfsId = okfsId;
    }

    public String getOkpo() {
        return okpo;
    }

    public void setOkpo(String okpo) {
        this.okpo = okpo;
    }

    public String getFss() {
        return fss;
    }

    public void setFss(String fss) {
        this.fss = fss;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getChief() {
        return chief;
    }

    public void setChief(String chief) {
        this.chief = chief;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAccountant() {
        return accountant;
    }

    public void setAccountant(String accountant) {
        this.accountant = accountant;
    }

    public boolean getIsInsurer() {
        return isInsurer;
    }

    public void setIsInsurer(boolean isInsurer) {
        this.isInsurer = isInsurer;
    }

    public boolean getCompulsoryServiceStop() {
        return compulsoryServiceStop;
    }

    public void setCompulsoryServiceStop(boolean compulsoryServiceStop) {
        this.compulsoryServiceStop = compulsoryServiceStop;
    }

    public boolean getVoluntaryServiceStop() {
        return voluntaryServiceStop;
    }

    public void setVoluntaryServiceStop(boolean voluntaryServiceStop) {
        this.voluntaryServiceStop = voluntaryServiceStop;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public boolean getIsHospital() {
        return isHospital;
    }

    public void setIsHospital(boolean isHospital) {
        this.isHospital = isHospital;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getHeadId() {
        return headId;
    }

    public void setHeadId(Integer headId) {
        this.headId = headId;
    }

    public String getMiacCode() {
        return miacCode;
    }

    public void setMiacCode(String miacCode) {
        this.miacCode = miacCode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Organisation)) {
            return false;
        }
        Organisation other = (Organisation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.korus.tmis.core.entity.model.Organisation[id=" + id + "]";
    }

    public String getOid() {
        return oid;
    }
}
