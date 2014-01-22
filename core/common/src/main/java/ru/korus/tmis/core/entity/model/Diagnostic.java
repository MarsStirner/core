package ru.korus.tmis.core.entity.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@Entity
@Table(name = "Diagnostic")
@NamedQueries(
        {
                @NamedQuery(name = "Diagnostic.findAll", query = "SELECT d FROM Diagnostic d")
        })
@XmlType(name = "diagnostic")
@XmlRootElement(name = "diagnostic")
public class Diagnostic implements Serializable {

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
    @JoinColumn(name = "event_id")
    private Event event;

    @OneToOne
    @JoinColumn(name = "diagnosis_id")
    private Diagnosis diagnosis;

    @ManyToOne
    @JoinColumn(name = "diagnosisType_id")
    private RbDiagnosisType diagnosisType;

    @ManyToOne
    @JoinColumn(name = "character_id")
    private RbDiseaseCharacter character;

    @ManyToOne
    @JoinColumn(name = "stage_id")
    private RbDiseaseStage stage;

    @Basic(optional = false)
    @Column(name = "phase_id")
    private Integer phaseId;                    //TODO: Новая Энтити

    @ManyToOne
    @JoinColumn(name = "dispanser_id")
    private RbDispanser dispanser;

    @Column(name = "sanatorium")
    private short sanatorium;

    @Column(name = "hospital")
    private short hospital;

    @ManyToOne
    @JoinColumn(name = "traumaType_id")
    private RbTraumaType traumaType;

    @ManyToOne
    @JoinColumn(name = "speciality_id")
    private Speciality speciality;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Staff person;

    @ManyToOne
    @JoinColumn(name = "healthGroup_id")
    private RbHealthGroup healthGroup;

    @ManyToOne
    @JoinColumn(name = "result_id")
    private RbResult result;

    @Column(name = "setDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date setDate;

    @Column(name = "endDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @Column(name = "notes")
    private String notes;

    @Version
    @Basic(optional = false)
    @Column(name = "version")
    private int version;

    public Diagnostic() {
    }

    public Diagnostic(Integer id) {
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

    public boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
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

    public RbDiagnosisType getDiagnosisType() {
        return diagnosisType;
    }

    public void setDiagnosisType(RbDiagnosisType diagnosisType) {
        this.diagnosisType = diagnosisType;
    }

    public RbDiseaseCharacter getCharacter() {
        return character;
    }

    public void setCharacter(RbDiseaseCharacter character) {
        this.character = character;
    }

    public RbDiseaseStage getStage() {
        return stage;
    }

    public void setStage(RbDiseaseStage stage) {
        this.stage = stage;
    }

    public Integer getPhaseId() {
        return phaseId;
    }

    public void setPhase(Integer phaseId) {
        this.phaseId = phaseId;
    }

    public RbDispanser getDispanser() {
        return dispanser;
    }

    public void setDispanser(RbDispanser dispanser) {
        this.dispanser = dispanser;
    }

    public short getSanatorium() {
        return sanatorium;
    }

    public void setSanatorium(short sanatorium) {
        this.sanatorium = sanatorium;
    }

    public short getHospital() {
        return hospital;
    }

    public void setHospital(short hospital) {
        this.hospital = hospital;
    }

    public RbTraumaType getTraumaType() {
        return traumaType;
    }

    public void setTraumaType(RbTraumaType traumaType) {
        this.traumaType = traumaType;
    }

    public Speciality getSpeciality() {
        return speciality;
    }

    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
    }

    public Staff getPerson() {
        return person;
    }

    public void setPerson(Staff person) {
        this.person = person;
    }

    public RbHealthGroup getHealthGroup() {
        return healthGroup;
    }

    public void setHealthGroup(RbHealthGroup healthGroup) {
        this.healthGroup = healthGroup;
    }

    public RbResult getResult() {
        return result;
    }

    public void setResult(RbResult result) {
        this.result = result;
    }

    public Date getSetDate() {
        return setDate;
    }

    public void setSetDate(Date setDate) {
        this.setDate = setDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
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
        if (!(object instanceof Diagnostic)) {
            return false;
        }
        Diagnostic other = (Diagnostic) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.korus.tmis.core.entity.model.Diagnostic[id=" + id + "]";
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public static Diagnostic clone(Diagnostic self) throws CloneNotSupportedException {
        Diagnostic newDiagnostic = (Diagnostic) self.clone();
        return newDiagnostic;
    }

}