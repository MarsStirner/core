package ru.korus.tmis.core.database.finance;

import ru.korus.tmis.core.entity.model.EventLocalContract;

import javax.ejb.Local;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        17.04.14, 17:31 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Local
public interface DbEventLocalContractLocal {

    EventLocalContract getByEventId(Integer eventId);
}
