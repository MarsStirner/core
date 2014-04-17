
package ru.korus.tmis.ws.finance.odvd;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for RowTable complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RowTable">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="idService" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="endDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="codeOfService" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="nameOfService" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codeOfStruct" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="nameOfStruct" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="amount" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="isService" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RowTable", propOrder = {
    "idService",
    "endDate",
    "codeOfService",
    "nameOfService",
    "codeOfStruct",
    "nameOfStruct",
    "amount",
    "isService"
})
public class RowTable {

    @XmlElement(required = true, nillable = true)
    protected BigInteger idService;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar endDate;
    @XmlElement(required = true, nillable = true)
    protected String codeOfService;
    @XmlElement(required = true, nillable = true)
    protected String nameOfService;
    @XmlElement(required = true, nillable = true)
    protected BigInteger codeOfStruct;
    @XmlElement(required = true, nillable = true)
    protected String nameOfStruct;
    @XmlElement(required = true, nillable = true)
    protected BigInteger amount;
    @XmlElement(required = true, type = Boolean.class, nillable = true)
    protected Boolean isService;

    /**
     * Gets the value of the idService property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIdService() {
        return idService;
    }

    /**
     * Sets the value of the idService property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIdService(BigInteger value) {
        this.idService = value;
    }

    /**
     * Gets the value of the endDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEndDate() {
        return endDate;
    }

    /**
     * Sets the value of the endDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEndDate(XMLGregorianCalendar value) {
        this.endDate = value;
    }

    /**
     * Gets the value of the codeOfService property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodeOfService() {
        return codeOfService;
    }

    /**
     * Sets the value of the codeOfService property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodeOfService(String value) {
        this.codeOfService = value;
    }

    /**
     * Gets the value of the nameOfService property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNameOfService() {
        return nameOfService;
    }

    /**
     * Sets the value of the nameOfService property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNameOfService(String value) {
        this.nameOfService = value;
    }

    /**
     * Gets the value of the codeOfStruct property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getCodeOfStruct() {
        return codeOfStruct;
    }

    /**
     * Sets the value of the codeOfStruct property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setCodeOfStruct(BigInteger value) {
        this.codeOfStruct = value;
    }

    /**
     * Gets the value of the nameOfStruct property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNameOfStruct() {
        return nameOfStruct;
    }

    /**
     * Sets the value of the nameOfStruct property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNameOfStruct(String value) {
        this.nameOfStruct = value;
    }

    /**
     * Gets the value of the amount property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getAmount() {
        return amount;
    }

    /**
     * Sets the value of the amount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setAmount(BigInteger value) {
        this.amount = value;
    }

    /**
     * Gets the value of the isService property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsService() {
        return isService;
    }

    /**
     * Sets the value of the isService property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsService(Boolean value) {
        this.isService = value;
    }

}
