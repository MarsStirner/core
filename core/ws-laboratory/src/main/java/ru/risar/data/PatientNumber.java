package ru.risar.data;

import javax.xml.bind.annotation.*;

/**
 * @author anosov
 *         Date: 28.07.13 0:47
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PatientNumber", propOrder = {
        "number",
        "numberType"
})
public class PatientNumber {

    @XmlElement(required = true)
    protected String number;
    @XmlElement(required = true)
    protected String numberType;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNumberType() {
        return numberType;
    }

    public void setNumberType(String numberType) {
        this.numberType = numberType;
    }
}
