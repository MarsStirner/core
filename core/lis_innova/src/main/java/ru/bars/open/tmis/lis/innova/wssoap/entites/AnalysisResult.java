package ru.bars.open.tmis.lis.innova.wssoap.entites;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Date;
import java.util.List;

@XmlType(propOrder = {
        "indicatorName",
        "indicatorCode",
        "deviceName",
        "valueType",
        "resultValueText",
        "imageValues",
        "microValues",
        "microSensitivity",
        "resultNormString",
        "resultNormalityIndex",
        "resultUnit",
        "resultSignDate",
        "resultStatus",
        "resultComment"
})
@XmlAccessorType(XmlAccessType.FIELD)
public class AnalysisResult {

    /**
     * Код методики/показателя (Основной)
     */
    @XmlElement(name = "indicatorCode", required = true)
    private String indicatorCode;

    /**
     * Название методики/показателя
     */
    @XmlElement(name = "indicatorName")
    private String indicatorName;

    /**
     * Название прибора, на котором выполнялось исследование
     */
    @XmlElement(name = "deviceName")
    private String deviceName;

    /**
     * Тип значения
     */
    @XmlElement(name = "valueType", required = true)
    private int valueType;

    /**
     * Значение
     */
    @XmlElement(name = "resultValueText", required = true)
    private String resultValueText;

    /**
     * Список изображений
     */
    @XmlElement(name = "imageValues")
    private List<ImageValue> imageValues;

    /**
     * Результаты исследования микроорганизмов
     */
    @XmlElement(name = "microValues")
    private List<MicroOrganismResult> microValues;

    /**
     * Результаты чувствительности микроорганизмов к антибиотикам
     */
    @XmlElement(name = "microSensitivity")
    private List<AntibioticSensitivity> microSensitivity;

    /**
     * Норма (для числового результата)
     */
    @XmlElement(name = "resultNormString")
    private String resultNormString;

    /**
     * Значение результата относительно нормы (не опр./норма/ниже критической/ниже/выше критической/выше)
     */
    @XmlElement(name = "resultNormalityIndex")
    private Float resultNormalityIndex;

    /**
     * Единица измерения
     */
    @XmlElement(name = "resultUnit")
    private String resultUnit;

    /**
     * Дата выполнения
     */
    @XmlElement(name = "resultSignDate", required = true)
    private Date resultSignDate;

    /**
     * Статус результата. В это поле выводится причина в случае отсутствия результата
     */
    @XmlElement(name = "resultStatus")
    private String resultStatus;

    /**
     * Комментарий к методике
     */
    @XmlElement(name = "resultComment")
    private String resultComment;

    public String getIndicatorCode() {
        return indicatorCode;
    }

    public void setIndicatorCode(final String indicatorCode) {
        this.indicatorCode = indicatorCode;
    }

    public String getIndicatorName() {
        return indicatorName;
    }

    public void setIndicatorName(final String indicatorName) {
        this.indicatorName = indicatorName;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(final String deviceName) {
        this.deviceName = deviceName;
    }

    public int getValueType() {
        return valueType;
    }

    public void setValueType(final int valueType) {
        this.valueType = valueType;
    }

    public String getResultValueText() {
        return resultValueText;
    }

    public void setResultValueText(final String resultValueText) {
        this.resultValueText = resultValueText;
    }

    public List<ImageValue> getImageValues() {
        return imageValues;
    }

    public void setImageValues(final List<ImageValue> imageValues) {
        this.imageValues = imageValues;
    }

    public List<MicroOrganismResult> getMicroValues() {
        return microValues;
    }

    public void setMicroValues(final List<MicroOrganismResult> microValues) {
        this.microValues = microValues;
    }

    public List<AntibioticSensitivity> getMicroSensitivity() {
        return microSensitivity;
    }

    public void setMicroSensitivity(final List<AntibioticSensitivity> microSensitivity) {
        this.microSensitivity = microSensitivity;
    }

    public String getResultNormString() {
        return resultNormString;
    }

    public void setResultNormString(final String resultNormString) {
        this.resultNormString = resultNormString;
    }

    public Float getResultNormalityIndex() {
        return resultNormalityIndex;
    }

    public void setResultNormalityIndex(final Float resultNormalityIndex) {
        this.resultNormalityIndex = resultNormalityIndex;
    }

    public String getResultUnit() {
        return resultUnit;
    }

    public void setResultUnit(final String resultUnit) {
        this.resultUnit = resultUnit;
    }

    public Date getResultSignDate() {
        return resultSignDate;
    }

    public void setResultSignDate(final Date resultSignDate) {
        this.resultSignDate = resultSignDate;
    }

    public String getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(final String resultStatus) {
        this.resultStatus = resultStatus;
    }

    public String getResultComment() {
        return resultComment;
    }

    public void setResultComment(final String resultComment) {
        this.resultComment = resultComment;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AnalysisResult{");
        sb.append("indicatorCode='").append(indicatorCode).append('\'');
        sb.append(", indicatorName='").append(indicatorName).append('\'');
        sb.append(", deviceName='").append(deviceName).append('\'');
        sb.append(", valueType=").append(valueType);
        sb.append(", resultValueText='").append(resultValueText).append('\'');
        sb.append(", imageValues=").append(imageValues);
        sb.append(", microValues=").append(microValues);
        sb.append(", microSensitivity=").append(microSensitivity);
        sb.append(", resultNormString='").append(resultNormString).append('\'');
        sb.append(", resultNormalityIndex=").append(resultNormalityIndex);
        sb.append(", resultUnit='").append(resultUnit).append('\'');
        sb.append(", resultSignDate=").append(resultSignDate);
        sb.append(", resultStatus='").append(resultStatus).append('\'');
        sb.append(", resultComment='").append(resultComment).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
