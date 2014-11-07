package ru.korus.tmis.core.database

import javax.interceptor.Interceptors
import javax.ejb.Stateless
import grizzled.slf4j.Logging
import ru.korus.tmis.core.logging.LoggingInterceptor
import javax.persistence.PersistenceContext
import javax.persistence.EntityManager
import java.lang.Iterable
import java.util.Date
import javax.ejb.EJB
import ru.korus.tmis.core.exception.NoSuchEntityException
import ru.korus.tmis.core.entity.model._
import fd.ClientSocStatus
import scala.collection.JavaConversions._
import ru.korus.tmis.core.data.DocumentContainer
import ru.korus.tmis.scala.util.{I18nable, ConfigManager}
import scala.language.reflectiveCalls

@Interceptors(Array(classOf[LoggingInterceptor]))
@Stateless
class DbClientSocStatusBean
  extends DbClientSocStatusBeanLocal
  with Logging
  with I18nable {


  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  @EJB
  var socTypeBean: DbRbSocTypeBeanLocal = _

  @EJB
  var socStatusClassBean: DbRbSocStatusClassBeanLocal = _

  @EJB
  var dbClientDocument: DbClientDocumentBeanLocal = _

  @EJB
  var dbFDRecord: DbFDRecordBeanLocal = _

  val FindByIdQuery = """
    SELECT t
    FROM
      ClientSocStatus t
    WHERE
      t.id = :id
                      """

  def getClientSocStatusById(id: Int): ClientSocStatus = {
    val result = em.createQuery(FindByIdQuery,
      classOf[ClientSocStatus])
      .setParameter("id", id)
      .getResultList

    result.size match {
      case 0 => {
        throw new NoSuchEntityException(
          ConfigManager.ErrorCodes.ClientSocStatusNotFound,
          id,
          i18n("error.ClientSocStatusNotFound"))
      }
      case size => {
        result.foreach(rbType => {
          em.detach(rbType)
        })
        result(0)
      }
    }
  }


  def insertOrUpdateClientSocStatus(
                                     id: Int,
                                     socStatusClassId: Int, /* NOT USED*/
                                     socStatusTypeId: Int,
                                     document: DocumentContainer,      //documentId: Int,
                                     begDate: Date,
                                     endDate: Date,
                                     patient: Patient,
                                     benefitCategoryId: Int,
                                     note: String,
                                     sessUser: Staff): ClientSocStatus = {
    var cs: ClientSocStatus = null
    val now: Date = new Date
    if (id > 0) {
      cs = getClientSocStatusById(id);
    } else {
      cs = new ClientSocStatus
      cs.setCreateDatetime(now)
      cs.setCreatePerson(sessUser)
    }
    cs.setModifyPerson(sessUser)
    cs.setModifyDatetime(now)
    cs.setDeleted(false)
    if (note != null) {
      cs.setNote(note)
    } else {
      cs.setNote("")
    }
    if (benefitCategoryId > 0) {
      cs.setBenefitCategoryId(dbFDRecord.getFDRecordById(benefitCategoryId))
    }
    if (begDate != null) {
      cs.setBegDate(begDate)
    }

    //if (endDate != null) {
      cs.setEndDate(endDate)
    //}

    if (document != null && document.getId() > 0) {
      var currentDocument = cs.getDocument
      //cs.setDocument(dbClientDocument.getClientDocumentById(documentId))
      currentDocument = dbClientDocument.insertOrUpdateClientDocument(
        if (currentDocument != null) {
          currentDocument.getId.intValue()
        } else {
          0
        },
        document.getId(), //тип документа (documentTypeId)
        document.getComment(), //clientIdCard.getIssued(),
        document.getNumber(), //clientIdCard.getNumber(),
        document.getSeries(), //clientIdCard.getSeries(),
        document.getDate(),
        null,//endDate,
        patient,
        sessUser
      )
      cs.setDocument(currentDocument)
    }
    if (socStatusTypeId > 0) {
      cs.setSocStatusType(socTypeBean.getSocStatusTypeById(socStatusTypeId))
    }
    if (socStatusClassId > 0) {
      cs.setSocStatusClass(socStatusClassBean.getSocStatusClassById(socStatusClassId))
    }
    cs.setPatient(patient)

    cs
  }

  def deleteClientSocStatus(id: Int, sessionUser: Staff) = {
    val c = getClientSocStatusById(id)
    val now = new Date
    c.setDeleted(true)
    c.setModifyPerson(sessionUser)
    c.setModifyDatetime(now)
  }
}