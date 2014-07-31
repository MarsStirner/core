package ru.korus.tmis.laboratory.across.accept2;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@XmlType(propOrder = {
        "name",
        "code",
        "deviceName",
        "valueTypeNum",
        "textValue",
        "imageValues",
        "microValues",
        "microSensitivity",
        "norm",
        "normalityIndex",
        "unitCode",
        "endDate",
        "resultStatus",
        "comment"
})
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
    private int valueTypeNum;

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
    private Float normalityIndex;

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
        if (microSensitivity == null) microSensitivity = new LinkedList<AntibioticSensitivity>();
        return microSensitivity;
    }

    public void setMicroSensitivity(List<AntibioticSensitivity> microSensitivity) {
        this.microSensitivity = microSensitivity;
    }

    public enum ValueType {
        TEXT(1),
        IMAGE(2),
        NUMERIC(3),
        DIAPASON(4);

        int val;

        ValueType(int v) {
            this.val = v;
        }
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

    @XmlElement(name = "valueType", required = true)
    public int getValueTypeNum() {
        return valueTypeNum;
    }

    public void setValueTypeNum(int valueType) {
        this.valueTypeNum = valueType;
    }

    @XmlTransient
    public ValueType getValueType() {
        return ValueType.values()[this.valueTypeNum - 1];
    }

    public void setValueType(ValueType valueType) {
        this.valueTypeNum = valueType.val;
    }

    @XmlElement(name = "resultValueText", required = true)
    public String getTextValue() {
        return textValue;
    }

    public void setTextValue(String textValue) {
        this.textValue = textValue;
    }

    public List<ImageValue> getImageValues() {
        if (imageValues == null) imageValues = new LinkedList<ImageValue>();
        return imageValues;
    }

    public void setImageValues(List<ImageValue> imageValues) {
        this.imageValues = imageValues;
    }

    public List<MicroOrganismResult> getMicroValues() {
        if (microValues == null) microValues = new LinkedList<MicroOrganismResult>();
        return microValues;
    }

    public void setMicroValues(List<MicroOrganismResult> microValues) {
        this.microValues = microValues;
    }

    @XmlElement(name = "resultNormString")
    public String getNorm() {
        return norm;
    }

    public void setNorm(String norm) {
        this.norm = norm;
    }

    @XmlElement(name = "resultNormalityIndex")
    public Float getNormalityIndex() {
        return normalityIndex;
    }

    public void setNormalityIndex(Float normalityIndex) {
        this.normalityIndex = normalityIndex;
    }

    @XmlElement(name = "resultUnit")
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

    @XmlElement()
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
