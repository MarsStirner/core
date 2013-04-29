
package nsi;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Международный классификатор болезней (MKB10)
 * 
 * <p>Java class for M001Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="M001Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="IDDS" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="10"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="DSNAME">
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
@XmlType(name = "M001Type")
public class M001Type {

    @XmlAttribute(name = "IDDS", required = true)
    protected String idds;
    @XmlAttribute(name = "DSNAME")
    protected String dsname;
    @XmlAttribute(name = "DATEBEG")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar datebeg;
    @XmlAttribute(name = "DATEEND")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateend;

    /**
     * Gets the value of the idds property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDDS() {
        return idds;
    }

    /**
     * Sets the value of the idds property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDDS(String value) {
        this.idds = value;
    }

    /**
     * Gets the value of the dsname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDSNAME() {
        return dsname;
    }

    /**
     * Sets the value of the dsname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDSNAME(String value) {
        this.dsname = value;
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
