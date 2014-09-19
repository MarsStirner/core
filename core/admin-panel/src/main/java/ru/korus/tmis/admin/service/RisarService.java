package ru.korus.tmis.admin.service;

import ru.korus.tmis.admin.model.RisarSettings;

import java.net.URL;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        19.09.14, 11:00 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public interface RisarService {
    RisarSettings getRisarSettings();

    RisarSettings.ValidationState checkUrl(URL url);

    void updateRisarUrl(URL url);
}
