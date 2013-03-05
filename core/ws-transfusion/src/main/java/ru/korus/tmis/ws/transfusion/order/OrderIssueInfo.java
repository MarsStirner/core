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
     * @return the componentId
     */
    public Integer getComponentId() {
        return componentId;
    }

    /**
     * @param componentId
     *            the componentId to set
     */
    public void setComponentId(final Integer componentId) {
        this.componentId = componentId;
    }

    /**
     * @return the number
     */
    public String getNumber() {
        return number;
    }

    /**
     * @param number
     *            the number to set
     */
    public void setNumber(final String number) {
        this.number = number;
    }

    /**
     * @return the componentTypeId
     */
    public Integer getComponentTypeId() {
        return componentTypeId;
    }

    /**
     * @param componentTypeId
     *            the componentTypeId to set
     */
    public void setComponentTypeId(final Integer componentTypeId) {
        this.componentTypeId = componentTypeId;
    }

    /**
     * @return the bloodGroupId
     */
    public Integer getBloodGroupId() {
        return bloodGroupId;
    }

    /**
     * @param bloodGroupId
     *            the bloodGroupId to set
     */
    public void setBloodGroupId(final Integer bloodGroupId) {
        this.bloodGroupId = bloodGroupId;
    }

    /**
     * @return the rhesusFactorId
     */
    public Integer getRhesusFactorId() {
        return rhesusFactorId;
    }

    /**
     * @param rhesusFactorId
     *            the rhesusFactorId to set
     */
    public void setRhesusFactorId(final Integer rhesusFactorId) {
        this.rhesusFactorId = rhesusFactorId;
    }

    /**
     * @return the volume
     */
    public Integer getVolume() {
        return volume;
    }

    /**
     * @param volume
     *            the volume to set
     */
    public void setVolume(final Integer volume) {
        this.volume = volume;
    }

    /**
     * @return the doseCount
     */
    public Double getDoseCount() {
        return doseCount;
    }

    /**
     * @param doseCount
     *            the doseCount to set
     */
    public void setDoseCount(final Double doseCount) {
        this.doseCount = doseCount;
    }

    /**
     * @return the donorIdid
     */
    public Integer getDonorId() {
        return donorId;
    }

    /**
     * @param donorId
     *            the donorId to set
     */
    public void setDonorId(final Integer donorId) {
        this.donorId = donorId;
    }

}
