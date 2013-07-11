package ru.korus.tmis.core.entity.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * The persistent class for the PatientsToHS database table.
 * 
 */
@Entity
@Table(name = "PatientsToHS")
public class PatientsToHs implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id", unique = true, nullable = false)
    private int patientId;

    @Column(nullable = false)
    private int errCount;

    @Column(length = 1024)
    private String info;

    @Column(nullable = false)
    private Timestamp sendTime;

    // bi-directional one-to-one association to Client
    @OneToOne
    @JoinColumn(name = "client_id", nullable = false, insertable = false, updatable = false)
    private Patient patient;

    public PatientsToHs() {
    }

    public int getPatientId() {
        return this.patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getErrCount() {
        return this.errCount;
    }

    public void setErrCount(int errCount) {
        this.errCount = errCount;
    }

    public String getInfo() {
        return this.info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Timestamp getSendTime() {
        return this.sendTime;
    }

    public void setSendTime(Timestamp sendTime) {
        this.sendTime = sendTime;
    }

    public Patient getPatient() {
        return this.patient;
    }

    public void setPatient(Patient patient) {
        patientId = patient.getId();
        this.patient = patient;
    }

}