package ru.korus.tmis.core.database

import ru.korus.tmis.core.entity.model.Patient
import ru.korus.tmis.core.exception.NoSuchPatientException
import ru.korus.tmis.core.logging.db.LoggingInterceptor
import ru.korus.tmis.util.{ConfigManager, I18nable}

import grizzled.slf4j.Logging
import java.lang.Iterable
import javax.ejb.Stateless
import javax.interceptor.Interceptors
import javax.persistence.{EntityManager, PersistenceContext}

@Interceptors(Array(classOf[LoggingInterceptor]))
@Stateless
class DbPatientBean
  extends DbPatientBeanLocal
  with Logging
  with I18nable {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  def getAllPatients(): Iterable[Patient] = {
    em.createNamedQuery("Patient.findAll", classOf[Patient]).getResultList
  }

  def getPatientById(id: Int): Patient = {
    val result = em.createQuery(PatientFindQuery,
                                classOf[Patient])
                 .setParameter("id", id)
                 .getResultList

    result.size match {
      case 0 => {
        throw new NoSuchPatientException(
          ConfigManager.ErrorCodes.PatientNotFound,
          id,
          i18n("error.patientNotFound"))
      }
      case size => {
        val patient = result.iterator.next()
        em.detach(patient)
        patient
      }
    }
  }

  val PatientFindQuery = """
    SELECT p
    FROM
      Patient p
    WHERE
      p.id = :id
    AND
      p.deleted = 0
  """
}
