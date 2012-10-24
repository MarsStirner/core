
package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ActReleationshipICSRInvestigation.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ActReleationshipICSRInvestigation">
 *   &lt;restriction base="{urn:hl7-org:v3}cs">
 *     &lt;enumeration value="RPLC"/>
 *     &lt;enumeration value="SEQL"/>
 *     &lt;enumeration value="SPRT"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ActReleationshipICSRInvestigation")
@XmlEnum
public enum ActReleationshipICSRInvestigation {

    RPLC,
    SEQL,
    SPRT;

    public String value() {
        return name();
    }

    public static ActReleationshipICSRInvestigation fromValue(String v) {
        return valueOf(v);
    }

}
