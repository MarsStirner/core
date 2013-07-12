
package ru.korus.tmis.pix.sda.ws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfCareProviderCareProvider complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfCareProviderCareProvider">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CareProvider" type="{}CareProvider" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfCareProviderCareProvider", propOrder = {
    "careProvider"
})
public class ArrayOfCareProviderCareProvider {

    @XmlElement(name = "CareProvider", nillable = true)
    protected List<CareProvider> careProvider;

    /**
     * Gets the value of the careProvider property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the careProvider property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCareProvider().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CareProvider }
     * 
     * 
     */
    public List<CareProvider> getCareProvider() {
        if (careProvider == null) {
            careProvider = new ArrayList<CareProvider>();
        }
        return this.careProvider;
    }

}
