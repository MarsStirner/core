
package ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EntityNamePartQualifier.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="EntityNamePartQualifier">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="LS"/>
 *     &lt;enumeration value="AC"/>
 *     &lt;enumeration value="NB"/>
 *     &lt;enumeration value="PR"/>
 *     &lt;enumeration value="HON"/>
 *     &lt;enumeration value="BR"/>
 *     &lt;enumeration value="AD"/>
 *     &lt;enumeration value="SP"/>
 *     &lt;enumeration value="MID"/>
 *     &lt;enumeration value="CL"/>
 *     &lt;enumeration value="IN"/>
 *     &lt;enumeration value="PFX"/>
 *     &lt;enumeration value="SFX"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "EntityNamePartQualifier")
@XmlEnum
public enum EntityNamePartQualifier {

    LS,
    AC,
    NB,
    PR,
    HON,
    BR,
    AD,
    SP,
    MID,
    CL,
    IN,
    PFX,
    SFX;

    public String value() {
        return name();
    }

    public static EntityNamePartQualifier fromValue(String v) {
        return valueOf(v);
    }

}
