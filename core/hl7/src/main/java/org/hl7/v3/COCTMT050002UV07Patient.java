
package org.hl7.v3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for COCT_MT050002UV07.Patient complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="COCT_MT050002UV07.Patient">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="realmCode" type="{urn:hl7-org:v3}CS" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="typeId" type="{urn:hl7-org:v3}II" minOccurs="0"/>
 *         &lt;element name="templateId" type="{urn:hl7-org:v3}II" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="id" type="{urn:hl7-org:v3}CS" maxOccurs="unbounded"/>
 *         &lt;element name="addr" type="{urn:hl7-org:v3}CS" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="patientPerson" type="{urn:hl7-org:v3}COCT_MT050002UV07.Person" minOccurs="0"/>
 *         &lt;element name="patientNonPersonLivingSubject" type="{urn:hl7-org:v3}COCT_MT050002UV07.NonPersonLivingSubject" minOccurs="0"/>
 *         &lt;element name="providerOrganization" type="{urn:hl7-org:v3}COCT_MT150003UV03.Organization" minOccurs="0"/>
 *       &lt;/choice>
 *       &lt;attribute name="classCode" use="required" type="{urn:hl7-org:v3}RoleClassPatient" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "COCT_MT050002UV07.Patient", propOrder = {
    "realmCode",
    "typeId",
    "templateId",
    "id",
    "addr",
    "patientPerson",
    "patientNonPersonLivingSubject",
    "providerOrganization"
})
public class COCTMT050002UV07Patient {

    protected List<CS> realmCode;
    protected II typeId;
    protected List<II> templateId;
    protected List<CS> id;
    protected List<CS> addr;
    @XmlElementRef(name = "patientPerson", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT050002UV07Person> patientPerson;
    @XmlElementRef(name = "patientNonPersonLivingSubject", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT050002UV07NonPersonLivingSubject> patientNonPersonLivingSubject;
    @XmlElementRef(name = "providerOrganization", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT150003UV03Organization> providerOrganization;
    @XmlAttribute(name = "classCode", required = true)
    protected RoleClassPatient classCode;

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
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the id property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getId().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CS }
     * 
     * 
     */
    public List<CS> getId() {
        if (id == null) {
            id = new ArrayList<CS>();
        }
        return this.id;
    }

    /**
     * Gets the value of the addr property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the addr property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAddr().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CS }
     * 
     * 
     */
    public List<CS> getAddr() {
        if (addr == null) {
            addr = new ArrayList<CS>();
        }
        return this.addr;
    }

    /**
     * Gets the value of the patientPerson property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT050002UV07Person }{@code >}
     *     
     */
    public JAXBElement<COCTMT050002UV07Person> getPatientPerson() {
        return patientPerson;
    }

    /**
     * Sets the value of the patientPerson property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT050002UV07Person }{@code >}
     *     
     */
    public void setPatientPerson(JAXBElement<COCTMT050002UV07Person> value) {
        this.patientPerson = value;
    }

    /**
     * Gets the value of the patientNonPersonLivingSubject property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT050002UV07NonPersonLivingSubject }{@code >}
     *     
     */
    public JAXBElement<COCTMT050002UV07NonPersonLivingSubject> getPatientNonPersonLivingSubject() {
        return patientNonPersonLivingSubject;
    }

    /**
     * Sets the value of the patientNonPersonLivingSubject property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT050002UV07NonPersonLivingSubject }{@code >}
     *     
     */
    public void setPatientNonPersonLivingSubject(JAXBElement<COCTMT050002UV07NonPersonLivingSubject> value) {
        this.patientNonPersonLivingSubject = value;
    }

    /**
     * Gets the value of the providerOrganization property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT150003UV03Organization }{@code >}
     *     
     */
    public JAXBElement<COCTMT150003UV03Organization> getProviderOrganization() {
        return providerOrganization;
    }

    /**
     * Sets the value of the providerOrganization property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT150003UV03Organization }{@code >}
     *     
     */
    public void setProviderOrganization(JAXBElement<COCTMT150003UV03Organization> value) {
        this.providerOrganization = value;
    }

    /**
     * Gets the value of the classCode property.
     * 
     * @return
     *     possible object is
     *     {@link RoleClassPatient }
     *     
     */
    public RoleClassPatient getClassCode() {
        return classCode;
    }

    /**
     * Sets the value of the classCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link RoleClassPatient }
     *     
     */
    public void setClassCode(RoleClassPatient value) {
        this.classCode = value;
    }

}
