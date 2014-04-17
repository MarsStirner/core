package ru.korus.tmis.core.transmit

import ru.korus.tmis.core.entity.model.PatientsToHs
import java.sql.Timestamp
import java.util.Date
import grizzled.slf4j.Logging
import javax.persistence.{EntityManager, PersistenceContext}
import javax.xml.ws.soap.SOAPFaultException
import javax.xml.ws.WebServiceException
import scala.collection.JavaConversions._
import javax.ejb.Stateless

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
      logger.info("Sending {} info. Transmittable.getId = {}", transmittable.getClass.getCanonicalName, transmittable.getId)
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
      transmittable.setSendTime(new Timestamp(transmittable.getSendTime.getTime + (errCount).asInstanceOf[Long] * step))
      sender.sendEntity(transmittable)
      em.remove(transmittable)
    }
    catch {
      case ex: SOAPFaultException => {
        transmittable.setInfo(ex.getMessage)
        ex.printStackTrace
        em.flush
      }
      case ex: WebServiceException => {
        transmittable.setInfo(ex.getMessage)
        ex.printStackTrace
        em.flush
      }
    }
  }
}
