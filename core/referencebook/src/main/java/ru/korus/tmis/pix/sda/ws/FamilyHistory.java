
package ru.korus.tmis.pix.sda.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FamilyHistory complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FamilyHistory">
 *   &lt;complexContent>
 *     &lt;extension base="{}SuperClass">
 *       &lt;sequence>
 *         &lt;element name="FamilyMember" type="{}FamilyMember" minOccurs="0"/>
 *         &lt;element name="Diagnosis" type="{}DiagnosisCode" minOccurs="0"/>
 *         &lt;element name="NoteText" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FamilyHistory", propOrder = {
    "familyMember",
    "diagnosis",
    "noteText",
    "status"
})
public class FamilyHistory
    extends SuperClass
{

    @XmlElement(name = "FamilyMember")
    protected FamilyMember familyMember;
    @XmlElement(name = "Diagnosis")
    protected DiagnosisCode diagnosis;
    @XmlElement(name = "NoteText")
    protected String noteText;
    @XmlElement(name = "Status")
    protected String status;

    /**
     * Gets the value of the familyMember property.
     * 
     * @return
     *     possible object is
     *     {@link FamilyMember }
     *     
     */
    public FamilyMember getFamilyMember() {
        return familyMember;
    }

    /**
     * Sets the value of the familyMember property.
     * 
     * @param value
     *     allowed object is
     *     {@link FamilyMember }
     *     
     */
    public void setFamilyMember(FamilyMember value) {
        this.familyMember = value;
    }

    /**
     * Gets the value of the diagnosis property.
     * 
     * @return
     *     possible object is
     *     {@link DiagnosisCode }
     *     
     */
    public DiagnosisCode getDiagnosis() {
        return diagnosis;
    }

    /**
     * Sets the value of the diagnosis property.
     * 
     * @param value
     *     allowed object is
     *     {@link DiagnosisCode }
     *     
     */
    public void setDiagnosis(DiagnosisCode value) {
        this.diagnosis = value;
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

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

}
