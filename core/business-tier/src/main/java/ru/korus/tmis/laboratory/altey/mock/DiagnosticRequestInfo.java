package ru.korus.tmis.laboratory.altey.mock;

import javax.xml.bind.annotation.XmlElement;
import java.util.Date;

/**
 * Информация о направлении
 */
public class DiagnosticRequestInfo {
    /**
     * Уникальный идентификатор направления в МИС (Action.id)
     */
    private int id;

    /**
     * Дата создания направления врачом
     */
    private Date creationDate;

    /**
     * Cрок беременности пациентки (в днях)
     */
    private Integer pregnancyDaysMin;
    private Integer pregnancyDaysMax;

    /**
     * Код болезни по МКБ
     * (последний из обоснования; если нет, то из первичного осмотра)
     */
    private String mkbCode;

    /**
     * Текстовое описание диагноза
     * (последний из обоснования; если нет, то из первичного осмотра)
     */
    private String diagnosis;

    /**
     * Произвольный текстовый комментарий к направлению
     */
    private String comment;

    /**
     * Название отделения
     */
    private String departmentName;

    /**
     * Уникальный код отделение
     */
    private String departmentCode;

    /**
     * Фамилия назначившего врача
     */
    private String doctorLastName;

    /**
     * Имя назначившего врача
     */
    private String doctorFirstName;

    /**
     * Отчетство назначившего врача
     */
    private String doctorMiddleName;

    /**
     * Уникальный код назначившего врача
     */
    private String doctorCode;

    @XmlElement(name = "orderMisId")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @XmlElement(name = "orderMisDate", required = true)
    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @XmlElement(name = "orderPregnatMin")
    public int getPregnancyDaysMin() {
        return pregnancyDaysMin;
    }

    public void setPregnancyDaysMin(int pregnancyWeeks) {
        this.pregnancyDaysMin = pregnancyWeeks;
    }

    @XmlElement(name = "orderPregnatMax")
    public int getPregnancyDaysMax() {
        return pregnancyDaysMax;
    }

    public void setPregnancyDaysMax(int pregnancyWeeks) {
        this.pregnancyDaysMax = pregnancyWeeks;
    }

    @XmlElement(name = "orderDiagCode", required = true)
    public String getMkbCode() {
        return mkbCode;
    }

    public void setMkbCode(String mkbCode) {
        this.mkbCode = mkbCode;
    }

    @XmlElement(name = "orderDiagText", required = true)
    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    @XmlElement(name = "orderComment")
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @XmlElement(name = "orderDepartmentName")
    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @XmlElement(name = "orderDepartmentMisId", required = true)
    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    @XmlElement(name = "orderDoctorFamily")
    public String getDoctorLastName() {
        return doctorLastName;
    }

    public void setDoctorLastName(String doctorLastName) {
        this.doctorLastName = doctorLastName;
    }

    @XmlElement(name = "orderDoctorName")
    public String getDoctorFirstName() {
        return doctorFirstName;
    }

    public void setDoctorFirstName(String doctorFirstName) {
        this.doctorFirstName = doctorFirstName;
    }

    @XmlElement(name = "orderDoctorPatronum")
    public String getDoctorMiddleName() {
        return doctorMiddleName;
    }

    public void setDoctorMiddleName(String doctorMiddleName) {
        this.doctorMiddleName = doctorMiddleName;
    }

    @XmlElement(name = "orderDoctorMisId", required = true)
    public String getDoctorCode() {
        return doctorCode;
    }

    public void setDoctorCode(String doctorCode) {
        this.doctorCode = doctorCode;
    }
}
