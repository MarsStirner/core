
package ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VaginalOintment.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="VaginalOintment">
 *   &lt;restriction base="{urn:hl7-org:v3}cs">
 *     &lt;enumeration value="VAGOINT"/>
 *     &lt;enumeration value="VAGOINTAPL"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "VaginalOintment")
@XmlEnum
public enum VaginalOintment {

    VAGOINT,
    VAGOINTAPL;

    public String value() {
        return name();
    }

    public static VaginalOintment fromValue(String v) {
        return valueOf(v);
    }

}
