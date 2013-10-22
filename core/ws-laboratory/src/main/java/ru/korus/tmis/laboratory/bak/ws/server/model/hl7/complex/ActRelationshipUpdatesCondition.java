
package ru.korus.tmis.laboratory.bak.ws.server.model.hl7.complex;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ActRelationshipUpdatesCondition.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ActRelationshipUpdatesCondition">
 *   &lt;restriction base="{urn:hl7-org:v3}cs">
 *     &lt;enumeration value="UPDT"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ActRelationshipUpdatesCondition")
@XmlEnum
public enum ActRelationshipUpdatesCondition {

    UPDT;

    public String value() {
        return name();
    }

    public static ActRelationshipUpdatesCondition fromValue(String v) {
        return valueOf(v);
    }

}