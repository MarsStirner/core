package ru.korus.tmis.ws.laboratory.bulk.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author anosov@outlook.com
 *         date: 5/28/13
 */
@XmlRootElement(name = "microSensitivity")
public class MicroSensitivity {

    /**
     * идентификатор антибиотика БД
     */
    @XmlElement
    private String antibioticLisId;

    /**
     * название антибиотика
     */
    @XmlElement
    private String antibioticName;

    /**
     * величина концентрации
     */
    @XmlElement
    private String MIC;

    /**
     * описание чувствительности в произвольном виде: R,S,I
     */
    @XmlElement
    private String antibioticActivityValue;

    String getAntibioticLisId() {
        return antibioticLisId;
    }

    void setAntibioticLisId(String antibioticLisId) {
        this.antibioticLisId = antibioticLisId;
    }

    String getAntibioticName() {
        return antibioticName;
    }

    void setAntibioticName(String antibioticName) {
        this.antibioticName = antibioticName;
    }

    String getMIC() {
        return MIC;
    }

    void setMIC(String MIC) {
        this.MIC = MIC;
    }

    String getAntibioticActivityValue() {
        return antibioticActivityValue;
    }

    void setAntibioticActivityValue(String antibioticActivityValue) {
        this.antibioticActivityValue = antibioticActivityValue;
    }

    @Override
    public String toString() {
        return "MicroSensitivity{" +
                "antibioticLisId='" + antibioticLisId + '\'' +
                ", antibioticName='" + antibioticName + '\'' +
                ", MIC='" + MIC + '\'' +
                ", antibioticActivityValue='" + antibioticActivityValue + '\'' +
                '}';
    }
}
