package ru.korus.tmis.core.database


import ru.korus.tmis.core.logging.LoggingInterceptor
import ru.korus.tmis.core.entity.model.ActionPropertyType
import ru.korus.tmis.util.I18nable

import grizzled.slf4j.Logging
import java.util.{Collections, HashSet}
import javax.ejb.Stateless
import javax.interceptor.Interceptors
import javax.persistence.{TypedQuery, PersistenceContext, EntityManager}

import scala.collection.JavaConversions._
import ru.korus.tmis.core.entity.model.{ActionType, ActionPropertyType}
import ru.korus.tmis.core.data.{QueryDataStructure, DictionaryListRequestDataFilter}
import ru.korus.tmis.auxiliary.AuxiliaryQuickSort
import ru.korus.tmis.core.filter.ListDataFilter

@Interceptors(Array(classOf[LoggingInterceptor]))
@Stateless
class DbActionPropertyTypeBean
  extends DbActionPropertyTypeBeanLocal
  with Logging
  with I18nable {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  def getActionPropertyTypeById(id: Int) = {
    val result = em.createQuery(AptByIdQuery,
      classOf[ActionPropertyType])
      .setParameter("id", id)
      .getResultList

    val apt = result(0)
    em.detach(apt)
    apt
  }

  def getDepartmentAPT() = {
    getAptByName(i18n("db.apt.departmentName"))
  }

  def getHospitalBedAPT() = {
    getAptByName(i18n("db.apt.hospitalBedName"))
  }

  def getAnamnesisAPT() = {
    getAptByNameAndDename(i18n("db.apt.anamnesisName"),
      i18n("db.apt.anamnesisDename"),
      i18n("db.apt.allergoanamnesisName"))
  }

  def getAllergoanamnesisAPT() = {
    getAptByName(i18n("db.apt.allergoanamnesisName"))
  }

  def getDiagnosisAPT() = {
    getAptByNameAndName(i18n("db.apt.diagnosisName01"),
      i18n("db.apt.diagnosisName02"))
  }

  def getDrugNomenAPT() = {
    getAptByName(i18n("db.apt.drugName"))
  }

  def getDosageAPT() = {
    getAptByName(i18n("db.apt.dosageName"))
  }

  //////////////////////////////////////////////////////////////////////////////

  def getAptByName(name: String) = {
    getAptByQuery(em.createQuery(AptByNameQuery,
      classOf[ActionPropertyType])
      .setParameter("aptName", name))
  }

  def getAptByNameAndName(name01: String,
                          name02: String) = {
    getAptByQuery(em.createQuery(AptByNameAndNameQuery,
      classOf[ActionPropertyType])
      .setParameter("aptName01", name01)
      .setParameter("aptName02", name02))
  }

  def getAptByNameAndDename(name: String,
                            dename01: String,
                            dename02: String) = {
    getAptByQuery(em.createQuery(AptByNameAndDenameQuery,
      classOf[ActionPropertyType])
      .setParameter("aptName", name)
      .setParameter("aptDename01", dename01)
      .setParameter("aptDename02", dename02))
  }

  def getAptByQuery(query: TypedQuery[ActionPropertyType]) = {
    val result = query.getResultList
    result.foreach((apt) => em.detach((apt)))
    result.size match {
      case 0 => Collections.emptySet[ActionPropertyType]
      case size => new HashSet[ActionPropertyType](result)
    }
  }

  def getActionPropertyTypeByActionTypeIdWithCode(code: String) = {
    val rolesId = em.createQuery(ActionTypeByCode, classOf[ActionType])
      .setParameter("code", code)
      .getResultList
    rolesId.foreach((at) => em.detach((at)))

    val result = em.createQuery(ActionPropertyTypeByActionTypeIdWithCodeQuery,
      classOf[ActionPropertyType])
      .setParameter("rolesId", rolesId(0).getId.intValue()) //TODO: временно первое значение из листа (хотя оно и ед. пока)
      .getResultList
    result.foreach((apt) => em.detach((apt)))
    result
  }

  def getActionPropertyTypesByActionTypeId(actionTypeId: Int) = {
    val result = em.createQuery(ActionPropertyTypeByActionTypeIdWithCodeQuery,
      classOf[ActionPropertyType])
      .setParameter("rolesId", actionTypeId)
      .getResultList
    result.foreach((apt) => em.detach((apt)))
    result
  }

  def getActionPropertyTypeValueDomainsWithFilter(page: Int, limit: Int, sorting: String, filter: ListDataFilter) = {

    val queryStr = filter.toQueryStructure()
    if (queryStr.data.size() > 0 || queryStr.query.size > 0) {
      if (queryStr.query.indexOf("AND ") == 0) {
        queryStr.query = "WHERE " + queryStr.query.substring("AND ".length())
      }
    }

    val typed = em.createQuery(valueDomainQuery.format(queryStr.query), classOf[String])
                  .setMaxResults(limit)
                  .setFirstResult(limit * page)
    if (queryStr.data.size() > 0) {
      queryStr.data.foreach(qdp => typed.setParameter(qdp.name, qdp.value))
    }
    val result = typed.getResultList

    result.size match {
      case 0 => {
        null
      }
      case size => {
        val list = new java.util.LinkedList[Object]
        var str = result.iterator().next()

        var flgBegin = false
        var pos = -1

        while (str.size > 0 && str.indexOf("'") >= 0) {
          pos = str.indexOf("'")
          if (!flgBegin) {
            flgBegin = true
          } else {
            flgBegin = false
            list.add(str.substring(0, pos))
          }
          str = str.substring(pos + 1)
        }
        //AuxiliaryQuickSort.sort(list.toArray(String))
        list
      }
    }
  }

  val AptByIdQuery = """
    SELECT apt
    FROM
      ActionPropertyType apt
    WHERE
      apt.deleted = 0
    AND
      apt.id = :id
                     """

  val AptByNameQuery = """
    SELECT apt
    FROM
      ActionPropertyType apt
    WHERE
      apt.deleted = 0
    AND
      apt.name like :aptName
                       """

  val AptByNameAndNameQuery = """
    SELECT apt
    FROM
      ActionPropertyType apt
    WHERE
      apt.deleted = 0
    AND
      apt.name like :aptName01
    OR
      apt.name like :aptName02
                              """

  val AptByNameAndDenameQuery = """
    SELECT apt
    FROM
      ActionPropertyType apt
    WHERE
      apt.deleted = 0
    AND
      apt.name like :aptName
    AND
      apt.name not like :aptDename01
    AND
      apt.name not like :aptDename02
                                """

  val ActionPropertyTypeByActionTypeIdWithCodeQuery = """
  SELECT apt
  FROM ActionPropertyType apt
  WHERE apt.actionType.id = :rolesId
  AND apt.deleted = 0
                                                      """
  val ActionTypeByCode = """
   SELECT at
   FROM ActionType at
   WHERE at.code = :code
   AND at.deleted = 0
                         """

  val valueDomainQuery = """
     SELECT DISTINCT apt.valueDomain
     FROM
      Action a
      JOIN a.actionType at
      JOIN at.actionPropertyTypes apt
     %s
                         """
}

