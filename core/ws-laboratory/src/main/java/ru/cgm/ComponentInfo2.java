
package ru.cgm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ComponentInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ComponentInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="section" type="{http://cgm.ru}sectionInfo"/>
 *         &lt;element name="entry" type="{http://cgm.ru}entryInfo"/>
 *         &lt;element name="specimen" type="{http://cgm.ru}specimenInfo"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ComponentInfo", propOrder = {
    "section",
    "entry",
    "specimen"
})
public class ComponentInfo2 {

    @XmlElement(required = true)
    protected SectionInfo section;
    @XmlElement(required = true)
    protected EntryInfo entry;
    @XmlElement(required = true)
    protected SpecimenInfo specimen;

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
     * @return
     *     possible object is
     *     {@link EntryInfo }
     *     
     */
    public EntryInfo getEntry() {
        return entry;
    }

    /**
     * Sets the value of the entry property.
     * 
     * @param value
     *     allowed object is
     *     {@link EntryInfo }
     *     
     */
    public void setEntry(EntryInfo value) {
        this.entry = value;
    }

    /**
     * Gets the value of the specimen property.
     * 
     * @return
     *     possible object is
     *     {@link SpecimenInfo }
     *     
     */
    public SpecimenInfo getSpecimen() {
        return specimen;
    }

    /**
     * Sets the value of the specimen property.
     * 
     * @param value
     *     allowed object is
     *     {@link SpecimenInfo }
     *     
     */
    public void setSpecimen(SpecimenInfo value) {
        this.specimen = value;
    }

}
