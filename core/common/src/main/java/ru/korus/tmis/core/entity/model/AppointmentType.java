package ru.korus.tmis.core.entity.model;

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

    private AppointmentType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static AppointmentType getByValue(String appointmentType) {
        for(AppointmentType type : values()){
            if(type.getName().equalsIgnoreCase(appointmentType)){
                return type;
            }
        }
        return NONE;
    }
}
