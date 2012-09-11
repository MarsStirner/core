package ru.korus.tmis.core.entity.model;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@Entity
@Table(name = "Client", catalog = "", schema = "")
@NamedQueries(
        {
                @NamedQuery(name = "Patient.findAll", query = "SELECT p FROM Patient p")
        })
@XmlType(name = "patient")
@XmlRootElement(name = "patient")
public class Patient implements Serializable {

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

    @Basic(optional = false)
    @Column(name = "lastName")
    private String lastName;

    @Basic(optional = false)
    @Column(name = "firstName")
    private String firstName;

    @Basic(optional = false)
    @Column(name = "patrName")
    private String patrName;

    @Basic(optional = false)
    @Column(name = "birthDate")
    @Temporal(TemporalType.DATE)
    private Date birthDate;

    @Basic(optional = false)
    @Column(name = "sex")
    private short sex;

    @Basic(optional = false)
    @Column(name = "SNILS")
    private String snils;

    @ManyToOne
    @JoinColumn(name = "bloodType_id")
    private RbBloodType bloodType;

    @Column(name = "bloodDate")
    @Temporal(TemporalType.DATE)
    private Date bloodDate;

    @Basic(optional = false)
    @Column(name = "bloodNotes")
    private String bloodNotes;

    @Basic(optional = false)
    @Column(name = "growth")
    private String height;

    @Basic(optional = false)
    @Column(name = "weight")
    private String weight;

    @Basic(optional = false)
    @Column(name = "notes")
    private String notes;

    ////////////////////////////////////////////////////////////////////////////
    // Custom mappings
    ////////////////////////////////////////////////////////////////////////////

    @OneToMany(mappedBy = "patient")
    private List<Event> events = new LinkedList<Event>();

    public List<Event> getEvents() {
        return events;
    }

    public void addEvent(final Event event) {
        this.events.add(event);
        if (event.getPatient() != this) {
            event.setPatient(this);
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    // End of custom mappings
    ////////////////////////////////////////////////////////////////////////////

    public Patient() {
    }

    public Patient(Integer id) {
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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPatrName() {
        return patrName;
    }

    public void setPatrName(String patrName) {
        this.patrName = patrName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public short getSex() {
        return sex;
    }

    public void setSex(short sex) {
        this.sex = sex;
    }

    public String getSnils() {
        return snils;
    }

    public void setSnils(String snils) {
        this.snils = snils;
    }

    public RbBloodType getBloodType() {
        if (bloodType != null) {
            return bloodType;
        } else {
            return RbBloodType.getEmptyBloodType();
        }
    }

    public void setBloodType(RbBloodType bloodType) {
        this.bloodType = bloodType;
    }

    public Date getBloodDate() {
        return bloodDate;
    }

    public void setBloodDate(Date bloodDate) {
        this.bloodDate = bloodDate;
    }

    public String getBloodNotes() {
        return bloodNotes;
    }

    public void setBloodNotes(String bloodNotes) {
        this.bloodNotes = bloodNotes;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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
        if (!(object instanceof Patient)) {
            return false;
        }
        Patient other = (Patient) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.korus.tmis.core.database.model.Patient[id=" + id + "]";
    }
}
