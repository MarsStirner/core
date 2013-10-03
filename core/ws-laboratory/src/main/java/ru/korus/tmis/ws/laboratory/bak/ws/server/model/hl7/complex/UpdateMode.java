
package ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UpdateMode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="UpdateMode">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="A"/>
 *     &lt;enumeration value="D"/>
 *     &lt;enumeration value="R"/>
 *     &lt;enumeration value="AR"/>
 *     &lt;enumeration value="N"/>
 *     &lt;enumeration value="U"/>
 *     &lt;enumeration value="K"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "UpdateMode")
@XmlEnum
public enum UpdateMode {

    A,
    D,
    R,
    AR,
    N,
    U,
    K;

    public String value() {
        return name();
    }

    public static UpdateMode fromValue(String v) {
        return valueOf(v);
    }

}
