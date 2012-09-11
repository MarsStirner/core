package ru.korus.tmis.core.database

import ru.korus.tmis.core.logging.db.LoggingInterceptor

import grizzled.slf4j.Logging
import javax.ejb.Stateless
import javax.interceptor.Interceptors
import javax.persistence.{EntityManager, PersistenceContext}

import scala.collection.JavaConversions._
import ru.korus.tmis.core.entity.model.DbVersions

@Interceptors(Array(classOf[LoggingInterceptor]))
@Stateless
class DbVersionBean
  extends DbVersionBeanLocal
  with Logging {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  def getGlobalVersion = {
    em
    .createNamedQuery("DbVersions.findAll", classOf[DbVersions])
    .getResultList
    .map(_.getVersion.toString)
    .reduceLeft(_ + ":" + _)
  }
}
