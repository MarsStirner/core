
package ru.korus.tmis.pix.sda.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for Procedure complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Procedure">
 *   &lt;complexContent>
 *     &lt;extension base="{}SuperClass">
 *       &lt;sequence>
 *         &lt;element name="Procedure" type="{}ProcedureCode" minOccurs="0"/>
 *         &lt;element name="Clinician" type="{}CareProvider" minOccurs="0"/>
 *         &lt;element name="ProcedureTime" type="{}TimeStamp" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Procedure", propOrder = {
    "procedure",
    "clinician",
    "procedureTime"
})
public class Procedure
    extends SuperClass
{

    @XmlElement(name = "Procedure")
    protected ProcedureCode procedure;
    @XmlElement(name = "Clinician")
    protected CareProvider clinician;
    @XmlElement(name = "ProcedureTime")
    protected XMLGregorianCalendar procedureTime;

    /**
     * Gets the value of the procedure property.
     * 
     * @return
     *     possible object is
     *     {@link ProcedureCode }
     *     
     */
    public ProcedureCode getProcedure() {
        return procedure;
    }

    /**
     * Sets the value of the procedure property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProcedureCode }
     *     
     */
    public void setProcedure(ProcedureCode value) {
        this.procedure = value;
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
     * Gets the value of the procedureTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getProcedureTime() {
        return procedureTime;
    }

    /**
     * Sets the value of the procedureTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setProcedureTime(XMLGregorianCalendar value) {
        this.procedureTime = value;
    }

}
