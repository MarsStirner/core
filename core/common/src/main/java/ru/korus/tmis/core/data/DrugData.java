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
@XmlType
@XmlRootElement
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
}
