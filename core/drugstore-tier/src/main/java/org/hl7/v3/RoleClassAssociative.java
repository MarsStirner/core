
package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RoleClassAssociative.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="RoleClassAssociative">
 *   &lt;restriction base="{urn:hl7-org:v3}cs">
 *     &lt;enumeration value="ACCESS"/>
 *     &lt;enumeration value="ADMM"/>
 *     &lt;enumeration value="AFFL"/>
 *     &lt;enumeration value="AGNT"/>
 *     &lt;enumeration value="ASSIGNED"/>
 *     &lt;enumeration value="BIRTHPL"/>
 *     &lt;enumeration value="CAREGIVER"/>
 *     &lt;enumeration value="CASEBJ"/>
 *     &lt;enumeration value="CIT"/>
 *     &lt;enumeration value="CLAIM"/>
 *     &lt;enumeration value="COMPAR"/>
 *     &lt;enumeration value="CON"/>
 *     &lt;enumeration value="COVPTY"/>
 *     &lt;enumeration value="CRINV"/>
 *     &lt;enumeration value="CRSPNSR"/>
 *     &lt;enumeration value="DEATHPLC"/>
 *     &lt;enumeration value="DEPEN"/>
 *     &lt;enumeration value="DSDLOC"/>
 *     &lt;enumeration value="DST"/>
 *     &lt;enumeration value="ECON"/>
 *     &lt;enumeration value="EMP"/>
 *     &lt;enumeration value="EXPR"/>
 *     &lt;enumeration value="GUAR"/>
 *     &lt;enumeration value="GUARD"/>
 *     &lt;enumeration value="HLD"/>
 *     &lt;enumeration value="HLTHCHRT"/>
 *     &lt;enumeration value="IDENT"/>
 *     &lt;enumeration value="INDIV"/>
 *     &lt;enumeration value="INVSBJ"/>
 *     &lt;enumeration value="ISDLOC"/>
 *     &lt;enumeration value="LIC"/>
 *     &lt;enumeration value="MANU"/>
 *     &lt;enumeration value="MIL"/>
 *     &lt;enumeration value="MNT"/>
 *     &lt;enumeration value="NAMED"/>
 *     &lt;enumeration value="NOK"/>
 *     &lt;enumeration value="NOT"/>
 *     &lt;enumeration value="OWN"/>
 *     &lt;enumeration value="PAT"/>
 *     &lt;enumeration value="PAYEE"/>
 *     &lt;enumeration value="PAYOR"/>
 *     &lt;enumeration value="POLHOLD"/>
 *     &lt;enumeration value="PROG"/>
 *     &lt;enumeration value="PROV"/>
 *     &lt;enumeration value="PRS"/>
 *     &lt;enumeration value="QUAL"/>
 *     &lt;enumeration value="RESBJ"/>
 *     &lt;enumeration value="RET"/>
 *     &lt;enumeration value="RGPR"/>
 *     &lt;enumeration value="SDLOC"/>
 *     &lt;enumeration value="SGNOFF"/>
 *     &lt;enumeration value="SPNSR"/>
 *     &lt;enumeration value="STD"/>
 *     &lt;enumeration value="SUBSCR"/>
 *     &lt;enumeration value="TERR"/>
 *     &lt;enumeration value="THER"/>
 *     &lt;enumeration value="UNDWRT"/>
 *     &lt;enumeration value="USED"/>
 *     &lt;enumeration value="WRTE"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "RoleClassAssociative")
@XmlEnum
public enum RoleClassAssociative {

    ACCESS,
    ADMM,
    AFFL,
    AGNT,
    ASSIGNED,
    BIRTHPL,
    CAREGIVER,
    CASEBJ,
    CIT,
    CLAIM,
    COMPAR,
    CON,
    COVPTY,
    CRINV,
    CRSPNSR,
    DEATHPLC,
    DEPEN,
    DSDLOC,
    DST,
    ECON,
    EMP,
    EXPR,
    GUAR,
    GUARD,
    HLD,
    HLTHCHRT,
    IDENT,
    INDIV,
    INVSBJ,
    ISDLOC,
    LIC,
    MANU,
    MIL,
    MNT,
    NAMED,
    NOK,
    NOT,
    OWN,
    PAT,
    PAYEE,
    PAYOR,
    POLHOLD,
    PROG,
    PROV,
    PRS,
    QUAL,
    RESBJ,
    RET,
    RGPR,
    SDLOC,
    SGNOFF,
    SPNSR,
    STD,
    SUBSCR,
    TERR,
    THER,
    UNDWRT,
    USED,
    WRTE;

    public String value() {
        return name();
    }

    public static RoleClassAssociative fromValue(String v) {
        return valueOf(v);
    }

}
