
package ru.korus.tmis.pix.sda.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for HistoryEntry complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HistoryEntry">
 *   &lt;complexContent>
 *     &lt;extension base="{}BaseSerial">
 *       &lt;sequence>
 *         &lt;element name="startedOn" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="historyEntry" type="{}String" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HistoryEntry", propOrder = {
    "startedOn",
    "historyEntry"
})
public class HistoryEntry
    extends BaseSerial
{

    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar startedOn;
    protected String historyEntry;

    /**
     * Gets the value of the startedOn property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStartedOn() {
        return startedOn;
    }

    /**
     * Sets the value of the startedOn property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStartedOn(XMLGregorianCalendar value) {
        this.startedOn = value;
    }

    /**
     * Gets the value of the historyEntry property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHistoryEntry() {
        return historyEntry;
    }

    /**
     * Sets the value of the historyEntry property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHistoryEntry(String value) {
        this.historyEntry = value;
    }

}
