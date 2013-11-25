package ru.korus.tmis.laboratory.bak.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        20.11.13, 19:52 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public class OrderInfo {
    private String diagnosticCode;
    private String diagnosticName;
    private Priority orderPriority;

    public enum Priority {
        NORMAL, URGENT
    }

    private List<IndicatorMetodic> indicatorList = new LinkedList<IndicatorMetodic>();

    public OrderInfo() {
    }

    public String getDiagnosticCode() {
        return diagnosticCode;
    }

    public void setDiagnosticCode(String diagnosticCode) {
        this.diagnosticCode = diagnosticCode;
    }

    public String getDiagnosticName() {
        return diagnosticName;
    }

    public void setDiagnosticName(String diagnosticName) {
        this.diagnosticName = diagnosticName;
    }

    public Priority getOrderPriority() {
        return orderPriority;
    }

    public void setOrderPriority(Priority orderPriority) {
        this.orderPriority = orderPriority;
    }

    public List<IndicatorMetodic> getIndicatorList() {
        return indicatorList;
    }

    public void addIndicatorList(IndicatorMetodic indicator) {
        this.indicatorList.add(indicator);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OrderInfo{");
        sb.append("diagnosticCode='").append(diagnosticCode).append('\'');
        sb.append(", diagnosticName='").append(diagnosticName).append('\'');
        sb.append(", orderPriority='").append(orderPriority).append('\'');
        sb.append(", indicatorList=").append(indicatorList);
        sb.append('}');
        return sb.toString();
    }
}
