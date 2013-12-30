package ru.rorus.tmis.schedule;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        25.12.13, 17:12 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public enum AppointmentType {
    NONE("0"),
    AMB("amb"),
    HOSPITAL("hospital"),
    POLYCLINIC("polyclinic"),
    DIAGNOSTICS("diagnostics"),
    PORTAL("portal"),
    OTHER_LPU("otherLPU");

    private final String name;

    AppointmentType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
