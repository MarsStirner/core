
package ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StrucDoc.VAlign.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="StrucDoc.VAlign">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="top"/>
 *     &lt;enumeration value="middle"/>
 *     &lt;enumeration value="bottom"/>
 *     &lt;enumeration value="baseline"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "StrucDoc.VAlign")
@XmlEnum
public enum StrucDocVAlign {

    @XmlEnumValue("top")
    TOP("top"),
    @XmlEnumValue("middle")
    MIDDLE("middle"),
    @XmlEnumValue("bottom")
    BOTTOM("bottom"),
    @XmlEnumValue("baseline")
    BASELINE("baseline");
    private final String value;

    StrucDocVAlign(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static StrucDocVAlign fromValue(String v) {
        for (StrucDocVAlign c: StrucDocVAlign.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
