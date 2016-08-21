package ru.korus.tmis.core.pharmacy;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.database.common.DbActionBeanLocal;
import ru.korus.tmis.core.database.common.DbActionPropertyBeanLocal;
import ru.korus.tmis.core.database.common.DbActionPropertyTypeBeanLocal;
import ru.korus.tmis.core.database.common.DbManagerBeanLocal;
import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.entity.model.ActionType;
import ru.korus.tmis.core.entity.model.Event;
import ru.korus.tmis.core.entity.model.pharmacy.DrugComponent;
import ru.korus.tmis.core.entity.model.pharmacy.Pharmacy;
import ru.korus.tmis.core.entity.model.pharmacy.PharmacyStatus;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Author   Dmitriy E. Nosov <br>
 * Date:    04.12.12, 18:56 <br>
 * Company: Korus Consulting IT<br>
 * Description: Работа с таблицей Pharmacy<br>
 */
//@Interceptors(LoggingInterceptor.class)
@Stateless
public class DbPharmacyBean implements DbPharmacyBeanLocal {

    private static final Logger logger = LoggerFactory.getLogger(DbPharmacyBean.class);

    @PersistenceContext(unitName = "s11r64")
    private EntityManager em = null;

    @EJB(beanName = "DbManagerBean")
    private DbManagerBeanLocal dbManager = null;

    @EJB(beanName = "DbActionPropertyBean")
    private DbActionPropertyBeanLocal dbActionPropertyBeanLocal = null;

    @EJB(beanName = "DbActionPropertyTypeBean")
    private DbActionPropertyTypeBeanLocal dbActionPropertyTypeBeanLocal = null;

    @EJB
    private DbActionBeanLocal dbActionBeanLocal;

    /**
     * Полоучение списка сообщений, которые по разным причинам не были отправлены в 1С Аптеку (статус != COMPLETE)
     *
     * @return
     */
    @Override
    public List<Pharmacy> getNonCompletedItems() {

        final List<Pharmacy> nonCompleteList = em.createQuery("SELECT p FROM Pharmacy p WHERE p.status <> :status ORDER BY p.actionId ASC", Pharmacy.class)
                .setParameter("status", PharmacyStatus.COMPLETE)
                .getResultList();

        return nonCompleteList;
    }

    public List<DrugComponent> getDrugComponent(final Action action) {
        List<DrugComponent> comps = em.createNamedQuery("DrugComponent.getByActionId", DrugComponent.class).setParameter("actionId", action.getId()).getResultList();

        return comps;
    }


    private Integer getResult(final List input) {
        if (!input.isEmpty()) {
            for (Object o : input) {
                if (o instanceof Integer) {
                    return (Integer) o;
                }
            }
        }
        return 0;
    }

    /**
     * @see
     */
    @Override
    public Pharmacy getOrCreate(final Action action) throws CoreException {
        Pharmacy pharmacy = em.find(Pharmacy.class, action.getId());
        if (pharmacy == null) {
            final ActionType actionType = action.getActionType();
            pharmacy = new Pharmacy();
            pharmacy.setActionId(action.getId());
            pharmacy.setFlatCode(actionType.getFlatCode());
            pharmacy.setStatus(PharmacyStatus.ADDED);
            em.persist(pharmacy);
            logger.info("create pharmacy {}", pharmacy);
        } else {
            logger.info("find pharmacy {}", pharmacy);
        }
        return pharmacy;
    }

    /**
     * @see
     */
    @Override
    public Pharmacy updateMessage(final Pharmacy pharmacy) throws CoreException {
        final Pharmacy findPharmacy = em.find(Pharmacy.class, pharmacy.getActionId());
        if (findPharmacy != null) {
            findPharmacy.setStatus(pharmacy.getStatus());
            findPharmacy.setUuid(pharmacy.getUuid());
            findPharmacy.setResult(pharmacy.getResult());
            em.merge(findPharmacy);
            return findPharmacy;
        }
        return null;
    }

    /**
     * @see
     */
    @Override
    public List<Action> getLastMaxAction(final int limit) {
        return em.createQuery("SELECT a FROM Action a ORDER BY a.id DESC", Action.class)
                .setMaxResults(limit)
                .getResultList();
    }

