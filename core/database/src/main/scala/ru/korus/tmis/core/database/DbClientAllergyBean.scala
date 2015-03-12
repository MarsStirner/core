package ru.korus.tmis.core.database

import grizzled.slf4j.Logging
import javax.persistence.{EntityManager, PersistenceContext}
import javax.ejb.{Stateless}
import ru.korus.tmis.core.entity.model.{Staff, Patient, ClientAllergy}
import java.util.Date
import ru.korus.tmis.core.exception.NoSuchClientAllergyException
import scala.collection.JavaConversions._
import java.lang.Iterable
import ru.korus.tmis.scala.util.{I18nable, ConfigManager}
import scala.language.reflectiveCalls

@Stateless
class DbClientAllergyBean
  extends DbClientAllergyBeanLocal
  with Logging
  with I18nable {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  val ClientAllergyFindQuery = """
    SELECT d
    FROM
      ClientAllergy d
    WHERE
      d.id = :id
                               """

  def getAllClientAllergy(patientId: Int): Iterable[ClientAllergy] = {
    em.createNamedQuery("ClientAllergy.findAll", classOf[ClientAllergy]).getResultList
  }

  def getClientAllergyById(id: Int): ClientAllergy = {
    val result = em.createQuery(ClientAllergyFindQuery,
      classOf[ClientAllergy])
      .setParameter("id", id)
      .getResultList

    result.size match {
      case 0 => {
        throw new NoSuchClientAllergyException(
          ConfigManager.ErrorCodes.ClientAllergyNotFound,
          id,
          i18n("error.clientAllergyNotFound").format(id))
      }
      case size => {
        result.foreach(rbType => {
          em.detach(rbType)
        })
        result(0)
      }
    }
  }

  def insertOrUpdateClientAllergy(id: Int,
                                  power: Int,
                                  nameSubstance: String,
                                  createDate: Date,
                                  notes: String,
                                  patient: Patient,
                                  sessionUser: Staff): ClientAllergy = {
    var d: ClientAllergy = null
    val now = new Date()
    if (id > 0) {
      d = getClientAllergyById(id)
    }
    else {
      d = new ClientAllergy()
      d.setCreatePerson(sessionUser)
      d.setCreateDatetime(now)
    }

    d.setPower(power)
    d.setNameSubstance(nameSubstance)
    d.setCreateDate(createDate)
    d.setNotes(notes)
    d.setPatient(patient)

    d.setDeleted(false)
    d.setModifyPerson(sessionUser)
    d.setModifyDatetime(now)

    d
  }

  def deleteClientAllergy(id: Int,
                          sessionUser: Staff) = {
    val d = getClientAllergyById(id)
    val now = new Date()
    d.setDeleted(true)
    d.setModifyPerson(sessionUser)
    d.setModifyDatetime(now)
  }
}