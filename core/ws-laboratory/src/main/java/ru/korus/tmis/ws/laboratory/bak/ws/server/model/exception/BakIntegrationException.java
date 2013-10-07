package ru.korus.tmis.ws.laboratory.bak.ws.server.model.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.exception.FaultBean;
import ru.korus.tmis.core.exception.SOAPFaultInfo;

import javax.xml.ws.WebFault;

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
