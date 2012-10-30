
package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EntityClassManufacturedMaterial.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="EntityClassManufacturedMaterial">
 *   &lt;restriction base="{urn:hl7-org:v3}cs">
 *     &lt;enumeration value="CER"/>
 *     &lt;enumeration value="CONT"/>
 *     &lt;enumeration value="DEV"/>
 *     &lt;enumeration value="HOLD"/>
 *     &lt;enumeration value="MMAT"/>
 *     &lt;enumeration value="MODDV"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "EntityClassManufacturedMaterial")
@XmlEnum
public enum EntityClassManufacturedMaterial {

    CER,
    CONT,
    DEV,
    HOLD,
    MMAT,
    MODDV;

    public String value() {
        return name();
    }

    public static EntityClassManufacturedMaterial fromValue(String v) {
        return valueOf(v);
    }

}
