
package ru.korus.tmis.laboratory.altey.ws;

import javax.xml.bind.annotation.*;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PatientInfo" type="{http://www.korusconsulting.ru}PatientInfo"/>
 *         &lt;element name="DiagnosticRequestInfo" type="{http://www.korusconsulting.ru}DiagnosticRequestInfo"/>
 *         &lt;element name="BiomaterialInfo" type="{http://www.korusconsulting.ru}BiomaterialInfo"/>
 *         &lt;element name="OrderInfo" type="{http://www.korusconsulting.ru}OrderInfo"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "patientInfo",
    "diagnosticRequestInfo",
    "biomaterialInfo",
    "orderInfo"
})
@XmlRootElement(name = "queryAnalysis")
public class QueryAnalysis {

    @XmlElement(name = "PatientInfo", required = true)
    protected PatientInfo patientInfo;
    @XmlElement(name = "DiagnosticRequestInfo", required = true)
    protected DiagnosticRequestInfo diagnosticRequestInfo;
    @XmlElement(name = "BiomaterialInfo", required = true)
    protected BiomaterialInfo biomaterialInfo;
    @XmlElement(name = "OrderInfo", required = true)
    protected OrderInfo orderInfo;

    /**
     * Gets the value of the patientInfo property.
     * 
     * @return
     *     possible object is
     *     {@link PatientInfo }
     *
     */
    public PatientInfo getPatientInfo() {
        return patientInfo;
    }

    /**
     * Sets the value of the patientInfo property.
     *
     * @param value
     *     allowed object is
     *     {@link PatientInfo }
     *
     */
    public void setPatientInfo(PatientInfo value) {
        this.patientInfo = value;
    }

    /**
     * Gets the value of the diagnosticRequestInfo property.
     *
     * @return
     *     possible object is
     *     {@link DiagnosticRequestInfo }
     *
     */
    public DiagnosticRequestInfo getDiagnosticRequestInfo() {
        return diagnosticRequestInfo;
    }

    /**
     * Sets the value of the diagnosticRequestInfo property.
     *
     * @param value
     *     allowed object is
     *     {@link DiagnosticRequestInfo }
     *
     */
    public void setDiagnosticRequestInfo(DiagnosticRequestInfo value) {
        this.diagnosticRequestInfo = value;
    }

    /**
     * Gets the value of the biomaterialInfo property.
     *
     * @return
     *     possible object is
     *     {@link BiomaterialInfo }
     *
     */
    public BiomaterialInfo getBiomaterialInfo() {
        return biomaterialInfo;
    }

    /**
     * Sets the value of the biomaterialInfo property.
     *
     * @param value
     *     allowed object is
     *     {@link BiomaterialInfo }
     *
     */
    public void setBiomaterialInfo(BiomaterialInfo value) {
        this.biomaterialInfo = value;
    }

    /**
     * Gets the value of the orderInfo property.
     *
     * @return
     *     possible object is
     *     {@link OrderInfo }
     *
     */
    public OrderInfo getOrderInfo() {
        return orderInfo;
    }

    /**
     * Sets the value of the orderInfo property.
     *
     * @param value
     *     allowed object is
     *     {@link OrderInfo }
     *     
     */
    public void setOrderInfo(OrderInfo value) {
        this.orderInfo = value;
    }

}
