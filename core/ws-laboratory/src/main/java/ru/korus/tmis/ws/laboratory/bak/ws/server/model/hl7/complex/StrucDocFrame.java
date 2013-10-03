
package ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StrucDoc.Frame.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="StrucDoc.Frame">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="void"/>
 *     &lt;enumeration value="above"/>
 *     &lt;enumeration value="below"/>
 *     &lt;enumeration value="hsides"/>
 *     &lt;enumeration value="lhs"/>
 *     &lt;enumeration value="rhs"/>
 *     &lt;enumeration value="vsides"/>
 *     &lt;enumeration value="box"/>
 *     &lt;enumeration value="border"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "StrucDoc.Frame")
@XmlEnum
public enum StrucDocFrame {

    @XmlEnumValue("void")
    VOID("void"),
    @XmlEnumValue("above")
    ABOVE("above"),
    @XmlEnumValue("below")
    BELOW("below"),
    @XmlEnumValue("hsides")
    HSIDES("hsides"),
    @XmlEnumValue("lhs")
    LHS("lhs"),
    @XmlEnumValue("rhs")
    RHS("rhs"),
    @XmlEnumValue("vsides")
    VSIDES("vsides"),
    @XmlEnumValue("box")
    BOX("box"),
    @XmlEnumValue("border")
    BORDER("border");
    private final String value;

    StrucDocFrame(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static StrucDocFrame fromValue(String v) {
        for (StrucDocFrame c: StrucDocFrame.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
