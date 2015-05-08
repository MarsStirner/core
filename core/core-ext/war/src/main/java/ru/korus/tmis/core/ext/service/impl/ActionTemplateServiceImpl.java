package ru.korus.tmis.core.ext.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    @Override
    public ActionTemplateDataContainer getActionTemplate(Integer actionTypeId, Integer ownerId, Integer groupId, Integer specialityId) {

        List<ActionTemplate> actionTemplateList = actionTemplateRepository.findByActionTypeAndPersonAdnGroupAndSspeciality(
                actionTypeId,
                ownerId,
                groupId,
                specialityId);
        if(ownerId == null && groupId == null && specialityId == null) {
            actionTemplateList = addNode(actionTemplateList);
        }

        return toActionTemplateDataContainer(actionTemplateList);
    }

    private List<ActionTemplate> addNode(List<ActionTemplate> actionTemplateList) {
        List<ActionTemplate> res = new LinkedList<>();
        Set<Integer> groupIdSet = new HashSet<>();
        for(ActionTemplate actionTemplate : actionTemplateList) {
             groupIdSet.add(getParent(actionTemplate).getId());
        }
        for(Integer i : groupIdSet) {
            ActionTemplate actionTemplate = actionTemplateRepository.findOne(i);
            if(actionTemplate != null) {
                res.add(actionTemplate);
            }
        }
        return res;
    }

    private ActionTemplate getParent(ActionTemplate actionTemplate) {
        return actionTemplate.getGroup() == null ? actionTemplate : getParent(actionTemplate.getGroup());
    }

    private ActionTemplateDataContainer toActionTemplateDataContainer(List<ActionTemplate> actionTemplateList) {
        ActionTemplateDataContainer res = new ActionTemplateDataContainer();
        for(ActionTemplate actionTemplate : actionTemplateList ) {
            res.getActionTemplateList().add(toActionTemplateData(actionTemplate));
        }
        return res;
    }

    @Override
    public ActionTemplateData createActionTemplate(ActionTemplateData actionTemplateData, AuthData authData) {
        Action action = actionRepository.findOne(actionTemplateData.getActionId());
        if (action == null) {
            throw new RuntimeException("Action not found. id =" + actionTemplateData.getActionId());
        }
        final Date now = new Date();
        ActionTemplate actionTemplate = new ActionTemplate();
        actionTemplate.setAction(action);
        actionTemplate.setCreateDatetime(now);
        actionTemplate.setModifyDatetime(now);
        actionTemplate.setModifyPerson_id(authData.getUserId());
        actionTemplate.setName(actionTemplateData.getName());
        if (actionTemplateData.getGroupId() != null) {
            ActionTemplate actionTemplateGroup = actionTemplateRepository.findOne(actionTemplateData.getGroupId());
            actionTemplate.setGroup(actionTemplateGroup);
        }
        actionTemplate.setSpecialityId(actionTemplateData.getSpecialityId());
        actionTemplate.setOwnerId(actionTemplateData.getOwnerId());
        actionTemplate = actionTemplateRepository.saveAndFlush(actionTemplate);
        return toActionTemplateData(actionTemplate);
    }

    private ActionTemplateData toActionTemplateData(ActionTemplate actionTemplate) {

        ActionTemplateData res = new ActionTemplateData();
        if (actionTemplate != null) {
            res.setId(actionTemplate.getId());
            res.setGroupId(actionTemplate.getGroup() == null ? null : actionTemplate.getGroup().getId());
            res.setActionId(actionTemplate.getAction() == null ? null : actionTemplate.getAction().getId());
            res.setName(actionTemplate.getName());
            for (ActionTemplate at : actionTemplate.getActionTemplateList()) {
                res.getTemplates().add(toActionTemplateData(at));
            }
        }
        return res;
    }
}
