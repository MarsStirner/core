package ru.korus.tmis.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.korus.tmis.admin.model.Settings;
import ru.korus.tmis.admin.service.AllSettingsService;

import java.io.Serializable;
import java.util.Map;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        15.09.14, 13:38 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Controller
@RequestMapping(value = "admin/settings")
@Scope("session")
public class SettingsController implements Serializable {

    @Autowired
    AllSettingsService allSettingsService;

    @RequestMapping(method = RequestMethod.GET)
    public String viewRegistration(Map<String, Object> model) {
        model.put("state", ViewState.ALL_SETTINGS);
        model.put("tmisSettings", allSettingsService.getSettings() );
        return AdminController.MAIN_JSP;
    }

}
