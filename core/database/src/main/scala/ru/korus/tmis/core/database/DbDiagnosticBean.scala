package ru.korus.tmis.core.database

import java.lang.Boolean

import common.{DbEventPersonBeanLocal, DbEventBeanLocal}

import javax.ejb.{EJB, Stateless}
import grizzled.slf4j.Logging
import javax.persistence.{EntityManager, PersistenceContext}
import ru.korus.tmis.core.entity.model.{Action, Speciality, Diagnosis, Diagnostic}
import scala.collection.JavaConversions._
import ru.korus.tmis.core.auth.{AuthStorageBeanLocal, AuthData}
import java.util.Date
import ru.korus.tmis.core.exception.CoreException
import ru.korus.tmis.scala.util.{I18nable, ConfigManager}
import scala.language.reflectiveCalls

/**
 * Методы для работы с таблицей Diagnostic
 * @author Ivan Dmitriev
 */
@Stateless
class DbDiagnosticBean  extends DbDiagnosticBeanLocal
                        with Logging
                        with I18nable {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  @EJB
  var appLock: AuthStorageBeanLocal = _

  @EJB
  var dbEventBean: DbEventBeanLocal = _

  @EJB
  var dbEventPersonBean: DbEventPersonBeanLocal = _

  @EJB
  var dbRbDiagnosisTypeBean: DbRbDiagnosisTypeBeanLocal = _

  @EJB
  var dbDiagnosisBean: DbDiagnosisBeanLocal = _

  @EJB
  var dbRbDiseaseCharacterBean: DbRbDiseaseCharacterBeanLocal = _

  @EJB
  var dbRbDiseaseStageBean: DbRbDiseaseStageBeanLocal = _

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

        result(0)
      }
    }
  }

  def getDiagnosticsByEventId (eventId: Int) = {
    val result =  em.createQuery(DiagnosticsByEventIdQuery, classOf[Diagnostic])
                    .setParameter("eventId", eventId)
                    .getResultList


    result
  }


  def insertOrUpdateDiagnostic(id: Int,
                               eventId: Int,
                               action: Action,
                               diagnosis: Diagnosis,
                               diagnosisTypeFlatCode: String,
                               diseaseCharacterId: Int,
                               diseaseStageId: Int,
                               note: String,
                               userData: AuthData) = {
    val now = new Date()
    var diagnostic: Diagnostic = null
    var oldDiagnostic: Diagnostic = null
    var lockId: Int = 0

    val event = dbEventBean.getEventById(eventId)
    val diagnosisType = dbRbDiagnosisTypeBean.getRbDiagnosisTypeByFlatCode(diagnosisTypeFlatCode)

    val save: Diagnostic => Diagnostic =
    if (id>0) {
      diagnostic = getDiagnosticById(id)
      oldDiagnostic = Diagnostic.clone(diagnostic)
      lockId = appLock.acquireLock("Diagnostic", id, oldDiagnostic.getId.intValue(), userData)
      val merge = { d: Diagnostic => em.merge(d) }
      merge
    } else {
      diagnostic = new Diagnostic()
      diagnostic.setCreateDatetime(now)
      diagnostic.setCreatePerson(userData.getUser)
      diagnostic.setSanatorium(0)
      diagnostic.setHospital(0)
      val persist: Diagnostic => Diagnostic = { d: Diagnostic => {
        em.persist(d)
        d} }
      persist
    }

    try {
      diagnostic.setModifyDatetime(now)
      diagnostic.setModifyPerson(userData.getUser)
      diagnostic.setEvent(event)
      diagnostic.setAction(action)
      diagnostic.setDiagnosisType(diagnosisType)
      if (diseaseCharacterId > 0) {
        diagnostic.setCharacter(dbRbDiseaseCharacterBean.getDiseaseCharacterById(diseaseCharacterId))
      }
      if (diseaseStageId > 0) {
        diagnostic.setStage(dbRbDiseaseStageBean.getDiseaseStageById(diseaseStageId))
      }
      val speciality: Speciality = if (userData.getUser.getSpeciality == null) {
        em.find(classOf[Speciality], 1);
      } else {
        userData.getUser.getSpeciality
      }

      diagnostic.setSpeciality(speciality)
      diagnostic.setPerson(userData.getUser)
      diagnostic.setSetDate(now)
      diagnostic.setNotes(note)
      diagnostic.setDiagnosis(diagnosis)
    }
    finally {
      if(lockId>0)
        appLock.releaseLock(lockId)
    }
    save(diagnostic)
  }

  def getDiagnosticsByEventIdAndTypes(eventId: Int, diagnosisTypeFlatCodes: java.util.Set[String]) = {
    val result =  em.createQuery(DiagnosticsByEventIdAndTypesQuery, classOf[Diagnostic])
                    .setParameter("eventId", eventId)
                    .setParameter("flatCodes", asJavaCollection(diagnosisTypeFlatCodes))
                    .getResultList


    result
  }

  def getLastDiagnosticByEventIdAndType(eventId: Int, diagnosisTypeFlatCode: String) = {
    val result =  em.createQuery(LastDiagnosticByEventIdAndTypesQuery, classOf[Diagnostic])
      .setParameter("eventId", eventId)
      .setParameter("flatCode", diagnosisTypeFlatCode)
      .getResultList


    if (result != null && result.size() > 0) {
      result.get(0)
    } else null

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

  val LastDiagnosticByEventIdAndTypesQuery =
    """
    SELECT diac
    FROM
      Diagnostic diac
        JOIN diac.event e
    WHERE
      e.id = :eventId
    AND
      diac.diagnosisType.flatCode = :flatCode
    AND
      diac.deleted = '0'
    ORDER BY diac.createDatetime DESC
    """

  override def deleteDiagnostic(actionId: Integer): Unit = {
    val result =  em.createNamedQuery("Diagnostic.findByActionId", classOf[Diagnostic])
      .setParameter("actionId", actionId)
      .getResultList
    result.foreach(d => {
      d.setDeleted(true)
      if(d.getDiagnosis != null) d.getDiagnosis.setDeleted(true)
      em.merge(d)}
    )

  }
}
