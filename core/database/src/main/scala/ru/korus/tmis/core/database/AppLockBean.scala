package ru.korus.tmis.core.database

import ru.korus.tmis.core.auth.AuthData
import ru.korus.tmis.core.logging.LoggingInterceptor

import grizzled.slf4j.Logging
import java.lang.String
import java.util.LinkedList
import javax.ejb.Stateless
import javax.interceptor.Interceptors
import javax.persistence.{EntityManager, PersistenceContext}
import org.eclipse.persistence.jpa.JpaHelper
import org.eclipse.persistence.queries.{ValueReadQuery, StoredProcedureCall}
import ru.korus.tmis.scala.util.I18nable
import ru.korus.tmis.core.entity.model.{AppLockDetail, AppLock, Action}
import ru.korus.tmis.core.lock.{EntityLockInfo, ActionWithLockInfo}
import scala.collection.JavaConversions._


@Interceptors(Array(classOf[LoggingInterceptor]))
@Stateless
class AppLockBean
  extends AppLockBeanLocal
  with Logging
  with I18nable {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  def prepare() = {
    em.createNativeQuery(getAppLockPrepareCall)
      .executeUpdate()
  }

  def getAppLock(table: String,
                 recordId: Int,
                 recordIndex: Int,
                 userData: AuthData): AppLockStatus = {

    prepare()

    val spc = new StoredProcedureCall
    spc.setProcedureName("getAppLock_")
    spc.addNamedArgument("aTableName", "aTableName")
    spc.addNamedArgument("aRecordId", "aRecordId")
    spc.addNamedArgument("aRecordIndex", "aRecordIndex")
    spc.addNamedArgument("aPersonId", "aPersonId")
    spc.addNamedArgument("aAddress", "aAddress")
    spc.addNamedOutputArgument("aResult", "aResult", classOf[String])

    val query = new ValueReadQuery(spc)
    query.addArgument("aTableName")
    query.addArgument("aRecordId")
    query.addArgument("aRecordIndex")
    query.addArgument("aPersonId")
    query.addArgument("aAddress")

    val args = new LinkedList[AnyRef]()
    args.add(table)
    args.add(Integer.valueOf(recordId))
    args.add(Integer.valueOf(recordIndex))
    args.add(Integer.valueOf(userData.doctor.id))
    args.add("MEDIPAD")

    val result = JpaHelper.getEntityManager(em)
      .getActiveSession
      .executeQuery(query, args)
      .asInstanceOf[String]

    result.size match {
      case 0 => {
        return new AppLockStatus(0, AppLockStatusType.busy)
        //throw new CoreException(i18n("error.entryIsLocked"))
      }
      case size => {
        val data = result.split(' ')
        val lockStatus = data(0)
        val lockId = data(1).toInt

        if (lockStatus == "0") {
          return new AppLockStatus(lockId, AppLockStatusType.alreadyLocked)
          //throw new CoreException(i18n("error.entryIsLocked"))
        } else {
          return new AppLockStatus(lockId, AppLockStatusType.lock)
        }
      }
    }
  }

  def releaseAppLock(lockId: Int) = {
    em.createNativeQuery(releaseAppLockCall)
      .setParameter(1, lockId)
      .executeUpdate()
    true
  }

  def prolongAppLock(lockId: Int) = {
    em.createNativeQuery(prolongAppLockCall)
      .setParameter(1, lockId)
      .executeUpdate()
    true
  }

  val getAppLockPrepareCall = "CALL getAppLock_prepare()"

  val releaseAppLockCall = "CALL ReleaseAppLock(?)"

  val prolongAppLockCall = "CALL prolongAppLock(?)"

  def getAppLock(action: Action): AppLockDetail = {
    //"WHERE al.id.tableName = :tableName AND al.id.recordId = :id AND al.appLock IS NOT NULL"
    val appLockDetailList = em.createNamedQuery("AppLockDetail.findLock", classOf[AppLockDetail])
      .setParameter("tableName", "Action")
      .setParameter("id", action.getId)
      .setMaxResults(1)
      .getResultList();
    for (a: AppLockDetail <- appLockDetailList) {
      if (em.find(classOf[AppLock], a.getId.getMasterId) != null) {
        return a
      }
    }
    return null;
  }

  def getLockInfo(action: Action): ActionWithLockInfo = {
    val appLockDetail = getAppLock(action)
    val lockInfo = if (appLockDetail == null) null else new EntityLockInfo(appLockDetail.getId.getMasterId.toInt, appLockDetail.getAppLock.getPerson, "NTK")
    val res: ActionWithLockInfo = new ActionWithLockInfo(action, lockInfo)
    res
  }

}
