package ru.korus.tmis.ws.finance;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        25.12.2012, 11:00:00 <br>
 * Company:     Korus Consulting IT<br>
 * Description: Веб-сервис экономических расчетов  <br>
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.EJB;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.Date;
import java.util.List;

/**
 * @see ru.korus.tmis.core.database.finance.ServicePaidFinanceInfo
 */
@WebService(endpointInterface = "ru.korus.tmis.ws.finance.ServiceFinanceInfo", targetNamespace = "http://korus.ru/tmis/ws/finance",
        serviceName = "tmis-finance", portName = "portFinance", name = "nameFinance")
public class ServiceFinanceInfoImpl implements ServiceFinanceInfo {

    private static final Logger logger = LoggerFactory.getLogger(ServiceFinanceInfoImpl.class);

    @EJB
    PaymentBeanLocal paymentBeanLocal;

    /**
     * @see ru.korus.tmis.core.database.finance.ServicePaidFinanceInfo#getFinanceInfo(String)
     */
    @Override
    public FinanceBean[] getFinanceInfo(final String nameOfStructure) {

        final FinanceInfo financeInfo = new FinanceInfo();

        return financeInfo.getFinanceInfo(nameOfStructure);
    }

    @Override
    public ServiceListResult getServiceList(@WebParam(name = "idTreatment", targetNamespace = "http://korus.ru/tmis/ws/finance") final Integer idTreatment) throws CoreException {
        logger.info("call ServiceFinanceInfoImpl.getServiceList. idTreatment = " + idTreatment);
        return paymentBeanLocal.getServiceList(idTreatment);
    }



    @Override
    public Integer setPaymentInfo(@WebParam(name = "inParam", targetNamespace = "http://korus.ru/tmis/ws/finance") PaymentPrm paymentPrm) throws CoreException {
        logger.info("call ServiceFinanceInfoImpl...");
        return setPaymentInfo(paymentPrm.getDatePaid(),
                paymentPrm.getCodeContract(),
                paymentPrm.getDateContract(),
                paymentPrm.getIdTreatment(),
                paymentPrm.getPaidName(),
                paymentPrm.getBirthDate(),
                paymentPrm.getListService());
    }


    public Integer setPaymentInfo(final Date datePaid,
                                  final String codeContract,
                                  final Date dateContract,
                                  final Integer idTreatment,
                                  final PersonName paidName,
                                  final Date birthDate,
                                  final List<ServicePaidInfo> listService) throws CoreException {
        logger.info("call ServiceFinanceInfoImpl.setPaymentInfo. idTreatment = " + idTreatment +
                " datePaid = " + datePaid +
                " codeContract = " + codeContract +
                " dateContract = " + dateContract +
                " paidName = " + paidName +
                " birthDate =" + birthDate +
                " size of listService = " + listService.size());
        if(paidName == null || paidName.getFamily() == null || paidName.getGiven() == null) {
            throw new CoreException("Фамилия и имя плательщика должны быть заданы");
        }
        return paymentBeanLocal.setPaymentInfo(datePaid, codeContract, dateContract, idTreatment, paidName, birthDate, listService);
    }

}
