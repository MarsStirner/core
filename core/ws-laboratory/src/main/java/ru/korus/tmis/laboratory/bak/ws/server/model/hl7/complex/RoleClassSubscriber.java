
package ru.korus.tmis.laboratory.bak.ws.server.model.hl7.complex;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RoleClassSubscriber.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="RoleClassSubscriber">
 *   &lt;restriction base="{urn:hl7-org:v3}cs">
 *     &lt;enumeration value="SUBSCR"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "RoleClassSubscriber")
@XmlEnum
public enum RoleClassSubscriber {

    SUBSCR;

    public String value() {
        return name();
    }

    public static RoleClassSubscriber fromValue(String v) {
        return valueOf(v);
    }

}
