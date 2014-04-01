
package ru.korus.tmis.ehr.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for DocumentParams complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DocumentParams">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="patientMRN" type="{}InstanceIdentifier" minOccurs="0"/>
 *         &lt;element name="status" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="SUBMITTED"/>
 *               &lt;enumeration value="APPROVED"/>
 *               &lt;enumeration value="DEPRECATED"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="docDateLow" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="docDateHigh" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DocumentParams", propOrder = {
    "patientMRN",
    "status",
    "docDateLow",
    "docDateHigh"
})
public class DocumentParams {

    protected InstanceIdentifier patientMRN;
    protected String status;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar docDateLow;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar docDateHigh;

    /**
     * Gets the value of the patientMRN property.
     * 
     * @return
     *     possible object is
     *     {@link InstanceIdentifier }
     *     
     */
    public InstanceIdentifier getPatientMRN() {
        return patientMRN;
    }

    /**
     * Sets the value of the patientMRN property.
     * 
     * @param value
     *     allowed object is
     *     {@link InstanceIdentifier }
     *     
     */
    public void setPatientMRN(InstanceIdentifier value) {
        this.patientMRN = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

    /**
     * Gets the value of the docDateLow property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDocDateLow() {
        return docDateLow;
    }

    /**
     * Sets the value of the docDateLow property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDocDateLow(XMLGregorianCalendar value) {
        this.docDateLow = value;
    }

    /**
     * Gets the value of the docDateHigh property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDocDateHigh() {
        return docDateHigh;
    }

    /**
     * Sets the value of the docDateHigh property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDocDateHigh(XMLGregorianCalendar value) {
        this.docDateHigh = value;
    }

}
