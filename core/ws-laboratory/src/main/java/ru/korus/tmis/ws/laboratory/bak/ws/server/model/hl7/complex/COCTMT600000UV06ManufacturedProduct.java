
package ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for COCT_MT600000UV06.ManufacturedProduct complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="COCT_MT600000UV06.ManufacturedProduct">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;group ref="{urn:hl7-org:v3}InfrastructureRootElements"/>
 *         &lt;element name="code" type="{urn:hl7-org:v3}CD" minOccurs="0"/>
 *         &lt;element name="manufacturedVisionProduct" type="{urn:hl7-org:v3}COCT_MT600000UV06.VisionProduct" minOccurs="0"/>
 *         &lt;element name="productOf" type="{urn:hl7-org:v3}COCT_MT600000UV06.Product2" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{urn:hl7-org:v3}InfrastructureRootAttributes"/>
 *       &lt;attribute name="classCode" use="required" type="{urn:hl7-org:v3}RoleClassManufacturedProduct" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "COCT_MT600000UV06.ManufacturedProduct", propOrder = {
    "realmCode",
    "typeId",
    "templateId",
    "code",
    "manufacturedVisionProduct",
    "productOf"
})
public class COCTMT600000UV06ManufacturedProduct {

    protected DSETCS realmCode;
    protected II typeId;
    protected LISTII templateId;
    protected CD code;
    @XmlElementRef(name = "manufacturedVisionProduct", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT600000UV06VisionProduct> manufacturedVisionProduct;
    @XmlElement(nillable = true)
    protected List<COCTMT600000UV06Product2> productOf;
    @XmlAttribute(name = "classCode", required = true)
    protected RoleClassManufacturedProduct classCode;

    /**
     * Gets the value of the realmCode property.
     * 
     * @return
     *     possible object is
     *     {@link DSETCS }
     *     
     */
    public DSETCS getRealmCode() {
        return realmCode;
    }

    /**
     * Sets the value of the realmCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link DSETCS }
     *     
     */
    public void setRealmCode(DSETCS value) {
        this.realmCode = value;
    }

    /**
     * Gets the value of the typeId property.
     * 
     * @return
     *     possible object is
     *     {@link II }
     *     
     */
    public II getTypeId() {
        return typeId;
    }

    /**
     * Sets the value of the typeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link II }
     *     
     */
    public void setTypeId(II value) {
        this.typeId = value;
    }

    /**
     * Gets the value of the templateId property.
     * 
     * @return
     *     possible object is
     *     {@link LISTII }
     *     
     */
    public LISTII getTemplateId() {
        return templateId;
    }

    /**
     * Sets the value of the templateId property.
     * 
     * @param value
     *     allowed object is
     *     {@link LISTII }
     *     
     */
    public void setTemplateId(LISTII value) {
        this.templateId = value;
    }

    /**
     * Gets the value of the code property.
     * 
     * @return
     *     possible object is
     *     {@link CD }
     *     
     */
    public CD getCode() {
        return code;
    }

    /**
     * Sets the value of the code property.
     * 
     * @param value
     *     allowed object is
     *     {@link CD }
     *     
     */
    public void setCode(CD value) {
        this.code = value;
    }

    /**
     * Gets the value of the manufacturedVisionProduct property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT600000UV06VisionProduct }{@code >}
     *     
     */
    public JAXBElement<COCTMT600000UV06VisionProduct> getManufacturedVisionProduct() {
        return manufacturedVisionProduct;
    }

    /**
     * Sets the value of the manufacturedVisionProduct property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT600000UV06VisionProduct }{@code >}
     *     
     */
    public void setManufacturedVisionProduct(JAXBElement<COCTMT600000UV06VisionProduct> value) {
        this.manufacturedVisionProduct = value;
    }

    /**
     * Gets the value of the productOf property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the productOf property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProductOf().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link COCTMT600000UV06Product2 }
     * 
     * 
     */
    public List<COCTMT600000UV06Product2> getProductOf() {
        if (productOf == null) {
            productOf = new ArrayList<COCTMT600000UV06Product2>();
        }
        return this.productOf;
    }

    /**
     * Gets the value of the classCode property.
     * 
     * @return
     *     possible object is
     *     {@link RoleClassManufacturedProduct }
     *     
     */
    public RoleClassManufacturedProduct getClassCode() {
        return classCode;
    }

    /**
     * Sets the value of the classCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link RoleClassManufacturedProduct }
     *     
     */
    public void setClassCode(RoleClassManufacturedProduct value) {
        this.classCode = value;
    }

}
