package ru.korus.tmis.ws.finance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.database.dbutil.Database;
import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.entity.model.OrgStructure;
import ru.korus.tmis.core.entity.model.RbService;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.core.patient.HospitalBedBeanLocal;
import ru.korus.tmis.ws.finance.odvd.ObjectFactory;
import ru.korus.tmis.ws.finance.odvd.RowTable;
import ru.korus.tmis.ws.finance.odvd.Table;

import javax.persistence.EntityManager;
import javax.xml.datatype.DatatypeConfigurationException;
import java.math.BigInteger;
import java.util.List;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        17.04.14, 18:53 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public class OdvdBuilder {

    private static final Logger logger = LoggerFactory.getLogger(OdvdBuilder.class);

    private static ru.korus.tmis.ws.finance.odvd.ObjectFactory odvdObjectFactory = new ObjectFactory();

    public static Table toOdvdTableActions(List<Action> actionList, HospitalBedBeanLocal hospitalBedBeanLocal, EntityManager em) {
        Table res = odvdObjectFactory.createTable();
        for (Action action : actionList) {
            res.getListServiceComplete().add(toOdvdRowTable(action, hospitalBedBeanLocal, em));
        }
        return res;
    }

    private static RowTable toOdvdRowTable(Action action, HospitalBedBeanLocal hospitalBedBeanLocal, EntityManager em) {
        RowTable res = odvdObjectFactory.createRowTable();
        assert action.getActionType() != null;
        assert action.getEvent() != null;
        RbService service = getServiceByAction(action, em);
        res.setIdAction(BigInteger.valueOf(action.getId()));
        try {
            res.setEndDate(Database.toGregorianCalendar(action.getEndDate()));
        } catch (DatatypeConfigurationException e) {
            logger.error("wrong action.endDate", e);  //To change body of catch statement use File | Settings | File Templates.
        }
        res.setIsService(service == null);
        if (service == null) { // если действие не услуга
            res.setNameOfService(action.getActionType().getName());
            res.setCodeOfService(action.getActionType().getCode());
        } else { //если услуга
            res.setNameOfService(service.getName());
            res.setCodeOfService(service.getCode());
        }

        try {
            if (action.getEvent() != null) {
                final OrgStructure orgStructure = hospitalBedBeanLocal.getCurrentDepartment(action.getEvent().getId());
                if (orgStructure != null) {
                    res.setNameOfStruct(orgStructure.getName());
                    res.setCodeOfService(orgStructure.getCode());
                }
            }
        } catch (CoreException ex) {
            logger.info("Current department is not set", ex);
        }

        res.setAmount(action.getAmount());
        return res;
    }

    private static RbService getServiceByAction(Action action, EntityManager em) {
        final String query = "SELECT `rbService`.* " +
                "FROM " +
                "    `Action` INNER JOIN `Event` ON `Event`.id = `Action`.event_id " +
                "    INNER JOIN `EventType` ON `EventType`.id = `Event`.`eventType_id` " +
                "    INNER JOIN `Contract` ON `Contract`.id = `Event`.contract_id " +
                "    INNER JOIN `Contract_Tariff` ON `Contract`.id = `Contract_Tariff`.master_id " +
                "    INNER JOIN `ActionType` ON `ActionType`.id = `Action`.`actionType_id` " +
                "    INNER JOIN `ActionType_Service` ON `ActionType`.id = `ActionType_Service`.master_id " +
                "    INNER JOIN `rbService` ON `ActionType_Service`.service_id = `rbService`.id " +
                "WHERE " +
                "    `Action`.id = %s AND " +
                "    `Contract_Tariff`.`eventType_id` = `EventType`.id AND " +
                "    `Contract_Tariff`.service_id = `ActionType_Service`.service_id AND " +
                "    `Action`.deleted = 0 AND " +
                "    `Contract_Tariff`.deleted = 0 AND " +
                "    date(`Event`.`setDate`) BETWEEN `ActionType_Service`.`begDate` AND " +
                "    coalesce(`ActionType_Service`.`endDate`, curdate()) AND " +
                "    date(`Event`.`setDate`) BETWEEN `Contract_Tariff`.`begDate` AND " +
                "    `Contract_Tariff`.`endDate`";

        List<RbService> resList = em.createNativeQuery(String.format(query, action.getId(), RbService.class)).getResultList();
        if (resList.isEmpty()) {
            return null;
        } else {
            return resList.get(0);
        }


    }


}
