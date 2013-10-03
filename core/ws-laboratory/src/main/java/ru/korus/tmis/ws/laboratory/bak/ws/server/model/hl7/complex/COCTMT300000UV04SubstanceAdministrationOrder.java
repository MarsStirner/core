
package ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for COCT_MT300000UV04.SubstanceAdministrationOrder complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="COCT_MT300000UV04.SubstanceAdministrationOrder">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;group ref="{urn:hl7-org:v3}InfrastructureRootElements"/>
 *         &lt;element name="author" type="{urn:hl7-org:v3}COCT_MT300000UV04.Author"/>
 *         &lt;element name="reason" type="{urn:hl7-org:v3}COCT_MT300000UV04.Reason" minOccurs="0"/>
 *         &lt;element name="pertinentInformation" type="{urn:hl7-org:v3}COCT_MT300000UV04.PertinentInformation" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{urn:hl7-org:v3}InfrastructureRootAttributes"/>
 *       &lt;attribute name="nullFlavor" type="{urn:hl7-org:v3}NullFlavor" />
 *       &lt;attribute name="classCode" use="required" type="{urn:hl7-org:v3}ActClassSubstanceAdministration" />
 *       &lt;attribute name="moodCode" use="required" type="{urn:hl7-org:v3}ActMoodRequest" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "COCT_MT300000UV04.SubstanceAdministrationOrder", propOrder = {
    "realmCode",
    "typeId",
    "templateId",
    "author",
    "reason",
    "pertinentInformation"
})
public class COCTMT300000UV04SubstanceAdministrationOrder {

    protected DSETCS realmCode;
    protected II typeId;
    protected LISTII templateId;
    @XmlElement(required = true, nillable = true)
    protected COCTMT300000UV04Author author;
    @XmlElementRef(name = "reason", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT300000UV04Reason> reason;
    @XmlElementRef(name = "pertinentInformation", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT300000UV04PertinentInformation> pertinentInformation;
    @XmlAttribute(name = "nullFlavor")
    protected NullFlavor nullFlavor;
    @XmlAttribute(name = "classCode", required = true)
    protected ActClassSubstanceAdministration classCode;
    @XmlAttribute(name = "moodCode", required = true)
    protected ActMoodRequest moodCode;

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
     * Gets the value of the author property.
     * 
     * @return
     *     possible object is
     *     {@link COCTMT300000UV04Author }
     *     
     */
    public COCTMT300000UV04Author getAuthor() {
        return author;
    }

    /**
     * Sets the value of the author property.
     * 
     * @param value
     *     allowed object is
     *     {@link COCTMT300000UV04Author }
     *     
     */
    public void setAuthor(COCTMT300000UV04Author value) {
        this.author = value;
    }

    /**
     * Gets the value of the reason property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT300000UV04Reason }{@code >}
     *     
     */
    public JAXBElement<COCTMT300000UV04Reason> getReason() {
        return reason;
    }

    /**
     * Sets the value of the reason property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT300000UV04Reason }{@code >}
     *     
     */
    public void setReason(JAXBElement<COCTMT300000UV04Reason> value) {
        this.reason = value;
    }

    /**
     * Gets the value of the pertinentInformation property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT300000UV04PertinentInformation }{@code >}
     *     
     */
    public JAXBElement<COCTMT300000UV04PertinentInformation> getPertinentInformation() {
        return pertinentInformation;
    }

    /**
     * Sets the value of the pertinentInformation property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT300000UV04PertinentInformation }{@code >}
     *     
     */
    public void setPertinentInformation(JAXBElement<COCTMT300000UV04PertinentInformation> value) {
        this.pertinentInformation = value;
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
     * Gets the value of the classCode property.
     * 
     * @return
     *     possible object is
     *     {@link ActClassSubstanceAdministration }
     *     
     */
    public ActClassSubstanceAdministration getClassCode() {
        return classCode;
    }

    /**
     * Sets the value of the classCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link ActClassSubstanceAdministration }
     *     
     */
    public void setClassCode(ActClassSubstanceAdministration value) {
        this.classCode = value;
    }

    /**
     * Gets the value of the moodCode property.
     * 
     * @return
     *     possible object is
     *     {@link ActMoodRequest }
     *     
     */
    public ActMoodRequest getMoodCode() {
        return moodCode;
    }

    /**
     * Sets the value of the moodCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link ActMoodRequest }
     *     
     */
    public void setMoodCode(ActMoodRequest value) {
        this.moodCode = value;
    }

}
