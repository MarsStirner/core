package ru.korus.tmis.ws.laboratory.bak.ws.server.model.fake;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author anosov
 *         Date: 04.10.13 1:00
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MicroOrg", propOrder = {
        "name",
        "code",
        "concentration"
})
public class MicroOrg {

    @XmlElement(required = true)
    protected String name;

    @XmlElement(required = true)
    protected String code;

    @XmlElement(required = true)
    protected String concentration;

    public MicroOrg() {
    }

    public MicroOrg(String name, String code, String concentration) {
        this.name = name;
        this.code = code;
        this.concentration = concentration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
