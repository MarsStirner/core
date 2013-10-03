
package ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for COCT_MT840100UV09.ManagingEntity complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="COCT_MT840100UV09.ManagingEntity">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;group ref="{urn:hl7-org:v3}InfrastructureRootElements"/>
 *         &lt;element name="id" type="{urn:hl7-org:v3}II" minOccurs="0"/>
 *         &lt;element name="statusCode" type="{urn:hl7-org:v3}CS" minOccurs="0"/>
 *         &lt;element name="effectiveTime" type="{urn:hl7-org:v3}IVL_TS" minOccurs="0"/>
 *         &lt;choice>
 *           &lt;choice>
 *             &lt;element name="scoper1" type="{urn:hl7-org:v3}COCT_MT840000UV09.Person" minOccurs="0"/>
 *             &lt;element name="scoper2" type="{urn:hl7-org:v3}COCT_MT840000UV09.LivingSubject" minOccurs="0"/>
 *             &lt;element name="scoper3" type="{urn:hl7-org:v3}COCT_MT840000UV09.NonPersonLivingSubject" minOccurs="0"/>
 *           &lt;/choice>
 *           &lt;choice>
 *             &lt;element name="scoper5" type="{urn:hl7-org:v3}COCT_MT840000UV09.Place" minOccurs="0"/>
 *             &lt;choice>
 *               &lt;element name="scoper6" type="{urn:hl7-org:v3}COCT_MT840000UV09.Material" minOccurs="0"/>
 *               &lt;element name="scoper7" type="{urn:hl7-org:v3}COCT_MT840000UV09.ManufacturedMaterial" minOccurs="0"/>
 *             &lt;/choice>
 *           &lt;/choice>
 *           &lt;element name="scoper19" type="{urn:hl7-org:v3}COCT_MT840000UV09.Organization" minOccurs="0"/>
 *           &lt;element name="scoper20" type="{urn:hl7-org:v3}COCT_MT840000UV09.EntityGroup" minOccurs="0"/>
 *           &lt;element name="scoper21" type="{urn:hl7-org:v3}COCT_MT840000UV09.Entity" minOccurs="0"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{urn:hl7-org:v3}InfrastructureRootAttributes"/>
 *       &lt;attribute name="nullFlavor" type="{urn:hl7-org:v3}NullFlavor" />
 *       &lt;attribute name="classCode" use="required" type="{urn:hl7-org:v3}RoleClassManagedEntity" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "COCT_MT840100UV09.ManagingEntity", propOrder = {
    "realmCode",
    "typeId",
    "templateId",
    "id",
    "statusCode",
    "effectiveTime",
    "scoper1",
    "scoper2",
    "scoper3",
    "scoper5",
    "scoper6",
    "scoper7",
    "scoper19",
    "scoper20",
    "scoper21"
})
public class COCTMT840100UV09ManagingEntity {

