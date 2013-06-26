package ru.korus.tmis.ws.transfusion.procedure;

import java.util.Date;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        30.01.2013, 15:19:40 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */

/**
 * 
 */
public class ProcedureInfo {

    /**
     * идентификатор лечебной процедуры
     */
    private Integer id;

    /**
     * время окончания лечебной процедуры
     */
    private Date factDate;

    /**
     * противопоказания к проведению процедуры
     */
    private String contraindication;

    /**
     * пульс до процедуры
     */
    private String beforeHemodynamicsPulse;

    /**
     * пульс после процедуры
     */
    private String afterHemodynamicsPulse;

    /**
     * артериальное давление до процедуры
     */
    private String beforeHemodynamicsArterialPressure;

    /**
     * артериальное давление после процедуры
     */
    private String afterHemodynamicsArterialPressure;

    /**
     * температура до процедуры
     */
    private String beforeHemodynamicsTemperature;

    /**
     * температура после процедуры
     */
    private String afterHemodynamicsTemperature;

    /**
     * осложнения
     */
    private String complications;

    /**
     * объем афереза - инициально (параметры процедуры)
     */
    private Double initialVolume;

    /**
     * объем афереза; изменения (параметры процедуры)
     */
    private Double changeVolume;

    /**
     * обработанный TBV - инициально (параметры процедуры)
     */
    private String initialTbv;

    /**
     * обработанный TBV - изменения (параметры процедуры)
     */
    private String changeTbv;

    /**
     * скорость забора; инициально (параметры процедуры)
     */
    private String initialSpeed;

    /**
     * скорость забора; изменения (параметры процедуры)
     */
    private String changeSpeed;

    /**
     * inletAcRatio; инициально (параметры процедуры)
     */
    private String initialInletAcRatio;

    /**
     * inletAcRatio; изменения (параметры процедуры )
     */
    private String changeInletAcRatio;

    /**
     * время афереза; инициально(параметры процедуры)
     */
    private String initialTime;

    /**
     * время афереза; изменения (параметры процедуры)
     */
    private String changeTime;

    /**
     * объем продукта афереза - инициально (параметры процедуры)
     */
    private Double initialProductVolume;

    /**
     * объем продукта афереза; изменения (параметры процедуры)
     */
    private Double changeProductVolume;

    /**
     * введено ACD (баланс жидкостей)
     */
    private String acdLoad;

    /**
     * введено NaCl,9% (баланс жидкостей)
     */
    private String naClLoad;

    /**
     * введено Ca++ (баланс жидкостей)
     */
    private String caLoad;

    /**
     * введено другое (баланс жидкостей)
     */
    private String otherLoad;

    /**
     * введено всего (баланс жидкостей)
     */
    private String totalLoad;

    /**
     * удалено в пакете (баланс жидкостей)
     */
    private String packRemove;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(final Integer id) {
        this.id = id;
    }

    /**
     * @return the factDate
     */
    public Date getFactDate() {
        return factDate;
    }

    /**
     * @param factDate
     *            the factDate to set
     */
    public void setFactDate(final Date factDate) {
        this.factDate = factDate;
    }

    /**
     * @return the contraindication
     */
    public String getContraindication() {
        return contraindication;
    }

    /**
     * @param contraindication
     *            the contraindication to set
     */
    public void setContraindication(final String contraindication) {
        this.contraindication = contraindication;
    }

    /**
     * @return the beforeHemodynamicsPulse
     */
    public String getBeforeHemodynamicsPulse() {
        return beforeHemodynamicsPulse;
    }

    /**
     * @param beforeHemodynamicsPulse
     *            the beforeHemodynamicsPulse to set
     */
    public void setBeforeHemodynamicsPulse(final String beforeHemodynamicsPulse) {
        this.beforeHemodynamicsPulse = beforeHemodynamicsPulse;
    }

    /**
     * @return the afterHemodynamicsPulse
     */
    public String getAfterHemodynamicsPulse() {
        return afterHemodynamicsPulse;
    }

    /**
     * @param afterHemodynamicsPulse
     *            the afterHemodynamicsPulse to set
     */
    public void setAfterHemodynamicsPulse(final String afterHemodynamicsPulse) {
        this.afterHemodynamicsPulse = afterHemodynamicsPulse;
    }

