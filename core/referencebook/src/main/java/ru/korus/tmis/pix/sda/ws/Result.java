
package ru.korus.tmis.pix.sda.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for Result complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Result">
 *   &lt;complexContent>
 *     &lt;extension base="{}SuperClass">
 *       &lt;sequence>
 *         &lt;element name="ResultType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ResultItems" type="{}ArrayOfLabResultItemLabResultItem" minOccurs="0"/>
 *         &lt;element name="VerifiedBy" type="{}CareProvider" minOccurs="0"/>
 *         &lt;element name="ResultTime" type="{}TimeStamp" minOccurs="0"/>
 *         &lt;element name="ResultStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ResultText" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FileType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DocumentName" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="220"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Stream" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *         &lt;element name="DocumentURL" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="32000"/>
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
 *         &lt;element name="Comments" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AuthorizationTime" type="{}TimeStamp" minOccurs="0"/>
 *         &lt;element name="PerformedAt" type="{}Organization" minOccurs="0"/>
 *         &lt;element name="ResultInterpretation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="GUID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="GUIDExpDate" type="{}TimeStamp" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Result", propOrder = {
    "resultType",
    "resultItems",
    "verifiedBy",
    "resultTime",
    "resultStatus",
    "resultText",
    "fileType",
    "documentName",
    "stream",
    "documentURL",
    "documentNumber",
    "comments",
    "authorizationTime",
    "performedAt",
    "resultInterpretation",
    "guid",
    "guidExpDate"
})
public class Result
    extends SuperClass
{

    @XmlElement(name = "ResultType")
    protected String resultType;
    @XmlElement(name = "ResultItems")
    protected ArrayOfLabResultItemLabResultItem resultItems;
    @XmlElement(name = "VerifiedBy")
    protected CareProvider verifiedBy;
    @XmlElement(name = "ResultTime")
    protected XMLGregorianCalendar resultTime;
    @XmlElement(name = "ResultStatus")
    protected String resultStatus;
    @XmlElement(name = "ResultText")
    protected String resultText;
    @XmlElement(name = "FileType")
    protected String fileType;
    @XmlElement(name = "DocumentName")
    protected String documentName;
    @XmlElement(name = "Stream")
    protected byte[] stream;
    @XmlElement(name = "DocumentURL")
    protected String documentURL;
    @XmlElement(name = "DocumentNumber")
    protected String documentNumber;
    @XmlElement(name = "Comments")
    protected String comments;
    @XmlElement(name = "AuthorizationTime")
    protected XMLGregorianCalendar authorizationTime;
    @XmlElement(name = "PerformedAt")
    protected Organization performedAt;
    @XmlElement(name = "ResultInterpretation")
    protected String resultInterpretation;
    @XmlElement(name = "GUID")
    protected String guid;
    @XmlElement(name = "GUIDExpDate")
    protected XMLGregorianCalendar guidExpDate;

    /**
     * Gets the value of the resultType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResultType() {
        return resultType;
    }

    /**
     * Sets the value of the resultType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResultType(String value) {
        this.resultType = value;
    }

    /**
     * Gets the value of the resultItems property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfLabResultItemLabResultItem }
     *     
     */
    public ArrayOfLabResultItemLabResultItem getResultItems() {
        return resultItems;
    }

    /**
     * Sets the value of the resultItems property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfLabResultItemLabResultItem }
     *     
     */
    public void setResultItems(ArrayOfLabResultItemLabResultItem value) {
        this.resultItems = value;
    }

    /**
     * Gets the value of the verifiedBy property.
     * 
     * @return
     *     possible object is
     *     {@link CareProvider }
     *     
     */
    public CareProvider getVerifiedBy() {
        return verifiedBy;
    }

    /**
     * Sets the value of the verifiedBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link CareProvider }
     *     
     */
    public void setVerifiedBy(CareProvider value) {
        this.verifiedBy = value;
    }

    /**
     * Gets the value of the resultTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getResultTime() {
        return resultTime;
    }

    /**
     * Sets the value of the resultTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setResultTime(XMLGregorianCalendar value) {
        this.resultTime = value;
    }

    /**
     * Gets the value of the resultStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResultStatus() {
        return resultStatus;
    }

    /**
     * Sets the value of the resultStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResultStatus(String value) {
        this.resultStatus = value;
    }

    /**
     * Gets the value of the resultText property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResultText() {
        return resultText;
    }

    /**
     * Sets the value of the resultText property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResultText(String value) {
        this.resultText = value;
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
     * Gets the value of the comments property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComments() {
        return comments;
    }

    /**
     * Sets the value of the comments property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComments(String value) {
        this.comments = value;
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
     * Gets the value of the performedAt property.
     * 
     * @return
     *     possible object is
     *     {@link Organization }
     *     
     */
    public Organization getPerformedAt() {
        return performedAt;
    }

    /**
     * Sets the value of the performedAt property.
     * 
     * @param value
     *     allowed object is
     *     {@link Organization }
     *     
     */
    public void setPerformedAt(Organization value) {
        this.performedAt = value;
    }

    /**
     * Gets the value of the resultInterpretation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResultInterpretation() {
        return resultInterpretation;
    }

    /**
     * Sets the value of the resultInterpretation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResultInterpretation(String value) {
        this.resultInterpretation = value;
    }

    /**
     * Gets the value of the guid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGUID() {
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
    public void setGUID(String value) {
        this.guid = value;
    }

    /**
     * Gets the value of the guidExpDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getGUIDExpDate() {
        return guidExpDate;
    }

    /**
     * Sets the value of the guidExpDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setGUIDExpDate(XMLGregorianCalendar value) {
        this.guidExpDate = value;
    }

}
