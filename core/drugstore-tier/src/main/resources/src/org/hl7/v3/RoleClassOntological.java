
package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RoleClassOntological.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="RoleClassOntological">
 *   &lt;restriction base="{urn:hl7-org:v3}cs">
 *     &lt;enumeration value="EQUIV"/>
 *     &lt;enumeration value="GEN"/>
 *     &lt;enumeration value="GRIC"/>
 *     &lt;enumeration value="INST"/>
 *     &lt;enumeration value="SAME"/>
 *     &lt;enumeration value="SUBS"/>
 *     &lt;enumeration value="SUBY"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "RoleClassOntological")
@XmlEnum
public enum RoleClassOntological {

    EQUIV,
    GEN,
    GRIC,
    INST,
    SAME,
    SUBS,
    SUBY;

    public String value() {
        return name();
    }

    public static RoleClassOntological fromValue(String v) {
        return valueOf(v);
    }

}
