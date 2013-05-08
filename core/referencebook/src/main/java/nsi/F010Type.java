
package nsi;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Классификатор субъектов Российской Федерации (Subekti)
 * 
 * <p>Java class for F010Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="F010Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="KOD_TF">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="2"/>
 *             &lt;minLength value="2"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="KOD_OKATO" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="5"/>
 *             &lt;minLength value="5"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="SUBNAME">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="254"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="OKRUG" type="{http://www.w3.org/2001/XMLSchema}long" />
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
@XmlType(name = "F010Type")
public class F010Type {

    @XmlAttribute(name = "KOD_TF")
    protected String kodtf;
    @XmlAttribute(name = "KOD_OKATO", required = true)
    protected String kodokato;
    @XmlAttribute(name = "SUBNAME")
    protected String subname;
    @XmlAttribute(name = "OKRUG")
    protected Long okrug;
    @XmlAttribute(name = "DATEBEG")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar datebeg;
    @XmlAttribute(name = "DATEEND")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateend;

    /**
     * Gets the value of the kodtf property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKODTF() {
        return kodtf;
    }

    /**
     * Sets the value of the kodtf property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKODTF(String value) {
        this.kodtf = value;
    }

    /**
     * Gets the value of the kodokato property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKODOKATO() {
        return kodokato;
    }

    /**
     * Sets the value of the kodokato property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKODOKATO(String value) {
        this.kodokato = value;
    }

    /**
     * Gets the value of the subname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSUBNAME() {
        return subname;
    }

    /**
     * Sets the value of the subname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSUBNAME(String value) {
        this.subname = value;
    }

    /**
     * Gets the value of the okrug property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getOKRUG() {
        return okrug;
    }

    /**
     * Sets the value of the okrug property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setOKRUG(Long value) {
        this.okrug = value;
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
