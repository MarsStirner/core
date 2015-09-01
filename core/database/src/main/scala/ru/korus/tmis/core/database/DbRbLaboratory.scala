package ru.korus.tmis.core.database

import javax.ejb.{LocalBean, Stateless}
import javax.persistence.{QueryTimeoutException, EntityManager, PersistenceContext}
import java.util
import scala.collection.JavaConversions._

import ru.korus.tmis.core.entity.model.RbLaboratory

/**
 * Author: <a href="mailto:alexey.kislin@gmail.com">Alexey Kislin</a>
 * Date: 10/7/14
 * Time: 1:06 PM
 */
@Stateless
@LocalBean
class DbRbLaboratory {
  def getLabById(ids: util.List[String]): util.List[RbLaboratory] = {
    val intIds:util.ArrayList[Integer] = new util.ArrayList[Integer](ids.size())
    ids.foreach( current =>
      try{
        intIds.add(Integer.valueOf(current))
      } catch {
        case e:NumberFormatException => {}
      }
    )
    if(intIds.isEmpty || (intIds.size() == 1 && 0 == intIds.get(0))){
      return getAllLabs
    }
    return em.createNamedQuery("RbLaboratory.findAllByIds", classOf[RbLaboratory]).setParameter("ids", intIds).getResultList
  }


  @PersistenceContext(unitName = "s11r64")
  private var em: EntityManager = _

  @throws[QueryTimeoutException]
  @throws[Exception]
  def getAllLabs: util.List[RbLaboratory] = {
    em.createNamedQuery("RbLaboratory.findAll", classOf[RbLaboratory]).getResultList
  }

}
