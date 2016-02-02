package ru.korus.tmis.core.auth

import java.{lang, util}
import java.util.Date
import javax.ejb._
import javax.servlet.http.Cookie

import org.slf4j.LoggerFactory
import ru.korus.tmis.core.database.common.DbSettingsBeanLocal
import ru.korus.tmis.core.database.{AppLockBeanLocal, AppLockStatus, AppLockStatusType, DbStaffBeanLocal}
import ru.korus.tmis.core.entity.model._
import ru.korus.tmis.core.exception.{AuthenticationException, CoreException, NoSuchUserException}
import ru.korus.tmis.core.lock.{ActionWithLockInfo, EntityLockInfo}
import ru.korus.tmis.scala.util.{ConfigManager, I18nable}
import ru.korus.tmis.util.TextUtils

import scala.collection.JavaConversions._
import scala.language.reflectiveCalls


@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
@Singleton
@DependsOn(Array("DbSettingsBean"))
class AuthStorageBean
  extends AuthStorageBeanLocal
  with I18nable {

  val logger: org.slf4j.Logger = LoggerFactory.getLogger(classOf[AuthStorageBean])

  @EJB
  var appLockBeanLocal: AppLockBeanLocal = _

  @EJB
  var dbStaff: DbStaffBeanLocal = _

  @EJB
  var dbSetting: DbSettingsBeanLocal = _

  @EJB
  var casBeanLocal: CasBeanLocal = _

  // Отображение токена в кортеж из данных аутентификации и даты окончания
  // срока действия токена
  val authMap: util.Map[AuthToken, (AuthData, Date)] = new util.concurrent.ConcurrentHashMap()

  val lockMap: util.Map[AppLockDetail, AuthToken] = new util.concurrent.ConcurrentHashMap()

  @Lock(LockType.WRITE)
  def createToken(login: String, password: String, roleId: Int): AuthData = {

    // Получаем роли сотрудника
    val roles = getRoles(login, password)

    // Проверяем, обладает ли сотрудник запрашиваемой ролью
    val staffRole = roles.find(_.getId == roleId)

    // Если роль не найдена в БД, отказываем
    val initRole = staffRole match {
      case None =>
        throw new NoSuchUserException(ConfigManager.TmisAuth.ErrorCodes.RoleNotAllowed, login, i18n("error.roleNotAllowed").format(login))
      case Some(r) => r
    }

    // Пытаемся получить сотрудника по логину
    val staff = dbStaff.getStaffByLogin(login)
    val tokenStr = casBeanLocal.createToken(login, password)
    val authData: AuthData = putToken(tokenStr, staff, initRole)
    authData
  }

  def putToken(token: CasResp, staff: Staff, initRole: Role): AuthData = {
    val authData = new AuthData(
      new AuthToken(token.getToken),
      staff,
      staff.getId.intValue,
      initRole,
      staff.getFirstName,
      staff.getLastName,
      staff.getPatrName,
      staff.getSpeciality match {
        case null => ""
        case specs => specs.getName
      },
      token.getDeadline.toLong,
      token.getTtl.toLong
    )

    val tokenEndTime = new Date(token.getDeadline.toLong*1000L)
    logger.debug("put token deadline {} date is {}", token.getDeadline.toLong*1000L, tokenEndTime)
    authMap.put(authData.authToken, (authData, tokenEndTime))
    authData
  }

  @Lock(LockType.READ)
  def getRoles(login: String, password: String) = {
    timeoutHandler()
    // Пытаемся получить сотрудника по логину
    if (!casBeanLocal.createToken(login, password).success) {
      throw new NoSuchUserException(
        ConfigManager.TmisAuth.ErrorCodes.LoginIncorrect,
        login,
        i18n("error.staffNotFound"))
    }
    val staff = dbStaff.getStaffByLogin(login)

    // Проверяем пароль
    // Пароли в базе хранятся в MD5 и по сети передаются тоже в MD5
    if (staff.getPassword != password && staff.getPassword != TextUtils.getMD5(password)) {
      logger.warn("Incorrect password for: {}", staff)
      throw new NoSuchUserException(ConfigManager.TmisAuth.ErrorCodes.LoginIncorrect, login, i18n("error.loginIncorrect").format(login))
    }

    // Получаем роли сотрудника
    staff.getRoles
  }

  @Lock(LockType.READ)
  def getAuthData(token: AuthToken) = {
    authMap.get(token) match {
      case null => null
      case pair => pair._1
    }
  }

  @Lock(LockType.READ)
  def getAuthDateTime(token: AuthToken) = {
    authMap.get(token) match {
      case null => null
      case pair => pair._2
    }
  }


  @Lock(LockType.WRITE)
  def timeoutHandler() = {
    val now = new Date()
    logger.info("Handler called at: " + now)

    // Находим и удаляем устаревшие токены
    authMap.filter((tuple) => tuple._2._2.before(now)).foreach(
      (tuple) => {
        logger.info("Outdated token " + tuple._1 + " removed at: " + now)
        authMap.remove(tuple._1)
      }
    )
  }

  @Lock(LockType.WRITE)
  override def checkToken(token: String, prolong: Boolean): AuthData = {
    var casResp: CasResp = null
    if(prolong){
      casResp = casBeanLocal.checkToken(token)
    } else {
      casResp = casBeanLocal.checkTokenWithoutProlong(token)
    }
    if(casResp.getSuccess){
      val authToken: AuthToken = new AuthToken(token)
      val authData : AuthData = getAuthData(authToken)
      if(authData != null){
        authData.setDeadline(casResp.getDeadline.toLong)
        authData.setTtl(casResp.getTtl.toLong)
        authMap.put(authToken, (authData, new Date(casResp.getDeadline.toLong*1000L)))
        return authData
      } else {
        logger.error("CAS RESPONSE IS SUCCESS, BUT WE HASN'T IT IN MAP!!!!!")
      }
    }
    throw new AuthenticationException(ConfigManager.TmisAuth.ErrorCodes.InvalidToken, i18n("error.invalidToken"))
  }

  def checkTokenCookies(cookies: Array[Cookie]): AuthData = {
    if(cookies == null){
      throw new AuthenticationException(ConfigManager.TmisAuth.ErrorCodes.InvalidToken, i18n("error.invalidToken"))
    }
    var token: String = null
    var curRole: String = null
    for (cookie <- cookies) {
      val cookieName = cookie.getName
      if ("authToken".equalsIgnoreCase(cookieName)) {
        token = cookie.getValue
      } else if("currentRole".equalsIgnoreCase(cookieName)){
        curRole = cookie.getValue
      }
    }
    var authData: AuthData = null
    if (token == null) {
      throw new AuthenticationException(ConfigManager.TmisAuth.ErrorCodes.InvalidToken, i18n("error.invalidToken"))
    }
    val casResp: CasResp = casBeanLocal.checkToken(token)
    val isCasTokenValid: Boolean = casResp.getSuccess
    val authToken: AuthToken = new AuthToken(token)
    //данные об авторизации
    authData = this.getAuthData(authToken)
    if (authData != null) {
      authData.setDeadline(casResp.getDeadline.toLong)
      authData.setTtl(casResp.getTtl.toLong)
      logger.info("Authentication data found: " + authData)
    } else if (isCasTokenValid) {
      val staff: Staff = dbStaff.getStaffById(casResp.getUser_id)
      authData = putToken(casResp, staff, staff.getRoles.find(_.getCode == curRole).orNull)
    } else {
      logger.warn("No authentication data found")
      throw new AuthenticationException(ConfigManager.TmisAuth.ErrorCodes.InvalidToken, i18n("error.invalidToken"))
    }
    //валидность сертификата по времени
    authMap.remove(authToken)
    if (!isCasTokenValid) {
      logger.warn("Token period exceeded")
      throw new AuthenticationException(ConfigManager.TmisAuth.ErrorCodes.InvalidToken, i18n("error.tokenExceeded"))
    }
    logger.info("Token is valid")
    authMap.put(authToken, (authData, new Date(casResp.getDeadline.toLong*1000L)))
    authData
  }

  def clearAppLock() {
    val timeout: Date = new Date(new Date().getTime - ConfigManager.Common.lockTimeoutSec * 1000)
    lockMap.filter((tuple) => tuple._1.getAppLock.getRetTime.before(timeout)).foreach((tuple) =>
      releaseAppLock(tuple._2, tuple._1.getId.getTableName, tuple._1.getId.getRecordId)
    )
  }

  def getAppLock(token: AuthToken, tableName: String, id: Integer): AppLockDetail = {
    clearAppLock()
    val authData = getAuthData(token)
    if (authData == null) {
      throw new CoreException("Пользователь не найден. Токен: %s".format(token))
    }
    val appLockStatus: AppLockStatus = appLockBeanLocal.getAppLock(tableName, id, 0, authData)
    if (appLockStatus.getStatus == AppLockStatusType.busy) {
      throw new CoreException(i18n("error.entryIsLocked"))
    }
    var lock: AppLockDetail = null
    lockMap.synchronized {
      lock = checkAppLock(tableName, id)
      if (lock == null) {
        val lockNew = new AppLockDetail()
        val appLock: AppLock = new AppLock()
        lockNew.setAppLock(appLock)
        lockNew.setId(new AppLockDetailPK())
        val now = new Date()
        appLock.setId(appLockStatus.getId)
        appLock.setLockTime(now)
        appLock.setRetTime(now)
        appLock.setPerson(authData.getUser)
        lockNew.getId.setMasterId(appLockStatus.getId)
        lockNew.getId.setTableName(tableName)
        lockNew.getId.setRecordId(id)
        lockMap.put(lockNew, token)
        return lockNew
      }
    }
    appLockBeanLocal.releaseAppLock(appLockStatus.getId)
    throw new CoreException("Документ уже редактируется. ФИО: %s".format(lock.getAppLock.getPerson.getFullName))
  }

  def prolongAppLock(token: AuthToken, tableName: String, id: Integer): AppLockDetail = {
    lockMap.synchronized {
      val lock = checkAppLock(tableName, id)
      if (lock != null) {
        appLockBeanLocal.prolongAppLock(lock.getId.getMasterId)
        lock.getAppLock.setLockTime(new Date())
        return lock
      }
    }
    throw new CoreException("Запись не залочена. Таблица: %s id: %s".format(tableName, id))
  }

  def releaseAppLock(token: AuthToken, tableName: String, id: Integer) {
    lockMap.synchronized {
      val lock = checkAppLock(tableName, id)
      if (lock != null) {
        appLockBeanLocal.releaseAppLock(lock.getId.getMasterId)
        lockMap.remove(lock)
        return
      }
    }
    throw new CoreException("Запись не залочена. Таблица: %s id: %s".format(tableName, id))
  }

  /**
   * Проверка лока записи для редактирования
   * @param tableName - имя таблицы
   * @param id - ИД записи в таблицы
   * @return - информацию о локе,если запись редактируется в данный момент;
   *         <code>null</code>, если записль не залочена
   */
  def checkAppLock(tableName: String, id: Integer): AppLockDetail = {
    val appLock: AppLockDetail = new AppLockDetail(tableName, id)
    for (res <- lockMap.keySet()) {
      if (res.equals(appLock)) {
        return res
      }
    }
    null
  }

  def getLockInfo(action: Action): ActionWithLockInfo = {
    var res = appLockBeanLocal.getLockInfo(action)
    if (res.lockInfo == null) {
      val appLockDetail = checkAppLock("Action", action.getId)
      if (appLockDetail != null) {
        res = new ActionWithLockInfo(action, new EntityLockInfo(appLockDetail.getId.getMasterId.toInt, appLockDetail.getAppLock.getPerson, "NTK"))
      }
    }
    res
  }

  def acquireLock(table: String, recordId: Int, recordIndex: Int, userData: AuthData): Integer = {
    val appLockDetail: AppLockDetail = checkAppLock(table, recordId)
    if (appLockDetail == null) {
      val appLockStatus = appLockBeanLocal.getAppLock(table, recordId, recordIndex, userData)
      if (appLockStatus.getStatus != AppLockStatusType.lock) {
        throw new CoreException(i18n("error.entryIsLocked"))
      }
      return appLockStatus.getId
    }
    appLockDetail.getId.getMasterId
  }

  def releaseLock(id: Integer) {
    appLockBeanLocal.releaseAppLock(id)
  }

  def getAuthTokenLifeTime: Int = {
    ConfigManager.TmisAuth.AuthTokenPeriod
  }


}
