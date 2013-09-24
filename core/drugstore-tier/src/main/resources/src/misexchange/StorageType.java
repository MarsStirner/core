
package misexchange;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StorageType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="StorageType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="¡ÓÎ¸ÌË˜Ì‡ˇ¿ÔÚÂÍ‡"/>
 *     &lt;enumeration value="ŒÚ‰ÂÎÂÌËÂ"/>
 *     &lt;enumeration value="–ÂˆÂÔÚÛÌÓœÓËÁ‚Ó‰ÒÚ‚ÂÌÌ˚ÈŒÚ‰ÂÎ"/>
 *     &lt;enumeration value="–ÓÁÌË˜Ì˚ÈÃ‡„‡ÁËÌ"/>
 *     &lt;enumeration value="œÓ˜ÂÂ"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "StorageType")
@XmlEnum
public enum StorageType {

    @XmlEnumValue("\u0411\u043e\u043b\u044c\u043d\u0438\u0447\u043d\u0430\u044f\u0410\u043f\u0442\u0435\u043a\u0430")
    ¡ŒÀ‹Õ»◊Õ¿ﬂ_¿œ“≈ ¿("\u0411\u043e\u043b\u044c\u043d\u0438\u0447\u043d\u0430\u044f\u0410\u043f\u0442\u0435\u043a\u0430"),
    @XmlEnumValue("\u041e\u0442\u0434\u0435\u043b\u0435\u043d\u0438\u0435")
    Œ“ƒ≈À≈Õ»≈("\u041e\u0442\u0434\u0435\u043b\u0435\u043d\u0438\u0435"),
    @XmlEnumValue("\u0420\u0435\u0446\u0435\u043f\u0442\u0443\u0440\u043d\u043e\u041f\u0440\u043e\u0438\u0437\u0432\u043e\u0434\u0441\u0442\u0432\u0435\u043d\u043d\u044b\u0439\u041e\u0442\u0434\u0435\u043b")
    –≈÷≈œ“”–ÕŒ_œ–Œ»«¬Œƒ—“¬≈ÕÕ€…_Œ“ƒ≈À("\u0420\u0435\u0446\u0435\u043f\u0442\u0443\u0440\u043d\u043e\u041f\u0440\u043e\u0438\u0437\u0432\u043e\u0434\u0441\u0442\u0432\u0435\u043d\u043d\u044b\u0439\u041e\u0442\u0434\u0435\u043b"),
    @XmlEnumValue("\u0420\u043e\u0437\u043d\u0438\u0447\u043d\u044b\u0439\u041c\u0430\u0433\u0430\u0437\u0438\u043d")
    –Œ«Õ»◊Õ€…_Ã¿√¿«»Õ("\u0420\u043e\u0437\u043d\u0438\u0447\u043d\u044b\u0439\u041c\u0430\u0433\u0430\u0437\u0438\u043d"),
    @XmlEnumValue("\u041f\u0440\u043e\u0447\u0435\u0435")
    œ–Œ◊≈≈("\u041f\u0440\u043e\u0447\u0435\u0435");
    private final String value;

    StorageType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static StorageType fromValue(String v) {
        for (StorageType c: StorageType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
