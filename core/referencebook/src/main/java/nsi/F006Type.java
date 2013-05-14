
package nsi;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Классификатор видов контроля (VidExp)
 * 
 * <p>Java class for F006Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="F006Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="IDVID" use="required" type="{http://www.w3.org/2001/XMLSchema}long" />
 *       &lt;attribute name="VIDNAME">
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
@XmlType(name = "F006Type")
public class F006Type {

    @XmlAttribute(name = "IDVID", required = true)
    protected long idvid;
    @XmlAttribute(name = "VIDNAME")
    protected String vidname;
    @XmlAttribute(name = "DATEBEG")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar datebeg;
    @XmlAttribute(name = "DATEEND")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateend;

    /**
     * Gets the value of the idvid property.
     * 
     */
    public long getIDVID() {
        return idvid;
    }

    /**
     * Sets the value of the idvid property.
     * 
     */
    public void setIDVID(long value) {
        this.idvid = value;
    }

    /**
     * Gets the value of the vidname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVIDNAME() {
        return vidname;
    }

    /**
     * Sets the value of the vidname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVIDNAME(String value) {
        this.vidname = value;
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
