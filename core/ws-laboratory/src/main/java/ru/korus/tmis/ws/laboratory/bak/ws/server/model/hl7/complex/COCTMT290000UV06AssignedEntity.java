
package ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for COCT_MT290000UV06.AssignedEntity complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="COCT_MT290000UV06.AssignedEntity">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;group ref="{urn:hl7-org:v3}InfrastructureRootElements"/>
 *         &lt;element name="id" type="{urn:hl7-org:v3}DSET_II" minOccurs="0"/>
 *         &lt;element name="code" type="{urn:hl7-org:v3}CD" minOccurs="0"/>
 *         &lt;choice>
 *           &lt;element name="assignedProviderPerson" type="{urn:hl7-org:v3}COCT_MT290000UV06.ProviderPerson" minOccurs="0"/>
 *           &lt;element name="assignedNonPersonLivingSubject" type="{urn:hl7-org:v3}COCT_MT290000UV06.NonPersonLivingSubject" minOccurs="0"/>
 *           &lt;element name="assignedDevice" type="{urn:hl7-org:v3}COCT_MT290000UV06.Device2" minOccurs="0"/>
 *         &lt;/choice>
 *         &lt;element name="indirectAuthority" type="{urn:hl7-org:v3}COCT_MT290000UV06.IndirectAuthorithyOver" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{urn:hl7-org:v3}InfrastructureRootAttributes"/>
 *       &lt;attribute name="nullFlavor" type="{urn:hl7-org:v3}NullFlavor" />
 *       &lt;attribute name="classCode" use="required" type="{urn:hl7-org:v3}RoleClassAssignedEntity" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "COCT_MT290000UV06.AssignedEntity", propOrder = {
    "realmCode",
    "typeId",
    "templateId",
    "id",
    "code",
    "assignedProviderPerson",
    "assignedNonPersonLivingSubject",
    "assignedDevice",
    "indirectAuthority"
})
public class COCTMT290000UV06AssignedEntity {

    protected DSETCS realmCode;
    protected II typeId;
    protected LISTII templateId;
    protected DSETII id;
    protected CD code;
    @XmlElementRef(name = "assignedProviderPerson", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT290000UV06ProviderPerson> assignedProviderPerson;
    @XmlElementRef(name = "assignedNonPersonLivingSubject", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT290000UV06NonPersonLivingSubject> assignedNonPersonLivingSubject;
    @XmlElementRef(name = "assignedDevice", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT290000UV06Device2> assignedDevice;
    @XmlElement(nillable = true)
    protected List<COCTMT290000UV06IndirectAuthorithyOver> indirectAuthority;
    @XmlAttribute(name = "nullFlavor")
    protected NullFlavor nullFlavor;
    @XmlAttribute(name = "classCode", required = true)
    protected RoleClassAssignedEntity classCode;

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
     * Gets the value of the assignedProviderPerson property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT290000UV06ProviderPerson }{@code >}
     *     
     */
    public JAXBElement<COCTMT290000UV06ProviderPerson> getAssignedProviderPerson() {
        return assignedProviderPerson;
    }

    /**
     * Sets the value of the assignedProviderPerson property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT290000UV06ProviderPerson }{@code >}
     *     
     */
    public void setAssignedProviderPerson(JAXBElement<COCTMT290000UV06ProviderPerson> value) {
        this.assignedProviderPerson = value;
    }

    /**
     * Gets the value of the assignedNonPersonLivingSubject property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT290000UV06NonPersonLivingSubject }{@code >}
     *     
     */
    public JAXBElement<COCTMT290000UV06NonPersonLivingSubject> getAssignedNonPersonLivingSubject() {
        return assignedNonPersonLivingSubject;
    }

    /**
     * Sets the value of the assignedNonPersonLivingSubject property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT290000UV06NonPersonLivingSubject }{@code >}
     *     
     */
    public void setAssignedNonPersonLivingSubject(JAXBElement<COCTMT290000UV06NonPersonLivingSubject> value) {
        this.assignedNonPersonLivingSubject = value;
    }

    /**
     * Gets the value of the assignedDevice property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT290000UV06Device2 }{@code >}
     *     
     */
    public JAXBElement<COCTMT290000UV06Device2> getAssignedDevice() {
        return assignedDevice;
    }

    /**
     * Sets the value of the assignedDevice property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT290000UV06Device2 }{@code >}
     *     
     */
    public void setAssignedDevice(JAXBElement<COCTMT290000UV06Device2> value) {
        this.assignedDevice = value;
    }

    /**
     * Gets the value of the indirectAuthority property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the indirectAuthority property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIndirectAuthority().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link COCTMT290000UV06IndirectAuthorithyOver }
     * 
     * 
     */
    public List<COCTMT290000UV06IndirectAuthorithyOver> getIndirectAuthority() {
        if (indirectAuthority == null) {
            indirectAuthority = new ArrayList<COCTMT290000UV06IndirectAuthorithyOver>();
        }
        return this.indirectAuthority;
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
     *     {@link RoleClassAssignedEntity }
     *     
     */
    public RoleClassAssignedEntity getClassCode() {
        return classCode;
    }

    /**
     * Sets the value of the classCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link RoleClassAssignedEntity }
     *     
     */
    public void setClassCode(RoleClassAssignedEntity value) {
        this.classCode = value;
    }

}
