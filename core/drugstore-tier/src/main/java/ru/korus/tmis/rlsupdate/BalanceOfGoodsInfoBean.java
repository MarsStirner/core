package ru.korus.tmis.rlsupdate;

import misexchange.DrugList;
import org.hl7.v3.CE;
import org.hl7.v3.POCDMT000040LabeledDrug;
import ru.korus.tmis.prescription.BalanceOfGoodsInfo;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        25.09.13, 15:24 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Stateless
public class BalanceOfGoodsInfoBean implements BalanceOfGoodsInfo {

    @EJB
    SyncWith1C sync;

    private final misexchange.ObjectFactory factory = new misexchange.ObjectFactory();
    private final org.hl7.v3.ObjectFactory factoryHL7 = new org.hl7.v3.ObjectFactory();

   // @Override
    public boolean update(List<Integer> drugIds) {

        DrugList drugList = factory.createDrugList();
        for(Integer code : drugIds) {
            addDrug(drugList, String.valueOf(code));
        }
        sync.updateBalance(drugList);
        return !drugList.getDrug().isEmpty();
    }

    private void addDrug(DrugList drugList, String drugCode) {
        final POCDMT000040LabeledDrug labeledDrug = getLabeledDrug(factoryHL7, drugCode);
        drugList.getDrug().add(labeledDrug);
    }

    public static POCDMT000040LabeledDrug getLabeledDrug(final org.hl7.v3.ObjectFactory factoryHL7, final String drugCode) {
        final POCDMT000040LabeledDrug labeledDrug = factoryHL7.createPOCDMT000040LabeledDrug();
        final CE ce = factoryHL7.createCE();
        labeledDrug.setCode(ce);
        ce.setCodeSystemName("RLS_NOMEN");
        ce.setCode(drugCode);
        return labeledDrug;
    }

}
