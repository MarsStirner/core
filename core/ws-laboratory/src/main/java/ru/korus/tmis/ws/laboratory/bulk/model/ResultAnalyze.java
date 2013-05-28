package ru.korus.tmis.ws.laboratory.bulk.model;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;

import java.util.List;

import static ru.korus.tmis.util.CompileTimeConfigManager.Laboratory.Namespace;

/**
 * Модель данных для результата анализов
 * <p/>
 * test @see ru.korus.tmis.ws.laboratory.bulk.model.ResultAnalyzeSpec
 *
 * @author anosov@outlook.com
 *         date: 5/26/13
 */
@XmlRootElement (namespace = Namespace)
public class ResultAnalyze {

    /**
     * список изображений (их может быть много)
     */
    protected List<ImageValue> imageValues;

    /**
     * набор значений
     */
    protected List<MicroValue> microValues;

    /**
     * набор значений
     */
    protected List<MicroSensitivity> microSensitivity;

    /**
     * код методики/показателя/микроорганизма
     */

    private String indicatorCode;

    /**
     * название методики/показателя/микроорганизма
     */

    private String indicatorName;

    /**
     * название прибора
     */

    private String deviceName;

    /**
     * тип значения
     * (1 – строковое значение или большой текст,
     * 2 – изображение,
     * 3 – концентрация микроорг.,
     * 4 –чувствительность микроорг.)
     * РЕЗУЛЬТАТ МОЖЕТ ИМЕТЬ ЗНАЧЕНИЯ ТОЛЬКО ОДНОГО ТИПА, и обязательно должен присутствовать хотя бы один.
     */

    private ValueType valueType;

    /**
     * значение (значение в виде строки или большого текста)
     */

    private String resultValueText;

    /**
     * норма, т.е. диапазон допустимых значений в строковом вид
     */

    private String resultNormString;

    /**
     * значение результата относительно нормы (число в диапазоне -1 до +1)
     */

    private Float resultNormalityIndex;

    /**
     * дата выполнения/утверждения результата
     */
    private XMLGregorianCalendar resultSignDate;

    /**
     * единица измерения
     */
    private String resultUnit;

    /**
     * если результата нет здесь указана причина
     */
    private String resultStatus;

    /**
     * произвольный текстовый комментарий
     */
    private String resultComment;

    @XmlElements ({
            @XmlElement (name = "imageValues", type = ImageValue.class)
    })
    @XmlElementWrapper
    public List<ImageValue> getImageValues() {
        return imageValues;
    }

    public void setImageValues(List<ImageValue> imageValues) {
        this.imageValues = imageValues;
    }

    @XmlElements ({
            @XmlElement (name = "microValues", type = MicroValue.class)
    })
    @XmlElementWrapper
    public List<MicroValue> getMicroValues() {
        return microValues;
    }

    public void setMicroValues(List<MicroValue> microValues) {
        this.microValues = microValues;
    }

    @XmlElements ({
            @XmlElement (name = "microSensitivity", type = MicroSensitivity.class)
    })
    @XmlElementWrapper
    public List<MicroSensitivity> getMicroSensitivity() {
        return microSensitivity;
    }

    public void setMicroSensitivity(List<MicroSensitivity> microSensitivity) {
        this.microSensitivity = microSensitivity;
    }

    @XmlElement(name = "indicatorCode")
    public String getIndicatorCode() {
        return indicatorCode;
    }

    public void setIndicatorCode(String indicatorCode) {
        this.indicatorCode = indicatorCode;
    }

    @XmlElement(name = "indicatorName")
    public String getIndicatorName() {
        return indicatorName;
    }

    public void setIndicatorName(String indicatorName) {
        this.indicatorName = indicatorName;
    }

    @XmlElement(name = "deviceName")
    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    @XmlElement(name = "valueType")
    public ValueType getValueType() {
        return valueType;
    }

    public void setValueType(ValueType valueType) {
        this.valueType = valueType;
    }

    @XmlElement(name = "resultValueText")
    public String getResultValueText() {
        return resultValueText;
    }

    public void setResultValueText(String resultValueText) {
        this.resultValueText = resultValueText;
    }

    @XmlElement(name = "resultNormString")
    public String getResultNormString() {
        return resultNormString;
    }

    public void setResultNormString(String resultNormString) {
        this.resultNormString = resultNormString;
    }

    @XmlElement(name = "resultNormalityIndex")
    public Float getResultNormalityIndex() {
        return resultNormalityIndex;
    }

    public void setResultNormalityIndex(Float resultNormalityIndex) {
        this.resultNormalityIndex = resultNormalityIndex;
    }

    @XmlElement(name = "resultSignDate")
    public XMLGregorianCalendar getResultSignDate() {
        return resultSignDate;
    }

    public void setResultSignDate(XMLGregorianCalendar resultSignDate) {
        this.resultSignDate = resultSignDate;
    }

    @XmlElement(name = "resultUnit")
    public String getResultUnit() {
        return resultUnit;
    }

    public void setResultUnit(String resultUnit) {
        this.resultUnit = resultUnit;
    }

    @XmlElement(name = "resultStatus")
    public String getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(String resultStatus) {
        this.resultStatus = resultStatus;
    }

    @XmlElement(name = "resultComment")
    public String getResultComment() {
        return resultComment;
    }

    public void setResultComment(String resultComment) {
        this.resultComment = resultComment;
    }

    @Override
    public String toString() {
        return "ResultAnalyze{" +
                "indicatorCode='" + indicatorCode + '\'' +
                ",\n indicatorName='" + indicatorName + '\'' +
                ",\n deviceName='" + deviceName + '\'' +
                ",\n valueType=" + valueType +
                ",\n resultValueText='" + resultValueText + '\'' +
//                ", imageValues=" + imageValues +
//                ", microValues=" + microValues +
//                ", microSensitivity=" + microSensitivity +
                ",\n resultNormString='" + resultNormString + '\'' +
                ",\n resultNormalityIndex=" + resultNormalityIndex +
                ",\n resultSignDate=" + resultSignDate +
                ",\n resultUnit='" + resultUnit + '\'' +
                ",\n resultStatus='" + resultStatus + '\'' +
                ",\n resultComment='" + resultComment + '\'' +
                '}';
    }

    @XmlEnum (Integer.class)
    public enum ValueType {
        /**
         * строковое значение или большой текст
         */
        @XmlEnumValue ("1")
        STRING,
        /**
         * изображение
         */
        @XmlEnumValue ("2")
        IMAGE,
        /**
         * концентрация микроорг.
         */
        @XmlEnumValue ("3")
        CONC_OF_MICROORG,
        /**
         * чувствительность микроорг.
         */
        @XmlEnumValue ("4")
        SENS_OF_MICROORG;
    }
}
