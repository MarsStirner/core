package ru.korus.tmis.admin.service;

import ru.korus.tmis.admin.model.DbInfo;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        11.09.14, 13:02 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public interface DbInfoService {

    DbInfo getMainDbInfo();

    DbInfo getSettingsDbInfo();
}
