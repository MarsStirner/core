package ru.korus.tmis.core.database.common

import org.slf4j.{LoggerFactory, Logger}
import ru.korus.tmis.core.entity.model.{OrgStructureAddress, Staff, OrgStructure, ActionType}

import javax.ejb.Stateless
import javax.interceptor.Interceptors
import javax.persistence.{EntityManager, PersistenceContext}
import scala.collection.JavaConversions._
import ru.korus.tmis.core.exception.CoreException
import ru.korus.tmis.core.data.{DepartmentsDataFilter, QueryDataStructure}
import java.util
import ru.korus.tmis.core.filter.{ListDataFilter, AbstractListDataFilter}
import ru.korus.tmis.scala.util.{I18nable, ConfigManager}
import scala.language.reflectiveCalls

//
@Stateless
class DbOrgStructureBean
  extends DbOrgStructureBeanLocal
  with I18nable {

  val logger: Logger = LoggerFactory.getLogger(this.getClass)

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  val OrgStructureFindQuery = """
    SELECT os
    FROM
      OrgStructure os
    WHERE
      os.id = :id
                              """
  //Flag marks that OrgStructure and Person is available for requests
  val AVAILABLE_FOR_EXTERNAL: Int = 1

  def getOrgStructureById(id: Int): OrgStructure = {
    val result = em.createQuery(OrgStructureFindQuery,
      classOf[OrgStructure])
      .setParameter("id", id)
      .getResultList
    result.size match {
      case 0 => throw new CoreException(ConfigManager.ErrorCodes.ClientQuotingNotFound, i18n("error.orgStructureNotFound").format(id))
      case size => result.iterator.next
    }
  }

  def getAllOrgStructures: util.List[OrgStructure] = {
    em.createNamedQuery("OrgStructure.findAll", classOf[OrgStructure]).getResultList
  }

  def getActionTypeFilter(departmentId: Int): util.HashSet[ActionType] = {
    val result = em.createQuery(ActionTypeFilterByDepartmentIdQuery,
      classOf[ActionType])
      .setParameter("id", departmentId)
      .getResultList


    new java.util.HashSet(result)
  }

  def getCountAllOrgStructuresWithFilter(filter: Object): Long = {
    val queryStr: QueryDataStructure = filter match {
      case x: DepartmentsDataFilter => x.toQueryStructure()
      case _ => new QueryDataStructure()
    }

    val typed = em.createQuery(OrgStructuresAndCountRecordsWithFilterQuery.format(
      "count(os)",
      queryStr.query,
      ""), classOf[Long])
    if (queryStr.data.size() > 0) {
      queryStr.data.foreach(qdp => typed.setParameter(qdp.name, qdp.value))
    }
    typed.getSingleResult
  }

  def getAllOrgStructuresByRequest(limit: Int, page: Int, sorting: String, filter: ListDataFilter): util.List[OrgStructure] = {

    val queryStr: QueryDataStructure = filter.toQueryStructure

    val typed = em.createQuery(OrgStructuresAndCountRecordsWithFilterQuery.format("os", queryStr.query, sorting), classOf[OrgStructure])
                  .setMaxResults(limit)
                  .setFirstResult(limit * page)
    if (queryStr.data.size() > 0) {
      queryStr.data.foreach(qdp => typed.setParameter(qdp.name, qdp.value))
    }
    typed.getResultList
  }

  def getOrgStructureByHospitalBedId(bedId: Int): OrgStructure = {
    val result = em.createQuery("SELECT org FROM OrgStructure org, OrgStructureHospitalBed bed WHERE bed.masterDepartment.id = org.id AND bed.id = :bedId", classOf[OrgStructure])
      .setParameter("bedId", bedId)
      .getSingleResult
    result
  }

  /**
   * Запрос на получение под-отделений начиная с указаного parentId.
   *
   * @param parentId  Идентификатор отделения для которго выводим все подотделения
   * @param recursive рекурсивно (выбираем все под-отделения, входящие в одно из выбраных под-отделений)
   * @param infisCode Принадлежащие одной структуре
   * @return Список под-отделений
   * @throws CoreException Если не найдено ни одной оргструктуры
   */
  def getRecursiveOrgStructures(parentId: Int, recursive: Boolean, infisCode: String): util.List[OrgStructure] = {
    val allEntitiesList = getAllOrgStructures()
    var parentIdsSet = Set[java.lang.Integer](parentId)
    var result = Set[OrgStructure]()

    val infisCodeIsDefined: Boolean = infisCode.length > 0
    val parentIdIsDefined: Boolean = parentId.intValue() > 0

    allEntitiesList.foreach((current: OrgStructure) => {
      if (!current.getDeleted && current.getAvailableForExternal == AVAILABLE_FOR_EXTERNAL &&
        (current.getParentId == parentId || (!parentIdIsDefined && current.getParentId == null)) &&
        (!infisCodeIsDefined || (current.getOrganization != null && current.getOrganization.getInfisCode == infisCode))) {
        result += current
        if (recursive.booleanValue()) parentIdsSet += current.getId
      }
    })

    if (recursive.booleanValue()) {
      var previousSize: Int = 0
      val MAX_DEEP: Int = 7
      var currentDeep = 0
      while (parentIdsSet.size > previousSize && currentDeep < MAX_DEEP) {
        //Get childrens from parentIdsSet
        previousSize = parentIdsSet.size
        allEntitiesList.foreach((current: OrgStructure) => {
          if (!current.getDeleted && current.getAvailableForExternal == AVAILABLE_FOR_EXTERNAL && parentIdsSet(current.getParentId)
            && (!infisCodeIsDefined || (current.getOrganization != null && current.getOrganization.getInfisCode == infisCode))
          ) {
            result += current
            parentIdsSet += current.getId
          }
        }) //End of First-Level childrens
        currentDeep += 1
      }
    }
    if (result.isEmpty) {
      throw new CoreException("Not Found OrgStructures")
    }
    result.toList
  }

  /**
   * Запрос на получение ИД оргструктур по заданному адресу
   * @return Список ИД оргутруктур удовлятворяющих заданному адресу  \ Пустой список
   */
  def getOrgStructureIdListByAddress(KLADRCode: java.lang.String, KLADRStreetCode: java.lang.String, number: java.lang.String,
                               corpus: java.lang.String, flat: java.lang.Integer): util.List[java.lang.Integer] = {
     em.createQuery(OrgStructureIdByAdressQuery, classOf[java.lang.Integer])
      .setParameter("KLADRCode", KLADRCode).setParameter("KLADRStreetCode", KLADRStreetCode)
      .setParameter("NUMBER", number).setParameter("CORPUS", corpus).setParameter("FLAT", flat)
      .getResultList
  }

  /**
   * Получение списка работников заданной оргструктуры
   * @param orgStructureId Оргструктура, в которой ищем работников
   * @param recursive      флаг рекурсии (при true- выборка работников еще и из подчиненных оргструктур)
   * @param infisCode      инфис-код организации
   * @return Список работников
   * @throws CoreException
   */
  def getPersonnel(orgStructureId: java.lang.Integer, recursive: Boolean, infisCode: java.lang.String): util.List[Staff] = {
    val organisationIDList: util.List[java.lang.Integer] = new util.ArrayList[java.lang.Integer]()
    organisationIDList.add(orgStructureId)
    if (recursive) try {
      getRecursiveOrgStructures(orgStructureId.intValue(), recursive, infisCode).map((current: OrgStructure) => {
        organisationIDList.add(current.getId)
      })
    } catch {
      case coreExc: CoreException => logger.warn("Recursive search is not found any children for parentId=" + orgStructureId)
    }
    em.createQuery(OrgStructureGetPersonnel, classOf[Staff]).setParameter("ORGSTRUCTUREIDLIST", organisationIDList).getResultList
  }

  val OrgStructureGetPersonnel = """
    SELECT person
    FROM Staff person
    WHERE person.orgStructure.id IN :ORGSTRUCTUREIDLIST
    AND person.deleted = 0
    AND person.retired = 0
    AND person.speciality IS NOT NULL
    AND person.availableForExternal = 1
    ORDER BY person.lastName
                                 """

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

  def getOrgStructureAddressByOrgStructure(orgStructure: OrgStructure): util.List[OrgStructureAddress] = {
    em.createNamedQuery("OrgStructureAddress.findByOrgStructure", classOf[OrgStructureAddress])
      .setParameter("orgStructure", orgStructure).getResultList
  }

  def getOrgStructuresByOrganisationId(organisationId: Int): util.List[OrgStructure] = {
    em.createNamedQuery("OrgStructure.findByOrganisationId", classOf[OrgStructure])
      .setParameter("ORG_ID", organisationId)
      .getResultList
  }

  override def getRecursiveOrgStructuresWithoutAvailableForExternal(parentId: Int, recursive: Boolean, infisCode: String): util.List[OrgStructure] = {
    val allEntitiesList = getAllOrgStructures()
    var parentIdsSet = Set[java.lang.Integer](parentId)
    var result = Set[OrgStructure]()

    val infisCodeIsDefined: Boolean = infisCode.length > 0
    val parentIdIsDefined: Boolean = parentId.intValue() > 0

    allEntitiesList.foreach((current: OrgStructure) => {
      if (!current.getDeleted &&
        (current.getParentId == parentId || (!parentIdIsDefined && current.getParentId == null)) &&
        (!infisCodeIsDefined || (current.getOrganization != null && current.getOrganization.getInfisCode == infisCode))) {
        result += current
        if (recursive.booleanValue()) parentIdsSet += current.getId
      }
    })

    if (recursive.booleanValue()) {
      var previousSize: Int = 0
      val MAX_DEEP: Int = 7
      var currentDeep = 0
      while (parentIdsSet.size > previousSize && currentDeep < MAX_DEEP) {
        //Get childrens from parentIdsSet
        previousSize = parentIdsSet.size
        allEntitiesList.foreach((current: OrgStructure) => {
          if (!current.getDeleted  && parentIdsSet(current.getParentId)
            && (!infisCodeIsDefined || (current.getOrganization != null && current.getOrganization.getInfisCode == infisCode))
          ) {
            result += current
            parentIdsSet += current.getId
          }
        }) //End of First-Level childrens
        currentDeep += 1
      }
    }
    if (result.isEmpty) {
      throw new CoreException("Not Found OrgStructures")
    }
    result.toList
  }
}
