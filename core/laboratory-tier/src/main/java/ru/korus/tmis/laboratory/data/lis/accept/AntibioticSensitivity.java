package ru.korus.tmis.laboratory.data.lis.accept;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Чувствительность микроорганизма к антибиотикам
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(propOrder = {
        "code",
        "name",
        "mic",
        "value"
})
public class AntibioticSensitivity {

    /**
     * Идентификатор антибиотика по БД ЛИС
     */
    private String code;

    /**
     * Название антибиотика
     */
    private String name;

    /**
     * Чувствительность
     */
    private String value;

    /**
     * Количественная характеристика чувствительности, если используется анализатор
     */
    private String mic;

    @XmlElement(name = "antibioticLisId", required = true)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @XmlElement(name = "antibioticName", required = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name = "antibioticActivityValue", required = true)
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @XmlElement(name = "MIC", required = true)
    public String getMic() {
        return mic;
    }

    public void setMic(String mic) {
        this.mic = mic;
    }
}
