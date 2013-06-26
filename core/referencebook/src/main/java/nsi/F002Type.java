
package nsi;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Единый реестр страховых медицинских организаций, осуществляющих деятельность в сфере обязательного медицинского страхования (SMO)
 * 
 * <p>Java class for F002Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="F002Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="tf_okato" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="5"/>
 *             &lt;minLength value="5"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="smocod">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="5"/>
 *             &lt;minLength value="5"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="nam_smop" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="254"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="nam_smok">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="250"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="inn" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="12"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="Ogrn">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="15"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="KPP">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="9"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="index_j">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="6"/>
 *             &lt;minLength value="6"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="addr_j" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="254"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="index_f">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="6"/>
 *             &lt;minLength value="6"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="addr_f">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="254"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="okopf">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="2"/>
 *             &lt;minLength value="2"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="fam_ruk" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="40"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="im_ruk" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="40"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="ot_ruk" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="40"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="phone">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="40"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="fax">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="40"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="e_mail">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="30"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="www">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="100"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="n_doc">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="20"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="d_start" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *       &lt;attribute name="data_e" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *       &lt;attribute name="org" use="required" type="{http://www.w3.org/2001/XMLSchema}long" />
 *       &lt;attribute name="d_begin" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *       &lt;attribute name="d_end" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *       &lt;attribute name="name_e">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="15"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="Nal_p">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="1"/>
 *             &lt;minLength value="1"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="DUVED" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *       &lt;attribute name="kol_zl" use="required" type="{http://www.w3.org/2001/XMLSchema}long" />
 *       &lt;attribute name="d_edit" use="required" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "F002Type")
public class F002Type {

