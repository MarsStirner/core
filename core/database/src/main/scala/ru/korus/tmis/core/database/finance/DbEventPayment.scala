package ru.korus.tmis.core.database.finance

import grizzled.slf4j.Logging
import ru.korus.tmis.scala.util.I18nable
import javax.persistence.{EntityManager, PersistenceContext}
import ru.korus.tmis.core.entity.model._
import java.util.Date
import javax.ejb.Stateless

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        15.04.14, 15:57 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Stateless
class DbEventPayment extends DbEventPaymentLocal
with Logging
with I18nable {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  def savePaidInfo(event: Event, date: Date, eventLocalContract: EventLocalContract, paidName: PersonFIO, action: Action, servicePaidFinanceInfo: ServicePaidFinanceInfo) {
    val eventPayment: EventPayment = new EventPayment
    val now = new Date
    eventPayment.setCreateDatetime(now)
    eventPayment.setModifyDatetime(now)
    eventPayment.setDeleted(false)
    eventPayment.setEvent(event)
    eventPayment.setDate(date) //Дата платежа
    eventPayment.setSum(servicePaidFinanceInfo.getSum)
    eventPayment.setSumDisc(servicePaidFinanceInfo.getSumDisc)
    eventPayment.setCashBox("")
    eventPayment.setAction(action)
    val servList = em.createNamedQuery("rbService.findByCode", classOf[RbService]).setParameter("code", servicePaidFinanceInfo.getCodeService).getResultList
    eventPayment.setService( if (servList.isEmpty) { null } else {servList.get(0)} )
    eventPayment.setEventLocalContract(eventLocalContract)
    em.persist(eventPayment)
  }
}
