package ru.korus.tmis.core.entity.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "rbService")
@NamedQueries(
        {
                @NamedQuery(name = "rbService.findAll", query = "SELECT s FROM RbService s"),
                @NamedQuery(name = "rbService.findByCode", query = "SELECT s FROM RbService s WHERE s.code = :code")
        })
@XmlType(name = "service")
@XmlRootElement(name = "service")
public class RbService
        implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "eisLegacy")
    private short eisLegacy;

    @Column(name = "nomenclatureLegacy")
    private short nomenclatureLegacy;

    @Column(name = "license")
    private short license;

    @Column(name = "infis")
    private String infis;

    @Column(name = "begDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date begDate;

    @Column(name = "endDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @ManyToOne
    @JoinColumn(name = "medicalAidProfile_id")
    private RbMedicalAidProfile medicalAidProfile;

    @Column(name = "adultUetDoctor")
    private double adultUetDoctor;

    @Column(name = "adultUetAverageMedWorker")
    private double adultUetAverageMedWorker;

    @Column(name = "childUetDoctor")
    private double childUetDoctor;

    @Column(name = "childUetAverageMedWorker")
    private double childUetAverageMedWorker;

    @ManyToOne
    @JoinColumn(name = "rbMedicalKind_id")
    private RbMedicalKind medicalKind;

    @Column(name = "departCode")
    private String departCode;

    @Column(name = "UET")
    private Double uet;


    public RbService(Integer id) {
        this.id = id;
    }

    public RbService() {
    }

    public double getChildUetAverageMedWorker() {
        return childUetAverageMedWorker;
    }

    public void setChildUetAverageMedWorker(double childUetAverageMedWorker) {
        this.childUetAverageMedWorker = childUetAverageMedWorker;
    }

    public short getNomenclatureLegacy() {
        return nomenclatureLegacy;
    }

    public void setNomenclatureLegacy(short nomenclatureLegacy) {
        this.nomenclatureLegacy = nomenclatureLegacy;
    }

    public short getLicense() {
        return license;
    }

    public void setLicense(short license) {
        this.license = license;
    }

    public String getInfis() {
        return infis;
    }

    public void setInfis(String infis) {
        this.infis = infis;
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

    public RbMedicalAidProfile getMedicalAidProfile() {
        return medicalAidProfile;
    }

    public void setMedicalAidProfile(RbMedicalAidProfile medicalAidProfile) {
        this.medicalAidProfile = medicalAidProfile;
    }

    public double getAdultUetDoctor() {
        return adultUetDoctor;
    }

    public void setAdultUetDoctor(double adultUetDoctor) {
        this.adultUetDoctor = adultUetDoctor;
    }

    public double getAdultUetAverageMedWorker() {
        return adultUetAverageMedWorker;
    }

    public void setAdultUetAverageMedWorker(double adultUetAverageMedWorker) {
        this.adultUetAverageMedWorker = adultUetAverageMedWorker;
    }

    public double getChildUetDoctor() {
        return childUetDoctor;
    }

    public void setChildUetDoctor(double childUetDoctor) {
        this.childUetDoctor = childUetDoctor;
    }

    public short getEisLegacy() {
        return eisLegacy;
    }

    public void setEisLegacy(short eisLegacy) {
        this.eisLegacy = eisLegacy;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public RbMedicalKind getMedicalKind() {
        return medicalKind;
    }

    public void setMedicalKind(RbMedicalKind medicalKind) {
        this.medicalKind = medicalKind;
    }

    public Double getUet() {
        return uet;
    }

    public void setUet(Double uet) {
        this.uet = uet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RbService that = (RbService) o;

        if (!id.equals(that.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return this.getClass().getName()+"[id=" + id + "]";
    }

    public String getDepartCode() {
        return departCode;
    }

    public void setDepartCode(String departCode) {
        this.departCode = departCode;
    }
}