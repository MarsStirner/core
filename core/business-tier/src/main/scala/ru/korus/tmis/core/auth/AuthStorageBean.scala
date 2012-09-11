package ru.korus.tmis.core.auth

import ru.korus.tmis.core.database.DbStaffBeanLocal
import ru.korus.tmis.core.exception.NoSuchUserException
import ru.korus.tmis.core.logging.db.LoggingInterceptor
import ru.korus.tmis.util.{I18nable, ConfigManager}

import grizzled.slf4j.Logging
import java.util.Date
import java.util.LinkedHashMap
import java.util.Map
import javax.annotation.{PostConstruct, Resource}
import javax.ejb._
import javax.interceptor.Interceptors

import scala.collection.JavaConversions._

@Interceptors(Array(classOf[LoggingInterceptor]))
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
@Singleton
class AuthStorageBean
  extends AuthStorageBeanLocal
  with Logging
  with I18nable {

  @EJB
  var dbStaff: DbStaffBeanLocal = _

  @Resource
  var timerService: TimerService = _

  // Отображение токена в кортеж из данных аутентификации и даты окончания
  // срока действия токена
  val authMap: Map[AuthToken, Tuple2[AuthData, Date]] = new LinkedHashMap()

  @PostConstruct
  def init() = {
    // Таймер для удаления токенов с истекшим сроком действия
    val timer = timerService.createIntervalTimer(
      ConfigManager.TmisAuth.AuthTokenPeriod,
      ConfigManager.TmisAuth.AuthTokenPeriod,
      null)
  }

  @Lock(LockType.WRITE)
  def createToken(login: String, password: String, roleId: Int): AuthData = {
    // Пытаемся получить сотрудника по логину
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

  @Timeout
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
}
