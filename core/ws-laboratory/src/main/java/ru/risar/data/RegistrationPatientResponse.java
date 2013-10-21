package ru.risar.data;

/**
 * @author anosov
 *         Date: 20.10.13 23:27
 */

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RegistrationPatientResponse", propOrder = {
        "code",
        "description"
})
public class RegistrationPatientResponse {

    @XmlElement(required = true)
    protected String code;

    @XmlElement(required = true)
    protected String description;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
