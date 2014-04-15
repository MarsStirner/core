
package ru.korus.tmis.pix.sda.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for Insurance complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Insurance">
 *   &lt;complexContent>
 *     &lt;extension base="{http://schemas.intersystems.ru/hs/ehr/v1}BaseSerial">
 *       &lt;sequence>
 *         &lt;element name="insuranceCompany" type="{http://schemas.intersystems.ru/hs/ehr/v1}CodeAndName" minOccurs="0"/>
 *         &lt;element name="insuranceCompanyOkato" type="{http://schemas.intersystems.ru/hs/ehr/v1}String" minOccurs="0"/>
 *         &lt;element name="policyType" type="{http://schemas.intersystems.ru/hs/ehr/v1}CodeAndName" minOccurs="0"/>
 *         &lt;element name="policySer" type="{http://schemas.intersystems.ru/hs/ehr/v1}String" minOccurs="0"/>
 *         &lt;element name="policyNum" type="{http://schemas.intersystems.ru/hs/ehr/v1}String" minOccurs="0"/>
 *         &lt;element name="effectiveDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="expirationDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="insuranceCompanyKladr" type="{http://schemas.intersystems.ru/hs/ehr/v1}CodeAndName" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Insurance", propOrder = {
    "insuranceCompany",
    "insuranceCompanyOkato",
    "policyType",
    "policySer",
    "policyNum",
    "effectiveDate",
    "expirationDate",
    "insuranceCompanyKladr"
})
public class Insurance
    extends BaseSerial
{

    protected CodeAndName insuranceCompany;
    protected String insuranceCompanyOkato;
    protected CodeAndName policyType;
    protected String policySer;
    protected String policyNum;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar effectiveDate;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar expirationDate;
    protected CodeAndName insuranceCompanyKladr;

    /**
     * Gets the value of the insuranceCompany property.
     * 
     * @return
     *     possible object is
     *     {@link CodeAndName }
     *     
     */
    public CodeAndName getInsuranceCompany() {
        return insuranceCompany;
    }

    /**
     * Sets the value of the insuranceCompany property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodeAndName }
     *     
     */
    public void setInsuranceCompany(CodeAndName value) {
        this.insuranceCompany = value;
    }

    /**
     * Gets the value of the insuranceCompanyOkato property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInsuranceCompanyOkato() {
        return insuranceCompanyOkato;
    }

    /**
     * Sets the value of the insuranceCompanyOkato property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInsuranceCompanyOkato(String value) {
        this.insuranceCompanyOkato = value;
    }

    /**
     * Gets the value of the policyType property.
     * 
     * @return
     *     possible object is
     *     {@link CodeAndName }
     *     
     */
    public CodeAndName getPolicyType() {
        return policyType;
    }

    /**
     * Sets the value of the policyType property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodeAndName }
     *     
     */
    public void setPolicyType(CodeAndName value) {
        this.policyType = value;
    }

    /**
     * Gets the value of the policySer property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPolicySer() {
        return policySer;
    }

    /**
     * Sets the value of the policySer property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPolicySer(String value) {
        this.policySer = value;
    }

    /**
     * Gets the value of the policyNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPolicyNum() {
        return policyNum;
    }

    /**
     * Sets the value of the policyNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPolicyNum(String value) {
        this.policyNum = value;
    }

    /**
     * Gets the value of the effectiveDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEffectiveDate() {
        return effectiveDate;
    }

    /**
     * Sets the value of the effectiveDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEffectiveDate(XMLGregorianCalendar value) {
        this.effectiveDate = value;
    }

    /**
     * Gets the value of the expirationDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getExpirationDate() {
        return expirationDate;
    }

    /**
     * Sets the value of the expirationDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setExpirationDate(XMLGregorianCalendar value) {
        this.expirationDate = value;
    }

    /**
     * Gets the value of the insuranceCompanyKladr property.
     * 
     * @return
     *     possible object is
     *     {@link CodeAndName }
     *     
     */
    public CodeAndName getInsuranceCompanyKladr() {
        return insuranceCompanyKladr;
    }

    /**
     * Sets the value of the insuranceCompanyKladr property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodeAndName }
     *     
     */
    public void setInsuranceCompanyKladr(CodeAndName value) {
        this.insuranceCompanyKladr = value;
    }

}
