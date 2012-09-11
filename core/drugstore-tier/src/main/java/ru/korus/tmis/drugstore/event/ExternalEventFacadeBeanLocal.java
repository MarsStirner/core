package ru.korus.tmis.drugstore.event;

import ru.korus.tmis.core.event.CreateActionNotification;
import ru.korus.tmis.core.event.PrescriptionChangedNotification;

import javax.ejb.Local;

@Local
public interface ExternalEventFacadeBeanLocal {
    void triggerCreateActionNotification(CreateActionNotification can);

    void triggerPrescriptionChangedNotification(PrescriptionChangedNotification pcn);
}
