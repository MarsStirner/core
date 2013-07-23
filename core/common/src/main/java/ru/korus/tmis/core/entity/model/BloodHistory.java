package ru.korus.tmis.core.entity.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.Date;

/**
 * Entity для таблицы BloodHistory
 * Author: idmitriev Systema-Soft
 * Date: 4/11/13 9:06 PM
 * Since: 1.0.1.1
 */
@Entity
@Table(name = "BloodHistory", catalog = "", schema = "")
@NamedQueries(
        {
          @NamedQuery(name = "BloodHistory.findAll", query = "SELECT bh FROM BloodHistory bh"),
          @NamedQuery(name = "BloodHistory.findByPatientId", query = "SELECT bh FROM BloodHistory bh WHERE bh.patient.id = :patientId ORDER BY bh.bloodDate desc, bh.id desc")
        })
@XmlType(name = "diagnostic")
@XmlRootElement(name = "diagnostic")
public class BloodHistory implements Serializable {

        private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Basic(optional = false)
    @Column(name = "bloodDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date bloodDate;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "bloodType_id")
    private RbBloodType bloodType;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Staff person;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getBloodDate() {
        return bloodDate;
    }

    public void setBloodDate(Date bloodDate) {
        this.bloodDate = bloodDate;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public RbBloodType getBloodType() {
        return bloodType;
    }

    public void setBloodType(RbBloodType bloodType) {
        this.bloodType = bloodType;
    }

    public Staff getPerson() {
        return person;
    }

    public void setPerson(Staff person) {
        this.person = person;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BloodHistory that = (BloodHistory) o;

        if (!id.equals(that.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "ru.korus.tmis.core.entity.model.BloodHistory[id=" + id + "]";
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public static BloodHistory clone(BloodHistory self) throws CloneNotSupportedException {
        BloodHistory newBloodHistory = (BloodHistory) self.clone();
        return newBloodHistory;
    }
}
