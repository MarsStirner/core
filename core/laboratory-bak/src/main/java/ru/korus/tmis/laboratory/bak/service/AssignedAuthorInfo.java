
package ru.korus.tmis.laboratory.bak.service;

import javax.xml.bind.annotation.*;


/**
 * <p>Java class for assignedAuthorInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="assignedAuthorInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://cgm.ru}doctorIdInfo"/>
 *         &lt;element name="assignedPerson" type="{http://cgm.ru}assignedPersonInfo"/>
 *         &lt;element name="representedOrganization" type="{http://cgm.ru}representedOrganizationInfo"/>
 *       &lt;/sequence>
 *       &lt;attribute name="classCode" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "assignedAuthorInfo", propOrder = {
    "id",
    "assignedPerson",
    "representedOrganization"
})
public class AssignedAuthorInfo {

    @XmlElement(required = true)
    protected DoctorIdInfo id;
    @XmlElement(required = true)
    protected AssignedPersonInfo assignedPerson;
    @XmlElement(required = true)
    protected RepresentedOrganizationInfo representedOrganization;
    @XmlAttribute(name = "classCode")
    protected String classCode;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link ru.korus.tmis.laboratory.bak.service.DoctorIdInfo }
     *
     */
    public DoctorIdInfo getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     *
     * @param value
     *     allowed object is
     *     {@link ru.korus.tmis.laboratory.bak.service.DoctorIdInfo }
     *
     */
    public void setId(DoctorIdInfo value) {
        this.id = value;
    }

    /**
     * Gets the value of the assignedPerson property.
     *
     * @return
     *     possible object is
     *     {@link ru.korus.tmis.laboratory.bak.service.AssignedPersonInfo }
     *
     */
    public AssignedPersonInfo getAssignedPerson() {
        return assignedPerson;
    }

    /**
     * Sets the value of the assignedPerson property.
     *
     * @param value
     *     allowed object is
     *     {@link ru.korus.tmis.laboratory.bak.service.AssignedPersonInfo }
     *
     */
    public void setAssignedPerson(AssignedPersonInfo value) {
        this.assignedPerson = value;
    }

    /**
     * Gets the value of the representedOrganization property.
     *
     * @return
     *     possible object is
     *     {@link ru.korus.tmis.laboratory.bak.service.RepresentedOrganizationInfo }
     *
     */
    public RepresentedOrganizationInfo getRepresentedOrganization() {
        return representedOrganization;
    }

    /**
     * Sets the value of the representedOrganization property.
     *
     * @param value
     *     allowed object is
     *     {@link ru.korus.tmis.laboratory.bak.service.RepresentedOrganizationInfo }
     *     
     */
    public void setRepresentedOrganization(RepresentedOrganizationInfo value) {
        this.representedOrganization = value;
    }

    /**
     * Gets the value of the classCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClassCode() {
        return classCode;
    }

    /**
     * Sets the value of the classCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClassCode(String value) {
        this.classCode = value;
    }

}
