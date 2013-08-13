
package ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Swish.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="Swish">
 *   &lt;restriction base="{urn:hl7-org:v3}cs">
 *     &lt;enumeration value="SWISHSPIT"/>
 *     &lt;enumeration value="SWISHSWAL"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "Swish")
@XmlEnum
public enum Swish {

    SWISHSPIT,
    SWISHSWAL;

    public String value() {
        return name();
    }

    public static Swish fromValue(String v) {
        return valueOf(v);
    }

}
