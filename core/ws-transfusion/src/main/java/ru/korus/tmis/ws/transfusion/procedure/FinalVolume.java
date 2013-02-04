package ru.korus.tmis.ws.transfusion.procedure;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        30.01.2013, 15:43:35 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */

/**
 * 
 */
public class FinalVolume {
    /**
     * длительность афереза (фактический результат)
     */
    private Double time;

    /**
     * объем антикоагулянта (фактический результат)
     */
    private Double anticoagulantVolume;

    /**
     * inlet (фактический результат)
     */
    private Double inletVolume;

    /**
     * plasma (фактический результат)
     */
    private Double plasmaVolume;

    /**
     * collect(фактический результат)
     */
    private Double collectVolume;

    /**
     * AC в collect(фактический результат)
     */
    private Double anticoagulantInCollect;

    /**
     * AC в collect(фактический результат)
     */
    private Double anticoagulantInPlasma;

    /**
     * @return the time
     */
    public Double getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(Double time) {
        this.time = time;
    }

    /**
     * @return the anticoagulantVolume
     */
    public Double getAnticoagulantVolume() {
        return anticoagulantVolume;
    }

    /**
     * @param anticoagulantVolume the anticoagulantVolume to set
     */
    public void setAnticoagulantVolume(Double anticoagulantVolume) {
        this.anticoagulantVolume = anticoagulantVolume;
    }

    /**
     * @return the inletVolume
     */
    public Double getInletVolume() {
        return inletVolume;
    }

    /**
     * @param inletVolume the inletVolume to set
     */
    public void setInletVolume(Double inletVolume) {
        this.inletVolume = inletVolume;
    }

    /**
     * @return the plasmaVolume
     */
    public Double getPlasmaVolume() {
        return plasmaVolume;
    }

    /**
     * @param plasmaVolume the plasmaVolume to set
     */
    public void setPlasmaVolume(Double plasmaVolume) {
        this.plasmaVolume = plasmaVolume;
    }

    /**
     * @return the collectVolume
     */
    public Double getCollectVolume() {
        return collectVolume;
    }

    /**
     * @param collectVolume the collectVolume to set
     */
    public void setCollectVolume(Double collectVolume) {
        this.collectVolume = collectVolume;
    }

    /**
     * @return the anticoagulantInCollect
     */
    public Double getAnticoagulantInCollect() {
        return anticoagulantInCollect;
    }

    /**
     * @param anticoagulantInCollect the anticoagulantInCollect to set
     */
    public void setAnticoagulantInCollect(Double anticoagulantInCollect) {
        this.anticoagulantInCollect = anticoagulantInCollect;
    }

    /**
     * @return the anticoagulantInPlasma
     */
    public Double getAnticoagulantInPlasma() {
        return anticoagulantInPlasma;
    }

    /**
     * @param anticoagulantInPlasma the anticoagulantInPlasma to set
     */
    public void setAnticoagulantInPlasma(Double anticoagulantInPlasma) {
        this.anticoagulantInPlasma = anticoagulantInPlasma;
    }
    

}
