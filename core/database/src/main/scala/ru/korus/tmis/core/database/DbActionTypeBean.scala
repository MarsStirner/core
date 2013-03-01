package ru.korus.tmis.core.database

import ru.korus.tmis.core.entity.model.{ActionPropertyType, ActionType}
import ru.korus.tmis.core.logging.LoggingInterceptor
import ru.korus.tmis.util.I18nable

import grizzled.slf4j.Logging
import javax.ejb.Stateless
import javax.interceptor.Interceptors
import javax.persistence.{EntityManager, PersistenceContext}

import scala.collection.JavaConversions._
import ru.korus.tmis.core.exception.CoreException
import ru.korus.tmis.core.data.{ActionTypesListRequestDataFilter, QueryDataStructure}
import ru.korus.tmis.core.filter.ListDataFilter

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

    val at = result.headOption.getOrElse {
      throw new CoreException(i18n("error.actionTypeNotFound"))
    }
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

  //Вернем ActionType по значению code
  def getActionTypeByCode(code: String) = {
    val result = em.createQuery(ActionTypeByCodeQuery, classOf[ActionType])
      .setParameter("code", code)
      .getResultList

    val at = result(0)
    em.detach(at)
    at
  }

  def getActionTypesByCode(code: String) = {
    val result = em.createQuery(ActionTypeByCodeQuery, classOf[ActionType])
      .setParameter("code", code)
      .getResultList

    result.foreach((at) => em.detach(at))
    new java.util.HashSet(result)
  }

  def getCountAllActionTypeWithFilter(filter: Object) = {

    val queryStr: QueryDataStructure = if (filter.isInstanceOf[ActionTypesListRequestDataFilter]) {
      filter.asInstanceOf[ActionTypesListRequestDataFilter].toQueryStructure()
    }
    else {
      new QueryDataStructure()
    }

    var typed = em.createQuery(AllActionTypeWithFilterQuery.format("count(at)", queryStr.query, ""), classOf[Long])
    if (queryStr.data.size() > 0) {
      queryStr.data.foreach(qdp => typed.setParameter(qdp.name, qdp.value))
    }
    val result = typed.getSingleResult()
    result
  }

  def getAllActionTypeWithFilter(page: Int, limit: Int, sorting: String, filter: ListDataFilter) = {

    val queryStr = filter.toQueryStructure()

    val typed = em.createQuery(AllActionTypeWithFilterQuery.format("at", queryStr.query, sorting), classOf[ActionType])
                  .setMaxResults(limit)
                  .setFirstResult(limit * page)

    if (queryStr.data.size() > 0) {
      queryStr.data.foreach(qdp => typed.setParameter(qdp.name, qdp.value))
    }

    val result = typed.getResultList

    result.foreach(at => em.detach(at))
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

  val ActionTypeByCodeQuery = """
    SELECT at
    FROM
      ActionType at
    WHERE
      at.code = :code
    AND
      at.deleted = 0
                              """

  val AllActionTypeWithFilterQuery = """
    SELECT %s
    FROM
      ActionType at
    WHERE
      at.deleted = 0
    %s
    %s
                                     """

  val countOfChildrenForActionTypeList = """
  SELECT
    at.groupId, count(at)
  FROM
    ActionType at
  WHERE
     at.groupId IN :atIds
  AND
    at.deleted = 0
  GROUP BY at.groupId
                                         """
}
