package ru.korus.tmis.core.database

import ru.korus.tmis.core.entity.model.RbHospitalBedProfile
import javax.persistence.{PersistenceContext, EntityManager}
import ru.korus.tmis.util.I18nable
import grizzled.slf4j.Logging
import javax.ejb.Stateless
import ru.korus.tmis.core.logging.LoggingInterceptor
import javax.interceptor.Interceptors

@Interceptors(Array(classOf[LoggingInterceptor]))
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
        em.detach(bedProfile)
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
        em.detach(bedProfile)
        bedProfile
      }
    }
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
}
