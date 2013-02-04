package ru.korus.tmis.core.database

import ru.korus.tmis.core.entity.model.{OrgStructure, ActionType}
import ru.korus.tmis.core.logging.LoggingInterceptor
import grizzled.slf4j.Logging
import javax.ejb.Stateless
import javax.interceptor.Interceptors
import javax.persistence.{EntityManager, PersistenceContext}
import scala.collection.JavaConversions._
import ru.korus.tmis.core.exception.CoreException
import ru.korus.tmis.util.{I18nable, ConfigManager}
import ru.korus.tmis.core.data.{DepartmentsDataFilter, QueryDataStructure}


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
  //Flag marks that OrgStructure is available for requests
  val AVAILABLE_FOR_EXTERNAL: Int = 1

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
    } else new QueryDataStructure()

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
    } else new QueryDataStructure()

    var sorting = "ORDER BY %s %s".format(sortField, sortMethod)
    if (sortField == null || sortField.compareTo("") == 0 || sortMethod == null || sortMethod.compareTo("") == 0) {
      sorting = "ORDER BY os.id asc"
    }

    val typed = em.createQuery(OrgStructuresAndCountRecordsWithFilterQuery.format("os", queryStr.query, sorting), classOf[OrgStructure])
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

  def getRecursiveOrgStructures(parentId: java.lang.Integer, recursive: Boolean): java.util.List[OrgStructure] = {
    val allEntitiesList = getAllOrgStructures()
    var parentIdsSet = Set[java.lang.Integer](parentId)
    var result = Set[OrgStructure]()
    allEntitiesList.map((current: OrgStructure) => {
      if (!current.getDeleted && current.getAvailableForExternal == AVAILABLE_FOR_EXTERNAL
        && (current.getParentId == parentId || (parentId == 0 && current.getParentId == null))) {
        result += current
        if (recursive) parentIdsSet += current.getId
      }
    })
    if (recursive) {
      var previousSize: Int = 0
      val MAX_DEEP: Int = 7
      var currentDeep = 0
      while (parentIdsSet.size > previousSize && currentDeep < MAX_DEEP) {
        //Get childrens from parentIdsSet
        previousSize = parentIdsSet.size
        allEntitiesList.map((current: OrgStructure) => {
          if (!current.getDeleted && current.getAvailableForExternal == AVAILABLE_FOR_EXTERNAL && parentIdsSet(current.getParentId)) {
            result += current
            parentIdsSet += current.getId
          }
        }) //End of First-Level childrens
        currentDeep += 1
      }
    }
    if (result.size == 0) {
      throw new CoreException(
        ConfigManager.ErrorCodes.ClientQuotingNotFound,
        i18n("error.orgStructureNotFound recursive from").format(parentId))
    }
    result.toList
  }

  def getOrgStructureByAdress(KLADRCode: java.lang.String, KLADRStreetCode: java.lang.String, number: java.lang.String,
                              corpus: java.lang.String, flat: java.lang.Integer) = {
    val result = em.createQuery(OrgStructureIdByAdressQuery, classOf[java.lang.Integer])
      .setParameter("KLADRCode", KLADRCode).setParameter("KLADRStreetCode", KLADRStreetCode)
      .setParameter("NUMBER", number).setParameter("CORPUS", corpus).setParameter("FLAT", flat)
      .getResultList
    result
  }

  val OrgStructureIdByAdressQuery = """
    SELECT DISTINCT org_adr.master.id
    FROM OrgStructureAddress org_adr
    LEFT JOIN org_adr.master  org
    LEFT JOIN org_adr.addressHouse adr_house
    WHERE adr_house.deleted=0
    AND org.deleted=0
    AND adr_house.KLADRCode=                     :KLADRCode
    AND adr_house.KLADRStreetCode=               :KLADRStreetCode
    AND adr_house.number=                        :NUMBER
    AND adr_house.corpus=                        :CORPUS
    AND (org_adr.lastFlat   = 0
      OR (org_adr.firstFlat <=         :FLAT
      AND org_adr.lastFlat  >=         :FLAT
      )
    )
                                    """

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
