
package ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for QTY complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="QTY">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:hl7-org:v3}ANY">
 *       &lt;sequence>
 *         &lt;element name="expression" type="{urn:hl7-org:v3}ED" minOccurs="0"/>
 *         &lt;element name="originalText" type="{urn:hl7-org:v3}ED" minOccurs="0"/>
 *         &lt;element name="uncertainty" type="{urn:hl7-org:v3}QTY" minOccurs="0"/>
 *         &lt;element name="uncertainRange" type="{urn:hl7-org:v3}IVL_QTY" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="uncertaintyType" type="{urn:hl7-org:v3}UncertaintyType" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QTY", propOrder = {
    "expression",
    "originalText",
    "uncertainty",
    "uncertainRange"
})
@XmlSeeAlso({
    TS.class,
    INT.class,
    CO.class,
    MO.class,
    REAL.class,
    PQ.class,
    RTO.class
})
public abstract class QTY
    extends ANY
{

    protected ED expression;
    protected ED originalText;
    protected QTY uncertainty;
    protected IVLQTY uncertainRange;
    @XmlAttribute(name = "uncertaintyType")
    protected UncertaintyType uncertaintyType;

    /**
     * Gets the value of the expression property.
     * 
     * @return
     *     possible object is
     *     {@link ED }
     *     
     */
    public ED getExpression() {
        return expression;
    }

    /**
     * Sets the value of the expression property.
     * 
     * @param value
     *     allowed object is
     *     {@link ED }
     *     
     */
    public void setExpression(ED value) {
        this.expression = value;
    }

    /**
     * Gets the value of the originalText property.
     * 
     * @return
     *     possible object is
     *     {@link ED }
     *     
     */
    public ED getOriginalText() {
        return originalText;
    }

    /**
     * Sets the value of the originalText property.
     * 
     * @param value
     *     allowed object is
     *     {@link ED }
     *     
     */
    public void setOriginalText(ED value) {
        this.originalText = value;
    }

    /**
     * Gets the value of the uncertainty property.
     * 
     * @return
     *     possible object is
     *     {@link QTY }
     *     
     */
    public QTY getUncertainty() {
        return uncertainty;
    }

    /**
     * Sets the value of the uncertainty property.
     * 
     * @param value
     *     allowed object is
     *     {@link QTY }
     *     
     */
    public void setUncertainty(QTY value) {
        this.uncertainty = value;
    }

    /**
     * Gets the value of the uncertainRange property.
     * 
     * @return
     *     possible object is
     *     {@link IVLQTY }
     *     
     */
    public IVLQTY getUncertainRange() {
        return uncertainRange;
    }

    /**
     * Sets the value of the uncertainRange property.
     * 
     * @param value
     *     allowed object is
     *     {@link IVLQTY }
     *     
     */
    public void setUncertainRange(IVLQTY value) {
        this.uncertainRange = value;
    }

    /**
     * Gets the value of the uncertaintyType property.
     * 
     * @return
     *     possible object is
     *     {@link UncertaintyType }
     *     
     */
    public UncertaintyType getUncertaintyType() {
        return uncertaintyType;
    }

    /**
     * Sets the value of the uncertaintyType property.
     * 
     * @param value
     *     allowed object is
     *     {@link UncertaintyType }
     *     
     */
    public void setUncertaintyType(UncertaintyType value) {
        this.uncertaintyType = value;
    }

}
