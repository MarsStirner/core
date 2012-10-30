
package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RoleClassMutualRelationship.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="RoleClassMutualRelationship">
 *   &lt;restriction base="{urn:hl7-org:v3}cs">
 *     &lt;enumeration value="AFFL"/>
 *     &lt;enumeration value="AGNT"/>
 *     &lt;enumeration value="ASSIGNED"/>
 *     &lt;enumeration value="CAREGIVER"/>
 *     &lt;enumeration value="CASEBJ"/>
 *     &lt;enumeration value="CIT"/>
 *     &lt;enumeration value="CLAIM"/>
 *     &lt;enumeration value="COMPAR"/>
 *     &lt;enumeration value="CON"/>
 *     &lt;enumeration value="COVPTY"/>
 *     &lt;enumeration value="CRINV"/>
 *     &lt;enumeration value="CRSPNSR"/>
 *     &lt;enumeration value="DEPEN"/>
 *     &lt;enumeration value="ECON"/>
 *     &lt;enumeration value="EMP"/>
 *     &lt;enumeration value="GUAR"/>
 *     &lt;enumeration value="GUARD"/>
 *     &lt;enumeration value="INDIV"/>
 *     &lt;enumeration value="INVSBJ"/>
 *     &lt;enumeration value="LIC"/>
 *     &lt;enumeration value="MIL"/>
 *     &lt;enumeration value="NAMED"/>
 *     &lt;enumeration value="NOK"/>
 *     &lt;enumeration value="NOT"/>
 *     &lt;enumeration value="PAT"/>
 *     &lt;enumeration value="PAYEE"/>
 *     &lt;enumeration value="PAYOR"/>
 *     &lt;enumeration value="POLHOLD"/>
 *     &lt;enumeration value="PROG"/>
 *     &lt;enumeration value="PROV"/>
 *     &lt;enumeration value="PRS"/>
 *     &lt;enumeration value="QUAL"/>
 *     &lt;enumeration value="RESBJ"/>
 *     &lt;enumeration value="SGNOFF"/>
 *     &lt;enumeration value="SPNSR"/>
 *     &lt;enumeration value="STD"/>
 *     &lt;enumeration value="SUBSCR"/>
 *     &lt;enumeration value="UNDWRT"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "RoleClassMutualRelationship")
@XmlEnum
public enum RoleClassMutualRelationship {

    AFFL,
    AGNT,
    ASSIGNED,
    CAREGIVER,
    CASEBJ,
    CIT,
    CLAIM,
    COMPAR,
    CON,
    COVPTY,
    CRINV,
    CRSPNSR,
    DEPEN,
    ECON,
    EMP,
    GUAR,
    GUARD,
    INDIV,
    INVSBJ,
    LIC,
    MIL,
    NAMED,
    NOK,
    NOT,
    PAT,
    PAYEE,
    PAYOR,
    POLHOLD,
    PROG,
    PROV,
    PRS,
    QUAL,
    RESBJ,
    SGNOFF,
    SPNSR,
    STD,
    SUBSCR,
    UNDWRT;

    public String value() {
        return name();
    }

    public static RoleClassMutualRelationship fromValue(String v) {
        return valueOf(v);
    }

}
