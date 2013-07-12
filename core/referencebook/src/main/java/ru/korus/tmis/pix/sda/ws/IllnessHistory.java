
package ru.korus.tmis.pix.sda.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for IllnessHistory complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IllnessHistory">
 *   &lt;complexContent>
 *     &lt;extension base="{}SuperClass">
 *       &lt;sequence>
 *         &lt;element name="NoteText" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Condition" type="{}PastHistoryCondition" minOccurs="0"/>
 *         &lt;element name="Clinician" type="{}CareProvider" minOccurs="0"/>
 *         &lt;element name="VerifiedTime" type="{}TimeStamp" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IllnessHistory", propOrder = {
    "noteText",
    "condition",
    "clinician",
    "verifiedTime"
})
public class IllnessHistory
    extends SuperClass
{

    @XmlElement(name = "NoteText")
    protected String noteText;
    @XmlElement(name = "Condition")
    protected PastHistoryCondition condition;
    @XmlElement(name = "Clinician")
    protected CareProvider clinician;
    @XmlElement(name = "VerifiedTime")
    protected XMLGregorianCalendar verifiedTime;

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
     * Gets the value of the condition property.
     * 
     * @return
     *     possible object is
     *     {@link PastHistoryCondition }
     *     
     */
    public PastHistoryCondition getCondition() {
        return condition;
    }

    /**
     * Sets the value of the condition property.
     * 
     * @param value
     *     allowed object is
     *     {@link PastHistoryCondition }
     *     
     */
    public void setCondition(PastHistoryCondition value) {
        this.condition = value;
    }

    /**
     * Gets the value of the clinician property.
     * 
     * @return
     *     possible object is
     *     {@link CareProvider }
     *     
     */
    public CareProvider getClinician() {
        return clinician;
    }

    /**
     * Sets the value of the clinician property.
     * 
     * @param value
     *     allowed object is
     *     {@link CareProvider }
     *     
     */
    public void setClinician(CareProvider value) {
        this.clinician = value;
    }

    /**
     * Gets the value of the verifiedTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getVerifiedTime() {
        return verifiedTime;
    }

    /**
     * Sets the value of the verifiedTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setVerifiedTime(XMLGregorianCalendar value) {
        this.verifiedTime = value;
    }

}
