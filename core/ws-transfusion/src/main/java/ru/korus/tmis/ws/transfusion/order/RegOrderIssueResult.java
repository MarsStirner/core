package ru.korus.tmis.ws.transfusion.order;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.entity.model.RbBloodComponentType;
import ru.korus.tmis.core.entity.model.RbBloodType;
import ru.korus.tmis.core.entity.model.TrfuOrderIssueResult;
import ru.korus.tmis.core.exception.CoreException;
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
@Stateless
public class RegOrderIssueResult {

    /**
     * Регистрация извещения о резульатах выполнения требования КК
     * 
     * @param orderIssue
     *            - входные данные от подсистемы ТРФУ
     * @return результат регистрации
     */
    @PersistenceContext(unitName = "s11r64")
    EntityManager em = null;

    private static final Logger logger = LoggerFactory.getLogger(SendOrderBloodComponents.class);

    public IssueResult save(Integer requestId, Date factDate, List<OrderIssueInfo> components, String orderComment) {

        IssueResult res = new IssueResult();
        res.setResult(false);

        res.setRequestId(requestId);
        Action action = getAction(requestId);

        if (action == null) { // требование КК не найдено в базе данных
            res.setDescription(String.format("The issue for requestId '%s' has been not found in MIS", "" + requestId));
            res.setResult(false);
            return res;
        }

        try {
            update(action, requestId, factDate, components, orderComment);
        } catch (CoreException ex) {
            logger.error("Cannot update action {} property. Error description: '{}'", action.getId(), ex.getMessage());
            ex.printStackTrace();
            res.setDescription("MIS Internal error");
            return res;
        }
        action.setStatus(SendOrderBloodComponents.ACTION_STATE_FINISHED);
        res.setResult(true);
        return res;
    }

    /**
     * @param em
     * @param actionId
     * @param orderIssue
     * @throws CoreException
     */
    private void update(Action action, Integer requestId, Date factDate, List<OrderIssueInfo> components, String orderComment) throws CoreException {

        TrfuActionProp trfuActionProp = TrfuActionProp.getInstance(em);
        final Integer actionId = action.getId();
        final boolean update = true;
        if (factDate != null) {
            trfuActionProp.setProp(factDate, em, actionId, TrfuActionProp.PropType.ORDER_ISSUE_RES_TIME, update);
            trfuActionProp.setProp(factDate, em, actionId, TrfuActionProp.PropType.ORDER_ISSUE_RES_DATE, update);
        }
        String errMsg = "";
        for (OrderIssueInfo orderIssue : components) {
            TrfuOrderIssueResult trfuOrderIssueResult = new TrfuOrderIssueResult();
            trfuOrderIssueResult.setAction(action);
            trfuOrderIssueResult.setTrfuCompId(orderIssue.getComponentId());
            trfuOrderIssueResult.setCompNumber(orderIssue.getNumber());
            final RbBloodComponentType rbBloodComponentType = toRbBloodComponentType(orderIssue.getComponentId());
            if (rbBloodComponentType == null) {
                errMsg += "; Неизвестный компонент крови: " + orderIssue.getComponentId() + " паспорт №" + orderIssue.getNumber();
            }
            trfuOrderIssueResult.setRbBloodComponentType(rbBloodComponentType);
            final RbBloodType rbBloodType = toRbBloodType(orderIssue.getBloodGroupId(), orderIssue.getRhesusFactorId());
            if (rbBloodType == null) {
                errMsg += "; Недопустимый тип крови. группа: " + orderIssue.getBloodGroupId() + " резус: " + orderIssue.getRhesusFactorId() + " паспорт №"
                        + orderIssue.getNumber();
            }
            trfuOrderIssueResult.setRbBloodType(rbBloodType);
            trfuOrderIssueResult.setVolume(orderIssue.getVolume());
            trfuOrderIssueResult.setDoseCount(orderIssue.getDoseCount());
            trfuOrderIssueResult.setTrfuDonorId(orderIssue.getDonorId());
            em.persist(trfuOrderIssueResult);
        }
        String res = trfuActionProp.getProp(em, actionId, TrfuActionProp.PropType.ORDER_REQUEST_ID) + errMsg + "; Зарегистрирован результат от ТРФУ";
        trfuActionProp.setProp(res, em, actionId, TrfuActionProp.PropType.ORDER_REQUEST_ID, true);
        em.flush();
    }

    /**
     * @param bloodGroupId
     * @param rhesusFactorId
     * @return
     */
    private RbBloodType toRbBloodType(Integer bloodGroupId, Integer rhesusFactorId) {
        final String bloodGroups[] = { "0(I)Rh", "A(II)Rh", "B(III)Rh", "AB(IV)Rh" };
        final String rhesusFoctors[] = { "+", "-" };
        RbBloodType res = null;
        if (bloodGroupId != null && bloodGroupId > 0 && bloodGroupId <= bloodGroups.length && rhesusFactorId != null && rhesusFactorId >= 0
                && rhesusFactorId < rhesusFoctors.length) {
            final String bloobName = bloodGroups[bloodGroupId - 1] + rhesusFoctors[rhesusFactorId];

            final List<RbBloodType> rbBloodTypes = em.createQuery("SELECT b FROM RbBloodType b WHERE b.name = :bloodName", RbBloodType.class)
                    .setParameter("bloodName", bloobName).getResultList();
            res = rbBloodTypes.isEmpty() ? null : rbBloodTypes.get(0);
        }
        return res;
    }

    /**
     * @param componentId
     * @return
     */
    private RbBloodComponentType toRbBloodComponentType(Integer componentId) {
        final List<RbBloodComponentType> rbBloodComponentTypes = em
                .createQuery("SELECT c FROM RbBloodComponentType c WHERE c.trfuId = :trfuId", RbBloodComponentType.class).setParameter("trfuId", componentId)
                .getResultList();
        return rbBloodComponentTypes.isEmpty() ? null : rbBloodComponentTypes.get(0);
    }

    /**
     * @param requestId
     * @param em
     * @return
     */
    private Action getAction(Integer requestId) {
        final List<Action> actions = em.createQuery("SELECT a FROM Action a WHERE a.id = :requestId", Action.class).setParameter("requestId", requestId)
                .getResultList();
        return actions.isEmpty() ? null : actions.get(0);
    }
}
