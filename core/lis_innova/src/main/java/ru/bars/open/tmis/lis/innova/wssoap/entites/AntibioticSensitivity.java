package ru.bars.open.tmis.lis.innova.wssoap.entites;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Чувствительность микроорганизма к антибиотикам
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"antibioticLisId", "antibioticName", "mic", "antibioticActivityValue"})
public class AntibioticSensitivity {

    /**
     * Идентификатор антибиотика по БД ЛИС
     */
    @XmlElement(name = "antibioticLisId")
    private String antibioticLisId;

    /**
     * Название антибиотика
     */
    @XmlElement(name = "antibioticName")
    private String antibioticName;

    /**
     * Количественная характеристика чувствительности, если используется анализатор
     */
    @XmlElement(name = "MIC")
    private String mic;

    /**
     * Чувствительность
     */
    @XmlElement(name = "antibioticActivityValue")
    private String antibioticActivityValue;

    public String getAntibioticLisId() {
        return antibioticLisId;
    }

    public void setAntibioticLisId(final String antibioticLisId) {
        this.antibioticLisId = antibioticLisId;
    }

    public String getAntibioticName() {
        return antibioticName;
    }

    public void setAntibioticName(final String antibioticName) {
        this.antibioticName = antibioticName;
    }

    public String getMic() {
        return mic;
    }

    public void setMic(final String mic) {
        this.mic = mic;
    }

    public String getAntibioticActivityValue() {
        return antibioticActivityValue;
    }

    public void setAntibioticActivityValue(final String antibioticActivityValue) {
        this.antibioticActivityValue = antibioticActivityValue;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AntibioticSensitivity{");
        sb.append("antibioticLisId='").append(antibioticLisId).append('\'');
        sb.append(", antibioticName='").append(antibioticName).append('\'');
        sb.append(", mic='").append(mic).append('\'');
        sb.append(", antibioticActivityValue='").append(antibioticActivityValue).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
