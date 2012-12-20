package ru.korus.tmis.core.hl7db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.database.*;
import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.entity.model.ActionType;
import ru.korus.tmis.core.entity.model.Patient;
import ru.korus.tmis.core.entity.model.hl7.Pharmacy;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.core.logging.LoggingInterceptor;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

/**
 * @Author:      Dmitriy E. Nosov <br>
 * @Date:        04.12.12, 18:56 <br>
 * @Company:     Korus Consulting IT<br>
 * Revision:    \$Id$ <br>
 * @Description: <br>
 */
@Interceptors(LoggingInterceptor.class)
@Stateless
public class DbPharmacyBean implements DbPharmacyBeanLocal {

    final static Logger logger = LoggerFactory.getLogger(DbPharmacyBean.class);

    @PersistenceContext(unitName = "s11r64")
    private EntityManager em = null;

    @EJB(beanName = "DbManagerBean")
    private DbManagerBeanLocal dbManager = null;

    @EJB(beanName = "DbPatientBean")
    private DbPatientBeanLocal dbPatientBeanLocal = null;


    @Override
    public Pharmacy getOrCreate(Action action) throws CoreException {

        Pharmacy pharmacy = em.find(Pharmacy.class, action.getId());
        if (pharmacy == null) {

            ActionType actionType = action.getActionType();
            pharmacy = new Pharmacy();
            pharmacy.setActionId(action.getId());
            pharmacy.setFlatCode(actionType.getFlatCode());
            pharmacy.setStatus(PharmacyStatus.ADDED.toString());
            dbManager.persist(pharmacy);
            logger.info("create pharmacy {}", pharmacy);
        } else {
            logger.info("find pharmacy {}", pharmacy);
        }
        return pharmacy;
    }

    public Pharmacy updateMessage(Pharmacy pharmacy) throws CoreException {
        Pharmacy findPharmacy = em.find(Pharmacy.class, pharmacy.getActionId());
        if (findPharmacy != null) {
            findPharmacy.setStatus(pharmacy.getStatus());
            findPharmacy.setDocumentUUID(pharmacy.getDocumentUUID());
            findPharmacy.setResult(pharmacy.getResult());
            dbManager.merge(findPharmacy);
            return findPharmacy;
        }
        return null;
    }


    public Action getMaxAction() {
        final Action action = em.createQuery("SELECT a FROM Action a ORDER BY a.id DESC", Action.class).setMaxResults(1).getSingleResult();
        logger.info("Get max action {}", action);
        return action;
    }

    @Override
    public List<Action> getLastMaxAction(int limit) {
        return em.createQuery("SELECT a FROM Action a ORDER BY a.id DESC", Action.class)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public List<Action> getActionAfterDate(Date after) {
        return em.createQuery("SELECT a FROM Action a WHERE a.createDatetime > :createDatetime", Action.class)
                .setParameter("createDatetime", after)
                .getResultList();
    }

    @Override
    public List<Pharmacy> getAllPharmacy() {
        return em.createQuery("SELECT p FROM Pharmacy p", Pharmacy.class).getResultList();

    }

    @Override
    public Pharmacy getPharmacyByAction(Action action) {
        List<Pharmacy> pharmacyList = em.createQuery("SELECT p FROM Pharmacy p WHERE p.actionId = :actionId", Pharmacy.class)
                .setParameter("actionId", action.getId())
                .getResultList();

        return !pharmacyList.isEmpty() ? pharmacyList.get(0) : null;
    }

    public Patient getClient(Patient patient) throws CoreException {
        return dbPatientBeanLocal.getPatientById(patient.getId());
    }

}
