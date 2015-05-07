package ru.korus.tmis.core.ext.service;

import ru.korus.tmis.core.ext.model.AuthData;
import ru.korus.tmis.core.ext.model.templates.ActionTemplateData;
import ru.korus.tmis.core.ext.model.templates.ActionTemplateDataContainer;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        07.05.2015, 11:12 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */

public interface ActionTemplateService {

    ActionTemplateDataContainer getActionTemplate(Integer actionTypeId, Integer ownerId, Integer groupId, Integer specialityId);

    ActionTemplateData createActionTemplate(ActionTemplateData actionTemplateData, AuthData authData);
}
