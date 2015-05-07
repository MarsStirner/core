package ru.korus.tmis.core.ext.model.templates;

import javax.swing.*;
import java.util.List;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        07.05.2015, 11:13 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public class ActionTemplateData {
    private Integer id;
    private Integer groupId;
    private Integer actionId;
    private String name;
    private List<ActionTemplateData> templates;
    private Integer specialityId;
    private Integer ownerId;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setActionId(Integer actionId) {
        this.actionId = actionId;
    }

    public Integer getActionId() {
        return actionId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<ActionTemplateData> getTemplates() {
        return templates;
    }

    public Integer getSpecialityId() {
        return specialityId;
    }

    public void setSpecialityId(Integer specialityId) {
        this.specialityId = specialityId;
    }

    public Integer getOwnerId() {
        return ownerId;
    }
}
