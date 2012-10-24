
package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Unknown.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="Unknown">
 *   &lt;restriction base="{urn:hl7-org:v3}cs">
 *     &lt;enumeration value="ASKU"/>
 *     &lt;enumeration value="NASK"/>
 *     &lt;enumeration value="NAV"/>
 *     &lt;enumeration value="QS"/>
 *     &lt;enumeration value="TRC"/>
 *     &lt;enumeration value="UNK"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "Unknown")
@XmlEnum
public enum Unknown {

    ASKU,
    NASK,
    NAV,
    QS,
    TRC,
    UNK;

    public String value() {
        return name();
    }

    public static Unknown fromValue(String v) {
        return valueOf(v);
    }

}
