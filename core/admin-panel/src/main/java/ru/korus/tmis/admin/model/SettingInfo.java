package ru.korus.tmis.admin.model;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        15.09.14, 14:59 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public class SettingInfo {

    private String path = new String();

    private String value = new String();

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getPath() {

        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
