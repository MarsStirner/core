package ru.korus.tmis.laboratory.bak.ws.server.model.fake;

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
    @XmlElement(name = "microOrg")
    private List<MicroOrg> microOrgs = new ArrayList<MicroOrg>();

    public MicroOrgs() {
    }

    public MicroOrgs(List<MicroOrg> microOrgs) {
        this.microOrgs = microOrgs;
    }

    public List<MicroOrg> getMicroOrgs() {
        return microOrgs;
    }

    public void setMicroOrgs(List<MicroOrg> microOrgs) {
        this.microOrgs = microOrgs;
    }
}
