package ru.korus.tmis.ws.transfusion.order;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.util.EntityMgr;
import ru.korus.tmis.ws.transfusion.IssueResult;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        16.01.2013, 10:25:56 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */

/**
 * Регистрация извещения о резульатах выполнения требования КК
 */
public class RegOrderIssueResult {

    /**
     * Регистрация извещения о резульатах выполнения требования КК
     * 
     * @param orderIssue
     *            - входные данные от подсистемы ТРФУ
     * @return результат регистрации
     */
    
    private static final Logger logger = LoggerFactory.getLogger(SendOrderBloodComponents.class);
    
    public IssueResult save(OrderIssueInfo orderIssueInfo) {
        
        IssueResult res = new IssueResult();
        res.setResult(false);
        EntityManager em = null; 
        try {
            em = EntityMgr.getEntityManagerForS11r64(logger);
        } catch (CoreException ex) {
            logger.error("Cannot create entety manager. Error description: '{}'", ex.getMessage());            
            ex.printStackTrace();
            res.setDescription("MIS Internal error");
            return res;
        }

        if (orderIssueInfo == null) {
            res.setDescription("illegal input argument");
            return res;
        }

        res.setRequestId(orderIssueInfo.getRequestId());
        Integer actionId = getAction(em, orderIssueInfo.getRequestId());

        if (actionId == null) { // требование КК не найдено в базе данных
            res.setDescription(String.format("The issue for requestId '%s' has been not found in MIS", "" + orderIssueInfo.getRequestId()));
            res.setResult(false);
            return res;
        }

        try {
            update(em, actionId, orderIssueInfo);
        } catch (CoreException ex) {
            logger.error("Cannot update action {} property. Error description: '{}'", actionId, ex.getMessage());            
            ex.printStackTrace();
            res.setDescription("MIS Internal error");
            return res;
        }

        return res;
    }

    /**
     * @param em 
     * @param actionId
     * @param orderIssue
     * @throws CoreException 
     */
    private void update(EntityManager em, Integer actionId, OrderIssueInfo orderIssue) throws CoreException {
        
        TrfuActionProp  trfuActionProp = TrfuActionProp.getInstance(em);
        
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date facDate;
        final boolean update = true;
        try {
            facDate = formatter.parse(orderIssue.getFactDate());
            trfuActionProp.setProp(facDate, em, actionId, TrfuActionProp.PropType.ORDER_ISSUE_RES_TIME, update);
            trfuActionProp.setProp(facDate, em, actionId, TrfuActionProp.PropType.ORDER_ISSUE_RES_DATE, update);
        } catch (ParseException e) {
            logger.error("Wrong date format for factDate: input factDate = {}", orderIssue.getFactDate());
            e.printStackTrace();
        }
        trfuActionProp.setProp(orderIssue.getComponentId(), em, actionId, TrfuActionProp.PropType.ORDER_ISSUE_RES_BLOOD_ID, update);
        trfuActionProp.setProp(orderIssue.getNumber(), em, actionId, TrfuActionProp.PropType.ORDER_ISSUE_RES_BLOOD_NUMBER, update);
        trfuActionProp.setProp(orderIssue.getComponentId(), em, actionId, TrfuActionProp.PropType.ORDER_ISSUE_RES_BLOOD_TYPE_ID, update);
        trfuActionProp.setProp(orderIssue.getBloodGroupId(), em, actionId, TrfuActionProp.PropType.ORDER_ISSUE_RES_DONOR_BLOOD_GROUP, update);
        trfuActionProp.setProp(orderIssue.getRhesusFactorId(), em, actionId, TrfuActionProp.PropType.ORDER_ISSUE_RES_DONOR_BLOOD_RHESUS, update);
        trfuActionProp.setProp(orderIssue.getVolume(), em, actionId, TrfuActionProp.PropType.ORDER_ISSUE_RES_DONOR_VALUE, update);
        trfuActionProp.setProp(orderIssue.getDoseCount(), em, actionId, TrfuActionProp.PropType.ORDER_ISSUE_RES_DONOR_COUNT, update);
        trfuActionProp.setProp(orderIssue.getDonorId(), em, actionId, TrfuActionProp.PropType.ORDER_ISSUE_RES_DONOR_CODE, update);
        trfuActionProp.setProp(orderIssue.getOrderComment(), em, actionId, TrfuActionProp.PropType.ORDER_ISSUE_RES_COMMENT, update);
    }

    /**
     * @param requestId
     * @param em 
     * @return
     */
    private Integer getAction( EntityManager em, Integer requestId) {
        final List<Action> actions = em.createQuery("SELECT a FROM Action a WHERE a.id = :requestId", Action.class)
                .setParameter("requestId", requestId).getResultList();
        return actions.isEmpty() ? null : actions.get(0).getId();
    }
}
