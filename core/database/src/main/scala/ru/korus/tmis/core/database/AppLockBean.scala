package ru.korus.tmis.core.database

import ru.korus.tmis.core.auth.AuthData
import ru.korus.tmis.core.exception.CoreException
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

  def acquireLock(table: String,
                  recordId: Int,
                  recordIndex: Int,
                  userData: AuthData): Int = {

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
        throw new CoreException(i18n("error.entryIsLocked"))
      }
      case size => {
        val data = result.split(' ')
        val lockStatus = data(0)
        val lockId = data(1).toInt

        if (lockStatus == "0") {
          throw new CoreException(i18n("error.entryIsLocked"))
        } else {
          return lockId
        }
      }
    }
  }

  def releaseLock(lockId: Int) = {
    em.createNativeQuery(releaseAppLockCall)
      .setParameter(1, lockId)
      .executeUpdate()
    true
  }

  val getAppLockPrepareCall = "CALL getAppLock_prepare()"

  val releaseAppLockCall = "CALL ReleaseAppLock(?)"
}
