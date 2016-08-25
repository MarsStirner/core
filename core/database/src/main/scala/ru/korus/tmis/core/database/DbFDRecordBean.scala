package ru.korus.tmis.core.database

import javax.ejb.Stateless
import javax.persistence.{EntityManager, PersistenceContext}

import ru.korus.tmis.core.entity.model.fd.FDRecord
import ru.korus.tmis.scala.util.I18nable

import scala.collection.JavaConversions._


@Stateless
class DbFDRecordBean extends DbFDRecordBeanLocal
with I18nable {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  def getFDRecordByIdWithOutDetach(recordId: Int) = {
    val result = em.createQuery(fdRecordByIdQuery, classOf[FDRecord])
      .setParameter("id", recordId)
      .getResultList
    result.size match {
      case 0 => null
      case size => result.iterator.next
    }
  }

  def getFDRecordById(recordId: Int) = {
    val result = em.createQuery(fdRecordByIdQuery, classOf[FDRecord])
      .setParameter("id", recordId)
      .getResultList
    result.size match {
      case 0 => null
      case size => result.iterator.next
    }
  }

  def getIdValueFDRecordByEventTypeId(flatDirectoryId: Int, eventTypeId: Int) = {
    val result = em.createQuery(IdValueFDRecordByEventTypeIdQuery, classOf[Array[AnyRef]])
      .setParameter("eventTypeId", eventTypeId)
      .setParameter("flatDirectoryId", flatDirectoryId)
      .getResultList
    val obj = result.iterator().next()
    (obj(0).asInstanceOf[java.lang.Integer], obj(1).asInstanceOf[String])
  }

  val fdRecordByIdQuery = """
    SELECT fdr
    FROM FDRecord fdr
    WHERE fdr.id = :id
                          """

  val IdValueFDRecordByEventTypeIdQuery = """
    SELECT Max(fdr.id), fdfv.value
    FROM
      FDRecord fdr,
      FDFieldValue fdfv,
      EventType et
    WHERE
      et.id = :eventTypeId
    AND
      fdr.flatDirectory.id = :flatDirectoryId
    AND
      fdfv.record.id = fdr.id
    AND
      et.name = fdfv.value
  """
}
