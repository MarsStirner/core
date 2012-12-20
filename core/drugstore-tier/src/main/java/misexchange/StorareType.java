
package misexchange;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StorareType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="StorareType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="БольничнаяАптека"/>
 *     &lt;enumeration value="Отделение"/>
 *     &lt;enumeration value="РецептурноПроизводственныйОтдел"/>
 *     &lt;enumeration value="РозничныйМагазин"/>
 *     &lt;enumeration value="Прочее"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "StorareType")
@XmlEnum
public enum StorareType {

    @XmlEnumValue("\u0411\u043e\u043b\u044c\u043d\u0438\u0447\u043d\u0430\u044f\u0410\u043f\u0442\u0435\u043a\u0430")
    БОЛЬНИЧНАЯ_АПТЕКА("\u0411\u043e\u043b\u044c\u043d\u0438\u0447\u043d\u0430\u044f\u0410\u043f\u0442\u0435\u043a\u0430"),
    @XmlEnumValue("\u041e\u0442\u0434\u0435\u043b\u0435\u043d\u0438\u0435")
    ОТДЕЛЕНИЕ("\u041e\u0442\u0434\u0435\u043b\u0435\u043d\u0438\u0435"),
    @XmlEnumValue("\u0420\u0435\u0446\u0435\u043f\u0442\u0443\u0440\u043d\u043e\u041f\u0440\u043e\u0438\u0437\u0432\u043e\u0434\u0441\u0442\u0432\u0435\u043d\u043d\u044b\u0439\u041e\u0442\u0434\u0435\u043b")
    РЕЦЕПТУРНО_ПРОИЗВОДСТВЕННЫЙ_ОТДЕЛ("\u0420\u0435\u0446\u0435\u043f\u0442\u0443\u0440\u043d\u043e\u041f\u0440\u043e\u0438\u0437\u0432\u043e\u0434\u0441\u0442\u0432\u0435\u043d\u043d\u044b\u0439\u041e\u0442\u0434\u0435\u043b"),
    @XmlEnumValue("\u0420\u043e\u0437\u043d\u0438\u0447\u043d\u044b\u0439\u041c\u0430\u0433\u0430\u0437\u0438\u043d")
    РОЗНИЧНЫЙ_МАГАЗИН("\u0420\u043e\u0437\u043d\u0438\u0447\u043d\u044b\u0439\u041c\u0430\u0433\u0430\u0437\u0438\u043d"),
    @XmlEnumValue("\u041f\u0440\u043e\u0447\u0435\u0435")
    ПРОЧЕЕ("\u041f\u0440\u043e\u0447\u0435\u0435");
    private final String value;

    StorareType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static StorareType fromValue(String v) {
        for (StorareType c: StorareType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
