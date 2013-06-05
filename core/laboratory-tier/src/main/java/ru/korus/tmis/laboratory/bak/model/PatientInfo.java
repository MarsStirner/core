package ru.korus.tmis.laboratory.bak.model;

import org.apache.commons.lang.StringUtils;
import ru.korus.tmis.util.CompileTimeConfigManager;

import javax.xml.bind.annotation.*;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * <p>Java class for PatientInfo complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="PatientInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="patientMisId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="patientFamily" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="patientName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="patientPatronum" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="patientBirthDate" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="patientSex" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 * @author anosov@outlook.com
 *         date: 5/25/13
 *         <p/>
 *         test {{@see ru.korus.tmis.laboratory.bak.model.PatientInfoSpec}}
 */
@XmlAccessorType (XmlAccessType.FIELD)
@XmlType (name = "PatientInfo", propOrder = {
        "patientMisId",
        "patientFamily",
        "patientName",
        "patientPatronum",
        "patientBirthDate",
        "patientSex",
        "custodian",
        "patientAddress",
        "patientNumber"

})
@XmlRootElement (namespace = CompileTimeConfigManager.Laboratory.Namespace)
public class PatientInfo {

    /**
     * фамилия
     */
    @XmlElement
    private String patientFamily;

    /**
     * имя
     */
    @XmlElement
    private String patientName;

    /**
     * отчество
     */
    @XmlElement
    private String patientPatronum;

    /**
     * дата рождения в формате дд.мм.гггг
     */
    @XmlElement (required = true)
    private String patientBirthDate;

    /**
     * пол пациента (1-мужской, 2-женский, 3-не определен)
     */
    @XmlElement (required = true)
    private Gender patientSex;

    /**
     * Название ЛПУ, которое посылает в ЛИС направление
     */
    @XmlElement (required = true)
    private String custodian;

    /**
     * Адрес пациента
     */
    @XmlElement
    private String patientAddress;

    /**
     * уникальный код пациента в МИС
     */
    @XmlElement (required = true)
    private Integer patientMisId;

    /**
     * Номер карты /талона пациента
     */
    @XmlElement (required = true)
    private String patientNumber;

    public String getPatientFamily() {
        return patientFamily;
    }

    public void setPatientFamily(String patientFamily) {
        this.patientFamily = patientFamily;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientPatronum() {
        return patientPatronum;
    }

    public void setPatientPatronum(String patientPatronum) {
        this.patientPatronum = patientPatronum;
    }

    public String getPatientBirthDate() {
        return patientBirthDate;
    }

    public void setPatientBirthDate(Date patientBirthDate) {
        SimpleDateFormat sf = new SimpleDateFormat("dd.MM.yyyy");
        this.patientBirthDate = sf.format(patientBirthDate);
    }

    public Gender getPatientSex() {
        return patientSex;
    }

    public void setPatientSex(Gender patientSex) {
        this.patientSex = patientSex;
    }

    public String getCustodian() {
        return custodian;
    }

    public void setCustodian(String custodian) {
        this.custodian = custodian;
    }

    public String getPatientAddress() {
        return patientAddress;
    }

    public void setPatientAddress(String patientAddress) {
        this.patientAddress = patientAddress;
    }

    public Integer getPatientMisId() {
        return patientMisId;
    }

    public void setPatientMisId(Integer patientMisId) {
        this.patientMisId = patientMisId;
    }

    public String getPatientNumber() {
        return patientNumber;
    }

    public void setPatientNumber(String patientNumber) {
        this.patientNumber = patientNumber;
    }

    /**
     * валидация. Наличие ошибок в модели
     * @return
     */
    public boolean hasErrors() {
        return StringUtils.isEmpty(this.custodian) ||
                this.patientMisId == null ||
                this.patientSex == null ||
                StringUtils.isEmpty(this.patientNumber) ||
                StringUtils.isEmpty(this.patientBirthDate);
    }


    /**
     * Перечисление для пола пациента
     */
    @XmlEnum (Integer.class)
    public enum Gender {
        @XmlEnumValue ("1")
        MEN,
        @XmlEnumValue ("2")
        WOMEN,
        /**
         * Не определен
         */
        @XmlEnumValue ("3")
        UNKNOWN
    }
}
