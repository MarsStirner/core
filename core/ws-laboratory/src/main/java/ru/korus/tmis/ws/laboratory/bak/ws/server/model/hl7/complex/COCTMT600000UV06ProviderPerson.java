
package ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for COCT_MT600000UV06.ProviderPerson complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="COCT_MT600000UV06.ProviderPerson">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;group ref="{urn:hl7-org:v3}InfrastructureRootElements"/>
 *         &lt;element name="name" type="{urn:hl7-org:v3}EN" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{urn:hl7-org:v3}InfrastructureRootAttributes"/>
 *       &lt;attribute name="nullFlavor" type="{urn:hl7-org:v3}NullFlavor" />
 *       &lt;attribute name="classCode" use="required" type="{urn:hl7-org:v3}EntityClassPerson" />
 *       &lt;attribute name="determinerCode" use="required" type="{urn:hl7-org:v3}EntityDeterminerSpecific" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "COCT_MT600000UV06.ProviderPerson", propOrder = {
    "realmCode",
    "typeId",
    "templateId",
    "name"
})
public class COCTMT600000UV06ProviderPerson {

    protected DSETCS realmCode;
    protected II typeId;
    protected LISTII templateId;
    protected EN name;
    @XmlAttribute(name = "nullFlavor")
    protected NullFlavor nullFlavor;
    @XmlAttribute(name = "classCode", required = true)
    protected EntityClassPerson classCode;
    @XmlAttribute(name = "determinerCode", required = true)
    protected EntityDeterminerSpecific determinerCode;

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
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link EN }
     *     
     */
    public EN getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link EN }
     *     
     */
    public void setName(EN value) {
        this.name = value;
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
     *     {@link EntityClassPerson }
     *     
     */
    public EntityClassPerson getClassCode() {
        return classCode;
    }

    /**
     * Sets the value of the classCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link EntityClassPerson }
     *     
     */
    public void setClassCode(EntityClassPerson value) {
        this.classCode = value;
    }

    /**
     * Gets the value of the determinerCode property.
     * 
     * @return
     *     possible object is
     *     {@link EntityDeterminerSpecific }
     *     
     */
    public EntityDeterminerSpecific getDeterminerCode() {
        return determinerCode;
    }

    /**
     * Sets the value of the determinerCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link EntityDeterminerSpecific }
     *     
     */
    public void setDeterminerCode(EntityDeterminerSpecific value) {
        this.determinerCode = value;
    }

}
