package ru.korus.tmis.core.database

import javax.interceptor.Interceptors

import javax.ejb.Stateless
import grizzled.slf4j.Logging
import javax.persistence.{EntityManager, PersistenceContext}
import ru.korus.tmis.core.entity.model.layout.LayoutAttributeValue
import scala.collection.JavaConversions._
import ru.korus.tmis.scala.util.I18nable


@Stateless
class DbLayoutAttributeValueBean extends DbLayoutAttributeValueBeanLocal
                                 with Logging
                                 with I18nable {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  def getLayoutAttributeValuesByActionPropertyTypeId (aptId: Int) = {
    val values = em.createNamedQuery("LayoutAttributeValue.findByActionPropertyTypeId", classOf[LayoutAttributeValue])
                       .setParameter("id", aptId)
                       .getResultList

    values
  }
}
