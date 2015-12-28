package ru.korus.tmis.hsct.external;

import javax.xml.bind.annotation.XmlElement;

/**
 * Author: Upatov Egor <br>
 * Date: 24.12.2015, 16:34 <br>
 * Company: hitsl (Hi-Tech Solutions) <br>
 * Description: <br>
 */
public class HsctExternalRequest {
    /**
     * Статус болезни	Текстовое поле (string)	disease_status   //необязательное
     */
    @XmlElement(name = "disease_status", required = false)
    private String diseaseStatus;
    /**
     *     Дата установки диагноза	Поле типа «Дата»	diagnosis_date  //необязательное
     */
    @XmlElement(name="diagnosis_date", required = false)
    private String diagnosisDate;
    /**
     *    Анти-CMV IgG	Выпадающий список с значениями: «Положительный», «Отрицательный»	anti_cmv_igg   //необязательное
     */
    @XmlElement(name="anti_cmv_igg", required = false)
    private String antiCmvIgG;
    /**
     *    Показания к ТСГК	Текстовое поле (string)	hsct_indications   //необязательное
     */
    @XmlElement(name="hsct_indications", required = false)
    private String hsctIndications;
    /**
     *     Дата установления показаний	Поле типа «Дата»	hsct_indications_date    //необязательное
     */
    @XmlElement(name="hsct_indications_date", required = false)
    private String hsctIndicationsDate;
    /**
     *    Оптимальный срок ТГСК 	Поле типа «Дата» 	hsct_optimal_date    //необязательное
     */
    @XmlElement(name="hsct_optimal_date", required = false)
    private String hsctOptimalDate;
    /**
     *     Вид ТСГК	Выпадающий список с значениями: «Аутологичная», «Аллогенная»	hsct_type    //необязательное
     */
    @XmlElement(name="hsct_type", required = false)
    private String hsctType;
    /**
     *  Наличие сиблингов	Выпадающий список с значениями: «Есть», «Нет»	siblings      //необязательное
     */
    @XmlElement(name="siblings", required = false)
    private String siblings;

}
