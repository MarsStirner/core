package ru.korus.tmis.core.entity.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@Entity
@Table(name = "EventType_Action")
@NamedQueries(
        {
                @NamedQuery(name = "EventTypeAction.findAll", query = "SELECT e FROM EventTypeAction e")
        })
@XmlType(name = "eventTypeAction")
@XmlRootElement(name = "eventTypeAction")
public class EventTypeAction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "eventType_id")
    private EventType eventType;

    @Basic(optional = false)
    @Column(name = "idx")
    private int idx;

    @ManyToOne(optional = false)
    @JoinColumn(name = "actionType_id")
    private ActionType actionType;

    @ManyToOne
    @JoinColumn(name = "speciality_id")
    private Speciality speciality;

    @Column(name = "tissueType_id")
    private Integer tissueTypeId;

    @Basic(optional = false)
    @Column(name = "sex")
    private short sex;

    @Basic(optional = false)
    @Column(name = "age")
    private String age;

    @Column(name = "age_bu")
    private Boolean ageBu;

    @Column(name = "age_bc")
    private Short ageBc;

    @Column(name = "age_eu")
    private Boolean ageEu;

    @Column(name = "age_ec")
    private Short ageEc;

    @Basic(optional = false)
    @Column(name = "selectionGroup")
    private short selectionGroup;

    @Basic(optional = false)
    @Column(name = "actuality")
    private short actuality;

    @Basic(optional = false)
    @Column(name = "expose")
    private boolean expose;

    @Basic(optional = false)
    @Column(name = "payable")
    private boolean payable;

    public EventTypeAction() {
    }

    public EventTypeAction(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public Speciality getSpeciality() {
        return speciality;
    }

    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
    }

    public Integer getTissueTypeId() {
        return tissueTypeId;
    }

    public void setTissueTypeId(Integer tissueTypeId) {
        this.tissueTypeId = tissueTypeId;
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

    public Boolean getAgeBu() {
        return ageBu;
    }

    public void setAgeBu(Boolean ageBu) {
        this.ageBu = ageBu;
    }

    public Short getAgeBc() {
        return ageBc;
    }

    public void setAgeBc(Short ageBc) {
        this.ageBc = ageBc;
    }

    public Boolean getAgeEu() {
        return ageEu;
    }

    public void setAgeEu(Boolean ageEu) {
        this.ageEu = ageEu;
    }

    public Short getAgeEc() {
        return ageEc;
    }

    public void setAgeEc(Short ageEc) {
        this.ageEc = ageEc;
    }

    public short getSelectionGroup() {
        return selectionGroup;
    }

    public void setSelectionGroup(short selectionGroup) {
        this.selectionGroup = selectionGroup;
    }

    public short getActuality() {
        return actuality;
    }

    public void setActuality(short actuality) {
        this.actuality = actuality;
    }

    public boolean getExpose() {
        return expose;
    }

    public void setExpose(boolean expose) {
        this.expose = expose;
    }

    public boolean getPayable() {
        return payable;
    }

    public void setPayable(boolean payable) {
        this.payable = payable;
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
        if (!(object instanceof EventTypeAction)) {
            return false;
        }
        EventTypeAction other = (EventTypeAction) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.korus.tmis.core.entity.model.EventTypeAction[id=" + id + "]";
    }
}
