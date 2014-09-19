package ru.korus.tmis.core.auth

import ru.korus.tmis.core.database.DbStaffBeanLocal
import ru.korus.tmis.core.logging.LoggingInterceptor

import grizzled.slf4j.Logging
import java.util.Date
import javax.ejb._
import javax.interceptor.Interceptors

import scala.collection.JavaConversions._
import javax.servlet.http.HttpServletRequest
import scala.None
import ru.korus.tmis.core.exception.{AuthenticationException, NoSuchUserException}
import ru.korus.tmis.util.reflect.TmisLogging
import ru.korus.tmis.scala.util.{I18nable, ConfigManager}
import java.util

@Interceptors(Array(classOf[LoggingInterceptor]))
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
@Singleton
class AuthStorageBean
  extends AuthStorageBeanLocal
  with Logging
  with TmisLogging
  with I18nable {

  @EJB
  var dbStaff: DbStaffBeanLocal = _

  // Отображение токена в кортеж из данных аутентификации и даты окончания
  // срока действия токена
  val authMap: util.Map[AuthToken, (AuthData, Date)] = new util.LinkedHashMap()


  @Lock(LockType.WRITE)
  def createToken(login: String, password: String, roleId: Int): AuthData = {

    logTmis.setValueForKey(logTmis.LoggingKeys.Login, login, logTmis.StatusKeys.Success)
    logTmis.setValueForKey(logTmis.LoggingKeys.Password, password, logTmis.StatusKeys.Success)
    logTmis.setValueForKey(logTmis.LoggingKeys.Role, roleId.toString, logTmis.StatusKeys.Success)
    // Пытаемся получить сотрудника по л огину
    val staff = dbStaff.getStaffByLogin(login)

    // Получаем роли сотрудника
    val roles = getRoles(login, password)

    // Проверяем, обладает ли сотрудник запрашиваемой ролью
    val staffRole = roles.find(_.getId == roleId)

    // Если роль не найдена в БД, отказываем
    val initRole = staffRole match {
      case None => {
        throw new NoSuchUserException(
          ConfigManager.TmisAuth.ErrorCodes.RoleNotAllowed,
          login,
          i18n("error.roleNotAllowed").format(login))
      }
      case Some(r) => r
    }

    val userId: Int = staff.getId.intValue
    val userFirstName = staff.getFirstName
    val userLastName = staff.getLastName
    val userPatrName = staff.getPatrName
    val userSpeciality = staff.getSpeciality match {
      case null => ""
      case specs => specs.getName
    }

    val tokenStr = createToken(userId,
      initRole.getId.intValue,
      staff.getFullName,
      userSpeciality)

    val authData = new AuthData(
      new AuthToken(tokenStr),
      staff,
      userId,
      initRole,
      userFirstName,
      userLastName,
      userPatrName,
      userSpeciality
    )

    val tokenEndTime =
      new Date(new Date().getTime + ConfigManager.TmisAuth.AuthTokenPeriod)

    authMap.put(authData.authToken, (authData, tokenEndTime))

    authData
  }

  @Lock(LockType.READ)
  def getRoles(login: String, password: String) = {
    // Пытаемся получить сотрудника по логину
    val staff = dbStaff.getStaffByLogin(login)

    // Проверяем пароль
    // Пароли в базе хранятся в MD5 и по сети передаются тоже в MD5
    if (staff.getPassword != password) {
      error("Incorrect password for: " + staff)
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
                  speciality: String) = {
    val tokenStr = userId + role + userFullName + speciality +
      new Date().getTime.toString
    tokenStr.hashCode.toHexString
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

  def checkTokenCookies(srvletRequest: HttpServletRequest): AuthData = {
    //проверим, пришли ли куки
    val cookiesArray = srvletRequest.getCookies //srvletRequest
    if (cookiesArray == null) {
      error("No authentication data found")
      throw new AuthenticationException(
        ConfigManager.TmisAuth.ErrorCodes.InvalidToken,
        i18n("error.invalidToken"))
    }

    var token: String = null
    for (i <- 0 to cookiesArray.length - 1) {
      val cookieName = cookiesArray(i).getName
      if (cookieName.compareTo("authToken") == 0) {
        token = cookiesArray(i).getValue
      }
    }
    var authData: AuthData = null
    if (token != null) {
      var authToken: AuthToken = new AuthToken(token)
      //данные об авторизации

      authData = this.getAuthData(authToken)
      if (authData != null) {
        info("Authentication data found: " + authData)
      } else {
        error("No authentication data found")
        throw new AuthenticationException(
          ConfigManager.TmisAuth.ErrorCodes.InvalidToken,
          i18n("error.invalidToken"))
      }
      //валидность сертификата по времени
      var tokenEndDate: Date = null
      tokenEndDate = this.getAuthDateTime(authToken)
      if (tokenEndDate != null) {
        if (tokenEndDate.before(new Date())) {
          error("Token period exceeded")
          throw new AuthenticationException(
            ConfigManager.TmisAuth.ErrorCodes.InvalidToken,
            i18n("error.tokenExceeded"))
        } else {
          info("Token is valid")
          val tokenEndTimeNew = new Date(new Date().getTime + ConfigManager.TmisAuth.AuthTokenPeriod)
          authMap.remove(authToken)
          authMap.put(authToken, (authData, tokenEndTimeNew))
        }
      } else {
        error("Token end date not found")
        throw new AuthenticationException(
          ConfigManager.TmisAuth.ErrorCodes.InvalidToken,
          i18n("error.invalidToken"))
      }
      //currentUser.login(new TmisShiroToken(authData))
      logTmis.setValueForKey(logTmis.LoggingKeys.User, authData.getUser.getId, logTmis.StatusKeys.Success)
      logTmis.setValueForKey(logTmis.LoggingKeys.Role, authData.getUserRole.getId, logTmis.StatusKeys.Success)
    }
    else {
      throw new AuthenticationException(ConfigManager.TmisAuth.ErrorCodes.InvalidToken, i18n("error.invalidToken"))
    }
    authData
  }
}
