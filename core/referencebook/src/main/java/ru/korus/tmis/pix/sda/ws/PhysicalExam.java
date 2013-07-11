
package ru.korus.tmis.pix.sda.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for PhysicalExam complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PhysicalExam">
 *   &lt;complexContent>
 *     &lt;extension base="{}SuperClass">
 *       &lt;sequence>
 *         &lt;element name="PhysExamTime" type="{}TimeStamp" minOccurs="0"/>
 *         &lt;element name="PhysExamCode" type="{}PhysExam" minOccurs="0"/>
 *         &lt;element name="PhysExamObs" type="{}PhysExamObs" minOccurs="0"/>
 *         &lt;element name="PhysExamObsQualifier" type="{}PhysExamObsQualifier" minOccurs="0"/>
 *         &lt;element name="PhysExamObsValue" type="{}PhysExamObsValue" minOccurs="0"/>
 *         &lt;element name="NoteText" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PhysicalExam", propOrder = {
    "physExamTime",
    "physExamCode",
    "physExamObs",
    "physExamObsQualifier",
    "physExamObsValue",
    "noteText"
})
public class PhysicalExam
    extends SuperClass
{

    @XmlElement(name = "PhysExamTime")
    protected XMLGregorianCalendar physExamTime;
    @XmlElement(name = "PhysExamCode")
    protected PhysExam physExamCode;
    @XmlElement(name = "PhysExamObs")
    protected PhysExamObs physExamObs;
    @XmlElement(name = "PhysExamObsQualifier")
    protected PhysExamObsQualifier physExamObsQualifier;
    @XmlElement(name = "PhysExamObsValue")
    protected PhysExamObsValue physExamObsValue;
    @XmlElement(name = "NoteText")
    protected String noteText;

    /**
     * Gets the value of the physExamTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getPhysExamTime() {
        return physExamTime;
    }

    /**
     * Sets the value of the physExamTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setPhysExamTime(XMLGregorianCalendar value) {
        this.physExamTime = value;
    }

    /**
     * Gets the value of the physExamCode property.
     * 
     * @return
     *     possible object is
     *     {@link PhysExam }
     *     
     */
    public PhysExam getPhysExamCode() {
        return physExamCode;
    }

    /**
     * Sets the value of the physExamCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link PhysExam }
     *     
     */
    public void setPhysExamCode(PhysExam value) {
        this.physExamCode = value;
    }

    /**
     * Gets the value of the physExamObs property.
     * 
     * @return
     *     possible object is
     *     {@link PhysExamObs }
     *     
     */
    public PhysExamObs getPhysExamObs() {
        return physExamObs;
    }

    /**
     * Sets the value of the physExamObs property.
     * 
     * @param value
     *     allowed object is
     *     {@link PhysExamObs }
     *     
     */
    public void setPhysExamObs(PhysExamObs value) {
        this.physExamObs = value;
    }

    /**
     * Gets the value of the physExamObsQualifier property.
     * 
     * @return
     *     possible object is
     *     {@link PhysExamObsQualifier }
     *     
     */
    public PhysExamObsQualifier getPhysExamObsQualifier() {
        return physExamObsQualifier;
    }

    /**
     * Sets the value of the physExamObsQualifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link PhysExamObsQualifier }
     *     
     */
    public void setPhysExamObsQualifier(PhysExamObsQualifier value) {
        this.physExamObsQualifier = value;
    }

    /**
     * Gets the value of the physExamObsValue property.
     * 
     * @return
     *     possible object is
     *     {@link PhysExamObsValue }
     *     
     */
    public PhysExamObsValue getPhysExamObsValue() {
        return physExamObsValue;
    }

    /**
     * Sets the value of the physExamObsValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link PhysExamObsValue }
     *     
     */
    public void setPhysExamObsValue(PhysExamObsValue value) {
        this.physExamObsValue = value;
    }

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

}
