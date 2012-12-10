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
 * Created with IntelliJ IDEA.
 * User: nde
 * Date: 04.12.12
 * Time: 18:56
 * To change this template use File | Settings | File Templates.
 */
@Interceptors(LoggingInterceptor.class)
@Stateless
public class DbPharmacyBean implements DbPharmacyBeanLocal {

    final static Logger logger = LoggerFactory.getLogger(DbPharmacyBean.class);

    @PersistenceContext(unitName = "s11r64")
    private EntityManager em = null;

    @EJB
    private DbEventBeanLocal dbEvent = null;

    @EJB
    private DbActionTypeBeanLocal dbActionType = null;


    @EJB
    private DbActionBeanLocal dbAction = null;

    @EJB
    private DbManagerBeanLocal dbManager = null;

    @EJB
    private DbPatientBeanLocal dbPatientBeanLocal = null;

//    @Override
//    public Pharmacy createMessage(Action action, String flatCode) throws CoreException {
//
//        Pharmacy pharmacy = new Pharmacy();
//        pharmacy.setActionId(action.getId());
//        pharmacy.setFlatCode(flatCode);
//        pharmacy.setStatus("added");
//
//        dbManager.persist(pharmacy);
//
//        return pharmacy;
//    }

    @Override
    public Pharmacy getOrCreate(Action action) throws CoreException {

        Pharmacy pharmacy = em.find(Pharmacy.class, action.getId());
        if (pharmacy == null) {

            ActionType actionType = action.getActionType();
            pharmacy = new Pharmacy();
            pharmacy.setActionId(action.getId());
            pharmacy.setFlatCode(actionType.getFlatCode());
            pharmacy.setStatus("added");
            dbManager.persist(pharmacy);
            logger.info("create pharmacy {}", pharmacy);
        } else {
            logger.info("find pharmacy {}", pharmacy);
        }
        return pharmacy;
    }

    public int updateMessage(int pharmacyId, String status) throws CoreException {
        Pharmacy pharmacy = em.find(Pharmacy.class, pharmacyId);
        if (pharmacy != null) {
            pharmacy.setStatus(status);
            dbManager.merge(pharmacy);
            return pharmacy.getActionId();
        }
        return 0;
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
    public Pharmacy markComplete(Pharmacy pharmacy) throws CoreException {
        pharmacy.setStatus("complete");
        //updateMessage(pharmacy.getId(), "complete");
        //em.refresh(pharmacy);
        return pharmacy;
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
