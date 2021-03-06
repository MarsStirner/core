package ru.korus.tmis.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.korus.tmis.admin.model.CommonSettings;
import ru.korus.tmis.admin.model.Settings;
import ru.korus.tmis.admin.service.AllSettingsService;
import ru.korus.tmis.admin.service.CommonService;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Map;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        16.09.14, 16:11 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Controller
@RequestMapping(value = "admin/common")
@Scope("session")
public class CommonController implements Serializable {

    @Autowired
    private CommonService commonService;

    @Autowired
    private AllSettingsService allSettingsService;

    @RequestMapping(method = RequestMethod.GET)
    public String get(Map<String, Object> model, HttpServletRequest request) {
        model.put("state", ViewState.COMMON);
        model.put("commonSettings", commonService.getCommonSettings());
        return AdminController.MAIN_JSP;
    }

    @RequestMapping(value = "orgid", method = RequestMethod.POST)
    public String updateOrgId(@ModelAttribute CommonSettings commonSettings, Map<String, Object> model, HttpServletRequest request) {
        allSettingsService.save("Common.OrgId", String.valueOf(commonSettings.getOrgId()));
        return ViewState.COMMON.redirect();
    }
}
