package ru.korus.tmis.core.database

import ru.korus.tmis.core.logging.db.LoggingInterceptor

import grizzled.slf4j.Logging
import javax.ejb.Stateless
import javax.interceptor.Interceptors
import javax.persistence.{EntityManager, PersistenceContext}

import scala.collection.JavaConversions._
import ru.korus.tmis.core.entity.model.Nomenclature

@Interceptors(Array(classOf[LoggingInterceptor]))
@Stateless
class DbRlsBean
  extends DbRlsBeanLocal
  with Logging {

  @PersistenceContext(unitName = "rls")
  var em: EntityManager = _

  def getRlsDrugList = {
    val result = em.createQuery(RlsSortedNomenQuery,
                                classOf[Nomenclature])
                 .getResultList
    result.foreach(em.detach(_))
    result
  }

  val RlsSortedNomenQuery = """
    SELECT n
    FROM Nomenclature n
    ORDER BY
      n.tradeName,
      n.iNPName,
      n.form,
      n.dosage,
      n.filling,
      n.packing
  """
}
