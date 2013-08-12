
package nsi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Общероссийский классификатор стран мира (OKSM)
 * 
 * <p>Java class for O001Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="O001Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="KOD" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="3"/>
 *             &lt;minLength value="3"/>
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
 *       &lt;attribute name="ALFA2">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="2"/>
 *             &lt;minLength value="2"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="ALFA3">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="3"/>
 *             &lt;minLength value="3"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="NOMDESCR">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="4000"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="NOMAKT">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="3"/>
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
@XmlType(name = "O001Type")
public class O001Type {

    @XmlAttribute(name = "KOD", required = true)
    protected String kod;
    @XmlAttribute(name = "NAME11")
    protected String name11;
    @XmlAttribute(name = "NAME12")
    protected String name12;
    @XmlAttribute(name = "ALFA2")
    protected String alfa2;
    @XmlAttribute(name = "ALFA3")
    protected String alfa3;
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
     * Gets the value of the alfa2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getALFA2() {
        return alfa2;
    }

    /**
     * Sets the value of the alfa2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setALFA2(String value) {
        this.alfa2 = value;
    }

    /**
     * Gets the value of the alfa3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getALFA3() {
        return alfa3;
    }

    /**
     * Sets the value of the alfa3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setALFA3(String value) {
        this.alfa3 = value;
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
     *     {@link XMLGregorianCalendar }
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
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDATAUPD(XMLGregorianCalendar value) {
        this.dataupd = value;
    }

}
