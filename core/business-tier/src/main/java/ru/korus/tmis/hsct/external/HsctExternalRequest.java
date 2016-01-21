package ru.korus.tmis.hsct.external;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Author: Upatov Egor <br>
 * Date: 24.12.2015, 16:34 <br>
 * Company: hitsl (Hi-Tech Solutions) <br>
 * Description: <br>
 */
@XmlRootElement(name = "request")
@XmlAccessorType(XmlAccessType.FIELD)
public class HsctExternalRequest {

    //Таблица 1. Поля формы заявки на ТГСК

    /**
     * Статус болезни	Текстовое поле (string)	disease_status
     */
    @XmlElement(name = "disease_status", required = true)
    private String diseaseStatus;

    /**
     * Дата установки диагноза	Поле типа «Дата»	diagnosis_date
     */
    @XmlElement(name = "diagnosis_date", required = true)
    private String diagnosisDate;

    /**
     * Анти-CMV IgG	Выпадающий список с значениями: «Положительный», «Отрицательный»	anti_cmv_igg_code
     */
    @XmlElement(name = "anti_cmv_igg_code", required = true)
    private String antiCmvIgG;

    /**
     * Показания к ТСГК	Текстовое поле (string)	indications
     */
    @XmlElement(name = "indications", required = true)
    private String indications;

    /**
     * Дата установления показаний	Поле типа «Дата»	indications_date
     */
    @XmlElement(name = "indications_date", required = true)
    private String indicationsDate;

    /**
     * Оптимальный срок ТГСК 	Поле типа «Дата» 	optimal_hsct_date  :: Дата вида "yyyy-mm"
     */
    @XmlElement(name = "optimal_hsct_date", required = true)
    private String hsctOptimalDate;
    /**
     * Вид ТСГК	Выпадающий список с значениями: «Аутологичная», «Аллогенная»	hsct_type_code
     */
    @XmlElement(name = "hsct_type_code", required = true)
    private String hsctTypeCode;

    /**
     * Наличие сиблингов	Выпадающий список с значениями: «Есть», «Нет»	has_siblings
     */
    @XmlElement(name = "has_siblings", required = true)
    private boolean siblings;

    //Таблица 3. Другие данные, отправляемые из МИС в систему планирования ТГСК
    /**
     * ID заявки в МИС	Текстовое поле (string) mis_id
     */
    @XmlElement(name = "mis_id")
    private String misId;

    /**
     * Регистрационная карта пациента
     */
    @XmlElement(name = "patient")
    private Patient patient;

    /**
     * Отделение
     */
    @XmlElement(name = "department_code")
    private String departmentCode;

    /**
     * Данные текущего пользователя МИС, заполняющего заявку
     */
    @XmlElement(name = "doctor")
    private Doctor doctor;

    /**
     * Регистрационная карта представителя
     */
    @XmlElement(name = "representative")
    private Spokesman spokesman;

    /**
     * Клинический диагноз
     */
    @XmlElement(name = "diagnosis", required = true)
    private String diagnosis;

    /**
     * Основной клинический диагноз по МКБ
     */
    @XmlElement(name = "diagnosis_icd_code", required = true)
    private String diagnosisIcdCode;

    /**
     * complications – массив осложнений содержащий хэши с полями descript и icd_code
     */
    @XmlElement(name = "complications")
    private List<Item> complications;

    /**
     * secondary_diagnoses – массив сопутств. Диагнозов содержащий хэши с полями descript и icd_code
     */
    @XmlElement(name = "secondary_diagnoses")
    private List<Item> secondaryDiagnoses;

    /**
     * Протокол терапии  protocol_code
     */
    @XmlElement(name = "protocol_code")
    private String protocolCode;

    /**
     * Дата начала протокола  protocol_start_date
     */
    @XmlElement(name = "protocol_start_date")
    private String protocolStartDate;

    /**
     * Дата завершения протокола  protocol_end_date
     */
    @XmlElement(name = "protocol_end_date")
    private String protocolEndDate;

    /**
     * Этап терапии  protocol_code
     */
    @XmlElement(name = "protocol_stage_code")
    private String protocolStageCode;

    /**
     * Дата начала этапа  protocol_start_date
     */
    @XmlElement(name = "protocol_stage_start_date")
    private String protocolStageStartDate;

