
package ru.korus.tmis.pix.sda.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CodeTableTranslated complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CodeTableTranslated">
 *   &lt;complexContent>
 *     &lt;extension base="{}CodeTableDetail">
 *       &lt;sequence>
 *         &lt;element name="OriginalText" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="32000"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="PriorCodes" type="{}ArrayOfPriorCodePriorCode" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CodeTableTranslated", propOrder = {
    "originalText",
    "priorCodes"
})
@XmlSeeAlso({
    DiagnosisCode.class,
    LabTestItem.class,
    Severity.class,
    DiagnosisType.class,
    AllergyCategory.class,
    AllergyCode.class,
    Order.class,
    ObservationCode.class,
    DocumentType.class,
    ProcedureCode.class,
    DrugProduct.class
})
public class CodeTableTranslated
    extends CodeTableDetail
{

    @XmlElement(name = "OriginalText")
    protected String originalText;
    @XmlElement(name = "PriorCodes")
    protected ArrayOfPriorCodePriorCode priorCodes;

    /**
     * Gets the value of the originalText property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOriginalText() {
        return originalText;
    }

    /**
     * Sets the value of the originalText property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOriginalText(String value) {
        this.originalText = value;
    }

    /**
     * Gets the value of the priorCodes property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfPriorCodePriorCode }
     *     
     */
    public ArrayOfPriorCodePriorCode getPriorCodes() {
        return priorCodes;
    }

    /**
     * Sets the value of the priorCodes property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfPriorCodePriorCode }
     *     
     */
    public void setPriorCodes(ArrayOfPriorCodePriorCode value) {
        this.priorCodes = value;
    }

}
