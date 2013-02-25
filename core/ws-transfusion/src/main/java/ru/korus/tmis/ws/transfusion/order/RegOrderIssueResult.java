package ru.korus.tmis.ws.transfusion.order;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.entity.model.RbBloodType;
import ru.korus.tmis.core.entity.model.RbTrfuBloodComponentType;
import ru.korus.tmis.core.entity.model.TrfuOrderIssueResult;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.ws.transfusion.Database;
import ru.korus.tmis.ws.transfusion.IssueResult;
import ru.korus.tmis.ws.transfusion.PropType;

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
    private EntityManager em = null;

    private static final Logger logger = LoggerFactory.getLogger(SendOrderBloodComponents.class);

    public IssueResult save(final Integer requestId, final Date factDate, final List<OrderIssueInfo> components, final String orderComment) {

        final IssueResult res = new IssueResult();
        res.setResult(false);

        res.setRequestId(requestId);
        final Action action = Database.getAction(em, requestId);

        if (action == null) { // требование КК не найдено в базе данных
            res.setDescription(String.format("The issue for requestId '%s' has been not found in MIS", "" + requestId));
            res.setResult(false);
            return res;
        }

        try {
            update(action, factDate, components, orderComment);
        } catch (final CoreException ex) {
            logger.error("Cannot update action {} property. Error description: '{}'", action.getId(), ex.getMessage());
            ex.printStackTrace();
            res.setDescription("MIS Internal error");
            return res;
        }
        action.setStatus(Database.ACTION_STATE_FINISHED);
        res.setResult(true);
        return res;
    }

    /**
     * @param em
     * @param actionId
     * @param orderIssue
     * @throws CoreException
     */
    private void update(final Action action, final Date factDate, final List<OrderIssueInfo> components, final String orderComment)
            throws CoreException {
        final TrfuActionProp trfuActionProp =
                new TrfuActionProp(em, SendOrderBloodComponents.TRANSFUSION_ACTION_FLAT_CODE, Arrays.asList(SendOrderBloodComponents.propConstants));
        final Integer actionId = action.getId();
        final boolean update = true;
        if (factDate != null) {
            trfuActionProp.setProp(factDate, em, actionId, PropType.ORDER_ISSUE_RES_TIME, update);
            trfuActionProp.setProp(factDate, em, actionId, PropType.ORDER_ISSUE_RES_DATE, update);
        }
        String errMsg = "";
        for (final OrderIssueInfo orderIssue : components) {
            final TrfuOrderIssueResult trfuOrderIssueResult = new TrfuOrderIssueResult();
            trfuOrderIssueResult.setAction(action);
            trfuOrderIssueResult.setTrfuCompId(orderIssue.getComponentId());
            trfuOrderIssueResult.setCompNumber(orderIssue.getNumber());
            final RbTrfuBloodComponentType rbBloodComponentType = toRbBloodComponentType(orderIssue.getComponentId());
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
        final String res = trfuActionProp.getProp(em, actionId, PropType.ORDER_REQUEST_ID) + errMsg + "; Зарегистрирован результат от ТРФУ";
        trfuActionProp.setProp(actionId, em, actionId, PropType.ORDER_ISSUE_BLOOD_COMP_PASPORT, true);
        trfuActionProp.setProp(res, em, actionId, PropType.ORDER_REQUEST_ID, true);
        em.flush();
    }

    /**
     * @param bloodGroupId
     * @param rhesusFactorId
     * @return
     */
    private RbBloodType toRbBloodType(final Integer bloodGroupId, final Integer rhesusFactorId) {
        final String[] bloodGroups = {
                "0(I)Rh", "A(II)Rh", "B(III)Rh", "AB(IV)Rh" };
        final String[] rhesusFoctors = {
                "+", "-" };
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
    private RbTrfuBloodComponentType toRbBloodComponentType(final Integer componentId) {
        final List<RbTrfuBloodComponentType> rbBloodComponentTypes =
                em
                        .createQuery("SELECT c FROM RbTrfuBloodComponentType c WHERE c.trfuId = :trfuId", RbTrfuBloodComponentType.class)
                        .setParameter("trfuId", componentId)
                        .getResultList();
        return rbBloodComponentTypes.isEmpty() ? null : rbBloodComponentTypes.get(0);
    }

}
