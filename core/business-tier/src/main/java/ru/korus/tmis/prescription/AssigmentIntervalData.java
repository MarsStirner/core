package ru.korus.tmis.prescription;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import ru.korus.tmis.core.entity.model.pharmacy.DrugChart;
import ru.korus.tmis.core.pharmacy.DbDrugChartBeanLocal;

import javax.xml.bind.annotation.XmlType;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        15.05.14, 11:02 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@XmlType
@JsonIgnoreProperties(ignoreUnknown = true)
public class AssigmentIntervalData {
    /**
     * DrugChart.id
     */
    private Integer id;

    private Integer actionId;

    private Integer masterId;

    /**
     * Время начала интервала в миллисекундах
     */
    private Long beginDateTime;

    /**
     * Время начала интервала
     */
    private String bdt;

    /**
     * Время окончания интервала в миллисекундах
     */
    private Long endDateTime;

    /**
     * Время окончания интервала
     */
    private String edt;

    private Short status;

    private String note;

    private List<AssigmentIntervalData> executionIntervals = new LinkedList<AssigmentIntervalData>();

    public AssigmentIntervalData(DrugChart drugChart, DbDrugChartBeanLocal dbDrugChartBeanLocal) {

        id = drugChart.getId();
        actionId = drugChart.getAction() == null ? null : drugChart.getAction().getId();
        masterId = drugChart.getMaster() == null ? null : drugChart.getMaster().getId();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        bdt = drugChart.getBegDateTime() == null ? null : simpleDateFormat.format(drugChart.getBegDateTime());
        beginDateTime = drugChart.getBegDateTime() == null ? null : drugChart.getBegDateTime().getTime();
        edt = drugChart.getEndDateTime() == null ? null : simpleDateFormat.format(drugChart.getEndDateTime());
        endDateTime = drugChart.getEndDateTime() == null ? null : drugChart.getEndDateTime().getTime();

        status = drugChart.getStatus();
        note = drugChart.getNote();

        List<DrugChart> intervals = dbDrugChartBeanLocal.getExecIntervals(actionId, id);
        for (DrugChart interval : intervals) {
            executionIntervals.add(new AssigmentIntervalData(interval, dbDrugChartBeanLocal));
        }
    }

    public AssigmentIntervalData() {
        id = null;
        actionId = null;
        masterId = null;
        beginDateTime = null;
        bdt = null;
        endDateTime = null;
        edt = null;
        status = null;
        note = null;
    }

    public Integer getId() {
        return id;
    }

    public Integer getActionId() {
        return actionId;
    }

    public Integer getMasterId() {
        return masterId;
    }

    public Long getBeginDateTime() {
        return beginDateTime;
    }

    public String getBdt() {
        return bdt;
    }

    public Long getEndDateTime() {
        return endDateTime;
    }

    public String getEdt() {
        return edt;
    }

    public Short getStatus() {
        return status;
    }

    public String getNote() {
        return note;
    }

    public List<AssigmentIntervalData> getExecutionIntervals() {
        return executionIntervals;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setActionId(Integer actionId) {
        this.actionId = actionId;
    }

    public void setMasterId(Integer masterId) {
        this.masterId = masterId;
    }

    public void setBeginDateTime(Long beginDateTime) {
        this.beginDateTime = beginDateTime;
    }

    public void setBdt(String bdt) {
        this.bdt = bdt;
    }

    public void setEndDateTime(Long endDateTime) {
        this.endDateTime = endDateTime;
    }

    public void setEdt(String edt) {
        this.edt = edt;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setExecutionIntervals(List<AssigmentIntervalData> executionIntervals) {
        this.executionIntervals = executionIntervals;
    }
}
