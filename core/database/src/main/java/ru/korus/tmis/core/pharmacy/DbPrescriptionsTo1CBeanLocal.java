package ru.korus.tmis.core.pharmacy;

import ru.korus.tmis.core.entity.model.pharmacy.PrescriptionsTo1C;

import javax.ejb.Local;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        01.10.13, 17:04 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Local
public interface DbPrescriptionsTo1CBeanLocal {

    Iterable<PrescriptionsTo1C> getPrescriptions();

    void remove(PrescriptionsTo1C prescriptionsTo1C);
}
