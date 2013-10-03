
package ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for COCT_MT050004UV01.Subject complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="COCT_MT050004UV01.Subject">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;group ref="{urn:hl7-org:v3}InfrastructureRootElements"/>
 *         &lt;choice>
 *           &lt;element name="substanceAdministration" type="{urn:hl7-org:v3}COCT_MT200000UV01.SubstanceAdministration"/>
 *           &lt;element name="observationIntolerance" type="{urn:hl7-org:v3}COCT_MT120300UV.ObservationIntolerance"/>
 *           &lt;element name="procedure" type="{urn:hl7-org:v3}COCT_MT200000UV01.Procedure"/>
 *           &lt;element name="observationDx" type="{urn:hl7-org:v3}COCT_MT120100UV.ObservationDx"/>
 *           &lt;element name="observationGeneral" type="{urn:hl7-org:v3}COCT_MT120500UV.ObservationGeneral"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{urn:hl7-org:v3}InfrastructureRootAttributes"/>
 *       &lt;attribute name="nullFlavor" type="{urn:hl7-org:v3}NullFlavor" />
 *       &lt;attribute name="typeCode" use="required" type="{urn:hl7-org:v3}ParticipationTargetSubject" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "COCT_MT050004UV01.Subject", propOrder = {
    "realmCode",
    "typeId",
    "templateId",
    "substanceAdministration",
    "observationIntolerance",
    "procedure",
    "observationDx",
    "observationGeneral"
})
public class COCTMT050004UV01Subject {

    protected DSETCS realmCode;
    protected II typeId;
    protected LISTII templateId;
    @XmlElementRef(name = "substanceAdministration", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT200000UV01SubstanceAdministration> substanceAdministration;
    @XmlElementRef(name = "observationIntolerance", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT120300UVObservationIntolerance> observationIntolerance;
    @XmlElementRef(name = "procedure", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT200000UV01Procedure> procedure;
    @XmlElementRef(name = "observationDx", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT120100UVObservationDx> observationDx;
    @XmlElementRef(name = "observationGeneral", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT120500UVObservationGeneral> observationGeneral;
    @XmlAttribute(name = "nullFlavor")
    protected NullFlavor nullFlavor;
    @XmlAttribute(name = "typeCode", required = true)
    protected ParticipationTargetSubject typeCode;

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
     * Gets the value of the substanceAdministration property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT200000UV01SubstanceAdministration }{@code >}
     *     
     */
    public JAXBElement<COCTMT200000UV01SubstanceAdministration> getSubstanceAdministration() {
        return substanceAdministration;
    }

    /**
     * Sets the value of the substanceAdministration property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT200000UV01SubstanceAdministration }{@code >}
     *     
     */
    public void setSubstanceAdministration(JAXBElement<COCTMT200000UV01SubstanceAdministration> value) {
        this.substanceAdministration = value;
    }

    /**
     * Gets the value of the observationIntolerance property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT120300UVObservationIntolerance }{@code >}
     *     
     */
    public JAXBElement<COCTMT120300UVObservationIntolerance> getObservationIntolerance() {
        return observationIntolerance;
    }

    /**
     * Sets the value of the observationIntolerance property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT120300UVObservationIntolerance }{@code >}
     *     
     */
    public void setObservationIntolerance(JAXBElement<COCTMT120300UVObservationIntolerance> value) {
        this.observationIntolerance = value;
    }

    /**
     * Gets the value of the procedure property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT200000UV01Procedure }{@code >}
     *     
     */
    public JAXBElement<COCTMT200000UV01Procedure> getProcedure() {
        return procedure;
    }

    /**
     * Sets the value of the procedure property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT200000UV01Procedure }{@code >}
     *     
     */
    public void setProcedure(JAXBElement<COCTMT200000UV01Procedure> value) {
        this.procedure = value;
    }

    /**
     * Gets the value of the observationDx property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT120100UVObservationDx }{@code >}
     *     
     */
    public JAXBElement<COCTMT120100UVObservationDx> getObservationDx() {
        return observationDx;
    }

    /**
     * Sets the value of the observationDx property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT120100UVObservationDx }{@code >}
     *     
     */
    public void setObservationDx(JAXBElement<COCTMT120100UVObservationDx> value) {
        this.observationDx = value;
    }

    /**
     * Gets the value of the observationGeneral property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT120500UVObservationGeneral }{@code >}
     *     
     */
    public JAXBElement<COCTMT120500UVObservationGeneral> getObservationGeneral() {
        return observationGeneral;
    }

    /**
     * Sets the value of the observationGeneral property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT120500UVObservationGeneral }{@code >}
     *     
     */
    public void setObservationGeneral(JAXBElement<COCTMT120500UVObservationGeneral> value) {
        this.observationGeneral = value;
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
     *     {@link ParticipationTargetSubject }
     *     
     */
    public ParticipationTargetSubject getTypeCode() {
        return typeCode;
    }

    /**
     * Sets the value of the typeCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link ParticipationTargetSubject }
     *     
     */
    public void setTypeCode(ParticipationTargetSubject value) {
        this.typeCode = value;
    }

}
