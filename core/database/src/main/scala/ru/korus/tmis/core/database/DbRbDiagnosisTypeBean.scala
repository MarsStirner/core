package ru.korus.tmis.core.database

import javax.interceptor.Interceptors
import ru.korus.tmis.core.logging.LoggingInterceptor
import javax.ejb.Stateless
import grizzled.slf4j.Logging
import javax.persistence.{EntityManager, PersistenceContext}
import ru.korus.tmis.core.entity.model.RbDiagnosisType
import ru.korus.tmis.core.exception.NoSuchRbDiagnosisTypeException
import scala.collection.JavaConversions._
import ru.korus.tmis.scala.util.{I18nable, ConfigManager}
import scala.language.reflectiveCalls

/**
 * Методы для работы с таблицей Diagnostic
 * @author Ivan Dmitriev Systema-Soft
 */
@Interceptors(Array(classOf[LoggingInterceptor]))
@Stateless
class DbRbDiagnosisTypeBean  extends DbRbDiagnosisTypeBeanLocal
                                with Logging
                                with I18nable {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  def getRbDiagnosisTypeById(id: Int) = {
    val result = em.createQuery(RbDiagnosisTypeFindQuery.format(ByIdSubQuery),classOf[RbDiagnosisType])
                   .setParameter("id", id)
                   .getResultList

    result.size match {
      case 0 => {
        throw new NoSuchRbDiagnosisTypeException( ConfigManager.ErrorCodes.RbDiagnosisTypeNotFound,
                                                  id,
                                                  i18n("error.rbDiagnosisTypeNotFound").format("id: %d".format(id)))
      }
      case size => {
        result.foreach(em.detach(_))
        result(0)
      }
    }
  }

  def getRbDiagnosisTypeByFlatCode(flatCode: String) = {
    val result = em.createQuery(RbDiagnosisTypeFindQuery.format(ByFlatCodeSubQuery),classOf[RbDiagnosisType])
      .setParameter("flatCode", flatCode)
      .getResultList

    result.size match {
      case 0 => {
        throw new NoSuchRbDiagnosisTypeException( ConfigManager.ErrorCodes.RbDiagnosisTypeNotFound,
          flatCode,
          i18n("error.rbDiagnosisTypeNotFound").format("flatCode: %s".format(flatCode)))
      }
      case size => {
        result.foreach(em.detach(_))
        result(0)
      }
    }
  }

  val RbDiagnosisTypeFindQuery =
    """
      SELECT dt
      FROM RbDiagnosisType dt
      WHERE
      %s
    """

  val ByIdSubQuery = """ dt.id = :id """

  val ByFlatCodeSubQuery = """ dt.flatCode = :flatCode """
}
