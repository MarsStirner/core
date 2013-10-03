package ru.korus.tmis.ws.laboratory.bak.ws.server.model.fake;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author anosov
 *         Date: 04.10.13 1:14
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Antibiotic", propOrder = {
        "idAntibiotic",
        "nameAntibiotic",
        "senseAntibiotic",
        "concentation"
})
public class Antibiotic {

    @XmlElement(required = true)
    protected String idAntibiotic;

    @XmlElement(required = true)
    protected String nameAntibiotic;

    @XmlElement(required = true)
    protected String senseAntibiotic;

    @XmlElement(required = true)
    protected String concentation;

    public String getIdAntibiotic() {
        return idAntibiotic;
    }

    public void setIdAntibiotic(String idAntibiotic) {
        this.idAntibiotic = idAntibiotic;
    }

    public String getNameAntibiotic() {
        return nameAntibiotic;
    }

    public void setNameAntibiotic(String nameAntibiotic) {
        this.nameAntibiotic = nameAntibiotic;
    }

    public String getSenseAntibiotic() {
        return senseAntibiotic;
    }

    public void setSenseAntibiotic(String senseAntibiotic) {
        this.senseAntibiotic = senseAntibiotic;
    }

    public String getConcentation() {
        return concentation;
    }

    public void setConcentation(String concentation) {
        this.concentation = concentation;
    }
}
