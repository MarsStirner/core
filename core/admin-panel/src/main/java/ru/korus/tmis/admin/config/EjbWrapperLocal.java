package ru.korus.tmis.admin.config;

import org.springframework.stereotype.Service;
import ru.korus.tmis.core.auth.AuthStorageBeanLocal;
import ru.korus.tmis.core.database.common.DbSettingsBean;
import ru.korus.tmis.core.database.common.DbSettingsBeanLocal;

import javax.ejb.Local;
import javax.persistence.EntityManager;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        04.09.14, 9:35 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Local
public interface EjbWrapperLocal {

    AuthStorageBeanLocal getAuthStorageBeanLocal();

    EntityManager getMainEntityManager();

    DbSettingsBeanLocal getDbSettingsBean();
}