    /**
     * @return the beforeHemodynamicsArterialPressure
     */
    public String getBeforeHemodynamicsArterialPressure() {
        return beforeHemodynamicsArterialPressure;
    }

    /**
     * @param beforeHemodynamicsArterialPressure
     *            the beforeHemodynamicsArterialPressure to set
     */
    public void setBeforeHemodynamicsArterialPressure(final String beforeHemodynamicsArterialPressure) {
        this.beforeHemodynamicsArterialPressure = beforeHemodynamicsArterialPressure;
    }

    /**
     * @return the afterHemodynamicsArterialPressure
     */
    public String getAfterHemodynamicsArterialPressure() {
        return afterHemodynamicsArterialPressure;
    }

    /**
     * @param afterHemodynamicsArterialPressure
     *            the afterHemodynamicsArterialPressure to set
     */
    public void setAfterHemodynamicsArterialPressure(final String afterHemodynamicsArterialPressure) {
        this.afterHemodynamicsArterialPressure = afterHemodynamicsArterialPressure;
    }

    /**
     * @return the beforeHemodynamicsTemperature
     */
    public String getBeforeHemodynamicsTemperature() {
        return beforeHemodynamicsTemperature;
    }

    /**
     * @param beforeHemodynamicsTemperature
     *            the beforeHemodynamicsTemperature to set
     */
    public void setBeforeHemodynamicsTemperature(final String beforeHemodynamicsTemperature) {
        this.beforeHemodynamicsTemperature = beforeHemodynamicsTemperature;
    }

    /**
     * @return the afterHemodynamicsTemperature
     */
    public String getAfterHemodynamicsTemperature() {
        return afterHemodynamicsTemperature;
    }

    /**
     * @param afterHemodynamicsTemperature
     *            the afterHemodynamicsTemperature to set
     */
    public void setAfterHemodynamicsTemperature(final String afterHemodynamicsTemperature) {
        this.afterHemodynamicsTemperature = afterHemodynamicsTemperature;
    }

    /**
     * @return the complications
     */
    public String getComplications() {
        return complications;
    }

    /**
     * @param complications
     *            the complications to set
     */
    public void setComplications(final String complications) {
        this.complications = complications;
    }

    /**
     * @return the initialVolume
     */
    public Double getInitialVolume() {
        return initialVolume;
    }

    /**
     * @param initialVolume
     *            the initialVolume to set
     */
    public void setInitialVolume(final Double initialVolume) {
        this.initialVolume = initialVolume;
    }

    /**
     * @return the changeVolume
     */
    public Double getChangeVolume() {
        return changeVolume;
    }

    /**
     * @param changeVolume
     *            the changeVolume to set
     */
    public void setChangeVolume(final Double changeVolume) {
        this.changeVolume = changeVolume;
    }

    /**
     * @return the initialTbv
     */
    public String getInitialTbv() {
        return initialTbv;
    }

    /**
     * @param initialTbv
     *            the initialTbv to set
     */
    public void setInitialTbv(final String initialTbv) {
        this.initialTbv = initialTbv;
    }

    /**
     * @return the changeTbv
     */
    public String getChangeTbv() {
        return changeTbv;
    }

    /**
     * @param changeTbv
     *            the changeTbv to set
     */
    public void setChangeTbv(final String changeTbv) {
        this.changeTbv = changeTbv;
    }

    /**
     * @return the initialSpeed
     */
    public String getInitialSpeed() {
        return initialSpeed;
    }

    /**
     * @param initialSpeed
     *            the initialSpeed to set
     */
    public void setInitialSpeed(final String initialSpeed) {
        this.initialSpeed = initialSpeed;
    }

    /**
     * @return the changeSpeed
     */
    public String getChangeSpeed() {
        return changeSpeed;
    }

    /**
     * @param changeSpeed
     *            the changeSpeed to set
     */
    public void setChangeSpeed(final String changeSpeed) {
        this.changeSpeed = changeSpeed;
    }

    /**
     * @return the initialInletAcRatio
     */
    public String getInitialInletAcRatio() {
        return initialInletAcRatio;
    }

    /**
     * @param initialInletAcRatio
     *            the initialInletAcRatio to set
     */
    public void setInitialInletAcRatio(final String initialInletAcRatio) {
        this.initialInletAcRatio = initialInletAcRatio;
    }

