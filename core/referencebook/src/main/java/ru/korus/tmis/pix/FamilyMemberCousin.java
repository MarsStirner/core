
package ru.korus.tmis.pix;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FamilyMemberCousin.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="FamilyMemberCousin">
 *   &lt;restriction base="{urn:hl7-org:v3}cs">
 *     &lt;enumeration value="COUSN"/>
 *     &lt;enumeration value="MCOUSN"/>
 *     &lt;enumeration value="PCOUSN"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "FamilyMemberCousin")
@XmlEnum
public enum FamilyMemberCousin {

    COUSN,
    MCOUSN,
    PCOUSN;

    public String value() {
        return name();
    }

    public static FamilyMemberCousin fromValue(String v) {
        return valueOf(v);
    }

}
