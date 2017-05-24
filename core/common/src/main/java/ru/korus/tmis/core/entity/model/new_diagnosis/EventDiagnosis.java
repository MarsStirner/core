package ru.korus.tmis.core.entity.model.new_diagnosis;

import ru.korus.tmis.core.entity.model.Diagnosis;
import ru.korus.tmis.core.entity.model.Event;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by EUpatov on 23.05.2017.
 */
@Entity
@Table(name="Event_Diagnosis")
public class EventDiagnosis implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @ManyToOne
    @JoinColumn(name = "diagnosis_id", nullable = false)
    private Diagnosis diagnosis;

    @ManyToOne
    @JoinColumn(name = "diagnosisType_id", nullable = false)
    private RbDiagnosisTypeN diagnosisType;

    @ManyToOne
    @JoinColumn(name = "diagnosisKind_id", nullable = false)
    private RbDiagnosisKind diagnosisKind;

    @Basic(optional = false)
    @Column(name = "deleted")
    private boolean deleted;

    @Column(name = "createDatetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDatetime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Diagnosis getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(Diagnosis diagnosis) {
        this.diagnosis = diagnosis;
    }

    public RbDiagnosisTypeN getDiagnosisType() {
        return diagnosisType;
    }

    public void setDiagnosisType(RbDiagnosisTypeN diagnosisType) {
        this.diagnosisType = diagnosisType;
    }

    public RbDiagnosisKind getDiagnosisKind() {
        return diagnosisKind;
    }

    public void setDiagnosisKind(RbDiagnosisKind diagnosisKind) {
        this.diagnosisKind = diagnosisKind;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EventDiagnosis[").append(id).append(']');
        sb.append("{ Event[").append(event != null ? event.getId() : null).append(']');
        sb.append(", diagnosisType=").append(diagnosisType);
        sb.append(", diagnosisKind=").append(diagnosisKind);
        sb.append(", diagnosis=").append(diagnosis);
        sb.append(", createDatetime=").append(createDatetime);
        sb.append('}');
        return sb.toString();
    }
}
