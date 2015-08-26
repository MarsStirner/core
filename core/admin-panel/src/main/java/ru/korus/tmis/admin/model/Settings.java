package ru.korus.tmis.admin.model;

import java.io.Serializable;
import java.util.List;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        15.09.14, 17:00 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public class Settings implements Serializable {

    private List<SettingInfo> settings;

    public List<SettingInfo> getSettings() {
        return settings;
    }

    public void setSettings(List<SettingInfo> settings) {
        this.settings = settings;
    }
}
