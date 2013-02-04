
package ru.korus.tmis.ws.transfusion;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for orderBloodComponents complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="orderBloodComponents">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="patientCredentials" type="{http://www.korusconsulting.ru}PatientCredentials" minOccurs="0"/>
 *         &lt;element name="orderInformation" type="{http://www.korusconsulting.ru}OrderInformation" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "orderBloodComponents", propOrder = {
    "patientCredentials",
    "orderInformation"
})
public class OrderBloodComponents {

    protected PatientCredentials patientCredentials;
    protected OrderInformation orderInformation;

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
     * Gets the value of the orderInformation property.
     * 
     * @return
     *     possible object is
     *     {@link OrderInformation }
     *     
     */
    public OrderInformation getOrderInformation() {
        return orderInformation;
    }

    /**
     * Sets the value of the orderInformation property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrderInformation }
     *     
     */
    public void setOrderInformation(OrderInformation value) {
        this.orderInformation = value;
    }

}