    @XmlAttribute(name = "tf_okato", required = true)
    protected String tfOkato;
    @XmlAttribute(name = "smocod")
    protected String smocod;
    @XmlAttribute(name = "nam_smop", required = true)
    protected String namSmop;
    @XmlAttribute(name = "nam_smok")
    protected String namSmok;
    @XmlAttribute(name = "inn", required = true)
    protected String inn;
    @XmlAttribute(name = "Ogrn")
    protected String ogrn;
    @XmlAttribute(name = "KPP")
    protected String kpp;
    @XmlAttribute(name = "index_j")
    protected String indexJ;
    @XmlAttribute(name = "addr_j", required = true)
    protected String addrJ;
    @XmlAttribute(name = "index_f")
    protected String indexF;
    @XmlAttribute(name = "addr_f")
    protected String addrF;
    @XmlAttribute(name = "okopf")
    protected String okopf;
    @XmlAttribute(name = "fam_ruk", required = true)
    protected String famRuk;
    @XmlAttribute(name = "im_ruk", required = true)
    protected String imRuk;
    @XmlAttribute(name = "ot_ruk", required = true)
    protected String otRuk;
    @XmlAttribute(name = "phone")
    protected String phone;
    @XmlAttribute(name = "fax")
    protected String fax;
    @XmlAttribute(name = "e_mail")
    protected String eMail;
    @XmlAttribute(name = "www")
    protected String www;
    @XmlAttribute(name = "n_doc")
    protected String nDoc;
    @XmlAttribute(name = "d_start")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dStart;
    @XmlAttribute(name = "data_e")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataE;
    @XmlAttribute(name = "org", required = true)
    protected long org;
    @XmlAttribute(name = "d_begin")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dBegin;
    @XmlAttribute(name = "d_end")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dEnd;
    @XmlAttribute(name = "name_e")
    protected String nameE;
    @XmlAttribute(name = "Nal_p")
    protected String nalP;
    @XmlAttribute(name = "DUVED")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar duved;
    @XmlAttribute(name = "kol_zl", required = true)
    protected long kolZl;
    @XmlAttribute(name = "d_edit", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dEdit;

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
     * Gets the value of the smocod property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSmocod() {
        return smocod;
    }

    /**
     * Sets the value of the smocod property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSmocod(String value) {
        this.smocod = value;
    }

    /**
     * Gets the value of the namSmop property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNamSmop() {
        return namSmop;
    }

    /**
     * Sets the value of the namSmop property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNamSmop(String value) {
        this.namSmop = value;
    }

    /**
     * Gets the value of the namSmok property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNamSmok() {
        return namSmok;
    }

    /**
     * Sets the value of the namSmok property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNamSmok(String value) {
        this.namSmok = value;
    }

    /**
     * Gets the value of the inn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInn() {
        return inn;
    }

    /**
     * Sets the value of the inn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInn(String value) {
        this.inn = value;
    }

    /**
     * Gets the value of the ogrn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOgrn() {
        return ogrn;
    }

    /**
     * Sets the value of the ogrn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOgrn(String value) {
        this.ogrn = value;
    }

    /**
     * Gets the value of the kpp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKPP() {
        return kpp;
    }

    /**
     * Sets the value of the kpp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKPP(String value) {
        this.kpp = value;
    }

    /**
     * Gets the value of the indexJ property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIndexJ() {
        return indexJ;
    }

    /**
     * Sets the value of the indexJ property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIndexJ(String value) {
        this.indexJ = value;
    }

    /**
     * Gets the value of the addrJ property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddrJ() {
        return addrJ;
    }

    /**
     * Sets the value of the addrJ property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddrJ(String value) {
        this.addrJ = value;
    }

    /**
     * Gets the value of the indexF property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIndexF() {
        return indexF;
    }

    /**
     * Sets the value of the indexF property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIndexF(String value) {
        this.indexF = value;
    }

    /**
     * Gets the value of the addrF property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddrF() {
        return addrF;
    }

    /**
     * Sets the value of the addrF property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddrF(String value) {
        this.addrF = value;
    }

    /**
     * Gets the value of the okopf property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOkopf() {
        return okopf;
    }

    /**
     * Sets the value of the okopf property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOkopf(String value) {
        this.okopf = value;
    }

    /**
     * Gets the value of the famRuk property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFamRuk() {
        return famRuk;
    }

    /**
     * Sets the value of the famRuk property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFamRuk(String value) {
        this.famRuk = value;
    }

    /**
     * Gets the value of the imRuk property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImRuk() {
        return imRuk;
    }

    /**
     * Sets the value of the imRuk property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImRuk(String value) {
        this.imRuk = value;
    }

    /**
     * Gets the value of the otRuk property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOtRuk() {
        return otRuk;
    }

    /**
     * Sets the value of the otRuk property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOtRuk(String value) {
        this.otRuk = value;
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
     * Gets the value of the nDoc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNDoc() {
        return nDoc;
    }

    /**
     * Sets the value of the nDoc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNDoc(String value) {
        this.nDoc = value;
    }

    /**
     * Gets the value of the dStart property.
     * 
     * @return
     *     possible object is
     *     {@link javax.xml.datatype.XMLGregorianCalendar }
     *
     */
    public XMLGregorianCalendar getDStart() {
        return dStart;
    }

    /**
     * Sets the value of the dStart property.
     *
     * @param value
     *     allowed object is
     *     {@link javax.xml.datatype.XMLGregorianCalendar }
     *
     */
    public void setDStart(XMLGregorianCalendar value) {
        this.dStart = value;
    }

    /**
     * Gets the value of the dataE property.
     *
     * @return
     *     possible object is
     *     {@link javax.xml.datatype.XMLGregorianCalendar }
     *
     */
    public XMLGregorianCalendar getDataE() {
        return dataE;
    }

    /**
     * Sets the value of the dataE property.
     *
     * @param value
     *     allowed object is
     *     {@link javax.xml.datatype.XMLGregorianCalendar }
     *
     */
    public void setDataE(XMLGregorianCalendar value) {
        this.dataE = value;
    }

    /**
     * Gets the value of the org property.
     *
     */
    public long getOrg() {
        return org;
    }

    /**
     * Sets the value of the org property.
     *
     */
    public void setOrg(long value) {
        this.org = value;
    }

    /**
     * Gets the value of the dBegin property.
     *
     * @return
     *     possible object is
     *     {@link javax.xml.datatype.XMLGregorianCalendar }
     *
     */
    public XMLGregorianCalendar getDBegin() {
        return dBegin;
    }

    /**
     * Sets the value of the dBegin property.
     *
     * @param value
     *     allowed object is
     *     {@link javax.xml.datatype.XMLGregorianCalendar }
     *
     */
    public void setDBegin(XMLGregorianCalendar value) {
        this.dBegin = value;
    }

    /**
     * Gets the value of the dEnd property.
     *
     * @return
     *     possible object is
     *     {@link javax.xml.datatype.XMLGregorianCalendar }
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
     *     {@link javax.xml.datatype.XMLGregorianCalendar }
     *
     */
    public void setDEnd(XMLGregorianCalendar value) {
        this.dEnd = value;
    }

    /**
     * Gets the value of the nameE property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getNameE() {
        return nameE;
    }

    /**
     * Sets the value of the nameE property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setNameE(String value) {
        this.nameE = value;
    }

    /**
     * Gets the value of the nalP property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getNalP() {
        return nalP;
    }

    /**
     * Sets the value of the nalP property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setNalP(String value) {
        this.nalP = value;
    }

    /**
     * Gets the value of the duved property.
     *
     * @return
     *     possible object is
     *     {@link javax.xml.datatype.XMLGregorianCalendar }
     *
     */
    public XMLGregorianCalendar getDUVED() {
        return duved;
    }

    /**
     * Sets the value of the duved property.
     *
     * @param value
     *     allowed object is
     *     {@link javax.xml.datatype.XMLGregorianCalendar }
     *
     */
    public void setDUVED(XMLGregorianCalendar value) {
        this.duved = value;
    }

    /**
     * Gets the value of the kolZl property.
     *
     */
    public long getKolZl() {
        return kolZl;
    }

    /**
     * Sets the value of the kolZl property.
     *
     */
    public void setKolZl(long value) {
        this.kolZl = value;
    }

    /**
     * Gets the value of the dEdit property.
     *
     * @return
     *     possible object is
     *     {@link javax.xml.datatype.XMLGregorianCalendar }
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
     *     {@link javax.xml.datatype.XMLGregorianCalendar }
     *     
     */
    public void setDEdit(XMLGregorianCalendar value) {
        this.dEdit = value;
    }

}
