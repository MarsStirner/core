package ru.korus.tmis.laboratory.data.lis.mock;

import javax.xml.bind.annotation.XmlElement;
import java.util.Collection;
import java.util.LinkedList;

public class OrderInfo {
    /**
     * Код исследования (Тип исследования идентифицируется кодом)
     */
    private String code;

    /**
     * Название исследования (Вспомогательная информация)
     */
    private String name;

    /**
     * Флаг срочности
     */
    private Integer urgent;

    /**
     * Показатель/метод исследования
     */
    private Collection<Indicator> indicators;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name = "diagnosticCode", required = true)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @XmlElement(name = "orderPriority")
    public int getUrgent() {
        return urgent;
    }

    public void setUrgent(int urgent) {
        this.urgent = urgent;
    }

    @XmlElement(name = "indicators")
    public Collection<Indicator> getIndicators() {
        if (indicators == null) indicators = new LinkedList<Indicator>();
        return indicators;
    }

    public void setIndicators(Collection<Indicator> indicators) {
        this.indicators = indicators;
    }
}
