package ru.korus.tmis.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.korus.tmis.admin.model.CommonSettings;
import ru.korus.tmis.admin.service.CommonService;
import ru.korus.tmis.core.database.common.DbOrganizationBeanLocal;
import ru.korus.tmis.core.entity.model.Organisation;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.scala.util.ConfigManager;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        16.09.14, 16:48 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    DbOrganizationBeanLocal organizationBeanLocal;

    @Override
    public CommonSettings getCommonSettings() {
        CommonSettings res = new CommonSettings();
        final int orgId = ConfigManager.Common().OrgId();
        res.setOrgId(orgId);
        try {
            Organisation organization = organizationBeanLocal.getOrganizationById(orgId);
            if (organization != null) {
                res.setOrgName(organization.getShortName());
            }
        } catch (CoreException ex) {
            ex.printStackTrace();
            res.setOrgName(ex.getMessage());
        }
        return res;
    }
}
