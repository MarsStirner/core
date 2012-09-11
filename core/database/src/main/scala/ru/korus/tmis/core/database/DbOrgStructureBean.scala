package ru.korus.tmis.core.database

import ru.korus.tmis.core.logging.db.LoggingInterceptor
import ru.korus.tmis.core.entity.model.{OrgStructure, ActionType}


import grizzled.slf4j.Logging
import javax.ejb.Stateless
import javax.interceptor.Interceptors
import javax.persistence.{EntityManager, PersistenceContext}

import scala.collection.JavaConversions._

@Interceptors(Array(classOf[LoggingInterceptor]))
@Stateless
class DbOrgStructureBean
  extends DbOrgStructureBeanLocal
  with Logging {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  def getAllOrgStructures() = {
    em.createNamedQuery("OrgStructure.findAll", classOf[OrgStructure]).getResultList
  }

  def getActionTypeFilter(departmentId: Int) = {
    val result = em.createQuery(ActionTypeFilterByDepartmentIdQuery,
                                classOf[ActionType])
                 .setParameter("id", departmentId)
                 .getResultList

    result.foreach(em.detach(_))
    new java.util.HashSet(result)
  }

  val ActionTypeFilterByDepartmentIdQuery = """
    SELECT at
    FROM
      OrgStructureActionType osat JOIN osat.actionType at
    WHERE
      osat.masterDepartment.id = :id
    AND
      at.deleted = 0
  """
}
