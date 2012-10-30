
package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RoleClassPassive.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="RoleClassPassive">
 *   &lt;restriction base="{urn:hl7-org:v3}cs">
 *     &lt;enumeration value="ACCESS"/>
 *     &lt;enumeration value="ADMM"/>
 *     &lt;enumeration value="BIRTHPL"/>
 *     &lt;enumeration value="DEATHPLC"/>
 *     &lt;enumeration value="DSDLOC"/>
 *     &lt;enumeration value="DST"/>
 *     &lt;enumeration value="EXPR"/>
 *     &lt;enumeration value="HLD"/>
 *     &lt;enumeration value="HLTHCHRT"/>
 *     &lt;enumeration value="IDENT"/>
 *     &lt;enumeration value="ISDLOC"/>
 *     &lt;enumeration value="MANU"/>
 *     &lt;enumeration value="MNT"/>
 *     &lt;enumeration value="OWN"/>
 *     &lt;enumeration value="RET"/>
 *     &lt;enumeration value="RGPR"/>
 *     &lt;enumeration value="SDLOC"/>
 *     &lt;enumeration value="TERR"/>
 *     &lt;enumeration value="THER"/>
 *     &lt;enumeration value="USED"/>
 *     &lt;enumeration value="WRTE"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "RoleClassPassive")
@XmlEnum
public enum RoleClassPassive {

    ACCESS,
    ADMM,
    BIRTHPL,
    DEATHPLC,
    DSDLOC,
    DST,
    EXPR,
    HLD,
    HLTHCHRT,
    IDENT,
    ISDLOC,
    MANU,
    MNT,
    OWN,
    RET,
    RGPR,
    SDLOC,
    TERR,
    THER,
    USED,
    WRTE;

    public String value() {
        return name();
    }

    public static RoleClassPassive fromValue(String v) {
        return valueOf(v);
    }

}
