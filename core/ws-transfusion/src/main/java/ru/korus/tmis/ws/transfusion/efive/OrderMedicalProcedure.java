
package ru.korus.tmis.ws.transfusion.efive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for orderMedicalProcedure complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="orderMedicalProcedure">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="donorInfo" type="{http://www.korusconsulting.ru}DonorInfo" minOccurs="0"/>
 *         &lt;element name="patientCredentials" type="{http://www.korusconsulting.ru}PatientCredentials" minOccurs="0"/>
 *         &lt;element name="procedureInfo" type="{http://www.korusconsulting.ru}ProcedureInfo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "orderMedicalProcedure", propOrder = {
    "donorInfo",
    "patientCredentials",
    "procedureInfo"
})
public class OrderMedicalProcedure {

    protected DonorInfo donorInfo;
    protected PatientCredentials patientCredentials;
    protected ProcedureInfo procedureInfo;

    /**
     * Gets the value of the donorInfo property.
     * 
     * @return
     *     possible object is
     *     {@link DonorInfo }
     *     
     */
    public DonorInfo getDonorInfo() {
        return donorInfo;
    }

    /**
     * Sets the value of the donorInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link DonorInfo }
     *     
     */
    public void setDonorInfo(DonorInfo value) {
        this.donorInfo = value;
    }

    /**
     * Gets the value of the patientCredentials property.
     * 
     * @return
     *     possible object is
     *     {@link PatientCredentials }
     *     
     */
    public PatientCredentials getPatientCredentials() {
        return patientCredentials;
    }

    /**
     * Sets the value of the patientCredentials property.
     * 
     * @param value
     *     allowed object is
     *     {@link PatientCredentials }
     *     
     */
    public void setPatientCredentials(PatientCredentials value) {
        this.patientCredentials = value;
    }

    /**
     * Gets the value of the procedureInfo property.
     * 
     * @return
     *     possible object is
     *     {@link ProcedureInfo }
     *     
     */
    public ProcedureInfo getProcedureInfo() {
        return procedureInfo;
    }

    /**
     * Sets the value of the procedureInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProcedureInfo }
     *     
     */
    public void setProcedureInfo(ProcedureInfo value) {
        this.procedureInfo = value;
    }

}
