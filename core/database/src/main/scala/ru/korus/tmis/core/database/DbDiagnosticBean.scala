package ru.korus.tmis.core.database

import javax.interceptor.Interceptors
import ru.korus.tmis.core.logging.LoggingInterceptor
import javax.ejb.{EJB, Stateless}
import grizzled.slf4j.Logging
import ru.korus.tmis.util.{ConfigManager, I18nable}
import javax.persistence.{EntityManager, PersistenceContext}
import ru.korus.tmis.core.entity.model.{Diagnosis, Diagnostic}
import scala.collection.JavaConversions._
import ru.korus.tmis.core.auth.AuthData
import java.util.Date
import ru.korus.tmis.core.exception.CoreException

/**
 * Методы для работы с таблицей Diagnostic
 * @author Ivan Dmitriev
 */
@Interceptors(Array(classOf[LoggingInterceptor]))
@Stateless
class DbDiagnosticBean  extends DbDiagnosticBeanLocal
                        with Logging
                        with I18nable {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  @EJB
  var appLock: AppLockBeanLocal = _

  @EJB
  var dbEventBean: DbEventBeanLocal = _

  @EJB
  var dbEventPersonBean: DbEventPersonBeanLocal = _

  @EJB
  var dbRbDiagnosisTypeBean: DbRbDiagnosisTypeBeanLocal = _

  @EJB
  var dbDiagnosisBean: DbDiagnosisBeanLocal = _

  def getDiagnosticById (id: Int) = {
    val result =  em.createQuery(DiagnosticByIdQuery, classOf[Diagnostic])
                    .setParameter("id", id)
                    .getResultList

    result.size match {
      case 0 => {
        throw new CoreException(ConfigManager.ErrorCodes.DiagnosticNotFound,
          i18n("error.diagnosticNotFound").format(id))
      }
      case size => {
        result.foreach(em.detach(_))
        result(0)
      }
    }
  }

  def getDiagnosticsByEventId (eventId: Int) = {
    val result =  em.createQuery(DiagnosticsByEventIdQuery, classOf[Diagnostic])
                    .setParameter("eventId", eventId)
                    .getResultList

    result.foreach(em.detach(_))
    result
  }

  def createDiagnostic(eventId: Int,
                       diagnosis: Diagnosis,
                       diagnosisTypeFlatCode: String,
                       diseaseCharacterId: Int,
                       userData: AuthData) = {

    val now = new Date()
    val event = dbEventBean.getEventById(eventId)
    val diagnosisType = dbRbDiagnosisTypeBean.getRbDiagnosisTypeByFlatCode(diagnosisTypeFlatCode)

    //Создание
    val diagnostic = new Diagnostic()

    diagnostic.setCreateDatetime(now)
    diagnostic.setModifyDatetime(now)
    diagnostic.setCreatePerson(userData.getUser)
    diagnostic.setModifyPerson(userData.getUser)
    diagnostic.setEvent(event)
    diagnostic.setDiagnosisType(diagnosisType)
    diagnostic.setDiagnosis(diagnosis)
    diagnostic.setCharacterId(diseaseCharacterId)
    diagnostic.setSanatorium(0)
    diagnostic.setHospital(0)
    diagnostic.setSpeciality(userData.getUser.getSpeciality)
    diagnostic.setPerson(userData.getUser)
    diagnostic.setSetDate(now)
    diagnostic.setNotes("")

    diagnostic
  }

  def insertOrUpdateDiagnostic(id: Int,
                               eventId: Int,
                               diagnosis: Diagnosis,
                               diagnosisTypeFlatCode: String,
                               diseaseCharacterId: Int,
                               userData: AuthData) = {
    val now = new Date()
    var diagnostic: Diagnostic = null
    var oldDiagnostic: Diagnostic = null
    var lockId: Int = 0

    if (id>0) {
      diagnostic = getDiagnosticById(id)
      oldDiagnostic = Diagnostic.clone(diagnostic)
      lockId = appLock.acquireLock("Diagnostic", diagnostic.getId.intValue(), oldDiagnostic.getId.intValue(), userData)
    }
    else {
      diagnostic = new Diagnostic()

      diagnostic.setCreateDatetime(now)
      diagnostic.setCreatePerson(userData.getUser)
      diagnostic.setSanatorium(0)
      diagnostic.setHospital(0)
      diagnostic.setNotes("")
    }

    try {
      val event = dbEventBean.getEventById(eventId)
      val diagnosisType = dbRbDiagnosisTypeBean.getRbDiagnosisTypeByFlatCode(diagnosisTypeFlatCode)

      diagnostic.setModifyDatetime(now)
      diagnostic.setModifyPerson(userData.getUser)
      diagnostic.setEvent(event)
      diagnostic.setDiagnosisType(diagnosisType)
      diagnostic.setDiagnosis(diagnosis)
      diagnostic.setCharacterId(diseaseCharacterId)
      diagnostic.setSpeciality(userData.getUser.getSpeciality)
      diagnostic.setPerson(userData.getUser)
      diagnostic.setSetDate(now)
    }
    finally {
      if(lockId>0)
        appLock.releaseLock(lockId)
    }
    diagnostic
  }

  def getDiagnosticsByEventIdAndTypes(eventId: Int, diagnosisTypeFlatCodes: java.util.Set[String]) = {
    val result =  em.createQuery(DiagnosticsByEventIdAndTypesQuery, classOf[Diagnostic])
                    .setParameter("eventId", eventId)
                    .setParameter("flatCodes", asJavaCollection(diagnosisTypeFlatCodes))
                    .getResultList

    result.foreach(em.detach(_))
    result
  }

  val DiagnosticByIdQuery = """
    SELECT diac
    FROM Diagnostic diac
    WHERE
      diac.id = :id
                           """

  val DiagnosticsByEventIdQuery =
    """
    SELECT diac
    FROM
      Diagnostic diac
        JOIN diac.event e
    WHERE
      e.id = :eventId
    AND
      diac.deleted = '0'
    """

  val DiagnosticsByEventIdAndTypesQuery =
    """
    SELECT diac
    FROM
      Diagnostic diac
        JOIN diac.event e
    WHERE
      e.id = :eventId
    AND
      diac.diagnosisType.flatCode IN :flatCodes
    AND
      diac.deleted = '0'
    """
}
