
package ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for COCT_MT080000UV09.Product complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="COCT_MT080000UV09.Product">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;group ref="{urn:hl7-org:v3}InfrastructureRootElements"/>
 *         &lt;element name="time" type="{urn:hl7-org:v3}IVL_TS" minOccurs="0"/>
 *         &lt;choice>
 *           &lt;element name="specimenProcessStep" type="{urn:hl7-org:v3}COCT_MT080000UV09.SpecimenProcessStep"/>
 *           &lt;element name="specimenCollectionProcess" type="{urn:hl7-org:v3}COCT_MT080000UV09.SpecimenCollectionProcess"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{urn:hl7-org:v3}InfrastructureRootAttributes"/>
 *       &lt;attribute name="nullFlavor" type="{urn:hl7-org:v3}NullFlavor" />
 *       &lt;attribute name="typeCode" use="required" type="{urn:hl7-org:v3}ParticipationProduct" />
 *       &lt;attribute name="contextControlCode" type="{urn:hl7-org:v3}ContextControl" default="OP" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "COCT_MT080000UV09.Product", propOrder = {
    "realmCode",
    "typeId",
    "templateId",
    "time",
    "specimenProcessStep",
    "specimenCollectionProcess"
})
public class COCTMT080000UV09Product {

    protected DSETCS realmCode;
    protected II typeId;
    protected LISTII templateId;
    protected IVLTS time;
    @XmlElementRef(name = "specimenProcessStep", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT080000UV09SpecimenProcessStep> specimenProcessStep;
    @XmlElementRef(name = "specimenCollectionProcess", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT080000UV09SpecimenCollectionProcess> specimenCollectionProcess;
    @XmlAttribute(name = "nullFlavor")
    protected NullFlavor nullFlavor;
    @XmlAttribute(name = "typeCode", required = true)
    protected ParticipationProduct typeCode;
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
     * Gets the value of the time property.
     * 
     * @return
     *     possible object is
     *     {@link IVLTS }
     *     
     */
    public IVLTS getTime() {
        return time;
    }

    /**
     * Sets the value of the time property.
     * 
     * @param value
     *     allowed object is
     *     {@link IVLTS }
     *     
     */
    public void setTime(IVLTS value) {
        this.time = value;
    }

    /**
     * Gets the value of the specimenProcessStep property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT080000UV09SpecimenProcessStep }{@code >}
     *     
     */
    public JAXBElement<COCTMT080000UV09SpecimenProcessStep> getSpecimenProcessStep() {
        return specimenProcessStep;
    }

    /**
     * Sets the value of the specimenProcessStep property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT080000UV09SpecimenProcessStep }{@code >}
     *     
     */
    public void setSpecimenProcessStep(JAXBElement<COCTMT080000UV09SpecimenProcessStep> value) {
        this.specimenProcessStep = value;
    }

    /**
     * Gets the value of the specimenCollectionProcess property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT080000UV09SpecimenCollectionProcess }{@code >}
     *     
     */
    public JAXBElement<COCTMT080000UV09SpecimenCollectionProcess> getSpecimenCollectionProcess() {
        return specimenCollectionProcess;
    }

    /**
     * Sets the value of the specimenCollectionProcess property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT080000UV09SpecimenCollectionProcess }{@code >}
     *     
     */
    public void setSpecimenCollectionProcess(JAXBElement<COCTMT080000UV09SpecimenCollectionProcess> value) {
        this.specimenCollectionProcess = value;
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
     *     {@link ParticipationProduct }
     *     
     */
    public ParticipationProduct getTypeCode() {
        return typeCode;
    }

    /**
     * Sets the value of the typeCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link ParticipationProduct }
     *     
     */
    public void setTypeCode(ParticipationProduct value) {
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
            return ContextControl.OP;
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
