
package ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CalendarCycle.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="CalendarCycle">
 *   &lt;restriction base="{urn:hl7-org:v3}cs">
 *     &lt;enumeration value="CD"/>
 *     &lt;enumeration value="CH"/>
 *     &lt;enumeration value="CM"/>
 *     &lt;enumeration value="CN"/>
 *     &lt;enumeration value="CS"/>
 *     &lt;enumeration value="CW"/>
 *     &lt;enumeration value="CY"/>
 *     &lt;enumeration value="D"/>
 *     &lt;enumeration value="DM"/>
 *     &lt;enumeration value="DW"/>
 *     &lt;enumeration value="DY"/>
 *     &lt;enumeration value="H"/>
 *     &lt;enumeration value="HD"/>
 *     &lt;enumeration value="J"/>
 *     &lt;enumeration value="M"/>
 *     &lt;enumeration value="MY"/>
 *     &lt;enumeration value="N"/>
 *     &lt;enumeration value="NH"/>
 *     &lt;enumeration value="S"/>
 *     &lt;enumeration value="SN"/>
 *     &lt;enumeration value="W"/>
 *     &lt;enumeration value="WY"/>
 *     &lt;enumeration value="Y"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "CalendarCycle")
@XmlEnum
public enum CalendarCycle {

    CD,
    CH,
    CM,
    CN,
    CS,
    CW,
    CY,
    D,
    DM,
    DW,
    DY,
    H,
    HD,
    J,
    M,
    MY,
    N,
    NH,
    S,
    SN,
    W,
    WY,
    Y;

    public String value() {
        return name();
    }

    public static CalendarCycle fromValue(String v) {
        return valueOf(v);
    }

}
