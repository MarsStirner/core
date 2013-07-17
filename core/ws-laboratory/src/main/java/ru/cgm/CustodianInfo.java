
package ru.cgm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for custodianInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="custodianInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="assignedCustodian" type="{http://cgm.ru}assignedCustodianInfo"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "custodianInfo", propOrder = {
    "assignedCustodian"
})
public class CustodianInfo {

    @XmlElement(required = true)
    protected AssignedCustodianInfo assignedCustodian;

    /**
     * Gets the value of the assignedCustodian property.
     * 
     * @return
     *     possible object is
     *     {@link AssignedCustodianInfo }
     *     
     */
    public AssignedCustodianInfo getAssignedCustodian() {
        return assignedCustodian;
    }

    /**
     * Sets the value of the assignedCustodian property.
     * 
     * @param value
     *     allowed object is
     *     {@link AssignedCustodianInfo }
     *     
     */
    public void setAssignedCustodian(AssignedCustodianInfo value) {
        this.assignedCustodian = value;
    }

}
