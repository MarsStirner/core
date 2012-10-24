package ru.korus.tmis.core.database

import ru.korus.tmis.core.logging.LoggingInterceptor
import ru.korus.tmis.core.entity.model.{OrgStructure, ActionType}
import ru.korus.tmis.core.logging.LoggingInterceptor


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

  def getCountAllOrgStructuresWithFilter(filter: String) = {
    val result = em.createQuery(OrgStructuresAndCountRecordsWithFilterQuery.format("count(os)", "", filter), classOf[Long])
      .getSingleResult
    result
  }

  def getAllOrgStructuresByRequest(limit: Int, page: Int, sortField: String, sortMethod: String, filter: String) = {

    val sorting: String = "ORDER BY os.%s %s".format(sortField, sortMethod)
    val result = em.createQuery(OrgStructuresAndCountRecordsWithFilterQuery.format("os", sorting, filter), classOf[OrgStructure])
      .setMaxResults(limit)
      .setFirstResult(limit * page)
      .getResultList

    result
  }

  def getOrgStructureByHospitalBedId(bedId: Int) = {
    val result = em.createQuery("SELECT org FROM OrgStructure org, OrgStructureHospitalBed bed WHERE bed.masterDepartment.id = org.id AND bed.id = :bedId", classOf[OrgStructure])
      .setParameter("bedId", bedId)
      .getSingleResult
    result
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

  val OrgStructuresAndCountRecordsWithFilterQuery = """
    SELECT %s
    FROM OrgStructure os
    WHERE os.deleted = 0
    %s
    %s
                                                    """
}
