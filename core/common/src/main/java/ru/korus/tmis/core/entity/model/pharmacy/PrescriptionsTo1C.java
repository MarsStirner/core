package ru.korus.tmis.core.entity.model.pharmacy;

import ru.korus.tmis.core.entity.model.Action;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Arrays;

/**
 * The persistent class for the PatientsToHS database table.
 * 
 */
@Entity
@Table(name = "PrescriptionsTo1C")
@NamedQueries(
        {
                @NamedQuery(name = "PrescriptionsTo1C.findToSend", query = "SELECT p FROM PrescriptionsTo1C p " +
                        "WHERE p.sendTime < :now AND (p.isPrescription = 1 OR (p.oldStatus = 0 AND p.newStatus = 1) OR (p.oldStatus = 1 AND p.newStatus = 0))"),
        })
public class PrescriptionsTo1C implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final int MAX_INFO_LENTHG = 1024;

    @Id
    @Column(name = "interval_id", columnDefinition = "COMMENT 'Идентификатор события {Event.event_id}'")
    private int intervalId;

    @Column(name = "is_prescription", columnDefinition = "TINYINT(1) COMMENT '1 - назначение, 0 - исполнение'")
    private Boolean isPrescription;

    @Column(name = "new_status", columnDefinition = "INT(11) COMMENT 'Новый статус интервала'")
    private Integer newStatus;

    @Column(name = "old_status", columnDefinition = "INT(11) COMMENT 'Предыдущий статус интервала'")
    private Integer oldStatus;

    @Column(columnDefinition = "INT(11) NOT NULL DEFAULT '0' COMMENT 'Количество неудачных попыток'")
    private int errCount;

    @Column(columnDefinition = "VARCHAR(" + MAX_INFO_LENTHG + ") NULL DEFAULT NULL COMMENT 'Сообщение об ошибке'")
    private String info;

    @Column(columnDefinition = "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Время следующей отсылки в HealthShare'")
    private Timestamp sendTime;

    // bi-directional one-to-one association to Client
    @OneToOne
    @JoinColumn(name = "interval_id", nullable = false, insertable = false, updatable = false)
    private DrugChart drugChart;

    public PrescriptionsTo1C() {
    }

    public int getIntervalId() {
        return this.intervalId;
    }

    public void setIntervalIdId(int intervalId) {
        this.intervalId = intervalId;
    }

    public int getErrCount() {
        return this.errCount;
    }

    public void setErrCount(int errCount) {
        this.errCount = errCount;
    }

    public String getInfo() {
        return this.info;
    }

    public void setInfo(String info) {
        this.info = info.substring(0, Math.min(info.length(), MAX_INFO_LENTHG));
    }

    public Timestamp getSendTime() {
        return this.sendTime;
    }

    public void setSendTime(Timestamp sendTime) {
        this.sendTime = sendTime;
    }

    public DrugChart getDrugChart() {
        return this.drugChart;
    }

    public void setDrugChart(Action action) {
        intervalId = drugChart.getId();
        this.drugChart = drugChart;
    }

    public Boolean isPrescription() {
        return isPrescription;
    }

    public void setPrescription(Boolean prescription) {
        isPrescription = prescription;
    }

    public PrescriptionStatus getNewStatus() {
        return PrescriptionStatus.values()[newStatus];
    }

    public void setNewStatus(PrescriptionStatus newStatus) {
        this.newStatus = Arrays.asList(PrescriptionStatus.values()).indexOf(newStatus);
    }

    public PrescriptionStatus getOldStatus() {
        return PrescriptionStatus.values()[oldStatus];
    }

    public void setOldStatus(Integer oldStatus) {
        this.newStatus = Arrays.asList(PrescriptionStatus.values()).indexOf(oldStatus);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PrescriptionsTo1C{");
        sb.append("intervalId=").append(intervalId);
        sb.append(", isPrescription=").append(isPrescription);
        sb.append(", newStatus=").append(newStatus);
        sb.append(", oldStatus=").append(oldStatus);
        sb.append(", errCount=").append(errCount);
        sb.append(", info='").append(info).append('\'');
        sb.append(", sendTime=").append(sendTime);
        sb.append(", drugChart=").append(drugChart);
        sb.append('}');
        return sb.toString();
    }
}