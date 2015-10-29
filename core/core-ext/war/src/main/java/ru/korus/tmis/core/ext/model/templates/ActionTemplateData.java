package ru.korus.tmis.core.ext.model.templates;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.LinkedList;
import java.util.List;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        07.05.2015, 11:13 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActionTemplateData {
    private Integer id;
    private Integer groupId;
    private Integer actionId;
    private String authToken;
    private Integer user_id;
    private String name;
    private List<ActionTemplateData> templates;
    private Integer specialityId;
    private Integer ownerId;


    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(final String authToken) {
        this.authToken = authToken;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(final Integer user_id) {
        this.user_id = user_id;
    }

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
        if(templates == null) {
            templates = new LinkedList<>();
        }
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
