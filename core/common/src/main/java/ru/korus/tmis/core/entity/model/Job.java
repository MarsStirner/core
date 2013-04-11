package ru.korus.tmis.core.entity.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.Date;

/**
 * Job Entity
 * Author: idmitriev Systema-Soft
 * Date: 2/13/13 2:30 PM
 * Since: 1.0.0.64
 */
@Entity
@Table(name = "Job", catalog = "", schema = "")
@NamedQueries(
        {
                @NamedQuery(name = "Job.findAll", query = "SELECT j FROM Job j")
        })
@XmlType(name = "job")
@XmlRootElement(name = "job")
public class Job implements Serializable {

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

    @ManyToOne
    @JoinColumn(name = "jobType_id")
    private RbJobType jobType;

    @ManyToOne
    @JoinColumn(name = "orgStructure_id")
    private OrgStructure orgStructure;

    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(name = "begTime")
    @Temporal(TemporalType.TIME)
    private Date begTime;

    @Column(name = "endTime")
    @Temporal(TemporalType.TIME)
    private Date endTime;

    @Basic(optional = false)
    @Column(name = "quantity")
    private int quantity;

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

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public RbJobType getJobType() {
        return jobType;
    }

    public void setJobType(RbJobType jobType) {
        this.jobType = jobType;
    }

    public OrgStructure getOrgStructure() {
        return orgStructure;
    }

    public void setOrgStructure(OrgStructure orgStructure) {
        this.orgStructure = orgStructure;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getBegTime() {
        return begTime;
    }

    public void setBegTime(Date begTime) {
        this.begTime = begTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Job job = (Job) o;

        if (id != null ? !id.equals(job.id) : job.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ru.korus.tmis.core.entity.model.Job[id=" + id + "]";
    }
}
