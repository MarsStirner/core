
package ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StrucDoc.ColGroup complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="StrucDoc.ColGroup">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:hl7-org:v3}StrucDoc.ColItem">
 *       &lt;sequence>
 *         &lt;element name="col" type="{urn:hl7-org:v3}StrucDoc.Col" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StrucDoc.ColGroup", propOrder = {
    "col"
})
public class StrucDocColGroup
    extends StrucDocColItem
{

    protected List<StrucDocCol> col;

    /**
     * Gets the value of the col property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the col property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCol().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link StrucDocCol }
     * 
     * 
     */
    public List<StrucDocCol> getCol() {
        if (col == null) {
            col = new ArrayList<StrucDocCol>();
        }
        return this.col;
    }

}
