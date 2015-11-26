package ru.korus.tmis.schedule;

import org.joda.time.LocalTime;
import ru.korus.tmis.core.entity.model.Patient;
import ru.korus.tmis.core.entity.model.ScheduleClientTicket;
import ru.korus.tmis.core.entity.model.ScheduleTicket;

import java.util.Iterator;

/**
 * Author: Upatov Egor <br>
 * Date: 11.06.2014, 18:09 <br>
 * Company: Korus Consulting IT <br>
 * Description: <br>
 */
public class ScheduleTicketExtended implements Comparable<ScheduleTicketExtended> {
    private boolean available;
    private ScheduleTicket ticket;
    private LocalTime begTime;
    private LocalTime endTime;
    private boolean free;
    private Patient patient;

    public ScheduleTicketExtended(final ScheduleTicket ticket, final boolean available) {
        this.available = available;
        this.free = ticket.isFree();
        this.ticket = ticket;
        this.begTime = new LocalTime(ticket.getBegTime());
        this.endTime = new LocalTime(ticket.getEndTime());
        if (!free) {
            ScheduleClientTicket clientTicket = ticket.getActiveClientTicketList().iterator().next();
            patient = clientTicket.getClient();
        }
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public ScheduleTicket getTicket() {
        return ticket;
    }

    public void setTicket(ScheduleTicket ticket) {
        this.ticket = ticket;
    }

    public LocalTime getBegTime() {
        return begTime;
    }

    public void setBegTime(LocalTime begTime) {
        this.begTime = begTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @Override
    public int compareTo(ScheduleTicketExtended o) {
        return begTime.compareTo(o.getBegTime());
    }
}
