package ru.korus.tmis.core.database

import common.DbOrgStructureBeanLocal
import javax.interceptor.Interceptors
import javax.ejb.{EJB, Stateless}
import grizzled.slf4j.Logging
import ru.korus.tmis.core.logging.LoggingInterceptor
import javax.persistence.{EntityManager, PersistenceContext}
import java.util.Date
import ru.korus.tmis.core.entity.model.{Mkb, Patient, Staff, ClientQuoting, Event}
import scala.collection.JavaConversions._
import ru.korus.tmis.core.exception.CoreException
import javax.swing.table.TableModel
import ru.korus.tmis.util.reflect.TmisLogging
import ru.korus.tmis.scala.util.{I18nable, ConfigManager}
import scala.language.reflectiveCalls

/**
 * Класс с методами для работы с таблицей s11r64.Client_Quoting
 * @author mmakankov
 * @since 1.0.0.48
 * @see DbClientQuotingBeanLocal
 */

@Interceptors(Array(classOf[LoggingInterceptor]))
@Stateless
class DbClientQuotingBean
  extends DbClientQuotingBeanLocal
  with Logging
  with I18nable
  with TmisLogging {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  @EJB
  var dbRbQuotaStatus: DbRbQuotaStatusBeanLocal = _

  @EJB
  var dbQuotaType: DbQuotaTypeBeanLocal = _

  @EJB
  var dbOrgStructure: DbOrgStructureBeanLocal = _

  val ClientQuotingFindQuery = """
    SELECT cq
    FROM
      ClientQuoting cq
    WHERE
      cq.id = :id
                                """

  def getClientQuotingById(id: Int): ClientQuoting = {
    val result = em.createQuery(ClientQuotingFindQuery,
      classOf[ClientQuoting])
      .setParameter("id", id)
      .getResultList

    result.size match {
      case 0 => {
        throw new CoreException(
          ConfigManager.ErrorCodes.ClientQuotingNotFound,
          i18n("error.clientQuotingNotFound").format(id))
      }
      case size => {
        result.foreach(rbType => {
          em.detach(rbType)
        })
        result(0)
      }
    }
  }

  def insertOrUpdateClientQuoting(id: Int,
                                  version: Int,
                                  rbQuotaTypeId: Int,
                                  quotaStatusId: Int,
                                  orgStructureId: Int,
                                  appealNumber: String,
                                  talonNumber: String,
                                  stage: Int,
                                  request: Int,
                                  mkb: Mkb,
                                  patient: Patient,
                                  event: Event,
                                  sessionUser: Staff): ClientQuoting = {

    var cq: ClientQuoting = null
    val now = new Date
    if (id > 0) {
      cq = getClientQuotingById(id)
      cq.setVersion(version)
    }
    else {
      cq = new ClientQuoting
      cq.setCreatePerson(sessionUser)
      cq.setCreateDatetime(now)
      cq.setMaster(patient)
      //нотнул йопта
      cq.setDateEnd(now)
      cq.setDateRegistration(now)
      cq.setDirectionDate(now)
      cq.setPacientModelId(0)
      cq.setTreatmentId(0)
    }

    cq.setIdentifier(appealNumber)
    cq.setQuotaTicket(talonNumber)
    cq.setStage(stage)
    cq.setRequest(request)
    cq.setMkb(mkb)
    val qwas = dbQuotaType.getQuotaTypeById(rbQuotaTypeId)
    cq.setQuotaType(qwas)
    cq.setOrgStructure(dbOrgStructure.getOrgStructureById(orgStructureId))
    cq.setStatus(dbRbQuotaStatus.getRbQuotaStatusById(quotaStatusId))

    cq.setDeleted(false)
    cq.setModifyPerson(sessionUser)
    cq.setModifyDatetime(now)
    cq.setEvent(event)
    cq
  }

  def deleteClientQuoting(id: Int,
                          sessionUser: Staff) = {
    val cq = getClientQuotingById(id)
    val now = new Date
    cq.setDeleted(true)
    cq.setModifyPerson(sessionUser)
    cq.setModifyDatetime(now)
  }

  def getAllClientQuotingForPatient (patientId: Int, page: Int, limit: Int, sortingField: String, sortingMethod: String, filter: Object, records: (java.lang.Long) => java.lang.Boolean) = {
    //var sortField = ""
    //if (sortingField.compareTo("eventId") == 0) {sortField = "event.id"} else {sortField = sortingField}
    val sorting = "ORDER BY %s %s".format(sortingField, sortingMethod)

    if (records!=null) records(em.createQuery(AllClientQuotingForPatientFindQuery.format("count(cq)", ""), classOf[Long])
      .setParameter("patientId", patientId)
      .getSingleResult)//Перепишем количество записей для структуры

    var typed = em.createQuery(AllClientQuotingForPatientFindQuery.format("cq", sorting), classOf[ClientQuoting])
      .setParameter("patientId", patientId)
      .setMaxResults(limit)
      .setFirstResult(limit * page)
    val result = typed.getResultList

    result.size match {
      case 0 => {
        logTmis.setLoggerType(logTmis.LoggingTypes.Debug)
        logTmis.warning("code " + ConfigManager.ErrorCodes.ClientQuotingAllNotFound + ": " + i18n("error.clientQuotingAllNotFound"))
      }
      case size => {
        result.foreach(rbType => {
          em.detach(rbType)
        })
      }
    }
    result
  }

  val AllClientQuotingForPatientFindQuery = """
    SELECT DISTINCT %s
    FROM
      ClientQuoting cq
    WHERE
      cq.master.id = :patientId
    AND
      cq.deleted = 0
    %s
                                            """
}


