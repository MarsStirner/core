
package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RoleClassCoveredParty.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="RoleClassCoveredParty">
 *   &lt;restriction base="{urn:hl7-org:v3}cs">
 *     &lt;enumeration value="CLAIM"/>
 *     &lt;enumeration value="COVPTY"/>
 *     &lt;enumeration value="DEPEN"/>
 *     &lt;enumeration value="INDIV"/>
 *     &lt;enumeration value="NAMED"/>
 *     &lt;enumeration value="PROG"/>
 *     &lt;enumeration value="SUBSCR"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "RoleClassCoveredParty")
@XmlEnum
public enum RoleClassCoveredParty {

    CLAIM,
    COVPTY,
    DEPEN,
    INDIV,
    NAMED,
    PROG,
    SUBSCR;

    public String value() {
        return name();
    }

    public static RoleClassCoveredParty fromValue(String v) {
        return valueOf(v);
    }

}
