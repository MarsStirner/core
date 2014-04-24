package ru.korus.tmis.core.database.finance

import grizzled.slf4j.Logging
import ru.korus.tmis.scala.util.I18nable
import javax.ejb.Stateless
import java.util
import ru.korus.tmis.core.entity.model.{PaymentLocalContract, EventPayment}
import javax.persistence.{EntityManager, PersistenceContext}

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        23.04.14, 15:26 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Stateless
class DbPaymentLocalContract extends DbPaymentLocalContractLocal
with Logging
with I18nable {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  def getPaymentsByCodeContract(numberOfContract: String): util.List[PaymentLocalContract] = {
    em.createNamedQuery("PaymentLocalContract.findByCodeContract", classOf[PaymentLocalContract])
      .setParameter("code", numberOfContract)
      .getResultList
  }
}
