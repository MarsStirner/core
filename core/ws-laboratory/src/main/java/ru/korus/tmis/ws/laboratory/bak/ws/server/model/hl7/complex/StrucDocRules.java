
package ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StrucDoc.Rules.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="StrucDoc.Rules">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="none"/>
 *     &lt;enumeration value="groups"/>
 *     &lt;enumeration value="rows"/>
 *     &lt;enumeration value="cols"/>
 *     &lt;enumeration value="all"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "StrucDoc.Rules")
@XmlEnum
public enum StrucDocRules {

    @XmlEnumValue("none")
    NONE("none"),
    @XmlEnumValue("groups")
    GROUPS("groups"),
    @XmlEnumValue("rows")
    ROWS("rows"),
    @XmlEnumValue("cols")
    COLS("cols"),
    @XmlEnumValue("all")
    ALL("all");
    private final String value;

    StrucDocRules(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static StrucDocRules fromValue(String v) {
        for (StrucDocRules c: StrucDocRules.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
