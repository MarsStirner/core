package ru.korus.tmis.core.ext.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.korus.tmis.core.ext.ambulatoryService.ActionTemplateCreateRequest;
import ru.korus.tmis.core.ext.ambulatoryService.ActionTemplateCreateResponse;
import ru.korus.tmis.core.ext.config.ConfigManager;
import ru.korus.tmis.core.ext.entities.s11r64.Action;
import ru.korus.tmis.core.ext.entities.s11r64.ActionTemplate;
import ru.korus.tmis.core.ext.model.AuthData;
import ru.korus.tmis.core.ext.model.templates.ActionTemplateData;
import ru.korus.tmis.core.ext.model.templates.ActionTemplateDataContainer;
import ru.korus.tmis.core.ext.repositories.s11r64.ActionRepository;
import ru.korus.tmis.core.ext.repositories.s11r64.ActionTemplateRepository;
import ru.korus.tmis.core.ext.service.ActionTemplateService;

import java.util.*;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        07.05.2015, 11:13 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Service
public class ActionTemplateServiceImpl implements ActionTemplateService {

    @Autowired
    ActionTemplateRepository actionTemplateRepository;

    @Autowired
    ActionRepository actionRepository;

    @Autowired
    ConfigManager configManager;

    @Override
    public ActionTemplateDataContainer getActionTemplate(Integer actionTypeId, Integer ownerId, Integer groupId, Integer specialityId) {

        List<ActionTemplate> actionTemplateList = actionTemplateRepository.findByActionTypeAndPersonAdnGroupAndSspeciality(
                actionTypeId,
                ownerId,
                groupId,
                specialityId);
        if (ownerId == null && groupId == null && specialityId == null) {
            actionTemplateList = addNode(actionTemplateList, actionTypeId);
        }

        return toActionTemplateDataContainer(actionTemplateList, actionTypeId);
    }

    private List<ActionTemplate> addNode(List<ActionTemplate> actionTemplateList, Integer actionTypeId) {
        List<ActionTemplate> res = new LinkedList<>();
        Set<Integer> groupIdSet = new HashSet<>();
        for (ActionTemplate actionTemplate : actionTemplateList) {
            groupIdSet.add(getParent(actionTemplate).getId());
        }
        for (Integer i : groupIdSet) {
            ActionTemplate actionTemplate = actionTemplateRepository.findOne(i);
            if (actionTemplate != null) {
                res.add(actionTemplate);
            }
        }
        return res;
    }

    private ActionTemplate getParent(ActionTemplate actionTemplate) {
        return actionTemplate.getGroup() == null ? actionTemplate : getParent(actionTemplate.getGroup());
    }

    private ActionTemplateDataContainer toActionTemplateDataContainer(List<ActionTemplate> actionTemplateList, Integer actionTypeId) {
        ActionTemplateDataContainer res = new ActionTemplateDataContainer();
        for (ActionTemplate actionTemplate : actionTemplateList) {
            res.getActionTemplateList().add(toActionTemplateData(actionTemplate, actionTypeId));
        }
        return res;
    }

    @Override
    public ActionTemplateData createActionTemplate(ActionTemplateData actionTemplateData, AuthData authData) {
        Action action = actionTemplateData.getActionId() == null ? null : actionRepository.findOne(actionTemplateData.getActionId());
        final RestTemplate restTemplate = new RestTemplate();

        final ActionTemplateCreateRequest request = new ActionTemplateCreateRequest();
        request.setAid(actionTemplateData.getActionId());
        request.setName(actionTemplateData.getName());
        request.setGid(actionTemplateData.getGroupId());
        request.setUser_id(actionTemplateData.getUser_id());

        final HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie", "authToken=" + actionTemplateData.getAuthToken());

        final HttpEntity<ActionTemplateCreateRequest> requestEntity = new HttpEntity<>(request, requestHeaders);

        final Map<String, Object> uriVariables = new HashMap<>(2);
        uriVariables.put("actionType_id", action != null ? action.getActionType_id() : 0);
        //uriVariables.put("action_id", actionTemplateData.getActionId());

       //TODO адрес амбулатории хранить надо где-то в настройках
        final ResponseEntity<ActionTemplateCreateResponse> response =
                restTemplate.exchange(
                        configManager.getAmbulatoryUrl()+ "/actions/api/templates/{actionType_id}",
                        HttpMethod.PUT,
                        requestEntity,
                        ActionTemplateCreateResponse.class,
                        uriVariables
                );
        final ActionTemplateCreateResponse result = response.getBody();
        if("OK".equals(result.getMeta().getName())){
            final ActionTemplate res = actionTemplateRepository.getOne(result.getResult().getId());
            return toActionTemplateData(res, action == null ? null : action.getActionType_id());
        }  else {
            return null;
        }
    }

    private ActionTemplateData toActionTemplateData(ActionTemplate actionTemplate, Integer actionTypeId) {

        ActionTemplateData res = new ActionTemplateData();
        if (actionTemplate != null) {
            res.setId(actionTemplate.getId());
            res.setGroupId(actionTemplate.getGroup() == null ? null : actionTemplate.getGroup().getId());
            res.setActionId(actionTemplate.getAction() == null ? null : actionTemplate.getAction().getId());
            res.setName(actionTemplate.getName());
            for (ActionTemplate at : actionTemplate.getActionTemplateList()) {
                if(at.getAction() == null || at.getAction().getActionType_id().equals(actionTypeId)) {
                    res.getTemplates().add(toActionTemplateData(at, actionTypeId));
                }
            }
        }
        return res;
    }
}
