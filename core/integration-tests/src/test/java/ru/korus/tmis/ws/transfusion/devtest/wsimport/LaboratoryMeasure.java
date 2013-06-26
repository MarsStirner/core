package ru.korus.tmis.ws.transfusion.devtest.wsimport;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for laboratoryMeasure complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="laboratoryMeasure">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="afterOperation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="beforeOperation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="duringOperation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="inProduct" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "laboratoryMeasure", propOrder = { "afterOperation", "beforeOperation", "duringOperation", "id", "inProduct" })
public class LaboratoryMeasure {

    protected String afterOperation;
    protected String beforeOperation;
    protected String duringOperation;
    protected Integer id;
    protected String inProduct;

    /**
     * Gets the value of the afterOperation property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getAfterOperation() {
        return afterOperation;
    }

    /**
     * Sets the value of the afterOperation property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setAfterOperation(final String value) {
        this.afterOperation = value;
    }

    /**
     * Gets the value of the beforeOperation property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getBeforeOperation() {
        return beforeOperation;
    }

    /**
     * Sets the value of the beforeOperation property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setBeforeOperation(final String value) {
        this.beforeOperation = value;
    }

    /**
     * Gets the value of the duringOperation property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getDuringOperation() {
        return duringOperation;
    }

    /**
     * Sets the value of the duringOperation property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setDuringOperation(final String value) {
        this.duringOperation = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return possible object is {@link Integer }
     * 
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *            allowed object is {@link Integer }
     * 
     */
    public void setId(final Integer value) {
        this.id = value;
    }

    /**
     * Gets the value of the inProduct property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getInProduct() {
        return inProduct;
    }

    /**
     * Sets the value of the inProduct property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setInProduct(final String value) {
        this.inProduct = value;
    }

}
