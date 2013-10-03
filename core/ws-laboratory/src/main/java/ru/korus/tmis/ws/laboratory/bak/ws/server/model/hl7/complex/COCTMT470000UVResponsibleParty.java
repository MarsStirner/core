
package ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for COCT_MT470000UV.ResponsibleParty complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="COCT_MT470000UV.ResponsibleParty">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;group ref="{urn:hl7-org:v3}InfrastructureRootElements"/>
 *         &lt;element name="noteText" type="{urn:hl7-org:v3}ED" minOccurs="0"/>
 *         &lt;element name="time" type="{urn:hl7-org:v3}TS" minOccurs="0"/>
 *         &lt;element name="modeCode" type="{urn:hl7-org:v3}CD" minOccurs="0"/>
 *         &lt;element name="signatureCode" type="{urn:hl7-org:v3}CS" minOccurs="0"/>
 *         &lt;element name="signatureText" type="{urn:hl7-org:v3}ED" minOccurs="0"/>
 *         &lt;element name="responsibleParty" type="{urn:hl7-org:v3}COCT_MT040200UV09.ResponsibleParty"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{urn:hl7-org:v3}InfrastructureRootAttributes"/>
 *       &lt;attribute name="nullFlavor" type="{urn:hl7-org:v3}NullFlavor" />
 *       &lt;attribute name="typeCode" use="required" type="{urn:hl7-org:v3}ParticipationResponsibleParty" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "COCT_MT470000UV.ResponsibleParty", propOrder = {
    "realmCode",
    "typeId",
    "templateId",
    "noteText",
    "time",
    "modeCode",
    "signatureCode",
    "signatureText",
    "responsibleParty"
})
public class COCTMT470000UVResponsibleParty {

    protected DSETCS realmCode;
    protected II typeId;
    protected LISTII templateId;
    protected ED noteText;
    protected TS time;
    protected CD modeCode;
    protected CS signatureCode;
    protected ED signatureText;
    @XmlElement(required = true, nillable = true)
    protected COCTMT040200UV09ResponsibleParty responsibleParty;
    @XmlAttribute(name = "nullFlavor")
    protected NullFlavor nullFlavor;
    @XmlAttribute(name = "typeCode", required = true)
    protected ParticipationResponsibleParty typeCode;

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
     * Gets the value of the noteText property.
     * 
     * @return
     *     possible object is
     *     {@link ED }
     *     
     */
    public ED getNoteText() {
        return noteText;
    }

    /**
     * Sets the value of the noteText property.
     * 
     * @param value
     *     allowed object is
     *     {@link ED }
     *     
     */
    public void setNoteText(ED value) {
        this.noteText = value;
    }

    /**
     * Gets the value of the time property.
     * 
     * @return
     *     possible object is
     *     {@link TS }
     *     
     */
    public TS getTime() {
        return time;
    }

    /**
     * Sets the value of the time property.
     * 
     * @param value
     *     allowed object is
     *     {@link TS }
     *     
     */
    public void setTime(TS value) {
        this.time = value;
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
     * Gets the value of the signatureCode property.
     * 
     * @return
     *     possible object is
     *     {@link CS }
     *     
     */
    public CS getSignatureCode() {
        return signatureCode;
    }

    /**
     * Sets the value of the signatureCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link CS }
     *     
     */
    public void setSignatureCode(CS value) {
        this.signatureCode = value;
    }

    /**
     * Gets the value of the signatureText property.
     * 
     * @return
     *     possible object is
     *     {@link ED }
     *     
     */
    public ED getSignatureText() {
        return signatureText;
    }

    /**
     * Sets the value of the signatureText property.
     * 
     * @param value
     *     allowed object is
     *     {@link ED }
     *     
     */
    public void setSignatureText(ED value) {
        this.signatureText = value;
    }

    /**
     * Gets the value of the responsibleParty property.
     * 
     * @return
     *     possible object is
     *     {@link COCTMT040200UV09ResponsibleParty }
     *     
     */
    public COCTMT040200UV09ResponsibleParty getResponsibleParty() {
        return responsibleParty;
    }

    /**
     * Sets the value of the responsibleParty property.
     * 
     * @param value
     *     allowed object is
     *     {@link COCTMT040200UV09ResponsibleParty }
     *     
     */
    public void setResponsibleParty(COCTMT040200UV09ResponsibleParty value) {
        this.responsibleParty = value;
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

    /**
     * Gets the value of the typeCode property.
     * 
     * @return
     *     possible object is
     *     {@link ParticipationResponsibleParty }
     *     
     */
    public ParticipationResponsibleParty getTypeCode() {
        return typeCode;
    }

    /**
     * Sets the value of the typeCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link ParticipationResponsibleParty }
     *     
     */
    public void setTypeCode(ParticipationResponsibleParty value) {
        this.typeCode = value;
    }

}
