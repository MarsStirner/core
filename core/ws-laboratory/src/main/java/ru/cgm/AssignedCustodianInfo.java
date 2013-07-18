
package ru.cgm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for assignedCustodianInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="assignedCustodianInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="representedCustodianOrganization" type="{http://cgm.ru}representedCustodianOrganizationInfo"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "assignedCustodianInfo", propOrder = {
    "representedCustodianOrganization"
})
public class AssignedCustodianInfo {

    @XmlElement(required = true)
    protected RepresentedCustodianOrganizationInfo representedCustodianOrganization;

    /**
     * Gets the value of the representedCustodianOrganization property.
     * 
     * @return
     *     possible object is
     *     {@link RepresentedCustodianOrganizationInfo }
     *     
     */
    public RepresentedCustodianOrganizationInfo getRepresentedCustodianOrganization() {
        return representedCustodianOrganization;
    }

    /**
     * Sets the value of the representedCustodianOrganization property.
     * 
     * @param value
     *     allowed object is
     *     {@link RepresentedCustodianOrganizationInfo }
     *     
     */
    public void setRepresentedCustodianOrganization(RepresentedCustodianOrganizationInfo value) {
        this.representedCustodianOrganization = value;
    }

}
