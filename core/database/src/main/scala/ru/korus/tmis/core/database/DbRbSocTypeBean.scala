package ru.korus.tmis.core.database

import java.util
import javax.ejb.Stateless
import javax.persistence.{EntityManager, PersistenceContext}

import ru.korus.tmis.core.data.{DictionaryListRequestDataFilter, QueryDataStructure}
import ru.korus.tmis.core.entity.model.RbSocStatusType
import ru.korus.tmis.core.exception.NoSuchEntityException
import ru.korus.tmis.core.filter.ListDataFilter
import ru.korus.tmis.scala.util.{ConfigManager, I18nable}

import scala.collection.JavaConversions._
import scala.language.reflectiveCalls


@Stateless
class DbRbSocTypeBean extends DbRbSocTypeBeanLocal
with I18nable {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _


  val FindByIdQuery = """
    SELECT t
    FROM
      RbSocStatusType t
    WHERE
      t.id = :id
                      """

  val SocStatusTypeDictionaryFindQuery = """
    SELECT %s
    FROM
      RbSocStatusClassTypeAssoc sa,
      RbSocStatusType sst,
      RbSocStatusClass ssc
    WHERE
      sa.classId = ssc.id
    AND
      sa.typeId = sst.id
    %s
    %s
                                         """

  def getCountOfSocStatusTypesWithFilter(filter: Object): Long = {
    val queryStr: QueryDataStructure = filter match {
      case x: DictionaryListRequestDataFilter => x.toQueryStructure()
      case _ => new QueryDataStructure()
    }

    val typed = em.createQuery(SocStatusTypeDictionaryFindQuery.format(
      "count(sst)",
      queryStr.query,
      ""),
      classOf[Long])
    if (queryStr.data.size() > 0) {
      queryStr.data.foreach(qdp => typed.setParameter(qdp.name, qdp.value))
    }
    typed.getSingleResult
  }

  def getAllSocStatusTypesWithFilter(page: Int, limit: Int, sorting: String, filter: ListDataFilter): util.LinkedList[Object] = {

    val queryStr = filter.toQueryStructure
    val typed = em.createQuery(SocStatusTypeDictionaryFindQuery.format("sst.id, sst.name", queryStr.query, sorting), classOf[Array[AnyRef]])
                  .setMaxResults(limit)
                  .setFirstResult(limit * page)
    if (queryStr.data.size() > 0) {
      queryStr.data.foreach(qdp => typed.setParameter(qdp.name, qdp.value))
    }
    val result = typed.getResultList

    val list = new java.util.LinkedList[Object]
    result.foreach(f => {
      list.add((f(0).asInstanceOf[java.lang.Integer],
        f(1).asInstanceOf[java.lang.String]))
    })
    list
  }

  def getSocStatusTypeById(id: Int): RbSocStatusType = {
    val result = em.createQuery(FindByIdQuery,
      classOf[RbSocStatusType])
      .setParameter("id", id)
      .getResultList
    result.size match {
      case 0 => throw new NoSuchEntityException(
        ConfigManager.ErrorCodes.ClientSocStatusTypeNotFound,
        id,
        i18n("error.ClientSocStatusTypeNotFound"))
      case size => result.iterator.next
    }
  }

}