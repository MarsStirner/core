package ru.korus.tmis.lis.data;

import javax.xml.bind.annotation.XmlElement;

/**
 * Результат анализа микроорганизмов
 */

public class MicroOrganismResult {
    /**
     * идентификатор организма по БД ЛИС
     */
    private String organismCode;

    /**
     * название организма
     */
    private String organismName;

    /**
     * концентрация организма
     */
    private String organismConcentration;

    @XmlElement(name = "organismLisId")
    public String getOrganismCode() {
        return organismCode;
    }

    public void setOrganismCode(String organismCode) {
        this.organismCode = organismCode;
    }

    @XmlElement(name = "organismName")
    public String getOrganismName() {
        return organismName;
    }

    public void setOrganismName(String organismName) {
        this.organismName = organismName;
    }

    @XmlElement(name = "organismConcentration")
    public String getOrganismConcentration() {
        return organismConcentration;
    }

    public void setOrganismConcentration(String organismConcentration) {
        this.organismConcentration = organismConcentration;
    }
}
