
package ru.korus.tmis.ws.finance.odvd;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


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
 *         &lt;element name="idTreatment" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="numTreatment" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dateTreatment" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="codePatient" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="patientName" type="{http://localhost/Policlinic}TableName"/>
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
    "idTreatment",
    "numTreatment",
    "dateTreatment",
    "codePatient",
    "patientName"
})
@XmlRootElement(name = "putTreatment")
public class PutTreatment {

    @XmlElement(required = true)
    protected BigInteger idTreatment;
    @XmlElement(required = true)
    protected String numTreatment;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dateTreatment;
    @XmlElement(required = true)
    protected String codePatient;
    @XmlElement(required = true, nillable = true)
    protected TableName patientName;

    /**
     * Gets the value of the idTreatment property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIdTreatment() {
        return idTreatment;
    }

    /**
     * Sets the value of the idTreatment property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIdTreatment(BigInteger value) {
        this.idTreatment = value;
    }

    /**
     * Gets the value of the numTreatment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumTreatment() {
        return numTreatment;
    }

    /**
     * Sets the value of the numTreatment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumTreatment(String value) {
        this.numTreatment = value;
    }

    /**
     * Gets the value of the dateTreatment property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateTreatment() {
        return dateTreatment;
    }

    /**
     * Sets the value of the dateTreatment property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateTreatment(XMLGregorianCalendar value) {
        this.dateTreatment = value;
    }

    /**
     * Gets the value of the codePatient property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodePatient() {
        return codePatient;
    }

    /**
     * Sets the value of the codePatient property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodePatient(String value) {
        this.codePatient = value;
    }

    /**
     * Gets the value of the patientName property.
     * 
     * @return
     *     possible object is
     *     {@link TableName }
     *     
     */
    public TableName getPatientName() {
        return patientName;
    }

    /**
     * Sets the value of the patientName property.
     * 
     * @param value
     *     allowed object is
     *     {@link TableName }
     *     
     */
    public void setPatientName(TableName value) {
        this.patientName = value;
    }

}
