
package ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StrucDoc.ListType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="StrucDoc.ListType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ordered"/>
 *     &lt;enumeration value="unordered"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "StrucDoc.ListType")
@XmlEnum
public enum StrucDocListType {

    @XmlEnumValue("ordered")
    ORDERED("ordered"),
    @XmlEnumValue("unordered")
    UNORDERED("unordered");
    private final String value;

    StrucDocListType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static StrucDocListType fromValue(String v) {
        for (StrucDocListType c: StrucDocListType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
