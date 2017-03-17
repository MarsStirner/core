package ru.korus.tmis.core.database

import java.lang.Iterable
import java.util.Date
import javax.ejb.Stateless
import javax.persistence.{EntityManager, PersistenceContext}

import ru.korus.tmis.core.entity.model.{ClientIntoleranceMedicament, Patient, Staff}
import ru.korus.tmis.core.exception.NoSuchClientIntoleranceMedicamentException
import ru.korus.tmis.scala.util.{ConfigManager, I18nable}

import scala.language.reflectiveCalls

@Stateless
class DbClientIntoleranceMedicamentBean
  extends DbClientIntoleranceMedicamentBeanLocal
  with I18nable {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  val ClientIntoleranceMedicamentFindQuery =
    """
    SELECT d
    FROM
      ClientIntoleranceMedicament d
    WHERE
      d.id = :id
    """


  def getAllClientIntoleranceMedicament(patientId: Int): Iterable[ClientIntoleranceMedicament] = {
    em.createNamedQuery("ClientIntoleranceMedicament.findAll", classOf[ClientIntoleranceMedicament]).getResultList
  }

  def getClientIntoleranceMedicamentById(id: Int): ClientIntoleranceMedicament = {
    val result = em.createQuery(ClientIntoleranceMedicamentFindQuery,
      classOf[ClientIntoleranceMedicament])
      .setParameter("id", id)
      .getResultList

    result.size match {
      case 0 => throw new NoSuchClientIntoleranceMedicamentException(
        ConfigManager.ErrorCodes.ClientIntoleranceMedicamentNotFound,
        id,
        i18n("error.ClientIntoleranceMedicamentNotFound").format(id))

      case _ => result.iterator().next()
    }
  }

  def insertOrUpdateClientIntoleranceMedicament(id: Int,
                                                power: Int,
                                                nameMedicament: String,
                                                createDate: Date,
                                                notes: String,
                                                patient: Patient,
                                                sessionUser: Staff): ClientIntoleranceMedicament = {
    var d: ClientIntoleranceMedicament = null
    val now = new Date()
    if (id > 0) {
      d = getClientIntoleranceMedicamentById(id)
    }
    else {
      d = new ClientIntoleranceMedicament()
      d.setCreatePerson(sessionUser)
      d.setCreateDatetime(now)
    }

    d.setPower(power)
    d.setNameMedicament(nameMedicament)
    d.setCreateDate(createDate)
    d.setNotes(notes)
    d.setPatient(patient)

    d.setDeleted(false)
    d.setModifyPerson(sessionUser)
    d.setModifyDatetime(now)

    d
  }

  def deleteClientIntoleranceMedicament(id: Int,
                                        sessionUser: Staff): Unit = {
    val d = getClientIntoleranceMedicamentById(id)
    val now = new Date()
    d.setDeleted(true)
    d.setModifyPerson(sessionUser)
    d.setModifyDatetime(now)
  }
}