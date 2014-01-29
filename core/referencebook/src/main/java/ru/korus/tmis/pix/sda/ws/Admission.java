
package ru.korus.tmis.pix.sda.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * Данные о госпитализации
 * 
 * <p>Java class for Admission complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Admission">
 *   &lt;complexContent>
 *     &lt;extension base="{}BaseSerial">
 *       &lt;sequence>
 *         &lt;element name="isUrgentAdmission" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="timeAfterFallingIll" type="{}CodeNamePair" minOccurs="0"/>
 *         &lt;element name="transportType" type="{}CodeNamePair" minOccurs="0"/>
 *         &lt;element name="department" type="{}CodeNamePair" minOccurs="0"/>
 *         &lt;element name="finalDepartment" type="{}CodeNamePair" minOccurs="0"/>
 *         &lt;element name="ward" type="{}String" minOccurs="0"/>
 *         &lt;element name="bedDayCount" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="admittingDoctor" type="{}Employee" minOccurs="0"/>
 *         &lt;element name="attendingDoctor" type="{}Employee" minOccurs="0"/>
 *         &lt;element name="admissionReferral" type="{}CodeNamePair" minOccurs="0"/>
 *         &lt;element name="priorityCode" type="{}CodeNamePair" minOccurs="0"/>
 *         &lt;element name="admissionsThisYear" type="{}CodeNamePair" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Admission", propOrder = {
    "isUrgentAdmission",
    "timeAfterFallingIll",
    "transportType",
    "department",
    "finalDepartment",
    "ward",
    "bedDayCount",
    "admittingDoctor",
    "attendingDoctor",
    "admissionReferral",
    "priorityCode",
    "admissionsThisYear"
})
public class Admission
    extends BaseSerial
{

    protected Boolean isUrgentAdmission;
    protected CodeAndName timeAfterFallingIll;
    protected CodeAndName transportType;
    protected CodeAndName department;
    protected CodeAndName finalDepartment;
    protected String ward;
    protected Long bedDayCount;
    protected Employee admittingDoctor;
    protected Employee attendingDoctor;
    protected CodeAndName admissionReferral;
    protected CodeAndName priorityCode;
    protected CodeAndName admissionsThisYear;

    /**
     * Gets the value of the isUrgentAdmission property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsUrgentAdmission() {
        return isUrgentAdmission;
    }

    /**
     * Sets the value of the isUrgentAdmission property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsUrgentAdmission(Boolean value) {
        this.isUrgentAdmission = value;
    }

    /**
     * Gets the value of the timeAfterFallingIll property.
     * 
     * @return
     *     possible object is
     *     {@link CodeAndName }
     *     
     */
    public CodeAndName getTimeAfterFallingIll() {
        return timeAfterFallingIll;
    }

    /**
     * Sets the value of the timeAfterFallingIll property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodeAndName }
     *     
     */
    public void setTimeAfterFallingIll(CodeAndName value) {
        this.timeAfterFallingIll = value;
    }

    /**
     * Gets the value of the transportType property.
     * 
     * @return
     *     possible object is
     *     {@link CodeAndName }
     *     
     */
    public CodeAndName getTransportType() {
        return transportType;
    }

    /**
     * Sets the value of the transportType property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodeAndName }
     *     
     */
    public void setTransportType(CodeAndName value) {
        this.transportType = value;
    }

    /**
     * Gets the value of the department property.
     * 
     * @return
     *     possible object is
     *     {@link CodeAndName }
     *     
     */
    public CodeAndName getDepartment() {
        return department;
    }

    /**
     * Sets the value of the department property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodeAndName }
     *     
     */
    public void setDepartment(CodeAndName value) {
        this.department = value;
    }

    /**
     * Gets the value of the finalDepartment property.
     * 
     * @return
     *     possible object is
     *     {@link CodeAndName }
     *     
     */
    public CodeAndName getFinalDepartment() {
        return finalDepartment;
    }

    /**
     * Sets the value of the finalDepartment property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodeAndName }
     *     
     */
    public void setFinalDepartment(CodeAndName value) {
        this.finalDepartment = value;
    }

    /**
     * Gets the value of the ward property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWard() {
        return ward;
    }

    /**
     * Sets the value of the ward property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWard(String value) {
        this.ward = value;
    }

    /**
     * Gets the value of the bedDayCount property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getBedDayCount() {
        return bedDayCount;
    }

    /**
     * Sets the value of the bedDayCount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setBedDayCount(Long value) {
        this.bedDayCount = value;
    }

    /**
     * Gets the value of the admittingDoctor property.
     * 
     * @return
     *     possible object is
     *     {@link Employee }
     *     
     */
    public Employee getAdmittingDoctor() {
        return admittingDoctor;
    }

    /**
     * Sets the value of the admittingDoctor property.
     * 
     * @param value
     *     allowed object is
     *     {@link Employee }
     *     
     */
    public void setAdmittingDoctor(Employee value) {
        this.admittingDoctor = value;
    }

    /**
     * Gets the value of the attendingDoctor property.
     * 
     * @return
     *     possible object is
     *     {@link Employee }
     *     
     */
    public Employee getAttendingDoctor() {
        return attendingDoctor;
    }

    /**
     * Sets the value of the attendingDoctor property.
     * 
     * @param value
     *     allowed object is
     *     {@link Employee }
     *     
     */
    public void setAttendingDoctor(Employee value) {
        this.attendingDoctor = value;
    }

    /**
     * Gets the value of the admissionReferral property.
     * 
     * @return
     *     possible object is
     *     {@link CodeAndName }
     *     
     */
    public CodeAndName getAdmissionReferral() {
        return admissionReferral;
    }

    /**
     * Sets the value of the admissionReferral property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodeAndName }
     *     
     */
    public void setAdmissionReferral(CodeAndName value) {
        this.admissionReferral = value;
    }

    /**
     * Gets the value of the priorityCode property.
     * 
     * @return
     *     possible object is
     *     {@link CodeAndName }
     *     
     */
    public CodeAndName getPriorityCode() {
        return priorityCode;
    }

    /**
     * Sets the value of the priorityCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodeAndName }
     *     
     */
    public void setPriorityCode(CodeAndName value) {
        this.priorityCode = value;
    }

    /**
     * Gets the value of the admissionsThisYear property.
     * 
     * @return
     *     possible object is
     *     {@link CodeAndName }
     *     
     */
    public CodeAndName getAdmissionsThisYear() {
        return admissionsThisYear;
    }

    /**
     * Sets the value of the admissionsThisYear property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodeAndName }
     *     
     */
    public void setAdmissionsThisYear(CodeAndName value) {
        this.admissionsThisYear = value;
    }

}
