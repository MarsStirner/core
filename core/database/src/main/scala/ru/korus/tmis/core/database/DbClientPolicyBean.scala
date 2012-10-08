package ru.korus.tmis.core.database

import javax.interceptor.Interceptors
import ru.korus.tmis.core.logging.LoggingInterceptor
import java.lang.Iterable
import grizzled.slf4j.Logging
import javax.persistence.{EntityManager, PersistenceContext}
import javax.ejb.{EJB, Stateless}
import ru.korus.tmis.util.{ConfigManager, I18nable}
import java.util.Date
import ru.korus.tmis.core.entity.model.{Staff, Patient, ClientPolicy}
import ru.korus.tmis.core.exception.NoSuchClientPolicyException
import scala.collection.JavaConversions._

@Interceptors(Array(classOf[LoggingInterceptor]))
@Stateless
class DbClientPolicyBean
  extends DbClientPolicyBeanLocal
  with Logging
  with I18nable {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  @EJB
  var dbRbPolicyType: DbRbPolicyTypeBeanLocal = _

  @EJB
  var dbOrganisation: DbOrganizationBeanLocal = _

  val ClientPolicyFindQuery = """
    SELECT p
    FROM
      ClientPolicy p
    WHERE
      p.id = :id
                              """


  def getAllPolicies(patientId: Int): Iterable[ClientPolicy] = {
    em.createNamedQuery("ClientPolicy.findAll", classOf[ClientPolicy]).getResultList
  }

  def getClientPolicyById(id: Int): ClientPolicy = {
    var result = em.createQuery(ClientPolicyFindQuery,
      classOf[ClientPolicy])
      .setParameter("id", id)
      .getResultList

    result.size match {
      case 0 => {
        throw new NoSuchClientPolicyException(
          ConfigManager.ErrorCodes.ClientPolicyNotFound,
          id,
          i18n("error.clientPolicyNotFound"))
      }
      case size => {
        result.foreach(em.detach(_))
        result(0)
      }
    }
  }

  def insertOrUpdateClientPolicy(id: Int,
                                 rbPolicyTypeId: Int,
                                 insurerId: Int,
                                 number: String,
                                 serial: String,
                                 startDate: Date,
                                 endDate: Date,
                                 name: String,
                                 note: String,
                                 patient: Patient,
                                 sessionUser: Staff): ClientPolicy = {
    var p = new ClientPolicy
    val now = new Date
    if (id > 0) {
      p = getClientPolicyById(id)
    }
    else {
      p.setCreatePerson(sessionUser)
      p.setCreateDatetime(now)
    }

    p.setPolicyType(rbPolicyTypeId match {
      case 0 => {
        null
      }
      case _ => {
        dbRbPolicyType.getRbPolicyTypeById(rbPolicyTypeId)
      }
    })
    p.setInsurer(insurerId match {
      case 0 => {
        null
      }
      case _ => {
        dbOrganisation.getOrganizationById(insurerId)
      }
    })
    p.setNumber(number)
    p.setSerial(serial)
    p.setBegDate(startDate)
    p.setEndDate(endDate)
    p.setName(name)
    p.setNote(note)
    p.setPatient(patient)

    p.setDeleted(false)
    p.setModifyPerson(sessionUser)
    p.setModifyDatetime(now)

    p
  }

  def deleteClientPolicy(id: Int,
                         sessionUser: Staff) = {
    val p = getClientPolicyById(id)
    val now = new Date
    p.setDeleted(true)
    p.setModifyPerson(sessionUser)
    p.setModifyDatetime(now)
  }

  def checkPolicyNumber(number: String, serial: String, typeId: Int) = {
    var isNumberFree = false
    var result = em.createQuery(ClientPolicyByNumberSerialAndTypeIdQuery, classOf[ClientPolicy])
      .setParameter("number", number)
      .setParameter("serial", serial)
      .setParameter("typeId", typeId)
      .getResultList
    result.size match {
      case 0 => {}
      case size => {
        result.foreach(em.detach(_))
        result(0)
      }
    }
    if (result == null || result.size() < 1) {
      isNumberFree = true
    }
    isNumberFree
  }

  val ClientPolicyByNumberSerialAndTypeIdQuery = """
  SELECT p
  FROM
    ClientPolicy p
  WHERE
    p.number = :number
  AND
    p.serial = :serial
  AND
    p.policyType.id = :typeId
  AND
    p.deleted = 0
                                                 """
}
