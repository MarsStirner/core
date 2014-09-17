package ru.korus.tmis.prescription;

import ru.korus.tmis.core.database.DbActionTypeBeanLocal;
import ru.korus.tmis.core.entity.model.ActionType;
import ru.korus.tmis.core.pharmacy.FlatCode;

import javax.xml.bind.annotation.XmlType;
import java.util.LinkedList;
import java.util.List;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        16.05.14, 15:56 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@XmlType
public class PrescriptionTypeDataArray {

    private final List<PrescriptionTypeData> data = new LinkedList<PrescriptionTypeData>();

    public PrescriptionTypeDataArray(DbActionTypeBeanLocal dbActionTypeBeanLocal) {
        List<ActionType> actionTypeList = dbActionTypeBeanLocal.getActionTypeByCode(FlatCode.getPrescriptionCodeList());
        for(ActionType actionType : actionTypeList) {
            data.add(new PrescriptionTypeData(actionType));
        }
    }

    public PrescriptionTypeDataArray() {
    }

    public List<PrescriptionTypeData> getData() {
        return data;
    }
}
