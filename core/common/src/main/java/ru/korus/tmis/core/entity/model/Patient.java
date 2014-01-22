package ru.korus.tmis.core.entity.model;

import ru.korus.tmis.core.entity.model.fd.ClientFlatDirectory;
import ru.korus.tmis.core.entity.model.fd.ClientSocStatus;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "Client")
@NamedQueries(
        {
                @NamedQuery(name = "Patient.findAll", query = "SELECT p FROM Patient p"),
                @NamedQuery(name = "ClientRelation.findAllActive", query = "SELECT r FROM ClientRelation r WHERE r.deleted = 0"),
                @NamedQuery(name = "Patient.findByPersonalInfo",
                        query = "SELECT p FROM Patient p " +
                                "WHERE UPPER(p.lastName) = :lastName " +
                                "AND UPPER(p.firstName) = :firstName " +
                                "AND UPPER(p.patrName) = :patrName " +
                                "AND p.sex = :sex " +
                                "AND p.birthDate = :birthDate")
        })
@XmlType(name = "patient")
@XmlRootElement(name = "patient")
public class Patient implements Serializable, Cloneable {

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

    @ManyToOne //(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "createPerson_id")
    private Staff createPerson;

    @Basic(optional = false)
    @Column(name = "modifyDatetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifyDatetime;

    @ManyToOne //(cascade = CascadeType.PERSIST)
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
    @Column(name = "birthPlace")
    private String birthPlace;

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

    @Version
    @Basic(optional = false)
    @Column(name = "version")
    private int version;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "uuid_id")
//    @Transient
    private UUID uuid;

