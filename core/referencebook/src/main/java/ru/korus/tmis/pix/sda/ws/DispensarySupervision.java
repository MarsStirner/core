
package ru.korus.tmis.pix.sda.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Диспансерное наблюдение
 * 
 * <p>Java class for DispensarySupervision complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DispensarySupervision">
 *   &lt;complexContent>
 *     &lt;extension base="{http://schemas.intersystems.ru/hs/ehr/v1}BaseSerial">
 *       &lt;sequence>
 *         &lt;element name="startDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="stopDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="startedBy" type="{http://schemas.intersystems.ru/hs/ehr/v1}Employee" minOccurs="0"/>
 *         &lt;element name="stoppedBy" type="{http://schemas.intersystems.ru/hs/ehr/v1}Employee" minOccurs="0"/>
 *         &lt;element name="stopReason" type="{http://schemas.intersystems.ru/hs/ehr/v1}CodeAndName" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DispensarySupervision", propOrder = {
    "startDate",
    "stopDate",
    "startedBy",
    "stoppedBy",
    "stopReason"
})
public class DispensarySupervision
    extends BaseSerial
{

    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar startDate;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar stopDate;
    protected Employee startedBy;
    protected Employee stoppedBy;
    protected CodeAndName stopReason;

    /**
     * Gets the value of the startDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStartDate() {
        return startDate;
    }

    /**
     * Sets the value of the startDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStartDate(XMLGregorianCalendar value) {
        this.startDate = value;
    }

    /**
     * Gets the value of the stopDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStopDate() {
        return stopDate;
    }

    /**
     * Sets the value of the stopDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStopDate(XMLGregorianCalendar value) {
        this.stopDate = value;
    }

    /**
     * Gets the value of the startedBy property.
     * 
     * @return
     *     possible object is
     *     {@link Employee }
     *     
     */
    public Employee getStartedBy() {
        return startedBy;
    }

    /**
     * Sets the value of the startedBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link Employee }
     *     
     */
    public void setStartedBy(Employee value) {
        this.startedBy = value;
    }

    /**
     * Gets the value of the stoppedBy property.
     * 
     * @return
     *     possible object is
     *     {@link Employee }
     *     
     */
    public Employee getStoppedBy() {
        return stoppedBy;
    }

    /**
     * Sets the value of the stoppedBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link Employee }
     *     
     */
    public void setStoppedBy(Employee value) {
        this.stoppedBy = value;
    }

    /**
     * Gets the value of the stopReason property.
     * 
     * @return
     *     possible object is
     *     {@link CodeAndName }
     *     
     */
    public CodeAndName getStopReason() {
        return stopReason;
    }

    /**
     * Sets the value of the stopReason property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodeAndName }
     *     
     */
    public void setStopReason(CodeAndName value) {
        this.stopReason = value;
    }

}
