package ru.korus.tmis.ws.webmis.rest;

import ru.korus.tmis.core.entity.model.Setting;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        16.07.14, 14:31 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@XmlType
@XmlRootElement
public class SettingsInfo {
    private String path;
    private String value;
    private String description;

    public SettingsInfo() {
    }

    public SettingsInfo(Setting setting) {
        path = setting.getPath();
        value = setting.getValue();
    }


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
