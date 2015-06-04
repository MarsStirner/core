package ru.korus.tmis.core.auth

import ru.korus.tmis.core.database.common.DbSettingsBeanLocal
import ru.korus.tmis.core.database.{AppLockStatusType, AppLockStatus, AppLockBeanLocal, DbStaffBeanLocal}


import grizzled.slf4j.Logging
import java.util.Date
import javax.ejb._
import javax.interceptor.Interceptors

import ru.korus.tmis.util.TextUtils

import scala.collection.JavaConversions._
import javax.servlet.http.Cookie
import ru.korus.tmis.core.exception.{CoreException, AuthenticationException, NoSuchUserException}

import ru.korus.tmis.scala.util.{I18nable, ConfigManager}
import java.util
import java.lang
import ru.korus.tmis.core.entity.model._
import ru.korus.tmis.core.lock.{EntityLockInfo, ActionWithLockInfo}
import scala.language.reflectiveCalls


@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
@Singleton
class AuthStorageBean
  extends AuthStorageBeanLocal
  with Logging
  with I18nable {

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
  val authMap: util.Map[AuthToken, (AuthData, Date)] = new util.LinkedHashMap()

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
        throw new NoSuchUserException(
          ConfigManager.TmisAuth.ErrorCodes.RoleNotAllowed,
          login,
          i18n("error.roleNotAllowed").format(login))
      case Some(r) => r
    }

    // Пытаемся получить сотрудника по л огину
    val staff = dbStaff.getStaffByLogin(login)

    val userSpeciality = staff.getSpeciality match {
      case null => ""
      case specs => specs.getName
    }

    val tokenStr = createToken(staff.getId.intValue,
      initRole.getId.intValue,
      staff.getFullName,
      userSpeciality,
      login,
      password)


    val authData: AuthData = putToken(tokenStr, staff, initRole)

    authData
  }

  def putToken(tokenStr: String, staff: Staff, initRole: Role): AuthData = {
    val authData = new AuthData(
      new AuthToken(tokenStr),
      staff,
      staff.getId.intValue,
      initRole,
      staff.getFirstName,
      staff.getLastName,
      staff.getPatrName,
      staff.getSpeciality match {
        case null => ""
        case specs => specs.getName
      }
    )

    val tokenEndTime =
      new Date(new Date().getTime + getAuthTokenLifeTime)

    authMap.put(authData.authToken, (authData, tokenEndTime))
    authData
  }

  @Lock(LockType.READ)
  def getRoles(login: String, password: String) = {
    timeoutHandler();
    // Пытаемся получить сотрудника по логину
    if(ConfigManager.Cas.isActive && casBeanLocal.createToken(login, password, "").isEmpty) {
      throw new NoSuchUserException(
        ConfigManager.TmisAuth.ErrorCodes.LoginIncorrect,
        login,
        i18n("error.staffNotFound"))
    }
    val staff = dbStaff.getStaffByLogin(login)

    // Проверяем пароль
    // Пароли в базе хранятся в MD5 и по сети передаются тоже в MD5
    if (staff.getPassword != password && staff.getPassword != TextUtils.getMD5(password)) {
      warn("Incorrect password for: " + staff)
      throw new NoSuchUserException(
        ConfigManager.TmisAuth.ErrorCodes.LoginIncorrect,
        login,
        i18n("error.loginIncorrect").format(login))
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

  @Lock(LockType.READ)
  def createToken(userId: Int,
                  role: Int,
                  userFullName: String,
                  speciality: String,
                  login: String,
                  password: String) = {
    val tokenStr = userId + role + userFullName + speciality + new Date().getTime.toString
    casBeanLocal.createToken(login, password, tokenStr.hashCode.toHexString)
  }

  @Lock(LockType.WRITE)
  def timeoutHandler() = {
    val now = new Date()
    info("Handler called at: " + now)

    // Находим и удаляем устаревшие токены
    authMap.filter((tuple) => tuple._2._2.before(now)).foreach(
      (tuple) => {
        info("Outdated token " + tuple._1 + " removed at: " + now)
        authMap.remove(tuple._1)
      }
    )
  }

  def checkTokenCookies(cookies: lang.Iterable[Cookie]): AuthData = {
    //проверим, пришли ли куки
    if (cookies == null) {
      warn("No authentication data found")
      throw new AuthenticationException(
        ConfigManager.TmisAuth.ErrorCodes.InvalidToken,
        i18n("error.invalidToken"))
    }
    val cookiesArray = cookies.toArray

    var token: String = null
    var curRole: String = null
    for (i <- 0 to cookiesArray.length - 1) {
      val cookieName = cookiesArray(i).getName
      if (cookieName.compareTo("authToken") == 0) {
        token = cookiesArray(i).getValue
      }
      if (cookieName.compareTo("currentRole") == 0) {
        curRole = cookiesArray(i).getValue
      }
    }
    var authData: AuthData = null
    if (token != null) {
      val casResp: CasResp = casBeanLocal.checkToken(token)
      val isCasTokenValid: Boolean = casResp.getSuccess
      val authToken: AuthToken = new AuthToken(token)
      //данные об авторизации
      authData = this.getAuthData(authToken)
      if (authData != null) {
        info("Authentication data found: " + authData)
        authData.setUser(dbStaff.getStaffById(authData.getUserId))
      } else if (isCasTokenValid) {
        val staff: Staff = dbStaff.getStaffById(casResp.getUser_id)
        authData = putToken(token, staff, staff.getRoles.find(_.getCode == curRole).getOrElse(null))
      } else {
        warn("No authentication data found")
        throw new AuthenticationException(
          ConfigManager.TmisAuth.ErrorCodes.InvalidToken,
          i18n("error.invalidToken"))
      }
      //валидность сертификата по времени
      var tokenEndDate: Date = null
      tokenEndDate = this.getAuthDateTime(authToken)
      if (tokenEndDate != null) {
        authMap.remove(authToken)
        if (tokenEndDate.before(new Date()) || !isCasTokenValid) {
          warn("Token period exceeded")
          throw new AuthenticationException(
            ConfigManager.TmisAuth.ErrorCodes.InvalidToken,
            i18n("error.tokenExceeded"))
        } else {
          info("Token is valid")
          val tokenEndTimeNew = new Date(new Date().getTime + getAuthTokenLifeTime)
          authMap.put(authToken, (authData, tokenEndTimeNew))
        }
      } else {
        warn("Token end date not found")
        throw new AuthenticationException(
          ConfigManager.TmisAuth.ErrorCodes.InvalidToken,
          i18n("error.invalidToken"))
      }
    }
    else {
      throw new AuthenticationException(ConfigManager.TmisAuth.ErrorCodes.InvalidToken, i18n("error.invalidToken"))
    }
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
    if (lockMap.get(appLock) == null) {
      null
    } else {
      for (res <- lockMap.keySet()) {
        if (res.equals(appLock)) {
          return res
        }
      }
      null
    }
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
