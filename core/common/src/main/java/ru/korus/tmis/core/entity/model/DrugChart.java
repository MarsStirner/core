package ru.korus.tmis.core.entity.model;

import org.joda.time.DateMidnight;

import javax.persistence.*;
import java.util.Date;

/**
 * Author: Upatov Egor <br>
 * Date: 30.08.13, 13:08 <br>
 * Company: Korus Consulting IT <br>
 * Description: <br>
 */
@Entity
@Table(name = "DrugChart", catalog = "", schema = "")
public class DrugChart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "action_id", nullable = false)
    private Action action;

    @ManyToOne
    @JoinColumn(name = "master_id", nullable = true)
    private DrugChart master;

    @Basic(optional = false)
    @Column(name = "begDateTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date begDateTime;

    @Basic(optional = false)
    @Column(name = "endDateTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDateTime;

    @Basic(optional = false)
    @Column(name = "status")
    private Short status;

    @Basic(optional = true)
    @Column(name = "statusDateTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date statusDateTime;

    @Basic(optional = true)
    @Column(name = "note")
    private String note;

    public DrugChart() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public DrugChart getMaster() {
        return master;
    }

    public void setMaster(DrugChart master) {
        this.master = master;
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

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Date getStatusDateTime() {
        return statusDateTime;
    }

    public void setStatusDateTime(Date statusDateTime) {
        this.statusDateTime = statusDateTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + "{id=" + id + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DrugChart drugChart = (DrugChart) o;

        if (id != null ? !id.equals(drugChart.id) : drugChart.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
