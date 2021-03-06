
package ru.korus.tmis.lis.data.model.hl7.complex;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RoleClassUsedEntity.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="RoleClassUsedEntity">
 *   &lt;restriction base="{urn:hl7-org:v3}cs">
 *     &lt;enumeration value="USED"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "RoleClassUsedEntity")
@XmlEnum
public enum RoleClassUsedEntity {

    USED;

    public String value() {
        return name();
    }

    public static RoleClassUsedEntity fromValue(String v) {
        return valueOf(v);
    }

}
