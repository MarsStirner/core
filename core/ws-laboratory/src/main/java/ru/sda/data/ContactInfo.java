package ru.sda.data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author anosov
 *         Date: 28.07.13 0:52
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "contactInfo", propOrder = {
        "homePhoneNumber",
        "workPhoneNumber",
        "mobilePhoneNumber",
        "emailAddress"
})
public class ContactInfo {
    @XmlElement
    protected String homePhoneNumber;
    @XmlElement
    protected String workPhoneNumber;
    @XmlElement
    protected String mobilePhoneNumber;
    @XmlElement
    protected String emailAddress;

    public String getHomePhoneNumber() {
        return homePhoneNumber;
    }

    public void setHomePhoneNumber(String homePhoneNumber) {
        this.homePhoneNumber = homePhoneNumber;
    }

    public String getWorkPhoneNumber() {
        return workPhoneNumber;
    }

    public void setWorkPhoneNumber(String workPhoneNumber) {
        this.workPhoneNumber = workPhoneNumber;
    }

    public String getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
