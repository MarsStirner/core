package ru.korus.tmis.admin.config;

import ru.korus.tmis.core.auth.AuthStorageBeanLocal;
import ru.korus.tmis.core.database.common.DbOrganizationBeanLocal;
import ru.korus.tmis.core.database.common.DbSettingsBean;
import ru.korus.tmis.core.database.common.DbSettingsBeanLocal;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        04.09.14, 9:41 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Stateless
public class EjbWrapper implements EjbWrapperLocal {

    @PersistenceContext(unitName = "s11r64")
    EntityManager s11r64;

    @EJB
    private AuthStorageBeanLocal authStorageBeanLocal;

    @EJB
    private DbSettingsBeanLocal DbSettingsBeanLocal;

    @EJB
    private DbOrganizationBeanLocal organizationBeanLocal;

    @Override
    public AuthStorageBeanLocal getAuthStorageBeanLocal() {
        return authStorageBeanLocal;
    }

    @Override
    public EntityManager getMainEntityManager() {
        return s11r64;
    }

    @Override
    public DbSettingsBeanLocal getDbSettingsBean() {
        return DbSettingsBeanLocal;
    }

    @Override
    public DbOrganizationBeanLocal getOrganizationBeanLocal() {
        return organizationBeanLocal;
    }
}
