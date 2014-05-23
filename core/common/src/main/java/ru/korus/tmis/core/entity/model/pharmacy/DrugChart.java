package ru.korus.tmis.core.entity.model.pharmacy;

import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.entity.model.UUID;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Author: Upatov Egor <br>
 * Date: 30.08.13, 13:08 <br>
 * Company: Korus Consulting IT <br>
 * Description: <br>
 */

@NamedQueries(
        {
                @NamedQuery(name = "DrugChart.findByEvent", query = "SELECT i FROM DrugChart i WHERE i.action.event.id = :eventId"),
                @NamedQuery(name = "DrugChart.getPrescriptionList", query = "SELECT dc FROM DrugChart dc WHERE dc.action.event.id = :eventId")
        })
@Entity
@Table(name = "DrugChart")
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


    @Basic(optional = true)
    @Column(name = "uuid")
    private String uuid;

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


    public PrescriptionStatus getStatusEnum() {
        return PrescriptionStatus.values()[status];
    }

    public void setStatusEnum(PrescriptionStatus status) {
        this.status = (short) Arrays.asList(PrescriptionStatus.values()).indexOf(status);
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


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
