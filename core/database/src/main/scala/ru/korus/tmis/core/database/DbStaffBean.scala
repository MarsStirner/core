package ru.korus.tmis.core.database

import ru.korus.tmis.core.logging.db.LoggingInterceptor
import ru.korus.tmis.util.{I18nable, ConfigManager}

import grizzled.slf4j.Logging
import javax.ejb.Stateless
import javax.interceptor.Interceptors
import javax.persistence.{EntityManager, PersistenceContext}
import ru.korus.tmis.core.exception.NoSuchUserException
import ru.korus.tmis.core.entity.model.Staff

@Interceptors(Array(classOf[LoggingInterceptor]))
@Stateless
class DbStaffBean
  extends DbStaffBeanLocal
  with Logging
  with I18nable {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  def getStaffByLogin(login: String) = {
    val staffs = em.createNamedQuery("Staff.findByLogin", classOf[Staff])
                 .setParameter("login", login)
                 .getResultList
    if (staffs.size() == 0) {
      error("Staff not found: " + login)
      throw new NoSuchUserException(
        ConfigManager.TmisAuth.ErrorCodes.LoginIncorrect,
        login,
        i18n("error.staffNotFound"))
    }

    val staff = staffs.iterator.next()
    em.detach(staff)
    staff
  }
}
