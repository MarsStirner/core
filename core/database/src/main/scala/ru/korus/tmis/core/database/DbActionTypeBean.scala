package ru.korus.tmis.core.database

import ru.korus.tmis.core.entity.model.{ActionPropertyType, ActionType}
import ru.korus.tmis.core.logging.db.LoggingInterceptor
import ru.korus.tmis.util.I18nable

import grizzled.slf4j.Logging
import javax.ejb.Stateless
import javax.interceptor.Interceptors
import javax.persistence.{EntityManager, PersistenceContext}

import scala.collection.JavaConversions._
import ru.korus.tmis.core.exception.CoreException

@Interceptors(Array(classOf[LoggingInterceptor]))
@Stateless
class DbActionTypeBean
  extends DbActionTypeBeanLocal
  with Logging
  with I18nable {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  def getActionTypeById(id: Int) = {
    val result = em.createQuery(ActionTypeByIdQuery,
                                classOf[ActionType])
                 .setParameter("id", id)
                 .getResultList

    val at = result.headOption.getOrElse{ throw new CoreException(i18n("error.actionTypeNotFound")) }
    em.detach(at)
    at
  }

  def getAssessmentTypes = {
    val result = em.createQuery(AssessmentTypesQuery,
                                classOf[ActionType])
                 .getResultList
    result.foreach((at) => em.detach(at))
    new java.util.HashSet(result)
  }

  def getDiagnosticTypes = {
    val result = em.createQuery(DiagnosticTypesQuery,
                                classOf[ActionType])
                 .getResultList
    result.foreach((at) => em.detach(at))
    new java.util.HashSet(result)
  }

  def getTreatmentTypes = {
    val result = em.createQuery(TreatmentTypesQuery,
                                classOf[ActionType])
                 .getResultList
    result.foreach((at) => em.detach(at))
    new java.util.HashSet(result)
  }

  def getDrugTreatmentTypes = {
    val result = em.createQuery(DrugTreatmentTypesQuery,
                                classOf[ActionType])
                 .getResultList
    result.foreach((at) => em.detach(at))
    new java.util.HashSet(result)
  }

  def getActionTypePropertiesById(actionTypeId: Int) = {
    val result = em.createQuery(ActionTypePropertiesByIdQuery,
                                classOf[ActionPropertyType])
                 .setParameter("actionTypeId", actionTypeId)
                 .getResultList
    result.foreach((apt) => em.detach(apt))
    result
  }

  val ActionTypeByIdQuery = """
    SELECT at
    FROM
      ActionType at
    WHERE
      at.id = :id
    AND
      at.deleted = 0
  """

  val ActionTypePropertiesByIdQuery = """
    SELECT apt
    FROM
      ActionPropertyType apt
    WHERE
      apt.actionType.id = :actionTypeId
    AND
      apt.deleted = 0
    ORDER BY
      apt.idx ASC
  """

  val ActionTypesByClassQuery = """
    SELECT at
    FROM
      ActionType at
    WHERE
      at.clazz = %s
    AND
      at.deleted = 0
  """

  val AssessmentTypesQuery =
    ActionTypesByClassQuery.format(i18n("db.action.assessmentClass"))

  val DiagnosticTypesQuery =
    ActionTypesByClassQuery.format(i18n("db.action.diagnosticClass"))

  val TreatmentTypesQuery =
    ActionTypesByClassQuery.format(i18n("db.action.treatmentClass"))

  val ActionTypesByNameQuery = """
    SELECT at
    FROM
      ActionType at
    WHERE
      at.name = "%s"
    AND
      at.deleted = 0
  """

  val DrugTreatmentTypesQuery =
    ActionTypesByNameQuery.format(i18n("db.at.drugTreatmentName"))
}
