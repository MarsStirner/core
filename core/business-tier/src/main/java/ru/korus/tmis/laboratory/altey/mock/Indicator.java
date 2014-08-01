package ru.korus.tmis.laboratory.altey.mock;

import javax.xml.bind.annotation.XmlElement;

/**
 * Показатель/метод исследования
 */
public class Indicator {
    /**
     * Код показателя (Определяет показатель)
     */
    private String code;

    /**
     * Название показателя (Вспомогательная информация)
     */
    private String name;

    @XmlElement(name = "indicatorCode", nillable = false, required = true)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @XmlElement(name = "indicatorName")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
