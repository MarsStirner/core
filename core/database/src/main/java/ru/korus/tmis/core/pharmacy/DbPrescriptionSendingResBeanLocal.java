package ru.korus.tmis.core.pharmacy;

import ru.korus.tmis.core.entity.model.DrugChart;
import ru.korus.tmis.core.entity.model.RlsNomen;
import ru.korus.tmis.core.entity.model.pharmacy.PrescriptionSendingRes;

import javax.ejb.Local;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        11.10.13, 14:30 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Local
public interface DbPrescriptionSendingResBeanLocal {
    PrescriptionSendingRes getPrescriptionSendingRes(DrugChart drugChart, RlsNomen rlsNomen);
}
