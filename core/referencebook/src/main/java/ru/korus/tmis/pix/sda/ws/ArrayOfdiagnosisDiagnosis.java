
package ru.korus.tmis.pix.sda.ws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfdiagnosisDiagnosis complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfdiagnosisDiagnosis">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="diagnosis" type="{http://schemas.intersystems.ru/hs/ehr/v1}Diagnosis" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfdiagnosisDiagnosis", propOrder = {
    "diagnosis"
})
public class ArrayOfdiagnosisDiagnosis {

    @XmlElement(nillable = true)
    protected List<Diagnosis> diagnosis;

    /**
     * Gets the value of the diagnosis property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the diagnosis property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDiagnosis().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Diagnosis }
     * 
     * 
     */
    public List<Diagnosis> getDiagnosis() {
        if (diagnosis == null) {
            diagnosis = new ArrayList<Diagnosis>();
        }
        return this.diagnosis;
    }

}
