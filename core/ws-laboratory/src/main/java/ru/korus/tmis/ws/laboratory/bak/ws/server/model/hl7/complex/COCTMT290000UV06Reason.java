
package ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for COCT_MT290000UV06.Reason complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="COCT_MT290000UV06.Reason">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;group ref="{urn:hl7-org:v3}InfrastructureRootElements"/>
 *         &lt;element name="administrativeDiagnosisReference" type="{urn:hl7-org:v3}COCT_MT290000UV06.AdministrativeDiagnosisReference"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{urn:hl7-org:v3}InfrastructureRootAttributes"/>
 *       &lt;attribute name="nullFlavor" type="{urn:hl7-org:v3}NullFlavor" />
 *       &lt;attribute name="typeCode" use="required" type="{urn:hl7-org:v3}ActRelationshipReason" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "COCT_MT290000UV06.Reason", propOrder = {
    "realmCode",
    "typeId",
    "templateId",
    "administrativeDiagnosisReference"
})
public class COCTMT290000UV06Reason {

    protected DSETCS realmCode;
    protected II typeId;
    protected LISTII templateId;
    @XmlElement(required = true, nillable = true)
    protected COCTMT290000UV06AdministrativeDiagnosisReference administrativeDiagnosisReference;
    @XmlAttribute(name = "nullFlavor")
    protected NullFlavor nullFlavor;
    @XmlAttribute(name = "typeCode", required = true)
    protected ActRelationshipReason typeCode;

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
     * Gets the value of the administrativeDiagnosisReference property.
     * 
     * @return
     *     possible object is
     *     {@link COCTMT290000UV06AdministrativeDiagnosisReference }
     *     
     */
    public COCTMT290000UV06AdministrativeDiagnosisReference getAdministrativeDiagnosisReference() {
        return administrativeDiagnosisReference;
    }

    /**
     * Sets the value of the administrativeDiagnosisReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link COCTMT290000UV06AdministrativeDiagnosisReference }
     *     
     */
    public void setAdministrativeDiagnosisReference(COCTMT290000UV06AdministrativeDiagnosisReference value) {
        this.administrativeDiagnosisReference = value;
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
     *     {@link ActRelationshipReason }
     *     
     */
    public ActRelationshipReason getTypeCode() {
        return typeCode;
    }

    /**
     * Sets the value of the typeCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link ActRelationshipReason }
     *     
     */
    public void setTypeCode(ActRelationshipReason value) {
        this.typeCode = value;
    }

}
