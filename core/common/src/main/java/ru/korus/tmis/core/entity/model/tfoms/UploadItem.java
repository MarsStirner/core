package ru.korus.tmis.core.entity.model.tfoms;

import javax.persistence.*;
import java.util.Date;

/**
 * Author: Upatov Egor <br>
 * Date: 03.02.14, 16:49 <br>
 * Company: Korus Consulting IT <br>
 * Description: <br>
 */

/*
@SqlResultSetMapping(
        name = "UploadItemMapping",
        classes = {
                @ConstructorResult(targetClass = ru.korus.tmis.core.entity.model.tfoms.UploadItem.class,
                        columns = {
                                @ColumnResult(name = "id", type = Integer.class),
                                @ColumnResult(name = "Patient", type = Integer.class),
                                @ColumnResult(name = "Event", type = Integer.class),
                                @ColumnResult(name = "EventType", type = Integer.class),
                                @ColumnResult(name = "USL_OK", type = String.class),
                                @ColumnResult(name = "Action", type = Integer.class),
                                @ColumnResult(name = "ActionType", type = Integer.class),
                                @ColumnResult(name = "VIDPOM", type = String.class),
                                @ColumnResult(name = "FOR_POM", type = String.class),
                                @ColumnResult(name = "rbMedicalKind", type = Integer.class),
                                @ColumnResult(name = "rbMedicalKindCode", type = String.class),
                                @ColumnResult(name = "rbService", type = Integer.class),
                                @ColumnResult(name = "rbServiceInfis", type = String.class),
                                @ColumnResult(name = "PROFIL", type = String.class),
                                @ColumnResult(name = "begDate", type = Date.class),
                                @ColumnResult(name = "endDate", type = Date.class),
                                @ColumnResult(name = "ActionAmount", type = Double.class)
                        }
                )
        }
)
*/

public class UploadItem implements Informationable {
    private Integer id;
    private static int nextIdentifier=0;

    @Column(name = "Patient")
    private Integer patient;

    @Column(name = "Event")
    private Integer event;

    @Column(name = "EventType")
    private Integer eventType;

    @Column(name = "USL_OK")
    private String USL_OK;

    @Column(name = "Action")
    private Integer action;

    @Column(name = "ActionType")
    private Integer actionType;

    @Column(name = "VIDPOM")
    private String VIDPOM;

    @Column(name = "FOR_POM")
    private String FOR_POM;

    @Column(name = "rbMedicalKind")
    private Integer medicalKind;

    @Column(name = "rbMedicalKindCode")
    private String medicalKindCode;

    @Column(name = "rbService")
    private Integer service;

    @Column(name = "rbServiceInfis")
    private String serviceInfis;

    @Column(name = "PROFIL")
    private String PROFIL;

    @Column(name = "begDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date begDate;

    @Column(name = "endDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @Column(name = "ActionAmount")
    private double actionAmount;


    public UploadItem(final Object[] args) {
        this.id = nextIdentifier++;
        if (args.length >= 16) {
            this.patient = (Integer) args[0];
            this.event = (Integer) args[1];
            this.eventType = (Integer) args[2];
            this.USL_OK = (String) args[3];
            this.action = (Integer) args[4];
            this.actionType = (Integer) args[5];
            this.VIDPOM = (String) args[6];
            this.FOR_POM = (String) args[7];
            this.medicalKind = (Integer) args[8];
            this.medicalKindCode = (String) args[9];
            this.service = (Integer) args[10];
            this.serviceInfis = (String) args[11];
            this.PROFIL = (String) args[12];
            this.begDate = (Date) args[13];
            this.endDate = (Date) args[14];
            this.actionAmount = (Double) args[15];
        }
    }

    @Override
    public String toString() {
        return getInfo();
    }

    @Override
    public String getInfo() {
        final StringBuilder result = new StringBuilder("UploadItem[").append(id).append("]");
        result.append("[ Patient=").append(patient);
        result.append(" Event=").append(event);
        result.append(" EventType=").append(eventType);
        result.append(" USL_OK=").append(USL_OK);
        result.append(" Action=").append(action);
        result.append(" ActionType=").append(actionType);
        result.append(" VIDPOM=").append(VIDPOM);
        result.append(" FOR_POM=").append(FOR_POM);
        result.append(" rbMedicalKind=").append(medicalKind).append(" ").append(medicalKindCode);
        result.append(" rbService=").append(service).append(" ").append(serviceInfis);
        result.append(" PROFIL=").append(PROFIL);
        result.append(" begDate=").append(begDate);
        result.append(" endDate=").append(endDate);
        result.append(" amount=").append(actionAmount);
        result.append(" ]");
        return result.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UploadItem that = (UploadItem) o;

        return action.equals(that.action) && patient.equals(that.patient) && service.equals(that.service);

    }

    @Override
    public int hashCode() {
        int result = patient.hashCode();
        result = 31 * result + action.hashCode();
        result = 31 * result + service.hashCode();
        return result;
    }

    //Getters & Setters


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPatient() {
        return patient;
    }

    public void setPatient(Integer patient) {
        this.patient = patient;
    }

    public Integer getEvent() {
        return event;
    }

    public void setEvent(Integer event) {
        this.event = event;
    }

    public Integer getEventType() {
        return eventType;
    }

    public void setEventType(Integer eventType) {
        this.eventType = eventType;
    }

    public String getUSL_OK() {
        return USL_OK;
    }

    public void setUSL_OK(String USL_OK) {
        this.USL_OK = USL_OK;
    }

    public Integer getAction() {
        return action;
    }

    public void setAction(Integer action) {
        this.action = action;
    }

    public Integer getActionType() {
        return actionType;
    }

    public void setActionType(Integer actionType) {
        this.actionType = actionType;
    }

    public String getVIDPOM() {
        return VIDPOM;
    }

    public void setVIDPOM(String VIDPOM) {
        this.VIDPOM = VIDPOM;
    }

    public String getFOR_POM() {
        return FOR_POM;
    }

    public void setFOR_POM(String FOR_POM) {
        this.FOR_POM = FOR_POM;
    }

    public Integer getMedicalKind() {
        return medicalKind;
    }

    public void setMedicalKind(Integer medicalKind) {
        this.medicalKind = medicalKind;
    }

    public Integer getService() {
        return service;
    }

    public void setService(Integer service) {
        this.service = service;
    }

    public String getPROFIL() {
        return PROFIL;
    }

    public void setPROFIL(String PROFIL) {
        this.PROFIL = PROFIL;
    }

    public Date getBegDate() {
        return begDate;
    }

    public void setBegDate(Date begDate) {
        this.begDate = begDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getMedicalKindCode() {
        return medicalKindCode;
    }

    public void setMedicalKindCode(String medicalKindCode) {
        this.medicalKindCode = medicalKindCode;
    }

    public String getServiceInfis() {
        return serviceInfis;
    }

    public void setServiceInfis(String serviceInfis) {
        this.serviceInfis = serviceInfis;
    }

    public double getActionAmount() {
        return actionAmount;
    }

    public void setActionAmount(double actionAmount) {
        this.actionAmount = actionAmount;
    }
}
