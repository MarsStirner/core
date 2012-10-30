
package org.hl7.v3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for COCT_MT510000UV06.Limitation2 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="COCT_MT510000UV06.Limitation2">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="realmCode" type="{urn:hl7-org:v3}CS" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="typeId" type="{urn:hl7-org:v3}II" minOccurs="0"/>
 *         &lt;element name="templateId" type="{urn:hl7-org:v3}II" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="coverageCharge" type="{urn:hl7-org:v3}COCT_MT510000UV06.CoverageCharge" minOccurs="0"/>
 *         &lt;element name="financialParticipationCharge" type="{urn:hl7-org:v3}COCT_MT510000UV06.FinancialParticipationCharge" minOccurs="0"/>
 *       &lt;/choice>
 *       &lt;attribute name="nullFlavor" type="{urn:hl7-org:v3}NullFlavor" />
 *       &lt;attribute name="typeCode" use="required" type="{urn:hl7-org:v3}ActRelationshipLimitedBy" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "COCT_MT510000UV06.Limitation2", propOrder = {
    "realmCode",
    "typeId",
    "templateId",
    "coverageCharge",
    "financialParticipationCharge"
})
public class COCTMT510000UV06Limitation2 {

    protected List<CS> realmCode;
    protected II typeId;
    protected List<II> templateId;
    @XmlElementRef(name = "coverageCharge", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT510000UV06CoverageCharge> coverageCharge;
    @XmlElementRef(name = "financialParticipationCharge", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT510000UV06FinancialParticipationCharge> financialParticipationCharge;
    @XmlAttribute(name = "nullFlavor")
    protected NullFlavor nullFlavor;
    @XmlAttribute(name = "typeCode", required = true)
    protected ActRelationshipLimitedBy typeCode;

    /**
     * Gets the value of the realmCode property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the realmCode property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRealmCode().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CS }
     * 
     * 
     */
    public List<CS> getRealmCode() {
        if (realmCode == null) {
            realmCode = new ArrayList<CS>();
        }
        return this.realmCode;
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
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the templateId property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTemplateId().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link II }
     * 
     * 
     */
    public List<II> getTemplateId() {
        if (templateId == null) {
            templateId = new ArrayList<II>();
        }
        return this.templateId;
    }

    /**
     * Gets the value of the coverageCharge property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT510000UV06CoverageCharge }{@code >}
     *     
     */
    public JAXBElement<COCTMT510000UV06CoverageCharge> getCoverageCharge() {
        return coverageCharge;
    }

    /**
     * Sets the value of the coverageCharge property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT510000UV06CoverageCharge }{@code >}
     *     
     */
    public void setCoverageCharge(JAXBElement<COCTMT510000UV06CoverageCharge> value) {
        this.coverageCharge = value;
    }

    /**
     * Gets the value of the financialParticipationCharge property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT510000UV06FinancialParticipationCharge }{@code >}
     *     
     */
    public JAXBElement<COCTMT510000UV06FinancialParticipationCharge> getFinancialParticipationCharge() {
        return financialParticipationCharge;
    }

    /**
     * Sets the value of the financialParticipationCharge property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT510000UV06FinancialParticipationCharge }{@code >}
     *     
     */
    public void setFinancialParticipationCharge(JAXBElement<COCTMT510000UV06FinancialParticipationCharge> value) {
        this.financialParticipationCharge = value;
    }

    /**
     * Gets the value of the nullFlavor property.
     * 
     * @return
     *     possible object is
     *     {@link NullFlavor }
     *     
     */
    public NullFlavor getNullFlavor() {
        return nullFlavor;
    }

    /**
     * Sets the value of the nullFlavor property.
     * 
     * @param value
     *     allowed object is
     *     {@link NullFlavor }
     *     
     */
    public void setNullFlavor(NullFlavor value) {
        this.nullFlavor = value;
    }

    /**
     * Gets the value of the typeCode property.
     * 
     * @return
     *     possible object is
     *     {@link ActRelationshipLimitedBy }
     *     
     */
    public ActRelationshipLimitedBy getTypeCode() {
        return typeCode;
    }

    /**
     * Sets the value of the typeCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link ActRelationshipLimitedBy }
     *     
     */
    public void setTypeCode(ActRelationshipLimitedBy value) {
        this.typeCode = value;
    }

}
