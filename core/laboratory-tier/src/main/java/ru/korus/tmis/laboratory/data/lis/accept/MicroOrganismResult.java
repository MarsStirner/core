package ru.korus.tmis.laboratory.data.lis.accept;

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

    @XmlElement(name = "organismLisId", required = true)
    public String getOrganismCode() {
        return organismCode;
    }

    public void setOrganismCode(String organismCode) {
        this.organismCode = organismCode;
    }

    @XmlElement(name = "organismName", required = true)
    public String getOrganismName() {
        return organismName;
    }

    public void setOrganismName(String organismName) {
        this.organismName = organismName;
    }

    @XmlElement(name = "organismConcetration", required = true)
    public String getOrganismConcentration() {
        return organismConcentration;
    }

    public void setOrganismConcentration(String organismConcentration) {
        this.organismConcentration = organismConcentration;
    }
}
