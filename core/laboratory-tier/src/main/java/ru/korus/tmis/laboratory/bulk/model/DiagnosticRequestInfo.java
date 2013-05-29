package ru.korus.tmis.laboratory.bulk.model;


import org.apache.commons.lang.StringUtils;
import ru.korus.tmis.util.CompileTimeConfigManager;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for DiagnosticRequestInfo complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="DiagnosticRequestInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="orderMisId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="orderMisDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="orderPregnatMin" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="orderPregnatMax" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="orderDiagCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="orderDiagText" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="orderComment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="orderDepartmentName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="orderDepartmentMisId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="orderDoctorFamily" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="orderDoctorName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="orderDoctorPatronum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="orderDoctorMisId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 * @author anosov@outlook.com
 *         date: 5/25/13
 *         <p/>
 *         test {{@see ru.korus.tmis.laboratory.bulk.model.DiagnosticRequestInfoSpec}}
 */
@XmlAccessorType (XmlAccessType.FIELD)
@XmlType (name = "DiagnosticRequestInfo", propOrder = {
        "orderMisId",
        "orderMisDate",
        "isUrgent",
        "orderPregnat",
        "orderDiagCode",
        "orderDiagText",
        "orderComment",
        "orderDepartmentName",
        "orderDepartmentMisId",
        "orderDoctorFamily",
        "orderDoctorName",
        "orderDoctorPatronum",
        "orderDoctorMisId"
})
@XmlRootElement(namespace = CompileTimeConfigManager.Laboratory.Namespace)
public class DiagnosticRequestInfo {

    /**
     * уникальный идентификатор направления в МИС (Action.id)
     */
    @XmlElement (required = true)
    private Integer orderMisId;

    /**
     * дата и время создания направления врачом
     */
    @XmlElement (required = true)
    @XmlSchemaType (name = "dateTime")
    private XMLGregorianCalendar orderMisDate;

    /**
     * срочность заказа (0 – не срочно, 1 – срочно)
     */
    @XmlElement (required = true)
    private Integer isUrgent;

    /**
     * средний срок беременности, в неделях
     */
    @XmlElement
    private Integer orderPregnat;

    /**
     * код болезни по МКБ (последний из обоснования; если нет, то из первичного осмотра)
     */
    @XmlElement
    private String orderDiagCode;

    /**
     * текстовое описание диагноза
     */
    @XmlElement
    private String orderDiagText;

    /**
     * произвольный текстовый комментарий к направлению
     */
    @XmlElement
    private String orderComment;

    /**
     * название отделения
     */
    @XmlElement
    private String orderDepartmentName;

    /**
     * уникальный код отделения
     */
    @XmlElement (required = true)
    private String orderDepartmentMisId;

    /**
     * уникальный код назначившего врача
     */
    @XmlElement (required = true)
    private String orderDoctorMisId;

    /**
     * фамилия назначившего врача
     */
    @XmlElement
    private String orderDoctorFamily;

    /**
     * имя назначившего врача
     */
    @XmlElement
    private String orderDoctorName;

    /**
     * отчество назначившего врача
     */
    @XmlElement
    private String orderDoctorPatronum;

    public Integer getOrderMisId() {
        return orderMisId;
    }

    public void setOrderMisId(Integer orderMisId) {
        this.orderMisId = orderMisId;
    }

    public XMLGregorianCalendar getOrderMisDate() {
        return orderMisDate;
    }

    public void setOrderMisDate(XMLGregorianCalendar orderMisDate) {
        this.orderMisDate = orderMisDate;
    }

    public Integer getUrgent() {
        return isUrgent;
    }

    public void setUrgent(Integer urgent) {
        isUrgent = urgent;
    }

    public Integer getOrderPregnat() {
        return orderPregnat;
    }

    public void setOrderPregnat(Integer orderPregnat) {
        this.orderPregnat = orderPregnat;
    }

    public String getOrderDiagCode() {
        return orderDiagCode;
    }

    public void setOrderDiagCode(String orderDiagCode) {
        this.orderDiagCode = orderDiagCode;
    }

    public String getOrderDiagText() {
        return orderDiagText;
    }

    public void setOrderDiagText(String orderDiagText) {
        this.orderDiagText = orderDiagText;
    }

    public String getOrderComment() {
        return orderComment;
    }

    public void setOrderComment(String orderComment) {
        this.orderComment = orderComment;
    }

    public String getOrderDepartmentName() {
        return orderDepartmentName;
    }

    public void setOrderDepartmentName(String orderDepartmentName) {
        this.orderDepartmentName = orderDepartmentName;
    }

    public String getOrderDepartmentMisId() {
        return orderDepartmentMisId;
    }

    public void setOrderDepartmentMisId(String orderDepartmentMisId) {
        this.orderDepartmentMisId = orderDepartmentMisId;
    }

    public String getOrderDoctorMisId() {
        return orderDoctorMisId;
    }

    public void setOrderDoctorMisId(String orderDoctorMisId) {
        this.orderDoctorMisId = orderDoctorMisId;
    }

    public String getOrderDoctorFamily() {
        return orderDoctorFamily;
    }

    public void setOrderDoctorFamily(String orderDoctorFamily) {
        this.orderDoctorFamily = orderDoctorFamily;
    }

    public String getOrderDoctorName() {
        return orderDoctorName;
    }

    public void setOrderDoctorName(String orderDoctorName) {
        this.orderDoctorName = orderDoctorName;
    }

    public String getOrderDoctorPatronum() {
        return orderDoctorPatronum;
    }

    public void setOrderDoctorPatronum(String orderDoctorPatronum) {
        this.orderDoctorPatronum = orderDoctorPatronum;
    }

    /**
     * валидация. Наличие ошибок в модели
     *
     * @return
     */
    public boolean hasErrors() {
        return StringUtils.isEmpty(this.orderDepartmentMisId ) ||
                this.orderMisId == null ||
                this.isUrgent == null ||
                this.orderMisDate == null ||
                StringUtils.isEmpty(this.orderDoctorMisId);
    }

}

