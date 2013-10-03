
package ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for COCT_MT530000UV.RelatedEntity complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="COCT_MT530000UV.RelatedEntity">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;group ref="{urn:hl7-org:v3}InfrastructureRootElements"/>
 *         &lt;element name="id" type="{urn:hl7-org:v3}DSET_II" minOccurs="0"/>
 *         &lt;element name="code" type="{urn:hl7-org:v3}CD" minOccurs="0"/>
 *         &lt;element name="addr" type="{urn:hl7-org:v3}DSET_AD" minOccurs="0"/>
 *         &lt;element name="telecom" type="{urn:hl7-org:v3}DSET_TEL" minOccurs="0"/>
 *         &lt;choice>
 *           &lt;choice>
 *             &lt;element name="relatedPerson" type="{urn:hl7-org:v3}COCT_MT530000UV.Person" minOccurs="0"/>
 *             &lt;element name="relatedAnimal" type="{urn:hl7-org:v3}COCT_MT530000UV.Animal" minOccurs="0"/>
 *           &lt;/choice>
 *           &lt;element name="relatedEntity" type="{urn:hl7-org:v3}COCT_MT530000UV.Entity" minOccurs="0"/>
 *         &lt;/choice>
 *         &lt;choice>
 *           &lt;choice>
 *             &lt;element name="scopingPerson" type="{urn:hl7-org:v3}COCT_MT530000UV.Person" minOccurs="0"/>
 *             &lt;element name="scopingAnimal" type="{urn:hl7-org:v3}COCT_MT530000UV.Animal" minOccurs="0"/>
 *           &lt;/choice>
 *           &lt;element name="scopingEntity" type="{urn:hl7-org:v3}COCT_MT530000UV.Entity" minOccurs="0"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{urn:hl7-org:v3}InfrastructureRootAttributes"/>
 *       &lt;attribute name="nullFlavor" type="{urn:hl7-org:v3}NullFlavor" />
 *       &lt;attribute name="classCode" use="required" type="{urn:hl7-org:v3}RoleClassMutualRelationship" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "COCT_MT530000UV.RelatedEntity", propOrder = {
    "realmCode",
    "typeId",
    "templateId",
    "id",
    "code",
    "addr",
    "telecom",
    "relatedPerson",
    "relatedAnimal",
    "relatedEntity",
    "scopingPerson",
    "scopingAnimal",
    "scopingEntity"
})
public class COCTMT530000UVRelatedEntity {

