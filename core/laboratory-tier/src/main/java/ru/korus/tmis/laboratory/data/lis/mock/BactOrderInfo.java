package ru.korus.tmis.laboratory.data.lis.mock;

import javax.xml.bind.annotation.XmlElement;

public class BactOrderInfo {
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
    private boolean urgent;

    /**
     * Название микроорганизма
     */
    private String bactName;

    @XmlElement(required = true)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isUrgent() {
        return urgent;
    }

    public void setUrgent(boolean urgent) {
        this.urgent = urgent;
    }

    @XmlElement(required = true)
    public String getBactName() {
        return bactName;
    }

    public void setBactName(String bactName) {
        this.bactName = bactName;
    }
}
