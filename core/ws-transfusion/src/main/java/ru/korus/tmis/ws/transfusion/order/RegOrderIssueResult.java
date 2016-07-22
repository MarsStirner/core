package ru.korus.tmis.ws.transfusion.order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.database.dbutil.Database;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        16.01.2013, 10:25:56 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */

/**
 * Регистрация извещения о результатах выполнения требования КК
 */
@Stateless
public class RegOrderIssueResult {

    @EJB
    private Database database;

    private static final Logger logger = LoggerFactory.getLogger(SendOrderBloodComponents.class);

    /**
     * Регистрация извещения о резульатах выполнения требования КК
     *
     * @return результат регистрации
     *
    public IssueResult save(final Integer requestId, final Date factDate, final List<OrderIssueInfo> components, final String orderComment) {

        final IssueResult res = new IssueResult();
        res.setResult(false);

        res.setRequestId(requestId);
        final Action action = database.getEntityMgr().find(Action.class, requestId);

        if (action == null) { // требование КК не найдено в базе данных
            res.setDescription(String.format("The issue for requestId '%s' has been not found in MIS", "" + requestId));
            res.setResult(false);
            return res;
        }

        try {
            if (!update(action, factDate, components, orderComment)) {
                res.setDescription("The result has been already set");
                return res;
            }
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
     * Сохранение извещения о результатах выполнения требования КК в БД
     * 
     * @param action
     *            - действие, соответствующее требованию КК
     * @param factDate
     *            - фактическая дата/время выдачи КК
     * @param components
     *            - паспортные данные выданного(-ых) компонентов крови
     * @param orderComment
     *            - комментарий
     * @return true - если данные ранее не были установлены
     *         false - если результат уже установлен
     * @throws CoreException
     *             - при кокой-либо ошибке во время работы с БД
     *
    private boolean update(final Action action, final Date factDate, final List<OrderIssueInfo> components, final String orderComment)
            throws CoreException {
        final TrfuActionProp trfuActionProp =
                new TrfuActionProp(database, SendOrderBloodComponents.TRANSFUSION_ACTION_FLAT_CODE, Arrays.asList(SendOrderBloodComponents.propConstants));
        final Integer actionId = action.getId();
        if (alreadySet(actionId, trfuActionProp)) {
            return false;
        }
        final boolean update = true;
        final EntityManager em = database.getEntityMgr();

        trfuActionProp.setProp(factDate, actionId, Constants.ORDER_ISSUE_RES_TIME, update);
        trfuActionProp.setProp(factDate, actionId, Constants.ORDER_ISSUE_RES_DATE, update);

        String errMsg = "";
        for (final OrderIssueInfo orderIssue : components) {
            final TrfuOrderIssueResult trfuOrderIssueResult = new TrfuOrderIssueResult();
            trfuOrderIssueResult.setAction(action);
            trfuOrderIssueResult.setTrfuCompId(orderIssue.getComponentId());
            trfuOrderIssueResult.setCompNumber(orderIssue.getNumber());
            final RbTrfuBloodComponentType rbBloodComponentType = toRbBloodComponentType(orderIssue.getComponentTypeId());
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
            trfuOrderIssueResult.setStickerUrl(orderIssue.getStickerUrl());
            em.persist(trfuOrderIssueResult);
        }
        final String res = trfuActionProp.getProp(actionId, Constants.ORDER_REQUEST_ID) + errMsg + "; Зарегистрирован результат от ТРФУ";
        trfuActionProp.setProp(actionId, actionId, Constants.ORDER_ISSUE_BLOOD_COMP_PASPORT, true);
        trfuActionProp.setProp(res, actionId, Constants.ORDER_REQUEST_ID, true);
        em.flush();
        return true;
    }

    public static boolean alreadySet(Integer actionId, TrfuActionProp trfuActionProp) {
        try {
            return  trfuActionProp.getProp(actionId,  Constants.ORDER_ISSUE_RES_DATE, false) != null;
        } catch (CoreException e) {
            return  false;
        }
    }

    private RbBloodType toRbBloodType(final Integer bloodGroupId, final Integer rhesusFactorId) {
        final EntityManager em = database.getEntityMgr();
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

    private RbTrfuBloodComponentType toRbBloodComponentType(final Integer componentId) {
        final EntityManager em = database.getEntityMgr();
        final List<RbTrfuBloodComponentType> rbBloodComponentTypes =
                em
                        .createQuery("SELECT c FROM RbTrfuBloodComponentType c WHERE c.trfuId = :trfuId", RbTrfuBloodComponentType.class)
                        .setParameter("trfuId", componentId)
                        .getResultList();
        return rbBloodComponentTypes.isEmpty() ? null : rbBloodComponentTypes.get(0);
    }
      */
}
