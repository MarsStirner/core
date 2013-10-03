
package ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for COCT_MT030203UV07.LanguageCommunication complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="COCT_MT030203UV07.LanguageCommunication">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;group ref="{urn:hl7-org:v3}InfrastructureRootElements"/>
 *         &lt;element name="languageCode" type="{urn:hl7-org:v3}CD"/>
 *         &lt;element name="modeCode" type="{urn:hl7-org:v3}CD" minOccurs="0"/>
 *         &lt;element name="proficiencyLevelCode" type="{urn:hl7-org:v3}CD" minOccurs="0"/>
 *         &lt;element name="preferenceInd" type="{urn:hl7-org:v3}BL" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{urn:hl7-org:v3}InfrastructureRootAttributes"/>
 *       &lt;attribute name="nullFlavor" type="{urn:hl7-org:v3}NullFlavor" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "COCT_MT030203UV07.LanguageCommunication", propOrder = {
    "realmCode",
    "typeId",
    "templateId",
    "languageCode",
    "modeCode",
    "proficiencyLevelCode",
    "preferenceInd"
})
public class COCTMT030203UV07LanguageCommunication {

    protected DSETCS realmCode;
    protected II typeId;
    protected LISTII templateId;
    @XmlElement(required = true)
    protected CD languageCode;
    protected CD modeCode;
    protected CD proficiencyLevelCode;
    protected BL preferenceInd;
    @XmlAttribute(name = "nullFlavor")
    protected NullFlavor nullFlavor;

    /**
     * Gets the value of the realmCode property.
     * 
     * @return
     *     possible object is
     *     {@link DSETCS }
     *     
     */
    public DSETCS getRealmCode() {
        return realmCode;
    }

    /**
     * Sets the value of the realmCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link DSETCS }
     *     
     */
    public void setRealmCode(DSETCS value) {
        this.realmCode = value;
    }

    /**
     * Gets the value of the typeId property.
     * 
     * @return
     *     possible object is
     *     {@link II }
     *     
     */
    public II getTypeId() {
        return typeId;
    }

    /**
     * Sets the value of the typeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link II }
     *     
     */
    public void setTypeId(II value) {
        this.typeId = value;
    }

    /**
     * Gets the value of the templateId property.
     * 
     * @return
     *     possible object is
     *     {@link LISTII }
     *     
     */
    public LISTII getTemplateId() {
        return templateId;
    }

    /**
     * Sets the value of the templateId property.
     * 
     * @param value
     *     allowed object is
     *     {@link LISTII }
     *     
     */
    public void setTemplateId(LISTII value) {
        this.templateId = value;
    }

    /**
     * Gets the value of the languageCode property.
     * 
     * @return
     *     possible object is
     *     {@link CD }
     *     
     */
    public CD getLanguageCode() {
        return languageCode;
    }

    /**
     * Sets the value of the languageCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link CD }
     *     
     */
    public void setLanguageCode(CD value) {
        this.languageCode = value;
    }

    /**
     * Gets the value of the modeCode property.
     * 
     * @return
     *     possible object is
     *     {@link CD }
     *     
     */
    public CD getModeCode() {
        return modeCode;
    }

    /**
     * Sets the value of the modeCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link CD }
     *     
     */
    public void setModeCode(CD value) {
        this.modeCode = value;
    }

    /**
     * Gets the value of the proficiencyLevelCode property.
     * 
     * @return
     *     possible object is
     *     {@link CD }
     *     
     */
    public CD getProficiencyLevelCode() {
        return proficiencyLevelCode;
    }

    /**
     * Sets the value of the proficiencyLevelCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link CD }
     *     
     */
    public void setProficiencyLevelCode(CD value) {
        this.proficiencyLevelCode = value;
    }

    /**
     * Gets the value of the preferenceInd property.
     * 
     * @return
     *     possible object is
     *     {@link BL }
     *     
     */
    public BL getPreferenceInd() {
        return preferenceInd;
    }

    /**
     * Sets the value of the preferenceInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link BL }
     *     
     */
    public void setPreferenceInd(BL value) {
        this.preferenceInd = value;
    }

    /**
     * Gets the value of the nullFlavor property.
     * 
     * @return
     *     possible object is
     *     {@link NullFlavor }
     *     
     */
    public NullFlavor getNullFlavor() {
        return nullFlavor;
    }

    /**
     * Sets the value of the nullFlavor property.
     * 
     * @param value
     *     allowed object is
     *     {@link NullFlavor }
     *     
     */
    public void setNullFlavor(NullFlavor value) {
        this.nullFlavor = value;
    }

}
