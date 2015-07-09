package ru.korus.tmis.core.database

import common.{DbEventPersonBeanLocal, DbPatientBeanLocal}
import javax.ejb.{EJB, TransactionAttributeType, TransactionAttribute, Stateless}
import grizzled.slf4j.Logging
import javax.persistence.{EntityManager, PersistenceContext}
import ru.korus.tmis.core.entity.model._
import ru.korus.tmis.core.exception.CoreException
import ru.korus.tmis.core.auth.{AuthStorageBeanLocal, AuthData}
import java.util.Date
import scala.collection.JavaConversions._
import ru.korus.tmis.scala.util.{I18nable, ConfigManager}
import scala.language.reflectiveCalls

/**
 * Методы для работы с таблицей Diagnosis
 * @author Ivan Dmitriev
 */
@Stateless
class DbDiagnosisBean extends DbDiagnosisBeanLocal
with Logging
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

  //@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
  def getDiagnosisById(id: Int) = {
    val result = em.createQuery(DiagnosisByIdQuery, classOf[Diagnosis])
      .setParameter("id", id)
      .getResultList

    result.size match {
      case 0 => {
        throw new CoreException(ConfigManager.ErrorCodes.DiagnosisNotFound,
          i18n("error.diagnosisNotFound").format(id))
      }
      case size => {

        result(0)
      }
    }
  }

  def insertOrUpdateDiagnosis(id: Int,
                              clientId: Int,
                              diagnosisTypeFlatCode: String,
                              diseaseCharacterId: Int,
                              mkbId: Int,
                              staff: Staff) = {

    val now = new Date()
    var diagnosis: Diagnosis = null
    var oldDiagnosis: Diagnosis = null
    val save: Diagnosis => Diagnosis = if (id > 0) {
      diagnosis = getDiagnosisById(id)
      oldDiagnosis = Diagnosis.clone(diagnosis)
      val fun = { d: Diagnosis => em.merge(d)}
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

    save(diagnosis)
  }

  val DiagnosisByIdQuery = """
    SELECT dias
    FROM Diagnosis dias
    WHERE
      dias.id = :id
                           """
}
