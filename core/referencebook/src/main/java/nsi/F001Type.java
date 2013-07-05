
package nsi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Справочник территориальных фондов ОМС (TFOMS)
 * 
 * <p>Java class for F001Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="F001Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="tf_kod" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="2"/>
 *             &lt;minLength value="2"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="tf_okato" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="5"/>
 *             &lt;minLength value="5"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="tf_ogrn" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="15"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="name_tfp" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="250"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="name_tfk" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="250"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="index">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="6"/>
 *             &lt;minLength value="6"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="address" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="250"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="fam_dir" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="40"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="im_dir" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="40"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="ot_dir" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="40"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="phone" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="40"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="fax" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="40"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="e_mail" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="50"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="kf_tf" use="required" type="{http://www.w3.org/2001/XMLSchema}long" />
 *       &lt;attribute name="www">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="100"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="d_edit" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *       &lt;attribute name="d_end" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "F001Type")
public class F001Type {

    @XmlAttribute(name = "tf_kod", required = true)
    protected String tfKod;
    @XmlAttribute(name = "tf_okato", required = true)
    protected String tfOkato;
    @XmlAttribute(name = "tf_ogrn", required = true)
    protected String tfOgrn;
    @XmlAttribute(name = "name_tfp", required = true)
    protected String nameTfp;
    @XmlAttribute(name = "name_tfk", required = true)
    protected String nameTfk;
    @XmlAttribute(name = "index")
    protected String index;
    @XmlAttribute(name = "address", required = true)
    protected String address;
    @XmlAttribute(name = "fam_dir", required = true)
    protected String famDir;
    @XmlAttribute(name = "im_dir", required = true)
    protected String imDir;
    @XmlAttribute(name = "ot_dir", required = true)
    protected String otDir;
    @XmlAttribute(name = "phone", required = true)
    protected String phone;
    @XmlAttribute(name = "fax", required = true)
    protected String fax;
    @XmlAttribute(name = "e_mail", required = true)
    protected String eMail;
    @XmlAttribute(name = "kf_tf", required = true)
    protected long kfTf;
    @XmlAttribute(name = "www")
    protected String www;
    @XmlAttribute(name = "d_edit")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dEdit;
    @XmlAttribute(name = "d_end")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dEnd;

    /**
     * Gets the value of the tfKod property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTfKod() {
        return tfKod;
    }

    /**
     * Sets the value of the tfKod property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTfKod(String value) {
        this.tfKod = value;
    }

    /**
     * Gets the value of the tfOkato property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTfOkato() {
        return tfOkato;
    }

    /**
     * Sets the value of the tfOkato property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTfOkato(String value) {
        this.tfOkato = value;
    }

    /**
     * Gets the value of the tfOgrn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTfOgrn() {
        return tfOgrn;
    }

    /**
     * Sets the value of the tfOgrn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTfOgrn(String value) {
        this.tfOgrn = value;
    }

    /**
     * Gets the value of the nameTfp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNameTfp() {
        return nameTfp;
    }

    /**
     * Sets the value of the nameTfp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNameTfp(String value) {
        this.nameTfp = value;
    }

    /**
     * Gets the value of the nameTfk property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNameTfk() {
        return nameTfk;
    }

    /**
     * Sets the value of the nameTfk property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNameTfk(String value) {
        this.nameTfk = value;
    }

    /**
     * Gets the value of the index property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIndex() {
        return index;
    }

    /**
     * Sets the value of the index property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIndex(String value) {
        this.index = value;
    }

    /**
     * Gets the value of the address property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the value of the address property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddress(String value) {
        this.address = value;
    }

    /**
     * Gets the value of the famDir property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFamDir() {
        return famDir;
    }

    /**
     * Sets the value of the famDir property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFamDir(String value) {
        this.famDir = value;
    }

    /**
     * Gets the value of the imDir property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImDir() {
        return imDir;
    }

    /**
     * Sets the value of the imDir property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImDir(String value) {
        this.imDir = value;
    }

    /**
     * Gets the value of the otDir property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOtDir() {
        return otDir;
    }

    /**
     * Sets the value of the otDir property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOtDir(String value) {
        this.otDir = value;
    }

    /**
     * Gets the value of the phone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the value of the phone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPhone(String value) {
        this.phone = value;
    }

    /**
     * Gets the value of the fax property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFax() {
        return fax;
    }

    /**
     * Sets the value of the fax property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFax(String value) {
        this.fax = value;
    }

    /**
     * Gets the value of the eMail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEMail() {
        return eMail;
    }

    /**
     * Sets the value of the eMail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEMail(String value) {
        this.eMail = value;
    }

    /**
     * Gets the value of the kfTf property.
     * 
     */
    public long getKfTf() {
        return kfTf;
    }

    /**
     * Sets the value of the kfTf property.
     * 
     */
    public void setKfTf(long value) {
        this.kfTf = value;
    }

    /**
     * Gets the value of the www property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWww() {
        return www;
    }

    /**
     * Sets the value of the www property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWww(String value) {
        this.www = value;
    }

    /**
     * Gets the value of the dEdit property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDEdit() {
        return dEdit;
    }

    /**
     * Sets the value of the dEdit property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDEdit(XMLGregorianCalendar value) {
        this.dEdit = value;
    }

    /**
     * Gets the value of the dEnd property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDEnd() {
        return dEnd;
    }

    /**
     * Sets the value of the dEnd property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDEnd(XMLGregorianCalendar value) {
        this.dEnd = value;
    }

}
