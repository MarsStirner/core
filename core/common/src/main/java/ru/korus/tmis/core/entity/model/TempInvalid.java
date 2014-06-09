package ru.korus.tmis.core.entity.model;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;


/**
 * The persistent class for the TempInvalid database table.
 */
@Entity
public class TempInvalid implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private byte age;

    @Column(name = "age_bc")
    private int ageBc;

    @Column(name = "age_bu")
    private short ageBu;

    @Column(name = "age_ec")
    private int ageEc;

    @Column(name = "age_eu")
    private short ageEu;

    @Temporal(TemporalType.DATE)
    private Date begDate;

    @Temporal(TemporalType.DATE)
    private Date caseBegDate;

    @ManyToOne
    @JoinColumn(name = "createPerson_id")
    private Staff createPerson;

    @ManyToOne
    @JoinColumn(name = "modifyPerson_id")
    private Staff modifyPerson;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Patient patient;

    private short closed;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createDatetime;

    private boolean deleted;

    @Column(name = "diagnosis_id")
    private int diagnosisId;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    //0-листок нетрудоспособности, 1-справка
    @Basic(optional = false)
    @Column(name = "docType")
    private short docTypeCode;

    private int duration;

    @Temporal(TemporalType.DATE)
    private Date endDate;

    private int insuranceOfficeMark;

    @Temporal(TemporalType.TIMESTAMP)
    private Date modifyDatetime;

    @Lob()
    private String notes;

    private String number;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Staff person;

    @OneToOne
    @JoinColumn(name = "prev_id")
    private TempInvalid prev;

    private String serial;

    private short sex;

    @ManyToOne
    @JoinColumn(name = "tempInvalidReason_id")
    private RbTempInvalidReason tempInvalidReason;

    @ManyToOne
    @JoinColumn(name = "doctype_id")
    private RbTempInvalidDocument docType;


    //Тип 0-ВУТ, 1-инвалидность, 2-ограничение жизнедеятельности
    private byte type;

    @OneToMany(mappedBy = "master", cascade = CascadeType.ALL)
    private List<TempInvalidPeriod> tempInvalidPeriod = new LinkedList<TempInvalidPeriod>();

    public TempInvalid() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte getAge() {
        return this.age;
    }

    public void setAge(byte age) {
        this.age = age;
    }

    public int getAgeBc() {
        return this.ageBc;
    }

    public void setAgeBc(int ageBc) {
        this.ageBc = ageBc;
    }

    public short getAgeBu() {
        return this.ageBu;
    }

    public void setAgeBu(short ageBu) {
        this.ageBu = ageBu;
    }

    public int getAgeEc() {
        return this.ageEc;
    }

    public void setAgeEc(int ageEc) {
        this.ageEc = ageEc;
    }

    public short getAgeEu() {
        return this.ageEu;
    }

    public void setAgeEu(short ageEu) {
        this.ageEu = ageEu;
    }

    public Date getBegDate() {
        return this.begDate;
    }

    public void setBegDate(Date begDate) {
        this.begDate = begDate;
    }

    public Date getCaseBegDate() {
        return this.caseBegDate;
    }

    public void setCaseBegDate(Date caseBegDate) {
        this.caseBegDate = caseBegDate;
    }


    public short getClosed() {
        return this.closed;
    }

    public void setClosed(short closed) {
        this.closed = closed;
    }

    public Date getCreateDatetime() {
        return this.createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }


    public boolean getDeleted() {
        return this.deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public int getDiagnosisId() {
        return this.diagnosisId;
    }

    public void setDiagnosisId(int diagnosisId) {
        this.diagnosisId = diagnosisId;
    }

    public Event getEvent() {
        return this.event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getInsuranceOfficeMark() {
        return this.insuranceOfficeMark;
    }

    public void setInsuranceOfficeMark(int insuranceOfficeMark) {
        this.insuranceOfficeMark = insuranceOfficeMark;
    }

    public Date getModifyDatetime() {
        return this.modifyDatetime;
    }

    public void setModifyDatetime(Date modifyDatetime) {
        this.modifyDatetime = modifyDatetime;
    }


    public String getNotes() {
        return this.notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber(String number) {
        this.number = number.substring(0, Math.min(serial.length(), 16));
    }

    public String getSerial() {
        return this.serial;
    }

    public void setSerial(String serial) {
        this.serial = serial.substring(0, Math.min(serial.length(), 8));
    }

    public short getSex() {
        return this.sex;
    }

    public void setSex(short sex) {
        this.sex = sex;
    }


    public byte getType() {
        return this.type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public Staff getCreatePerson() {
        return createPerson;
    }

    public void setCreatePerson(Staff createPerson) {
        this.createPerson = createPerson;
    }

    public Staff getModifyPerson() {
        return modifyPerson;
    }

    public void setModifyPerson(Staff modifyPerson) {
        this.modifyPerson = modifyPerson;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
        if (!patient.getTempInvalids().contains(this)) {
            patient.getTempInvalids().add(this);
        }
    }

    public short getDocTypeCode() {
        return docTypeCode;
    }

    public void setDocTypeCode(short docTypeCode) {
        this.docTypeCode = docTypeCode;
    }

    public RbTempInvalidDocument getDocType() {
        return docType;
    }

    public void setDocType(RbTempInvalidDocument docType) {
        this.docType = docType;
    }

    public Staff getPerson() {
        return person;
    }

    public void setPerson(Staff person) {
        this.person = person;
    }

    public TempInvalid getPrev() {
        return prev;
    }

    public void setPrev(TempInvalid prev) {
        this.prev = prev;
    }

    public RbTempInvalidReason getTempInvalidReason() {
        return tempInvalidReason;
    }

    public void setTempInvalidReason(RbTempInvalidReason tempInvalidReason) {
        this.tempInvalidReason = tempInvalidReason;
    }

    public List<TempInvalidPeriod> getTempInvalidPeriod() {
        return tempInvalidPeriod;
    }

    public void setTempInvalidPeriod(List<TempInvalidPeriod> tempInvalidPeriod) {
        this.tempInvalidPeriod = tempInvalidPeriod;
    }
}