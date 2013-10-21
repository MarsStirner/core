package ru.korus.tmis.laboratory.bak.ws.server.model.exception;

import ru.korus.tmis.core.exception.SOAPFaultInfo;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        04.10.13, 19:58 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
//@WebFault(name = "ExceptionFault", faultBean = "ru.korus.tmis.core.exception.SOAPFaultInfo")
public class BakIntegrationException  extends Exception {

    private static final long serialVersionUID = 1L;

    private SOAPFaultInfo faultInfo;

    public BakIntegrationException(SOAPFaultInfo fault) {
        super();
        this.faultInfo = fault;
    }

    public BakIntegrationException(SOAPFaultInfo fault, Throwable cause) {
        super(cause);
        this.faultInfo = fault;
    }

    public SOAPFaultInfo getFaultInfo() {
        return faultInfo;
    }

    public void setFaultInfo(SOAPFaultInfo faultInfo) {
        this.faultInfo = faultInfo;
    }
}
