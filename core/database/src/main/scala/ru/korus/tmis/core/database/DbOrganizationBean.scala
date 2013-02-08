package ru.korus.tmis.core.database

import ru.korus.tmis.core.logging.LoggingInterceptor

import javax.ejb.Stateless
import javax.interceptor.Interceptors
import javax.persistence.{EntityManager, PersistenceContext}

import grizzled.slf4j.Logging
import ru.korus.tmis.core.entity.model.Organisation
import scala.collection.JavaConversions._
import ru.korus.tmis.core.data.{QueryDataStructure, DictionaryListRequestDataFilter}

@Interceptors(Array(classOf[LoggingInterceptor]))
@Stateless
class DbOrganizationBean
  extends DbOrganizationBeanLocal
  with Logging {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  val OrganisationFindQuery = """
    SELECT o
    FROM
      Organisation o
    WHERE
      o.id = :id
                              """

  val InsurenceDictionaryFindQuery = """
    SELECT o.id, o.fullName
    FROM
      Organisation o
    WHERE
      o.isInsurer = 1
                                     """

  val TFOMSDictionaryFindQuery = """
    SELECT o.id, o.fullName
    FROM
      Organisation o
    WHERE
      o.headId IS NULL
    AND
      o.isHospital = 0
                                 """
  /*      раньше было так
  WHERE
      (o.infisCode IS NULL OR o.infisCode = '')
    AND
      o.obsoleteInfisCode IS NOT NULL
    AND
      o.area IS NOT NULL
   */

  val AllOrganizationsWithFilterQuery = """
  SELECT DISTINCT %s
  FROM
    Organisation r
  %s
  %s
                                        """

  val OrganisationFindQueryByInfisCode = """
  SELECT org
  FROM Organisation org
  WHERE org.infisCode =                 :INFISCODE
                                         """

  def getCountOfOrganizationWithFilter(filter: Object) = {
    var queryStr: QueryDataStructure = if (filter.isInstanceOf[DictionaryListRequestDataFilter]) {
      filter.asInstanceOf[DictionaryListRequestDataFilter].toQueryStructure()
    }
    else {
      new QueryDataStructure()
    }
    queryStr.query += (if (filter.asInstanceOf[DictionaryListRequestDataFilter].getDictName().compare("TFOMS") == 0) {
      "AND r.headId IS NULL AND r.isHospital = 0"
    }
    else {
      "AND r.isInsurer = 1"
    })
    if (queryStr.data.size() > 0 || queryStr.query.size > 0) {
      if (queryStr.query.indexOf("AND ") == 0) {
        queryStr.query = "WHERE " + queryStr.query.substring("AND ".length())
      }
    }
    var typed = em.createQuery(AllOrganizationsWithFilterQuery.format(
      "count(r)",
      queryStr.query,
      ""),
      classOf[Long])
    if (queryStr.data.size() > 0) {
      queryStr.data.foreach(qdp => typed.setParameter(qdp.name, qdp.value))
    }
    typed.getSingleResult
  }

  def getAllOrganizationWithFilter(page: Int, limit: Int, sortingField: String, sortingMethod: String, filter: Object): java.util.LinkedList[Object] = {
    var queryStr: QueryDataStructure = if (filter.isInstanceOf[DictionaryListRequestDataFilter]) {
      filter.asInstanceOf[DictionaryListRequestDataFilter].toQueryStructure()
    }
    else {
      new QueryDataStructure()
    }
    queryStr.query += (if (filter.asInstanceOf[DictionaryListRequestDataFilter].getDictName().compare("TFOMS") == 0) {
      "AND r.headId IS NULL AND r.isHospital = 0"
    }
    else {
      "AND r.isInsurer = 1"
    })
    val sorting = "ORDER BY %s %s".format(sortingField, sortingMethod)
    if (queryStr.data.size() > 0 || queryStr.query.size > 0) {
      if (queryStr.query.indexOf("AND ") == 0) {
        queryStr.query = "WHERE " + queryStr.query.substring("AND ".length())
      }
    }

    val handle = if (filter.asInstanceOf[DictionaryListRequestDataFilter].getDictName().compare("insurance") == 0) {
      "r.id, r.fullName, COALESCE(r.headId, 0)"
    } else {
      "r.id, r.fullName"
    }
    var typed = em.createQuery(AllOrganizationsWithFilterQuery.format(handle, queryStr.query, sorting), classOf[Array[AnyRef]])
      .setMaxResults(limit)
      .setFirstResult(limit * page)
    if (queryStr.data.size() > 0) {
      queryStr.data.foreach(qdp => typed.setParameter(qdp.name, qdp.value))
    }
    val result = typed.getResultList
    val list = new java.util.LinkedList[Object]
    if (filter.asInstanceOf[DictionaryListRequestDataFilter].getDictName().compare("insurance") == 0) {
      result.foreach(f => {
        list.add((f(0).asInstanceOf[java.lang.Integer],
          f(1).asInstanceOf[java.lang.String],
          f(2).asInstanceOf[java.lang.Long].intValue()))
      })
    } else {
      result.foreach(f => {
        list.add((f(0).asInstanceOf[java.lang.Integer],
          f(1).asInstanceOf[java.lang.String]))
      })
    }
    list
  }

  def getAllInsurenceOrganizationsData(): java.util.LinkedList[Object] = {
    val types = em.createQuery(InsurenceDictionaryFindQuery, classOf[Array[AnyRef]]).getResultList
    val list = new java.util.LinkedList[Object]
    types.foreach(t => {
      list.add((t(0).asInstanceOf[java.lang.Integer], t(1).asInstanceOf[java.lang.String]))
    })
    list
  }

  def getAllTFOMSOrganizationsData(): java.util.LinkedList[Object] = {
    val types = em.createQuery(TFOMSDictionaryFindQuery, classOf[Array[AnyRef]]).getResultList
    val list = new java.util.LinkedList[Object]
    types.foreach(t => {
      list.add((t(0).asInstanceOf[java.lang.Integer], t(1).asInstanceOf[java.lang.String]))
    })
    list
  }

  def getAllOrganizations() = {
    em.createNamedQuery("Organisation.findAll", classOf[Organisation]).getResultList
  }

  def getOrganizationById(id: Int): Organisation = {
    val result = em.createQuery(OrganisationFindQuery,
      classOf[Organisation])
      .setParameter("id", id)
      .getResultList

    result.size match {
      case 0 => {
        null
        //        throw new NoSuchOrganisationException(
        //          ConfigManager.ErrorCodes.OrganisationNotFound,
        //          id,
        //          i18n("error.OrganisationNotFound"))
      }
      case size => {
        result.foreach(rbType => {
          em.detach(rbType)
        })
        result(0)
      }
    }
  }

  def getOrganizationByInfisCode(infisCode: String): Organisation = {
    em.createQuery(OrganisationFindQueryByInfisCode, classOf[Organisation]).setParameter("INFISCODE", infisCode).getSingleResult;
  }
}
