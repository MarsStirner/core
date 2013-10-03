
package ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Compression.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="Compression">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="DF"/>
 *     &lt;enumeration value="GZ"/>
 *     &lt;enumeration value="ZL"/>
 *     &lt;enumeration value="Z"/>
 *     &lt;enumeration value="BZ"/>
 *     &lt;enumeration value="Z7"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "Compression")
@XmlEnum
public enum Compression {

    DF("DF"),
    GZ("GZ"),
    ZL("ZL"),
    Z("Z"),
    BZ("BZ"),
    @XmlEnumValue("Z7")
    Z_7("Z7");
    private final String value;

    Compression(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static Compression fromValue(String v) {
        for (Compression c: Compression.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
