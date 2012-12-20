
package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RoleClassPartitive.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="RoleClassPartitive">
 *   &lt;restriction base="{urn:hl7-org:v3}cs">
 *     &lt;enumeration value="ACTI"/>
 *     &lt;enumeration value="ACTIB"/>
 *     &lt;enumeration value="ACTIM"/>
 *     &lt;enumeration value="ACTIR"/>
 *     &lt;enumeration value="ACTM"/>
 *     &lt;enumeration value="ADTV"/>
 *     &lt;enumeration value="ALQT"/>
 *     &lt;enumeration value="BASE"/>
 *     &lt;enumeration value="COLR"/>
 *     &lt;enumeration value="CONT"/>
 *     &lt;enumeration value="EXPAGTCAR"/>
 *     &lt;enumeration value="EXPVECTOR"/>
 *     &lt;enumeration value="FLVR"/>
 *     &lt;enumeration value="FOMITE"/>
 *     &lt;enumeration value="IACT"/>
 *     &lt;enumeration value="INGR"/>
 *     &lt;enumeration value="ISLT"/>
 *     &lt;enumeration value="LOCE"/>
 *     &lt;enumeration value="MBR"/>
 *     &lt;enumeration value="PART"/>
 *     &lt;enumeration value="PRSV"/>
 *     &lt;enumeration value="SPEC"/>
 *     &lt;enumeration value="STBL"/>
 *     &lt;enumeration value="STOR"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "RoleClassPartitive")
@XmlEnum
public enum RoleClassPartitive {

    ACTI,
    ACTIB,
    ACTIM,
    ACTIR,
    ACTM,
    ADTV,
    ALQT,
    BASE,
    COLR,
    CONT,
    EXPAGTCAR,
    EXPVECTOR,
    FLVR,
    FOMITE,
    IACT,
    INGR,
    ISLT,
    LOCE,
    MBR,
    PART,
    PRSV,
    SPEC,
    STBL,
    STOR;

    public String value() {
        return name();
    }

    public static RoleClassPartitive fromValue(String v) {
        return valueOf(v);
    }

}
