
package ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StrucDoc.TRowGroup complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="StrucDoc.TRowGroup">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:hl7-org:v3}StrucDoc.TableItem">
 *       &lt;sequence>
 *         &lt;element name="tr" type="{urn:hl7-org:v3}StrucDoc.TRow" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StrucDoc.TRowGroup", propOrder = {
    "tr"
})
public class StrucDocTRowGroup
    extends StrucDocTableItem
{

    protected List<StrucDocTRow> tr;

    /**
     * Gets the value of the tr property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tr property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTr().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link StrucDocTRow }
     * 
     * 
     */
    public List<StrucDocTRow> getTr() {
        if (tr == null) {
            tr = new ArrayList<StrucDocTRow>();
        }
        return this.tr;
    }

}
