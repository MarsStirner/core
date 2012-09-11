package ru.korus.tmis.core.database


import ru.korus.tmis.core.logging.db.LoggingInterceptor
import ru.korus.tmis.core.entity.model.ActionPropertyType
import ru.korus.tmis.util.I18nable

import grizzled.slf4j.Logging
import java.util.{Collections, HashSet}
import javax.ejb.Stateless
import javax.interceptor.Interceptors
import javax.persistence.{TypedQuery, PersistenceContext, EntityManager}

import scala.collection.JavaConversions._

@Interceptors(Array(classOf[LoggingInterceptor]))
@Stateless
class DbActionPropertyTypeBean
  extends DbActionPropertyTypeBeanLocal
  with Logging
  with I18nable {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  def getActionPropertyTypeById(id: Int) = {
    val result = em.createQuery(AptByIdQuery,
                                classOf[ActionPropertyType])
                 .setParameter("id", id)
                 .getResultList

    val apt = result(0)
    em.detach(apt)
    apt
  }

  def getDepartmentAPT() = {
    getAptByName(i18n("db.apt.departmentName"))
  }

  def getHospitalBedAPT() = {
    getAptByName(i18n("db.apt.hospitalBedName"))
  }

  def getAnamnesisAPT() = {
    getAptByNameAndDename(i18n("db.apt.anamnesisName"),
                          i18n("db.apt.anamnesisDename"),
                          i18n("db.apt.allergoanamnesisName"))
  }

  def getAllergoanamnesisAPT() = {
    getAptByName(i18n("db.apt.allergoanamnesisName"))
  }

  def getDiagnosisAPT() = {
    getAptByNameAndName(i18n("db.apt.diagnosisName01"),
                        i18n("db.apt.diagnosisName02"))
  }

  def getDrugNomenAPT() = {
    getAptByName(i18n("db.apt.drugName"))
  }

  def getDosageAPT() = {
    getAptByName(i18n("db.apt.dosageName"))
  }

  //////////////////////////////////////////////////////////////////////////////

  def getAptByName(name: String) = {
    getAptByQuery(em.createQuery(AptByNameQuery,
                                 classOf[ActionPropertyType])
                  .setParameter("aptName", name))
  }

  def getAptByNameAndName(name01: String,
                          name02: String) = {
    getAptByQuery(em.createQuery(AptByNameAndNameQuery,
                                 classOf[ActionPropertyType])
                  .setParameter("aptName01", name01)
                  .setParameter("aptName02", name02))
  }

  def getAptByNameAndDename(name: String,
                            dename01: String,
                            dename02: String) = {
    getAptByQuery(em.createQuery(AptByNameAndDenameQuery,
                                 classOf[ActionPropertyType])
                  .setParameter("aptName", name)
                  .setParameter("aptDename01", dename01)
                  .setParameter("aptDename02", dename02))
  }

  def getAptByQuery(query: TypedQuery[ActionPropertyType]) = {
    val result = query.getResultList
    result.foreach((apt) => em.detach((apt)))
    result.size match {
      case 0 => Collections.emptySet[ActionPropertyType]
      case size => new HashSet[ActionPropertyType](result)
    }
  }

  val AptByIdQuery = """
    SELECT apt
    FROM
      ActionPropertyType apt
    WHERE
      apt.deleted = 0
    AND
      apt.id = :id
  """

  val AptByNameQuery = """
    SELECT apt
    FROM
      ActionPropertyType apt
    WHERE
      apt.deleted = 0
    AND
      apt.name like :aptName
  """

  val AptByNameAndNameQuery = """
    SELECT apt
    FROM
      ActionPropertyType apt
    WHERE
      apt.deleted = 0
    AND
      apt.name like :aptName01
    OR
      apt.name like :aptName02
  """

  val AptByNameAndDenameQuery = """
    SELECT apt
    FROM
      ActionPropertyType apt
    WHERE
      apt.deleted = 0
    AND
      apt.name like :aptName
    AND
      apt.name not like :aptDename01
    AND
      apt.name not like :aptDename02
  """
}
