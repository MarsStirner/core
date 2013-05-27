package ru.korus.tmis.ws.laboratory.bulk.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.datatype.XMLGregorianCalendar;

import static ru.korus.tmis.util.CompileTimeConfigManager.Laboratory.Namespace;

/**
 * @author anosov@outlook.com
 *         date: 5/26/13
 */
@XmlRootElement (namespace = Namespace)
public class ResultAnalyze {

    /**
     * код методики/показателя/микроорганизма
     */
    @XmlElement
    private String indicatorCode;

    /**
     * название методики/показателя/микроорганизма
     */
    @XmlElement
    private String indicatorName;

    /**
     * название прибора
     */
    @XmlElement
    private String deviceName;

    /**
     * тип значения
     * (1 – строковое значение или большой текст,
     * 2 – изображение,
     * 3 – концентрация микроорг.,
     * 4 –чувствительность микроорг.)
     * РЕЗУЛЬТАТ МОЖЕТ ИМЕТЬ ЗНАЧЕНИЯ ТОЛЬКО ОДНОГО ТИПА, и обязательно должен присутствовать хотя бы один.
     */
    @XmlElement
    private ValueType valueType;

    /**
     * значение (значение в виде строки или большого текста)
     */
    @XmlElement
    private String resultValueText;

//    /**
//     * список изображений (их может быть много)
//     */
//    @XmlElement
//    @XmlList
//    private List<ImageValue> imageValues;

//    /**
//     * набор значений
//     */
//    @XmlElement
//    private List<MicroValue> microValues;
//
//    /**
//     * набор значений
//     */
//    @XmlElement
//    private List<MicroSensitivity> microSensitivity;

    /**
     * норма, т.е. диапазон допустимых значений в строковом вид
     */
    @XmlElement
    private String resultNormString;

    /**
     * значение результата относительно нормы (число в диапазоне -1 до +1)
     */
    @XmlElement
    private Float resultNormalityIndex;

    /**
     * дата выполнения/утверждения результата
     */
    @XmlElement
    private XMLGregorianCalendar resultSignDate;

    /**
     * единица измерения
     */
    @XmlElement
    private String resultUnit;

    /**
     * если результата нет здесь указана причина
     */
    @XmlElement
    private String resultStatus;

    /**
     * произвольный текстовый комментарий
     */
    @XmlElement
    private String resultComment;



//    public List<ImageValue> getImageValues() {
//        return imageValues;
//    }
//
//    public void setImageValues(List<ImageValue> imageValues) {
//        this.imageValues = imageValues;
//    }
//
//    public List<MicroValue> getMicroValues() {
//        return microValues;
//    }
//
//    public void setMicroValues(List<MicroValue> microValues) {
//        this.microValues = microValues;
//    }
//
//    public List<MicroSensitivity> getMicroSensitivity() {
//        return microSensitivity;
//    }
//
//    public void setMicroSensitivity(List<MicroSensitivity> microSensitivity) {
//        this.microSensitivity = microSensitivity;
//    }
//




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

//    public class ImageValue {
//
//        /**
//         * строка описания
//         */
//        @XmlElement
//        private String imageString;
//
//        /**
//         * картинка, закодированная в Base64
//         */
//        @XmlElement
//        private String imageData;
//
//        String getImageString() {
//            return imageString;
//        }
//
//        void setImageString(String imageString) {
//            this.imageString = imageString;
//        }
//
//        String getImageData() {
//            return imageData;
//        }
//
//        void setImageData(String imageData) {
//            this.imageData = imageData;
//        }
//
//        @Override
//        public String toString() {
//            return "ImageValue{" +
//                    "imageString='" + imageString + '\'' +
//                    ", imageData='" + imageData + '\'' +
//                    '}';
//        }
//    }

//    public class MicroValue {
//
//        /**
//         * идентификатор организма по БД ЛИС
//         */
//        @XmlElement
//        private String organismLisId;
//
//        /**
//         * название организма
//         */
//        @XmlElement
//        private String organismName;
//
//        /**
//         * описание концентрации в произвольном виде
//         */
//        @XmlElement
//        private String organismConcetration;
//
//        String getOrganismLisId() {
//            return organismLisId;
//        }
//
//        void setOrganismLisId(String organismLisId) {
//            this.organismLisId = organismLisId;
//        }
//
//        String getOrganismName() {
//            return organismName;
//        }
//
//        void setOrganismName(String organismName) {
//            this.organismName = organismName;
//        }
//
//        String getOrganismConcetration() {
//            return organismConcetration;
//        }
//
//        void setOrganismConcetration(String organismConcetration) {
//            this.organismConcetration = organismConcetration;
//        }
//
//        @Override
//        public String toString() {
//            return "MicroValue{" +
//                    "organismLisId='" + organismLisId + '\'' +
//                    ", organismName='" + organismName + '\'' +
//                    ", organismConcetration='" + organismConcetration + '\'' +
//                    '}';
//        }
//    }
//
//    public class MicroSensitivity {
//
//        /**
//         * идентификатор антибиотика БД
//         */
//        @XmlElement
//        private String antibioticLisId;
//
//        /**
//         * название антибиотика
//         */
//        @XmlElement
//        private String antibioticName;
//
//        /**
//         * величина концентрации
//         */
//        @XmlElement
//        private String MIC;
//
//        /**
//         * описание чувствительности в произвольном виде: R,S,I
//         */
//        @XmlElement
//        private String antibioticActivityValue;
//
//        String getAntibioticLisId() {
//            return antibioticLisId;
//        }
//
//        void setAntibioticLisId(String antibioticLisId) {
//            this.antibioticLisId = antibioticLisId;
//        }
//
//        String getAntibioticName() {
//            return antibioticName;
//        }
//
//        void setAntibioticName(String antibioticName) {
//            this.antibioticName = antibioticName;
//        }
//
//        String getMIC() {
//            return MIC;
//        }
//
//        void setMIC(String MIC) {
//            this.MIC = MIC;
//        }
//
//        String getAntibioticActivityValue() {
//            return antibioticActivityValue;
//        }
//
//        void setAntibioticActivityValue(String antibioticActivityValue) {
//            this.antibioticActivityValue = antibioticActivityValue;
//        }
//
//        @Override
//        public String toString() {
//            return "MicroSensitivity{" +
//                    "antibioticLisId='" + antibioticLisId + '\'' +
//                    ", antibioticName='" + antibioticName + '\'' +
//                    ", MIC='" + MIC + '\'' +
//                    ", antibioticActivityValue='" + antibioticActivityValue + '\'' +
//                    '}';
//        }
//    }
}
