package ru.korus.tmis.core.database

import javax.interceptor.Interceptors

import javax.ejb.Stateless
import grizzled.slf4j.Logging
import javax.persistence.{EntityManager, PersistenceContext}
import ru.korus.tmis.core.entity.model.layout.LayoutAttribute
import scala.collection.JavaConversions._
import ru.korus.tmis.scala.util.I18nable

@Stateless
class DbLayoutAttributeBean extends DbLayoutAttributeBeanLocal
                            with Logging
                            with I18nable {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  def getAllLayoutAttributes = {
    val attributes = em.createNamedQuery("LayoutAttribute.findAll", classOf[LayoutAttribute]).getResultList

    attributes
  }
}
