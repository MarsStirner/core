package ru.korus.tmis.core.entity.model;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@Entity
@Table(name = "Diagnosis", catalog = "", schema = "")
@NamedQueries(
        {
                @NamedQuery(name = "Diagnosis.findAll", query = "SELECT d FROM Diagnosis d")
        })
@XmlType(name = "diagnosis")
@XmlRootElement(name = "diagnosis")
public class Diagnosis implements Serializable {

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
    @JoinColumn(name = "client_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "diagnosisType_id")
    private RbDiagnosisType diagnosisType;

    @Basic(optional = false)
    @Column(name = "character_id")
    private Integer characterId;                //TODO: Новая Энтити

    @ManyToOne
    @JoinColumn(name = "dispanser_id")
    private RbDispanser dispanser;

    @ManyToOne
    @JoinColumn(name = "traumaType_id")
    private RbTraumaType traumaType;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Staff person;

    @Column(name = "setDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date setDate;

    @Column(name = "endDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @ManyToOne
    @JoinColumn(name = "MKB", referencedColumnName = "DiagID")
    //@NotNull
    //@Valid
    //@Size(min = 1, max = 8)
    private Mkb mkb;

    @Column(name = "MKBEx")
    //@Size(min = 1, max = 8)
    private String mkbExCode;

    @Basic(optional = false)
    @Column(name = "mod_id")
    private Integer modId;                //TODO: Новая Энтити

    public Diagnosis() {
    }

    public Diagnosis(Integer id) {
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

    public RbDiagnosisType getDiagnosisType() {
        return diagnosisType;
    }

    public void setDiagnosisType(RbDiagnosisType diagnosisType) {
        this.diagnosisType = diagnosisType;
    }

    public Integer getСharacterId() {
        return characterId;
    }

    public void setСharacterId(Integer characterId) {
        this.characterId = characterId;
    }

    public Integer getModId() {
        return modId;
    }

    public void setModId(Integer modId) {
        this.modId = modId;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public RbDispanser getDispanser() {
        return dispanser;
    }

    public void setDispanser(RbDispanser dispanser) {
        this.dispanser = dispanser;
    }

    public RbTraumaType getTraumaType() {
        return traumaType;
    }

    public void setTraumaType(RbTraumaType traumaType) {
        this.traumaType = traumaType;
    }

    public Staff getPerson() {
        return person;
    }

    public void setPerson(Staff person) {
        this.person = person;
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

    public String getMkbExCode() {
        return mkbExCode;
    }

    public void setMkbExCode(String mkbExCode) {
        this.mkbExCode = mkbExCode;
    }

    public Mkb getMkb() {
        return mkb;
    }

    public void setMkb(final Mkb mkb) {
        this.mkb = mkb;
        if (!mkb.getDiagnosis().contains(this)) {
            mkb.addDiagnosis(this);
        }
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
        if (!(object instanceof Diagnosis)) {
            return false;
        }
        Diagnosis other = (Diagnosis) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.korus.tmis.core.entity.model.Diagnosis[id=" + id + "]";
    }

}
