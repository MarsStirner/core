
package ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for COCT_MT310000UV04.AccommodationRequestorRole complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="COCT_MT310000UV04.AccommodationRequestorRole">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;group ref="{urn:hl7-org:v3}InfrastructureRootElements"/>
 *         &lt;element name="code" type="{urn:hl7-org:v3}CS" minOccurs="0"/>
 *         &lt;element name="accommodationRequestor" type="{urn:hl7-org:v3}COCT_MT310000UV04.AcommodationRequestor" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{urn:hl7-org:v3}InfrastructureRootAttributes"/>
 *       &lt;attribute name="classCode" use="required" type="{urn:hl7-org:v3}x_RoleClassAccommodationRequestor" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "COCT_MT310000UV04.AccommodationRequestorRole", propOrder = {
    "realmCode",
    "typeId",
    "templateId",
    "code",
    "accommodationRequestor"
})
public class COCTMT310000UV04AccommodationRequestorRole {

    protected DSETCS realmCode;
    protected II typeId;
    protected LISTII templateId;
    protected CS code;
    @XmlElementRef(name = "accommodationRequestor", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT310000UV04AcommodationRequestor> accommodationRequestor;
    @XmlAttribute(name = "classCode", required = true)
    protected XRoleClassAccommodationRequestor classCode;

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
     * Gets the value of the code property.
     * 
     * @return
     *     possible object is
     *     {@link CS }
     *     
     */
    public CS getCode() {
        return code;
    }

    /**
     * Sets the value of the code property.
     * 
     * @param value
     *     allowed object is
     *     {@link CS }
     *     
     */
    public void setCode(CS value) {
        this.code = value;
    }

    /**
     * Gets the value of the accommodationRequestor property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT310000UV04AcommodationRequestor }{@code >}
     *     
     */
    public JAXBElement<COCTMT310000UV04AcommodationRequestor> getAccommodationRequestor() {
        return accommodationRequestor;
    }

    /**
     * Sets the value of the accommodationRequestor property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT310000UV04AcommodationRequestor }{@code >}
     *     
     */
    public void setAccommodationRequestor(JAXBElement<COCTMT310000UV04AcommodationRequestor> value) {
        this.accommodationRequestor = value;
    }

    /**
     * Gets the value of the classCode property.
     * 
     * @return
     *     possible object is
     *     {@link XRoleClassAccommodationRequestor }
     *     
     */
    public XRoleClassAccommodationRequestor getClassCode() {
        return classCode;
    }

    /**
     * Sets the value of the classCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link XRoleClassAccommodationRequestor }
     *     
     */
    public void setClassCode(XRoleClassAccommodationRequestor value) {
        this.classCode = value;
    }

}
