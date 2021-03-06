package ru.korus.tmis.admin.config;

import ru.korus.tmis.core.auth.AuthStorageBeanLocal;
import ru.korus.tmis.core.database.common.DbOrganizationBeanLocal;
import ru.korus.tmis.core.database.common.DbSettingsBeanLocal;
import ru.korus.tmis.core.notification.DbNotificationActionBeanLocal;

import javax.ejb.Local;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        04.09.14, 9:35 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Local
public interface EjbWrapperLocal {

    AuthStorageBeanLocal getAuthStorageBeanLocal();

    DbSettingsBeanLocal getDbSettingsBean();

    DbOrganizationBeanLocal getOrganizationBeanLocal();

    DbNotificationActionBeanLocal getDbNotificationActionBeanLocal();
}
