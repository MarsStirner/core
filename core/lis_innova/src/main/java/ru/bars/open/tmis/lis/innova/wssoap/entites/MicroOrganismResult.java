package ru.bars.open.tmis.lis.innova.wssoap.entites;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Результат анализа микроорганизмов
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {
        "organismLisId",
        "organismName",
        "organismConcentration"
})
public class MicroOrganismResult {
    /**
     * идентификатор организма по БД ЛИС
     */
    @XmlElement(name = "organismLisId")
    private String organismLisId;

    /**
     * название организма
     */
    @XmlElement(name = "organismName")
    private String organismName;

    /**
     * концентрация организма
     */
    @XmlElement(name = "organismConcetration")
    private String organismConcentration;


    public String getOrganismLisId() {
        return organismLisId;
    }

    public void setOrganismLisId(final String organismLisId) {
        this.organismLisId = organismLisId;
    }

    public String getOrganismName() {
        return organismName;
    }

    public void setOrganismName(final String organismName) {
        this.organismName = organismName;
    }

    public String getOrganismConcentration() {
        return organismConcentration;
    }

    public void setOrganismConcentration(final String organismConcentration) {
        this.organismConcentration = organismConcentration;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MicroOrganismResult{");
        sb.append("organismLisId='").append(organismLisId).append('\'');
        sb.append(", organismName='").append(organismName).append('\'');
        sb.append(", organismConcentration='").append(organismConcentration).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
