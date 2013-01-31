package ru.korus.tmis.ws.transfusion.order;
/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        15.01.2013, 16:28:53 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */

/**
 * Сообщение о результатах выполнения требования 
 */
public class OrderIssueInfo {
    
    /**
     * Идентификатор требования на выдачу КК (Action.id)
     */
    private Integer requestId;
    
    /**
     * Фактическая дата/время выдачи КК в формате "yyyy-MM-dd" 
     */
    private String factDate; 
    
    /**
     * Идентификатор компонента крови
     */
    private Integer componentId;
    
    /**
     * Номер компонента крови (номер, зашитый в ШК)
     */
    private String number; 
    
    /**
     * идентификатор типа компонента крови
     */
    private Integer componentTypeId;
    
    /**
     * идентификатор группы крови донора (1- первая 0 (I), 2 – вторая А (II), 3 – третья В (III), 4 – четвертая АВ (IV))
     */
    private Integer bloodGroupId;
    
    /**
     * идентификатор резус-фактора донора (0 – Положительный, 1 - Отрицательный)
     */
    private Integer rhesusFactorId;
    
    /**
     * объем компонента крови
     */
    private Integer volume;
    
    /**
     * количество донорских доз
     */
    private Double doseCount;
    
    /**
     * код донора
     */
    private Integer donorId;
    
    /**
     * произвольный текстовый комментарий
     */
    private String orderComment;
    

    /**
     * @return the requestId
     */
    public Integer getRequestId() {
        return requestId;
    }

    /**
     * @param requestId the requestId to set
     */
    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    /**
     * @return the factDate
     */
    public String getFactDate() {
        return factDate;
    }

    /**
     * @param factDate the factDate to set
     */
    public void setFactDate(String factDate) {
        this.factDate = factDate;
    }

    /**
     * @return the componentId
     */
    public Integer getComponentId() {
        return componentId;
    }

    /**
     * @param componentId the componentId to set
     */
    public void setComponentId(Integer componentId) {
        this.componentId = componentId;
    }

    /**
     * @return the number
     */
    public String getNumber() {
        return number;
    }

    /**
     * @param number the number to set
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * @return the componentTypeId
     */
    public Integer getComponentTypeId() {
        return componentTypeId;
    }

    /**
     * @param componentTypeId the componentTypeId to set
     */
    public void setComponentTypeId(Integer componentTypeId) {
        this.componentTypeId = componentTypeId;
    }

    /**
     * @return the bloodGroupId
     */
    public Integer getBloodGroupId() {
        return bloodGroupId;
    }

    /**
     * @param bloodGroupId the bloodGroupId to set
     */
    public void setBloodGroupId(Integer bloodGroupId) {
        this.bloodGroupId = bloodGroupId;
    }

    /**
     * @return the rhesusFactorId
     */
    public Integer getRhesusFactorId() {
        return rhesusFactorId;
    }

    /**
     * @param rhesusFactorId the rhesusFactorId to set
     */
    public void setRhesusFactorId(Integer rhesusFactorId) {
        this.rhesusFactorId = rhesusFactorId;
    }

    /**
     * @return the volume
     */
    public Integer getVolume() {
        return volume;
    }

    /**
     * @param volume the volume to set
     */
    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    /**
     * @return the doseCount
     */
    public Double getDoseCount() {
        return doseCount;
    }

    /**
     * @param doseCount the doseCount to set
     */
    public void setDoseCount(Double doseCount) {
        this.doseCount = doseCount;
    }

    /**
     * @return the donorIdid
     */
    public Integer getDonorId() {
        return donorId;
    }

    /**
     * @param donorId the donorId to set
     */
    public void setDonorId(Integer donorId) {
        this.donorId = donorId;
    }

    /**
     * @return the orderComment
     */
    public String getOrderComment() {
        return orderComment;
    }

    /**
     * @param orderComment the orderComment to set
     */
    public void setOrderComment(String orderComment) {
        this.orderComment = orderComment;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("OrderIssueInfo [requestId=");
        builder.append(requestId);
        builder.append(", factDate=");
        builder.append(factDate);
        builder.append(", componentId=");
        builder.append(componentId);
        builder.append(", number=");
        builder.append(number);
        builder.append(", componentTypeId=");
        builder.append(componentTypeId);
        builder.append(", bloodGroupId=");
        builder.append(bloodGroupId);
        builder.append(", rhesusFactorId=");
        builder.append(rhesusFactorId);
        builder.append(", volume=");
        builder.append(volume);
        builder.append(", doseCount=");
        builder.append(doseCount);
        builder.append(", donorId=");
        builder.append(donorId);
        builder.append(", orderComment=");
        builder.append(orderComment);
        builder.append("]");
        return builder.toString();
    }
  

}
