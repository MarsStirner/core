package ru.korus.tmis.ws.laboratory.bak.ws.server.model.fake;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author anosov
 *         Date: 04.10.13 1:14
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Antibiotics {
    @XmlElement(name = "antibiotics")
    private List<Antibiotic> antibiotics = new ArrayList<Antibiotic>();

    public Antibiotics(List<Antibiotic> antibiotics) {
        this.antibiotics = antibiotics;
    }

    public List<Antibiotic> getAntibiotics() {
        return antibiotics;
    }

    public void setAntibiotics(List<Antibiotic> antibiotics) {
        this.antibiotics = antibiotics;
    }
}