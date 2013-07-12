
package ru.korus.tmis.pix.sda.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for Document complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Document">
 *   &lt;complexContent>
 *     &lt;extension base="{}SuperClass">
 *       &lt;sequence>
 *         &lt;element name="DocumentTime" type="{}TimeStamp" minOccurs="0"/>
 *         &lt;element name="NoteText" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DocumentType" type="{}DocumentType" minOccurs="0"/>
 *         &lt;element name="Clinician" type="{}CareProvider" minOccurs="0"/>
 *         &lt;element name="TranscriptionTime" type="{}TimeStamp" minOccurs="0"/>
 *         &lt;element name="AuthorizationTime" type="{}TimeStamp" minOccurs="0"/>
 *         &lt;element name="DocumentName" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="220"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="FileType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Stream" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *         &lt;element name="DocumentURL" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="220"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="DocumentNumber" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="220"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Facilities" type="{}ArrayOfOrganizationOrganization" minOccurs="0"/>
 *         &lt;element name="Status" type="{}DocumentStatus" minOccurs="0"/>
 *         &lt;element name="ActionTime" type="{}TimeStamp" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Document", propOrder = {
    "documentTime",
    "noteText",
    "documentType",
    "clinician",
    "transcriptionTime",
    "authorizationTime",
    "documentName",
    "fileType",
    "stream",
    "documentURL",
    "documentNumber",
    "facilities",
    "status",
    "actionTime"
})
public class Document
    extends SuperClass
{

    @XmlElement(name = "DocumentTime")
    protected XMLGregorianCalendar documentTime;
    @XmlElement(name = "NoteText")
    protected String noteText;
    @XmlElement(name = "DocumentType")
    protected DocumentType documentType;
    @XmlElement(name = "Clinician")
    protected CareProvider clinician;
    @XmlElement(name = "TranscriptionTime")
    protected XMLGregorianCalendar transcriptionTime;
    @XmlElement(name = "AuthorizationTime")
    protected XMLGregorianCalendar authorizationTime;
    @XmlElement(name = "DocumentName")
    protected String documentName;
    @XmlElement(name = "FileType")
    protected String fileType;
    @XmlElement(name = "Stream")
    protected byte[] stream;
    @XmlElement(name = "DocumentURL")
    protected String documentURL;
    @XmlElement(name = "DocumentNumber")
    protected String documentNumber;
    @XmlElement(name = "Facilities")
    protected ArrayOfOrganizationOrganization facilities;
    @XmlElement(name = "Status")
    protected DocumentStatus status;
    @XmlElement(name = "ActionTime")
    protected XMLGregorianCalendar actionTime;

    /**
     * Gets the value of the documentTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDocumentTime() {
        return documentTime;
    }

    /**
     * Sets the value of the documentTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDocumentTime(XMLGregorianCalendar value) {
        this.documentTime = value;
    }

    /**
     * Gets the value of the noteText property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNoteText() {
        return noteText;
    }

    /**
     * Sets the value of the noteText property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNoteText(String value) {
        this.noteText = value;
    }

    /**
     * Gets the value of the documentType property.
     * 
     * @return
     *     possible object is
     *     {@link DocumentType }
     *     
     */
    public DocumentType getDocumentType() {
        return documentType;
    }

    /**
     * Sets the value of the documentType property.
     * 
     * @param value
     *     allowed object is
     *     {@link DocumentType }
     *     
     */
    public void setDocumentType(DocumentType value) {
        this.documentType = value;
    }

    /**
     * Gets the value of the clinician property.
     * 
     * @return
     *     possible object is
     *     {@link CareProvider }
     *     
     */
    public CareProvider getClinician() {
        return clinician;
    }

    /**
     * Sets the value of the clinician property.
     * 
     * @param value
     *     allowed object is
     *     {@link CareProvider }
     *     
     */
    public void setClinician(CareProvider value) {
        this.clinician = value;
    }

    /**
     * Gets the value of the transcriptionTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTranscriptionTime() {
        return transcriptionTime;
    }

    /**
     * Sets the value of the transcriptionTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTranscriptionTime(XMLGregorianCalendar value) {
        this.transcriptionTime = value;
    }

    /**
     * Gets the value of the authorizationTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getAuthorizationTime() {
        return authorizationTime;
    }

    /**
     * Sets the value of the authorizationTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setAuthorizationTime(XMLGregorianCalendar value) {
        this.authorizationTime = value;
    }

    /**
     * Gets the value of the documentName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocumentName() {
        return documentName;
    }

    /**
     * Sets the value of the documentName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocumentName(String value) {
        this.documentName = value;
    }

    /**
     * Gets the value of the fileType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFileType() {
        return fileType;
    }

    /**
     * Sets the value of the fileType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFileType(String value) {
        this.fileType = value;
    }

    /**
     * Gets the value of the stream property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getStream() {
        return stream;
    }

    /**
     * Sets the value of the stream property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setStream(byte[] value) {
        this.stream = value;
    }

    /**
     * Gets the value of the documentURL property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocumentURL() {
        return documentURL;
    }

    /**
     * Sets the value of the documentURL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocumentURL(String value) {
        this.documentURL = value;
    }

    /**
     * Gets the value of the documentNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocumentNumber() {
        return documentNumber;
    }

    /**
     * Sets the value of the documentNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocumentNumber(String value) {
        this.documentNumber = value;
    }

    /**
     * Gets the value of the facilities property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfOrganizationOrganization }
     *     
     */
    public ArrayOfOrganizationOrganization getFacilities() {
        return facilities;
    }

    /**
     * Sets the value of the facilities property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfOrganizationOrganization }
     *     
     */
    public void setFacilities(ArrayOfOrganizationOrganization value) {
        this.facilities = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link DocumentStatus }
     *     
     */
    public DocumentStatus getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link DocumentStatus }
     *     
     */
    public void setStatus(DocumentStatus value) {
        this.status = value;
    }

    /**
     * Gets the value of the actionTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getActionTime() {
        return actionTime;
    }

    /**
     * Sets the value of the actionTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setActionTime(XMLGregorianCalendar value) {
        this.actionTime = value;
    }

}
