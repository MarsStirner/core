package ru.korus.tmis.core.exception;

import java.io.Serializable;

public class FaultBean implements Serializable {
//    @XmlElement(name = "faultcode")
    private int id;
//    @XmlElement(name = "faultstring")
    private String message;

    public FaultBean() {
    }

    public FaultBean(final int id, final String message) {
        this.id = id;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }
}
