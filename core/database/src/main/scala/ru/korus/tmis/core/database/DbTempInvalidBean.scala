package ru.korus.tmis.core.database

import ru.korus.tmis.core.entity.model.{TempInvalidPeriod, TempInvalid, Staff, Patient}
import javax.interceptor.Interceptors
import javax.ejb.Stateless
import grizzled.slf4j.Logging
import ru.korus.tmis.core.logging.LoggingInterceptor
import javax.persistence.PersistenceContext
import javax.persistence.EntityManager
import ru.korus.tmis.core.exception.NoSuchRbTempInvalidDocumentException
import java.util.Date
import javax.ejb.EJB
import ru.korus.tmis.scala.util.{I18nable, ConfigManager}
import scala.language.reflectiveCalls

@Interceptors(Array(classOf[LoggingInterceptor]))
@Stateless
class DbTempInvalidBean
  extends DbTempInvalidBeanLocal
  with Logging
  with I18nable {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _


  @EJB
  var rbTempInvalidDocLocal: DbRbTempInvalidDocumentBeanLocal = _

  @EJB
  var rbTempIvalidReason: DbRbTempInvalidReasonBeanLocal = _


  val FindByIdQuery = """
    SELECT t
    FROM
      TempInvalid t
    WHERE
      t.id = :id
                      """

  val FindByEventIdQuery = """
    SELECT t
    FROM
      TempInvalid t
    WHERE
      t.event.id = :eventId
                           """

  def getTempInvalidById(id: Int): TempInvalid = {
    val result = em.createQuery(FindByIdQuery,
      classOf[TempInvalid])
      .setParameter("id", id)
      .getResultList

    result.size match {
      case 0 => {
        throw new NoSuchRbTempInvalidDocumentException(
          ConfigManager.ErrorCodes.RbTempInvalidDocumentNotFound,
          id,
          i18n("error.rbTempInvalidDocumentNotFound"))
      }
      case size => {
        var ti = result.iterator.next()
        ti
      }
    }
  }

  def getTempInvalidByEventId(eventId: Int): TempInvalid = {
    val result = em.createQuery(FindByEventIdQuery,
      classOf[TempInvalid])
      .setParameter("eventId", eventId)
      .getResultList

    result.size match {
      case 0 => {
        null
      }
      case size => {
        var ti = result.iterator.next()
        em.detach(ti)
        ti
      }
    }
  }

  def insertOrUpdateTempInvalid(tempInvalidIn: TempInvalid,
                                start: Date,
                                end: Date,
                                docType: Short,
                                reasonCode: String,
                                caseBegDate: Date,
                                serial: String,
                                number: String,
                                sex: Short,
                                age: Byte,
                                duration: Int,
                                closed: Short,
                                patient: Patient,
                                sessionUser: Staff
                                 ) = {

    var tempInvalid: TempInvalid = if (tempInvalidIn == null) new TempInvalid() else tempInvalidIn
    val now: Date = new Date
    tempInvalid.setCreateDatetime(now)
    tempInvalid.setCreatePerson(sessionUser)
    tempInvalid.setModifyDatetime(now)
    tempInvalid.setCaseBegDate(caseBegDate);
    tempInvalid.setBegDate(start)
    tempInvalid.setEndDate(end)
    tempInvalid.setSerial(if (serial == null) "" else serial)
    tempInvalid.setNumber(if (number == null) "" else number)
    tempInvalid.setSex(sex)
    tempInvalid.setAge(age)
    tempInvalid.setDeleted(false)
    tempInvalid.setDuration(duration)
    tempInvalid.setClosed(closed)
    tempInvalid.setModifyPerson(sessionUser)
    tempInvalid.setDocTypeCode(docType)
    tempInvalid.setNotes("")
    tempInvalid.setDocType(rbTempInvalidDocLocal.getRbTempInvalidDocumentByCode(String.valueOf(docType)))
    tempInvalid.setTempInvalidReason(if (reasonCode == null) null else rbTempIvalidReason.getRbTempInvalidReasonByCode(reasonCode))

    tempInvalid.setPatient(patient)

    val tempInvalidPeriod = {
      if (tempInvalid.getTempInvalidPeriod.isEmpty) {
        val periodNew: TempInvalidPeriod = new TempInvalidPeriod()
        periodNew.setIsExternal(0)
        periodNew.setMaster(tempInvalid)
        periodNew.setNote("")
        tempInvalid.getTempInvalidPeriod.add(periodNew)
      }
      tempInvalid.getTempInvalidPeriod.iterator.next
    }
    tempInvalidPeriod.setBegDate(start)
    tempInvalidPeriod.setEndDate(end)

    em.persist(tempInvalid)
    em.flush()
    tempInvalid
  }


  def deleteTempInvalid(id: Int, sessionUser: Staff) = {
    var tempInvalid: TempInvalid = getTempInvalidById(id)
    val now: Date = new Date
    tempInvalid.setModifyPerson(sessionUser)
    tempInvalid.setModifyDatetime(now)
    tempInvalid.setDeleted(true)
  }

}