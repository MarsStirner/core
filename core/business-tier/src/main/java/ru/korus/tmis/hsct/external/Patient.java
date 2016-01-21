package ru.korus.tmis.hsct.external;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Author: Upatov Egor <br>
 * Date: 15.01.2016, 16:23 <br>
 * Company: hitsl (Hi-Tech Solutions) <br>
 * Description: <br>
 */
@XmlRootElement(name = "patient")
@XmlAccessorType(XmlAccessType.FIELD)
public class Patient {

    /**
     * ID пациента в МИС mis_id
     */
    @XmlElement(name="mis_id")
    private String misId;

    /**
     * Фамилия пациента   family_name
     */
    @XmlElement(name="family_name")
    private String lastName;

    /**
     * Имя пациента given_name
     */
    @XmlElement(name="given_name")
    private String firstName;

    /**
     * Отчество пациента   patronymic
     */
    @XmlElement(name="patronymic")
    private String patrName;

    /**
     * Дата рождения пациента  birth_date
     */
    @XmlElement(name="birth_date")
    private String birthDate;

    public String getMisId() {
        return misId;
    }

    public void setMisId(final String misId) {
        this.misId = misId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getPatrName() {
        return patrName;
    }

    public void setPatrName(final String patrName) {
        this.patrName = patrName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(final String birthDate) {
        this.birthDate = birthDate;
    }

    public Patient() {
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Patient patient = (Patient) o;

        return !(misId != null ? !misId.equals(patient.misId) : patient.misId != null);

    }

    @Override
    public int hashCode() {
        return misId != null ? misId.hashCode() : 0;
    }
}
