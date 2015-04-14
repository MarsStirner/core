package ru.korus.tmis.core.database

import common.{DbEventPersonBeanLocal, DbEventBeanLocal}

import javax.ejb.{EJB, Stateless}
import grizzled.slf4j.Logging
import javax.persistence.{EntityManager, PersistenceContext}
import ru.korus.tmis.core.data._
import ru.korus.tmis.core.entity.model._
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

  @EJB
  var dbStaff: DbStaffBeanLocal = _

  def getDiagnosticById (id: Int): Diagnostic = {
    return em.find(classOf[Diagnostic], id)
  }

  def getDiagnosticsByEventId (eventId: Int) = {
    val result =  em.createQuery(DiagnosticsByEventIdQuery, classOf[Diagnostic])
                    .setParameter("eventId", eventId)
                    .getResultList
    result
  }

  @deprecated
  def insertOrUpdateDiagnostic(id: Int,
                               eventId: Int,
                               diagnosis: Diagnosis,
                               diagnosisTypeFlatCode: String,
                               diseaseCharacterId: Int,
                               diseaseStageId: Int,
                               note: String,
                               userData: AuthData) = {
    val now = new Date()

    val event = dbEventBean.getEventById(eventId)
    val diagnosisType = dbRbDiagnosisTypeBean.getRbDiagnosisTypeByFlatCode(diagnosisTypeFlatCode)
    val character: RbDiseaseCharacter = if (diseaseCharacterId > 0) {
      dbRbDiseaseCharacterBean.getDiseaseCharacterById(diseaseCharacterId)
    } else null
    val stage: RbDiseaseStage = if(diseaseStageId > 0) {
      dbRbDiseaseStageBean.getDiseaseStageById(diseaseStageId)
    } else null

    val staff: Staff = userData.getUser
    val diagnostic: Diagnostic = if (id>0) {
      getDiagnosticById(id)
    }
    else {
      initDiagnostic(staff, now, new Diagnostic())
    }

    try {
      modifyDiagnostic(diagnostic, diagnosis, event, staff, now, diagnosisType, character, stage, note, now)
    }
    finally {
    }
    diagnostic
  }

  def modifyDiagnostic(diagnostic: Diagnostic,
                      diagnosis: Diagnosis, 
                      event: Event, 
                      staff: Staff,
                      setDate: Date,
                      diagnosisType: RbDiagnosisType, 
                      character: RbDiseaseCharacter, 
                      stage: RbDiseaseStage, 
                      note: String,
                      now: Date) {
    diagnostic.setModifyDatetime(now)
    diagnostic.setModifyPerson(staff)
    diagnostic.setEvent(event)
    diagnostic.setDiagnosisType(diagnosisType)
    diagnostic.setCharacter(character)
    diagnostic.setStage(stage)
    val speciality: Speciality = if (staff.getSpeciality == null) {
      em.find(classOf[Speciality], 1);
    } else {
      staff.getSpeciality
    }
    diagnostic.setSpeciality(speciality)
    diagnostic.setPerson(staff)
    diagnostic.setSetDate(setDate)
    diagnostic.setNotes(note)
    if(diagnosis != null) {
      diagnostic.setDiagnosis(diagnosis)
    }
  }

  def initDiagnostic(createPerson: Staff, now: Date, diagnostic: Diagnostic) : Diagnostic =  {
    diagnostic.setCreateDatetime(now)
    diagnostic.setCreatePerson(createPerson)
    diagnostic.setSanatorium(0)
    diagnostic.setHospital(0)
    diagnostic
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

  override def deleteDiagnostic(id: Integer): Diagnostic = {
    val res: Diagnostic = getDiagnosticById(id)
    if( res != null) {
      res.setDeleted(true)
      if(res.getDiagnosis != null) {
        res.getDiagnosis.setDeleted(true)
        em.merge(res.getDiagnosis)
      }
      em.merge(res)
    }
    res
  }

  override def insertOrUpdateDiagnostic(ap: ActionProperty, tableCol: TableCol, staff: Staff): Diagnostic = {
    val now = new Date()
    while(tableCol.getValues.size() < 7) tableCol.getValues.add(new TableValue(null, ""));
    val (diagnosic: Diagnostic, save) = if(tableCol.getId == null) {
      (initDiagnostic(staff, now, new Diagnostic(dbDiagnosisBean.createDiagnosis(ap, tableCol, staff))), em.persist(_))
    } else {
      (getDiagnosticById(tableCol.getId), em.merge[Diagnostic](_))
    }

    val staffId =  if(tableCol.getValues.get(DbDiagnosticBeanLocal.INDX_DIAG_PERSON) != null ) {
      tableCol.getValues.get(DbDiagnosticBeanLocal.INDX_DIAG_PERSON).getPerson.getId
    }
    else -1

    val setPerson: Staff = if(staffId > 0) dbStaff.getStaffById(staffId) else null
    val diagType: RbDiagnosisType = dbRbDiagnosisTypeBean.getRbDiagnosisTypeById(tableCol.getValues.get(DbDiagnosticBeanLocal.INDX_DIAG_TYPE).getRbValue.getId)
    modifyDiagnostic(diagnosic,
      null,
      ap.getAction.getEvent,
      setPerson,
      tableCol.getValues.get(DbDiagnosticBeanLocal.INDX_SET_DATE).getDate,
      diagType,
      dbRbDiseaseCharacterBean.getDiseaseCharacterById(tableCol.getValues.get(DbDiagnosticBeanLocal.INDX_DIAG_CHARACTER).getRbValue.getId),
      null,
      tableCol.getValues.get(DbDiagnosticBeanLocal.INDX_DIAG_NOTE).getValue,
      now)

    save(diagnosic)
    return diagnosic
  }

  override def toTableCol(d : Diagnostic): TableCol = {
    val diagnosis: Diagnosis = if (d == null) null else d.getDiagnosis
    if (d != null && diagnosis != null) {

      val col: TableCol = new TableCol(d.getId)
      col.values.add(new TableValue(d.getSetDate))
      col.values.add(new TableValue(diagnosis.getEndDate))
      col.values.add(new TableValue(if (diagnosis.getMkb == null) null else diagnosis.getMkb))
      col.values.add(new TableValue(if (diagnosis.getDiagnosisType == null) null else
        new IdNameContainer(diagnosis.getDiagnosisType.getId, diagnosis.getDiagnosisType.getCode, diagnosis.getDiagnosisType.getName),
        "rbDiagnosisType"))
      col.values.add(new TableValue(if (diagnosis.getCharacter == null) null else
        new IdNameContainer(diagnosis.getCharacter.getId, diagnosis.getCharacter.getCode, diagnosis.getCharacter.getName),
        "rbDiseaseCharacter"))
      col.values.add(new TableValue(if (d.getResult == null) null else new IdNameContainer(d.getResult.getId, d.getResult.getCode, d.getResult.getName),
        "rbResult"))
      col.values.add(new TableValue(if (d.getAcheResult == null) null else new IdNameContainer(d.getAcheResult.getId, d.getAcheResult.getCode, d.getAcheResult.getName),
        "rbAcheResult"))
      col.values.add(new TableValue(if (diagnosis.getPerson == null) null else new DoctorContainer(diagnosis.getPerson)))
      col.values.add(new TableValue((d.getNotes)))
      return col
    }
    null
  }
}
