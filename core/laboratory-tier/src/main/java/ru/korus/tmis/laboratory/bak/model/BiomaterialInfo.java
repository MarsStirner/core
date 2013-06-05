package ru.korus.tmis.laboratory.bak.model;


import org.apache.commons.lang.StringUtils;
import ru.korus.tmis.util.CompileTimeConfigManager;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for BiomaterialInfo complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="BiomaterialInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="orderBiomaterialCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="orderBiomaterialname" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="orderBarCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="orderProbeDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="orderBiomaterialComment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 * @author anosov@outlook.com
 *         date: 5/25/13
 *         <p/>
 *         test {{@see ru.korus.tmis.laboratory.bak.model.BiomaterialInfoSpec}}
 */
@XmlAccessorType (XmlAccessType.FIELD)
@XmlType (name = "BiomaterialInfo", propOrder = {
        "orderBiomaterialCode",
        "orderBiomaterialname",
        "orderBarCode",
        "orderProbeDate",
        "orderBiomaterialVolume",
        "takenTissueJournal",
        "unitBiomaterialCode",
        "unitBiomaterialName",
        "orderBiomaterialComment"
})
@XmlRootElement (namespace = CompileTimeConfigManager.Laboratory.Namespace)
public class BiomaterialInfo {

    /**
     * название  биоматериала из справочника биоматериалов
     */
    @XmlElement
    private String orderBiomaterialname;

    /**
     * код  биоматериала из справочника
     */
    @XmlElement
    private String orderBiomaterialCode;

    /**
     * штрих-код на контейнере c биоматериалом (десятичное представление считанного штрих-кода)
     */
    @XmlElement (required = true)
    private String orderBarCode;

    /**
     * номер заказа TakenTissueJournal.id
     */
    @XmlElement (required = true)
    private String takenTissueJournal;

    /**
     * дата и время  забора биоматериала
     */
    @XmlElement (required = true)
    @XmlSchemaType (name = "dateTime")
    private XMLGregorianCalendar orderProbeDate;


    /**
     * произвольный текстовый комментарий к биоматериалу
     */
    @XmlElement
    private String orderBiomaterialComment;

    /**
     * Количество/объем биоматериала
     */
    @XmlElement
    private Integer orderBiomaterialVolume;

    /**
     * Код единицы измерения биоматериала
     */
    @XmlElement
    private Integer unitBiomaterialCode;

    /**
     * Название единицы измерения биоматериала
     */
    @XmlElement
    private String unitBiomaterialName;


    public String getOrderBiomaterialname() {
        return orderBiomaterialname;
    }

    public void setOrderBiomaterialname(String orderBiomaterialname) {
        this.orderBiomaterialname = orderBiomaterialname;
    }

    public String getOrderBiomaterialCode() {
        return orderBiomaterialCode;
    }

    public void setOrderBiomaterialCode(String orderBiomaterialCode) {
        this.orderBiomaterialCode = orderBiomaterialCode;
    }

    public String getOrderBarCode() {
        return orderBarCode;
    }

    public void setOrderBarCode(String orderBarCode) {
        this.orderBarCode = orderBarCode;
    }

    public String getTakenTissueJournal() {
        return takenTissueJournal;
    }

    public void setTakenTissueJournal(String takenTissueJournal) {
        this.takenTissueJournal = takenTissueJournal;
    }

    public XMLGregorianCalendar getOrderProbeDate() {
        return orderProbeDate;
    }

    public void setOrderProbeDate(XMLGregorianCalendar orderProbeDate) {
        this.orderProbeDate = orderProbeDate;
    }

    public String getOrderBiomaterialComment() {
        return orderBiomaterialComment;
    }

    public void setOrderBiomaterialComment(String orderBiomaterialComment) {
        this.orderBiomaterialComment = orderBiomaterialComment;
    }

    public Integer getOrderBiomaterialVolume() {
        return orderBiomaterialVolume;
    }

    public void setOrderBiomaterialVolume(Integer orderBiomaterialVolume) {
        this.orderBiomaterialVolume = orderBiomaterialVolume;
    }

    public Integer getUnitBiomaterialCode() {
        return unitBiomaterialCode;
    }

    public void setUnitBiomaterialCode(Integer unitBiomaterialCode) {
        this.unitBiomaterialCode = unitBiomaterialCode;
    }

    public String getUnitBiomaterialName() {
        return unitBiomaterialName;
    }

    public void setUnitBiomaterialName(String unitBiomaterialName) {
        this.unitBiomaterialName = unitBiomaterialName;
    }


    /**
     * валидация. Наличие ошибок в модели
     */
    public boolean hasErrors() {
        return StringUtils.isEmpty(this.orderBarCode) ||
                this.orderProbeDate == null ||
                StringUtils.isEmpty(this.takenTissueJournal);
    }
}

