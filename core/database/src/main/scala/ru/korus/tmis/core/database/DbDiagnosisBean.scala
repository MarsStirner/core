package ru.korus.tmis.core.database

import common.{DbEventPersonBeanLocal, DbPatientBeanLocal}
import javax.interceptor.Interceptors
import ru.korus.tmis.core.logging.LoggingInterceptor
import javax.ejb.{EJB, TransactionAttributeType, TransactionAttribute, Stateless}
import grizzled.slf4j.Logging
import javax.persistence.{EntityManager, PersistenceContext}
import ru.korus.tmis.core.entity.model.{Mkb, Action, Diagnostic, Diagnosis}
import ru.korus.tmis.core.exception.CoreException
import ru.korus.tmis.core.auth.AuthData
import java.util.Date
import scala.collection.JavaConversions._
import ru.korus.tmis.scala.util.{I18nable, ConfigManager}

/**
 * Методы для работы с таблицей Diagnosis
 * @author Ivan Dmitriev
 */
@Interceptors(Array(classOf[LoggingInterceptor]))
@Stateless
class DbDiagnosisBean  extends DbDiagnosisBeanLocal
                          with Logging
                          with I18nable {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  @EJB
  var appLock: AppLockBeanLocal = _

  @EJB
  var dbPatientBean: DbPatientBeanLocal = _

  @EJB
  var dbEventPersonBean: DbEventPersonBeanLocal = _

  @EJB
  var dbRbDiagnosisTypeBean: DbRbDiagnosisTypeBeanLocal = _

  @EJB
  var dbMKBBean: DbMkbBeanLocal = _

  //@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
  def getDiagnosisById (id: Int) = {
    val result =  em.createQuery(DiagnosisByIdQuery, classOf[Diagnosis])
                    .setParameter("id", id)
                    .getResultList

    result.size match {
      case 0 => {
        throw new CoreException( ConfigManager.ErrorCodes.DiagnosisNotFound,
                                 i18n("error.diagnosisNotFound").format(id))
      }
      case size => {
        result.foreach(em.detach(_))
        result(0)
      }
    }
  }

  def insertOrUpdateDiagnosis(id: Int,
                              clientId: Int,
                              diagnosisTypeFlatCode: String,
                              diseaseCharacterId: Int,
                              mkbId: Int,
                              userData: AuthData) = {

    val now = new Date()
    var diagnosis: Diagnosis = null
    var oldDiagnosis: Diagnosis = null
    var lockId: Int = 0

    if (id>0) {
      diagnosis = getDiagnosisById(id)
      oldDiagnosis = Diagnosis.clone(diagnosis)
      lockId = appLock.acquireLock("Diagnosis", diagnosis.getId.intValue(), oldDiagnosis.getId.intValue(), userData)
    }
    else {
      diagnosis = new Diagnosis()

      diagnosis.setCreateDatetime(now)
      diagnosis.setCreatePerson(userData.getUser)
      diagnosis.setMkbExCode("")
    }

    try {


      val client = dbPatientBean.getPatientById(clientId)
      val diagnosisType = dbRbDiagnosisTypeBean.getRbDiagnosisTypeByFlatCode(diagnosisTypeFlatCode)
      var mkb :Mkb = null
      try {
        mkb = dbMKBBean.getMkbById(mkbId)
      } catch {
        case e: Exception => mkb = null
      }

      if (client==null || diagnosisType==null || mkb==null){
        diagnosis = null
      }
      else {
        diagnosis.setModifyDatetime(now)
        diagnosis.setModifyPerson(userData.getUser)
        diagnosis.setPatient(client)
        diagnosis.setDiagnosisType(diagnosisType)
        diagnosis.setCharacterId(diseaseCharacterId)
        diagnosis.setMkb(mkb)
        diagnosis.setPerson(userData.getUser)
        diagnosis.setEndDate(now)
      }
    }
    finally {
      if(lockId>0)
        appLock.releaseLock(lockId)
    }
    diagnosis
  }

  val DiagnosisByIdQuery = """
    SELECT dias
    FROM Diagnosis dias
    WHERE
      dias.id = :id
  """
}
