
package ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for COCT_MT040200UV09.ResponsibleParty complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="COCT_MT040200UV09.ResponsibleParty">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;group ref="{urn:hl7-org:v3}InfrastructureRootElements"/>
 *         &lt;element name="id" type="{urn:hl7-org:v3}DSET_II" minOccurs="0"/>
 *         &lt;element name="code" type="{urn:hl7-org:v3}CD" minOccurs="0"/>
 *         &lt;element name="addr" type="{urn:hl7-org:v3}COLL_AD" minOccurs="0"/>
 *         &lt;element name="telecom" type="{urn:hl7-org:v3}COLL_TEL" minOccurs="0"/>
 *         &lt;element name="statusCode" type="{urn:hl7-org:v3}CS" minOccurs="0"/>
 *         &lt;element name="effectiveTime" type="{urn:hl7-org:v3}IVL_TS" minOccurs="0"/>
 *         &lt;choice>
 *           &lt;element name="agentOrganization" type="{urn:hl7-org:v3}COCT_MT150000UV02.Organization"/>
 *           &lt;element name="agentPerson" type="{urn:hl7-org:v3}COCT_MT030200UV09.Person"/>
 *         &lt;/choice>
 *         &lt;choice>
 *           &lt;element name="representedPerson" type="{urn:hl7-org:v3}COCT_MT030200UV09.Person" minOccurs="0"/>
 *           &lt;element name="representedOrganization" type="{urn:hl7-org:v3}COCT_MT150000UV02.Organization" minOccurs="0"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{urn:hl7-org:v3}InfrastructureRootAttributes"/>
 *       &lt;attribute name="classCode" use="required" type="{urn:hl7-org:v3}RoleClassAgent" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "COCT_MT040200UV09.ResponsibleParty", propOrder = {
    "realmCode",
    "typeId",
    "templateId",
    "id",
    "code",
    "addr",
    "telecom",
    "statusCode",
    "effectiveTime",
    "agentOrganization",
    "agentPerson",
    "representedPerson",
    "representedOrganization"
})
public class COCTMT040200UV09ResponsibleParty {

    protected DSETCS realmCode;
    protected II typeId;
    protected LISTII templateId;
    protected DSETII id;
    protected CD code;
    protected COLLAD addr;
    protected COLLTEL telecom;
    protected CS statusCode;
    protected IVLTS effectiveTime;
    @XmlElementRef(name = "agentOrganization", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT150000UV02Organization> agentOrganization;
    @XmlElementRef(name = "agentPerson", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT030200UV09Person> agentPerson;
    @XmlElementRef(name = "representedPerson", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT030200UV09Person> representedPerson;
    @XmlElementRef(name = "representedOrganization", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT150000UV02Organization> representedOrganization;
    @XmlAttribute(name = "classCode", required = true)
    protected RoleClassAgent classCode;

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
     *     {@link COLLAD }
     *     
     */
    public COLLAD getAddr() {
        return addr;
    }

    /**
     * Sets the value of the addr property.
     * 
     * @param value
     *     allowed object is
     *     {@link COLLAD }
     *     
     */
    public void setAddr(COLLAD value) {
        this.addr = value;
    }

    /**
     * Gets the value of the telecom property.
     * 
     * @return
     *     possible object is
     *     {@link COLLTEL }
     *     
     */
    public COLLTEL getTelecom() {
        return telecom;
    }

    /**
     * Sets the value of the telecom property.
     * 
     * @param value
     *     allowed object is
     *     {@link COLLTEL }
     *     
     */
    public void setTelecom(COLLTEL value) {
        this.telecom = value;
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
     * Gets the value of the agentOrganization property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT150000UV02Organization }{@code >}
     *     
     */
    public JAXBElement<COCTMT150000UV02Organization> getAgentOrganization() {
        return agentOrganization;
    }

    /**
     * Sets the value of the agentOrganization property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT150000UV02Organization }{@code >}
     *     
     */
    public void setAgentOrganization(JAXBElement<COCTMT150000UV02Organization> value) {
        this.agentOrganization = value;
    }

    /**
     * Gets the value of the agentPerson property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT030200UV09Person }{@code >}
     *     
     */
    public JAXBElement<COCTMT030200UV09Person> getAgentPerson() {
        return agentPerson;
    }

    /**
     * Sets the value of the agentPerson property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT030200UV09Person }{@code >}
     *     
     */
    public void setAgentPerson(JAXBElement<COCTMT030200UV09Person> value) {
        this.agentPerson = value;
    }

    /**
     * Gets the value of the representedPerson property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT030200UV09Person }{@code >}
     *     
     */
    public JAXBElement<COCTMT030200UV09Person> getRepresentedPerson() {
        return representedPerson;
    }

    /**
     * Sets the value of the representedPerson property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT030200UV09Person }{@code >}
     *     
     */
    public void setRepresentedPerson(JAXBElement<COCTMT030200UV09Person> value) {
        this.representedPerson = value;
    }

    /**
     * Gets the value of the representedOrganization property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT150000UV02Organization }{@code >}
     *     
     */
    public JAXBElement<COCTMT150000UV02Organization> getRepresentedOrganization() {
        return representedOrganization;
    }

    /**
     * Sets the value of the representedOrganization property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT150000UV02Organization }{@code >}
     *     
     */
    public void setRepresentedOrganization(JAXBElement<COCTMT150000UV02Organization> value) {
        this.representedOrganization = value;
    }

    /**
     * Gets the value of the classCode property.
     * 
     * @return
     *     possible object is
     *     {@link RoleClassAgent }
     *     
     */
    public RoleClassAgent getClassCode() {
        return classCode;
    }

    /**
     * Sets the value of the classCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link RoleClassAgent }
     *     
     */
    public void setClassCode(RoleClassAgent value) {
        this.classCode = value;
    }

}
