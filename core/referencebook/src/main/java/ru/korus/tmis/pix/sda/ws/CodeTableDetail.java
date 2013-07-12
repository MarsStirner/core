
package ru.korus.tmis.pix.sda.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CodeTableDetail complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CodeTableDetail">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SDACodingStandard" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="32000"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Code" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{}StrippedString">
 *               &lt;maxLength value="32000"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Description" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{}StrippedString">
 *               &lt;maxLength value="32000"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CodeTableDetail", propOrder = {
    "sdaCodingStandard",
    "code",
    "description"
})
@XmlSeeAlso({
    User.class,
    AlertCode.class,
    SeparationMode.class,
    RateUnits.class,
    ReferralDoctor.class,
    Trust.class,
    SocialHabit.class,
    Frequency.class,
    ContactType.class,
    OrderCategory.class,
    Religion.class,
    Race.class,
    Country.class,
    ProblemCategory.class,
    HealthCareFacility.class,
    FamilyDoctor.class,
    EthnicGroup.class,
    PhysExamObsValue.class,
    PackageSizeUoM.class,
    DiagnosisRelatedGroup.class,
    AlertType.class,
    Occupation.class,
    PublicityCode.class,
    Zip.class,
    ATCCode.class,
    AdmissionType.class,
    Reaction.class,
    City.class,
    Priority.class,
    Strength.class,
    HealthFundPlan.class,
    Language.class,
    Confidentiality.class,
    PhysExam.class,
    Organization.class,
    HealthFundCode.class,
    DocumentStatus.class,
    UoM.class,
    Route.class,
    DosageForm.class,
    DrugProductType.class,
    PhysExamObsQualifier.class,
    AdmissionSource.class,
    Certainty.class,
    PhysExamObs.class,
    Gender.class,
    Relationship.class,
    AppointmentType.class,
    DiagnosisStatus.class,
    SocialHabitQty.class,
    DrugProductForm.class,
    PastHistoryCondition.class,
    Duration.class,
    EncounterPriority.class,
    MaritalStatus.class,
    CareProvider.class,
    Citizenship.class,
    DoseUoM.class,
    CareProviderType.class,
    DischargeLocation.class,
    AdmitReason.class,
    BodyPart.class,
    State.class,
    CodeTableTranslated.class,
    Generic.class,
    FamilyMember.class
})
public class CodeTableDetail {

    @XmlElement(name = "SDACodingStandard")
    protected String sdaCodingStandard;
    @XmlElement(name = "Code")
    protected String code;
    @XmlElement(name = "Description")
    protected String description;

    /**
     * Gets the value of the sdaCodingStandard property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSDACodingStandard() {
        return sdaCodingStandard;
    }

    /**
     * Sets the value of the sdaCodingStandard property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSDACodingStandard(String value) {
        this.sdaCodingStandard = value;
    }

    /**
     * Gets the value of the code property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the value of the code property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCode(String value) {
        this.code = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

}
