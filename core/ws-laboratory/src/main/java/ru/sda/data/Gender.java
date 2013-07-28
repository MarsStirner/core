package ru.sda.data;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * @author anosov
 *         Date: 27.07.13 23:13
 */
@XmlType(name = "gender")
@XmlEnum
public enum Gender {
    @XmlEnumValue("M")
    MALE("M"),
    @XmlEnumValue("F")
    FAMALE("F");

    private String code;

    private Gender(String code) {
        this.code = code;
    }

    public String code() {
        return code;
    }

    public static Gender fromValue(String v) {
        for (Gender c : Gender.values()) {
            if (c.code.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

    public short codeShort() {
        if (this == Gender.MALE) {
            return 1;
        } else {
            return 2;
        }
    }

}