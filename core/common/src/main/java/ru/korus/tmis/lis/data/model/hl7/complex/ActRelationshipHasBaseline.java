
package ru.korus.tmis.lis.data.model.hl7.complex;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ActRelationshipHasBaseline.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ActRelationshipHasBaseline">
 *   &lt;restriction base="{urn:hl7-org:v3}cs">
 *     &lt;enumeration value="BSLN"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ActRelationshipHasBaseline")
@XmlEnum
public enum ActRelationshipHasBaseline {

    BSLN;

    public String value() {
        return name();
    }

    public static ActRelationshipHasBaseline fromValue(String v) {
        return valueOf(v);
    }

}
