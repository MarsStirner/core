
package ru.korus.tmis.lis.data.model.hl7.complex;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RoleClassAdjuvant.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="RoleClassAdjuvant">
 *   &lt;restriction base="{urn:hl7-org:v3}cs">
 *     &lt;enumeration value="ADJV"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "RoleClassAdjuvant")
@XmlEnum
public enum RoleClassAdjuvant {

    ADJV;

    public String value() {
        return name();
    }

    public static RoleClassAdjuvant fromValue(String v) {
        return valueOf(v);
    }

}
