package ru.korus.tmis.prescription;

import ru.korus.tmis.core.database.common.DbActionPropertyTypeBeanLocal;
import ru.korus.tmis.core.entity.model.ActionType;
import ru.korus.tmis.core.exception.CoreException;

import javax.xml.bind.annotation.XmlType;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        19.05.14, 11:54 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@XmlType
public class CreatePrescriptionReqData {

    private CreatePrescriptionData data;

    private Object requestData = new Object(); //TODO ???

    public CreatePrescriptionReqData(ActionType actionType, DbActionPropertyTypeBeanLocal dbActionPropertyTypeBeanLocal) throws CoreException {
        data = new CreatePrescriptionData(actionType, dbActionPropertyTypeBeanLocal);
    }

    public CreatePrescriptionReqData() {
        data = null;
    }

    public Object getRequestData() {
        return requestData;
    }

    public CreatePrescriptionData getData() {
        return data;
    }

    public void setRequestData(Object requestData) {
        this.requestData = requestData;
    }

    public void setData(CreatePrescriptionData data) {
        this.data = data;
    }
}
