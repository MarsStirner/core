package ru.korus.tmis.core.exception;

import javax.xml.ws.WebFault;

@WebFault(name = "ExceptionFault", faultBean = "ru.korus.tmis.core.exception.FaultBean")
public class CoreException extends Exception {

    private final FaultBean faultBean;

    public FaultBean getFaultInfo() {
        return faultBean;
    }

    public CoreException() {
        faultBean = new FaultBean(0, "");
    }

    public CoreException(final int id) {
        faultBean = new FaultBean(id, "");
    }

    public CoreException(final String message) {
        super(message);
        faultBean = new FaultBean(0, message);
    }

    public CoreException(final int id, final String message) {
        super(message);
        faultBean = new FaultBean(id, message);
    }

    public int getId() {
        return faultBean.getId();
    }

    @Override
    public String getMessage() {
        return faultBean.getMessage();
    }
}
