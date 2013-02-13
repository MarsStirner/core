package ru.korus.tmis.core.pharmacy;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.database.DbManagerBeanLocal;
import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.entity.model.ActionType;
import ru.korus.tmis.core.entity.model.pharmacy.Pharmacy;
import ru.korus.tmis.core.entity.model.pharmacy.PharmacyStatus;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.core.logging.LoggingInterceptor;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Dmitriy E. Nosov <br>
 *         Date: 04.12.12, 18:56 <br>
 *         Company: Korus Consulting IT<br>
 *         Description: Работа с таблицей Pharmacy<br>
 */
@Interceptors(LoggingInterceptor.class)
@Stateless
public class DbPharmacyBean implements DbPharmacyBeanLocal {

    private final Logger logger = LoggerFactory.getLogger(DbPharmacyBean.class);

    @PersistenceContext(unitName = "s11r64")
    private EntityManager em = null;

    @EJB(beanName = "DbManagerBean")
    private DbManagerBeanLocal dbManager = null;

    @Override
    public Pharmacy getOrCreate(final Action action) throws CoreException {

        Pharmacy pharmacy = em.find(Pharmacy.class, action.getId());
        if (pharmacy == null) {

            final ActionType actionType = action.getActionType();
            pharmacy = new Pharmacy();
            pharmacy.setActionId(action.getId());
            pharmacy.setFlatCode(actionType.getFlatCode());
            pharmacy.setStatus(PharmacyStatus.ADDED);
            dbManager.persist(pharmacy);
            logger.info("create pharmacy {}", pharmacy);
        } else {
            logger.info("find pharmacy {}", pharmacy);
        }
        return pharmacy;
    }

    public Pharmacy updateMessage(final Pharmacy pharmacy) throws CoreException {
        final Pharmacy findPharmacy = em.find(Pharmacy.class, pharmacy.getActionId());
        if (findPharmacy != null) {
            findPharmacy.setStatus(pharmacy.getStatus());
            findPharmacy.setDocumentUUID(pharmacy.getDocumentUUID());
            findPharmacy.setResult(pharmacy.getResult());
            dbManager.merge(findPharmacy);
            return findPharmacy;
        }
        return null;
    }


//    public Action getMaxAction() {
//        final Action action = em.createQuery("SELECT a FROM Action a ORDER BY a.id DESC", Action.class).setMaxResults(1).getSingleResult();
//        logger.info("Get max action {}", action);
//        return action;
//    }

    @Override
    public List<Action> getLastMaxAction(final int limit) {
        return em.createQuery("SELECT a FROM Action a ORDER BY a.id DESC", Action.class)
                .setMaxResults(limit)
                .getResultList();
    }

    public List<Action> getVirtualActions(final int limit) {
        final List<String> flatCodeList = new ArrayList<String>(10);
        for (FlatCode fc : FlatCode.values()) {
            flatCodeList.add(fc.getCode());
        }
        return em.createQuery(
                "SELECT a FROM Action a WHERE a.actionType.flatCode IN (:flatCode) ORDER BY a.id DESC", Action.class)
                .setParameter("flatCode", flatCodeList)
                .setMaxResults(limit)
                .getResultList();
    }

    public List<Action> getVirtualActionsAfterDate(final DateTime after) {
        return em.createQuery(
                "SELECT a FROM Action a WHERE a.actionType.flatCode IN (:flatCode) AND a.modifyDatetime > :modifyDatetime", Action.class)
                .setParameter("flatCode", FlatCode.values())
                .setParameter("modifyDatetime", after.toDate())
                .getResultList();
    }

    @Override
    public List<Action> getActionAfterDate(final DateTime after) {
        return em.createQuery("SELECT a FROM Action a WHERE a.createDatetime > :createDatetime", Action.class)
                .setParameter("createDatetime", after.toDate())
                .getResultList();
    }

//    @Override
//    public List<Pharmacy> getAllPharmacy() {
//        return em.createQuery("SELECT p FROM Pharmacy p", Pharmacy.class).getResultList();
//    }

    @Override
    public Pharmacy getPharmacyByAction(final Action action) {
        List<Pharmacy> pharmacyList = em.createQuery("SELECT p FROM Pharmacy p WHERE p.actionId = :actionId", Pharmacy.class)
                .setParameter("actionId", action.getId())
                .getResultList();

        return !pharmacyList.isEmpty() ? pharmacyList.get(0) : null;
    }
}
