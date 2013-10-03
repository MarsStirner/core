
package ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TelecommunicationCapability.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TelecommunicationCapability">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="voice"/>
 *     &lt;enumeration value="fax"/>
 *     &lt;enumeration value="data"/>
 *     &lt;enumeration value="tty"/>
 *     &lt;enumeration value="sms"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TelecommunicationCapability")
@XmlEnum
public enum TelecommunicationCapability {

    @XmlEnumValue("voice")
    VOICE("voice"),
    @XmlEnumValue("fax")
    FAX("fax"),
    @XmlEnumValue("data")
    DATA("data"),
    @XmlEnumValue("tty")
    TTY("tty"),
    @XmlEnumValue("sms")
    SMS("sms");
    private final String value;

    TelecommunicationCapability(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TelecommunicationCapability fromValue(String v) {
        for (TelecommunicationCapability c: TelecommunicationCapability.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
