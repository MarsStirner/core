
package ru.korus.tmis.pix.sda.ws;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DrugProduct complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DrugProduct">
 *   &lt;complexContent>
 *     &lt;extension base="{}CodeTableTranslated">
 *       &lt;sequence>
 *         &lt;element name="BaseQty" type="{}Numeric" minOccurs="0"/>
 *         &lt;element name="BaseUnits" type="{}UoM" minOccurs="0"/>
 *         &lt;element name="ProductName" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="220"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="OfficialCode" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="220"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="PackageSize" type="{}Numeric" minOccurs="0"/>
 *         &lt;element name="PackageSizeUnits" type="{}PackageSizeUoM" minOccurs="0"/>
 *         &lt;element name="Type" type="{}DrugProductType" minOccurs="0"/>
 *         &lt;element name="Form" type="{}DrugProductForm" minOccurs="0"/>
 *         &lt;element name="Identifier1" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="220"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Identifier2" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="220"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="ATCCode" type="{}ATCCode" minOccurs="0"/>
 *         &lt;element name="StrengthQty" type="{}Numeric" minOccurs="0"/>
 *         &lt;element name="StrengthUnits" type="{}UoM" minOccurs="0"/>
 *         &lt;element name="Strength" type="{}Strength" minOccurs="0"/>
 *         &lt;element name="Generic" type="{}Generic" minOccurs="0"/>
 *         &lt;element name="LabelName" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="220"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DrugProduct", propOrder = {
    "baseQty",
    "baseUnits",
    "productName",
    "officialCode",
    "packageSize",
    "packageSizeUnits",
    "type",
    "form",
    "identifier1",
    "identifier2",
    "atcCode",
    "strengthQty",
    "strengthUnits",
    "strength",
    "generic",
    "labelName"
})
public class DrugProduct
    extends CodeTableTranslated
{

    @XmlElement(name = "BaseQty")
    protected BigDecimal baseQty;
    @XmlElement(name = "BaseUnits")
    protected UoM baseUnits;
    @XmlElement(name = "ProductName")
    protected String productName;
    @XmlElement(name = "OfficialCode")
    protected String officialCode;
    @XmlElement(name = "PackageSize")
    protected BigDecimal packageSize;
    @XmlElement(name = "PackageSizeUnits")
    protected PackageSizeUoM packageSizeUnits;
    @XmlElement(name = "Type")
    protected DrugProductType type;
    @XmlElement(name = "Form")
    protected DrugProductForm form;
    @XmlElement(name = "Identifier1")
    protected String identifier1;
    @XmlElement(name = "Identifier2")
    protected String identifier2;
    @XmlElement(name = "ATCCode")
    protected ATCCode atcCode;
    @XmlElement(name = "StrengthQty")
    protected BigDecimal strengthQty;
    @XmlElement(name = "StrengthUnits")
    protected UoM strengthUnits;
    @XmlElement(name = "Strength")
    protected Strength strength;
    @XmlElement(name = "Generic")
    protected Generic generic;
    @XmlElement(name = "LabelName")
    protected String labelName;

    /**
     * Gets the value of the baseQty property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getBaseQty() {
        return baseQty;
    }

    /**
     * Sets the value of the baseQty property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setBaseQty(BigDecimal value) {
        this.baseQty = value;
    }

    /**
     * Gets the value of the baseUnits property.
     * 
     * @return
     *     possible object is
     *     {@link UoM }
     *     
     */
    public UoM getBaseUnits() {
        return baseUnits;
    }

    /**
     * Sets the value of the baseUnits property.
     * 
     * @param value
     *     allowed object is
     *     {@link UoM }
     *     
     */
    public void setBaseUnits(UoM value) {
        this.baseUnits = value;
    }

    /**
     * Gets the value of the productName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductName() {
        return productName;
    }

    /**
     * Sets the value of the productName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductName(String value) {
        this.productName = value;
    }

    /**
     * Gets the value of the officialCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOfficialCode() {
        return officialCode;
    }

    /**
     * Sets the value of the officialCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOfficialCode(String value) {
        this.officialCode = value;
    }

    /**
     * Gets the value of the packageSize property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPackageSize() {
        return packageSize;
    }

    /**
     * Sets the value of the packageSize property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPackageSize(BigDecimal value) {
        this.packageSize = value;
    }

    /**
     * Gets the value of the packageSizeUnits property.
     * 
     * @return
     *     possible object is
     *     {@link PackageSizeUoM }
     *     
     */
    public PackageSizeUoM getPackageSizeUnits() {
        return packageSizeUnits;
    }

    /**
     * Sets the value of the packageSizeUnits property.
     * 
     * @param value
     *     allowed object is
     *     {@link PackageSizeUoM }
     *     
     */
    public void setPackageSizeUnits(PackageSizeUoM value) {
        this.packageSizeUnits = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link DrugProductType }
     *     
     */
    public DrugProductType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link DrugProductType }
     *     
     */
    public void setType(DrugProductType value) {
        this.type = value;
    }

    /**
     * Gets the value of the form property.
     * 
     * @return
     *     possible object is
     *     {@link DrugProductForm }
     *     
     */
    public DrugProductForm getForm() {
        return form;
    }

    /**
     * Sets the value of the form property.
     * 
     * @param value
     *     allowed object is
     *     {@link DrugProductForm }
     *     
     */
    public void setForm(DrugProductForm value) {
        this.form = value;
    }

    /**
     * Gets the value of the identifier1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentifier1() {
        return identifier1;
    }

    /**
     * Sets the value of the identifier1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentifier1(String value) {
        this.identifier1 = value;
    }

    /**
     * Gets the value of the identifier2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentifier2() {
        return identifier2;
    }

    /**
     * Sets the value of the identifier2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentifier2(String value) {
        this.identifier2 = value;
    }

    /**
     * Gets the value of the atcCode property.
     * 
     * @return
     *     possible object is
     *     {@link ATCCode }
     *     
     */
    public ATCCode getATCCode() {
        return atcCode;
    }

    /**
     * Sets the value of the atcCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link ATCCode }
     *     
     */
    public void setATCCode(ATCCode value) {
        this.atcCode = value;
    }

    /**
     * Gets the value of the strengthQty property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getStrengthQty() {
        return strengthQty;
    }

    /**
     * Sets the value of the strengthQty property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setStrengthQty(BigDecimal value) {
        this.strengthQty = value;
    }

    /**
     * Gets the value of the strengthUnits property.
     * 
     * @return
     *     possible object is
     *     {@link UoM }
     *     
     */
    public UoM getStrengthUnits() {
        return strengthUnits;
    }

    /**
     * Sets the value of the strengthUnits property.
     * 
     * @param value
     *     allowed object is
     *     {@link UoM }
     *     
     */
    public void setStrengthUnits(UoM value) {
        this.strengthUnits = value;
    }

    /**
     * Gets the value of the strength property.
     * 
     * @return
     *     possible object is
     *     {@link Strength }
     *     
     */
    public Strength getStrength() {
        return strength;
    }

    /**
     * Sets the value of the strength property.
     * 
     * @param value
     *     allowed object is
     *     {@link Strength }
     *     
     */
    public void setStrength(Strength value) {
        this.strength = value;
    }

    /**
     * Gets the value of the generic property.
     * 
     * @return
     *     possible object is
     *     {@link Generic }
     *     
     */
    public Generic getGeneric() {
        return generic;
    }

    /**
     * Sets the value of the generic property.
     * 
     * @param value
     *     allowed object is
     *     {@link Generic }
     *     
     */
    public void setGeneric(Generic value) {
        this.generic = value;
    }

    /**
     * Gets the value of the labelName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLabelName() {
        return labelName;
    }

    /**
     * Sets the value of the labelName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLabelName(String value) {
        this.labelName = value;
    }

}
