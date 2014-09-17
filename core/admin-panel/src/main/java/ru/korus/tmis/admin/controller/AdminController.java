package ru.korus.tmis.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.korus.tmis.admin.service.DbInfoService;
import ru.korus.tmis.core.auth.AuthStorageBeanLocal;
import ru.korus.tmis.scala.util.ConfigManager;

import java.io.Serializable;
import java.util.Map;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        02.09.14, 12:36 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Controller
@RequestMapping(value = "admin")
@Scope("session")
public class AdminController implements Serializable {

    public final static String MAIN_JSP = "admin";
    @Autowired
    AuthStorageBeanLocal authStorageBeanLocal;

    @Autowired
    DbInfoService dbInfoService;

    @RequestMapping(method = RequestMethod.GET)
    public String get(Map<String, Object> model) {
        model.put("s11r64Db", dbInfoService.getMainDbInfo());
        model.put("tmisCoreDb", dbInfoService.getSettingsDbInfo());
        model.put("state", ViewState.MAIN);
        model.put("version", ConfigManager.Common().version());
        model.put("domainPath",  dbInfoService.getDomainPath());
        return MAIN_JSP;
    }

}
