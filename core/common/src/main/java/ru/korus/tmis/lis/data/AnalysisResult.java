package ru.korus.tmis.lis.data;

import javax.xml.bind.annotation.XmlElement;
import java.util.Date;
import java.util.List;

public class AnalysisResult {

    /**
     * Код методики/показателя (Основной)
     */
    private String code;

    /**
     * Название методики/показателя
     */
    private String name;

    /**
     * Название прибора, на котором выполнялось исследование
     */
    private String deviceName;

    /**
     * Тип значения
     */
    private ValueType valueType;

    /**
     * Значение
     */
    private String textValue;

    /**
     * Список изображений
     */
    private List<ImageValue> imageValues;

    /**
     * Результаты исследования микроорганизмов
     */
    private List<MicroOrganismResult> microValues;

    /**
     * Результаты чувствительности микроорганизмов к антибиотикам
     */
    private List<AntibioticSensitivity> microSensitivity;

    /**
     * Норма (для числового результата)
     */
    private String norm;

    /**
     * Значение результата относительно нормы (не опр./норма/ниже критической/ниже/выше критической/выше)
     */
    private float normalityIndex;

    /**
     * Единица измерения
     */
    private String unitCode;

    /**
     * Дата выполнения
     */
    private Date endDate;

    /**
     * Статус результата. В это поле выводится причина в случае отсутствия результата
     */
    private String resultStatus;

    /**
     * Комментарий к методике
     */
    private String Comment;

    public List<AntibioticSensitivity> getMicroSensitivity() {
        return microSensitivity;
    }

    public void setMicroSensitivity(List<AntibioticSensitivity> microSensitivity) {
        this.microSensitivity = microSensitivity;
    }

    public enum ValueType {
        NUMERIC,
        DIAPASON,
        TEXT,
        IMAGE
    }

    @XmlElement(name = "indicatorName")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name = "indicatorCode", required = true)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @XmlElement(name = "deviceName")
    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    @XmlElement(required = true)
    public ValueType getValueType() {
        return valueType;
    }

    public void setValueType(ValueType valueType) {
        this.valueType = valueType;
    }

    @XmlElement(name = "resultTextValue", required = true)
    public String getTextValue() {
        return textValue;
    }

    public void setTextValue(String textValue) {
        this.textValue = textValue;
    }

    public List<ImageValue> getImageValues() {
        return imageValues;
    }

    public void setImageValues(List<ImageValue> imageValues) {
        this.imageValues = imageValues;
    }

    public List<MicroOrganismResult> getMicroValues() {
        return microValues;
    }

    public void setMicroValues(List<MicroOrganismResult> microValues) {
        this.microValues = microValues;
    }

    @XmlElement(name = "resultNormString", required = true)
    public String getNorm() {
        return norm;
    }

    public void setNorm(String norm) {
        this.norm = norm;
    }

    @XmlElement(name = "resultNormalityIndex", required = true)
    public float getNormalityIndex() {
        return normalityIndex;
    }

    public void setNormalityIndex(float normalityIndex) {
        this.normalityIndex = normalityIndex;
    }

    @XmlElement(name = "resultUnit", required = true)
    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    @XmlElement(name = "resultSignDate", required = true)
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(String resultStatus) {
        this.resultStatus = resultStatus;
    }

    @XmlElement(name = "resultComment")
    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }
}
