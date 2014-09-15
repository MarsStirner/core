package ru.korus.tmis.admin.service;

import org.springframework.stereotype.Service;
import ru.korus.tmis.admin.model.SettingInfo;
import ru.korus.tmis.admin.model.Settings;

import java.util.List;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        15.09.14, 15:02 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public interface AllSettingsService {
    Settings getSettings();
}
