package ru.korus.tmis.ws.transfusion;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.ws.WebServiceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.korus.tmis.core.entity.model.APValue;
import ru.korus.tmis.core.entity.model.AbstractAPValue;
import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.entity.model.ActionProperty;
import ru.korus.tmis.core.entity.model.ActionPropertyType;
import ru.korus.tmis.core.entity.model.IndexedId;
import ru.korus.tmis.core.entity.model.OrgStructure;
import ru.korus.tmis.core.entity.model.RbUnit;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.util.EntityMgr;
import ru.korus.tmis.ws.transfusion.efive.TransfusionMedicalService;
import ru.korus.tmis.ws.transfusion.efive.TransfusionMedicalService_Service;
import ru.korus.tmis.ws.transfusion.order.SendOrderBloodComponents;

/**
 * Author: Sergey A. Zagrebelny <br>
 * Date: 23.01.2013, 10:41:20 <br>
 * Company: Korus Consulting IT<br>
 * Description: <br>
 */

@Singleton
public class Database {
    private static final Logger logger = LoggerFactory.getLogger(SendOrderBloodComponents.class);
    

    @Schedule(hour = "*", minute = "*")
    public void pullDB() {
        try {
            final TransfusionMedicalService_Service service = new TransfusionMedicalService_Service();
            final TransfusionMedicalService transfusionMedicalService = service.getTransfusionMedicalService();
            SendOrderBloodComponents order = new SendOrderBloodComponents();
            order.pullDB(transfusionMedicalService);
        } catch (WebServiceException ex) {
            logger.error("The TRFU service is not available. Exception description: {}", ex.getMessage());
            ex.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T getSingleProp(@SuppressWarnings("rawtypes") Class classType, final EntityManager em, final int actionId, final int propTypeId) throws CoreException {

        final List<ActionProperty> prop = getActionProp(em, actionId, propTypeId);

        checkCountProp(actionId, propTypeId, prop.size());

        final String className = classType.getName();
        List<Object> propRes = em.createQuery("SELECT p FROM " + className.substring(className.lastIndexOf('.') + 1) + " p WHERE p.id = :id", classType)
                .setParameter("id", new IndexedId(prop.get(0).getId(), 0)).getResultList();

        checkCountProp(actionId, propTypeId, propRes.size());

        return (T) ((APValue) propRes.get(0)).getValue();
    }

    public static <T> T getSingleProp(@SuppressWarnings("rawtypes") Class classType,
            final EntityManager em,
            final int actionId,
            final int propTypeId,
            T defaultVal) {
        try {
            return getSingleProp(classType, em, actionId, propTypeId);
        } catch (CoreException e) {
            return defaultVal;
        }
    }

    /**
     * @param em
     * @param actionId
     * @param propTypeId
     * @return
     */
    private static List<ActionProperty> getActionProp(EntityManager em, int actionId, int propTypeId) {
        List<ActionProperty> prop = em
                .createQuery("SELECT p FROM ActionProperty p WHERE p.action.id = :curAction_id AND p.deleted = 0 AND p.actionPropertyType.id = :propTypeId",
                        ActionProperty.class).setParameter("curAction_id", actionId).setParameter("propTypeId", propTypeId).getResultList();
        return prop;
    }

    /**
     * @param actionId
     * @param propTypeId
     * @param size
     * @throws CoreException
     */
    private static void checkCountProp(int actionId, int propTypeId, int size) throws CoreException {
        if (size == 0) {
            throw new CoreException(String.format("The property %d for action %d has been not found", propTypeId, actionId));
        } else if (size > 1) {
            throw new CoreException(String.format("More that one property %d for action %d has been found", propTypeId, actionId));
        }
    }

    public static <T> int addSinglePropBasic(T value, @SuppressWarnings("rawtypes") Class classType, EntityManager em, int actionId, int propTypeId, boolean update) throws CoreException {
        Integer newPropId = null;
        final List<ActionProperty> prop = getActionProp(em, actionId, propTypeId);
        if (prop.size() > 0) {
            if (update) {
                newPropId = prop.get(0).getId();
            } else {
                new CoreException(String.format("The property %i for action %i has been alredy set", propTypeId, actionId)); // свойство уже установленно
            }
        } else {
            newPropId = addActionProp(em, actionId, propTypeId);
        }

        final IndexedId actionPropId = new IndexedId();
        actionPropId.setId(newPropId);
        actionPropId.setIndex(0);
        AbstractAPValue actionProp = null;
        
        final String msg = String.format("Internal error: The type %s is supproted by Database.addSinglePropBasic", value.getClass().getName());
        try {
            actionProp = setPropValue(em, newPropId, (AbstractAPValue) classType.newInstance());
        } catch (InstantiationException e) {
            logger.error(msg);
            e.printStackTrace();
            throw new Error(msg);
        } catch (IllegalAccessException e) {
            logger.error(msg);
            e.printStackTrace();
            e.printStackTrace();
        }

        actionProp.setValueFromString(value.toString());
        actionProp.setId(actionPropId);
        em.persist(actionProp);
        return newPropId;

    }

    /**
     * @param em
     * @param newPropId
     * @return
     */
    private static <T> T setPropValue(EntityManager em, Integer newPropId, final T propInteger) {
        String template = "SELECT p FROM %s p WHERE p.id.id = :curProp_id";
        final String className = propInteger.getClass().getName();
        @SuppressWarnings("unchecked")
        List<T> curProp = (List<T>) em.createQuery(String.format(template, className.substring(className.lastIndexOf('.') + 1)), propInteger.getClass())
                .setParameter("curProp_id", newPropId).getResultList();
        return curProp.size() > 0 ? curProp.get(0) : propInteger;
    }

    /**
     * @param em
     * @param actionId
     * @param propTypeId
     * @return
     * @throws CoreException
     */
    private static Integer addActionProp(EntityManager em, int actionId, int propTypeId) throws CoreException {
        Action action = EntityMgr.getSafe(em.find(Action.class, new Integer(actionId)));
        ActionPropertyType actionPropType = EntityMgr.getSafe(em.find(ActionPropertyType.class, new Integer(propTypeId)));
        final ActionProperty actionProp = new ActionProperty();
        final Date now = new Date();
        actionProp.setCreateDatetime(now);
        actionProp.setCreatePerson(action.getCreatePerson());
        actionProp.setModifyDatetime(now);
        actionProp.setModifyPerson(action.getCreatePerson());
        actionProp.setType(actionPropType);
        final RbUnit unit = actionPropType.getUnit();
        actionProp.setUnit(unit == RbUnit.getEmptyUnit() ? null : unit);
        actionProp.setNorm(actionPropType.getName());
        actionProp.setNorm(null);
        actionProp.setVersion(0);
        actionProp.setAction(action);
        em.persist(actionProp);
        em.flush();
        logger.info("The new property {} for action {} has been added. Property id: {}", propTypeId, actionId, actionProp.getId());
        return actionProp.getId();
    }

    /**
     * @return
     */
    public static List<DivisionInfo> getDivisions() {
        List<DivisionInfo> res = new LinkedList<DivisionInfo>();
        try {
            EntityManager em = EntityMgr.getEntityManagerForS11r64(logger);
            List<OrgStructure> structs = em.createQuery("SELECT s FROM OrgStructure s WHERE s.deleted = 0", OrgStructure.class).getResultList();
            for (OrgStructure struct : structs) {
                DivisionInfo info = new DivisionInfo();
                info.setId(struct.getId());
                info.setName(struct.getName());
                res.add(info);
            }
        } catch (CoreException ex) {
            logger.error("Cannot create entety manager. Error description: '{}'", ex.getMessage());
        }

        return res;
    }

}
