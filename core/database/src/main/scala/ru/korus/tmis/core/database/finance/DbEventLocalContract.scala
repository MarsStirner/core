package ru.korus.tmis.core.database.finance

import javax.ejb.{EJB, Stateless}
import grizzled.slf4j.Logging
import ru.korus.tmis.core.data.{PaymentContractInfo, PayerInfo}
import ru.korus.tmis.core.database.common.DbOrganizationBeanLocal
import ru.korus.tmis.core.exception.CoreException
import ru.korus.tmis.scala.util.I18nable
import javax.persistence.{EntityManager, PersistenceContext}
import ru.korus.tmis.core.entity.model.{EventLocalContract, Event}
import java.util.Date
import ru.korus.tmis.core.database.{DbRbDocumentTypeBeanLocal, DbStaffBeanLocal}

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

  @EJB
  var dbRbDocumentTypeBeanLocal: DbRbDocumentTypeBeanLocal = _

  @EJB
  var dbOrganizationBeanLocal: DbOrganizationBeanLocal = _


 /* def getByEventId(eventId: Integer): EventLocalContract = {
    val resList = em.createNamedQuery("EventLocalContract.findByEventId", classOf[EventLocalContract])
      .setParameter("eventId", eventId)
      .getResultList
    if (resList.isEmpty)
      return null
    else
      return resList.get(0)
  }*/

  def getByContractNumber(numberOfContract: String): EventLocalContract = {
    val resList = em.createNamedQuery("EventLocalContract.findByContractCode", classOf[EventLocalContract])
      .setParameter("code", numberOfContract)
      .getResultList
    if (resList.isEmpty)
      return null
    else
      return resList.get(0)

  }

  def initDefault(contract: EventLocalContract): EventLocalContract = {
    val now = new Date
    contract.setCoordAgent("")
    contract.setCoordDate(null)
    contract.setCoordInspector("")
    contract.setCoordText("")
    contract.setCreateDatetime(now)
    contract.setDeleted(false)
    contract.setModifyDatetime(now)
    contract.setNumber("")
    contract.setRegAddress("")
    contract.setSerialLeft("")
    contract.setSerialRight("")
    contract.setSumLimit(0)
    contract.setOrganisation(null)
    contract.setRbDocumentType(null)
    contract
  }

  def create(code: String, dateContract: Date, event: Event, paidName: PersonFIO, birthDate: Date): EventLocalContract = {
    val res = initDefault(new EventLocalContract)
    val coreStaff = dbStaffBeanLocal.getCoreUser
    res.setBirthDate(birthDate)
    res.setCreatePerson(coreStaff)
    res.setDateContract(dateContract)
    res.setEvent(event)
    res.setFirstName(paidName.getGiven)
    res.setLastName(paidName.getFamily)
    res.setPatrName(paidName.getPartName)
    res.setModifyPerson(coreStaff)
    res.setNumberContract(code)
    em.persist(res)
    res
  }

  def insertOrUpdate(event: Event, payerInfo: PayerInfo, paymentContract: PaymentContractInfo): EventLocalContract = {

    if(paymentContract.getDate == null) {
      return null
    }
    var isNew = false
    var eventLocalContract: EventLocalContract =  event.getEventLocalContract
    if(eventLocalContract == null) {
      eventLocalContract = initDefault(new EventLocalContract())
      isNew = true

    }

    eventLocalContract.setFirstName(payerInfo.getFirstName)
    eventLocalContract.setLastName(payerInfo.getLastName)
    eventLocalContract.setPatrName(payerInfo.getMiddleName)
    eventLocalContract.setBirthDate(payerInfo.getBirthDate)
    try {
      val rbDocumentType = if (payerInfo.getDocumentType == null) null else dbRbDocumentTypeBeanLocal.getRbDocumentTypeById(payerInfo.getDocumentType)
      eventLocalContract.setRbDocumentType(rbDocumentType)
    } catch {
      case ex: CoreException =>
    }
    eventLocalContract.setSerialLeft(payerInfo.getDocumentSeriesLeft)
    eventLocalContract.setSerialRight(payerInfo.getDocumentSeriesRight)
    eventLocalContract.setNumber(payerInfo.getDocumentNumber)
    eventLocalContract.setRegAddress(payerInfo.getAddress)
    try {
      eventLocalContract.setOrganisation(
        if (payerInfo.getCompany == null) null else dbOrganizationBeanLocal.getOrganizationById(payerInfo.getCompany))
    } catch {
      case ex: CoreException =>
    }
    eventLocalContract.setNumberContract(paymentContract.getNumber)
    eventLocalContract.setDateContract(paymentContract.getDate)
    if (isNew) em.persist(eventLocalContract) else em.merge(eventLocalContract)
    eventLocalContract
  }


}
