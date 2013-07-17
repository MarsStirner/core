
package ru.cgm;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for HL7_document complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HL7_document">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="typeId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="setID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="versionNumber" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="title" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="effectiveTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="confidentialityCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="recordTarget" type="{http://cgm.ru}recordTargetInfo"/>
 *         &lt;element name="author" type="{http://cgm.ru}authorInfo"/>
 *         &lt;element name="custodian" type="{http://cgm.ru}custodianInfo"/>
 *         &lt;element name="componentOf" type="{http://cgm.ru}componentOfInfo"/>
 *         &lt;element name="component" type="{http://cgm.ru}componentInfo"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HL7_document", propOrder = {
    "typeId",
    "id",
    "setID",
    "versionNumber",
    "code",
    "title",
    "effectiveTime",
    "confidentialityCode",
    "recordTarget",
    "author",
    "custodian",
    "componentOf",
    "component"
})
public class HL7Document {

    @XmlElement(required = true)
    protected String typeId;
    @XmlElement(required = true)
    protected String id;
    @XmlElement(required = true)
    protected String setID;
    protected int versionNumber;
    @XmlElement(required = true)
    protected String code;
    @XmlElement(required = true)
    protected String title;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar effectiveTime;
    @XmlElement(required = true)
    protected String confidentialityCode;
    @XmlElement(required = true)
    protected RecordTargetInfo recordTarget;
    @XmlElement(required = true)
    protected AuthorInfo author;
    @XmlElement(required = true)
    protected CustodianInfo custodian;
    @XmlElement(required = true)
    protected ComponentOfInfo componentOf;
    @XmlElement(required = true)
    protected ComponentInfo component;

    /**
     * Gets the value of the typeId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypeId() {
        return typeId;
    }

    /**
     * Sets the value of the typeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypeId(String value) {
        this.typeId = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the setID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSetID() {
        return setID;
    }

    /**
     * Sets the value of the setID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSetID(String value) {
        this.setID = value;
    }

    /**
     * Gets the value of the versionNumber property.
     * 
     */
    public int getVersionNumber() {
        return versionNumber;
    }

    /**
     * Sets the value of the versionNumber property.
     * 
     */
    public void setVersionNumber(int value) {
        this.versionNumber = value;
    }

    /**
     * Gets the value of the code property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the value of the code property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCode(String value) {
        this.code = value;
    }

    /**
     * Gets the value of the title property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the value of the title property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitle(String value) {
        this.title = value;
    }

    /**
     * Gets the value of the effectiveTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEffectiveTime() {
        return effectiveTime;
    }

    /**
     * Sets the value of the effectiveTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEffectiveTime(XMLGregorianCalendar value) {
        this.effectiveTime = value;
    }

    /**
     * Gets the value of the confidentialityCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConfidentialityCode() {
        return confidentialityCode;
    }

    /**
     * Sets the value of the confidentialityCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConfidentialityCode(String value) {
        this.confidentialityCode = value;
    }

    /**
     * Gets the value of the recordTarget property.
     * 
     * @return
     *     possible object is
     *     {@link RecordTargetInfo }
     *     
     */
    public RecordTargetInfo getRecordTarget() {
        return recordTarget;
    }

    /**
     * Sets the value of the recordTarget property.
     * 
     * @param value
     *     allowed object is
     *     {@link RecordTargetInfo }
     *     
     */
    public void setRecordTarget(RecordTargetInfo value) {
        this.recordTarget = value;
    }

    /**
     * Gets the value of the author property.
     * 
     * @return
     *     possible object is
     *     {@link AuthorInfo }
     *     
     */
    public AuthorInfo getAuthor() {
        return author;
    }

    /**
     * Sets the value of the author property.
     * 
     * @param value
     *     allowed object is
     *     {@link AuthorInfo }
     *     
     */
    public void setAuthor(AuthorInfo value) {
        this.author = value;
    }

    /**
     * Gets the value of the custodian property.
     * 
     * @return
     *     possible object is
     *     {@link CustodianInfo }
     *     
     */
    public CustodianInfo getCustodian() {
        return custodian;
    }

    /**
     * Sets the value of the custodian property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustodianInfo }
     *     
     */
    public void setCustodian(CustodianInfo value) {
        this.custodian = value;
    }

    /**
     * Gets the value of the componentOf property.
     * 
     * @return
     *     possible object is
     *     {@link ComponentOfInfo }
     *     
     */
    public ComponentOfInfo getComponentOf() {
        return componentOf;
    }

    /**
     * Sets the value of the componentOf property.
     * 
     * @param value
     *     allowed object is
     *     {@link ComponentOfInfo }
     *     
     */
    public void setComponentOf(ComponentOfInfo value) {
        this.componentOf = value;
    }

    /**
     * Gets the value of the component property.
     * 
     * @return
     *     possible object is
     *     {@link ComponentInfo }
     *     
     */
    public ComponentInfo getComponent() {
        return component;
    }

    /**
     * Sets the value of the component property.
     * 
     * @param value
     *     allowed object is
     *     {@link ComponentInfo }
     *     
     */
    public void setComponent(ComponentInfo value) {
        this.component = value;
    }

}
