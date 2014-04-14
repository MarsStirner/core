
package ru.korus.tmis.pix.sda.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for Occupation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Occupation">
 *   &lt;complexContent>
 *     &lt;extension base="{http://schemas.intersystems.ru/hs/ehr/v1}BaseSerial">
 *       &lt;sequence>
 *         &lt;element name="organizationName" type="{http://schemas.intersystems.ru/hs/ehr/v1}String" minOccurs="0"/>
 *         &lt;element name="organizationType" type="{http://schemas.intersystems.ru/hs/ehr/v1}CodeAndName" minOccurs="0"/>
 *         &lt;element name="position" type="{http://schemas.intersystems.ru/hs/ehr/v1}String" minOccurs="0"/>
 *         &lt;element name="profession" type="{http://schemas.intersystems.ru/hs/ehr/v1}String" minOccurs="0"/>
 *         &lt;element name="laborType" type="{http://schemas.intersystems.ru/hs/ehr/v1}CodeAndName" minOccurs="0"/>
 *         &lt;element name="socialProfessionalGroup" type="{http://schemas.intersystems.ru/hs/ehr/v1}CodeAndName" minOccurs="0"/>
 *         &lt;element name="okved" type="{http://schemas.intersystems.ru/hs/ehr/v1}CodeAndName" minOccurs="0"/>
 *         &lt;element name="startedOn" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="harmfulConditions" type="{http://schemas.intersystems.ru/hs/ehr/v1}String" minOccurs="0"/>
 *         &lt;element name="dayDuration" type="{http://schemas.intersystems.ru/hs/ehr/v1}String" minOccurs="0"/>
 *         &lt;element name="shiftDuration" type="{http://schemas.intersystems.ru/hs/ehr/v1}String" minOccurs="0"/>
 *         &lt;element name="ogrn" type="{http://schemas.intersystems.ru/hs/ehr/v1}String" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Occupation", propOrder = {
    "organizationName",
    "organizationType",
    "position",
    "profession",
    "laborType",
    "socialProfessionalGroup",
    "okved",
    "startedOn",
    "harmfulConditions",
    "dayDuration",
    "shiftDuration",
    "ogrn"
})
public class Occupation
    extends BaseSerial
{

    protected String organizationName;
    protected CodeAndName organizationType;
    protected String position;
    protected String profession;
    protected CodeAndName laborType;
    protected CodeAndName socialProfessionalGroup;
    protected CodeAndName okved;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar startedOn;
    protected String harmfulConditions;
    protected String dayDuration;
    protected String shiftDuration;
    protected String ogrn;

    /**
     * Gets the value of the organizationName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrganizationName() {
        return organizationName;
    }

    /**
     * Sets the value of the organizationName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrganizationName(String value) {
        this.organizationName = value;
    }

    /**
     * Gets the value of the organizationType property.
     * 
     * @return
     *     possible object is
     *     {@link CodeAndName }
     *     
     */
    public CodeAndName getOrganizationType() {
        return organizationType;
    }

    /**
     * Sets the value of the organizationType property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodeAndName }
     *     
     */
    public void setOrganizationType(CodeAndName value) {
        this.organizationType = value;
    }

    /**
     * Gets the value of the position property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPosition() {
        return position;
    }

    /**
     * Sets the value of the position property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPosition(String value) {
        this.position = value;
    }

    /**
     * Gets the value of the profession property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProfession() {
        return profession;
    }

    /**
     * Sets the value of the profession property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProfession(String value) {
        this.profession = value;
    }

    /**
     * Gets the value of the laborType property.
     * 
     * @return
     *     possible object is
     *     {@link CodeAndName }
     *     
     */
    public CodeAndName getLaborType() {
        return laborType;
    }

    /**
     * Sets the value of the laborType property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodeAndName }
     *     
     */
    public void setLaborType(CodeAndName value) {
        this.laborType = value;
    }

    /**
     * Gets the value of the socialProfessionalGroup property.
     * 
     * @return
     *     possible object is
     *     {@link CodeAndName }
     *     
     */
    public CodeAndName getSocialProfessionalGroup() {
        return socialProfessionalGroup;
    }

    /**
     * Sets the value of the socialProfessionalGroup property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodeAndName }
     *     
     */
    public void setSocialProfessionalGroup(CodeAndName value) {
        this.socialProfessionalGroup = value;
    }

    /**
     * Gets the value of the okved property.
     * 
     * @return
     *     possible object is
     *     {@link CodeAndName }
     *     
     */
    public CodeAndName getOkved() {
        return okved;
    }

    /**
     * Sets the value of the okved property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodeAndName }
     *     
     */
    public void setOkved(CodeAndName value) {
        this.okved = value;
    }

    /**
     * Gets the value of the startedOn property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStartedOn() {
        return startedOn;
    }

    /**
     * Sets the value of the startedOn property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStartedOn(XMLGregorianCalendar value) {
        this.startedOn = value;
    }

    /**
     * Gets the value of the harmfulConditions property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHarmfulConditions() {
        return harmfulConditions;
    }

    /**
     * Sets the value of the harmfulConditions property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHarmfulConditions(String value) {
        this.harmfulConditions = value;
    }

    /**
     * Gets the value of the dayDuration property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDayDuration() {
        return dayDuration;
    }

    /**
     * Sets the value of the dayDuration property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDayDuration(String value) {
        this.dayDuration = value;
    }

    /**
     * Gets the value of the shiftDuration property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShiftDuration() {
        return shiftDuration;
    }

    /**
     * Sets the value of the shiftDuration property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShiftDuration(String value) {
        this.shiftDuration = value;
    }

    /**
     * Gets the value of the ogrn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOgrn() {
        return ogrn;
    }

    /**
     * Sets the value of the ogrn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOgrn(String value) {
        this.ogrn = value;
    }

}
