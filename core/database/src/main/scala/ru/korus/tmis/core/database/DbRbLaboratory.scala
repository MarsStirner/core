package ru.korus.tmis.core.database

import javax.ejb.{LocalBean, Stateless}
import javax.persistence.{QueryTimeoutException, EntityManager, PersistenceContext}
import java.util

import ru.korus.tmis.core.entity.model.RbLaboratory

/**
 * Author: <a href="mailto:alexey.kislin@gmail.com">Alexey Kislin</a>
 * Date: 10/7/14
 * Time: 1:06 PM
 */
@Stateless
@LocalBean
class DbRbLaboratory {

  @PersistenceContext(unitName = "s11r64")
  private var em: EntityManager = _

  @throws[QueryTimeoutException]
  @throws[Exception]
  def getAllLabs: util.List[RbLaboratory] = {
    em.createNamedQuery("RbLaboratory.findAll", classOf[RbLaboratory]).getResultList
  }

}
