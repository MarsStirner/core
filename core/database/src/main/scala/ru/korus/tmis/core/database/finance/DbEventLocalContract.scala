package ru.korus.tmis.core.database.finance

import javax.ejb.{Stateless, EJB}
import grizzled.slf4j.Logging
import ru.korus.tmis.scala.util.I18nable
import javax.persistence.{EntityManager, PersistenceContext}
import ru.korus.tmis.core.entity.model.EventLocalContract

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        17.04.14, 17:39 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Stateless
class DbEventLocalContract extends DbEventLocalContractLocal
with Logging
with I18nable {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  def getByEventId(eventId: Integer): EventLocalContract = {
    val resList = em.createNamedQuery("EventLocalContract.findByEventId", classOf[EventLocalContract])
      .setParameter("eventId", eventId)
      .getResultList
    if (resList.isEmpty)
      return null
    else
      return resList.get(0)
  }
}
