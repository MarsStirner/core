package ru.korus.tmis.pix.sda;

import ru.korus.tmis.core.entity.model.RbPost;
import ru.korus.tmis.core.entity.model.Speciality;
import ru.korus.tmis.core.entity.model.Staff;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        28.01.14, 10:07 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public class EmployeeInfo {
    //Код врача
    private final String code;
    //Специальность
    private final CodeNamePair spesialty;
    //Должность
    private final CodeNamePair role;
    //Снилс
    private final String snils;
    //Фамилия
    private final String family;
    //Имя
    private final String given;
    //Отчество
    private final String middleName;

    private EmployeeInfo(Staff createPerson) {
        code = createPerson.getCode();
        final Speciality speciality = createPerson.getSpeciality();
        spesialty = speciality == null ? null : new CodeNamePair(speciality.getCode(), speciality.getName());
        final RbPost post = createPerson.getPost();
        role = post == null ? null : new CodeNamePair(post.getCode(), post.getName());
        snils = createPerson.getSnils();
        family = createPerson.getLastName();
        given = createPerson.getFirstName();
        middleName = createPerson.getPatrName();
    }


    public static EmployeeInfo newInstance(Staff createPerson) {
        return createPerson == null ? null : new EmployeeInfo(createPerson);
    }

    public String getCode() {
        return code;
    }

    public CodeNamePair getSpesialty() {
        return spesialty;
    }

    public CodeNamePair getRole() {
        return role;
    }

    public String getSnils() {
        return snils;
    }

    public String getFamily() {
        return family;
    }

    public String getGiven() {
        return given;
    }

    public String getMiddleName() {
        return middleName;
    }
}
