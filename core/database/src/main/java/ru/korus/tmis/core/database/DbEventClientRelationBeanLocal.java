package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.ClientRelation;
import ru.korus.tmis.core.entity.model.Event;
import ru.korus.tmis.core.entity.model.EventClientRelation;

import javax.ejb.Local;
import java.util.List;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        02.03.2015, 16:09 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Local
public interface DbEventClientRelationBeanLocal {

    EventClientRelation insertOrUpdate(Event event, ClientRelation clientRelation);

    List<EventClientRelation> getByEvent(Event event);

}
