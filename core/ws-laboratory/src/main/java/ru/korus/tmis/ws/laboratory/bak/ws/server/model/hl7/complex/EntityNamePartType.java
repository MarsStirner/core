
package ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EntityNamePartType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="EntityNamePartType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="FAM"/>
 *     &lt;enumeration value="GIV"/>
 *     &lt;enumeration value="TITLE"/>
 *     &lt;enumeration value="DEL"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "EntityNamePartType")
@XmlEnum
public enum EntityNamePartType {

    FAM,
    GIV,
    TITLE,
    DEL;

    public String value() {
        return name();
    }

    public static EntityNamePartType fromValue(String v) {
        return valueOf(v);
    }

}
