package ru.korus.tmis.prescription;

import ru.korus.tmis.core.entity.model.APValue;
import ru.korus.tmis.core.entity.model.ActionProperty;
import ru.korus.tmis.core.entity.model.ActionPropertyType;

import javax.xml.bind.annotation.XmlType;
import java.util.List;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        15.05.14, 13:49 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@XmlType
public class PropertiesData {
    private final Integer id;
    private final String name;
    private final String type;
    private final String code;
    private final String valueDomain;
    private final String value;
    private final String valueId;

    public PropertiesData(ActionProperty actionProperty, List<APValue> apValueList) {
        id = actionProperty.getId();
        final ActionPropertyType propType = actionProperty.getType();
        name = propType == null ? null : propType.getName();
        type =  propType == null ? null : propType.getTypeName();
        code = propType == null ? null : propType.getCode();
        valueDomain = (propType == null || propType.getValueDomain() == null) ? null :
                propType.getValueDomain().split(";")[0];
        APValue apValue = apValueList.isEmpty() ? null : apValueList.get(0);
        final boolean isRefRb = actionProperty.getType().getTypeName().equals("ReferenceRb");
        value = apValue == null ? null : apValue.getValueAsString();
        valueId = apValue == null ? null : isRefRb ? apValue.getValueAsString() : apValue.getValueAsId();
    }

    public PropertiesData() {

        id = null;
        name = null;
        type = null;
        code = null;
        valueDomain = null;
        value = null;
        valueId = null;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getCode() {
        return code;
    }

    public String getValueDomain() {
        return valueDomain;
    }

    public String getValue() {
        return value;
    }

    public String getValueId() {
        return valueId;
    }
}
