package ru.korus.tmis.core.entity.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@Entity
@Table(name = "Person_TimeTemplate", catalog = "", schema = "")
@NamedQueries(
        {
                @NamedQuery(name = "PersonTimeTemplate.findAll", query = "SELECT ptt FROM PersonTimeTemplate ptt")
        })
@XmlType(name = "personTimeTemplate")
@XmlRootElement(name = "personTimeTemplate")
public class PersonTimeTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Column(name = "createDatetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDatetime;

    @OneToOne
    @JoinColumn(name = "createPerson_id",
            nullable = false)
    private Staff createPerson;

    @Column(name = "modifyDatetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifyDatetime;

    @OneToOne
    @JoinColumn(name = "modifyPerson_id",
            nullable = false)
    private Staff modifyPerson;

    @Basic(optional = false)
    @Column(name = "deleted")
    private int deleted;

    @OneToOne
    @JoinColumn(name = "master_id",
            nullable = false)
    private Staff masterPerson;

    @Basic(optional = false)
    @Column(name = "idx")
    private int idx;

    @Column(name = "ambBegTime")
    @Temporal(TemporalType.TIME)
    private Date ambBegTime;

    @Column(name = "ambEndTime")
    @Temporal(TemporalType.TIME)
    private Date ambEndTime;

    @Basic(optional = false)
    @Column(name = "ambPlan")
    private int ambPlan;

    @Basic(optional = false)
    @Column(name = "office")
    private String office;

    @Column(name = "ambBegTime2")
    @Temporal(TemporalType.TIME)
    private Date ambBegTime2;

    @Column(name = "ambEndTime2")
    @Temporal(TemporalType.TIME)
    private Date ambEndTime2;

    @Basic(optional = false)
    @Column(name = "ambPlan2")
    private int ambPlan2;

    @Basic(optional = false)
    @Column(name = "office2")
    private String office2;

    @Column(name = "homBegTime")
    @Temporal(TemporalType.TIME)
    private Date homBegTime;

    @Column(name = "homEndTime")
    @Temporal(TemporalType.TIME)
    private Date homEndTime;

    @Basic(optional = false)
    @Column(name = "homPlan")
    private int homPlan;

    @Column(name = "homBegTime2")
    @Temporal(TemporalType.TIME)
    private Date homBegTime2;

    @Column(name = "homEndTime2")
    @Temporal(TemporalType.TIME)
    private Date homEndTime2;

    @Basic(optional = false)
    @Column(name = "homPlan2")
    private int homPlan2;

    public PersonTimeTemplate() {
    }

    public PersonTimeTemplate(Integer id) {
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

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    public Staff getMasterPerson() {
        return masterPerson;
    }

    public void setMasterPerson(Staff masterPerson) {
        this.masterPerson = masterPerson;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public Date getAmbBegTime() {
        return ambBegTime;
    }

    public void setAmbBegTime(Date ambBegTime) {
        this.ambBegTime = ambBegTime;
    }

    public Date getAmbEndTime() {
        return ambEndTime;
    }

    public void setAmbEndTime(Date ambEndTime) {
        this.ambEndTime = ambEndTime;
    }

    public int getAmbPlan() {
        return ambPlan;
    }

    public void setAmbPlan(int ambPlan) {
        this.ambPlan = ambPlan;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public Date getAmbBegTime2() {
        return ambBegTime2;
    }

    public void setAmbBegTime2(Date ambBegTime2) {
        this.ambBegTime2 = ambBegTime2;
    }

    public Date getAmbEndTime2() {
        return ambEndTime2;
    }

    public void setAmbEndTime2(Date ambEndTime2) {
        this.ambEndTime2 = ambEndTime2;
    }

    public int getAmbPlan2() {
        return ambPlan2;
    }

    public void setAmbPlan2(int ambPlan2) {
        this.ambPlan2 = ambPlan2;
    }

    public String getOffice2() {
        return office2;
    }

    public void setOffice2(String office2) {
        this.office2 = office2;
    }

    public Date getHomBegTime() {
        return homBegTime;
    }

    public void setHomBegTime(Date homBegTime) {
        this.homBegTime = homBegTime;
    }

    public Date getHomEndTime() {
        return homEndTime;
    }

    public void setHomEndTime(Date homEndTime) {
        this.homEndTime = homEndTime;
    }

    public int getHomPlan() {
        return homPlan;
    }

    public void setHomPlan(int homPlan) {
        this.homPlan = homPlan;
    }

    public Date getHomBegTime2() {
        return homBegTime2;
    }

    public void setHomBegTime2(Date homBegTime2) {
        this.homBegTime2 = homBegTime2;
    }

    public Date getHomEndTime2() {
        return homEndTime2;
    }

    public void setHomEndTime2(Date homEndTime2) {
        this.homEndTime2 = homEndTime2;
    }

    public int getHomPlan2() {
        return homPlan2;
    }

    public void setHomPlan2(int homPlan2) {
        this.homPlan2 = homPlan2;
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
        if (!(object instanceof PersonTimeTemplate)) {
            return false;
        }
        PersonTimeTemplate other = (PersonTimeTemplate) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.korus.tmis.core.entity.model.PersonTimeTemplate[id=" + id + "]";
    }
}
