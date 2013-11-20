package ru.korus.tmis.laboratory.bak.model;

import org.joda.time.DateTime;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        20.11.13, 19:51 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public class BiomaterialInfo {
    private String orderBiomaterialCode;
    private String orderBiomaterialname;
    private String orderBarCode;
    private int orderBarCodePeriod;
    private int orderTakenTissueId;
    private DateTime orderProbeDate;
    private String orderBiomaterialComment;

    public BiomaterialInfo() {
    }

    public BiomaterialInfo(String orderBiomaterialCode, String orderBiomaterialname, String orderBarCode, int orderBarCodePeriod, int orderTakenTissueId, DateTime orderProbeDate, String orderBiomaterialComment) {
        this.orderBiomaterialCode = orderBiomaterialCode;
        this.orderBiomaterialname = orderBiomaterialname;
        this.orderBarCode = orderBarCode;
        this.orderBarCodePeriod = orderBarCodePeriod;
        this.orderTakenTissueId = orderTakenTissueId;
        this.orderProbeDate = orderProbeDate;
        this.orderBiomaterialComment = orderBiomaterialComment;
    }

    public String getOrderBiomaterialCode() {
        return orderBiomaterialCode;
    }

    public void setOrderBiomaterialCode(String orderBiomaterialCode) {
        this.orderBiomaterialCode = orderBiomaterialCode;
    }

    public String getOrderBiomaterialname() {
        return orderBiomaterialname;
    }

    public void setOrderBiomaterialname(String orderBiomaterialname) {
        this.orderBiomaterialname = orderBiomaterialname;
    }

    public String getOrderBarCode() {
        return orderBarCode;
    }

    public void setOrderBarCode(String orderBarCode) {
        this.orderBarCode = orderBarCode;
    }

    public int getOrderBarCodePeriod() {
        return orderBarCodePeriod;
    }

    public void setOrderBarCodePeriod(int orderBarCodePeriod) {
        this.orderBarCodePeriod = orderBarCodePeriod;
    }

    public int getOrderTakenTissueId() {
        return orderTakenTissueId;
    }

    public void setOrderTakenTissueId(int orderTakenTissueId) {
        this.orderTakenTissueId = orderTakenTissueId;
    }

    public DateTime getOrderProbeDate() {
        return orderProbeDate;
    }

    public void setOrderProbeDate(DateTime orderProbeDate) {
        this.orderProbeDate = orderProbeDate;
    }

    public String getOrderBiomaterialComment() {
        return orderBiomaterialComment;
    }

    public void setOrderBiomaterialComment(String orderBiomaterialComment) {
        this.orderBiomaterialComment = orderBiomaterialComment;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BiomaterialInfo{");
        sb.append("orderBiomaterialCode='").append(orderBiomaterialCode).append('\'');
        sb.append(", orderBiomaterialname='").append(orderBiomaterialname).append('\'');
        sb.append(", orderBarCode='").append(orderBarCode).append('\'');
        sb.append(", orderBarCodePeriod=").append(orderBarCodePeriod);
        sb.append(", orderTakenTissueId=").append(orderTakenTissueId);
        sb.append(", orderProbeDate=").append(orderProbeDate);
        sb.append(", orderBiomaterialComment='").append(orderBiomaterialComment).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
