
package ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RoleClassOntologicalEquivalentEntityByBOT.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="RoleClassOntologicalEquivalentEntityByBOT">
 *   &lt;restriction base="{urn:hl7-org:v3}cs">
 *     &lt;enumeration value="EQUIV"/>
 *     &lt;enumeration value="SAME"/>
 *     &lt;enumeration value="SUBY"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "RoleClassOntologicalEquivalentEntityByBOT")
@XmlEnum
public enum RoleClassOntologicalEquivalentEntityByBOT {

    EQUIV,
    SAME,
    SUBY;

    public String value() {
        return name();
    }

    public static RoleClassOntologicalEquivalentEntityByBOT fromValue(String v) {
        return valueOf(v);
    }

}
