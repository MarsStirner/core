package ru.korus.tmis.hsct.external;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Author: Upatov Egor <br>
 * Date: 15.01.2016, 16:40 <br>
 * Company: hitsl (Hi-Tech Solutions) <br>
 * Description: <br>
 */
@XmlRootElement(name = "doctor")
@XmlAccessorType(XmlAccessType.FIELD)
public class Doctor {

    /**
     * ID врача mis_id
     */
    @XmlElement(name="mis_id")
    private String misId;

    /**
     * Фамилия врача   family_name
     */
    @XmlElement(name="family_name")
    private String lastName;

    /**
     * Имя врача given_name
     */
    @XmlElement(name="given_name")
    private String firstName;

    /**
     * Отчество врача  patronymic
     */
    @XmlElement(name="patronymic")
    private String patrName;

    /**
     * Отделение врача
     */
    @XmlElement(name="department_code")
    private String departmentCode;

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

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(final String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public Doctor() {
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Doctor doctor = (Doctor) o;

        return !(misId != null ? !misId.equals(doctor.misId) : doctor.misId != null);

    }

    @Override
    public int hashCode() {
        return misId != null ? misId.hashCode() : 0;
    }
}
