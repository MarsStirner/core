
package ru.korus.tmis.pix.sda.ws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfsickLeaveDocumentSickLeaveDocument complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfsickLeaveDocumentSickLeaveDocument">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="sickLeaveDocument" type="{http://schemas.intersystems.ru/hs/ehr/v1}SickLeaveDocument" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfsickLeaveDocumentSickLeaveDocument", propOrder = {
    "sickLeaveDocument"
})
public class ArrayOfsickLeaveDocumentSickLeaveDocument {

    @XmlElement(nillable = true)
    protected List<SickLeaveDocument> sickLeaveDocument;

    /**
     * Gets the value of the sickLeaveDocument property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sickLeaveDocument property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSickLeaveDocument().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SickLeaveDocument }
     * 
     * 
     */
    public List<SickLeaveDocument> getSickLeaveDocument() {
        if (sickLeaveDocument == null) {
            sickLeaveDocument = new ArrayList<SickLeaveDocument>();
        }
        return this.sickLeaveDocument;
    }

}
