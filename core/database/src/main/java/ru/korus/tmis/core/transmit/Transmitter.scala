package ru.korus.tmis.core.transmit

import java.sql.Timestamp
import java.util.Date
import grizzled.slf4j.Logging
import javax.persistence.{EntityManager, PersistenceContext}
import scala.collection.JavaConversions._
import javax.ejb.Stateless
import ru.korus.tmis.core.exception.CoreException

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        16.04.14, 17:46 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Stateless
class Transmitter extends TransmitterLocal with Logging {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  def send(sender: Sender, entityClass: Class[_], namedQuery: String) {
    val listEntity = em.createNamedQuery(namedQuery, entityClass).setParameter("now", new Timestamp((new Date).getTime)).setMaxResults(TransmitterLocal.MAX_RESULT).getResultList
    for (entity <- listEntity) {
      try {
        val transmittable = entity.asInstanceOf[Transmittable];
        logger.info("Sending %s info. Transmittable.getId = %d".format(transmittable.getClass.getCanonicalName, transmittable.getId))
        sendEntity(sender, transmittable)
      }
      catch {
        case ex: Exception => {
          logger.error("Sending info. Integration internal error.", ex)
        }
      }
    }
  }

  private def sendEntity(sender: Sender, transmittable: Transmittable) {
    try {
      val errCount: Int = transmittable.getErrCount
      val step: Long = 89 * 1000
      transmittable.setErrCount(errCount + 1)
      transmittable.setSendTime(new Timestamp((new Date).getTime + (errCount).asInstanceOf[Long] * step))
      sender.sendEntity(transmittable)
      em.remove(transmittable)
      em.flush()
    }
    catch {
      case ex: CoreException => {
        val message: String = ex.getMessage.substring(0, Math.min(1024, ex.getMessage.length))
        transmittable.setInfo(message)
        logger.error(ex)
        em.flush
      }
      case ex: Exception => {
        val message: String = ex.getMessage.substring(0, Math.min(1024, ex.getMessage.length))
        transmittable.setInfo(message)
        logger.error(ex)
        em.flush
      }
    }
  }
}
