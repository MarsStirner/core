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
    private final CodeNameSystem spesialty;
    //Должность
    private final CodeNameSystem role;
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
        spesialty = speciality == null ? null : RbManager.get(RbManager.RbType.rbSpeciality,
                CodeNameSystem.newInstance(speciality.getCode(), speciality.getName(), "1.2.643.5.1.13.2.1.1.181"));
        final RbPost post = createPerson.getPost();
        role = post == null ? null : RbManager.get(RbManager.RbType.rbPost,
                CodeNameSystem.newInstance(post.getCode(), post.getName(), "1.2.643.5.1.13.2.1.1.607"));
        String snils = initSnils(createPerson);
        this.snils = snils;
        family = createPerson.getLastName();
        given = createPerson.getFirstName();
        middleName = createPerson.getPatrName();
    }

    private String initSnils(Staff createPerson) {
        String snils = createPerson.getSnils();
        if(snils != null ) {
            snils = snils.trim();
            if(snils.length() == 11 ) {
                snils = String.format("%s-%s-%s %s",
                        snils.substring(0,3), snils.substring(3,6), snils.substring(6,9), snils.substring(9));
            }
        }
        return snils;
    }


    public static EmployeeInfo newInstance(Staff createPerson) {
        return createPerson == null ? null : new EmployeeInfo(createPerson);
    }

    public String getCode() {
        return code;
    }

    public CodeNameSystem getSpesialty() {
        return spesialty;
    }

    public CodeNameSystem getRole() {
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
