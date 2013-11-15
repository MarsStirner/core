package ru.korus.tmis.communication;

import ru.korus.tmis.core.entity.model.Patient;

import java.util.Date;

/**
 * Author: Upatov Egor <br>
 * Date: 14.11.13, 18:44 <br>
 * Company: Korus Consulting IT <br>
 * Description: Талончик на прием к врачу <br>
 */
public class Ticket {

    private Date begTime;
    private Date endTime;
    private boolean free;
    private boolean available;
    private Patient patient;

    public Ticket(
            final Date begTime,
            final Date endTime,
            final boolean free,
            final boolean available,
            final Patient patient){
        this.begTime = begTime;
        this.endTime = endTime;
        this.free = free;
        this.available = available;
        this.patient = patient;
    }

    public Ticket(){

    }

    public Date getBegTime() {
        return begTime;
    }

    public void setBegTime(Date begTime) {
        this.begTime = begTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
