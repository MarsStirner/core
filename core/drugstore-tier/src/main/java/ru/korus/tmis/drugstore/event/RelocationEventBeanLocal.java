package ru.korus.tmis.drugstore.event;

import ru.korus.tmis.core.event.Notification;

import javax.ejb.Local;
import javax.enterprise.event.Observes;

@Local
public interface RelocationEventBeanLocal {
    void trigger(@Observes Notification n);
}
