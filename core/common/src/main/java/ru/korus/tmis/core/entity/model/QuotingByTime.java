package ru.korus.tmis.core.entity.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.Date;

/**
 * Entity для работы из ORM с таблицей s11r64.QuotingByTime.
 * User: eupatov<br>
 * Date: 28.01.13 at 13:02<br>
 * Company Korus Consulting IT<br>
 */

@Entity
@Table(name = "QuotingByTime", catalog = "", schema = "")
@NamedQueries(
        {
                @NamedQuery(name = "QuotingByTime.findAll", query = "SELECT cq FROM QuotingByTime cq")
        })
@XmlType(name = "quotingByTime")
@XmlRootElement(name = "quotingByTime")
public class QuotingByTime implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Staff doctor;


    @Basic(optional = false)
    @Column(name = "quoting_date")
    @Temporal(TemporalType.DATE)
    private Date quotingDate;

    @Basic(optional = false)
    @Column(name = "QuotingTimeStart")
    @Temporal(TemporalType.TIME)
    private Date quotingTimeStart;

    @Basic(optional = false)
    @Column(name = "QuotingTimeEnd")
    @Temporal(TemporalType.TIME)
    private Date quotingTimeEnd;

    @Basic(optional = false)
    @Column(name = "QuotingType")
    private Integer quotingType;

    public QuotingByTime() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Staff getDoctor() {
        return doctor;
    }

    public void setDoctor(Staff doctor) {
        this.doctor = doctor;
    }

    public Date getQuotingDate() {
        return quotingDate;
    }

    public void setQuotingDate(Date quotingDate) {
        this.quotingDate = quotingDate;
    }

    public Date getQuotingTimeStart() {
        return quotingTimeStart;
    }

    public void setQuotingTimeStart(Date quotingTimeStart) {
        this.quotingTimeStart = quotingTimeStart;
    }

    public Date getQuotingTimeEnd() {
        return quotingTimeEnd;
    }

    public void setQuotingTimeEnd(Date quotingTimeEnd) {
        this.quotingTimeEnd = quotingTimeEnd;
    }

    public Integer getQuotingType() {
        return quotingType;
    }

    public void setQuotingType(Integer quotingType) {
        this.quotingType = quotingType;
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
        if (!(object instanceof QuotingByTime)) {
            return false;
        }
        QuotingByTime other = (QuotingByTime) object;
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
        return "ru.korus.tmis.core.database.model.QuotingByTime[id=" + id + "]";
    }
}
