package ru.korus.tmis.core.database.finance;

import ru.korus.tmis.core.entity.model.PaymentLocalContract;

import javax.ejb.Local;
import java.util.List;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        23.04.14, 15:13 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Local
public interface DbPaymentLocalContractLocal {
    /**
     * Получить список платежей по договору
     * @param numberOfContract - номер договора
     * @return список платежей
     */
    List<PaymentLocalContract> getPaymentsByCodeContract(String numberOfContract);

}
