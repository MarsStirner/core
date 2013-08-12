
package ru.cgm.service;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for subComponentInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="subComponentInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="section" type="{http://cgm.ru}sectionInfo"/>
 *         &lt;element name="entry" type="{http://cgm.ru}entryInfo" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "subComponentInfo", propOrder = {
    "section",
    "entry"
})
public class SubComponentInfo {

    @XmlElement(required = true)
    protected SectionInfo section;
    protected List<EntryInfo> entry;

    /**
     * Gets the value of the section property.
     * 
     * @return
     *     possible object is
     *     {@link SectionInfo }
     *     
     */
    public SectionInfo getSection() {
        return section;
    }

    /**
     * Sets the value of the section property.
     * 
     * @param value
     *     allowed object is
     *     {@link SectionInfo }
     *     
     */
    public void setSection(SectionInfo value) {
        this.section = value;
    }

    /**
     * Gets the value of the entry property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the entry property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEntry().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EntryInfo }
     * 
     * 
     */
    public List<EntryInfo> getEntry() {
        if (entry == null) {
            entry = new ArrayList<EntryInfo>();
        }
        return this.entry;
    }

}
