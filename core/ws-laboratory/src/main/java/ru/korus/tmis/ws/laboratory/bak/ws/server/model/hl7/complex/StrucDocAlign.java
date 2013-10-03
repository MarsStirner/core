
package ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StrucDoc.Align.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="StrucDoc.Align">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="left"/>
 *     &lt;enumeration value="center"/>
 *     &lt;enumeration value="right"/>
 *     &lt;enumeration value="justify"/>
 *     &lt;enumeration value="char"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "StrucDoc.Align")
@XmlEnum
public enum StrucDocAlign {

    @XmlEnumValue("left")
    LEFT("left"),
    @XmlEnumValue("center")
    CENTER("center"),
    @XmlEnumValue("right")
    RIGHT("right"),
    @XmlEnumValue("justify")
    JUSTIFY("justify"),
    @XmlEnumValue("char")
    CHAR("char");
    private final String value;

    StrucDocAlign(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static StrucDocAlign fromValue(String v) {
        for (StrucDocAlign c: StrucDocAlign.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
