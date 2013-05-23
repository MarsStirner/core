
package nsi;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Классификатор причин отказа в оплате медицинской помощи
 * 
 * <p>Java class for F014Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="F014Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="Kod" use="required" type="{http://www.w3.org/2001/XMLSchema}long" />
 *       &lt;attribute name="IDVID" type="{http://www.w3.org/2001/XMLSchema}long" />
 *       &lt;attribute name="Naim">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="1000"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="Osn">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="20"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="Komment">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="100"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="KodPG">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="20"/>
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
@XmlType(name = "F014Type")
public class F014Type {

    @XmlAttribute(name = "Kod", required = true)
    protected long kod;
    @XmlAttribute(name = "IDVID")
    protected Long idvid;
    @XmlAttribute(name = "Naim")
    protected String naim;
    @XmlAttribute(name = "Osn")
    protected String osn;
    @XmlAttribute(name = "Komment")
    protected String komment;
    @XmlAttribute(name = "KodPG")
    protected String kodPG;
    @XmlAttribute(name = "DATEBEG")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar datebeg;
    @XmlAttribute(name = "DATEEND")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateend;

    /**
     * Gets the value of the kod property.
     * 
     */
    public long getKod() {
        return kod;
    }

    /**
     * Sets the value of the kod property.
     * 
     */
    public void setKod(long value) {
        this.kod = value;
    }

    /**
     * Gets the value of the idvid property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getIDVID() {
        return idvid;
    }

    /**
     * Sets the value of the idvid property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setIDVID(Long value) {
        this.idvid = value;
    }

    /**
     * Gets the value of the naim property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNaim() {
        return naim;
    }

    /**
     * Sets the value of the naim property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNaim(String value) {
        this.naim = value;
    }

    /**
     * Gets the value of the osn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOsn() {
        return osn;
    }

    /**
     * Sets the value of the osn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOsn(String value) {
        this.osn = value;
    }

    /**
     * Gets the value of the komment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKomment() {
        return komment;
    }

    /**
     * Sets the value of the komment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKomment(String value) {
        this.komment = value;
    }

    /**
     * Gets the value of the kodPG property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKodPG() {
        return kodPG;
    }

    /**
     * Sets the value of the kodPG property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKodPG(String value) {
        this.kodPG = value;
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
