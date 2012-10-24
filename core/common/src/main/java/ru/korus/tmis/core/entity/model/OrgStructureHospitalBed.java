package ru.korus.tmis.core.entity.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@Entity
@Table(name = "OrgStructure_HospitalBed", catalog = "", schema = "")
@NamedQueries(
        {
                @NamedQuery(name = "OrgStructureHospitalBed.findAll", query = "SELECT o FROM OrgStructureHospitalBed o")
        })
@XmlType(name = "orgStructureHospitalBed")
@XmlRootElement(name = "orgStructureHospitalBed")
public class OrgStructureHospitalBed implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "master_id",
            nullable = false)
    private OrgStructure masterDepartment;

    @Basic(optional = false)
    @Column(name = "idx")
    private int idx;

    @Basic(optional = false)
    @Column(name = "code")
    private String code;

    @Basic(optional = false)
    @Column(name = "name")
    private String name;

    @Basic(optional = false)
    @Column(name = "isPermanent")
    private boolean isPermanent;

    @Column(name = "type_id")
    private Integer typeId;

    @OneToOne
    @JoinColumn(name = "profile_id",
            nullable = false)
    private RbHospitalBedProfile profileId;

    @Basic(optional = false)
    @Column(name = "relief")
    private int relief;

    @Column(name = "schedule_id")
    private Integer scheduleId;

    @Column(name = "begDate")
    @Temporal(TemporalType.DATE)
    private Date begDate;

    @Column(name = "endDate")
    @Temporal(TemporalType.DATE)
    private Date endDate;

    @Basic(optional = false)
    @Column(name = "sex")
    private short sex;

    @Basic(optional = false)
    @Column(name = "age")
    private String age;

    @Basic(optional = false)
    @Column(name = "involution")
    private boolean involution;

    @Column(name = "begDateInvolute")
    @Temporal(TemporalType.DATE)
    private Date begDateInvolute;

    @Column(name = "endDateInvolute")
    @Temporal(TemporalType.DATE)
    private Date endDateInvolute;

    public OrgStructureHospitalBed() {
    }

    public OrgStructureHospitalBed(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public OrgStructure getMasterDepartment() {
        return masterDepartment;
    }

    public void setMasterDepartment(OrgStructure masterDepartment) {
        this.masterDepartment = masterDepartment;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getIsPermanent() {
        return isPermanent;
    }

    public void setIsPermanent(boolean isPermanent) {
        this.isPermanent = isPermanent;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public RbHospitalBedProfile getProfileId() {
        return profileId;
    }

    public void setProfileId(RbHospitalBedProfile profileId) {
        this.profileId = profileId;
    }

    public int getRelief() {
        return relief;
    }

    public void setRelief(int relief) {
        this.relief = relief;
    }

    public Integer getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Integer scheduleId) {
        this.scheduleId = scheduleId;
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

    public boolean getInvolution() {
        return involution;
    }

    public void setInvolution(boolean involution) {
        this.involution = involution;
    }

    public Date getBegDateInvolute() {
        return begDateInvolute;
    }

    public void setBegDateInvolute(Date begDateInvolute) {
        this.begDateInvolute = begDateInvolute;
    }

    public Date getEndDateInvolute() {
        return endDateInvolute;
    }

    public void setEndDateInvolute(Date endDateInvolute) {
        this.endDateInvolute = endDateInvolute;
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
        if (!(object instanceof OrgStructureHospitalBed)) {
            return false;
        }
        OrgStructureHospitalBed other = (OrgStructureHospitalBed) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.korus.tmis.core.entity.model.OrgStructureHospitalBed[id=" + id + "]";
    }
}
