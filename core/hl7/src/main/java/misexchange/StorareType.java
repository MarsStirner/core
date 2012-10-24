package misexchange;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StorareType.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;simpleType name="StorareType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="����������������"/>
 *     &lt;enumeration value="���������"/>
 *     &lt;enumeration value="�������������������������������"/>
 *     &lt;enumeration value="������"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "StorareType")
@XmlEnum
public enum StorareType {

    @XmlEnumValue("БольничнаяАптека")
    PHARMACY("БольничнаяАптека"),
    @XmlEnumValue("Отделение")
    DIVISION("Отделение"),
    @XmlEnumValue("РецептурноПроизводственныйОтдел")
    PRODUCTION_DIVISION("РецептурноПроизводственныйОтдел"),
    @XmlEnumValue("Прочее")
    OTHER("Прочее");

    private final String value;

    StorareType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static StorareType fromValue(String v) {
        for (StorareType c : StorareType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
