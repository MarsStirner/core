package ru.korus.tmis.laboratory.bulk.model;


import org.apache.commons.lang.StringUtils;
import ru.korus.tmis.util.CompileTimeConfigManager;
import ru.korus.ws.laboratory.IndicatorMetodic;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.*;


/**
 * <p>Java class for OrderInfo complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="OrderInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="diagnosticCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="diagnosticName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="orderPriority" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="indicators" type="{http://www.korusconsulting.ru}IndicatorMetodic" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 * @author anosov@outlook.com
 *         date: 5/25/13
 *         <p/>
 *         test {{@see ru.korus.tmis.laboratory.bulk.model.OrderInfoSpec}}
 */
@XmlAccessorType (XmlAccessType.FIELD)
@XmlType (name = "OrderInfo", propOrder = {
        "diagnosticCode",
        "indicatorCode",
        "indicatorName",
        "typeFinanceCode",
        "typeFinanceName",
        "diagnosticName",
        "indicators"
})
@XmlRootElement (namespace = CompileTimeConfigManager.Laboratory.Namespace)
public class OrderInfo {

    /**
     * код исследования (actiontype.code)
     */
    @XmlElement (required = true)
    private String diagnosticCode;

    /**
     * название исследования (actiontype.name)
     */
    @XmlElement
    private String diagnosticName;

    /**
     * Код типа финансирования
     */
    @XmlElement (required = true)
    private String typeFinanceCode;

    /**
     * Названия типа финансирования
     */
    @XmlElement (required = true)
    private String typeFinanceName;


    /**
     * список показателей/методов исследований/  обнаруживаемых микроорганизмов
     */
    @XmlElement (required = true)
    private List<IndicatorMetodic> indicators;

    /**
     * код показателя/метода/микроорганизма (из справочника rbTest)
     */
    @XmlElement (required = true)
    private String indicatorCode;

    /**
     * наименование показателя/метода/микроорганизма (из справочника rbTest)
     */
    @XmlElement
    private String indicatorName;


    public String getDiagnosticCode() {
        return diagnosticCode;
    }

    public void setDiagnosticCode(String diagnosticCode) {
        this.diagnosticCode = diagnosticCode;
    }

    public String getDiagnosticName() {
        return diagnosticName;
    }

    public void setDiagnosticName(String diagnosticName) {
        this.diagnosticName = diagnosticName;
    }

    public String getTypeFinanceCode() {
        return typeFinanceCode;
    }

    public void setTypeFinanceCode(String typeFinanceCode) {
        this.typeFinanceCode = typeFinanceCode;
    }

    public String getTypeFinanceName() {
        return typeFinanceName;
    }

    public void setTypeFinanceName(String typeFinanceName) {
        this.typeFinanceName = typeFinanceName;
    }


    public void setIndicators(List<IndicatorMetodic> indicators) {
        this.indicators = indicators;
    }

    public String getIndicatorCode() {
        return indicatorCode;
    }

    public void setIndicatorCode(String indicatorCode) {
        this.indicatorCode = indicatorCode;
    }

    public String getIndicatorName() {
        return indicatorName;
    }

    public void setIndicatorName(String indicatorName) {
        this.indicatorName = indicatorName;
    }

    /**
     * Gets the value of the indicators property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the indicators property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIndicators().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link IndicatorMetodic }
     */
    public List<IndicatorMetodic> getIndicators() {
        if (indicators == null) {
            indicators = new ArrayList<IndicatorMetodic>();
        }
        return this.indicators;
    }


    /**
     * валидация. Наличие ошибок в модели
     * @return
     */
    public boolean hasErrors() {
        return StringUtils.isEmpty(this.diagnosticCode) ||
                this.getIndicators() == null ||
                StringUtils.isEmpty(this.indicatorCode) ||
                StringUtils.isEmpty(this.typeFinanceCode) ||
                StringUtils.isEmpty(this.typeFinanceName);
    }
}
