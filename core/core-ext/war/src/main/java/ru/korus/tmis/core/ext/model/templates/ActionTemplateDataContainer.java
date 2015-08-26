package ru.korus.tmis.core.ext.model.templates;

import java.util.LinkedList;
import java.util.List;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        07.05.2015, 17:22 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public class ActionTemplateDataContainer {

    private List<ActionTemplateData> actionTemplateList;

    public List<ActionTemplateData> getActionTemplateList() {
        if(actionTemplateList == null) {
            actionTemplateList = new LinkedList<>();
        }
        return actionTemplateList;
    }

    public void setActionTemplateList(List<ActionTemplateData> actionTemplateList) {
        this.actionTemplateList = actionTemplateList;
    }
}
