
package ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for COCT_MT080000UV09.Consumable complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="COCT_MT080000UV09.Consumable">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;group ref="{urn:hl7-org:v3}InfrastructureRootElements"/>
 *         &lt;element name="sequenceNumber" type="{urn:hl7-org:v3}INT" minOccurs="0"/>
 *         &lt;choice>
 *           &lt;element name="labTestKit" type="{urn:hl7-org:v3}COCT_MT430000UV09.LabTestKit"/>
 *           &lt;element name="reagent" type="{urn:hl7-org:v3}COCT_MT250000UV03.Reagent"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{urn:hl7-org:v3}InfrastructureRootAttributes"/>
 *       &lt;attribute name="nullFlavor" type="{urn:hl7-org:v3}NullFlavor" />
 *       &lt;attribute name="typeCode" use="required" type="{urn:hl7-org:v3}ParticipationConsumable" />
 *       &lt;attribute name="contextControlCode" type="{urn:hl7-org:v3}ContextControl" default="ON" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "COCT_MT080000UV09.Consumable", propOrder = {
    "realmCode",
    "typeId",
    "templateId",
    "sequenceNumber",
    "labTestKit",
    "reagent"
})
public class COCTMT080000UV09Consumable {

    protected DSETCS realmCode;
    protected II typeId;
    protected LISTII templateId;
    protected INT sequenceNumber;
    @XmlElementRef(name = "labTestKit", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT430000UV09LabTestKit> labTestKit;
    @XmlElementRef(name = "reagent", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT250000UV03Reagent> reagent;
    @XmlAttribute(name = "nullFlavor")
    protected NullFlavor nullFlavor;
    @XmlAttribute(name = "typeCode", required = true)
    protected ParticipationConsumable typeCode;
    @XmlAttribute(name = "contextControlCode")
    protected ContextControl contextControlCode;

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
     * Gets the value of the sequenceNumber property.
     * 
     * @return
     *     possible object is
     *     {@link INT }
     *     
     */
    public INT getSequenceNumber() {
        return sequenceNumber;
    }

    /**
     * Sets the value of the sequenceNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link INT }
     *     
     */
    public void setSequenceNumber(INT value) {
        this.sequenceNumber = value;
    }

    /**
     * Gets the value of the labTestKit property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT430000UV09LabTestKit }{@code >}
     *     
     */
    public JAXBElement<COCTMT430000UV09LabTestKit> getLabTestKit() {
        return labTestKit;
    }

    /**
     * Sets the value of the labTestKit property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT430000UV09LabTestKit }{@code >}
     *     
     */
    public void setLabTestKit(JAXBElement<COCTMT430000UV09LabTestKit> value) {
        this.labTestKit = value;
    }

    /**
     * Gets the value of the reagent property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT250000UV03Reagent }{@code >}
     *     
     */
    public JAXBElement<COCTMT250000UV03Reagent> getReagent() {
        return reagent;
    }

    /**
     * Sets the value of the reagent property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT250000UV03Reagent }{@code >}
     *     
     */
    public void setReagent(JAXBElement<COCTMT250000UV03Reagent> value) {
        this.reagent = value;
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
     *     {@link ParticipationConsumable }
     *     
     */
    public ParticipationConsumable getTypeCode() {
        return typeCode;
    }

    /**
     * Sets the value of the typeCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link ParticipationConsumable }
     *     
     */
    public void setTypeCode(ParticipationConsumable value) {
        this.typeCode = value;
    }

    /**
     * Gets the value of the contextControlCode property.
     * 
     * @return
     *     possible object is
     *     {@link ContextControl }
     *     
     */
    public ContextControl getContextControlCode() {
        if (contextControlCode == null) {
            return ContextControl.ON;
        } else {
            return contextControlCode;
        }
    }

    /**
     * Sets the value of the contextControlCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContextControl }
     *     
     */
    public void setContextControlCode(ContextControl value) {
        this.contextControlCode = value;
    }

}
