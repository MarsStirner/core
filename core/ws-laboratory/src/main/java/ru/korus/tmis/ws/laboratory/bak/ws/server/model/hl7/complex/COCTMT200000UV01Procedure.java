
package ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for COCT_MT200000UV01.Procedure complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="COCT_MT200000UV01.Procedure">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;group ref="{urn:hl7-org:v3}InfrastructureRootElements"/>
 *         &lt;element name="id" type="{urn:hl7-org:v3}II"/>
 *         &lt;element name="code" type="{urn:hl7-org:v3}CD"/>
 *         &lt;element name="text" type="{urn:hl7-org:v3}ED" minOccurs="0"/>
 *         &lt;element name="statusCode" type="{urn:hl7-org:v3}CS"/>
 *         &lt;element name="effectiveTime" type="{urn:hl7-org:v3}QSET_TS" minOccurs="0"/>
 *         &lt;element name="confidentialityCode" type="{urn:hl7-org:v3}DSET_CD"/>
 *         &lt;element name="uncertaintyCode" type="{urn:hl7-org:v3}CD" minOccurs="0"/>
 *         &lt;element name="methodCode" type="{urn:hl7-org:v3}DSET_CD" minOccurs="0"/>
 *         &lt;element name="approachSiteCode" type="{urn:hl7-org:v3}DSET_CD" minOccurs="0"/>
 *         &lt;element name="targetSiteCode" type="{urn:hl7-org:v3}DSET_CD" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{urn:hl7-org:v3}InfrastructureRootAttributes"/>
 *       &lt;attribute name="classCode" use="required" type="{urn:hl7-org:v3}ActClassProcedure" />
 *       &lt;attribute name="moodCode" use="required" type="{urn:hl7-org:v3}x_ActMoodOrdPrmsEvn" />
 *       &lt;attribute name="negationInd" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "COCT_MT200000UV01.Procedure", propOrder = {
    "realmCode",
    "typeId",
    "templateId",
    "id",
    "code",
    "text",
    "statusCode",
    "effectiveTime",
    "confidentialityCode",
    "uncertaintyCode",
    "methodCode",
    "approachSiteCode",
    "targetSiteCode"
})
public class COCTMT200000UV01Procedure {

    protected DSETCS realmCode;
    protected II typeId;
    protected LISTII templateId;
    @XmlElement(required = true)
    protected II id;
    @XmlElement(required = true)
    protected CD code;
    protected ED text;
    @XmlElement(required = true)
    protected CS statusCode;
    protected QSETTS effectiveTime;
    @XmlElement(required = true)
    protected DSETCD confidentialityCode;
    protected CD uncertaintyCode;
    protected DSETCD methodCode;
    protected DSETCD approachSiteCode;
    protected DSETCD targetSiteCode;
    @XmlAttribute(name = "classCode", required = true)
    protected ActClassProcedure classCode;
    @XmlAttribute(name = "moodCode", required = true)
    protected XActMoodOrdPrmsEvn moodCode;
    @XmlAttribute(name = "negationInd")
    protected Boolean negationInd;

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
     *     {@link II }
     *     
     */
    public II getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link II }
     *     
     */
    public void setId(II value) {
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
     * Gets the value of the text property.
     * 
     * @return
     *     possible object is
     *     {@link ED }
     *     
     */
    public ED getText() {
        return text;
    }

    /**
     * Sets the value of the text property.
     * 
     * @param value
     *     allowed object is
     *     {@link ED }
     *     
     */
    public void setText(ED value) {
        this.text = value;
    }

    /**
     * Gets the value of the statusCode property.
     * 
     * @return
     *     possible object is
     *     {@link CS }
     *     
     */
    public CS getStatusCode() {
        return statusCode;
    }

    /**
     * Sets the value of the statusCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link CS }
     *     
     */
    public void setStatusCode(CS value) {
        this.statusCode = value;
    }

    /**
     * Gets the value of the effectiveTime property.
     * 
     * @return
     *     possible object is
     *     {@link QSETTS }
     *     
     */
    public QSETTS getEffectiveTime() {
        return effectiveTime;
    }

    /**
     * Sets the value of the effectiveTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link QSETTS }
     *     
     */
    public void setEffectiveTime(QSETTS value) {
        this.effectiveTime = value;
    }

    /**
     * Gets the value of the confidentialityCode property.
     * 
     * @return
     *     possible object is
     *     {@link DSETCD }
     *     
     */
    public DSETCD getConfidentialityCode() {
        return confidentialityCode;
    }

    /**
     * Sets the value of the confidentialityCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link DSETCD }
     *     
     */
    public void setConfidentialityCode(DSETCD value) {
        this.confidentialityCode = value;
    }

    /**
     * Gets the value of the uncertaintyCode property.
     * 
     * @return
     *     possible object is
     *     {@link CD }
     *     
     */
    public CD getUncertaintyCode() {
        return uncertaintyCode;
    }

    /**
     * Sets the value of the uncertaintyCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link CD }
     *     
     */
    public void setUncertaintyCode(CD value) {
        this.uncertaintyCode = value;
    }

    /**
     * Gets the value of the methodCode property.
     * 
     * @return
     *     possible object is
     *     {@link DSETCD }
     *     
     */
    public DSETCD getMethodCode() {
        return methodCode;
    }

    /**
     * Sets the value of the methodCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link DSETCD }
     *     
     */
    public void setMethodCode(DSETCD value) {
        this.methodCode = value;
    }

    /**
     * Gets the value of the approachSiteCode property.
     * 
     * @return
     *     possible object is
     *     {@link DSETCD }
     *     
     */
    public DSETCD getApproachSiteCode() {
        return approachSiteCode;
    }

    /**
     * Sets the value of the approachSiteCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link DSETCD }
     *     
     */
    public void setApproachSiteCode(DSETCD value) {
        this.approachSiteCode = value;
    }

    /**
     * Gets the value of the targetSiteCode property.
     * 
     * @return
     *     possible object is
     *     {@link DSETCD }
     *     
     */
    public DSETCD getTargetSiteCode() {
        return targetSiteCode;
    }

    /**
     * Sets the value of the targetSiteCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link DSETCD }
     *     
     */
    public void setTargetSiteCode(DSETCD value) {
        this.targetSiteCode = value;
    }

    /**
     * Gets the value of the classCode property.
     * 
     * @return
     *     possible object is
     *     {@link ActClassProcedure }
     *     
     */
    public ActClassProcedure getClassCode() {
        return classCode;
    }

    /**
     * Sets the value of the classCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link ActClassProcedure }
     *     
     */
    public void setClassCode(ActClassProcedure value) {
        this.classCode = value;
    }

    /**
     * Gets the value of the moodCode property.
     * 
     * @return
     *     possible object is
     *     {@link XActMoodOrdPrmsEvn }
     *     
     */
    public XActMoodOrdPrmsEvn getMoodCode() {
        return moodCode;
    }

    /**
     * Sets the value of the moodCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link XActMoodOrdPrmsEvn }
     *     
     */
    public void setMoodCode(XActMoodOrdPrmsEvn value) {
        this.moodCode = value;
    }

    /**
     * Gets the value of the negationInd property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isNegationInd() {
        if (negationInd == null) {
            return false;
        } else {
            return negationInd;
        }
    }

    /**
     * Sets the value of the negationInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setNegationInd(Boolean value) {
        this.negationInd = value;
    }

}
