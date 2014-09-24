package ru.korus.tmis.prescription;

import ru.korus.tmis.core.database.common.DbActionPropertyTypeBeanLocal;
import ru.korus.tmis.core.entity.model.ActionType;
import ru.korus.tmis.core.exception.CoreException;

import javax.xml.bind.annotation.XmlType;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        16.05.14, 17:47 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@XmlType
public class TemplateData {
    final private CreatePrescriptionData data;
    private final String message;

    public TemplateData(ActionType actionType, String message, DbActionPropertyTypeBeanLocal dbActionPropertyTypeBeanLocal) throws CoreException {
        data = new CreatePrescriptionData(actionType, dbActionPropertyTypeBeanLocal);
        this.message = message;
    }

    public TemplateData() {
        message = null;
        data = null;
    }

    public CreatePrescriptionData getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}
