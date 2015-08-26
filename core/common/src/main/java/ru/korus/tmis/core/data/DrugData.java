package ru.korus.tmis.core.data;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import ru.korus.tmis.core.entity.model.Nomenclature;
import ru.korus.tmis.core.entity.model.RbStorage;
import ru.korus.tmis.core.entity.model.RlsBalanceOfGood;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Author;      Sergey A. Zagrebelny <br>
 * Date;        05.02.2015, 13;48 <br>
 * Company;     Korus Consulting IT<br>
 * Description;  <br>
 */
@XmlType(name = "drugData")
@XmlRootElement(name = "drugData")
@JsonIgnoreProperties
public class DrugData {
    
    private  Integer id;

    private  String tradeName;

    private  String tradeLocalName;

    private  Integer tradeName_id;

    private  String  actMattersName;

    private  String actMattersLocalName;

    private  Integer actMatters_id;

    private  String form;

    private  String packing;

    private  String filling;

    private  Integer unit_id;

    private  String unitCode;

    private  String unitName;

    private  String dosageValue;

    private  Integer dosageUnit_id;

    private  String dosageUnitCode;

    private  String dosageUnitName;

    private  Date regDate;

    private  Date annDate;

    private List<BalanceOfGoodData> balanceOfGoodDataList;

    public DrugData() {
    }

    public DrugData(Nomenclature nomenclature, List<RlsBalanceOfGood> rlsBalanceOfGoodList) {
        id = nomenclature.getId();
        tradeName = nomenclature.getTradeName();
        tradeLocalName = nomenclature.getTradeLocalName();
        tradeName_id = nomenclature.getTradeName_id();
        actMattersName = nomenclature.actMattersName();
        actMattersLocalName = nomenclature.actMattersLocalName();
        actMatters_id = nomenclature.actMatters_id();
        form = nomenclature.getForm();
        packing = nomenclature.getPacking();
        filling = nomenclature.getFilling();
        unit_id = nomenclature.getUnit_id();
        unitCode = nomenclature.getUnitCode();
        unitName = nomenclature.getUnitName();
        dosageValue = nomenclature.getDosageValue();
        dosageUnit_id = nomenclature.getDosageUnit_id();
        dosageUnitCode = nomenclature.getDosageUnitCode();
        dosageUnitName = nomenclature.getDosageUnitName();
        regDate = nomenclature.getRegDate();
        annDate = nomenclature.getAnnDate();
        balanceOfGoodDataList = new LinkedList<BalanceOfGoodData>();
        for (RlsBalanceOfGood rlsBalanceOfGood : rlsBalanceOfGoodList) {
            balanceOfGoodDataList.add(new BalanceOfGoodData(rlsBalanceOfGood));
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTradeName() {
        return tradeName;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

    public String getTradeLocalName() {
        return tradeLocalName;
    }

    public void setTradeLocalName(String tradeLocalName) {
        this.tradeLocalName = tradeLocalName;
    }

    public Integer getTradeName_id() {
        return tradeName_id;
    }

    public void setTradeName_id(Integer tradeName_id) {
        this.tradeName_id = tradeName_id;
    }

    public String getActMattersName() {
        return actMattersName;
    }

    public void setActMattersName(String actMattersName) {
        this.actMattersName = actMattersName;
    }

    public String getActMattersLocalName() {
        return actMattersLocalName;
    }

    public void setActMattersLocalName(String actMattersLocalName) {
        this.actMattersLocalName = actMattersLocalName;
    }

    public Integer getActMatters_id() {
        return actMatters_id;
    }

    public void setActMatters_id(Integer actMatters_id) {
        this.actMatters_id = actMatters_id;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public String getPacking() {
        return packing;
    }

    public void setPacking(String packing) {
        this.packing = packing;
    }

    public String getFilling() {
        return filling;
    }

    public void setFilling(String filling) {
        this.filling = filling;
    }

    public Integer getUnit_id() {
        return unit_id;
    }

    public void setUnit_id(Integer unit_id) {
        this.unit_id = unit_id;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getDosageValue() {
        return dosageValue;
    }

    public void setDosageValue(String dosageValue) {
        this.dosageValue = dosageValue;
    }

    public Integer getDosageUnit_id() {
        return dosageUnit_id;
    }

    public void setDosageUnit_id(Integer dosageUnit_id) {
        this.dosageUnit_id = dosageUnit_id;
    }

    public String getDosageUnitCode() {
        return dosageUnitCode;
    }

    public void setDosageUnitCode(String dosageUnitCode) {
        this.dosageUnitCode = dosageUnitCode;
    }

    public String getDosageUnitName() {
        return dosageUnitName;
    }

    public void setDosageUnitName(String dosageUnitName) {
        this.dosageUnitName = dosageUnitName;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public Date getAnnDate() {
        return annDate;
    }

    public void setAnnDate(Date annDate) {
        this.annDate = annDate;
    }

    public List<BalanceOfGoodData> getBalanceOfGoodDataList() {
        return balanceOfGoodDataList;
    }

    public void setBalanceOfGoodDataList(List<BalanceOfGoodData> balanceOfGoodDataList) {
        this.balanceOfGoodDataList = balanceOfGoodDataList;
    }
}
