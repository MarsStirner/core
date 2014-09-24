package ru.korus.tmis.prescription;

import ru.korus.tmis.core.entity.model.Patient;
import ru.korus.tmis.core.entity.model.Staff;

import javax.xml.bind.annotation.XmlType;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        15.05.14, 10:58 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@XmlType
public class PersonData {

    final private Integer id;

    final private Long birthDate;

    final private String firstName;

    final private String middleName;

    final private String lastName;

    public PersonData(Patient patient) {
        id = patient.getId();
        birthDate = patient.getBirthDate().getTime();
        firstName = patient.getFirstName();
        middleName = patient.getPatrName();
        lastName = patient.getLastName();
    }

    public PersonData(Staff staff) {
        id = staff.getId();
        birthDate = null;
        firstName = staff.getFirstName();
        middleName = staff.getPatrName();
        lastName = staff.getLastName();
    }

    public PersonData() {
        id = null;
        birthDate = null;
        firstName = null;
        middleName = null;
        lastName = null;
    }

    public Integer getId() {
        return id;
    }

    public Long getBirthDate() {
        return birthDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }
}
