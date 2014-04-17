package ru.korus.tmis.ws.finance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.database.dbutil.Database;
import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.entity.model.OrgStructure;
import ru.korus.tmis.core.entity.model.RbService;
import ru.korus.tmis.core.entity.model.Staff;
import ru.korus.tmis.ws.finance.odvd.ObjectFactory;
import ru.korus.tmis.ws.finance.odvd.RowTable;
import ru.korus.tmis.ws.finance.odvd.Table;

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

    public static Table toOdvdTableActions(List<Action> actionList) {
        Table res = odvdObjectFactory.createTable();
        for (Action action : actionList) {
           res.getListServiceComplete().add(toOdvdRowTable(action));
        }
        return res;
    }

    private static RowTable toOdvdRowTable(Action action) {
        RowTable res = odvdObjectFactory.createRowTable();
        assert action.getActionType() != null;
        assert action.getEvent() != null;
        RbService service = action.getActionType().getService();
        res.setIdService(BigInteger.valueOf(action.getId()));
        try {
            res.setEndDate(Database.toGregorianCalendar(action.getEndDate()));
        } catch (DatatypeConfigurationException e) {
            logger.error("wrong action.endDate", e);  //To change body of catch statement use File | Settings | File Templates.
        }
        res.setIsService(service == null);
        if (service == null) { // если действие не услуга
            res.setNameOfService(action.getActionType().getName());
        } else { //если услуга
            res.setNameOfService(service.getName());
            res.setCodeOfService(service.getCode());
        }
        //TODO Уточнить у Алены Карасевой
        final Staff executor = action.getEvent().getExecutor();
        final OrgStructure orgStructure = executor == null ? null : executor.getOrgStructure();
        res.setNameOfStruct(orgStructure.getName());
        res.setCodeOfService(orgStructure.getCode());

        //TODO amount должен быть не integer, а double  ???
       // res.setAmount(action.getAmount());
        return res;
    }


}
