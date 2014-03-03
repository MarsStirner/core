package ru.korus.tmis.core.database

import ru.korus.tmis.core.entity.model.RbPrintTemplate
import javax.ejb.Stateless
import javax.persistence.{EntityManager, PersistenceContext}
import scala.collection.JavaConversions._

/**
 * Author: <a href="mailto:alexey.kislin@gmail.com">Alexey Kislin</a>
 * Date: 2/21/14
 * Time: 8:33 PM
 */
@Stateless
class DbRbPrintTemplateBean extends DbRbPrintTemplateBeanLocal {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  def getRbPrintTemplateByIds(ids: Array[Int]): List[RbPrintTemplate] = {
    val l = em
      .createNamedQuery("RbPrintTemplate.findByIds", classOf[RbPrintTemplate])
      .setParameter("values", asJavaCollection(ids))
      .getResultList.toList
    l.foreach(em.detach(_))
    l
  }

  def getRbPrintTemplatesByContexts(contexts: Array[String]): List[RbPrintTemplate] = {
    val l = em
      .createNamedQuery("RbPrintTemplate.findByContexts", classOf[RbPrintTemplate])
      .setParameter("values", asJavaCollection(contexts))
      .getResultList.toList
    l.foreach(em.detach(_))
    l
  }

}
