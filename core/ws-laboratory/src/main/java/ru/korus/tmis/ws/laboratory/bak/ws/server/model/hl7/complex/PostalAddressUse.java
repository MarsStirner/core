
package ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PostalAddressUse.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="PostalAddressUse">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="H"/>
 *     &lt;enumeration value="HP"/>
 *     &lt;enumeration value="HV"/>
 *     &lt;enumeration value="WP"/>
 *     &lt;enumeration value="DIR"/>
 *     &lt;enumeration value="PUB"/>
 *     &lt;enumeration value="BAD"/>
 *     &lt;enumeration value="PHYS"/>
 *     &lt;enumeration value="PST"/>
 *     &lt;enumeration value="TMP"/>
 *     &lt;enumeration value="ABC"/>
 *     &lt;enumeration value="IDE"/>
 *     &lt;enumeration value="SYL"/>
 *     &lt;enumeration value="SRCH"/>
 *     &lt;enumeration value="SNDX"/>
 *     &lt;enumeration value="PHON"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "PostalAddressUse")
@XmlEnum
public enum PostalAddressUse {

    H,
    HP,
    HV,
    WP,
    DIR,
    PUB,
    BAD,
    PHYS,
    PST,
    TMP,
    ABC,
    IDE,
    SYL,
    SRCH,
    SNDX,
    PHON;

    public String value() {
        return name();
    }

    public static PostalAddressUse fromValue(String v) {
        return valueOf(v);
    }

}
