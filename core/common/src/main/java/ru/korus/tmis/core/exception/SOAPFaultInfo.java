package ru.korus.tmis.core.exception;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.io.Serializable;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        04.10.13, 21:23 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public class SOAPFaultInfo implements Serializable {
    @XmlElementWrapper(name = "stackTrace")
    private SOAPStackElement[] stackTrace;
    @XmlElement(name = "faultstring")
    private String faultCode;
    @XmlElement(name = "faultcode")
    private String faultString;
        /* what else your heart desires.  :-) */

    public SOAPFaultInfo(String faultCode, String faultString) {
        this.faultCode = faultCode;
        this.faultString = faultString;
    }

    public SOAPStackElement[] getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(SOAPStackElement[] stackTrace) {
        this.stackTrace = stackTrace;
    }

    public String getFaultCode() {
        return faultCode;
    }

    public void setFaultCode(String faultCode) {
        this.faultCode = faultCode;
    }

    public String getFaultString() {
        return faultString;
    }

    public void setFaultString(String faultString) {
        this.faultString = faultString;
    }
}
