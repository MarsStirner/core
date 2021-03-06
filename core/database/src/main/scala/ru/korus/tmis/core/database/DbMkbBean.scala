package ru.korus.tmis.core.database

import org.apache.commons.lang.StringUtils
import ru.korus.tmis.core.entity.model.{Mkb, Nomenclature}


import grizzled.slf4j.Logging
import javax.ejb.Stateless
import javax.interceptor.Interceptors
import javax.persistence.{EntityManager, PersistenceContext}

import scala.collection.JavaConversions._
import ru.korus.tmis.core.exception.CoreException
import ru.korus.tmis.scala.util.{I18nable, ConfigManager}
import scala.language.reflectiveCalls


@Stateless
class DbMkbBean
  extends DbMkbBeanLocal
  with Logging
  with I18nable {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _


  def getMkbById(id: Int) = {
    val result = em.createQuery(MkbByIdQuery, classOf[Mkb])
      .setParameter("id", id)
      .getResultList

    result.size match {
      case 0 => {
        throw new CoreException(ConfigManager.ErrorCodes.JobNotFound, i18n("error.mkbWithIdNotFound").format(id))
      }
      case size => {

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
        throw new CoreException(ConfigManager.ErrorCodes.JobNotFound, i18n("error.mkbWithCodeNotFound").format(code))
      }
      case size => {

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

  override def getByCode(code: String): Mkb = {
    if(StringUtils.isEmpty(code)){
      return null
    }
    em.createNamedQuery("Mkb.findByCode", classOf[Mkb]).setParameter("code", code).getResultList.headOption.orNull
  }

  override def getById(id: Int): Mkb = {
    if(id > 0) em.find(classOf[Mkb], id) else null
  }
}
