package ru.korus.tmis.ws.finance;

import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;
import java.util.Date;
import java.util.List;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        21.04.14, 11:02 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Local
public interface PaymentBeanLocal {
    ServiceListResult getServiceList(Integer idTreatment) throws CoreException;
    public Integer setPaymentInfo(Date datePaid,
                                  String codeContract,
                                  Date dateContract,
                                  Integer idTreatment,
                                  PersonName paidName,
                                  Date birthDate,
                                  List<ServicePaidInfo> listService) throws CoreException;
}
