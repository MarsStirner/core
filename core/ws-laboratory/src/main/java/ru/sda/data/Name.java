package ru.sda.data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author anosov
 *         Date: 27.07.13 23:11
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "name", propOrder = {
        "familyName",
        "givenName",
        "middleName"
})
public class Name {
    @XmlElement(required = true)
    protected String familyName;
    @XmlElement(required = true)
    protected String givenName;
    @XmlElement(required = true)
    protected String middleName;

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    @Override
    public String toString() {
        return "familyName='" + familyName + '\'' +
                ", givenName='" + givenName + '\'' +
                ", middleName='" + middleName + '\'';
    }
}
