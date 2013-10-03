
package ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StrucDoc.CellScope.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="StrucDoc.CellScope">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="row"/>
 *     &lt;enumeration value="col"/>
 *     &lt;enumeration value="rowgroup"/>
 *     &lt;enumeration value="colgroup"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "StrucDoc.CellScope")
@XmlEnum
public enum StrucDocCellScope {

    @XmlEnumValue("row")
    ROW("row"),
    @XmlEnumValue("col")
    COL("col"),
    @XmlEnumValue("rowgroup")
    ROWGROUP("rowgroup"),
    @XmlEnumValue("colgroup")
    COLGROUP("colgroup");
    private final String value;

    StrucDocCellScope(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static StrucDocCellScope fromValue(String v) {
        for (StrucDocCellScope c: StrucDocCellScope.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
