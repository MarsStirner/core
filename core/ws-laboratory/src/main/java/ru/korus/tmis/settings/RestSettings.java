package ru.korus.tmis.settings;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.database.DbSettingsBeanLocal;
import ru.korus.tmis.core.entity.model.Setting;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        31.05.13, 15:22 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Stateless
@Path("/api")
public class RestSettings {
    private static final Logger logger = LoggerFactory.getLogger(RestSettings.class);

    @EJB(beanName = "DbSettingsBean")
    private DbSettingsBeanLocal settingsBean;

    @GET
    @Path("/settings")
    public Response getAll() {
        final Gson gson = new Gson();
        final List<JsonSetting> settingList = new ArrayList<JsonSetting>();
        for (Setting s : settingsBean.getAllSettings()) {
            settingList.add(new JsonSetting(s.getPath(), s.getValue(), s.getId().toString()));
        }
        return Response.status(Response.Status.OK).entity(gson.toJson(settingList)).build();
    }

    @GET
    @Path("/settings/{path}")
    public Response getPath(@PathParam(value = "path") final String path) {
        final Gson gson = new Gson();
        final Setting settingByPath = settingsBean.getSettingByPath(path);
        return Response.status(Response.Status.OK).entity(gson.toJson(settingByPath)).build();
    }
}