    protected DSETCS realmCode;
    protected II typeId;
    protected LISTII templateId;
    protected II id;
    protected CS statusCode;
    protected IVLTS effectiveTime;
    @XmlElementRef(name = "scoper1", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT840000UV09Person> scoper1;
    @XmlElementRef(name = "scoper2", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT840000UV09LivingSubject> scoper2;
    @XmlElementRef(name = "scoper3", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT840000UV09NonPersonLivingSubject> scoper3;
    @XmlElementRef(name = "scoper5", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT840000UV09Place> scoper5;
    @XmlElementRef(name = "scoper6", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT840000UV09Material> scoper6;
    @XmlElementRef(name = "scoper7", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT840000UV09ManufacturedMaterial> scoper7;
    @XmlElementRef(name = "scoper19", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT840000UV09Organization> scoper19;
    @XmlElementRef(name = "scoper20", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT840000UV09EntityGroup> scoper20;
    @XmlElementRef(name = "scoper21", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT840000UV09Entity> scoper21;
    @XmlAttribute(name = "nullFlavor")
    protected NullFlavor nullFlavor;
    @XmlAttribute(name = "classCode", required = true)
    protected RoleClassManagedEntity classCode;

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
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link II }
     *     
     */
    public II getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link II }
     *     
     */
    public void setId(II value) {
        this.id = value;
    }

    /**
     * Gets the value of the statusCode property.
     * 
     * @return
     *     possible object is
     *     {@link CS }
     *     
     */
    public CS getStatusCode() {
        return statusCode;
    }

    /**
     * Sets the value of the statusCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link CS }
     *     
     */
    public void setStatusCode(CS value) {
        this.statusCode = value;
    }

    /**
     * Gets the value of the effectiveTime property.
     * 
     * @return
     *     possible object is
     *     {@link IVLTS }
     *     
     */
    public IVLTS getEffectiveTime() {
        return effectiveTime;
    }

    /**
     * Sets the value of the effectiveTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link IVLTS }
     *     
     */
    public void setEffectiveTime(IVLTS value) {
        this.effectiveTime = value;
    }

    /**
     * Gets the value of the scoper1 property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UV09Person }{@code >}
     *     
     */
    public JAXBElement<COCTMT840000UV09Person> getScoper1() {
        return scoper1;
    }

    /**
     * Sets the value of the scoper1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UV09Person }{@code >}
     *     
     */
    public void setScoper1(JAXBElement<COCTMT840000UV09Person> value) {
        this.scoper1 = value;
    }

    /**
     * Gets the value of the scoper2 property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UV09LivingSubject }{@code >}
     *     
     */
    public JAXBElement<COCTMT840000UV09LivingSubject> getScoper2() {
        return scoper2;
    }

    /**
     * Sets the value of the scoper2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UV09LivingSubject }{@code >}
     *     
     */
    public void setScoper2(JAXBElement<COCTMT840000UV09LivingSubject> value) {
        this.scoper2 = value;
    }

    /**
     * Gets the value of the scoper3 property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UV09NonPersonLivingSubject }{@code >}
     *     
     */
    public JAXBElement<COCTMT840000UV09NonPersonLivingSubject> getScoper3() {
        return scoper3;
    }

    /**
     * Sets the value of the scoper3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UV09NonPersonLivingSubject }{@code >}
     *     
     */
    public void setScoper3(JAXBElement<COCTMT840000UV09NonPersonLivingSubject> value) {
        this.scoper3 = value;
    }

    /**
     * Gets the value of the scoper5 property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UV09Place }{@code >}
     *     
     */
    public JAXBElement<COCTMT840000UV09Place> getScoper5() {
        return scoper5;
    }

    /**
     * Sets the value of the scoper5 property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UV09Place }{@code >}
     *     
     */
    public void setScoper5(JAXBElement<COCTMT840000UV09Place> value) {
        this.scoper5 = value;
    }

    /**
     * Gets the value of the scoper6 property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UV09Material }{@code >}
     *     
     */
    public JAXBElement<COCTMT840000UV09Material> getScoper6() {
        return scoper6;
    }

    /**
     * Sets the value of the scoper6 property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UV09Material }{@code >}
     *     
     */
    public void setScoper6(JAXBElement<COCTMT840000UV09Material> value) {
        this.scoper6 = value;
    }

    /**
     * Gets the value of the scoper7 property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UV09ManufacturedMaterial }{@code >}
     *     
     */
    public JAXBElement<COCTMT840000UV09ManufacturedMaterial> getScoper7() {
        return scoper7;
    }

    /**
     * Sets the value of the scoper7 property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UV09ManufacturedMaterial }{@code >}
     *     
     */
    public void setScoper7(JAXBElement<COCTMT840000UV09ManufacturedMaterial> value) {
        this.scoper7 = value;
    }

    /**
     * Gets the value of the scoper19 property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UV09Organization }{@code >}
     *     
     */
    public JAXBElement<COCTMT840000UV09Organization> getScoper19() {
        return scoper19;
    }

    /**
     * Sets the value of the scoper19 property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UV09Organization }{@code >}
     *     
     */
    public void setScoper19(JAXBElement<COCTMT840000UV09Organization> value) {
        this.scoper19 = value;
    }

    /**
     * Gets the value of the scoper20 property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UV09EntityGroup }{@code >}
     *     
     */
    public JAXBElement<COCTMT840000UV09EntityGroup> getScoper20() {
        return scoper20;
    }

    /**
     * Sets the value of the scoper20 property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UV09EntityGroup }{@code >}
     *     
     */
    public void setScoper20(JAXBElement<COCTMT840000UV09EntityGroup> value) {
        this.scoper20 = value;
    }

    /**
     * Gets the value of the scoper21 property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UV09Entity }{@code >}
     *     
     */
    public JAXBElement<COCTMT840000UV09Entity> getScoper21() {
        return scoper21;
    }

    /**
     * Sets the value of the scoper21 property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UV09Entity }{@code >}
     *     
     */
    public void setScoper21(JAXBElement<COCTMT840000UV09Entity> value) {
        this.scoper21 = value;
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
     *     {@link RoleClassManagedEntity }
     *     
     */
    public RoleClassManagedEntity getClassCode() {
        return classCode;
    }

    /**
     * Sets the value of the classCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link RoleClassManagedEntity }
     *     
     */
    public void setClassCode(RoleClassManagedEntity value) {
        this.classCode = value;
    }

}
