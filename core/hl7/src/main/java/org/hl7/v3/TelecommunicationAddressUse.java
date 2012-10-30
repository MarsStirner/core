
package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TelecommunicationAddressUse.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TelecommunicationAddressUse">
 *   &lt;restriction base="{urn:hl7-org:v3}cs">
 *     &lt;enumeration value="BAD"/>
 *     &lt;enumeration value="CONF"/>
 *     &lt;enumeration value="H"/>
 *     &lt;enumeration value="HP"/>
 *     &lt;enumeration value="HV"/>
 *     &lt;enumeration value="TMP"/>
 *     &lt;enumeration value="WP"/>
 *     &lt;enumeration value="DIR"/>
 *     &lt;enumeration value="PUB"/>
 *     &lt;enumeration value="AS"/>
 *     &lt;enumeration value="EC"/>
 *     &lt;enumeration value="MC"/>
 *     &lt;enumeration value="PG"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TelecommunicationAddressUse")
@XmlEnum
public enum TelecommunicationAddressUse {

    BAD,
    CONF,
    H,
    HP,
    HV,
    TMP,
    WP,
    DIR,
    PUB,
    AS,
    EC,
    MC,
    PG;

    public String value() {
        return name();
    }

    public static TelecommunicationAddressUse fromValue(String v) {
        return valueOf(v);
    }

}
