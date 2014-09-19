package ru.korus.tmis.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.korus.tmis.admin.model.RisarSettings;
import ru.korus.tmis.admin.service.RisarService;

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
@RequestMapping(value = "admin/risar")
@Scope("session")
public class RisarController implements Serializable {

    @Autowired
    private RisarService risarService;

    @RequestMapping(method = RequestMethod.GET)
    public String get(Map<String, Object> model, HttpServletRequest request) {
        model.put("state", ViewState.RISAR);
        model.put("risarSettings", risarService.getRisarSettings());
        return AdminController.MAIN_JSP;
    }

    @RequestMapping(value = "url", method = RequestMethod.POST)
    public String updateOrgId(@ModelAttribute RisarSettings risarSettings, Map<String, Object> model, HttpServletRequest request) {
        risarService.updateRisarUrl(risarSettings.getUrl());
        risarService.checkUrl(risarSettings.getUrl());
        return ViewState.RISAR.redirect();
    }
}
