
package ru.korus.tmis.lis.data.model.hl7.complex;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ParticipationAuthorOriginator.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ParticipationAuthorOriginator">
 *   &lt;restriction base="{urn:hl7-org:v3}cs">
 *     &lt;enumeration value="AUT"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ParticipationAuthorOriginator")
@XmlEnum
public enum ParticipationAuthorOriginator {

    AUT;

    public String value() {
        return name();
    }

    public static ParticipationAuthorOriginator fromValue(String v) {
        return valueOf(v);
    }

}
