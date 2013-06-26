
package nsi;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Единый реестр медицинских организаций, осуществляющих деятельность в сфере обязательного медицинского страхования (MO)
 * 
 * <p>Java class for F003Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="F003Type">
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
 *       &lt;attribute name="mcod" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="6"/>
 *             &lt;minLength value="6"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="nam_mop" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="250"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="nam_mok" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="250"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="inn">
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
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="addr_j">
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
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="vedpri" use="required" type="{http://www.w3.org/2001/XMLSchema}long" />
 *       &lt;attribute name="org" use="required" type="{http://www.w3.org/2001/XMLSchema}long" />
 *       &lt;attribute name="fam_ruk">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="40"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="im_ruk">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="40"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="ot_ruk">
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
 *             &lt;maxLength value="50"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="n_doc">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="30"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="d_start" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *       &lt;attribute name="data_e" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *       &lt;attribute name="mp">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="15"/>
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
 *       &lt;attribute name="d_begin" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *       &lt;attribute name="d_end" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *       &lt;attribute name="name_e">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="10"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="DUVED" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *       &lt;attribute name="d_edit" use="required" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "F003Type")
public class F003Type {

    @XmlAttribute(name = "tf_okato", required = true)
    protected String tfOkato;
    @XmlAttribute(name = "mcod", required = true)
    protected String mcod;
    @XmlAttribute(name = "nam_mop", required = true)
    protected String namMop;
    @XmlAttribute(name = "nam_mok", required = true)
    protected String namMok;
    @XmlAttribute(name = "inn")
    protected String inn;
    @XmlAttribute(name = "Ogrn")
    protected String ogrn;
    @XmlAttribute(name = "KPP")
    protected String kpp;
    @XmlAttribute(name = "index_j")
    protected String indexJ;
    @XmlAttribute(name = "addr_j")
    protected String addrJ;
    @XmlAttribute(name = "okopf")
    protected String okopf;
    @XmlAttribute(name = "vedpri", required = true)
    protected long vedpri;
    @XmlAttribute(name = "org", required = true)
    protected long org;
    @XmlAttribute(name = "fam_ruk")
    protected String famRuk;
    @XmlAttribute(name = "im_ruk")
    protected String imRuk;
    @XmlAttribute(name = "ot_ruk")
    protected String otRuk;
    @XmlAttribute(name = "phone")
    protected String phone;
    @XmlAttribute(name = "fax")
    protected String fax;
    @XmlAttribute(name = "e_mail")
    protected String eMail;
    @XmlAttribute(name = "n_doc")
    protected String nDoc;
    @XmlAttribute(name = "d_start")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dStart;
    @XmlAttribute(name = "data_e")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataE;
    @XmlAttribute(name = "mp")
    protected String mp;
    @XmlAttribute(name = "www")
    protected String www;
    @XmlAttribute(name = "d_begin")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dBegin;
    @XmlAttribute(name = "d_end")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dEnd;
    @XmlAttribute(name = "name_e")
    protected String nameE;
    @XmlAttribute(name = "DUVED")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar duved;
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
     * Gets the value of the mcod property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMcod() {
        return mcod;
    }

    /**
     * Sets the value of the mcod property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMcod(String value) {
        this.mcod = value;
    }

    /**
     * Gets the value of the namMop property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNamMop() {
        return namMop;
    }

    /**
     * Sets the value of the namMop property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNamMop(String value) {
        this.namMop = value;
    }

    /**
     * Gets the value of the namMok property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNamMok() {
        return namMok;
    }

    /**
     * Sets the value of the namMok property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNamMok(String value) {
        this.namMok = value;
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
     * Gets the value of the vedpri property.
     * 
     */
    public long getVedpri() {
        return vedpri;
    }

    /**
     * Sets the value of the vedpri property.
     * 
     */
    public void setVedpri(long value) {
        this.vedpri = value;
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
     * Gets the value of the mp property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getMp() {
        return mp;
    }

    /**
     * Sets the value of the mp property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setMp(String value) {
        this.mp = value;
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
