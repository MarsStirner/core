package ru.korus.tmis.ws.transfusion.procedure;

import java.util.Date;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        30.01.2013, 15:36:31 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */

/**
 * 
 */
public class EritrocyteMass {
    /**
     * производитель эритроцитарной массы
     */
    private String maker;
    /**
     * номер пакета эритроцитарной массы
     */
    private String number;
    /**
     * идентификатор группы крови эритроцитарной массы (1- первая 0 (I), 2; вторая А (II), 3; третья В (III), 4; четвертая АВ (IV))
     */
    private Integer bloodGroupId;
    /**
     * идентификатор резус-фактора эритроцитарной массы (0; Положительный, 1 - Отрицательный)
     */
    private Integer rhesusFactorId;
    /**
     * объем эритроцитарной массы
     */
    private Double volume;
    /**
     * дата изготовления
     */
    private Date productionDate;
    /**
     * срок годности
     */
    private Date expirationDate;
    /**
     * гематокрит эритроцитарной массы
     */
    private Double ht;
    /**
     * объем добавленного физ. раствора
     */
    private Double salineVolume;
    /**
     * финальный гематокрит
     */
    private Double finalHt;
    /**
     * @return the maker
     */
    public String getMaker() {
        return maker;
    }
    /**
     * @param maker the maker to set
     */
    public void setMaker(String maker) {
        this.maker = maker;
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
    public Double getVolume() {
        return volume;
    }
    /**
     * @param volume the volume to set
     */
    public void setVolume(Double volume) {
        this.volume = volume;
    }
    /**
     * @return the productionDate
     */
    public Date getProductionDate() {
        return productionDate;
    }
    /**
     * @param productionDate the productionDate to set
     */
    public void setProductionDate(Date productionDate) {
        this.productionDate = productionDate;
    }
    /**
     * @return the expirationDate
     */
    public Date getExpirationDate() {
        return expirationDate;
    }
    /**
     * @param expirationDate the expirationDate to set
     */
    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
    /**
     * @return the ht
     */
    public Double getHt() {
        return ht;
    }
    /**
     * @param ht the ht to set
     */
    public void setHt(Double ht) {
        this.ht = ht;
    }
    /**
     * @return the salineVolume
     */
    public Double getSalineVolume() {
        return salineVolume;
    }
    /**
     * @param salineVolume the salineVolume to set
     */
    public void setSalineVolume(Double salineVolume) {
        this.salineVolume = salineVolume;
    }
    /**
     * @return the finalHt
     */
    public Double getFinalHt() {
        return finalHt;
    }
    /**
     * @param finalHt the finalHt to set
     */
    public void setFinalHt(Double finalHt) {
        this.finalHt = finalHt;
    }

}
