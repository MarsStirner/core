package ru.korus.tmis.lis.data;

import org.joda.time.DateTime;
import ru.korus.tmis.core.entity.model.Staff;

import java.io.Serializable;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        20.11.13, 19:38 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public class DiagnosticRequestInfo implements Serializable {

    public static final long serialVersionUID = 1L;

    private int orderMisId;
    private String orderCaseId;
    private int orderFinanceId;
    private DateTime orderMisDate;
    private int orderPregnatMin;
    private int orderPregnatMax;
    private String orderDiagCode;
    private String orderDiagText;
    private String orderComment;
    private String orderDepartmentName;
    private String orderDepartmentMisCode;
    private String orderDoctorFamily;
    private String orderDoctorName;
    private String orderDoctorPatronum;
    private int orderDoctorMisId;
    private Staff orderDoctorMis;

    public DiagnosticRequestInfo() {
    }

    public int getOrderMisId() {
        return orderMisId;
    }

    public void setOrderMisId(int orderMisId) {
        this.orderMisId = orderMisId;
    }

    public String getOrderCaseId() {
        return orderCaseId;
    }

    public void setOrderCaseId(String orderCaseId) {
        this.orderCaseId = orderCaseId;
    }

    public int getOrderFinanceId() {
        return orderFinanceId;
    }

    public void setOrderFinanceId(int orderFinanceId) {
        this.orderFinanceId = orderFinanceId;
    }

    public DateTime getOrderMisDate() {
        return orderMisDate;
    }

    public void setOrderMisDate(DateTime orderMisDate) {
        this.orderMisDate = orderMisDate;
    }

    public int getOrderPregnatMin() {
        return orderPregnatMin;
    }

    public void setOrderPregnatMin(int orderPregnatMin) {
        this.orderPregnatMin = orderPregnatMin;
    }

    public int getOrderPregnatMax() {
        return orderPregnatMax;
    }

    public void setOrderPregnatMax(int orderPregnatMax) {
        this.orderPregnatMax = orderPregnatMax;
    }

    public String getOrderDiagCode() {
        return orderDiagCode;
    }

    public void setOrderDiagCode(String orderDiagCode) {
        this.orderDiagCode = orderDiagCode;
    }

    public String getOrderDiagText() {
        return orderDiagText;
    }

    public void setOrderDiagText(String orderDiagText) {
        this.orderDiagText = orderDiagText;
    }

    public String getOrderComment() {
        return orderComment;
    }

    public void setOrderComment(String orderComment) {
        this.orderComment = orderComment;
    }

    public String getOrderDepartmentName() {
        return orderDepartmentName;
    }

    public void setOrderDepartmentName(String orderDepartmentName) {
        this.orderDepartmentName = orderDepartmentName;
    }

    public String getOrderDepartmentMisCode() {
        return orderDepartmentMisCode;
    }

    public void setOrderDepartmentMisCode(String orderDepartmentMisCode) {
        this.orderDepartmentMisCode = orderDepartmentMisCode;
    }

    public String getOrderDoctorFamily() {
        return orderDoctorFamily;
    }

    public void setOrderDoctorFamily(String orderDoctorFamily) {
        this.orderDoctorFamily = orderDoctorFamily;
    }

    public String getOrderDoctorName() {
        return orderDoctorName;
    }

    public void setOrderDoctorName(String orderDoctorName) {
        this.orderDoctorName = orderDoctorName;
    }

    public String getOrderDoctorPatronum() {
        return orderDoctorPatronum;
    }

    public void setOrderDoctorPatronum(String orderDoctorPatronum) {
        this.orderDoctorPatronum = orderDoctorPatronum;
    }

    public int getOrderDoctorMisId() {
        return orderDoctorMisId;
    }

    public void setOrderDoctorMisId(int orderDoctorMisId) {
        this.orderDoctorMisId = orderDoctorMisId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DiagnosticRequestInfo{");
        sb.append("orderMisId=").append(orderMisId);
        sb.append(", orderCaseId='").append(orderCaseId).append('\'');
        sb.append(", orderFinanceId=").append(orderFinanceId);
        sb.append(", orderMisDate=").append(orderMisDate);
        sb.append(", orderPregnatMin=").append(orderPregnatMin);
        sb.append(", orderPregnatMax=").append(orderPregnatMax);
        sb.append(", orderDiagCode='").append(orderDiagCode).append('\'');
        sb.append(", orderDiagText='").append(orderDiagText).append('\'');
        sb.append(", orderComment='").append(orderComment).append('\'');
        sb.append(", orderDepartmentName='").append(orderDepartmentName).append('\'');
        sb.append(", orderDepartmentMisCode='").append(orderDepartmentMisCode).append('\'');
        sb.append(", orderDoctorFamily='").append(orderDoctorFamily).append('\'');
        sb.append(", orderDoctorName='").append(orderDoctorName).append('\'');
        sb.append(", orderDoctorPatronum='").append(orderDoctorPatronum).append('\'');
        sb.append(", orderDoctorMisId=").append(orderDoctorMisId);
        sb.append('}');
        return sb.toString();
    }

    public Staff getOrderDoctorMis() {
        return orderDoctorMis;
    }

    public void setOrderDoctorMis(Staff orderDoctorMis) {
        this.orderDoctorMis = orderDoctorMis;
    }
}
