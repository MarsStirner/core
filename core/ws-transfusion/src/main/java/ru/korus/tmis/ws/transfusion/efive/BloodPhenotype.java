
package ru.korus.tmis.ws.transfusion.efive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BloodPhenotype complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BloodPhenotype">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="phenotype" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BloodPhenotype", propOrder = {
    "phenotype",
    "value"
})
public class BloodPhenotype {

    protected String phenotype;
    protected Boolean value;

    /**
     * Gets the value of the phenotype property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPhenotype() {
        return phenotype;
    }

    /**
     * Sets the value of the phenotype property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPhenotype(String value) {
        this.phenotype = value;
    }

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setValue(Boolean value) {
        this.value = value;
    }

}