    ////////////////////////////////////////////////////////////////////////////
    // Custom mappings
    ////////////////////////////////////////////////////////////////////////////

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
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


    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)//{CascadeType.PERSIST, CascadeType.REMOVE}
    private List<ClientDocument> clientDocuments = new LinkedList<ClientDocument>();

    public List<ClientDocument> getClientDocuments() {
        return this.clientDocuments;
    }

    public List<ClientDocument> getActiveClientDocuments() {
        List<ClientDocument> activeDocuments = new LinkedList<ClientDocument>();
        for (ClientDocument d : clientDocuments) {
            if (d.isDeleted() == false) {
                activeDocuments.add(d);
            }
        }
        return activeDocuments;
    }

    public void addClientDocument(final ClientDocument clientDocument) {
        this.clientDocuments.add(clientDocument);
        if (clientDocument.getPatient() != this) {
            clientDocument.setPatient(this);
        }
    }

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<ClientPolicy> clientPolicies = new LinkedList<ClientPolicy>();

    public List<ClientPolicy> getClientPolicies() {
        return this.clientPolicies;
    }

    public List<ClientPolicy> getActiveClientPolicies() {
        List<ClientPolicy> activePolicies = new LinkedList<ClientPolicy>();
        for (ClientPolicy p : clientPolicies) {
            if (p.isDeleted() == false) {
                activePolicies.add(p);
            }
        }
        return activePolicies;
    }

    public void addClientPolicies(final ClientPolicy clientPolicy) {
        this.clientPolicies.add(clientPolicy);
        if (clientPolicy.getPatient() != this) {
            clientPolicy.setPatient(this);
        }
    }

    @OneToMany(mappedBy = "patient", cascade = {CascadeType.ALL} )
    private List<ClientRelation> clientRelatives = new LinkedList<ClientRelation>();

    public List<ClientRelation> getClientRelatives() {
        return this.clientRelatives;
    }

    public List<ClientRelation> getActiveClientRelatives() {
        List<ClientRelation> activeRelations = new LinkedList<ClientRelation>();
        for (ClientRelation r : clientRelatives) {
            if (r.isDeleted() == false) {
                activeRelations.add(r);
            }
        }
        return activeRelations;
    }

    public void addClientRelative(final ClientRelation clientRelative) {
        this.clientRelatives.add(clientRelative);
        if (clientRelative.getPatient() != this) {
            clientRelative.setPatient(this);
        }
    }

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<ClientContact> clientContacts = new LinkedList<ClientContact>();

    public List<ClientContact> getClientContacts() {
        return this.clientContacts;
    }

    public List<ClientContact> getActiveClientContacts() {
        List<ClientContact> activeContacts = new LinkedList<ClientContact>();
        for (ClientContact c : clientContacts) {
            if (c.isDeleted() == false) {
                activeContacts.add(c);
            }
        }
        return activeContacts;
    }

    public void addClientContact(final ClientContact clientContact) {
        this.clientContacts.add(clientContact);
        if (clientContact.getPatient() != this) {
            clientContact.setPatient(this);
        }
    }

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<ClientAddress> clientAddresses = new LinkedList<ClientAddress>();

    public List<ClientAddress> getActiveClientAddresses() {
        List<ClientAddress> activeAddresses = new LinkedList<ClientAddress>();
        for (ClientAddress c : clientAddresses) {
            if (c.isDeleted() == false) {
                activeAddresses.add(c);
            }
        }
        return activeAddresses;
    }

    public List<ClientAddress> getClientAddresses() {
        return this.clientAddresses;
    }

    public void addClientAddress(final ClientAddress clientAddress) {
        this.clientAddresses.add(clientAddress);
        if (clientAddress.getPatient() != this) {
            clientAddress.setPatient(this);
        }
    }

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<ClientAllergy> clientAllergies = new LinkedList<ClientAllergy>();

    public List<ClientAllergy> getClientAllergies() {
        return this.clientAllergies;
    }

    public List<ClientAllergy> getActiveClientAllergies() {
        List<ClientAllergy> activeClientAllergies = new LinkedList<ClientAllergy>();
        for (ClientAllergy c : clientAllergies) {
            if (c.isDeleted() == false) {
                activeClientAllergies.add(c);
            }
        }
        return activeClientAllergies;
    }

    public void addClientAllergies(final ClientAllergy clientAllergy) {
        this.clientAllergies.add(clientAllergy);
        if (clientAllergy.getPatient() != this) {
            clientAllergy.setPatient(this);
        }
    }

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<ClientIntoleranceMedicament> clientIntoleranceMedicaments = new LinkedList<ClientIntoleranceMedicament>();

    public List<ClientIntoleranceMedicament> getClientIntoleranceMedicaments() {
        return this.clientIntoleranceMedicaments;
    }

    public List<ClientIntoleranceMedicament> getActiveClientIntoleranceMedicaments() {
        List<ClientIntoleranceMedicament> activeClientIntoleranceMedicaments = new LinkedList<ClientIntoleranceMedicament>();
        for (ClientIntoleranceMedicament c : clientIntoleranceMedicaments) {
            if (c.isDeleted() == false) {
                activeClientIntoleranceMedicaments.add(c);
            }
        }
        return activeClientIntoleranceMedicaments;
    }

    public void addClientIntoleranceMedicament(final ClientIntoleranceMedicament clientIntoleranceMedicament) {
        this.clientIntoleranceMedicaments.add(clientIntoleranceMedicament);
        if (clientIntoleranceMedicament.getPatient() != this) {
            clientIntoleranceMedicament.setPatient(this);
        }
    }

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<ClientFlatDirectory> clientFlatDirectories = new LinkedList<ClientFlatDirectory>();

    public List<ClientFlatDirectory> getClientFlatDirectories() {
        return this.clientFlatDirectories;
    }

    public void addClientFlatDirectory(final ClientFlatDirectory clientFlatDirectory) {
        this.clientFlatDirectories.add(clientFlatDirectory);
        if (clientFlatDirectory.getPatient() != this) {
            clientFlatDirectory.setPatient(this);
        }
    }

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<ClientWork> clientWorks = new LinkedList<ClientWork>();

    public List<ClientWork> getClientWorks() {
        return clientWorks;
    }

    public List<ClientWork> getActiveClientWorks() {
        List<ClientWork> activeClientWorks = new LinkedList<ClientWork>();
        for (ClientWork c : clientWorks) {
            if (c.getDeleted() == false) {
                activeClientWorks.add(c);
            }
        }
        return activeClientWorks;
    }

    public void addClientWork(final ClientWork clientWork) {
        this.clientWorks.add(clientWork);
        if (clientWork.getPatient() != this) {
            clientWork.setPatient(this);
        }
    }


    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<ClientSocStatus> clientSocStatuses = new LinkedList<ClientSocStatus>();

    public List<ClientSocStatus> getClientSocStatuses() {
        return clientSocStatuses;
    }

    public List<ClientSocStatus> getActiveClientSocStatuses() {
        List<ClientSocStatus> activeSocStatuses = new LinkedList<ClientSocStatus>();
        for (ClientSocStatus c : clientSocStatuses) {
            if (c.getDeleted() == false) {
                activeSocStatuses.add(c);
            }
        }
        return activeSocStatuses;
    }

    public void addClientSocStatus(final ClientSocStatus socStatus) {
        this.clientSocStatuses.add(socStatus);
        if (socStatus.getPatient() != this) {
            socStatus.setPatient(this);
        }
    }

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<TempInvalid> tempInvalids = new LinkedList<TempInvalid>();


    public List<TempInvalid> getTempInvalids() {
        return tempInvalids;
    }

    public void addTempInvalid(final TempInvalid tempInvalid) {
        this.tempInvalids.add(tempInvalid);
        if (tempInvalid.getPatient() != this) {
            tempInvalid.setPatient(this);
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

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
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

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
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
        if (this.id == null && other.id == null && this != other) {
            return false;
        }
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.korus.tmis.core.database.model.Patient[id=" + id + "]";
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public static Patient clone(Patient self) throws CloneNotSupportedException {
        Patient newPatient = (Patient) self.clone();

        newPatient.clientAddresses = new LinkedList<ClientAddress>();
        for (ClientAddress ca : self.getClientAddresses()) {
            newPatient.addClientAddress((ClientAddress) ca.clone());
        }
        newPatient.clientAllergies = new LinkedList<ClientAllergy>();
        for (ClientAllergy ca : self.getClientAllergies()) {
            newPatient.addClientAllergies((ClientAllergy) ca.clone());
        }
        newPatient.clientContacts = new LinkedList<ClientContact>();
        for (ClientContact ca : self.getClientContacts()) {
            newPatient.addClientContact((ClientContact) ca.clone());
        }
        newPatient.clientDocuments = new LinkedList<ClientDocument>();
        for (ClientDocument ca : self.getClientDocuments()) {
            newPatient.addClientDocument((ClientDocument) ca.clone());
        }
        newPatient.clientIntoleranceMedicaments = new LinkedList<ClientIntoleranceMedicament>();
        for (ClientIntoleranceMedicament ca : self.getClientIntoleranceMedicaments()) {
            newPatient.addClientIntoleranceMedicament((ClientIntoleranceMedicament) ca.clone());
        }
        newPatient.clientPolicies = new LinkedList<ClientPolicy>();
        for (ClientPolicy ca : self.getActiveClientPolicies()) {
            newPatient.addClientPolicies((ClientPolicy) ca.clone());
        }
        newPatient.clientRelatives = new LinkedList<ClientRelation>();
        for (ClientRelation ca : self.getClientRelatives()) {
            newPatient.addClientRelative((ClientRelation) ca.clone());
        }
        newPatient.clientSocStatuses = new LinkedList<ClientSocStatus>();
        for (ClientSocStatus ca : self.getClientSocStatuses()) {
            newPatient.addClientSocStatus((ClientSocStatus) ca.clone());
        }
        newPatient.clientWorks = new LinkedList<ClientWork>();
        for (ClientWork ca : self.getClientWorks()) {
            newPatient.addClientWork((ClientWork) ca.clone());
        }
        return newPatient;
    }

    /**
     * Детальное описание пациента
     * @return строка с описанием
     */
    public String getInfoString(){
        return new StringBuilder("Patient[id=").append(id)
                .append(' ').append(lastName)
                .append(' ').append(firstName)
                .append(' ').append(patrName)
                .append(" sex:").append(sex)
                .append(" birthDate:").append(new SimpleDateFormat("yyyy-MM-dd").format(birthDate))
                .append(']').toString();
    }
}
