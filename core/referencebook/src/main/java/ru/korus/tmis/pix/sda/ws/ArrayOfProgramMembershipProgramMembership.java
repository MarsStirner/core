
package ru.korus.tmis.pix.sda.ws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfProgramMembershipProgramMembership complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfProgramMembershipProgramMembership">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ProgramMembership" type="{}ProgramMembership" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfProgramMembershipProgramMembership", propOrder = {
    "programMembership"
})
public class ArrayOfProgramMembershipProgramMembership {

    @XmlElement(name = "ProgramMembership", nillable = true)
    protected List<ProgramMembership> programMembership;

    /**
     * Gets the value of the programMembership property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the programMembership property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProgramMembership().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ProgramMembership }
     * 
     * 
     */
    public List<ProgramMembership> getProgramMembership() {
        if (programMembership == null) {
            programMembership = new ArrayList<ProgramMembership>();
        }
        return this.programMembership;
    }

}
