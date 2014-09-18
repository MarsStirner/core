package ru.korus.tmis.core.database

import javax.interceptor.Interceptors
import ru.korus.tmis.core.logging.LoggingInterceptor
import javax.ejb.Stateless
import grizzled.slf4j.Logging
import javax.persistence.{EntityManager, PersistenceContext}
import ru.korus.tmis.core.entity.model.OrgStructureHospitalBed
import scala.collection.JavaConversions._
import ru.korus.tmis.scala.util.I18nable
import scala.language.reflectiveCalls

/**
 * User: idmitriev
 * Date: 8/19/13
 * Time: 11:09 AM
 */
@Interceptors(Array(classOf[LoggingInterceptor]))
@Stateless
class DbOrgStructureHospitalBedBean extends DbOrgStructureHospitalBedBeanLocal
                                    with Logging
                                    with I18nable {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  def getHospitalBedByDepartmentId(departmentId: Int) = {
    val result = em.createQuery(AllHospitalBedsByDepartmentIdQuery, classOf[OrgStructureHospitalBed])
                  .setParameter("departmentId", departmentId)
                  .getResultList
    result.foreach(em.detach(_))
    result
  }

  def getBusyHospitalBedByIds(ids: java.util.Collection[java.lang.Integer]) = {
    val result = em.createQuery(BusyHospitalBedsByDepartmentIdQuery.format(i18n("db.action.movingFlatCode"),
                                                                           i18n("db.apt.moving.codes.hospitalBed")),
                                classOf[OrgStructureHospitalBed])
                  .setParameter("ids", asJavaCollection(ids))
                  .getResultList
    result.foreach(em.detach(_))
    result
  }

  val AllHospitalBedsByDepartmentIdQuery =
    """
    SELECT DISTINCT bed
    FROM
      OrgStructureHospitalBed bed
      WHERE
        bed.masterDepartment.id = :departmentId
    ORDER BY bed.id
    """


  val BusyHospitalBedsByDepartmentIdQuery =
    """
    SELECT DISTINCT bed
    FROM
      APValueHospitalBed apval
        JOIN apval.value bed,
      ActionProperty ap
        JOIN ap.actionPropertyType apt
        JOIN ap.action a
        JOIN a.actionType at
      WHERE
        apval.id.id = ap.id
      AND
        bed.id IN :ids
      AND
        at.flatCode = '%s'
      AND
        apt.code = '%s'
      AND
        a.endDate IS NULL
      AND
        a.deleted = 0
      AND
        ap.deleted = 0
      AND
        at.deleted = 0
      AND
        apt.deleted = 0
    """
}
