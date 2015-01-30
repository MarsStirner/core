package ru.korus.tmis.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.korus.tmis.admin.model.DbInfo;
import ru.korus.tmis.admin.service.DbInfoService;
import ru.korus.tmis.admin.service.impl.domain.*;

import javax.xml.bind.JAXB;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        11.09.14, 13:07 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Service
public class DbInfoServiceImpl implements DbInfoService {

    static String domainPath = null;

    static DomainType domainXml = null;


    @Override
    public DbInfo getMainDbInfo() {
        return getDbInfo("s11r64");
    }

    @Override
    public DbInfo getSettingsDbInfo() {
        return getDbInfo("tmis_core");
    }


    private DbInfo getDbInfo(String poolJndiName) {
        DbInfo res = new DbInfo("???", "???");
        try {
            List<PropertyType> properties = getDbProperties(poolJndiName);
            res = new DbInfo(getDbName(properties),getDbHost(properties));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }


    private List<PropertyType> getDbProperties(String poolJndiName) throws FileNotFoundException {
        DomainType domainXml = getDomainXml();
        List<PropertyType> properties = new LinkedList<PropertyType>();
/*
        ApplicationsType applications = domainXml.getApplications();
        ApplicationType app = applications == null ? null : getTmisApp(applications.getApplication());
*/
        if(domainXml != null && domainXml.getResources() != null) {
            List<Object> jdbcConnectionPoolList = domainXml.getResources().getJdbcConnectionPoolOrJdbcResourceOrAdminObjectResource();
            resList:for(Object resource : jdbcConnectionPoolList) {
                if(resource instanceof JdbcResourceType &&
                        poolJndiName.equals(((JdbcResourceType) resource).getJndiName())) {
                   final String s11r64PoolName = ((JdbcResourceType) resource).getPoolName();
                   for(Object connectionPool : jdbcConnectionPoolList) {
                       if(connectionPool instanceof JdbcConnectionPoolType &&
                               s11r64PoolName.equals(((JdbcConnectionPoolType) connectionPool).getName())) {
                           properties = ((JdbcConnectionPoolType) connectionPool).getProperty();
                           break resList;
                       }
                   }
                }
            }
        }
        return properties;
    }

    private DomainType getDomainXml() throws FileNotFoundException {
        if(this.domainXml == null) {
            final String pathToDamainConfig = getDomainPath() + "/config/domain.xml";
            this.domainXml = JAXB.unmarshal(new FileInputStream(pathToDamainConfig), DomainType.class);
        }
        return this.domainXml;
    }


    private String getDbHost(List<PropertyType> properties) {
        return  getPropByName(properties, "DatabaseName");
    }

    private String getDbName(List<PropertyType> properties) {
        return  getPropByName(properties, "ServerName");
    }

    private String getPropByName(List<PropertyType> properties, String propName) {
        for(PropertyType prop : properties) {
            if ( propName.equals(prop.getName()) ) {
                return prop.getValueAttr();
            }
        }
        return "???";
    }

    private ApplicationType getTmisApp(List<ApplicationType> application) {
        for(ApplicationType app : application) {
            if(app.getName().contains("tmis-server") ||
                    app.getName().contains("admin-panel")) {
                return app;
            }
        }
        return null;
    }


    @Override
    public String getDomainPath() {
        if (domainPath == null) {
            domainPath = initDomainPath();
        }
        return domainPath;
    }

    private String initDomainPath() {
        final String path = this.getClass().getResource("").getPath();
        final Integer indexEnd = Math.max(path.indexOf("/applications/admin-panel"), path.indexOf("/applications/tmis-server"));
        if (indexEnd > 0) {
            return path.substring(0, indexEnd);
        }
        return "unknown";
    }
}
