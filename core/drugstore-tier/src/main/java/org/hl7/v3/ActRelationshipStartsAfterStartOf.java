
package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ActRelationshipStartsAfterStartOf.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ActRelationshipStartsAfterStartOf">
 *   &lt;restriction base="{urn:hl7-org:v3}cs">
 *     &lt;enumeration value="SAS"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ActRelationshipStartsAfterStartOf")
@XmlEnum
public enum ActRelationshipStartsAfterStartOf {

    SAS;

    public String value() {
        return name();
    }

    public static ActRelationshipStartsAfterStartOf fromValue(String v) {
        return valueOf(v);
    }

}
