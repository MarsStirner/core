
package nsi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Классификатор результатов обращения за медицинской помощью (Rezult)
 * 
 * <p>Java class for V009Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="V009Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="IDRMP" use="required" type="{http://www.w3.org/2001/XMLSchema}long" />
 *       &lt;attribute name="RMPNAME">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="254"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="ID_USLOV" type="{http://www.w3.org/2001/XMLSchema}long" />
 *       &lt;attribute name="DATEBEG" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *       &lt;attribute name="DATEEND" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "V009Type")
public class V009Type {

    @XmlAttribute(name = "IDRMP", required = true)
    protected long idrmp;
    @XmlAttribute(name = "RMPNAME")
    protected String rmpname;
    @XmlAttribute(name = "ID_USLOV")
    protected Long iduslov;
    @XmlAttribute(name = "DATEBEG")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar datebeg;
    @XmlAttribute(name = "DATEEND")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateend;

    /**
     * Gets the value of the idrmp property.
     * 
     */
    public long getIDRMP() {
        return idrmp;
    }

    /**
     * Sets the value of the idrmp property.
     * 
     */
    public void setIDRMP(long value) {
        this.idrmp = value;
    }

    /**
     * Gets the value of the rmpname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRMPNAME() {
        return rmpname;
    }

    /**
     * Sets the value of the rmpname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRMPNAME(String value) {
        this.rmpname = value;
    }

    /**
     * Gets the value of the iduslov property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getIDUSLOV() {
        return iduslov;
    }

    /**
     * Sets the value of the iduslov property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setIDUSLOV(Long value) {
        this.iduslov = value;
    }

    /**
     * Gets the value of the datebeg property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDATEBEG() {
        return datebeg;
    }

    /**
     * Sets the value of the datebeg property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDATEBEG(XMLGregorianCalendar value) {
        this.datebeg = value;
    }

    /**
     * Gets the value of the dateend property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDATEEND() {
        return dateend;
    }

    /**
     * Sets the value of the dateend property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDATEEND(XMLGregorianCalendar value) {
        this.dateend = value;
    }

}
