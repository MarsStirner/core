package ru.korus.tmis.core.database

import java.lang.Boolean
import java.util.Date
import javax.ejb.{EJB, Stateless}
import javax.persistence.{EntityManager, PersistenceContext}

import ru.korus.tmis.core.auth.AuthStorageBeanLocal
import ru.korus.tmis.core.data.{DoctorContainer, IdNameContainer, TableCol, TableValue}
import ru.korus.tmis.core.database.common.{DbEventBeanLocal, DbEventPersonBeanLocal}
import ru.korus.tmis.core.entity.model._
import ru.korus.tmis.core.exception.CoreException
import ru.korus.tmis.scala.util.{ConfigManager, I18nable}

import scala.collection.JavaConversions._
import scala.language.reflectiveCalls

/**
 * Методы для работы с таблицей Diagnostic
 * @author Ivan Dmitriev
 */
@Stateless
class DbDiagnosticBean  extends DbDiagnosticBeanLocal
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


  def getDiagnosticById (id: Int) = {
    val result =  em.createQuery(DiagnosticByIdQuery, classOf[Diagnostic])
                    .setParameter("id", id)
                    .getResultList
    result.size match {
      case 0 =>  throw new CoreException(ConfigManager.ErrorCodes.DiagnosticNotFound,
        i18n("error.diagnosticNotFound").format(id))
      case size => result.iterator.next
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
                               staff: Staff,
                               isNewDiag: Boolean) = {
    val now = new Date()
    var diagnostic: Diagnostic = null
    var oldDiagnostic: Diagnostic = null

    val event = dbEventBean.getEventById(eventId)
    val diagnosisType = dbRbDiagnosisTypeBean.getRbDiagnosisTypeByFlatCode(diagnosisTypeFlatCode)

    val save: Diagnostic => Diagnostic =
    if (id>0) {
      diagnostic = getDiagnosticById(id)
      oldDiagnostic = Diagnostic.clone(diagnostic)
      val merge = { d: Diagnostic => em.merge(d) }
      merge
    } else {
      diagnostic = new Diagnostic()
      diagnostic.setCreateDatetime(now)
      diagnostic.setCreatePerson(staff)
      diagnostic.setSanatorium(0)
      diagnostic.setHospital(0)
      val persist: Diagnostic => Diagnostic = { d: Diagnostic => {
        em.persist(d)
        d} }
      persist
    }

      diagnostic.setModifyDatetime(now)
      diagnostic.setModifyPerson(staff)
      diagnostic.setEvent(event)
      diagnostic.setAction(action)
      diagnostic.setDiagnosisType(diagnosisType)
      if (diseaseCharacterId > 0) {
        diagnostic.setCharacter(dbRbDiseaseCharacterBean.getDiseaseCharacterById(diseaseCharacterId))
      }
      if (diseaseStageId > 0) {
        diagnostic.setStage(dbRbDiseaseStageBean.getDiseaseStageById(diseaseStageId))
      }
      val speciality: Speciality = if (staff.getSpeciality == null) {
        em.find(classOf[Speciality], 1)
      } else {
        staff.getSpeciality
      }

      diagnostic.setSpeciality(speciality)
      diagnostic.setPerson(staff)
      if(diagnostic.getSetDate == null || isNewDiag) {
        diagnostic.setSetDate(now)
      }
      diagnostic.setNotes(note)
      diagnostic.setDiagnosis(diagnosis)

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
      col.values.add(new TableValue(d.getNotes))
      return col
    }
    null
  }

  override def insertOrUpdateDiagnostic(ap: ActionProperty, tableCol: TableCol, staff: Staff): Diagnostic = {
    val now = new Date()
    while(tableCol.getValues.size() < 7) tableCol.getValues.add(new TableValue(null, ""))
    val (diagnostic: Diagnostic, save) = if(tableCol.getId == null) {
      (initDiagnostic(staff, now, new Diagnostic(dbDiagnosisBean.createDiagnosis(ap, tableCol, staff))), saveNewDiagnostic(_))
    } else {
      (getDiagnosticById(tableCol.getId), em.merge[Diagnostic](_))
    }

    val staffId =  if(tableCol.getValues.get(DbDiagnosticBeanLocal.INDX_DIAG_PERSON) != null ) {
      tableCol.getValues.get(DbDiagnosticBeanLocal.INDX_DIAG_PERSON).getPerson.getId
    }
    else -1

    val setPerson: Staff = if(staffId > 0) dbStaff.getStaffById(staffId) else null
    val diagType: RbDiagnosisType = dbRbDiagnosisTypeBean.getRbDiagnosisTypeById(tableCol.getValues.get(DbDiagnosticBeanLocal.INDX_DIAG_TYPE).getRbValue.getId)
    modifyDiagnostic(diagnostic,
      null,
      ap.getAction.getEvent,
      setPerson,
      tableCol.getValues.get(DbDiagnosticBeanLocal.INDX_SET_DATE).getDate,
      diagType,
      dbRbDiseaseCharacterBean.getDiseaseCharacterById(tableCol.getValues.get(DbDiagnosticBeanLocal.INDX_DIAG_CHARACTER).getRbValue.getId),
      null,
      tableCol.getValues.get(DbDiagnosticBeanLocal.INDX_DIAG_NOTE).getValue,
      now)

    save(diagnostic)
    return diagnostic
  }

  def saveNewDiagnostic(d: Diagnostic) : Unit = {
    em.persist(d)
    em.flush()
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
      em.find(classOf[Speciality], 1)
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

}
