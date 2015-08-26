package ru.korus.tmis.core.database


import javax.persistence.{EntityManager, PersistenceContext}
import grizzled.slf4j.Logging
import java.lang.Iterable
import ru.korus.tmis.core.exception.NoSuchClientDocumentException
import java.util.Date
import ru.korus.tmis.core.entity.model.{Staff, Patient, ClientDocument}
import javax.ejb.{EJB, Stateless}
import scala.collection.JavaConversions._
import java.util
import ru.korus.tmis.scala.util.{I18nable, ConfigManager}
import scala.language.reflectiveCalls

@Stateless
class DbClientDocumentBean
  extends DbClientDocumentBeanLocal
  with Logging
  with I18nable {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  @EJB
  var dbRbDocumentType: DbRbDocumentTypeBeanLocal = _

  val ClientDocumentFindQuery = """
    SELECT d
    FROM
      ClientDocument d
    WHERE
      d.id = :id
                                """


  def getAllDocuments(patientId: Int): Iterable[ClientDocument] = {
    em.createNamedQuery("ClientDocument.findAll", classOf[ClientDocument]).getResultList
  }

  def getClientDocumentById(id: Int): ClientDocument = {
    val result = em.createQuery(ClientDocumentFindQuery,
      classOf[ClientDocument])
      .setParameter("id", id)
      .getResultList

    result.size match {
      case 0 => {
        throw new NoSuchClientDocumentException(
          ConfigManager.ErrorCodes.ClientDocumentNotFound,
          id,
          i18n("error.clientDocumentNotFound"))
      }
      case size => {
        result.foreach(rbType => {

        })
        result(0)
      }
    }
  }

  def isClientDocumentExists(id: Int): Boolean = {
    val result = em.createQuery(ClientDocumentFindQuery,
      classOf[ClientDocument])
      .setParameter("id", id)
      .getResultList
    result.size match {
      case 0 => {
        false
      }
      case size => {
        result.foreach(rbType => {

        })
        true
      }
    }
  }

  def insertOrUpdateClientDocument(id: Int,
                                   rbDocumentTypeId: Int,
                                   issued: String,
                                   number: String,
                                   serial: String,
                                   startDate: Date,
                                   endDate: Date,
                                   patient: Patient,
                                   sessionUser: Staff): ClientDocument = {
    var d: ClientDocument = null
    val now = new Date
    if (id > 0) {
      d = getClientDocumentById(id)
    }
    else {
      d = new ClientDocument
      d.setCreatePerson(sessionUser)
      d.setCreateDatetime(now)
    }

    d.setDocumentType(dbRbDocumentType.getRbDocumentTypeById(rbDocumentTypeId))
    d.setIssued(issued)
    d.setNumber(number)
    d.setSerial(serial)
    if (startDate != null) {
      d.setDate(startDate)
    } else {
      d.setDate(now)
    }
    d.setEndDate(endDate)
    d.setPatient(patient)

    d.setDeleted(false)
    d.setModifyPerson(sessionUser)
    d.setModifyDatetime(now)

    d
  }

  def deleteClientDocument(id: Int,
                           sessionUser: Staff) = {
    val d = getClientDocumentById(id)
    val now = new Date
    d.setDeleted(true)
    d.setModifyPerson(sessionUser)
    d.setModifyDatetime(now)
  }

  @Override
  def persistNewDocument(document : ClientDocument) : ClientDocument = {
    em.persist(document)
    em.merge(document)
  }

  def findBySerialAndNumberAndTypeCode(serial: String, number: String, typeCode: String): util.List[ClientDocument] = {
    em.createNamedQuery("ClientDocument.findBySerialAndNumberAndTypeCode", classOf[ClientDocument])
      .setParameter("serial", serial)
      .setParameter("number", number)
      .setParameter("typeCode", typeCode)
    .getResultList
  }
}