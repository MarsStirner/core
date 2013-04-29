
package nsi;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Общероссийский классификатор видов экономической деятельности (OKVED)
 * 
 * <p>Java class for O003Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="O003Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="RAZDEL">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="1"/>
 *             &lt;minLength value="1"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="PRAZDEL">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="2"/>
 *             &lt;minLength value="2"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="KOD" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="8"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="NAME11">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="250"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="NAME12">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="250"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="NOMDESCR">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="5000"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="NOMAKT">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="3"/>
 *             &lt;minLength value="3"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="STATUS" type="{http://www.w3.org/2001/XMLSchema}long" />
 *       &lt;attribute name="DATA_UPD" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "O003Type")
public class O003Type {

    @XmlAttribute(name = "RAZDEL")
    protected String razdel;
    @XmlAttribute(name = "PRAZDEL")
    protected String prazdel;
    @XmlAttribute(name = "KOD", required = true)
    protected String kod;
    @XmlAttribute(name = "NAME11")
    protected String name11;
    @XmlAttribute(name = "NAME12")
    protected String name12;
    @XmlAttribute(name = "NOMDESCR")
    protected String nomdescr;
    @XmlAttribute(name = "NOMAKT")
    protected String nomakt;
    @XmlAttribute(name = "STATUS")
    protected Long status;
    @XmlAttribute(name = "DATA_UPD")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataupd;

    /**
     * Gets the value of the razdel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRAZDEL() {
        return razdel;
    }

    /**
     * Sets the value of the razdel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRAZDEL(String value) {
        this.razdel = value;
    }

    /**
     * Gets the value of the prazdel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPRAZDEL() {
        return prazdel;
    }

    /**
     * Sets the value of the prazdel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPRAZDEL(String value) {
        this.prazdel = value;
    }

    /**
     * Gets the value of the kod property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKOD() {
        return kod;
    }

    /**
     * Sets the value of the kod property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKOD(String value) {
        this.kod = value;
    }

    /**
     * Gets the value of the name11 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNAME11() {
        return name11;
    }

    /**
     * Sets the value of the name11 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNAME11(String value) {
        this.name11 = value;
    }

    /**
     * Gets the value of the name12 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNAME12() {
        return name12;
    }

    /**
     * Sets the value of the name12 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNAME12(String value) {
        this.name12 = value;
    }

    /**
     * Gets the value of the nomdescr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNOMDESCR() {
        return nomdescr;
    }

    /**
     * Sets the value of the nomdescr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNOMDESCR(String value) {
        this.nomdescr = value;
    }

    /**
     * Gets the value of the nomakt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNOMAKT() {
        return nomakt;
    }

    /**
     * Sets the value of the nomakt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNOMAKT(String value) {
        this.nomakt = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getSTATUS() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setSTATUS(Long value) {
        this.status = value;
    }

    /**
     * Gets the value of the dataupd property.
     * 
     * @return
     *     possible object is
     *     {@link javax.xml.datatype.XMLGregorianCalendar }
     *
     */
    public XMLGregorianCalendar getDATAUPD() {
        return dataupd;
    }

    /**
     * Sets the value of the dataupd property.
     *
     * @param value
     *     allowed object is
     *     {@link javax.xml.datatype.XMLGregorianCalendar }
     *     
     */
    public void setDATAUPD(XMLGregorianCalendar value) {
        this.dataupd = value;
    }

}
