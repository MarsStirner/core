
package nsi;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Классификатор способов оплаты медицинской помощи (Sposob)
 * 
 * <p>Java class for V010Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="V010Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="IDSP" use="required" type="{http://www.w3.org/2001/XMLSchema}long" />
 *       &lt;attribute name="SPNAME">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="254"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
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
@XmlType(name = "V010Type")
public class V010Type {

    @XmlAttribute(name = "IDSP", required = true)
    protected long idsp;
    @XmlAttribute(name = "SPNAME")
    protected String spname;
    @XmlAttribute(name = "DATEBEG")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar datebeg;
    @XmlAttribute(name = "DATEEND")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateend;

    /**
     * Gets the value of the idsp property.
     * 
     */
    public long getIDSP() {
        return idsp;
    }

    /**
     * Sets the value of the idsp property.
     * 
     */
    public void setIDSP(long value) {
        this.idsp = value;
    }

    /**
     * Gets the value of the spname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSPNAME() {
        return spname;
    }

    /**
     * Sets the value of the spname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSPNAME(String value) {
        this.spname = value;
    }

    /**
     * Gets the value of the datebeg property.
     * 
     * @return
     *     possible object is
     *     {@link javax.xml.datatype.XMLGregorianCalendar }
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
     *     {@link javax.xml.datatype.XMLGregorianCalendar }
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
     *     {@link javax.xml.datatype.XMLGregorianCalendar }
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
     *     {@link javax.xml.datatype.XMLGregorianCalendar }
     *     
     */
    public void setDATEEND(XMLGregorianCalendar value) {
        this.dateend = value;
    }

}
