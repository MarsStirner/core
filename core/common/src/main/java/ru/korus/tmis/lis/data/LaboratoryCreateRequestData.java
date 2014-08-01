package ru.korus.tmis.lis.data;

import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.entity.model.Event;
import ru.korus.tmis.core.entity.model.Patient;

import java.io.Serializable;

/**
 * Author: <a href="mailto:alexey.kislin@gmail.com">Alexey Kislin</a>
 * Date: 8/1/14
 * Time: 3:42 PM
 */
public class LaboratoryCreateRequestData implements Serializable {

    public static final long serialVersionUID = 1L;

    private Action action;

    private DiagnosticRequestInfo requestInfo;

    private Patient patient;

    private Event event;

    private BiomaterialInfo biomaterialInfo;

    private OrderInfo orderInfo;

    public LaboratoryCreateRequestData(Action a, DiagnosticRequestInfo r, Patient p, Event e, BiomaterialInfo b, OrderInfo o) {
        action = a;
        requestInfo = r;
        patient = p;
        event = e;
        biomaterialInfo = b;
        orderInfo = o;
    }

    public Action getAction() {
        return action;
    }

    public DiagnosticRequestInfo getRequestInfo() {
        return requestInfo;
    }

    public Patient getPatient() {
        return patient;
    }

    public Event getEvent() {
        return event;
    }

    public BiomaterialInfo getBiomaterialInfo() {
        return biomaterialInfo;
    }

    public OrderInfo getOrderInfo() {
        return orderInfo;
    }
}
