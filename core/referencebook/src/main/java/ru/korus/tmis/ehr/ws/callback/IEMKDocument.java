
package ru.korus.tmis.ehr.ws.callback;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Информация о СЭМД, полученная от ИЭМК. В элементе guid содержится идентификатор документа в ИЭМК.
 * 
 * <p>Java class for IEMKDocument complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IEMKDocument">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="facilityCode" type="{}String" minOccurs="0"/>
 *         &lt;element name="patientMRN" type="{}String" minOccurs="0"/>
 *         &lt;element name="enteredBy" type="{}Employee" minOccurs="0"/>
 *         &lt;element name="enteredOn" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="docType" type="{}CodeAndName" minOccurs="0"/>
 *         &lt;element name="docName" type="{}String" minOccurs="0"/>
 *         &lt;element name="guid" type="{}String" minOccurs="0"/>
 *         &lt;element name="hash" type="{}String" minOccurs="0"/>
 *         &lt;element name="languageCode" type="{}String" minOccurs="0"/>
 *         &lt;element name="serviceStartTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="serviceStopTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="recordNumber" type="{}String" minOccurs="0"/>
 *         &lt;element name="documentSignatureType" type="{}String" minOccurs="0"/>
 *         &lt;element name="size" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="legalAuthenticator" type="{}Employee" minOccurs="0"/>
 *         &lt;element name="description" type="{}String" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IEMKDocument", propOrder = {
    "facilityCode",
    "patientMRN",
    "enteredBy",
    "enteredOn",
    "docType",
    "docName",
    "guid",
    "hash",
    "languageCode",
    "serviceStartTime",
    "serviceStopTime",
    "recordNumber",
    "documentSignatureType",
    "size",
    "legalAuthenticator",
    "description"
})
public class IEMKDocument {

    protected String facilityCode;
    protected String patientMRN;
    protected Employee enteredBy;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar enteredOn;
    protected CodeAndName docType;
    protected String docName;
    protected String guid;
    protected String hash;
    protected String languageCode;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar serviceStartTime;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar serviceStopTime;
    protected String recordNumber;
    protected String documentSignatureType;
    protected Long size;
    protected Employee legalAuthenticator;
    protected String description;

    /**
     * Gets the value of the facilityCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFacilityCode() {
        return facilityCode;
    }

    /**
     * Sets the value of the facilityCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFacilityCode(String value) {
        this.facilityCode = value;
    }

    /**
     * Gets the value of the patientMRN property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPatientMRN() {
        return patientMRN;
    }

    /**
     * Sets the value of the patientMRN property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPatientMRN(String value) {
        this.patientMRN = value;
    }

    /**
     * Gets the value of the enteredBy property.
     * 
     * @return
     *     possible object is
     *     {@link Employee }
     *     
     */
    public Employee getEnteredBy() {
        return enteredBy;
    }

    /**
     * Sets the value of the enteredBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link Employee }
     *     
     */
    public void setEnteredBy(Employee value) {
        this.enteredBy = value;
    }

    /**
     * Gets the value of the enteredOn property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEnteredOn() {
        return enteredOn;
    }

    /**
     * Sets the value of the enteredOn property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEnteredOn(XMLGregorianCalendar value) {
        this.enteredOn = value;
    }

    /**
     * Gets the value of the docType property.
     * 
     * @return
     *     possible object is
     *     {@link CodeAndName }
     *     
     */
    public CodeAndName getDocType() {
        return docType;
    }

    /**
     * Sets the value of the docType property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodeAndName }
     *     
     */
    public void setDocType(CodeAndName value) {
        this.docType = value;
    }

    /**
     * Gets the value of the docName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocName() {
        return docName;
    }

    /**
     * Sets the value of the docName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocName(String value) {
        this.docName = value;
    }

    /**
     * Gets the value of the guid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGuid() {
        return guid;
    }

    /**
     * Sets the value of the guid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGuid(String value) {
        this.guid = value;
    }

    /**
     * Gets the value of the hash property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHash() {
        return hash;
    }

    /**
     * Sets the value of the hash property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHash(String value) {
        this.hash = value;
    }

    /**
     * Gets the value of the languageCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLanguageCode() {
        return languageCode;
    }

    /**
     * Sets the value of the languageCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLanguageCode(String value) {
        this.languageCode = value;
    }

    /**
     * Gets the value of the serviceStartTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getServiceStartTime() {
        return serviceStartTime;
    }

    /**
     * Sets the value of the serviceStartTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setServiceStartTime(XMLGregorianCalendar value) {
        this.serviceStartTime = value;
    }

    /**
     * Gets the value of the serviceStopTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getServiceStopTime() {
        return serviceStopTime;
    }

    /**
     * Sets the value of the serviceStopTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setServiceStopTime(XMLGregorianCalendar value) {
        this.serviceStopTime = value;
    }

    /**
     * Gets the value of the recordNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecordNumber() {
        return recordNumber;
    }

    /**
     * Sets the value of the recordNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecordNumber(String value) {
        this.recordNumber = value;
    }

    /**
     * Gets the value of the documentSignatureType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocumentSignatureType() {
        return documentSignatureType;
    }

    /**
     * Sets the value of the documentSignatureType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocumentSignatureType(String value) {
        this.documentSignatureType = value;
    }

    /**
     * Gets the value of the size property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getSize() {
        return size;
    }

    /**
     * Sets the value of the size property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setSize(Long value) {
        this.size = value;
    }

    /**
     * Gets the value of the legalAuthenticator property.
     * 
     * @return
     *     possible object is
     *     {@link Employee }
     *     
     */
    public Employee getLegalAuthenticator() {
        return legalAuthenticator;
    }

    /**
     * Sets the value of the legalAuthenticator property.
     * 
     * @param value
     *     allowed object is
     *     {@link Employee }
     *     
     */
    public void setLegalAuthenticator(Employee value) {
        this.legalAuthenticator = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

}
