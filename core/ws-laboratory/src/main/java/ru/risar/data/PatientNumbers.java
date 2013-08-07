package ru.risar.data;

/**
 * @author anosov
 *         Date: 29.07.13 0:12
 */

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class PatientNumbers {

    @XmlElement(name = "patientNumber")
    private List<PatientNumber> patientNumbers = new ArrayList<PatientNumber>();

    public PatientNumbers() {
    }

    public PatientNumbers(List<PatientNumber> patientNumbers) {
        this.patientNumbers = patientNumbers;
    }

    public List<PatientNumber> getPatientNumbers() {
        return patientNumbers;
    }

    public void setPatientNumbers(List<PatientNumber> patientNumbers) {
        this.patientNumbers = patientNumbers;
    }
}
