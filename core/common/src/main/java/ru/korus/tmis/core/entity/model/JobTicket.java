package ru.korus.tmis.core.entity.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: mmakankov
 * Date: 11.02.13
 * Time: 14:31
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name = "Job_Ticket")
@NamedQueries(
        {
                @NamedQuery(name = "JobTicket.findAll", query = "SELECT jt FROM JobTicket jt")
        })
@XmlType(name = "jobTicket")
@XmlRootElement(name = "jobTicket")
public class JobTicket implements Serializable {

    private static final long serialVersionUID = 1L;

    public final static int STATUS_WAITING = 0;
    public final static int STATUS_IN_PROGRESS = 1;
    public final static int STATUS_IS_FINISHED = 2;
    public final static int STATUS_SENDING = 3;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "master_id")
    private Job job;

    @Basic(optional = false)
    @Column(name = "idx")
    private int idx;

    @Basic(optional = false)
    @Column(name = "datetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datetime;

    @Basic(optional = false)
    @Column(name = "resTimestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date resTimestamp;

    @Basic(optional = false)
    @Column(name = "resConnectionId")
    private int resConnectionId;

    @Basic(optional = false)
    @Column(name = "status")
    private int status;

    @Column(name = "begDateTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date begDateTime;

    @Column(name = "endDateTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDateTime;

    @OneToMany(mappedBy = "jobTicket")
    private List<APValueJobTicket> propertiesValues;

    @Column(name = "label")
    private String label;

    @Column(name = "note")
    private String note;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public Date getResTimestamp() {
        return resTimestamp;
    }

    public void setResTimestamp(Date resTimestamp) {
        this.resTimestamp = resTimestamp;
    }

    public int getResConnectionId() {
        return resConnectionId;
    }

    public void setResConnectionId(int resConnectionId) {
        this.resConnectionId = resConnectionId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getBegDateTime() {
        return begDateTime;
    }

    public void setBegDateTime(Date begDateTime) {
        this.begDateTime = begDateTime;
    }

    public Date getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Date endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JobTicket jobTicket = (JobTicket) o;

        if (id != null ? !id.equals(jobTicket.id) : jobTicket.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ru.korus.tmis.core.entity.model.JobTicket[id=" + id + "]";
    }

    public static JobTicket clone(JobTicket self) throws CloneNotSupportedException {
        JobTicket newJobTicket = (JobTicket) self.clone();
        return newJobTicket;
    }

    public List<APValueJobTicket> getPropertiesValues() {
        return propertiesValues;
    }
}
