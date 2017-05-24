package ru.korus.tmis.core.database

import java.util
import java.util.Date
import javax.ejb.{EJB, Stateless}
import javax.persistence.{EntityManager, PersistenceContext}

import ru.korus.tmis.core.auth.AuthStorageBeanLocal
import ru.korus.tmis.core.data.TableCol
import ru.korus.tmis.core.database.common.{DbEventPersonBeanLocal, DbPatientBeanLocal}
import ru.korus.tmis.core.entity.model._
import ru.korus.tmis.core.entity.model.new_diagnosis.EventDiagnosis
import ru.korus.tmis.core.exception.CoreException
import ru.korus.tmis.scala.util.{ConfigManager, I18nable}

import scala.collection.JavaConverters._
import scala.language.reflectiveCalls

/**
 * Методы для работы с таблицей Diagnosis
 * @author Ivan Dmitriev
 */
@Stateless
class DbDiagnosisBean extends DbDiagnosisBeanLocal
with I18nable {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  @EJB
  var appLock: AuthStorageBeanLocal = _

  @EJB
  var dbPatientBean: DbPatientBeanLocal = _

  @EJB
  var dbEventPersonBean: DbEventPersonBeanLocal = _

  @EJB
  var dbRbDiagnosisTypeBean: DbRbDiagnosisTypeBeanLocal = _

  @EJB
  var dbMKBBean: DbMkbBeanLocal = _

  @EJB
  var dbRbDiseaseCharacterBeanLocal: DbRbDiseaseCharacterBeanLocal = _

  @EJB
  var dbStaff: DbStaffBeanLocal = _

  //@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
  def getDiagnosisById(id: Int): Diagnosis = {
    val result = em.find(classOf[Diagnosis], id)
    if (result != null) {
      result
    } else {
      throw new CoreException(ConfigManager.ErrorCodes.DiagnosisNotFound,
        i18n("error.diagnosisNotFound").format(id))
    }
  }

  def insertOrUpdateDiagnosis(id: Int,
                              clientId: Int,
                              diagnosisTypeFlatCode: String,
                              diseaseCharacterId: Int,
                              mkbId: Int,
                              staff: Staff): Diagnosis = {

    val now = new Date()
    var diagnosis: Diagnosis = null
    var oldDiagnosis: Diagnosis = null
    val save: Diagnosis => Diagnosis = if (id > 0) {
      diagnosis = getDiagnosisById(id)
      oldDiagnosis = Diagnosis.clone(diagnosis)
      val fun = { d: Diagnosis => em.merge(d) }
      fun
    }
    else {
      diagnosis = new Diagnosis()
      diagnosis.setCreateDatetime(now)
      diagnosis.setCreatePerson(staff)
      diagnosis.setMkbExCode("")
      val fun = {
        d: Diagnosis => em.persist(d)
          d
      }
      fun
    }

    val client = dbPatientBean.getPatientById(clientId)
    val diagnosisType = dbRbDiagnosisTypeBean.getRbDiagnosisTypeByFlatCode(diagnosisTypeFlatCode)
    var mkb: Mkb = null
    try {
      mkb = dbMKBBean.getMkbById(mkbId)
    } catch {
      case e: Exception => mkb = null
    }
    if (client == null || diagnosisType == null || mkb == null) {
      diagnosis = null
    }
    else {
      diagnosis.setModifyDatetime(now)
      diagnosis.setModifyPerson(staff)
      diagnosis.setPatient(client)
      diagnosis.setDiagnosisType(diagnosisType)
      diagnosis.setCharacter(if (diseaseCharacterId > 0) dbRbDiseaseCharacterBeanLocal.getDiseaseCharacterById(diseaseCharacterId) else null)
      diagnosis.setMkb(mkb)
      diagnosis.setPerson(staff)
      diagnosis.setEndDate(now)
    }
    if (diagnosis != null) save(diagnosis) else null

  }

  override def createDiagnosis(ap: ActionProperty, tableCol: TableCol, staff: Staff): Diagnosis = {
    val diagnosis = new Diagnosis()
    diagnosis.setCreateDatetime(new Date)
    diagnosis.setCreatePerson(staff)
    diagnosis.setMkbExCode("")
    diagnosis.setModifyDatetime(new Date)
    diagnosis.setModifyPerson(staff)
    diagnosis.setPatient(ap.getAction.getEvent.getPatient)

    val staffId = if (tableCol.getValues.get(DbDiagnosticBeanLocal.INDX_DIAG_PERSON) != null) {
      tableCol.getValues.get(DbDiagnosticBeanLocal.INDX_DIAG_PERSON).getPerson.getId
    }
    else -1

    val setPerson: Staff = if (staffId > 0) dbStaff.getStaffById(staffId) else null

    diagnosis.setDiagnosisType(
      dbRbDiagnosisTypeBean.getRbDiagnosisTypeById(tableCol.getValues.get(DbDiagnosticBeanLocal.INDX_DIAG_TYPE).getRbValue.getId))
    diagnosis.setCharacter(dbRbDiseaseCharacterBeanLocal.getDiseaseCharacterById(tableCol.getValues.get(DbDiagnosticBeanLocal.INDX_DIAG_CHARACTER).getRbValue.getId))
    diagnosis.setMkb(dbMKBBean.getMkbByCode(tableCol.getValues.get(DbDiagnosticBeanLocal.INDX_DIAG_MKB).getValue))
    diagnosis.setPerson(setPerson)
    val date: Date = tableCol.getValues.get(DbDiagnosticBeanLocal.INDX_SET_DATE).getDate
    diagnosis.setSetDate(date)
    diagnosis.setEndDate(date)
    em.persist(diagnosis)
    diagnosis
  }

  /**
    *
    * @param event
    * @param date
    * @return
    * @since new_diagnosis
    */
  override def getEventDiagnosis(event: Event, date: Date): util.Set[EventDiagnosis] = {
    val result = em.createQuery("""
SELECT a FROM EventDiagnosis a
WHERE a.event = :event
AND a.diagnosis.setDate <= :date
AND ( a.diagnosis.endDate >= :date OR a.diagnosis.endDate IS NULL )
AND a.deleted = 0 """, classOf[EventDiagnosis])
      .setParameter("event", event)
      .setParameter("date", date)
      .getResultList
    new util.HashSet[EventDiagnosis](result)
  }

  def getActualDiagnostic(diagnosis: Diagnosis, checkDate: Date): Diagnostic = {
    em.createQuery(
      """SELECT a FROM Diagnostic a WHERE a.diagnosis = :diagnosis AND a.setDate <= :checkDate AND a.deleted = 0 ORDER BY a.setDate DESC""",
      classOf[Diagnostic]).setMaxResults(1)
      .setParameter("diagnosis", diagnosis)
      .setParameter("checkDate", checkDate)
      .getResultList.stream().findFirst().orElse(null)
  }

  override def getEventDiagnosisWithActualDiagnostic(event: Event, datetimeTaken: Date): util.Map[EventDiagnosis, Diagnostic] = {
    getEventDiagnosis(event, datetimeTaken).asScala.view.map(x => x -> getActualDiagnostic(x.getDiagnosis, datetimeTaken)).toMap.asJava
  }
}
