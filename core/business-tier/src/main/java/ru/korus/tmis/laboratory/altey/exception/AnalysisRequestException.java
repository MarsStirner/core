package ru.korus.tmis.laboratory.altey.exception;

import javax.xml.ws.WebFault;

@WebFault(name = "ExceptionFault",
        faultBean = "ru.korus.tmis.laboratory.FaultBean")
public class AnalysisRequestException extends Exception {

    final FaultBean faultBean;

    public FaultBean getFaultInfo() {
        return faultBean;
    }

    public AnalysisRequestException() {
        faultBean = new FaultBean(0, "");
    }

    public AnalysisRequestException(String message) {
        super(message);
        faultBean = new FaultBean(0, message);
    }

    public AnalysisRequestException(String message, Throwable cause) {
        super(message, cause);
        faultBean = new FaultBean(0, message);
    }

    public AnalysisRequestException(Throwable cause) {
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
