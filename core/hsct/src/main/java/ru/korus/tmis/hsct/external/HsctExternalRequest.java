package ru.korus.tmis.hsct.external;


import com.google.gson.annotations.SerializedName;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonRootName;

/**
 * Author: Upatov Egor <br>
 * Date: 24.12.2015, 16:34 <br>
 * Company: hitsl (Hi-Tech Solutions) <br>
 * Description: <br>
 */
@JsonRootName(value = "request")
public class HsctExternalRequest {

    //Таблица 1. Поля формы заявки на ТГСК

    /**
     * Статус болезни	Текстовое поле (string)	disease_status
     */
    @SerializedName("disease_status")
    @JsonProperty("disease_status")
    private String diseaseStatus;

    /**
     * Дата установки диагноза	Поле типа «Дата»	diagnosis_date
     */
    @SerializedName("diagnosis_date")
    @JsonProperty("diagnosis_date")
    private String diagnosisDate;

    /**
     * Анти-CMV IgG	Выпадающий список с значениями: «Положительный», «Отрицательный»	anti_cmv_igg_code
     */
    @SerializedName("anti_cmv_igg_code")
    @JsonProperty("anti_cmv_igg_code")
    private String antiCmvIgG;

    /**
     * Показания к ТСГК	Текстовое поле (string)	indications
     */
    @SerializedName("indications")
    @JsonProperty("indications")
    private String indications;

    /**
     * Дата установления показаний	Поле типа «Дата»	indications_date
     */
    @SerializedName("indications_date")
    @JsonProperty("indications_date")
    private String indicationsDate;

    /**
     * Оптимальный срок ТГСК 	Поле типа «Дата» 	optimal_hsct_date  :: Дата вида "yyyy-mm"
     */
    @SerializedName("optimal_hsct_date")
    @JsonProperty("optimal_hsct_date")
    private String hsctOptimalDate;
    /**
     * Вид ТСГК	Выпадающий список с значениями: «Аутологичная», «Аллогенная»	hsct_type_code
     */
    @SerializedName("hsct_type_code")
    @JsonProperty("hsct_type_code")
    private String hsctTypeCode;

    /**
     * Наличие сиблингов	Выпадающий список с значениями: «Есть», «Нет»	has_siblings
     */
    @SerializedName("has_siblings")
    @JsonProperty("has_siblings")
    private Boolean siblings;

    //Таблица 3. Другие данные, отправляемые из МИС в систему планирования ТГСК
    /**
     * ID заявки в МИС	Текстовое поле (string) mis_id
     */
    @SerializedName("mis_id")
    @JsonProperty("mis_id")
    private String misId;

    /**
     * Регистрационная карта пациента
     */
    @SerializedName("patient")
    @JsonProperty("patient")
    private Patient patient;

    /**
     * Отделение
     */
    @SerializedName("department_code")
    @JsonProperty("department_code")
    private String departmentCode;

    /**
     * Данные текущего пользователя МИС, заполняющего заявку
     */
    @SerializedName("doctor")
    @JsonProperty("doctor")
    private Doctor doctor;

    /**
     * Регистрационная карта представителя
     */
    @SerializedName("representative")
    @JsonProperty("representative")
    private Spokesman spokesman;

    /**
     * Клинический диагноз
     */
    @SerializedName("diagnosis")
    @JsonProperty("diagnosis")
    private String diagnosis;

    /**
     * Основной клинический диагноз по МКБ
     */
    @SerializedName("diagnosis_icd_code")
    @JsonProperty("diagnosis_icd_code")
    private String diagnosisIcdCode;

    /**
     * complications – массив осложнений содержащий хэши с полями descript и icd_code
     */
    @SerializedName("complications")
    @JsonProperty("complications")
    private String complications;

    /**
     * secondary_diagnoses – массив сопутств. Диагнозов содержащий хэши с полями descript и icd_code
     */
    @SerializedName("secondary_diagnoses")
    @JsonProperty("secondary_diagnoses")
    private String secondaryDiagnoses;

    /**
     * Протокол терапии  protocol_code
     */
    @SerializedName("protocol_code")
    @JsonProperty("protocol_code")
    private String protocolCode;

    /**
     * Дата начала протокола  protocol_start_date
     */
    @SerializedName("protocol_start_date")
    @JsonProperty("protocol_start_date")
    private String protocolStartDate;

    /**
     * Дата завершения протокола  protocol_end_date
     */
    @SerializedName("protocol_end_date")
    @JsonProperty("protocol_end_date")
    private String protocolEndDate;

    /**
     * Этап терапии  protocol_code
     */
    @SerializedName("protocol_stage_code")
    @JsonProperty("protocol_stage_code")
    private String protocolStageCode;

    /**
     * Дата начала этапа  protocol_start_date
     */
    @SerializedName("protocol_stage_start_date")
    @JsonProperty("protocol_stage_start_date")
    private String protocolStageStartDate;

    /**
     * Дата начала этапа  protocol_start_date
     */
    @SerializedName("protocol_stage_end_date")
    @JsonProperty("protocol_stage_end_date")
    private String protocolStageEndDate;

    /**
     * Масса тела  weight
     */
    @SerializedName("weight")
    @JsonProperty("weight")
    private Double weight;

    /**
     * ДГруппа крови и резус фактор blood_type_code
     */
    @SerializedName("blood_type_code")
    @JsonProperty("blood_type_code")
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

    public Boolean getSiblings() {
        return siblings;
    }

    public void setSiblings(final Boolean siblings) {
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

    public String getComplications() {
        return complications;
    }

    public void setComplications(final String complications) {
        this.complications = complications;
    }

    public String getSecondaryDiagnoses() {
        return secondaryDiagnoses;
    }

    public void setSecondaryDiagnoses(final String secondaryDiagnoses) {
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("HsctExternalRequest{");
        sb.append("diseaseStatus='").append(diseaseStatus).append('\'');
        sb.append(", diagnosisDate='").append(diagnosisDate).append('\'');
        sb.append(", antiCmvIgG='").append(antiCmvIgG).append('\'');
        sb.append(", indications='").append(indications).append('\'');
        sb.append(", indicationsDate='").append(indicationsDate).append('\'');
        sb.append(", hsctOptimalDate='").append(hsctOptimalDate).append('\'');
        sb.append(", hsctTypeCode='").append(hsctTypeCode).append('\'');
        sb.append(", siblings=").append(siblings);
        sb.append(", misId='").append(misId).append('\'');
        sb.append(", patient=").append(patient);
        sb.append(", departmentCode='").append(departmentCode).append('\'');
        sb.append(", doctor=").append(doctor);
        sb.append(", spokesman=").append(spokesman);
        sb.append(", diagnosis='").append(diagnosis).append('\'');
        sb.append(", diagnosisIcdCode='").append(diagnosisIcdCode).append('\'');
        sb.append(", complications=").append(complications);
        sb.append(", secondaryDiagnoses=").append(secondaryDiagnoses);
        sb.append(", protocolCode='").append(protocolCode).append('\'');
        sb.append(", protocolStartDate='").append(protocolStartDate).append('\'');
        sb.append(", protocolEndDate='").append(protocolEndDate).append('\'');
        sb.append(", protocolStageCode='").append(protocolStageCode).append('\'');
        sb.append(", protocolStageStartDate='").append(protocolStageStartDate).append('\'');
        sb.append(", protocolStageEndDate='").append(protocolStageEndDate).append('\'');
        sb.append(", weight=").append(weight);
        sb.append(", bloodTypeCode='").append(bloodTypeCode).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
