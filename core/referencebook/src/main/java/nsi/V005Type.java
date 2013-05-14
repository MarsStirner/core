
package nsi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * Классификатор пола застрахованного (V005Pol)
 * 
 * <p>Java class for V005Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="V005Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="IDPOL" use="required" type="{http://www.w3.org/2001/XMLSchema}long" />
 *       &lt;attribute name="POLNAME">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="7"/>
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
@XmlType(name = "V005Type")
public class V005Type {

    @XmlAttribute(name = "IDPOL", required = true)
    protected long idpol;
    @XmlAttribute(name = "POLNAME")
    protected String polname;

    /**
     * Gets the value of the idpol property.
     * 
     */
    public long getIDPOL() {
        return idpol;
    }

    /**
     * Sets the value of the idpol property.
     * 
     */
    public void setIDPOL(long value) {
        this.idpol = value;
    }

    /**
     * Gets the value of the polname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPOLNAME() {
        return polname;
    }

    /**
     * Sets the value of the polname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPOLNAME(String value) {
        this.polname = value;
    }

}
