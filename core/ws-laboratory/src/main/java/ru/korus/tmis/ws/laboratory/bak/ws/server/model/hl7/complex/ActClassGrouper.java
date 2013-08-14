
package ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ActClassGrouper.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ActClassGrouper">
 *   &lt;restriction base="{urn:hl7-org:v3}cs">
 *     &lt;enumeration value="GROUPER"/>
 *     &lt;enumeration value="BATTERY"/>
 *     &lt;enumeration value="CLUSTER"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ActClassGrouper")
@XmlEnum
public enum ActClassGrouper {

    GROUPER,
    BATTERY,
    CLUSTER;

    public String value() {
        return name();
    }

    public static ActClassGrouper fromValue(String v) {
        return valueOf(v);
    }

}