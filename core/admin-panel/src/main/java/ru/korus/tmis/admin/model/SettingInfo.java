package ru.korus.tmis.admin.model;

import ru.korus.tmis.core.entity.model.Setting;

import java.io.Serializable;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        15.09.14, 14:59 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public class SettingInfo implements Serializable {

    private String path = new String();

    private String value = new String();

    private Boolean enable = false;

    public SettingInfo() {
    }

    public SettingInfo(Setting s) {
        setPath(s.getPath());
        setValue(s.getValue());
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
        enable = "on".equals(value);
    }

    public String getPath() {

        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isBoolean() {
        return "on".equals(value) || "off".equals(value);
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        value = enable ? "on" : "off";
        this.enable = enable;
    }
}
