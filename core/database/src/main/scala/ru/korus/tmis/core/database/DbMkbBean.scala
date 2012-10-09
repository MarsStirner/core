package ru.korus.tmis.core.database

import ru.korus.tmis.core.entity.model.{Mkb, Nomenclature}
import ru.korus.tmis.core.logging.LoggingInterceptor

import grizzled.slf4j.Logging
import javax.ejb.Stateless
import javax.interceptor.Interceptors
import javax.persistence.{EntityManager, PersistenceContext}

import scala.collection.JavaConversions._
import ru.korus.tmis.core.data.{RlsDataListFilter, QueryDataStructure}

@Interceptors(Array(classOf[LoggingInterceptor]))
@Stateless
class DbMkbBean
  extends DbMkbBeanLocal
  with Logging {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _


  def getMkbById(id: Int) = {
    val result = em.createQuery(MkbByIdQuery, classOf[Mkb])
      .setParameter("id", id)
      .getResultList

    result.size match {
      case 0 => {
        null
      }
      case size => {
        result.foreach(em.detach(_))
        result(0)
      }
    }
  }

  def getMkbByCode(code: String) = {
    val result = em.createQuery(MkbByCodeQuery, classOf[Mkb])
      .setParameter("code", code)
      .getResultList

    result.size match {
      case 0 => {
        null
      }
      case size => {
        result.foreach(em.detach(_))
        result(0)
      }
    }
  }

  val MkbByIdQuery = """
    SELECT m
    FROM
      Mkb m
    WHERE
      m.id = :id
                     """
  val MkbByCodeQuery = """
    SELECT m
    FROM
      Mkb m
    WHERE
      m.diagID = :code
                       """
}
