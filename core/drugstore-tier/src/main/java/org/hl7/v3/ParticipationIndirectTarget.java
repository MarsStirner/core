//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.05.16 at 03:07:22 PM MSD 
//


package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ParticipationIndirectTarget.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ParticipationIndirectTarget">
 *   &lt;restriction base="{urn:hl7-org:v3}cs">
 *     &lt;enumeration value="IND"/>
 *     &lt;enumeration value="BEN"/>
 *     &lt;enumeration value="COV"/>
 *     &lt;enumeration value="HLD"/>
 *     &lt;enumeration value="RCT"/>
 *     &lt;enumeration value="RCV"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ParticipationIndirectTarget")
@XmlEnum
public enum ParticipationIndirectTarget {

    IND,
    BEN,
    COV,
    HLD,
    RCT,
    RCV;

    public String value() {
        return name();
    }

    public static ParticipationIndirectTarget fromValue(String v) {
        return valueOf(v);
    }

}
