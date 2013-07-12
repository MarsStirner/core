
package ru.korus.tmis.pix;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AdministrativeGender.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="AdministrativeGender">
 *   &lt;restriction base="{urn:hl7-org:v3}cs">
 *     &lt;enumeration value="F"/>
 *     &lt;enumeration value="M"/>
 *     &lt;enumeration value="UN"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "AdministrativeGender")
@XmlEnum
public enum AdministrativeGender {

    F,
    M,
    UN;

    public String value() {
        return name();
    }

    public static AdministrativeGender fromValue(String v) {
        return valueOf(v);
    }

}
