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
 * <p>Java class for PersonNamePartAffixTypes.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;simpleType name="PersonNamePartAffixTypes">
 *   &lt;restriction base="{urn:hl7-org:v3}cs">
 *     &lt;enumeration value="AC"/>
 *     &lt;enumeration value="NB"/>
 *     &lt;enumeration value="PR"/>
 *     &lt;enumeration value="VV"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "PersonNamePartAffixTypes")
@XmlEnum
public enum PersonNamePartAffixTypes {

    AC,
    NB,
    PR,
    VV;

    public String value() {
        return name();
    }

    public static PersonNamePartAffixTypes fromValue(String v) {
        return valueOf(v);
    }

}
