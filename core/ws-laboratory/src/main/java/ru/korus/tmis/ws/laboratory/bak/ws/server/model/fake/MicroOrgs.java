package ru.korus.tmis.ws.laboratory.bak.ws.server.model.fake;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author anosov
 *         Date: 04.10.13 1:05
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class MicroOrgs {
    @XmlElement(name = "microOrgs")
    private List<Antibiotic> microOrgs = new ArrayList<Antibiotic>();

    public MicroOrgs(List<Antibiotic> microOrgs) {
        this.microOrgs = microOrgs;
    }

    public List<Antibiotic> getMicroOrgs() {
        return microOrgs;
    }

    public void setMicroOrgs(List<Antibiotic> microOrgs) {
        this.microOrgs = microOrgs;
    }
}
