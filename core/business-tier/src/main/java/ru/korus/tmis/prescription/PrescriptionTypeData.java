package ru.korus.tmis.prescription;

import ru.korus.tmis.core.entity.model.ActionType;

import javax.xml.bind.annotation.XmlType;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        16.05.14, 16:25 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */

@XmlType
public class PrescriptionTypeData {

    private final String id;
    private final String title;

    public PrescriptionTypeData(ActionType actionType) {
        id = String.valueOf(actionType.getId());
        title = actionType.getTitle();
    }

    public PrescriptionTypeData() {
        id = null;
        title = null;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
