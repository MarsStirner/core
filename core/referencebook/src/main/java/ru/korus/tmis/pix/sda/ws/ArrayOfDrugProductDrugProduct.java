
package ru.korus.tmis.pix.sda.ws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfDrugProductDrugProduct complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfDrugProductDrugProduct">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DrugProduct" type="{}DrugProduct" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfDrugProductDrugProduct", propOrder = {
    "drugProduct"
})
public class ArrayOfDrugProductDrugProduct {

    @XmlElement(name = "DrugProduct", nillable = true)
    protected List<DrugProduct> drugProduct;

    /**
     * Gets the value of the drugProduct property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the drugProduct property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDrugProduct().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DrugProduct }
     * 
     * 
     */
    public List<DrugProduct> getDrugProduct() {
        if (drugProduct == null) {
            drugProduct = new ArrayList<DrugProduct>();
        }
        return this.drugProduct;
    }

}
