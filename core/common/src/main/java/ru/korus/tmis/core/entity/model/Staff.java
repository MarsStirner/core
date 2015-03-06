package ru.korus.tmis.core.entity.model;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@Entity
@Table(name = "Person")
@NamedQueries(
{
        @NamedQuery(name = "Staff.findAll", query = "SELECT s FROM Staff s"),
        @NamedQuery(name = "Staff.findByLogin",
                query = "SELECT s FROM Staff s WHERE s.login = :login AND s.deleted = 0")
})
@XmlType(name = "staff")
@XmlRootElement(name = "staff")
public class Staff implements Serializable {

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

    @Basic(optional = false)
    @Column(name = "modifyDatetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifyDatetime;

    @Basic(optional = false)
    @Column(name = "deleted")
    private boolean deleted;

    @Basic(optional = false)
    @Column(name = "code")
    private String code;

    @Basic(optional = false)
    @Column(name = "federalCode")
    private String federalCode;

    @Basic(optional = false)
    @Column(name = "regionalCode")
    private String regionalCode;

    @Basic(optional = false)
    @Column(name = "lastName")
    private String lastName;

    @Basic(optional = false)
    @Column(name = "firstName")
    private String firstName;

    @Basic(optional = false)
    @Column(name = "patrName")
    private String patrName;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private RbPost post;

    @Column(name = "org_id")
    private Integer orgId;

    @ManyToOne
    @JoinColumn(name = "orgStructure_id")
    private OrgStructure orgStructure;

    @Basic(optional = false)
    @Column(name = "office")
    private String office;

    @Basic(optional = false)
    @Column(name = "office2")
    private String office2;

    @Column(name = "tariffCategory_id")
    private Integer tariffCategoryid;

    @Column(name = "finance_id")
    private Integer financeId;

    @Column(name = "retireDate")
    @Temporal(TemporalType.DATE)
    private Date retireDate;

    @Basic(optional = false)
    @Column(name = "ambPlan")
    private short ambPlan;

    @Basic(optional = false)
    @Column(name = "ambPlan2")
    private short ambPlan2;

    @Basic(optional = false)
    @Column(name = "ambNorm")
    private short ambNorm;

    @Basic(optional = false)
    @Column(name = "homPlan")
    private short homPlan;

    @Basic(optional = false)
    @Column(name = "homPlan2")
    private short homPlan2;

    @Basic(optional = false)
    @Column(name = "homNorm")
    private short homNorm;

    @Basic(optional = false)
    @Column(name = "expPlan")
    private short expPlan;

    @Basic(optional = false)
    @Column(name = "expNorm")
    private short expNorm;

    @Basic(optional = false)
    @Column(name = "login")
    private String login;

    @Basic(optional = false)
    @Column(name = "password")
    private String password;

    @ManyToOne
    @JoinColumn(name = "userProfile_id")
    private Role userProfileId;

    @Basic(optional = false)
    @Column(name = "retired")
    private boolean retired;

    @Basic(optional = false)
    @Column(name = "birthDate")
    @Temporal(TemporalType.DATE)
    private Date birthDate;

    @Basic(optional = false)
    @Column(name = "birthPlace")
    private String birthPlace;

    @Basic(optional = false)
    @Column(name = "sex")
    private short sex;

    @Basic(optional = false)
    @Column(name = "SNILS")
    private String snils;

    @Basic(optional = false)
    @Column(name = "INN")
    private String inn;

    @Basic(optional = false)
    @Column(name = "availableForExternal")
    private int availableForExternal;

    @Basic(optional = false)
    @Column(name = "primaryQuota")
    private short primaryQuota;

    @Basic(optional = false)
    @Column(name = "ownQuota")
    private short ownQuota;

    @Basic(optional = false)
    @Column(name = "consultancyQuota")
    private short consultancyQuota;

    @Basic(optional = false)
    @Column(name = "externalQuota")
    private short externalQuota;

    @Column(name = "lastAccessibleTimelineDate")
    @Temporal(TemporalType.DATE)
    private Date lastAccessibleTimelineDate;

    @Basic(optional = false)
    @Column(name = "timelineAccessibleDays")
    private int timelineAccessibleDays;

    @Basic(optional = false)
    @Column(name = "typeTimeLinePerson")
    private int typeTimeLinePerson;

    @ManyToOne
    @JoinColumn(name = "speciality_id")
    private Speciality speciality;

    @Basic(optional = false)
    @Column(name = "maxCito")
    private int maxCito;

    @Basic(optional = false)
    @Column(name = "maxOverQueue")
    private int maxOverQueue;

    @Basic(optional = true)
    @Column(name = "quotUnit")
    private Integer quoteUnit;
    //

    @ManyToOne
    @JoinColumn(name = "uuid_id")
    private UUID uuid;
    // //////////////////////////////////////////////////////////////////////////
    // Custom mappings
    // //////////////////////////////////////////////////////////////////////////

    @ManyToOne
    @JoinColumn(name = "createPerson_id")
    private Staff createPerson;

    @XmlTransient
    public Staff getCreatePerson() {
        return createPerson;
    }

    public void setCreatePerson(Staff createPerson) {
        this.createPerson = createPerson;
    }

    @Transient
    private Integer createPersonId;

    @XmlElement
    private Integer getCreatePersonId() {
        return createPerson != null ? createPerson.getId() : -1;
    }

    private void setCreatePersonId(Integer createPersonId) {
        this.createPersonId = createPersonId;
    }

    // //////////////////////////////////////////////////////////////////////////

    @ManyToOne
    @JoinColumn(name = "modifyPerson_id")
    private Staff modifyPerson;

    @XmlTransient
    public Staff getModifyPerson() {
        return modifyPerson;
    }

    public void setModifyPerson(Staff modifyPerson) {
        this.modifyPerson = modifyPerson;
    }

    @Transient
    private Integer modifyPersonId;

    @XmlElement
    private Integer getModifyPersonId() {
        return modifyPersonId != null ? modifyPerson.getId() : -1;
    }

    private void setModifyPersonId(Integer modifyPersonId) {
        this.modifyPersonId = modifyPersonId;
    }

    // //////////////////////////////////////////////////////////////////////////

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "Person_Profiles",
            joinColumns = { @JoinColumn(name = "person_id") },
            inverseJoinColumns = { @JoinColumn(name = "userProfile_id") })
    private Set<Role> roles = new LinkedHashSet<Role>();

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(final Set<Role> roles) {
        this.roles = roles;
    }

