
package ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ActResearchInformationAccess.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ActResearchInformationAccess">
 *   &lt;restriction base="{urn:hl7-org:v3}cs">
 *     &lt;enumeration value="RESEARCH"/>
 *     &lt;enumeration value="RSDID"/>
 *     &lt;enumeration value="RSREID"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ActResearchInformationAccess")
@XmlEnum
public enum ActResearchInformationAccess {

    RESEARCH,
    RSDID,
    RSREID;

    public String value() {
        return name();
    }

    public static ActResearchInformationAccess fromValue(String v) {
        return valueOf(v);
    }

}
