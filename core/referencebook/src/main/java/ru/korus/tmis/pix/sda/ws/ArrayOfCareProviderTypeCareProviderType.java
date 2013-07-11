
package ru.korus.tmis.pix.sda.ws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfCareProviderTypeCareProviderType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfCareProviderTypeCareProviderType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CareProviderType" type="{}CareProviderType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfCareProviderTypeCareProviderType", propOrder = {
    "careProviderType"
})
public class ArrayOfCareProviderTypeCareProviderType {

    @XmlElement(name = "CareProviderType", nillable = true)
    protected List<CareProviderType> careProviderType;

    /**
     * Gets the value of the careProviderType property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the careProviderType property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCareProviderType().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CareProviderType }
     * 
     * 
     */
    public List<CareProviderType> getCareProviderType() {
        if (careProviderType == null) {
            careProviderType = new ArrayList<CareProviderType>();
        }
        return this.careProviderType;
    }

}
