package ru.korus.tmis.core.database

import ru.korus.tmis.core.entity.model.DbVersions



import javax.ejb.Stateless
import javax.interceptor.Interceptors
import javax.persistence.{EntityManager, PersistenceContext}

import scala.collection.JavaConversions._
import ru.korus.tmis.core.entity.model.DbVersions


@Stateless
class DbVersionBean
  extends DbVersionBeanLocal
 {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  def getGlobalVersion: String = {
    em
      .createNamedQuery("DbVersions.findAll", classOf[DbVersions])
      .getResultList
      .map(_.getVersion.toString)
      .reduceLeft(_ + ":" + _)
  }
}
