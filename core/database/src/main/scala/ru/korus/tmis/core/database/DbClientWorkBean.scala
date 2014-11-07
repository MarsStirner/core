package ru.korus.tmis.core.database

import javax.persistence.{PersistenceContext, EntityManager}
import javax.ejb.{Stateless, EJB}
import ru.korus.tmis.core.logging.LoggingInterceptor
import javax.interceptor.Interceptors
import grizzled.slf4j.Logging
import ru.korus.tmis.core.exception.NoSuchEntityException
import java.util.Date
import ru.korus.tmis.core.entity.model.{Staff, Patient, ClientWork}
import scala.collection.JavaConversions._
import ru.korus.tmis.core.entity.model.fd.FDRecord
import ru.korus.tmis.scala.util.{I18nable, ConfigManager}
import scala.language.reflectiveCalls

@Interceptors(Array(classOf[LoggingInterceptor]))
@Stateless
class DbClientWorkBean
  extends DbClientWorkBeanLocal
  with Logging
  with I18nable {


  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  @EJB
  var dbFDRecord: DbFDRecordBeanLocal = _


  val FindByIdQuery = """
    SELECT t
    FROM
      ClientWork t
    WHERE
      t.id = :id
                      """

  def getClientWorkById(id: Int) = {
    val result = em.createQuery(FindByIdQuery,
      classOf[ClientWork])
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
        result.foreach((r) => {
          em.detach(r)
        })
        result(0)
      }
    }
  }

  def insertOrUpdateClientWork(
                                id: Int,
                                patient: Patient,
                                freeInput: String,
                                post: String,
                                rankId: Int,
                                armId: Int,
                                sessUser: Staff) = {

    var cw: ClientWork = null
    val now: Date = new Date
    if (id > 0) {
      cw = getClientWorkById(id);
    } else {
      cw = new ClientWork()
      cw.setCreateDatetime(now)
      cw.setCreatePersonid(sessUser.getId)
    }
    cw.setModifyPersonid(sessUser.getId)
    cw.setModifyDatetime(now)
    cw.setDeleted(false)

    if (freeInput != null) {
      cw.setFreeInput(freeInput)
    } else {
      cw.setFreeInput("")
    }
    if (post != null) {
      cw.setPost(post)
    } else {
      cw.setPost("")
    }

    cw.setArmId(armId)
    cw.setRankId(rankId)
    /*
    if (rankId > 0) {
      cw.setRankId(dbFDRecord.getFDRecordById(rankId))
    } //else { cw.setRankId(new FDRecord()) }
    else {cw.setRankId(dbFDRecord.getFDRecordById(1))}
    if (armId > 0) {
      cw.setArmId(dbFDRecord.getFDRecordById(armId))
    } //else { cw.setArmId(new FDRecord()) }
    else {cw.setArmId(dbFDRecord.getFDRecordById(1))}
      */
    cw.setOkved("")
    cw.setPatient(patient)

    cw
  }

  def deleteClientWork(id: Int, sessionUser: Staff) = {
    val d = getClientWorkById(id)
    val now = new Date
    d.setDeleted(true)
    d.setModifyDatetime(now)
  }
}
