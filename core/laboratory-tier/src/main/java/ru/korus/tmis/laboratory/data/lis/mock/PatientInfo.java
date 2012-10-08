package ru.korus.tmis.laboratory.data.lis.mock;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlSchemaType;
import java.util.Date;

/**
 * Информация о пациенте
 */
public class PatientInfo {

    /**
     * Code (string) -- уникальный код пациента в МИС (Client.id)
     */
    private int code;

    /**
     * Фамилия
     */
    private String lastName;

    /**
     * Имя
     */
    private String firstName;

    /**
     * Отчество
     */
    private String middleName;

    /**
     * Дата рождения
     */
    private Date birthDate;

    /**
     * Перечисление для пола пациента
     */
    @XmlEnum(Integer.class)
    public enum Gender {
        @XmlEnumValue("1")
        MEN,
        @XmlEnumValue("2")
        WOMEN,
        /**
         * Не определен
         */
        @XmlEnumValue("3")
        UNKNOWN
    }

    ;

    /**
     * Пол пациента
     */
    Gender gender;

    @XmlElement(name = "patientMisId")
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @XmlElement(name = "patientFamily", required = true)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @XmlElement(name = "patientName", required = true)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @XmlElement(name = "patientPatronum", required = true)
    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    @XmlElement(name = "patientBirthDate", required = true)
    @XmlSchemaType(name = "date")
    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    @XmlElement(name = "patientSex", required = true)
    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}
