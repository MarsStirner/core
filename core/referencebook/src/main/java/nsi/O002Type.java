
package nsi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Общероссийский классификатор административнотерриториального деления (OKATO)
 * 
 * <p>Java class for O002Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="O002Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="TER">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="2"/>
 *             &lt;minLength value="2"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="KOD1">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="3"/>
 *             &lt;minLength value="3"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="KOD2">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="3"/>
 *             &lt;minLength value="3"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="KOD3">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="3"/>
 *             &lt;minLength value="3"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="RAZDEL">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="1"/>
 *             &lt;minLength value="1"/>
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
 *       &lt;attribute name="CENTRUM">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="80"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="NOMDESCR">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="250"/>
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
@XmlType(name = "O002Type")
public class O002Type {

    @XmlAttribute(name = "TER")
    protected String ter;
    @XmlAttribute(name = "KOD1")
    protected String kod1;
    @XmlAttribute(name = "KOD2")
    protected String kod2;
    @XmlAttribute(name = "KOD3")
    protected String kod3;
    @XmlAttribute(name = "RAZDEL")
    protected String razdel;
    @XmlAttribute(name = "NAME1")
    protected String name1;
    @XmlAttribute(name = "CENTRUM")
    protected String centrum;
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
     * Gets the value of the ter property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTER() {
        return ter;
    }

    /**
     * Sets the value of the ter property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTER(String value) {
        this.ter = value;
    }

    /**
     * Gets the value of the kod1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKOD1() {
        return kod1;
    }

    /**
     * Sets the value of the kod1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKOD1(String value) {
        this.kod1 = value;
    }

    /**
     * Gets the value of the kod2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKOD2() {
        return kod2;
    }

    /**
     * Sets the value of the kod2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKOD2(String value) {
        this.kod2 = value;
    }

    /**
     * Gets the value of the kod3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKOD3() {
        return kod3;
    }

    /**
     * Sets the value of the kod3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKOD3(String value) {
        this.kod3 = value;
    }

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
     * Gets the value of the centrum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCENTRUM() {
        return centrum;
    }

    /**
     * Sets the value of the centrum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCENTRUM(String value) {
        this.centrum = value;
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
