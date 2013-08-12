
package ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for COCT_MT841500UV.ManagedEntity complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="COCT_MT841500UV.ManagedEntity">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;group ref="{urn:hl7-org:v3}InfrastructureRootElements"/>
 *         &lt;element name="id" type="{urn:hl7-org:v3}II" minOccurs="0"/>
 *         &lt;element name="statusCode" type="{urn:hl7-org:v3}CS" minOccurs="0"/>
 *         &lt;element name="effectiveTime" type="{urn:hl7-org:v3}IVL_TS" minOccurs="0"/>
 *         &lt;choice>
 *           &lt;choice>
 *             &lt;element name="player1" type="{urn:hl7-org:v3}COCT_MT841500UV.Person" minOccurs="0"/>
 *             &lt;element name="player2" type="{urn:hl7-org:v3}COCT_MT841500UV.LivingSubject" minOccurs="0"/>
 *             &lt;element name="player3" type="{urn:hl7-org:v3}COCT_MT841500UV.NonPersonLivingSubject" minOccurs="0"/>
 *           &lt;/choice>
 *           &lt;choice>
 *             &lt;element name="player5" type="{urn:hl7-org:v3}COCT_MT841500UV.Place" minOccurs="0"/>
 *             &lt;choice>
 *               &lt;element name="player6" type="{urn:hl7-org:v3}COCT_MT841500UV.Material" minOccurs="0"/>
 *               &lt;element name="player7" type="{urn:hl7-org:v3}COCT_MT841500UV.ManufacturedMaterial" minOccurs="0"/>
 *             &lt;/choice>
 *           &lt;/choice>
 *           &lt;element name="player19" type="{urn:hl7-org:v3}COCT_MT841500UV.Entity" minOccurs="0"/>
 *           &lt;element name="player20" type="{urn:hl7-org:v3}COCT_MT841500UV.EntityGroup" minOccurs="0"/>
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
@XmlType(name = "COCT_MT841500UV.ManagedEntity", propOrder = {
    "realmCode",
    "typeId",
    "templateId",
    "id",
    "statusCode",
    "effectiveTime",
    "player1",
    "player2",
    "player3",
    "player5",
    "player6",
    "player7",
    "player19",
    "player20"
})
public class COCTMT841500UVManagedEntity {

    protected List<CS> realmCode;
    protected II typeId;
    protected List<II> templateId;
    protected II id;
    protected CS statusCode;
    protected IVLTS effectiveTime;
    @XmlElementRef(name = "player1", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT841500UVPerson> player1;
    @XmlElementRef(name = "player2", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT841500UVLivingSubject> player2;
    @XmlElementRef(name = "player3", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT841500UVNonPersonLivingSubject> player3;
    @XmlElementRef(name = "player5", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT841500UVPlace> player5;
    @XmlElementRef(name = "player6", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT841500UVMaterial> player6;
    @XmlElementRef(name = "player7", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT841500UVManufacturedMaterial> player7;
    @XmlElementRef(name = "player19", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT841500UVEntity> player19;
    @XmlElementRef(name = "player20", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT841500UVEntityGroup> player20;
    @XmlAttribute(name = "nullFlavor")
    protected List<String> nullFlavor;
    @XmlAttribute(name = "classCode", required = true)
    protected RoleClassManagedEntity classCode;

    /**
     * Gets the value of the realmCode property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the realmCode property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRealmCode().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CS }
     * 
     * 
     */
    public List<CS> getRealmCode() {
        if (realmCode == null) {
            realmCode = new ArrayList<CS>();
        }
        return this.realmCode;
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
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the templateId property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTemplateId().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link II }
     * 
     * 
     */
    public List<II> getTemplateId() {
        if (templateId == null) {
            templateId = new ArrayList<II>();
        }
        return this.templateId;
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
     * Gets the value of the player1 property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT841500UVPerson }{@code >}
     *     
     */
    public JAXBElement<COCTMT841500UVPerson> getPlayer1() {
        return player1;
    }

    /**
     * Sets the value of the player1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT841500UVPerson }{@code >}
     *     
     */
    public void setPlayer1(JAXBElement<COCTMT841500UVPerson> value) {
        this.player1 = value;
    }

    /**
     * Gets the value of the player2 property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT841500UVLivingSubject }{@code >}
     *     
     */
    public JAXBElement<COCTMT841500UVLivingSubject> getPlayer2() {
        return player2;
    }

    /**
     * Sets the value of the player2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT841500UVLivingSubject }{@code >}
     *     
     */
    public void setPlayer2(JAXBElement<COCTMT841500UVLivingSubject> value) {
        this.player2 = value;
    }

    /**
     * Gets the value of the player3 property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT841500UVNonPersonLivingSubject }{@code >}
     *     
     */
    public JAXBElement<COCTMT841500UVNonPersonLivingSubject> getPlayer3() {
        return player3;
    }

    /**
     * Sets the value of the player3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT841500UVNonPersonLivingSubject }{@code >}
     *     
     */
    public void setPlayer3(JAXBElement<COCTMT841500UVNonPersonLivingSubject> value) {
        this.player3 = value;
    }

    /**
     * Gets the value of the player5 property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT841500UVPlace }{@code >}
     *     
     */
    public JAXBElement<COCTMT841500UVPlace> getPlayer5() {
        return player5;
    }

    /**
     * Sets the value of the player5 property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT841500UVPlace }{@code >}
     *     
     */
    public void setPlayer5(JAXBElement<COCTMT841500UVPlace> value) {
        this.player5 = value;
    }

    /**
     * Gets the value of the player6 property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT841500UVMaterial }{@code >}
     *     
     */
    public JAXBElement<COCTMT841500UVMaterial> getPlayer6() {
        return player6;
    }

    /**
     * Sets the value of the player6 property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT841500UVMaterial }{@code >}
     *     
     */
    public void setPlayer6(JAXBElement<COCTMT841500UVMaterial> value) {
        this.player6 = value;
    }

    /**
     * Gets the value of the player7 property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT841500UVManufacturedMaterial }{@code >}
     *     
     */
    public JAXBElement<COCTMT841500UVManufacturedMaterial> getPlayer7() {
        return player7;
    }

    /**
     * Sets the value of the player7 property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT841500UVManufacturedMaterial }{@code >}
     *     
     */
    public void setPlayer7(JAXBElement<COCTMT841500UVManufacturedMaterial> value) {
        this.player7 = value;
    }

    /**
     * Gets the value of the player19 property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT841500UVEntity }{@code >}
     *     
     */
    public JAXBElement<COCTMT841500UVEntity> getPlayer19() {
        return player19;
    }

    /**
     * Sets the value of the player19 property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT841500UVEntity }{@code >}
     *     
     */
    public void setPlayer19(JAXBElement<COCTMT841500UVEntity> value) {
        this.player19 = value;
    }

    /**
     * Gets the value of the player20 property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT841500UVEntityGroup }{@code >}
     *     
     */
    public JAXBElement<COCTMT841500UVEntityGroup> getPlayer20() {
        return player20;
    }

    /**
     * Sets the value of the player20 property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT841500UVEntityGroup }{@code >}
     *     
     */
    public void setPlayer20(JAXBElement<COCTMT841500UVEntityGroup> value) {
        this.player20 = value;
    }

    /**
     * Gets the value of the nullFlavor property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the nullFlavor property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNullFlavor().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getNullFlavor() {
        if (nullFlavor == null) {
            nullFlavor = new ArrayList<String>();
        }
        return this.nullFlavor;
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
