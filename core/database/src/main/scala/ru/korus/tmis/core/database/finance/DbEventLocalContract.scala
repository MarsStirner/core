package ru.korus.tmis.core.database.finance

import javax.ejb.{EJB, Stateless}
import grizzled.slf4j.Logging
import ru.korus.tmis.scala.util.I18nable
import javax.persistence.{EntityManager, PersistenceContext}
import ru.korus.tmis.core.entity.model.{Event, EventLocalContract}
import java.util.Date
import ru.korus.tmis.core.database.DbStaffBeanLocal

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

  @EJB
  var dbStaffBeanLocal: DbStaffBeanLocal = _

  def getByContractNumber(numberOfContract: String): EventLocalContract = {
    val resList = em.createNamedQuery("EventLocalContract.findByContractCode", classOf[EventLocalContract])
      .setParameter("code", numberOfContract)
      .getResultList
    if (resList.isEmpty)
      return null
    else
      return resList.get(0)

  }

  def create(code: String, dateContract: Date, event: Event, paidName: PersonFIO, birthDate: Date): EventLocalContract = {
    val res = new EventLocalContract
    val now = new Date
    val coreStaff = dbStaffBeanLocal.getCoreUser
    res.setBirthDate(birthDate)
    res.setCoordAgent("")
    res.setCoordDate(null)
    res.setCoordInspector("")
    res.setCoordText("")
    res.setCreateDatetime(now)
    res.setCreatePerson(coreStaff)
    res.setDateContract(dateContract)
    res.setDeleted(false)
    if (event != null) {
      event.setEventLocalContract(res);
    }
    res.setFirstName(paidName.getGiven)
    res.setLastName(paidName.getFamily)
    res.setPatrName(paidName.getPartName)
    res.setModifyDatetime(now)
    res.setModifyPerson(coreStaff)
    res.setNumber("")
    res.setNumberContract(code)
    res.setOrganisation(null)
    res.setRbDocumentType(null)
    res.setRegAddress("")
    res.setSerialLeft("")
    res.setSerialRight("")
    res.setSumLimit(0)
    em.persist(res)
    res
  }
}
