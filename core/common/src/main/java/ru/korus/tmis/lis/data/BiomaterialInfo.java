package ru.korus.tmis.lis.data;

import javax.xml.bind.annotation.XmlElement;
import java.util.Date;

/**
 * Информация о биоматериале
 */
public class BiomaterialInfo {

    /**
     * Код типа биоматериала из справочника (Основная информация)
     */
    private String code;

    /**
     * Название типа биоматериала из справочника биоматериалов
     */
    private String name;

    /**
     * Штрих-код на контейнере c биоматериалом
     * Десятичное представление считанного штрих-кода
     */
    private String barCode;

    /**
     * Дата забора биоматериала
     */
    private Date samplingDate;

    private String comment;

    @XmlElement(name = "orderBiomaterialName")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name = "orderBiomaterialCode", required = true)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @XmlElement(name = "orderBarCode", required = true)
    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    @XmlElement(name = "orderProbeDate", required = true)
    public Date getSamplingDate() {
        return samplingDate;
    }

    public void setSamplingDate(Date samplingDate) {
        this.samplingDate = samplingDate;
    }

    @XmlElement(name = "orderBiomaterialComment")
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