    /**
     * @see
     */
    @Override
    public List<Action> getVirtualActions(final int limit) {
        return em.createQuery(
                "SELECT a FROM Action a WHERE a.actionType.flatCode IN :flatCode ORDER BY a.id DESC", Action.class)
                .setParameter("flatCode", getFlatCodeStrings())
                .setMaxResults(limit)
                .getResultList();
    }

    /**
     * Получение значений Flat Code в виде списка строк
     *
     * @return
     */
    private List<String> getFlatCodeStrings() {
        final List<String> flatCodeList = new ArrayList<String>(10);
        for (FlatCode fc : FlatCode.values()) {
            if (!FlatCode.PRESCRIPTION.equals(fc)) {
                flatCodeList.add(fc.getCode());
            }
        }
        return flatCodeList;
    }

    /**
     * @see
     */
    @Override
    public List<Action> getVirtualActionsAfterDate(final DateTime after) {
        return em.createQuery(
                "SELECT a FROM Action a WHERE a.actionType.flatCode IN :flatCode AND a.createDatetime > :modifyDatetime ORDER BY a.createDatetime ASC", Action.class)
                .setParameter("flatCode", getFlatCodeStrings())
                .setParameter("modifyDatetime", after.toDate())
                .getResultList();
    }

    @Override
    public List<Action> getAssignmentForToday(final DateTime dateTime) {

        return em.createQuery(
                "SELECT a FROM Action a WHERE a.actionType.flatCode IN :flatCode AND a.modifyDatetime BETWEEN :modifyDateStart AND :modifyDateStop",
                Action.class)
                .setParameter("flatCode", Arrays.asList(FlatCode.PRESCRIPTION.getCode()))
                .setParameter("modifyDateStart", dateTime.withTimeAtStartOfDay().toDate())
                .setParameter("modifyDateStop", dateTime.plusDays(1).withTimeAtStartOfDay().toDate())
                .getResultList();
    }

    @Override
    public List<Action> getPrescriptionForEvent(Event event) {
        return dbActionBeanLocal.getActionsByTypeFlatCodeAndEventId(event.getId(), FlatCode.getPrescriptionCodeList());
    }

    @Override
    public List<Action> getPrescriptionForTimeInterval(Date begDate, Date endDate) {
        // обозначения: < > -  границы интервала назначений;  { } - границы запрашиваемого интервала
        return em.createQuery("SELECT DISTINCT dc.action FROM DrugChart dc WHERE " +
                "(dc.begDateTime <= :begDate AND :begDate < dc.endDateTime) " + // < { > - если начнло запрашиваемого интервала в инервале назначений
                "OR (dc.begDateTime <= :endDate AND :endDate < dc.endDateTime) " +   // < } > - если конец запрашиваемого интервала в инервале назначений
                "OR (:begDate <= dc.begDateTime AND (dc.endDateTime IS NULL OR dc.endDateTime < :endDate)) " + // { < > } - если интервал назначений в запрашиваемом интервале
                "OR (dc.begDateTime <= :begDate AND :endDate < dc.endDateTime)", Action.class) // < {} > - если запрашиваемый интервал в интервале назначений
                .setParameter("begDate", begDate)
                .setParameter("endDate", endDate)
                .getResultList();
    }

    /**
     * @see
     */
    @Override
    public List<Action> getActionAfterDate(final DateTime after) {
        return em.createQuery("SELECT a FROM Action a WHERE a.createDatetime > :createDatetime", Action.class)
                .setParameter("createDatetime", after.toDate())
                .getResultList();
    }

    /**
     * @see
     */
    @Override
    public Pharmacy getPharmacyByAction(final Action action) {
        List<Pharmacy> pharmacyList = em.createQuery("SELECT p FROM Pharmacy p WHERE p.actionId = :actionId", Pharmacy.class)
                .setParameter("actionId", action.getId())
                .getResultList();

        return !pharmacyList.isEmpty() ? pharmacyList.get(0) : null;
    }


}
