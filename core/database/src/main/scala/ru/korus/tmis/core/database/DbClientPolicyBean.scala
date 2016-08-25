package ru.korus.tmis.core.database

import common.DbOrganizationBeanLocal
import java.lang.Iterable
import javax.persistence.{EntityManager, PersistenceContext}
import javax.ejb.{EJB, Stateless}
import java.util.Date
import ru.korus.tmis.core.entity.model.{RbPolicyType, Staff, Patient, ClientPolicy}
import ru.korus.tmis.core.exception.NoSuchClientPolicyException
import scala.collection.JavaConversions._
import java.util
import ru.korus.tmis.scala.util.{I18nable, ConfigManager}
import scala.language.reflectiveCalls

@Stateless
class DbClientPolicyBean
  extends DbClientPolicyBeanLocal
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

  val findBySerialAndNumberQuery = """
    SELECT p
    FROM
      ClientPolicy p
    WHERE
      p.serial = :serial AND
      p.number = :number AND
      p.policyType.id = :typeId
                                   """


  def getAllPolicies(patientId: Int): Iterable[ClientPolicy] = {
    em.createNamedQuery("ClientPolicy.findAll", classOf[ClientPolicy]).getResultList
  }

  def getClientPolicyById(id: Int): ClientPolicy = {
    val result = em.createQuery(ClientPolicyFindQuery,
      classOf[ClientPolicy])
      .setParameter("id", id)
      .getResultList

    result.size match {
      case 0 => throw new NoSuchClientPolicyException(ConfigManager.ErrorCodes.ClientPolicyNotFound, id, i18n("error.clientPolicyNotFound"))
      case _ => result.iterator().next()
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
      case 0 => null
      case _ => dbRbPolicyType.getRbPolicyTypeById(rbPolicyTypeId)
    })
    p.setInsurer(insurerId match {
      case 0 => null
      case _ => dbOrganisation.getOrganizationById(insurerId)
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
    em.createQuery(ClientPolicyByNumberSerialAndTypeIdQuery, classOf[ClientPolicy])
      .setParameter("number", number)
      .setParameter("serial", serial)
      .setParameter("typeId", typeId)
      .getResultList.isEmpty
  }


  def findBySerialAndNumberAndType(serial: String, number: String, typeId: Int): ClientPolicy = {
    val result = em.createQuery(findBySerialAndNumberQuery, classOf[ClientPolicy])
      .setParameter("number", number)
      .setParameter("serial", serial)
      .setParameter("typeId", typeId)
      .getResultList
    result.size match {
      case 0 => null
      case _ => result.iterator().next()
    }
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

  def persistNewPolicy(policy: ClientPolicy): ClientPolicy = {
    em.persist(policy)
    em.merge(policy)
  }

  def findBySerialAndNumberAndTypeCode(serial: String, number: String, typeCode: String): util.List[ClientPolicy] = {
    em.createNamedQuery("ClientPolicy.findBySerialAndNumberAndTypeCode", classOf[ClientPolicy])
      .setParameter("serial", serial).setParameter("number", number).setParameter("typeCode", typeCode).getResultList
  }

  def deleteAllClientPoliciesByType(patientId: Int, policyTypeCode: String): Int = {
    em.createNamedQuery("ClientPolicy.deleteAllClientPoliciesByType")
      .setParameter("patientId", patientId)
      .setParameter("policyTypeCode", policyTypeCode)
    .executeUpdate()
  }

  val ClientPolicyFindActiveByClientAndTypeQuery =
    """
      SELECT p
      FROM ClientPolicy p
      WHERE p.patient.id = :patientId
      AND p.deleted = 0
      AND p.policyType = :policyType
    """

  def getActivePoliciesByClientAndType(patientId: Int, policyType: RbPolicyType): util.List[ClientPolicy] = {
    val result = em.createQuery(ClientPolicyFindActiveByClientAndTypeQuery,
      classOf[ClientPolicy])
      .setParameter("patientId", patientId)
      .setParameter("policyType", policyType)
      .getResultList
    result.size match {
      case 0 =>
        throw new NoSuchClientPolicyException(
          ConfigManager.ErrorCodes.ClientPolicyNotFound,
          patientId,
          i18n("error.clientPolicyNotFound"))
      case size =>
        result
    }
  }
}
