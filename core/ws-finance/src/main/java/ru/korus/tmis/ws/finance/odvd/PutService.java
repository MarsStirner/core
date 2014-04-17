
package ru.korus.tmis.ws.finance.odvd;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="idTreatment" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="listServiceComplete" type="{http://localhost/Policlinic}Table"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "idTreatment",
    "listServiceComplete"
})
@XmlRootElement(name = "PutService")
public class PutService {

    @XmlElement(required = true)
    protected BigInteger idTreatment;
    @XmlElement(required = true)
    protected Table listServiceComplete;

    /**
     * Gets the value of the idTreatment property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIdTreatment() {
        return idTreatment;
    }

    /**
     * Sets the value of the idTreatment property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIdTreatment(BigInteger value) {
        this.idTreatment = value;
    }

    /**
     * Gets the value of the listServiceComplete property.
     * 
     * @return
     *     possible object is
     *     {@link Table }
     *     
     */
    public Table getListServiceComplete() {
        return listServiceComplete;
    }

    /**
     * Sets the value of the listServiceComplete property.
     * 
     * @param value
     *     allowed object is
     *     {@link Table }
     *     
     */
    public void setListServiceComplete(Table value) {
        this.listServiceComplete = value;
    }

}
