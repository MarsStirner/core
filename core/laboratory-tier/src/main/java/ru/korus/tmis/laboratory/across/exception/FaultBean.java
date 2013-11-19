package ru.korus.tmis.laboratory.across.exception;

public class FaultBean {

    private int id;
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
