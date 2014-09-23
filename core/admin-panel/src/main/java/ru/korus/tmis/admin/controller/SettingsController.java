package ru.korus.tmis.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.korus.tmis.admin.model.Settings;
import ru.korus.tmis.admin.service.AllSettingsService;

import javax.servlet.http.HttpServletRequest;
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

    public static final String SETTINGS_LIST = "SETTINGS_LIST";
    @Autowired
    AllSettingsService allSettingsService;

    @RequestMapping(method = RequestMethod.GET)
    public String get(Map<String, Object> model, HttpServletRequest request) {
        model.put("state", ViewState.ALL_SETTINGS);
        Settings settingsList = allSettingsService.getSettings();
        model.put("tmisSettings", settingsList);
        request.getSession().setAttribute(SETTINGS_LIST, settingsList);
        return AdminController.MAIN_JSP;
    }


    @RequestMapping(method = RequestMethod.POST)
    public String get(@ModelAttribute("tmisSettings") Settings settings, Map<String, Object> model, HttpServletRequest request) {
        Object settingsList = request.getSession().getAttribute(SETTINGS_LIST);
        if(settingsList != null && settingsList instanceof Settings ) {

            for(int i = 0; i < ((Settings)settingsList).getSettings().size(); ++i) {
                settings.getSettings().get(i).setPath(((Settings) settingsList).getSettings().get(i).getPath());
            }
            allSettingsService.save(settings);
        }
        model.put("state", ViewState.ALL_SETTINGS);
        Settings settingsListNew = allSettingsService.getSettings();
        model.put("tmisSettings", settingsListNew);
        request.getSession().setAttribute(SETTINGS_LIST, settingsListNew);
        return AdminController.MAIN_JSP;

    }

}
