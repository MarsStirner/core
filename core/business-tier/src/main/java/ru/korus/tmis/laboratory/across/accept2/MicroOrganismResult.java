package ru.korus.tmis.laboratory.across.accept2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Результат анализа микроорганизмов
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(propOrder = {
        "organismCode",
        "organismName",
        "organismConcentration"
})
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

    @XmlElement(name = "organismConcetration")
    public String getOrganismConcentration() {
        return organismConcentration;
    }

    public void setOrganismConcentration(String organismConcentration) {
        this.organismConcentration = organismConcentration;
    }
}
