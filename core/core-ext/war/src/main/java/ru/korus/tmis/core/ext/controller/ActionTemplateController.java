package ru.korus.tmis.core.ext.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.korus.tmis.core.ext.entities.s11r64.ActionTemplate;
import ru.korus.tmis.core.ext.model.AuthData;
import ru.korus.tmis.core.ext.model.templates.ActionTemplateData;
import ru.korus.tmis.core.ext.model.templates.ActionTemplateDataContainer;
import ru.korus.tmis.core.ext.service.ActionTemplateService;
import ru.korus.tmis.core.ext.service.AuthService;
import ru.korus.tmis.core.ext.utilities.MyJsonUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        06.05.2015, 15:02 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Controller
@RequestMapping(value = "template")
@Scope("session")
public class ActionTemplateController implements Serializable {

    @Autowired
    private ActionTemplateService actionTemplateService;

    @Autowired
    private AuthService authService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public byte[] getTemplates(@RequestParam Integer actionTypeId,
                                  @RequestParam(required = false) Integer ownerId,
                                  @RequestParam(required = false) Integer specialityId,
                                  @RequestParam(required = false) Integer groupId,
                                  @RequestParam(required = false) String callback){
        ActionTemplateDataContainer actionTemplateDataContainer = actionTemplateService.getActionTemplate(actionTypeId,
                ownerId,
                groupId,
                specialityId);
        return MyJsonUtils.toJsonWithPadding(callback, actionTemplateDataContainer);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public byte[] createTemplate(@RequestBody ActionTemplateData actionTemplateData,
                                 @RequestParam(required = false) String callback,
                                 HttpServletRequest request) {
        final AuthData authData = authService.getAuthData(request);
        return MyJsonUtils.toJsonWithPadding(callback, actionTemplateService.createActionTemplate(actionTemplateData, authData));
    }
}
