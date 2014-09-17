package ru.korus.tmis.ws.webmis.rest;

import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.database.common.DbSettingsBeanLocal;
import ru.korus.tmis.core.entity.model.Setting;
import ru.korus.tmis.core.logging.slf4j.interceptor.ServicesLoggingInterceptor;
import ru.korus.tmis.prescription.PrescriptionBeanLocal;

import javax.interceptor.Interceptors;
import javax.ws.rs.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        16.07.14, 13:31 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Interceptors(ServicesLoggingInterceptor.class)
public class SettingsRESTImpl {

    final private DbSettingsBeanLocal dbSettingsBeanLocal;
    final private AuthData auth;
    final private String callback;

    public SettingsRESTImpl(DbSettingsBeanLocal dbSettingsBeanLocal, AuthData auth, String callback) {
        this.dbSettingsBeanLocal = dbSettingsBeanLocal;
        this.auth = auth;
        this.callback = callback;
    }


    @GET
    @Path("/")
    //@Produces("application/x-javascript")
    public List<SettingsInfo> getSettings() {
        List<SettingsInfo> res = new LinkedList<SettingsInfo>();
        List<Setting> settings = dbSettingsBeanLocal.getAllSettings();
        for(Setting setting : settings) {
            res.add(new SettingsInfo(setting));
        }
        return res;
    }

    @PUT
    @Path("/")
    //@Produces("application/x-javascript")
    public boolean setSettings(@QueryParam("path") String path, @QueryParam("value") String value ) {

        return dbSettingsBeanLocal.updateSetting(path, value);
    }

}
