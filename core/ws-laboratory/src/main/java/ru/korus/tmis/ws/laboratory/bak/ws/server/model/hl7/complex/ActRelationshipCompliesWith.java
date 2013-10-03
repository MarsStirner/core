
package ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ActRelationshipCompliesWith.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ActRelationshipCompliesWith">
 *   &lt;restriction base="{urn:hl7-org:v3}cs">
 *     &lt;enumeration value="COMPLY"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ActRelationshipCompliesWith")
@XmlEnum
public enum ActRelationshipCompliesWith {

    COMPLY;

    public String value() {
        return name();
    }

    public static ActRelationshipCompliesWith fromValue(String v) {
        return valueOf(v);
    }

}
