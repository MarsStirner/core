package ru.korus.tmis.core.database

import ru.korus.tmis.core.logging.db.LoggingInterceptor

import javax.ejb.Stateless
import javax.interceptor.Interceptors
import javax.persistence.{EntityManager, PersistenceContext}

import grizzled.slf4j.Logging
import ru.korus.tmis.core.entity.model.Organisation

@Interceptors(Array(classOf[LoggingInterceptor]))
@Stateless
class DbOrganizationBean
  extends DbOrganizationBeanLocal
  with Logging {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  def getAllOrganizations() = {
    em.createNamedQuery("Organisation.findAll", classOf[Organisation]).getResultList
  }
}
