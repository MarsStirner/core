
package ru.korus.tmis.vmp.model.quote;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;


@JsonIgnoreProperties
public class QuotaData {

    private Integer id;

    private Date createDatetime;

    private Integer createPerson_id;

    private Date modifyDatetime;

    private Integer modifyPerson_id;

    private Integer master_id; //Client.id

    private Integer status;

    private Integer quotaType_id;

    private String quotaTypeCode;

    private String quotaTypeName;

    private String MKB;

    private Integer pacientModel_id;

    private Integer treatment_id;

    private Integer event_id;

    private Integer mkbId;

    private String DiagName;

    private String treatmentName;

    private String patientModelName;

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public Date getModifyDatetime() {
        return modifyDatetime;
    }

    public void setModifyDatetime(Date modifyDatetime) {
        this.modifyDatetime = modifyDatetime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMKB() {
        return MKB;
    }

    public void setMKB(String MKB) {
        this.MKB = MKB;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCreatePerson_id() {
        return createPerson_id;
    }

    public void setCreatePerson_id(Integer createPerson_id) {
        this.createPerson_id = createPerson_id;
    }

    public Integer getModifyPerson_id() {
        return modifyPerson_id;
    }

    public void setModifyPerson_id(Integer modifyPerson_id) {
        this.modifyPerson_id = modifyPerson_id;
    }

    public Integer getMaster_id() {
        return master_id;
    }

    public void setMaster_id(Integer master_id) {
        this.master_id = master_id;
    }

    public Integer getQuotaType_id() {
        return quotaType_id;
    }

    public void setQuotaType_id(Integer quotaType_id) {
        this.quotaType_id = quotaType_id;
    }

    public Integer getPacientModel_id() {
        return pacientModel_id;
    }

    public void setPacientModel_id(Integer pacientModel_id) {
        this.pacientModel_id = pacientModel_id;
    }

    public Integer getTreatment_id() {
        return treatment_id;
    }

    public void setTreatment_id(Integer treatment_id) {
        this.treatment_id = treatment_id;
    }

    public Integer getEvent_id() {
        return event_id;
    }

    public void setEvent_id(Integer event_id) {
        this.event_id = event_id;
    }

    public String getQuotaTypeCode() {
        return quotaTypeCode;
    }

    public void setQuotaTypeCode(String quotaTypeCode) {
        this.quotaTypeCode = quotaTypeCode;
    }

    public String getQuotaTypeName() {
        return quotaTypeName;
    }

    public void setQuotaTypeName(String quotaTypeName) {
        this.quotaTypeName = quotaTypeName;
    }

    public Integer getMkbId() {
        return mkbId;
    }

    public void setMkbId(Integer mkbId) {
        this.mkbId = mkbId;
    }

    public String getDiagName() {
        return DiagName;
    }

    public void setDiagName(String diagName) {
        DiagName = diagName;
    }

    public String getTreatmentName() {
        return treatmentName;
    }

    public void setTreatmentName(String treatmentName) {
        this.treatmentName = treatmentName;
    }

    public String getPatientModelName() {
        return patientModelName;
    }

    public void setPatientModelName(String patientModelName) {
        this.patientModelName = patientModelName;
    }
}
