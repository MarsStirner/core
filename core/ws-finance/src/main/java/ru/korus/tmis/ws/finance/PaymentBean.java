package ru.korus.tmis.ws.finance;

import ru.korus.tmis.core.database.common.DbActionBeanLocal;
import ru.korus.tmis.core.database.common.DbEventBeanLocal;
import ru.korus.tmis.core.database.finance.DbEventPaymentLocal;
import ru.korus.tmis.core.database.finance.PersonName;
import ru.korus.tmis.core.database.finance.ServicePaidInfo;
import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.entity.model.Event;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        21.04.14, 11:03 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Stateless
public class PaymentBean implements PaymentBeanLocal {

    @PersistenceContext(unitName = "s11r64")
    private EntityManager em;

    @EJB
    DbEventBeanLocal dbEventBeanLocal;

    @EJB
    DbActionBeanLocal dbActionBeanLocal;

    @EJB
    DbEventPaymentLocal dbEventPaymentLocal;

    @Override
    public ServiceListResult getServiceList(Integer idTreatment) throws CoreException {
        Event event =  dbEventBeanLocal.getEventById(idTreatment);
        return new ServiceListResult(event, dbActionBeanLocal);
    }

    @Override
    public Integer setPaymentInfo(Date datePaid, String codeContract, Integer idTreatment, PersonName paidName, List<ServicePaidInfo> listService) throws CoreException {
        Event event =  dbEventBeanLocal.getEventById(idTreatment);
        for(ServicePaidInfo servicePaidInfo : listService) {
            Action action = dbActionBeanLocal.getActionById(servicePaidInfo.getIdAction());
            dbEventPaymentLocal.savePaidInfo(event, datePaid, action, servicePaidInfo);
        }
        em.flush();
        return idTreatment;
    }

}
