package ru.korus.tmis.prescription;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import ru.korus.tmis.core.entity.model.ActionPropertyType;

import javax.xml.bind.annotation.XmlType;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        16.05.14, 18:11 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@XmlType
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActionPropertyTypeData {
    private Integer actionPropertyTypeId;
    private boolean mandatory;
    private String name;
    private String valueDomain;
    private String type;
    private String code;
    private String value;
    private Integer valueId;
    private Integer id = null;

    public ActionPropertyTypeData(ActionPropertyType actionPropertyType) {
        actionPropertyTypeId = actionPropertyType.getId();
        mandatory = actionPropertyType.isMandatory();
        name = actionPropertyType.getName();
        valueDomain = actionPropertyType.getValueDomain();
        type = actionPropertyType.getTypeName();
        code = actionPropertyType.getCode();
        value = null;
    }

    public ActionPropertyTypeData() {
        actionPropertyTypeId = null;
        mandatory = false;
        name = null;
        valueDomain = null;
        type = null;
        code = null;
        value = null;
    }

    public Integer getActionPropertyTypeId() {
        return actionPropertyTypeId;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    public String getName() {
        return name;
    }

    public String getValueDomain() {
        return valueDomain;
    }

    public String getType() {
        return type;
    }

    public String getCode() {
        return code;
    }

    public void setActionPropertyTypeId(Integer actionPropertyTypeId) {
        this.actionPropertyTypeId = actionPropertyTypeId;
    }

    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValueDomain(String valueDomain) {
        this.valueDomain = valueDomain;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getValueId() {
        return valueId;
    }

    public void setValueId(Integer valueId) {
        this.valueId = valueId;
    }
}