    /**
     * Дата начала этапа  protocol_start_date
     */
    @XmlElement(name = "protocol_stage_end_date")
    private String protocolStageEndDate;

    /**
     * Масса тела  weight
     */
    @XmlElement(name = "weight")
    private Double weight;

    /**
     * ДГруппа крови и резус фактор blood_type_code
     */
    @XmlElement(name = "blood_type_code")
    private String bloodTypeCode;

    public HsctExternalRequest() {
    }

    public String getDiseaseStatus() {
        return diseaseStatus;
    }

    public void setDiseaseStatus(final String diseaseStatus) {
        this.diseaseStatus = diseaseStatus;
    }

    public String getDiagnosisDate() {
        return diagnosisDate;
    }

    public void setDiagnosisDate(final String diagnosisDate) {
        this.diagnosisDate = diagnosisDate;
    }

    public String getAntiCmvIgG() {
        return antiCmvIgG;
    }

    public void setAntiCmvIgG(final String antiCmvIgG) {
        this.antiCmvIgG = antiCmvIgG;
    }

    public String getIndications() {
        return indications;
    }

    public void setIndications(final String indications) {
        this.indications = indications;
    }

    public String getIndicationsDate() {
        return indicationsDate;
    }

    public void setIndicationsDate(final String indicationsDate) {
        this.indicationsDate = indicationsDate;
    }

    public String getProtocolStageEndDate() {
        return protocolStageEndDate;
    }

    public void setProtocolStageEndDate(final String protocolStageEndDate) {
        this.protocolStageEndDate = protocolStageEndDate;
    }

    public String getHsctOptimalDate() {

        return hsctOptimalDate;
    }

    public void setHsctOptimalDate(final String hsctOptimalDate) {
        this.hsctOptimalDate = hsctOptimalDate;
    }

    public String getHsctTypeCode() {
        return hsctTypeCode;
    }

    public void setHsctTypeCode(final String hsctTypeCode) {
        this.hsctTypeCode = hsctTypeCode;
    }

    public boolean isSiblings() {
        return siblings;
    }

    public void setSiblings(final boolean siblings) {
        this.siblings = siblings;
    }

    public String getMisId() {
        return misId;
    }

    public void setMisId(final String misId) {
        this.misId = misId;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(final Patient patient) {
        this.patient = patient;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(final String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(final Doctor doctor) {
        this.doctor = doctor;
    }

    public Spokesman getSpokesman() {
        return spokesman;
    }

    public void setSpokesman(final Spokesman spokesman) {
        this.spokesman = spokesman;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(final String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getDiagnosisIcdCode() {
        return diagnosisIcdCode;
    }

    public void setDiagnosisIcdCode(final String diagnosisIcdCode) {
        this.diagnosisIcdCode = diagnosisIcdCode;
    }

    public List<Item> getComplications() {
        return complications;
    }

    public void setComplications(final List<Item> complications) {
        this.complications = complications;
    }

    public List<Item> getSecondaryDiagnoses() {
        return secondaryDiagnoses;
    }

    public void setSecondaryDiagnoses(final List<Item> secondaryDiagnoses) {
        this.secondaryDiagnoses = secondaryDiagnoses;
    }

    public String getProtocolCode() {
        return protocolCode;
    }

    public void setProtocolCode(final String protocolCode) {
        this.protocolCode = protocolCode;
    }

    public String getProtocolStartDate() {
        return protocolStartDate;
    }

    public void setProtocolStartDate(final String protocolStartDate) {
        this.protocolStartDate = protocolStartDate;
    }

    public String getProtocolEndDate() {
        return protocolEndDate;
    }

    public void setProtocolEndDate(final String protocolEndDate) {
        this.protocolEndDate = protocolEndDate;
    }

    public String getProtocolStageCode() {
        return protocolStageCode;
    }

    public void setProtocolStageCode(final String protocolStageCode) {
        this.protocolStageCode = protocolStageCode;
    }

    public String getProtocolStageStartDate() {
        return protocolStageStartDate;
    }

    public void setProtocolStageStartDate(final String protocolStageStartDate) {
        this.protocolStageStartDate = protocolStageStartDate;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(final Double weight) {
        this.weight = weight;
    }

    public String getBloodTypeCode() {
        return bloodTypeCode;
    }

    public void setBloodTypeCode(final String bloodTypeCode) {
        this.bloodTypeCode = bloodTypeCode;
    }
}
