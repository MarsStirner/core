package ru.korus.tmis.core.database

import java.util




import javax.ejb.Stateless
import javax.interceptor.Interceptors
import javax.persistence.{EntityManager, PersistenceContext}

import scala.collection.JavaConversions._
import ru.korus.tmis.core.entity.model.{RlsBalanceOfGood, Nomenclature}
import ru.korus.tmis.core.exception.CoreException


@Stateless
class DbRlsBean
  extends DbRlsBeanLocal
 {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  def getRlsById(id: Int): Nomenclature = {
    try {
      if(id < 1)
        throw new IllegalArgumentException("Invalid id value id=" + id)

      val n = em.find(classOf[Nomenclature], id)

      n
    } catch {
      case e: Throwable => throw new CoreException(e.getMessage, e)
    }
  }

  def getRlsByText(text: String): util.List[Nomenclature] = {
    try {
      if(text == null || text.isEmpty)
        throw new IllegalArgumentException("Find request cannot be empty")

      val findText = if(text.length < 3) text + "%" else "%" + text + "%"
      val l = em.
        createQuery(rlsByTextContainingQuery, classOf[Nomenclature]).
        setParameter("value", findText).
        getResultList

      l
    } catch {
      case e: Throwable => throw new CoreException(e.getMessage, e)
    }
  }


  val rlsByTextContainingQuery =
    """
       SELECT r FROM Nomenclature r WHERE r.tradeLocalName LIKE :value
    """

  override def getRlsBalanceOfGood(nomen: Nomenclature): util.List[RlsBalanceOfGood] = {
    val l = em.
      createNamedQuery("RlsBalanceOfGood.findByCode", classOf[RlsBalanceOfGood]).
      setParameter("code", if(nomen == null) 0 else nomen.getId).
      getResultList

    l
  }
}
