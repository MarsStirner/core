
package ru.korus.tmis.ehr.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for PatientParams complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PatientParams">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="gender" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="M"/>
 *               &lt;enumeration value="F"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="dobLow" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="dobHigh" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="mrn" type="{}InstanceIdentifier" minOccurs="0"/>
 *         &lt;element name="familyName" type="{}String" minOccurs="0"/>
 *         &lt;element name="givenName" type="{}String" minOccurs="0"/>
 *         &lt;element name="middleName" type="{}String" minOccurs="0"/>
 *         &lt;element name="snils" type="{}Snils" minOccurs="0"/>
 *         &lt;element name="omsPolicy" type="{}SeriesNumberAndType" minOccurs="0"/>
 *         &lt;element name="identityDocument" type="{}SeriesNumberAndType" minOccurs="0"/>
 *         &lt;element name="isUnidentified" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="extraMark" type="{}String" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PatientParams", propOrder = {
    "gender",
    "dobLow",
    "dobHigh",
    "mrn",
    "familyName",
    "givenName",
    "middleName",
    "snils",
    "omsPolicy",
    "identityDocument",
    "isUnidentified",
    "extraMark"
})
public class PatientParams {

    protected String gender;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dobLow;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dobHigh;
    protected InstanceIdentifier mrn;
    protected String familyName;
    protected String givenName;
    protected String middleName;
    protected String snils;
    protected SeriesNumberAndType omsPolicy;
    protected SeriesNumberAndType identityDocument;
    protected Boolean isUnidentified;
    protected String extraMark;

    /**
     * Gets the value of the gender property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGender() {
        return gender;
    }

    /**
     * Sets the value of the gender property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGender(String value) {
        this.gender = value;
    }

    /**
     * Gets the value of the dobLow property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDobLow() {
        return dobLow;
    }

    /**
     * Sets the value of the dobLow property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDobLow(XMLGregorianCalendar value) {
        this.dobLow = value;
    }

    /**
     * Gets the value of the dobHigh property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDobHigh() {
        return dobHigh;
    }

    /**
     * Sets the value of the dobHigh property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDobHigh(XMLGregorianCalendar value) {
        this.dobHigh = value;
    }

    /**
     * Gets the value of the mrn property.
     * 
     * @return
     *     possible object is
     *     {@link InstanceIdentifier }
     *     
     */
    public InstanceIdentifier getMrn() {
        return mrn;
    }

    /**
     * Sets the value of the mrn property.
     * 
     * @param value
     *     allowed object is
     *     {@link InstanceIdentifier }
     *     
     */
    public void setMrn(InstanceIdentifier value) {
        this.mrn = value;
    }

    /**
     * Gets the value of the familyName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFamilyName() {
        return familyName;
    }

    /**
     * Sets the value of the familyName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFamilyName(String value) {
        this.familyName = value;
    }

    /**
     * Gets the value of the givenName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGivenName() {
        return givenName;
    }

    /**
     * Sets the value of the givenName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGivenName(String value) {
        this.givenName = value;
    }

    /**
     * Gets the value of the middleName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     * Sets the value of the middleName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMiddleName(String value) {
        this.middleName = value;
    }

    /**
     * Gets the value of the snils property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSnils() {
        return snils;
    }

    /**
     * Sets the value of the snils property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSnils(String value) {
        this.snils = value;
    }

    /**
     * Gets the value of the omsPolicy property.
     * 
     * @return
     *     possible object is
     *     {@link SeriesNumberAndType }
     *     
     */
    public SeriesNumberAndType getOmsPolicy() {
        return omsPolicy;
    }

    /**
     * Sets the value of the omsPolicy property.
     * 
     * @param value
     *     allowed object is
     *     {@link SeriesNumberAndType }
     *     
     */
    public void setOmsPolicy(SeriesNumberAndType value) {
        this.omsPolicy = value;
    }

    /**
     * Gets the value of the identityDocument property.
     * 
     * @return
     *     possible object is
     *     {@link SeriesNumberAndType }
     *     
     */
    public SeriesNumberAndType getIdentityDocument() {
        return identityDocument;
    }

    /**
     * Sets the value of the identityDocument property.
     * 
     * @param value
     *     allowed object is
     *     {@link SeriesNumberAndType }
     *     
     */
    public void setIdentityDocument(SeriesNumberAndType value) {
        this.identityDocument = value;
    }

    /**
     * Gets the value of the isUnidentified property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsUnidentified() {
        return isUnidentified;
    }

    /**
     * Sets the value of the isUnidentified property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsUnidentified(Boolean value) {
        this.isUnidentified = value;
    }

    /**
     * Gets the value of the extraMark property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExtraMark() {
        return extraMark;
    }

    /**
     * Sets the value of the extraMark property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExtraMark(String value) {
        this.extraMark = value;
    }

}
