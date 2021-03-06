
package ru.korus.tmis.pix;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IntracavernosalRoute.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="IntracavernosalRoute">
 *   &lt;restriction base="{urn:hl7-org:v3}cs">
 *     &lt;enumeration value="ICAVINJ"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "IntracavernosalRoute")
@XmlEnum
public enum IntracavernosalRoute {

    ICAVINJ;

    public String value() {
        return name();
    }

    public static IntracavernosalRoute fromValue(String v) {
        return valueOf(v);
    }

}
