
package ru.cgm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="assignedAuthor" type="{http://cgm.ru}assignedAuthorInfo2"/>
 *         &lt;element name="assignedPerson" type="{http://cgm.ru}assignedPersonInfo"/>
 *       &lt;/sequence>
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
    "assignedAuthor",
    "assignedPerson"
})
public class AssignedAuthorInfo {

    protected int id;
    @XmlElement(required = true)
    protected AssignedAuthorInfo2 assignedAuthor;
    @XmlElement(required = true)
    protected AssignedPersonInfo assignedPerson;

    /**
     * Gets the value of the id property.
     * 
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     */
    public void setId(int value) {
        this.id = value;
    }

    /**
     * Gets the value of the assignedAuthor property.
     * 
     * @return
     *     possible object is
     *     {@link AssignedAuthorInfo2 }
     *     
     */
    public AssignedAuthorInfo2 getAssignedAuthor() {
        return assignedAuthor;
    }

    /**
     * Sets the value of the assignedAuthor property.
     * 
     * @param value
     *     allowed object is
     *     {@link AssignedAuthorInfo2 }
     *     
     */
    public void setAssignedAuthor(AssignedAuthorInfo2 value) {
        this.assignedAuthor = value;
    }

    /**
     * Gets the value of the assignedPerson property.
     * 
     * @return
     *     possible object is
     *     {@link AssignedPersonInfo }
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
     *     {@link AssignedPersonInfo }
     *     
     */
    public void setAssignedPerson(AssignedPersonInfo value) {
        this.assignedPerson = value;
    }

}
