
package nsi;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Общероссийский классификатор организационно-правовых форм (OKOPF)
 * 
 * <p>Java class for O005Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="O005Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="KOD" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="2"/>
 *             &lt;minLength value="2"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="NAME1">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="250"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="ALG">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="52"/>
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
@XmlType(name = "O005Type")
public class O005Type {

    @XmlAttribute(name = "KOD", required = true)
    protected String kod;
    @XmlAttribute(name = "NAME1")
    protected String name1;
    @XmlAttribute(name = "ALG")
    protected String alg;
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
     * Gets the value of the name1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNAME1() {
        return name1;
    }

    /**
     * Sets the value of the name1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNAME1(String value) {
        this.name1 = value;
    }

    /**
     * Gets the value of the alg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getALG() {
        return alg;
    }

    /**
     * Sets the value of the alg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setALG(String value) {
        this.alg = value;
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
