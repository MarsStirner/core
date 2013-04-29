
package nsi;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Классификатор работ (услуг) при лицензировании медицинской помощи (LicUsl)
 * 
 * <p>Java class for V003Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="V003Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="IDRL" use="required" type="{http://www.w3.org/2001/XMLSchema}long" />
 *       &lt;attribute name="LICNAME">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="254"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="IERARH" type="{http://www.w3.org/2001/XMLSchema}long" />
 *       &lt;attribute name="PRIM" type="{http://www.w3.org/2001/XMLSchema}long" />
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
@XmlType(name = "V003Type")
public class V003Type {

    @XmlAttribute(name = "IDRL", required = true)
    protected long idrl;
    @XmlAttribute(name = "LICNAME")
    protected String licname;
    @XmlAttribute(name = "IERARH")
    protected Long ierarh;
    @XmlAttribute(name = "PRIM")
    protected Long prim;
    @XmlAttribute(name = "DATEBEG")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar datebeg;
    @XmlAttribute(name = "DATEEND")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateend;

    /**
     * Gets the value of the idrl property.
     * 
     */
    public long getIDRL() {
        return idrl;
    }

    /**
     * Sets the value of the idrl property.
     * 
     */
    public void setIDRL(long value) {
        this.idrl = value;
    }

    /**
     * Gets the value of the licname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLICNAME() {
        return licname;
    }

    /**
     * Sets the value of the licname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLICNAME(String value) {
        this.licname = value;
    }

    /**
     * Gets the value of the ierarh property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getIERARH() {
        return ierarh;
    }

    /**
     * Sets the value of the ierarh property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setIERARH(Long value) {
        this.ierarh = value;
    }

    /**
     * Gets the value of the prim property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getPRIM() {
        return prim;
    }

    /**
     * Sets the value of the prim property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setPRIM(Long value) {
        this.prim = value;
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
