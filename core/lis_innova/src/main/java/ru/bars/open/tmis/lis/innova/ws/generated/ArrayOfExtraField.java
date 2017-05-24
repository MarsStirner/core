
package ru.bars.open.tmis.lis.innova.ws.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfExtraField complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfExtraField"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ExtraField" type="{http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS}ExtraField" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfExtraField", propOrder = {
    "extraField"
})
public class ArrayOfExtraField {

    @XmlElement(name = "ExtraField", nillable = true)
    protected List<ExtraField> extraField;

    /**
     * Gets the value of the extraField property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the extraField property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExtraField().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ExtraField }
     * 
     * 
     */
    public List<ExtraField> getExtraField() {
        if (extraField == null) {
            extraField = new ArrayList<ExtraField>();
        }
        return this.extraField;
    }

}
