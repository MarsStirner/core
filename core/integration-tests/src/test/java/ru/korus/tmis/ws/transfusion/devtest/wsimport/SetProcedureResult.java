package ru.korus.tmis.ws.transfusion.devtest.wsimport;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for setProcedureResult complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="setProcedureResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="patientCredentials" type="{http://www.korusconsulting.ru}PatientCredentials" minOccurs="0" form="qualified"/>
 *         &lt;element name="ProcedureInfo" type="{http://korus.ru/tmis/ws/transfusion}procedureInfo" minOccurs="0" form="qualified"/>
 *         &lt;element name="EritrocyteMass" type="{http://korus.ru/tmis/ws/transfusion}eritrocyteMass" minOccurs="0" form="qualified"/>
 *         &lt;element name="Measures" type="{http://korus.ru/tmis/ws/transfusion}laboratoryMeasure" maxOccurs="unbounded" minOccurs="0" form="qualified"/>
 *         &lt;element name="finalVolumeList" type="{http://korus.ru/tmis/ws/transfusion}finalVolume" maxOccurs="unbounded" minOccurs="0" form="qualified"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "setProcedureResult", propOrder = { "patientCredentials", "procedureInfo", "eritrocyteMass", "measures", "finalVolumeList" })
public class SetProcedureResult {

    @XmlElement(namespace = "http://korus.ru/tmis/ws/transfusion")
    protected PatientCredentials patientCredentials;
    @XmlElement(name = "ProcedureInfo", namespace = "http://korus.ru/tmis/ws/transfusion")
    protected ProcedureInfo procedureInfo;
    @XmlElement(name = "EritrocyteMass", namespace = "http://korus.ru/tmis/ws/transfusion")
    protected EritrocyteMass eritrocyteMass;
    @XmlElement(name = "Measures", namespace = "http://korus.ru/tmis/ws/transfusion")
    protected List<LaboratoryMeasure> measures;
    @XmlElement(namespace = "http://korus.ru/tmis/ws/transfusion")
    protected List<FinalVolume> finalVolumeList;

    /**
     * Gets the value of the patientCredentials property.
     * 
     * @return possible object is {@link PatientCredentials }
     * 
     */
    public PatientCredentials getPatientCredentials() {
        return patientCredentials;
    }

    /**
     * Sets the value of the patientCredentials property.
     * 
     * @param value
     *            allowed object is {@link PatientCredentials }
     * 
     */
    public void setPatientCredentials(final PatientCredentials value) {
        this.patientCredentials = value;
    }

    /**
     * Gets the value of the procedureInfo property.
     * 
     * @return possible object is {@link ProcedureInfo }
     * 
     */
    public ProcedureInfo getProcedureInfo() {
        return procedureInfo;
    }

    /**
     * Sets the value of the procedureInfo property.
     * 
     * @param value
     *            allowed object is {@link ProcedureInfo }
     * 
     */
    public void setProcedureInfo(final ProcedureInfo value) {
        this.procedureInfo = value;
    }

    /**
     * Gets the value of the eritrocyteMass property.
     * 
     * @return possible object is {@link EritrocyteMass }
     * 
     */
    public EritrocyteMass getEritrocyteMass() {
        return eritrocyteMass;
    }

    /**
     * Sets the value of the eritrocyteMass property.
     * 
     * @param value
     *            allowed object is {@link EritrocyteMass }
     * 
     */
    public void setEritrocyteMass(final EritrocyteMass value) {
        this.eritrocyteMass = value;
    }

    /**
     * Gets the value of the measures property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list will be present
     * inside the JAXB object. This is why there is not a <CODE>set</CODE> method for the measures property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getMeasures().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link LaboratoryMeasure }
     * 
     * 
     */
    public List<LaboratoryMeasure> getMeasures() {
        if (measures == null) {
            measures = new ArrayList<LaboratoryMeasure>();
        }
        return this.measures;
    }

    /**
     * Gets the value of the finalVolumeList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list will be present
     * inside the JAXB object. This is why there is not a <CODE>set</CODE> method for the finalVolumeList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getFinalVolumeList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link FinalVolume }
     * 
     * 
     */
    public List<FinalVolume> getFinalVolumeList() {
        if (finalVolumeList == null) {
            finalVolumeList = new ArrayList<FinalVolume>();
        }
        return this.finalVolumeList;
    }

}
