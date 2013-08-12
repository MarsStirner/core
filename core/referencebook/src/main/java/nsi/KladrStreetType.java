
package nsi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for KladrStreetType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="KladrStreetType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="name" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="40"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="socr" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="10"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="code" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="17"/>
 *             &lt;minLength value="17"/>
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
 *       &lt;attribute name="gninmb">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="4"/>
 *             &lt;minLength value="4"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="uno">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="4"/>
 *             &lt;minLength value="4"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="ocatd">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="11"/>
 *             &lt;minLength value="11"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "KladrStreetType")
public class KladrStreetType {

    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "socr", required = true)
    protected String socr;
    @XmlAttribute(name = "code", required = true)
    protected String code;
    @XmlAttribute(name = "index")
    protected String index;
    @XmlAttribute(name = "gninmb")
    protected String gninmb;
    @XmlAttribute(name = "uno")
    protected String uno;
    @XmlAttribute(name = "ocatd")
    protected String ocatd;

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the socr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSocr() {
        return socr;
    }

    /**
     * Sets the value of the socr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSocr(String value) {
        this.socr = value;
    }

    /**
     * Gets the value of the code property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the value of the code property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCode(String value) {
        this.code = value;
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
     * Gets the value of the gninmb property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGninmb() {
        return gninmb;
    }

    /**
     * Sets the value of the gninmb property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGninmb(String value) {
        this.gninmb = value;
    }

    /**
     * Gets the value of the uno property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUno() {
        return uno;
    }

    /**
     * Sets the value of the uno property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUno(String value) {
        this.uno = value;
    }

    /**
     * Gets the value of the ocatd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOcatd() {
        return ocatd;
    }

    /**
     * Sets the value of the ocatd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOcatd(String value) {
        this.ocatd = value;
    }

}
