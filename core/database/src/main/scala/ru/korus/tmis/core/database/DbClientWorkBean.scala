package ru.korus.tmis.core.database

import java.util.Date
import javax.ejb.{EJB, Stateless}
import javax.persistence.{EntityManager, PersistenceContext}

import ru.korus.tmis.core.entity.model.{ClientWork, Patient, Staff}
import ru.korus.tmis.core.exception.NoSuchEntityException
import ru.korus.tmis.scala.util.{ConfigManager, I18nable}

import scala.collection.JavaConversions._
import scala.language.reflectiveCalls

@Stateless
class DbClientWorkBean
  extends DbClientWorkBeanLocal
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
      case 0 =>  throw new NoSuchEntityException(
        ConfigManager.ErrorCodes.ClientSocStatusNotFound,
        id,
        i18n("error.ClientSocStatusNotFound"))
      case size => result.iterator.next
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
      cw = getClientWorkById(id)
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
