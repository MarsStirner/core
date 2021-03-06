package ru.korus.tmis.core.database


import java.util

import grizzled.slf4j.Logging
import javax.persistence.{EntityManager, PersistenceContext}
import javax.ejb.{EJB, Stateless}
import ru.korus.tmis.core.exception.NoSuchClientContactException
import ru.korus.tmis.core.entity.model.{Staff, Patient, ClientContact}
import java.util.Date
import java.lang.Iterable

import ru.korus.tmis.scala.util.{I18nable, ConfigManager}
import scala.language.reflectiveCalls

@Stateless
class DbClientContactBean
  extends DbClientContactBeanLocal
  with Logging
  with I18nable {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  @EJB
  var dbRbContactType: DbRbContactTypeBeanLocal = _

  def getAllContacts(patientId: Int): Iterable[ClientContact] = {
    em.createNamedQuery("ClientContact.findAll", classOf[ClientContact]).getResultList
  }

  def getClientContactById(id: Int): ClientContact = {
    val result = em.find(classOf[ClientContact], id)
    if(result == null){
      throw new NoSuchClientContactException(ConfigManager.ErrorCodes.ClientContactNotFound, id, i18n("error.clientContactNotFound").format(id))
    }  else {
      result
    }
  }

  def insertOrUpdateClientContact(id: Int,
                                  rbContactTypeId: Int,
                                  contact: String,
                                  notes: String,
                                  patient: Patient,
                                  sessionUser: Staff): ClientContact = {
    var c: ClientContact = null
    val now = new Date
    if (id > 0) {
      c = getClientContactById(id)
      if(rbContactTypeId>0)
        c.setContactType(dbRbContactType.getRbContactTypeById(rbContactTypeId))
    }
    else {
      c = new ClientContact
      c.setCreatePerson(sessionUser)
      c.setCreateDatetime(now)
      c.setContactType(dbRbContactType.getRbContactTypeById(rbContactTypeId))
    }

    c.setContact(contact)
    c.setNotes(notes)
    c.setPatient(patient)

    c.setDeleted(false)
    c.setModifyPerson(sessionUser)
    c.setModifyDatetime(now)

    c
  }

  def deleteClientContact(id: Int, sessionUser: Staff) = {
    val c = getClientContactById(id)
    val now = new Date
    c.setDeleted(true)
    c.setModifyPerson(sessionUser)
    c.setModifyDatetime(now)
  }

  override def getActiveClientContacts(patientId: Int): util.List[ClientContact] ={
    em.createQuery("SELECT c FROM ClientContact c WHERE c.deleted = false AND c.patient.id = :patientId ORDER BY c.createDatetime DESC", classOf[ClientContact])
      .setParameter("patientId", patientId).getResultList
  }
}
