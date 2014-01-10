package ru.korus.tmis.schedule;

import ru.korus.tmis.core.entity.model.AppointmentType;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        25.12.13, 18:27 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public class QueueActionParam {

    private String hospitalUidFrom = "";

    private String note = "";

    private PacientInQueueType pacientInQueueType = PacientInQueueType.QUEUE;

    private AppointmentType appointmentType = AppointmentType.NONE;

    public String getHospitalUidFrom() {
        return hospitalUidFrom;
    }

    public String getNote() {
        return note;
    }

    public PacientInQueueType getPacientInQueueType() {
        return pacientInQueueType;
    }

    public AppointmentType getAppointmentType() {
        return appointmentType;
    }

    public QueueActionParam setHospitalUidFrom(String hospitalUidFrom) {
        this.hospitalUidFrom = hospitalUidFrom == null ? "" : hospitalUidFrom;
        return this;
    }

    public QueueActionParam setNote(String note) {
        this.note = note == null ? "" : note;
        return this;
    }

    public QueueActionParam setPacientInQueueType(PacientInQueueType pacientInQueueType) {
        this.pacientInQueueType = pacientInQueueType;
        return this;
    }

    public QueueActionParam setAppointmentType(AppointmentType appointmentType) {
        this.appointmentType = appointmentType;
        return this;
    }
}
