package ru.korus.tmis.core.data;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import ru.korus.tmis.core.entity.model.OrgStructure;
import ru.korus.tmis.core.entity.model.RbStorage;
import ru.korus.tmis.core.entity.model.RlsBalanceOfGood;
import ru.korus.tmis.core.entity.model.RlsNomen;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlType;
import java.util.Date;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        05.02.2015, 14:16 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@XmlType
@JsonIgnoreProperties
public class BalanceOfGoodData {

    private Date bestBefore;

    private byte disabled;

    private Date updateDateTime;

    private double value;

    private Integer orgStructureId = null;

    public BalanceOfGoodData() {
    }

    public BalanceOfGoodData(RlsBalanceOfGood rlsBalanceOfGood) {

        bestBefore = rlsBalanceOfGood.getBestBefore();
        disabled = rlsBalanceOfGood.getDisabled();
        updateDateTime = rlsBalanceOfGood.getUpdateDateTime();
        value = rlsBalanceOfGood.getValue();
        RbStorage rbStorage = rlsBalanceOfGood.getRbStorage();
        OrgStructure orgStructure = rbStorage == null ? null : rbStorage.getOrgStructure();
        orgStructureId = orgStructure == null ? null : orgStructure.getId();
    }
}

