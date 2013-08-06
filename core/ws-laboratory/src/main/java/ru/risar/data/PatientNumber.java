package ru.risar.data;

import javax.xml.bind.annotation.*;

/**
 * @author anosov
 *         Date: 28.07.13 0:47
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PatientNumber", propOrder = {
        "number",
        "policySerial",
        "numberType",
        "policyType"
})
public class PatientNumber {

    @XmlElement(required = true)
    protected String number;
    @XmlElement(required = true)
    protected String numberType;
    @XmlElement
    protected String policyType;
    @XmlElement(name = "serial")
    protected String policySerial;

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

    public String getPolicyType() {
        return policyType;
    }

    public void setPolicyType(String policyType) {
        this.policyType = policyType;
    }

    public String getPolicySerial() {
        if (policySerial == null) {
            return "";
        }
        return policySerial;
    }

    public void setPolicySerial(String policySerial) {
        this.policySerial = policySerial;
    }
}