    // //////////////////////////////////////////////////////////////////////////
    // End of custom mappings
    // //////////////////////////////////////////////////////////////////////////

    public Staff() {
    }

    public Staff(Integer id) {
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

    public Date getModifyDatetime() {
        return modifyDatetime;
    }

    public void setModifyDatetime(Date modifyDatetime) {
        this.modifyDatetime = modifyDatetime;
    }

    public boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFederalCode() {
        return federalCode;
    }

    public void setFederalCode(String federalCode) {
        this.federalCode = federalCode;
    }

    public String getRegionalCode() {
        return regionalCode;
    }

    public void setRegionalCode(String regionalCode) {
        this.regionalCode = regionalCode;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPatrName() {
        return patrName;
    }

    public void setPatrName(String patrName) {
        this.patrName = patrName;
    }

    public RbPost getPost() {
        return post;
    }

    public void setPost(RbPost post) {
        this.post = post;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public OrgStructure getOrgStructure() {
        return orgStructure;
    }

    public void setOrgStructure(OrgStructure orgStructure) {
        this.orgStructure = orgStructure;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public String getOffice2() {
        return office2;
    }

    public void setOffice2(String office2) {
        this.office2 = office2;
    }

    public Integer getTariffCategoryid() {
        return tariffCategoryid;
    }

    public void setTariffCategoryid(Integer tariffCategoryid) {
        this.tariffCategoryid = tariffCategoryid;
    }

    public Integer getFinanceId() {
        return financeId;
    }

    public void setFinanceId(Integer financeId) {
        this.financeId = financeId;
    }

    public Date getRetireDate() {
        return retireDate;
    }

    public void setRetireDate(Date retireDate) {
        this.retireDate = retireDate;
    }

    public short getAmbPlan() {
        return ambPlan;
    }

    public void setAmbPlan(short ambPlan) {
        this.ambPlan = ambPlan;
    }

    public short getAmbPlan2() {
        return ambPlan2;
    }

    public void setAmbPlan2(short ambPlan2) {
        this.ambPlan2 = ambPlan2;
    }

    public short getAmbNorm() {
        return ambNorm;
    }

    public void setAmbNorm(short ambNorm) {
        this.ambNorm = ambNorm;
    }

    public short getHomPlan() {
        return homPlan;
    }

    public void setHomPlan(short homPlan) {
        this.homPlan = homPlan;
    }

    public short getHomPlan2() {
        return homPlan2;
    }

    public void setHomPlan2(short homPlan2) {
        this.homPlan2 = homPlan2;
    }

    public short getHomNorm() {
        return homNorm;
    }

    public void setHomNorm(short homNorm) {
        this.homNorm = homNorm;
    }

    public short getExpPlan() {
        return expPlan;
    }

    public void setExpPlan(short expPlan) {
        this.expPlan = expPlan;
    }

    public short getExpNorm() {
        return expNorm;
    }

    public void setExpNorm(short expNorm) {
        this.expNorm = expNorm;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @XmlTransient
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getUserProfileId() {
        return userProfileId;
    }

    public void setUserProfileId(Role userProfileId) {
        this.userProfileId = userProfileId;
    }

    public boolean getRetired() {
        return retired;
    }

    public void setRetired(boolean retired) {
        this.retired = retired;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public short getSex() {
        return sex;
    }

    public void setSex(short sex) {
        this.sex = sex;
    }

    public String getSnils() {
        return snils;
    }

    public void setSnils(String snils) {
        this.snils = snils;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public int getAvailableForExternal() {
        return availableForExternal;
    }

    public void setAvailableForExternal(int availableForExternal) {
        this.availableForExternal = availableForExternal;
    }

    public short getPrimaryQuota() {
        return primaryQuota;
    }

    public void setPrimaryQuota(short primaryQuota) {
        this.primaryQuota = primaryQuota;
    }

    public short getOwnQuota() {
        return ownQuota;
    }

    public void setOwnQuota(short ownQuota) {
        this.ownQuota = ownQuota;
    }

    public short getConsultancyQuota() {
        return consultancyQuota;
    }

    public void setConsultancyQuota(short consultancyQuota) {
        this.consultancyQuota = consultancyQuota;
    }

    public short getExternalQuota() {
        return externalQuota;
    }

    public void setExternalQuota(short externalQuota) {
        this.externalQuota = externalQuota;
    }

    public Date getLastAccessibleTimelineDate() {
        return lastAccessibleTimelineDate;
    }

    public void setLastAccessibleTimelineDate(Date lastAccessibleTimelineDate) {
        this.lastAccessibleTimelineDate = lastAccessibleTimelineDate;
    }

    public int getTimelineAccessibleDays() {
        return timelineAccessibleDays;
    }

    public void setTimelineAccessibleDays(int timelineAccessibleDays) {
        this.timelineAccessibleDays = timelineAccessibleDays;
    }

    public int getTypeTimeLinePerson() {
        return typeTimeLinePerson;
    }

    public void setTypeTimeLinePerson(int typeTimeLinePerson) {
        this.typeTimeLinePerson = typeTimeLinePerson;
    }

    public Speciality getSpeciality() {
        return speciality;
    }

    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
    }

    public int getMaxCito() {
        return maxCito;
    }

    public void setMaxCito(int maxCito) {
        this.maxCito = maxCito;
    }

    public int getMaxOverQueue() {
        return maxOverQueue;
    }

    public void setMaxOverQueue(int maxOverQueue) {
        this.maxOverQueue = maxOverQueue;
    }

    public Integer getQuoteUnit() {
        return quoteUnit;
    }

    public void setQuoteUnit(Integer quoteUnit) {
        this.quoteUnit = quoteUnit;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
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
        if (!(object instanceof Staff)) {
            return false;
        }
        Staff other = (Staff) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.korus.tmis.core.entity.model.Staff[id=" + id + "]";
    }

    @Transient
    @XmlTransient
    public String getFullName() {
        final StringBuilder sb = new StringBuilder();

        if (getLastName() != null) {
            sb.append(getLastName());
        }
        if (getFirstName() != null) {
            sb.append(' ');
            sb.append(getFirstName());
        }
        if (getPatrName() != null) {
            sb.append(' ');
            sb.append(getPatrName());
        }

        return sb.toString().trim();
    }

    /**
     * Детальное описание врача
     * @return строка с описанием
     */
    public String getInfoString(){
        return new StringBuilder("Staff[id=").append(id)
                .append(" sex=").append(sex)
                .append(" FIO=\"").append(lastName)
                .append(" ").append(firstName)
                .append(" ").append(patrName)
                .append("\" Speciality=").append(speciality != null ? speciality.getName() : "null")
                .append(']').toString();
    }
}
