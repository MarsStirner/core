
package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ParticipationTargetDirect.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ParticipationTargetDirect">
 *   &lt;restriction base="{urn:hl7-org:v3}cs">
 *     &lt;enumeration value="BBY"/>
 *     &lt;enumeration value="CSM"/>
 *     &lt;enumeration value="DEV"/>
 *     &lt;enumeration value="DIR"/>
 *     &lt;enumeration value="DON"/>
 *     &lt;enumeration value="EXPAGNT"/>
 *     &lt;enumeration value="EXPART"/>
 *     &lt;enumeration value="EXPTRGT"/>
 *     &lt;enumeration value="EXSRC"/>
 *     &lt;enumeration value="NRD"/>
 *     &lt;enumeration value="PRD"/>
 *     &lt;enumeration value="RDV"/>
 *     &lt;enumeration value="SBJ"/>
 *     &lt;enumeration value="SPC"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ParticipationTargetDirect")
@XmlEnum
public enum ParticipationTargetDirect {

    BBY,
    CSM,
    DEV,
    DIR,
    DON,
    EXPAGNT,
    EXPART,
    EXPTRGT,
    EXSRC,
    NRD,
    PRD,
    RDV,
    SBJ,
    SPC;

    public String value() {
        return name();
    }

    public static ParticipationTargetDirect fromValue(String v) {
        return valueOf(v);
    }

}
