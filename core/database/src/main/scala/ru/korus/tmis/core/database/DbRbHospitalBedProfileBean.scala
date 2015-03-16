package ru.korus.tmis.core.database

import ru.korus.tmis.core.entity.model.RbHospitalBedProfile
import javax.persistence.{PersistenceContext, EntityManager}
import grizzled.slf4j.Logging
import javax.ejb.Stateless

import javax.interceptor.Interceptors
import java.lang.Iterable
import ru.korus.tmis.scala.util.I18nable


@Stateless
class DbRbHospitalBedProfileBean
  extends DbRbHospitalBedProfileBeanLocal
  with Logging
  with I18nable {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  def getRbHospitalBedProfileById(id: Int) = {
    val result = em.createQuery(RbHospitalBedProfileByIdQuery,
      classOf[RbHospitalBedProfile])
      .setParameter("id", id)
      .getResultList

    result.size() match {
      case 0 => {
        null
      }
      case size => {
        val bedProfile = result.iterator().next()

        bedProfile
      }
    }
  }

  def getRbHospitalBedProfileByName(name: String) = {
    val result = em.createQuery(RbHospitalBedProfileByNameQuery,
      classOf[RbHospitalBedProfile])
      .setParameter("name", "%" + name + "%")
      .getResultList

    result.size() match {
      case 0 => {
        null
      }
      case size => {
        val bedProfile = result.iterator().next()

        bedProfile
      }
    }
  }

  def getAllRbHospitalBedProfiles: Iterable[RbHospitalBedProfile] = {
    val result = em.createQuery(allRbHospitalBedProfiles,
      classOf[RbHospitalBedProfile]).getResultList
    result
  }

  val RbHospitalBedProfileByIdQuery = """
    SELECT hbp
    FROM  RbHospitalBedProfile hbp
    WHERE hbp.id = :id
                                      """

  val RbHospitalBedProfileByNameQuery = """
    SELECT hbp
    FROM  RbHospitalBedProfile hbp
    WHERE hbp.name LIKE :name
                                        """

  val allRbHospitalBedProfiles = """
    SELECT hbp
    FROM RbHospitalBedProfile hbp
                                 """
}
