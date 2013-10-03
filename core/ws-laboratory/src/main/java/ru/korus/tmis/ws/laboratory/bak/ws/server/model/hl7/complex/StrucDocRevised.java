
package ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StrucDoc.Revised.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="StrucDoc.Revised">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="insert"/>
 *     &lt;enumeration value="delete"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "StrucDoc.Revised")
@XmlEnum
public enum StrucDocRevised {

    @XmlEnumValue("insert")
    INSERT("insert"),
    @XmlEnumValue("delete")
    DELETE("delete");
    private final String value;

    StrucDocRevised(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static StrucDocRevised fromValue(String v) {
        for (StrucDocRevised c: StrucDocRevised.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
