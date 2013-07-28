package ru.sda.data;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author anosov
 *         Date: 27.07.13 19:16
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Patient", propOrder = {
        "name",
        "gender",
        "birthTime",
        "patientNumbers",
        "addresses",
        "contactInfo"
})
public class Patient {

    @XmlElement(required = true)
    protected Name name;

    @XmlElement(required = true)
    protected Gender gender;

    @XmlElement(required = true)
    protected Date birthTime;

    @XmlElement(required = true)
    protected PatientNumbers patientNumbers;

    @XmlElement(required = true)
    protected List<Address> addresses;

    public PatientNumbers getPatientNumbers() {
        return patientNumbers;
    }

    public void setPatientNumbers(PatientNumbers patientNumbers) {
        this.patientNumbers = patientNumbers;
    }

    public List<Address> getAddresses() {
        if (addresses == null) {
            addresses = new ArrayList<Address>();
        }
        return this.addresses;
    }

    @XmlElement(required = true)
    protected ContactInfo contactInfo;

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
}
