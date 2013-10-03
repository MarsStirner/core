
package ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for COCT_MT440001UV09.ValuedItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="COCT_MT440001UV09.ValuedItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;group ref="{urn:hl7-org:v3}InfrastructureRootElements"/>
 *         &lt;element name="id" type="{urn:hl7-org:v3}DSET_II"/>
 *         &lt;element name="code" type="{urn:hl7-org:v3}CD" minOccurs="0"/>
 *         &lt;element name="effectiveTime" type="{urn:hl7-org:v3}IVL_TS" minOccurs="0"/>
 *         &lt;element name="unitQuantity" type="{urn:hl7-org:v3}RTO" minOccurs="0"/>
 *         &lt;element name="unitPriceAmt" type="{urn:hl7-org:v3}RTO"/>
 *         &lt;element name="netAmt" type="{urn:hl7-org:v3}MO" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{urn:hl7-org:v3}InfrastructureRootAttributes"/>
 *       &lt;attribute name="classCode" use="required" type="{urn:hl7-org:v3}ActClassInvoiceElement" />
 *       &lt;attribute name="moodCode" use="required" type="{urn:hl7-org:v3}x_ActMoodDefEvn" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "COCT_MT440001UV09.ValuedItem", propOrder = {
    "realmCode",
    "typeId",
    "templateId",
    "id",
    "code",
    "effectiveTime",
    "unitQuantity",
    "unitPriceAmt",
    "netAmt"
})
public class COCTMT440001UV09ValuedItem {

    protected DSETCS realmCode;
    protected II typeId;
    protected LISTII templateId;
    @XmlElement(required = true)
    protected DSETII id;
    protected CD code;
    protected IVLTS effectiveTime;
    protected RTO unitQuantity;
    @XmlElement(required = true)
    protected RTO unitPriceAmt;
    protected MO netAmt;
    @XmlAttribute(name = "classCode", required = true)
    protected ActClassInvoiceElement classCode;
    @XmlAttribute(name = "moodCode", required = true)
    protected XActMoodDefEvn moodCode;

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
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link DSETII }
     *     
     */
    public DSETII getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link DSETII }
     *     
     */
    public void setId(DSETII value) {
        this.id = value;
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
     * Gets the value of the effectiveTime property.
     * 
     * @return
     *     possible object is
     *     {@link IVLTS }
     *     
     */
    public IVLTS getEffectiveTime() {
        return effectiveTime;
    }

    /**
     * Sets the value of the effectiveTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link IVLTS }
     *     
     */
    public void setEffectiveTime(IVLTS value) {
        this.effectiveTime = value;
    }

    /**
     * Gets the value of the unitQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link RTO }
     *     
     */
    public RTO getUnitQuantity() {
        return unitQuantity;
    }

    /**
     * Sets the value of the unitQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link RTO }
     *     
     */
    public void setUnitQuantity(RTO value) {
        this.unitQuantity = value;
    }

    /**
     * Gets the value of the unitPriceAmt property.
     * 
     * @return
     *     possible object is
     *     {@link RTO }
     *     
     */
    public RTO getUnitPriceAmt() {
        return unitPriceAmt;
    }

    /**
     * Sets the value of the unitPriceAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link RTO }
     *     
     */
    public void setUnitPriceAmt(RTO value) {
        this.unitPriceAmt = value;
    }

    /**
     * Gets the value of the netAmt property.
     * 
     * @return
     *     possible object is
     *     {@link MO }
     *     
     */
    public MO getNetAmt() {
        return netAmt;
    }

    /**
     * Sets the value of the netAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link MO }
     *     
     */
    public void setNetAmt(MO value) {
        this.netAmt = value;
    }

    /**
     * Gets the value of the classCode property.
     * 
     * @return
     *     possible object is
     *     {@link ActClassInvoiceElement }
     *     
     */
    public ActClassInvoiceElement getClassCode() {
        return classCode;
    }

    /**
     * Sets the value of the classCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link ActClassInvoiceElement }
     *     
     */
    public void setClassCode(ActClassInvoiceElement value) {
        this.classCode = value;
    }

    /**
     * Gets the value of the moodCode property.
     * 
     * @return
     *     possible object is
     *     {@link XActMoodDefEvn }
     *     
     */
    public XActMoodDefEvn getMoodCode() {
        return moodCode;
    }

    /**
     * Sets the value of the moodCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link XActMoodDefEvn }
     *     
     */
    public void setMoodCode(XActMoodDefEvn value) {
        this.moodCode = value;
    }

}
