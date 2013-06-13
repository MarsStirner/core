
package ru.korus.tmis.pix.sda.ws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfClinicalRelationshipClinicalRelationship complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfClinicalRelationshipClinicalRelationship">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ClinicalRelationship" type="{}ClinicalRelationship" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfClinicalRelationshipClinicalRelationship", propOrder = {
    "clinicalRelationship"
})
public class ArrayOfClinicalRelationshipClinicalRelationship {

    @XmlElement(name = "ClinicalRelationship", nillable = true)
    protected List<ClinicalRelationship> clinicalRelationship;

    /**
     * Gets the value of the clinicalRelationship property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the clinicalRelationship property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getClinicalRelationship().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ClinicalRelationship }
     * 
     * 
     */
    public List<ClinicalRelationship> getClinicalRelationship() {
        if (clinicalRelationship == null) {
            clinicalRelationship = new ArrayList<ClinicalRelationship>();
        }
        return this.clinicalRelationship;
    }

}
