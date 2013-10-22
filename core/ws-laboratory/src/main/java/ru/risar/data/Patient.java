package ru.risar.data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Date;

/**
 * @author anosov
 *         Date: 27.07.13 19:16
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Patient", propOrder = {
        "identifier",
        "name",
        "gender",
        "birthTime",
        "socStatus",
        "bloodType",
        "patientNumbers",
        "addresses",
        "contactInfo"
})
public class Patient {

    @XmlElement(required = true)
    protected String identifier;

    @XmlElement(required = true)
    protected Name name;

    @XmlElement(required = true)
    protected Gender gender;

    @XmlElement(required = true)
    protected Date birthTime;

    /**
     * Социальный статус
     *
     * Справочник 'Социальный статус' {rbsocstatustype}
     */
    protected Integer socStatus;

    /**
     * Группа крови
     *
     * Справочник 'Группа крови' {rbbloodtype}
     */
    protected String bloodType;

    @XmlElement(required = true)
    protected PatientNumbers patientNumbers;

    @XmlElement(required = true)
    protected Addresses addresses;

    @XmlElement(required = true)
    protected ContactInfo contactInfo;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Addresses getAddressesList() {
        return addresses;
    }

    public void setAddresses(Addresses addresses) {
        this.addresses = addresses;
    }

    public PatientNumbers getPatientNumberList() {
        return patientNumbers;
    }

    public void setPatientNumbers(PatientNumbers patientNumbers) {
        this.patientNumbers = patientNumbers;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Date getBirthTime() {
        return birthTime;
    }

    public void setBirthTime(Date birthTime) {
        this.birthTime = birthTime;
    }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }

    public Integer getSocStatus() {
        return socStatus;
    }

    public void setSocStatus(Integer socStatus) {
        this.socStatus = socStatus;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }
}
