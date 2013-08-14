
package ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ActCoverageAuthorizationConfirmationCode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ActCoverageAuthorizationConfirmationCode">
 *   &lt;restriction base="{urn:hl7-org:v3}cs">
 *     &lt;enumeration value="AUTH"/>
 *     &lt;enumeration value="NAUTH"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ActCoverageAuthorizationConfirmationCode")
@XmlEnum
public enum ActCoverageAuthorizationConfirmationCode {

    AUTH,
    NAUTH;

    public String value() {
        return name();
    }

    public static ActCoverageAuthorizationConfirmationCode fromValue(String v) {
        return valueOf(v);
    }

}