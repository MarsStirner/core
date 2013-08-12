
package ru.korus.tmis.pix.sda.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Recommendation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Recommendation">
 *   &lt;complexContent>
 *     &lt;extension base="{}DataType">
 *       &lt;sequence>
 *         &lt;element name="NoteText" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RecipientPersons" type="{}ArrayOfCareProviderCareProvider" minOccurs="0"/>
 *         &lt;element name="RecipientOrganizations" type="{}ArrayOfHealthCareFacilityHealthCareFacility" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Recommendation", propOrder = {
    "noteText",
    "recipientPersons",
    "recipientOrganizations"
})
public class Recommendation
    extends DataType
{

    @XmlElement(name = "NoteText")
    protected String noteText;
    @XmlElement(name = "RecipientPersons")
    protected ArrayOfCareProviderCareProvider recipientPersons;
    @XmlElement(name = "RecipientOrganizations")
    protected ArrayOfHealthCareFacilityHealthCareFacility recipientOrganizations;

    /**
     * Gets the value of the noteText property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNoteText() {
        return noteText;
    }

    /**
     * Sets the value of the noteText property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNoteText(String value) {
        this.noteText = value;
    }

    /**
     * Gets the value of the recipientPersons property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfCareProviderCareProvider }
     *     
     */
    public ArrayOfCareProviderCareProvider getRecipientPersons() {
        return recipientPersons;
    }

    /**
     * Sets the value of the recipientPersons property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfCareProviderCareProvider }
     *     
     */
    public void setRecipientPersons(ArrayOfCareProviderCareProvider value) {
        this.recipientPersons = value;
    }

    /**
     * Gets the value of the recipientOrganizations property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfHealthCareFacilityHealthCareFacility }
     *     
     */
    public ArrayOfHealthCareFacilityHealthCareFacility getRecipientOrganizations() {
        return recipientOrganizations;
    }

    /**
     * Sets the value of the recipientOrganizations property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfHealthCareFacilityHealthCareFacility }
     *     
     */
    public void setRecipientOrganizations(ArrayOfHealthCareFacilityHealthCareFacility value) {
        this.recipientOrganizations = value;
    }

}
