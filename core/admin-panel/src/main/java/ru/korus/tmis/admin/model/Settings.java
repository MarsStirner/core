package ru.korus.tmis.admin.model;

import java.io.Serializable;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        15.09.14, 17:00 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public class Settings implements Serializable {

    private SettingInfo[] settings;

    public SettingInfo[] getSettings() {
        return settings;
    }

    public void setSettings(SettingInfo[] settings) {
        this.settings = settings;
    }
}
