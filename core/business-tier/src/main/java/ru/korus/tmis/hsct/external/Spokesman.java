package ru.korus.tmis.hsct.external;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Author: Upatov Egor <br>
 * Date: 15.01.2016, 17:53 <br>
 * Company: hitsl (Hi-Tech Solutions) <br>
 * Description: <br>
 */
@XmlRootElement(name = "representative")
@XmlAccessorType(XmlAccessType.FIELD)
public class Spokesman {

    /**
     * Фамилия представителя пациента   family_name
     */
    @XmlElement(name = "family_name")
    private String lastName;

    /**
     * Имя представителя пациента given_name
     */
    @XmlElement(name = "given_name")
    private String firstName;

    /**
     * Отчество представителя пациента   patronymic
     */
    @XmlElement(name = "patronymic")
    private String patrName;


    /**
     * Электронная почта представителя пациента email
     */
    @XmlElement(name = "email")
    private String email;


    /**
     * Телефон представителя пациента phone
     */
    @XmlElement(name = "phone")
    private String phone;

    public Spokesman() {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(final String phone) {
        this.phone = phone;
    }

    @Override
    public int hashCode() {
        int result = lastName != null ? lastName.hashCode() : 0;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (patrName != null ? patrName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Spokesman spokesman = (Spokesman) o;

        if (lastName != null ? !lastName.equals(spokesman.lastName) : spokesman.lastName != null) {
            return false;
        }
        if (firstName != null ? !firstName.equals(spokesman.firstName) : spokesman.firstName != null) {
            return false;
        }
        if (patrName != null ? !patrName.equals(spokesman.patrName) : spokesman.patrName != null) {
            return false;
        }
        if (email != null ? !email.equals(spokesman.email) : spokesman.email != null) {
            return false;
        }
        return !(phone != null ? !phone.equals(spokesman.phone) : spokesman.phone != null);

    }
}
