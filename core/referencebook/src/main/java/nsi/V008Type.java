
package nsi;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Классификатор видов медицинской помощи (V008VidMp)
 * 
 * <p>Java class for V008Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="V008Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="IDVMP" use="required" type="{http://www.w3.org/2001/XMLSchema}long" />
 *       &lt;attribute name="VMPNAME">
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
@XmlType(name = "V008Type")
public class V008Type {

    @XmlAttribute(name = "IDVMP", required = true)
    protected long idvmp;
    @XmlAttribute(name = "VMPNAME")
    protected String vmpname;
    @XmlAttribute(name = "DATEBEG")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar datebeg;
    @XmlAttribute(name = "DATEEND")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateend;

    /**
     * Gets the value of the idvmp property.
     * 
     */
    public long getIDVMP() {
        return idvmp;
    }

    /**
     * Sets the value of the idvmp property.
     * 
     */
    public void setIDVMP(long value) {
        this.idvmp = value;
    }

    /**
     * Gets the value of the vmpname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVMPNAME() {
        return vmpname;
    }

    /**
     * Sets the value of the vmpname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVMPNAME(String value) {
        this.vmpname = value;
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
