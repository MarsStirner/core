
package ru.korus.tmis.laboratory.altey.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for OrderInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrderInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="diagnosticCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="diagnosticName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="orderPriority" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="indicators" type="{http://www.korusconsulting.ru}IndicatorMetodic" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrderInfo", propOrder = {
    "diagnosticCode",
    "diagnosticName",
    "orderPriority",
    "indicators"
})
public class OrderInfo {

    protected String diagnosticCode;
    protected String diagnosticName;
    protected Integer orderPriority;
    protected List<IndicatorMetodic> indicators;

    /**
     * Gets the value of the diagnosticCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDiagnosticCode() {
        return diagnosticCode;
    }

    /**
     * Sets the value of the diagnosticCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDiagnosticCode(String value) {
        this.diagnosticCode = value;
    }

    /**
     * Gets the value of the diagnosticName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDiagnosticName() {
        return diagnosticName;
    }

    /**
     * Sets the value of the diagnosticName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDiagnosticName(String value) {
        this.diagnosticName = value;
    }

    /**
     * Gets the value of the orderPriority property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getOrderPriority() {
        return orderPriority;
    }

    /**
     * Sets the value of the orderPriority property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setOrderPriority(Integer value) {
        this.orderPriority = value;
    }

    /**
     * Gets the value of the indicators property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the indicators property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIndicators().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IndicatorMetodic }
     * 
     * 
     */
    public List<IndicatorMetodic> getIndicators() {
        if (indicators == null) {
            indicators = new ArrayList<IndicatorMetodic>();
        }
        return this.indicators;
    }

}