    protected DSETCS realmCode;
    protected II typeId;
    protected LISTII templateId;
    protected DSETII id;
    protected CD code;
    protected DSETAD addr;
    protected DSETTEL telecom;
    @XmlElementRef(name = "relatedPerson", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT530000UVPerson> relatedPerson;
    @XmlElementRef(name = "relatedAnimal", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT530000UVAnimal> relatedAnimal;
    @XmlElementRef(name = "relatedEntity", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT530000UVEntity> relatedEntity;
    @XmlElementRef(name = "scopingPerson", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT530000UVPerson> scopingPerson;
    @XmlElementRef(name = "scopingAnimal", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT530000UVAnimal> scopingAnimal;
    @XmlElementRef(name = "scopingEntity", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT530000UVEntity> scopingEntity;
    @XmlAttribute(name = "nullFlavor")
    protected NullFlavor nullFlavor;
    @XmlAttribute(name = "classCode", required = true)
    protected RoleClassMutualRelationship classCode;

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
     *     {@link DSETII }
     *     
     */
    public DSETII getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link DSETII }
     *     
     */
    public void setId(DSETII value) {
        this.id = value;
    }

    /**
     * Gets the value of the code property.
     * 
     * @return
     *     possible object is
     *     {@link CD }
     *     
     */
    public CD getCode() {
        return code;
    }

    /**
     * Sets the value of the code property.
     * 
     * @param value
     *     allowed object is
     *     {@link CD }
     *     
     */
    public void setCode(CD value) {
        this.code = value;
    }

    /**
     * Gets the value of the addr property.
     * 
     * @return
     *     possible object is
     *     {@link DSETAD }
     *     
     */
    public DSETAD getAddr() {
        return addr;
    }

    /**
     * Sets the value of the addr property.
     * 
     * @param value
     *     allowed object is
     *     {@link DSETAD }
     *     
     */
    public void setAddr(DSETAD value) {
        this.addr = value;
    }

    /**
     * Gets the value of the telecom property.
     * 
     * @return
     *     possible object is
     *     {@link DSETTEL }
     *     
     */
    public DSETTEL getTelecom() {
        return telecom;
    }

    /**
     * Sets the value of the telecom property.
     * 
     * @param value
     *     allowed object is
     *     {@link DSETTEL }
     *     
     */
    public void setTelecom(DSETTEL value) {
        this.telecom = value;
    }

    /**
     * Gets the value of the relatedPerson property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT530000UVPerson }{@code >}
     *     
     */
    public JAXBElement<COCTMT530000UVPerson> getRelatedPerson() {
        return relatedPerson;
    }

    /**
     * Sets the value of the relatedPerson property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT530000UVPerson }{@code >}
     *     
     */
    public void setRelatedPerson(JAXBElement<COCTMT530000UVPerson> value) {
        this.relatedPerson = value;
    }

    /**
     * Gets the value of the relatedAnimal property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT530000UVAnimal }{@code >}
     *     
     */
    public JAXBElement<COCTMT530000UVAnimal> getRelatedAnimal() {
        return relatedAnimal;
    }

    /**
     * Sets the value of the relatedAnimal property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT530000UVAnimal }{@code >}
     *     
     */
    public void setRelatedAnimal(JAXBElement<COCTMT530000UVAnimal> value) {
        this.relatedAnimal = value;
    }

    /**
     * Gets the value of the relatedEntity property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT530000UVEntity }{@code >}
     *     
     */
    public JAXBElement<COCTMT530000UVEntity> getRelatedEntity() {
        return relatedEntity;
    }

    /**
     * Sets the value of the relatedEntity property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT530000UVEntity }{@code >}
     *     
     */
    public void setRelatedEntity(JAXBElement<COCTMT530000UVEntity> value) {
        this.relatedEntity = value;
    }

    /**
     * Gets the value of the scopingPerson property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT530000UVPerson }{@code >}
     *     
     */
    public JAXBElement<COCTMT530000UVPerson> getScopingPerson() {
        return scopingPerson;
    }

    /**
     * Sets the value of the scopingPerson property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT530000UVPerson }{@code >}
     *     
     */
    public void setScopingPerson(JAXBElement<COCTMT530000UVPerson> value) {
        this.scopingPerson = value;
    }

    /**
     * Gets the value of the scopingAnimal property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT530000UVAnimal }{@code >}
     *     
     */
    public JAXBElement<COCTMT530000UVAnimal> getScopingAnimal() {
        return scopingAnimal;
    }

    /**
     * Sets the value of the scopingAnimal property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT530000UVAnimal }{@code >}
     *     
     */
    public void setScopingAnimal(JAXBElement<COCTMT530000UVAnimal> value) {
        this.scopingAnimal = value;
    }

    /**
     * Gets the value of the scopingEntity property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT530000UVEntity }{@code >}
     *     
     */
    public JAXBElement<COCTMT530000UVEntity> getScopingEntity() {
        return scopingEntity;
    }

    /**
     * Sets the value of the scopingEntity property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT530000UVEntity }{@code >}
     *     
     */
    public void setScopingEntity(JAXBElement<COCTMT530000UVEntity> value) {
        this.scopingEntity = value;
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
     *     {@link RoleClassMutualRelationship }
     *     
     */
    public RoleClassMutualRelationship getClassCode() {
        return classCode;
    }

    /**
     * Sets the value of the classCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link RoleClassMutualRelationship }
     *     
     */
    public void setClassCode(RoleClassMutualRelationship value) {
        this.classCode = value;
    }

}
