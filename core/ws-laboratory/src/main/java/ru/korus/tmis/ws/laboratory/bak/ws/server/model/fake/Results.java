package ru.korus.tmis.ws.laboratory.bak.ws.server.model.fake;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author anosov
 *         Date: 04.10.13 1:11
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Results {
    @XmlElement(name = "results")
    private List<Antibiotic> results = new ArrayList<Antibiotic>();

    public Results(List<Antibiotic> results) {
        this.results = results;
    }

    public List<Antibiotic> getResults() {
        return results;
    }

    public void setResults(List<Antibiotic> results) {
        this.results = results;
    }
}
