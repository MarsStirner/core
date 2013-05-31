package ru.korus.tmis.ws.laboratory.bulk.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author anosov@outlook.com
 *         date: 5/28/13
 */
@XmlRootElement
public class MicroValue {

    /**
     * идентификатор организма по БД ЛИС
     */
    @XmlElement
    private String organismLisId;

    /**
     * название организма
     */
    @XmlElement
    private String organismName;

    /**
     * описание концентрации в произвольном виде
     */
    @XmlElement
    private String organismConcetration;

    String getOrganismLisId() {
        return organismLisId;
    }

    void setOrganismLisId(String organismLisId) {
        this.organismLisId = organismLisId;
    }

    String getOrganismName() {
        return organismName;
    }

    void setOrganismName(String organismName) {
        this.organismName = organismName;
    }

    String getOrganismConcetration() {
        return organismConcetration;
    }

    void setOrganismConcetration(String organismConcetration) {
        this.organismConcetration = organismConcetration;
    }

    @Override
    public String toString() {
        return "MicroValue{" +
                "organismLisId='" + organismLisId + '\'' +
                ", organismName='" + organismName + '\'' +
                ", organismConcetration='" + organismConcetration + '\'' +
                '}';
    }
}
