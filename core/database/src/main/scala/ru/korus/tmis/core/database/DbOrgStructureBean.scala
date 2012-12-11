package ru.korus.tmis.core.database

import ru.korus.tmis.core.entity.model.{OrgStructure, ActionType}
import ru.korus.tmis.core.logging.LoggingInterceptor
import grizzled.slf4j.Logging
import javax.ejb.Stateless
import javax.interceptor.Interceptors
import javax.persistence.{EntityManager, PersistenceContext}
import scala.collection.JavaConversions._
import ru.korus.tmis.core.data.{QueryDataStructure, DepartmentsDataFilter}
import ru.korus.tmis.core.exception.CoreException
import ru.korus.tmis.util.{I18nable, ConfigManager}

@Interceptors(Array(classOf[LoggingInterceptor]))
@Stateless
class DbOrgStructureBean
  extends DbOrgStructureBeanLocal
  with Logging
  with I18nable {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  val OrgStructureFindQuery = """
    SELECT os
    FROM
      OrgStructure os
    WHERE
      os.id = :id
                               """

  def getOrgStructureById(id: Int): OrgStructure = {
    val result = em.createQuery(OrgStructureFindQuery,
      classOf[OrgStructure])
      .setParameter("id", id)
      .getResultList

    result.size match {
      case 0 => {
        throw new CoreException(
          ConfigManager.ErrorCodes.ClientQuotingNotFound,
          i18n("error.orgStructureNotFound").format(id))
      }
      case size => {
        result.foreach(rbType => {
          em.detach(rbType)
        })
        result(0)
      }
    }
  }

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

  def getCountAllOrgStructuresWithFilter(filter: Object) = {
    val queryStr: QueryDataStructure = if (filter.isInstanceOf[DepartmentsDataFilter]) {
      filter.asInstanceOf[DepartmentsDataFilter].toQueryStructure()
    } else  new QueryDataStructure()

    val typed = em.createQuery(OrgStructuresAndCountRecordsWithFilterQuery.format(
                  "count(os)",
                  queryStr.query,
                   ""), classOf[Long])
    if (queryStr.data.size() > 0) {
      queryStr.data.foreach(qdp => typed.setParameter(qdp.name, qdp.value))
    }
    typed.getSingleResult
  }

  def getAllOrgStructuresByRequest(limit: Int, page: Int, sortField: String, sortMethod: String, filter: Object) = {

    val queryStr: QueryDataStructure = if (filter.isInstanceOf[DepartmentsDataFilter]) {
      filter.asInstanceOf[DepartmentsDataFilter].toQueryStructure()
    } else  new QueryDataStructure()

    var sorting = "ORDER BY %s %s".format(sortField, sortMethod)
    if (sortField==null || sortField.compareTo("")==0 || sortMethod == null || sortMethod.compareTo("")==0) {
      sorting = "ORDER BY os.id asc"
    }

    var typed = em.createQuery(OrgStructuresAndCountRecordsWithFilterQuery.format("os", queryStr.query, sorting), classOf[OrgStructure])
                  .setMaxResults(limit)
                  .setFirstResult(limit * page)
    if (queryStr.data.size() > 0) {
      queryStr.data.foreach(qdp => typed.setParameter(qdp.name, qdp.value))
    }
    typed.getResultList
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
