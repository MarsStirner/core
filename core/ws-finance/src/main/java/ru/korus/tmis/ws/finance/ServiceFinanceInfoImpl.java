package ru.korus.tmis.ws.finance;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        25.12.2012, 11:00:00 <br>
 * Company:     Korus Consulting IT<br>
 * Description: Веб-сервис экономических расчетов  <br>
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.database.common.DbActionBeanLocal;
import ru.korus.tmis.core.database.common.DbEventBeanLocal;
import ru.korus.tmis.core.database.finance.DbEventPaymentLocal;
import ru.korus.tmis.core.database.finance.PersonName;
import ru.korus.tmis.core.database.finance.ServicePaidInfo;
import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.entity.model.Event;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.EJB;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.Date;
import java.util.List;

/**
 * @see ServiceFinanceInfo
 */
@WebService(endpointInterface = "ru.korus.tmis.ws.finance.ServiceFinanceInfo", targetNamespace = "http://korus.ru/tmis/ws/finance",
        serviceName = "tmis-finance", portName = "portFinance", name = "nameFinance")
public class ServiceFinanceInfoImpl implements ServiceFinanceInfo {

    private static final Logger logger = LoggerFactory.getLogger(ServiceFinanceInfoImpl.class);

    @EJB
    DbEventBeanLocal dbEventBeanLocal;

    @EJB
    DbActionBeanLocal dbActionBeanLocal;

    @EJB
    DbEventPaymentLocal dbEventPaymentLocal;

    /**
     * @see ServiceFinanceInfo#getFinanceInfo(String)
     */
    @Override
    public FinanceBean[] getFinanceInfo(final String nameOfStructure) {

        final FinanceInfo financeInfo = new FinanceInfo();

        return financeInfo.getFinanceInfo(nameOfStructure);
    }

   @Override
    public ServiceListResult getServiceList(Integer idTreatment) throws CoreException {
       logger.info("call ServiceFinanceInfoImpl.getServiceList. idTreatment = " + idTreatment);
        Event event =  dbEventBeanLocal.getEventById(idTreatment);
        return new ServiceListResult(event, dbActionBeanLocal);
    }

    @Override
    public Integer setPaymentInfo(@WebParam(targetNamespace = "http://korus.ru/tmis/ws/finance") final Date datePaid,
                                  @WebParam(targetNamespace = "http://korus.ru/tmis/ws/finance") final String codeContract,
                                  @WebParam(targetNamespace = "http://korus.ru/tmis/ws/finance") final Integer idTreatment,
                                  @WebParam(targetNamespace = "http://korus.ru/tmis/ws/finance") final PersonName paidName,
                                  @WebParam(targetNamespace = "http://korus.ru/tmis/ws/finance") final List<ServicePaidInfo> listService) throws CoreException {
        logger.info("call ServiceFinanceInfoImpl.setPaymentInfo. idTreatment = " + idTreatment +
                " datePaid = " + datePaid +
                " codeContract = " + codeContract +
                " paidName = " + paidName +
               " size of listService = " + listService.size());
        Event event =  dbEventBeanLocal.getEventById(idTreatment);
        for(ServicePaidInfo servicePaidInfo : listService) {
            Action action = dbActionBeanLocal.getActionById(servicePaidInfo.getIdAction());
            dbEventPaymentLocal.savePaidInfo(event, datePaid, action, servicePaidInfo);
        }
        return idTreatment;
    }
}
