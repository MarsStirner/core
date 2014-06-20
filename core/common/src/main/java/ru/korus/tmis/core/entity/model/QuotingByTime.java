package ru.korus.tmis.core.entity.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Entity для работы из ORM с таблицей s11r64.QuotingByTime.
 * User: eupatov<br>
 * Date: 28.01.13 at 13:02<br>
 * Company Korus Consulting IT<br>
 */

@Entity
@Table(name = "QuotingByTime")
@NamedQueries(
        {
                @NamedQuery(name = "QuotingByTime.findAll", query = "SELECT cq FROM QuotingByTime cq"),
                @NamedQuery(name = "QuotingByTime.findByPersonAndDate",
                        query = "SELECT q FROM QuotingByTime q WHERE q.doctor = :person AND q.quotingDate = :date"),
                @NamedQuery(name = "QuotingByTime.findByPersonAndDateAndType",
                        query = "SELECT q FROM QuotingByTime q WHERE q.doctor = :person AND q.quotingDate = :date AND q.quotingType = :quotingType")
        })
@XmlType(name = "quotingByTime")
@XmlRootElement(name = "quotingByTime")
public class QuotingByTime implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");


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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "QuotingType")
    private RbTimeQuotingType quotingType;

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

    public RbTimeQuotingType getQuotingType() {
        return quotingType;
    }

    public void setQuotingType(RbTimeQuotingType quotingType) {
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

    public String getInfo() {
        return new StringBuilder("QuotingByTime[").append(id).append("]{")
                .append("Person=").append(doctor.getId()).append(' ')
                .append(dateFormat.format(quotingDate)).append(' ')
                .append(timeFormat.format(quotingTimeStart)).append(' ')
                .append(timeFormat.format(quotingTimeEnd))
                .append(" type=").append(quotingType)
                .append('}')
                .toString();
    }
}
