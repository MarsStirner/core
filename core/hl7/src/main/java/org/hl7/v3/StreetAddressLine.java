
package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StreetAddressLine.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="StreetAddressLine">
 *   &lt;restriction base="{urn:hl7-org:v3}cs">
 *     &lt;enumeration value="BNN"/>
 *     &lt;enumeration value="BNR"/>
 *     &lt;enumeration value="BNS"/>
 *     &lt;enumeration value="DIR"/>
 *     &lt;enumeration value="POB"/>
 *     &lt;enumeration value="SAL"/>
 *     &lt;enumeration value="STB"/>
 *     &lt;enumeration value="STR"/>
 *     &lt;enumeration value="STTYP"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "StreetAddressLine")
@XmlEnum
public enum StreetAddressLine {

    BNN,
    BNR,
    BNS,
    DIR,
    POB,
    SAL,
    STB,
    STR,
    STTYP;

    public String value() {
        return name();
    }

    public static StreetAddressLine fromValue(String v) {
        return valueOf(v);
    }

}
