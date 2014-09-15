package ru.korus.tmis.admin.service.impl;

import org.springframework.stereotype.Service;
import ru.korus.tmis.admin.model.SettingInfo;
import ru.korus.tmis.admin.model.Settings;
import ru.korus.tmis.admin.service.AllSettingsService;

import java.util.LinkedList;
import java.util.List;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        15.09.14, 15:04 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Service
public class AllSettingsServiceImpl implements AllSettingsService {
    @Override
    public Settings getSettings() {
        List<SettingInfo> settingInfoList = new LinkedList<SettingInfo>();
        SettingInfo tmp = new SettingInfo();
        tmp.setPath("path.test");
        tmp.setValue("test value");
        settingInfoList.add(tmp);

        Settings settings = new Settings();
        settings.setSettings(settingInfoList.toArray(new SettingInfo[settingInfoList.size()]));
        return settings;
    }
}
