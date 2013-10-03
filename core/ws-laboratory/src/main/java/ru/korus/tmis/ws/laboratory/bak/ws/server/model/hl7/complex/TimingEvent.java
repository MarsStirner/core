
package ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TimingEvent.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TimingEvent">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="HS"/>
 *     &lt;enumeration value="WAKE"/>
 *     &lt;enumeration value="AC"/>
 *     &lt;enumeration value="ACM"/>
 *     &lt;enumeration value="ACD"/>
 *     &lt;enumeration value="ACV"/>
 *     &lt;enumeration value="IC"/>
 *     &lt;enumeration value="ICM"/>
 *     &lt;enumeration value="ICD"/>
 *     &lt;enumeration value="ICV"/>
 *     &lt;enumeration value="PC"/>
 *     &lt;enumeration value="PCM"/>
 *     &lt;enumeration value="PCD"/>
 *     &lt;enumeration value="PCV"/>
 *     &lt;enumeration value="C"/>
 *     &lt;enumeration value="CM"/>
 *     &lt;enumeration value="CD"/>
 *     &lt;enumeration value="CV"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TimingEvent")
@XmlEnum
public enum TimingEvent {

    HS,
    WAKE,
    AC,
    ACM,
    ACD,
    ACV,
    IC,
    ICM,
    ICD,
    ICV,
    PC,
    PCM,
    PCD,
    PCV,
    C,
    CM,
    CD,
    CV;

    public String value() {
        return name();
    }

    public static TimingEvent fromValue(String v) {
        return valueOf(v);
    }

}
