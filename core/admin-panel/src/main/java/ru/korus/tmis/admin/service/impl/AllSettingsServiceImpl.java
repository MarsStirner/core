package ru.korus.tmis.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.korus.tmis.admin.model.SettingInfo;
import ru.korus.tmis.admin.model.Settings;
import ru.korus.tmis.admin.service.AllSettingsService;
import ru.korus.tmis.core.database.common.DbSettingsBeanLocal;
import ru.korus.tmis.core.entity.model.Setting;


import java.util.ArrayList;
import java.util.List;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        15.09.14, 15:04 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Service
public class AllSettingsServiceImpl implements AllSettingsService {

    @Autowired
    DbSettingsBeanLocal dbSettingsBean;
    private ArrayList<SettingInfo> res;

    @Override
    public Settings getSettings() {
        List<SettingInfo> settingInfoList = getAllSettings();

        Settings settings = new Settings();
        settings.setSettings(settingInfoList.toArray(new SettingInfo[settingInfoList.size()]));
        return settings;
    }

    @Override
    public void save(Settings settings) {
        for(SettingInfo s : settings.getSettings()) {
            save(s.getPath(), s.getValue());
        }
    }

    @Override
    public void save(String path, String value) {
        dbSettingsBean.updateSetting(path, value);
    }

    private List<SettingInfo> getAllSettings() {
        res = new ArrayList<SettingInfo>();
        try {
            List<Setting> settings = dbSettingsBean.getAllSettings();
            for(Setting s : settings) {
                res.add(new SettingInfo(s));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return res;
    }


}
