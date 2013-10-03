
package ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MCCI_MT000200UV01.Acknowledgement complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MCCI_MT000200UV01.Acknowledgement">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;group ref="{urn:hl7-org:v3}InfrastructureRootElements"/>
 *         &lt;element name="expectedSequenceNumber" type="{urn:hl7-org:v3}INT" minOccurs="0"/>
 *         &lt;element name="messageWaitingNumber" type="{urn:hl7-org:v3}INT" minOccurs="0"/>
 *         &lt;element name="messageWaitingPriorityCode" type="{urn:hl7-org:v3}CD" minOccurs="0"/>
 *         &lt;element name="targetMessage" type="{urn:hl7-org:v3}MCCI_MT000200UV01.TargetMessage"/>
 *         &lt;element name="acknowledgementDetail" type="{urn:hl7-org:v3}MCCI_MT000200UV01.AcknowledgementDetail" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{urn:hl7-org:v3}InfrastructureRootAttributes"/>
 *       &lt;attribute name="nullFlavor" type="{urn:hl7-org:v3}NullFlavor" />
 *       &lt;attribute name="typeCode" use="required" type="{urn:hl7-org:v3}AcknowledgementType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MCCI_MT000200UV01.Acknowledgement", propOrder = {
    "realmCode",
    "typeId",
    "templateId",
    "expectedSequenceNumber",
    "messageWaitingNumber",
    "messageWaitingPriorityCode",
    "targetMessage",
    "acknowledgementDetail"
})
public class MCCIMT000200UV01Acknowledgement {

    protected DSETCS realmCode;
    protected II typeId;
    protected LISTII templateId;
    protected INT expectedSequenceNumber;
    protected INT messageWaitingNumber;
    protected CD messageWaitingPriorityCode;
    @XmlElement(required = true, nillable = true)
    protected MCCIMT000200UV01TargetMessage targetMessage;
    @XmlElement(nillable = true)
    protected List<MCCIMT000200UV01AcknowledgementDetail> acknowledgementDetail;
    @XmlAttribute(name = "nullFlavor")
    protected NullFlavor nullFlavor;
    @XmlAttribute(name = "typeCode", required = true)
    protected AcknowledgementType typeCode;

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
     * Gets the value of the expectedSequenceNumber property.
     * 
     * @return
     *     possible object is
     *     {@link INT }
     *     
     */
    public INT getExpectedSequenceNumber() {
        return expectedSequenceNumber;
    }

    /**
     * Sets the value of the expectedSequenceNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link INT }
     *     
     */
    public void setExpectedSequenceNumber(INT value) {
        this.expectedSequenceNumber = value;
    }

    /**
     * Gets the value of the messageWaitingNumber property.
     * 
     * @return
     *     possible object is
     *     {@link INT }
     *     
     */
    public INT getMessageWaitingNumber() {
        return messageWaitingNumber;
    }

    /**
     * Sets the value of the messageWaitingNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link INT }
     *     
     */
    public void setMessageWaitingNumber(INT value) {
        this.messageWaitingNumber = value;
    }

    /**
     * Gets the value of the messageWaitingPriorityCode property.
     * 
     * @return
     *     possible object is
     *     {@link CD }
     *     
     */
    public CD getMessageWaitingPriorityCode() {
        return messageWaitingPriorityCode;
    }

    /**
     * Sets the value of the messageWaitingPriorityCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link CD }
     *     
     */
    public void setMessageWaitingPriorityCode(CD value) {
        this.messageWaitingPriorityCode = value;
    }

    /**
     * Gets the value of the targetMessage property.
     * 
     * @return
     *     possible object is
     *     {@link MCCIMT000200UV01TargetMessage }
     *     
     */
    public MCCIMT000200UV01TargetMessage getTargetMessage() {
        return targetMessage;
    }

    /**
     * Sets the value of the targetMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link MCCIMT000200UV01TargetMessage }
     *     
     */
    public void setTargetMessage(MCCIMT000200UV01TargetMessage value) {
        this.targetMessage = value;
    }

    /**
     * Gets the value of the acknowledgementDetail property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the acknowledgementDetail property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAcknowledgementDetail().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MCCIMT000200UV01AcknowledgementDetail }
     * 
     * 
     */
    public List<MCCIMT000200UV01AcknowledgementDetail> getAcknowledgementDetail() {
        if (acknowledgementDetail == null) {
            acknowledgementDetail = new ArrayList<MCCIMT000200UV01AcknowledgementDetail>();
        }
        return this.acknowledgementDetail;
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
     *     {@link AcknowledgementType }
     *     
     */
    public AcknowledgementType getTypeCode() {
        return typeCode;
    }

    /**
     * Sets the value of the typeCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link AcknowledgementType }
     *     
     */
    public void setTypeCode(AcknowledgementType value) {
        this.typeCode = value;
    }

}
