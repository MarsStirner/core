
package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ActClassCondition.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ActClassCondition">
 *   &lt;restriction base="{urn:hl7-org:v3}cs">
 *     &lt;enumeration value="CASE"/>
 *     &lt;enumeration value="COND"/>
 *     &lt;enumeration value="OUTB"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ActClassCondition")
@XmlEnum
public enum ActClassCondition {

    CASE,
    COND,
    OUTB;

    public String value() {
        return name();
    }

    public static ActClassCondition fromValue(String v) {
        return valueOf(v);
    }

}
