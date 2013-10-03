package ru.korus.tmis.ws.laboratory.bak.ws.server.model.fake;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author anosov
 *         Date: 04.10.13 1:11
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Result", propOrder = {
        "idMicroOrg",
        "code",
        "concentration",
        "antibiotics"
})
public class Result {

    @XmlElement(required = true)
    protected String idMicroOrg;

    @XmlElement(required = true)
    protected String code;

    @XmlElement(required = true)
    protected String concentration;

    @XmlElement(required = true)
    public Antibiotics antibiotics;

    public String getIdMicroOrg() {
        return idMicroOrg;
    }

    public void setIdMicroOrg(String idMicroOrg) {
        this.idMicroOrg = idMicroOrg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getConcentration() {
        return concentration;
    }

    public void setConcentration(String concentration) {
        this.concentration = concentration;
    }

    public Antibiotics getAntibiotics() {
        return antibiotics;
    }

    public void setAntibiotics(Antibiotics antibiotics) {
        this.antibiotics = antibiotics;
    }
}