    /**
     * @return the changeInletAcRatio
     */
    public String getChangeInletAcRatio() {
        return changeInletAcRatio;
    }

    /**
     * @param changeInletAcRatio
     *            the changeInletAcRatio to set
     */
    public void setChangeInletAcRatio(final String changeInletAcRatio) {
        this.changeInletAcRatio = changeInletAcRatio;
    }

    /**
     * @return the initialTime
     */
    public String getInitialTime() {
        return initialTime;
    }

    /**
     * @param initialTime
     *            the initialTime to set
     */
    public void setInitialTime(final String initialTime) {
        this.initialTime = initialTime;
    }

    /**
     * @return the changeTime
     */
    public String getChangeTime() {
        return changeTime;
    }

    /**
     * @param changeTime
     *            the changeTime to set
     */
    public void setChangeTime(final String changeTime) {
        this.changeTime = changeTime;
    }

    /**
     * @return the initialProductVolume
     */
    public Double getInitialProductVolume() {
        return initialProductVolume;
    }

    /**
     * @param initialProductVolume
     *            the initialProductVolume to set
     */
    public void setInitialProductVolume(final Double initialProductVolume) {
        this.initialProductVolume = initialProductVolume;
    }

    /**
     * @return the changeProductVolume
     */
    public Double getChangeProductVolume() {
        return changeProductVolume;
    }

    /**
     * @param changeProductVolume
     *            the changeProductVolume to set
     */
    public void setChangeProductVolume(final Double changeProductVolume) {
        this.changeProductVolume = changeProductVolume;
    }

    /**
     * @return the acdLoad
     */
    public String getAcdLoad() {
        return acdLoad;
    }

    /**
     * @param acdLoad
     *            the acdLoad to set
     */
    public void setAcdLoad(final String acdLoad) {
        this.acdLoad = acdLoad;
    }

    /**
     * @return the naClLoad
     */
    public String getNaClLoad() {
        return naClLoad;
    }

    /**
     * @param naClLoad
     *            the naClLoad to set
     */
    public void setNaClLoad(final String naClLoad) {
        this.naClLoad = naClLoad;
    }

    /**
     * @return the caLoad
     */
    public String getCaLoad() {
        return caLoad;
    }

    /**
     * @param caLoad
     *            the caLoad to set
     */
    public void setCaLoad(final String caLoad) {
        this.caLoad = caLoad;
    }

    /**
     * @return the otherLoad
     */
    public String getOtherLoad() {
        return otherLoad;
    }

    /**
     * @param otherLoad
     *            the otherLoad to set
     */
    public void setOtherLoad(final String otherLoad) {
        this.otherLoad = otherLoad;
    }

    /**
     * @return the totalLoad
     */
    public String getTotalLoad() {
        return totalLoad;
    }

    /**
     * @param totalLoad
     *            the totalLoad to set
     */
    public void setTotalLoad(final String totalLoad) {
        this.totalLoad = totalLoad;
    }

    /**
     * @return the packRemove
     */
    public String getPackRemove() {
        return packRemove;
    }

    /**
     * @param packRemove
     *            the packRemove to set
     */
    public void setPackRemove(final String packRemove) {
        this.packRemove = packRemove;
    }

    /**
     * @return the otherRemove
     */
    public String getOtherRemove() {
        return otherRemove;
    }

    /**
     * @param otherRemove
     *            the otherRemove to set
     */
    public void setOtherRemove(final String otherRemove) {
        this.otherRemove = otherRemove;
    }

    /**
     * @return the totalRemove
     */
    public String getTotalRemove() {
        return totalRemove;
    }

    /**
     * @param totalRemove
     *            the totalRemove to set
     */
    public void setTotalRemove(final String totalRemove) {
        this.totalRemove = totalRemove;
    }

    /**
     * @return the balance
     */
    public String getBalance() {
        return balance;
    }

    /**
     * @param balance
     *            the balance to set
     */
    public void setBalance(final String balance) {
        this.balance = balance;
    }

    /**
     * удалено другое (баланс жидкостей)
     */
    private String otherRemove;

    /**
     * удалено всего (баланс жидкостей)
     */
    private String totalRemove;

    /**
     * баланс (баланс жидкостей)
     */
    private String balance;

}
