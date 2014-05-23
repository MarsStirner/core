package ru.korus.tmis.core.database

import ru.korus.tmis.core.logging.LoggingInterceptor

import grizzled.slf4j.Logging
import javax.ejb.Stateless
import javax.interceptor.Interceptors
import javax.persistence.{EntityManager, PersistenceContext}

import scala.collection.JavaConversions._
import ru.korus.tmis.core.entity.model.Nomenclature
import ru.korus.tmis.core.exception.CoreException

@Interceptors(Array(classOf[LoggingInterceptor]))
@Stateless
class DbRlsBean
  extends DbRlsBeanLocal
  with Logging {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  def getRlsById(id: Int) = {
    try {
      if(id < 1)
        throw new IllegalArgumentException("Invalid id value id=" + id)

      val n = em.find(classOf[Nomenclature], id)
      em.detach(n)
      n
    } catch {
      case e: Throwable => throw new CoreException(e.getMessage, e)
    }
  }

  def getRlsByText(text: String) = {
    try {
      if(text == null || text.isEmpty)
        throw new IllegalArgumentException("Find request cannot be empty")

      val findText = if(text.length < 3) text + "%" else "%" + text + "%"
      val l = em.
        createQuery(rlsByTextContainingQuery, classOf[Nomenclature]).
        setParameter("value", findText).
        getResultList
      l.foreach(em.detach(_))
      l
    } catch {
      case e: Throwable => throw new CoreException(e.getMessage, e)
    }
  }


  val rlsByTextContainingQuery =
    """
       SELECT r FROM NomenclatureNew r WHERE r.tradeLocalName LIKE :value
    """

}
