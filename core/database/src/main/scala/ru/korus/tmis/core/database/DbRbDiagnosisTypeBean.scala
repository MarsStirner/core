package ru.korus.tmis.core.database

import javax.ejb.Stateless
import javax.persistence.{EntityManager, PersistenceContext}


import ru.korus.tmis.core.entity.model.RbDiagnosisType
import ru.korus.tmis.core.exception.NoSuchRbDiagnosisTypeException
import ru.korus.tmis.scala.util.{ConfigManager, I18nable}

import scala.collection.JavaConversions._
import scala.language.reflectiveCalls

/**
 * Методы для работы с таблицей Diagnostic
 * @author Ivan Dmitriev Systema-Soft
 */

@Stateless
class DbRbDiagnosisTypeBean extends DbRbDiagnosisTypeBeanLocal
with I18nable {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  def getRbDiagnosisTypeById(id: Int) = {
    val result = em.find(classOf[RbDiagnosisType], id)
    if (result != null) {
      result
    } else {
      throw new NoSuchRbDiagnosisTypeException(ConfigManager.ErrorCodes.RbDiagnosisTypeNotFound,
        id,
        i18n("error.rbDiagnosisTypeNotFound").format("id: %d".format(id)))
    }
  }

  def getRbDiagnosisTypeByFlatCode(flatCode: String) = {
    val result = em.createNamedQuery("RbDiagnosisType.findByFlatCode", classOf[RbDiagnosisType])
      .setParameter("flatCode", flatCode)
      .getResultList
    result.size match {
      case 0 => throw new NoSuchRbDiagnosisTypeException(ConfigManager.ErrorCodes.RbDiagnosisTypeNotFound,
        flatCode,
        i18n("error.rbDiagnosisTypeNotFound").format("flatCode: %s".format(flatCode)))
      case size => result.iterator.next
    }
  }

  /**
   * Возвращает тип даигноза \ диагностики по заданному коду
   * @param flatCode полский код типа дагноза\диагностики
   * @return тип диагноза \ диагностики с заданным кодом. null если по коду ничего не нашли
   */
  override def getByFlatCode(flatCode: String): RbDiagnosisType = {
    em.createNamedQuery("RbDiagnosisType.findByFlatCode", classOf[RbDiagnosisType])
      .setParameter("flatCode", flatCode)
      .getResultList.headOption.orNull
  }

  /**
   * Возвращает тип даигноза \ диагностики по заданному идентификатору
   * @param id  идентифкатор сущности
   * @return тип диагноза \ диагностики с заданным идентификатором. null если по коду ничего не нашли
   */
  override def getById(id: Int): RbDiagnosisType = {
    em.find(classOf[RbDiagnosisType], id)
  }
}
