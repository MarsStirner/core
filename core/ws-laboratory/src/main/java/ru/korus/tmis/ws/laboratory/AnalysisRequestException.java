package ru.korus.tmis.ws.laboratory;

import javax.xml.ws.WebFault;

@WebFault(name = "ExceptionFault",
        faultBean = "ru.korus.tmis.ws.laboratory.FaultBean")
public class AnalysisRequestException extends Exception {

    private final FaultBean faultBean;

    public FaultBean getFaultInfo() {
        return faultBean;
    }

    public AnalysisRequestException() {
        faultBean = new FaultBean(0, "");
    }

    public AnalysisRequestException(final String message) {
        super(message);
        faultBean = new FaultBean(0, message);
    }

    public AnalysisRequestException(final String message, final Throwable cause) {
        super(message, cause);
        faultBean = new FaultBean(0, message);
    }

    public AnalysisRequestException(final Throwable cause) {
        super(cause);
        faultBean = new FaultBean(0, "");
    }

    public int getId() {
        return faultBean.getId();
    }

    @Override
    public String getMessage() {
        return faultBean.getMessage();
    }
}
