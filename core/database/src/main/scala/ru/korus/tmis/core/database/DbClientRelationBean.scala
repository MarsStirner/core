package ru.korus.tmis.core.database

import grizzled.slf4j.Logging
import ru.korus.tmis.core.logging.LoggingInterceptor
import javax.interceptor.Interceptors
import javax.persistence.{EntityManager, PersistenceContext}
import javax.ejb.{EJB, Stateless}
import java.lang.Iterable
import ru.korus.tmis.util.{ConfigManager, I18nable}
import java.util.Date
import ru.korus.tmis.core.exception.NoSuchClientRelationException
import ru.korus.tmis.core.data.{RelationEntryContainer, ClientContactContainer}
import ru.korus.tmis.core.entity.model.{Staff, Patient, ClientRelation}
import scala.collection.JavaConversions._
import scala.util.control.Breaks._

@Interceptors(Array(classOf[LoggingInterceptor]))
@Stateless
class DbClientRelationBean
  extends DbClientRelationBeanLocal
  with Logging
  with I18nable {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  @EJB
  var dbRbRelationType: DbRbRelationTypeBeanLocal = _

  @EJB
  var dbPatient: DbPatientBeanLocal = _

  @EJB
  var dbClientContact: DbClientContactBeanLocal = _

  val ClientRelationFindQuery = """
    SELECT d
    FROM
      ClientRelation d
    WHERE
      d.id = :id
                                """

  val ClientRelationFindByRelativeQuery = """
    SELECT d
    FROM
      ClientRelation d
    WHERE
      d.relative.id = :id
                                """


  def getAllClientRelations(patientId: Int): Iterable[ClientRelation] = {
    em.createNamedQuery("ClientRelation.findAll", classOf[ClientRelation]).getResultList
  }

  def getClientRelationById(id: Int): ClientRelation = {
    val result = em.createQuery(ClientRelationFindQuery,
      classOf[ClientRelation])
      .setParameter("id", id)
      .getResultList

    result.size match {
      case 0 => {
        throw new NoSuchClientRelationException(
          ConfigManager.ErrorCodes.ClientRelationNotFound,
          id,
          i18n("error.clientRelationNotFound").format(id))
      }
      case size => {
        result.foreach(rbType => {
          em.detach(rbType)
        })
        result(0)
      }
    }
  }

  def getClientRelationByRelativeId(id: Int): ClientRelation = {
    val result = em.createQuery(ClientRelationFindByRelativeQuery,
      classOf[ClientRelation])
      .setParameter("id", id)
      .getResultList

    result.size match {
      case 0 => {
        throw new NoSuchClientRelationException(
          ConfigManager.ErrorCodes.ClientRelationNotFound,
          id,
          i18n("error.clientRelationNotFound").format(id))
      }
      case size => {
        result.foreach(rbType => {
          em.detach(rbType)
        })
        result(0)
      }
    }
  }

  def insertOrUpdateClientRelation(id: Int,
                                   rbRelationTypeId: Int,
                                   firstName: String,
                                   lastName: String,
                                   middleName: String,
                                   contacts: Iterable[ClientContactContainer],
                                   patient: Patient,
                                   sessionUser: Staff): ClientRelation = {
    var d: ClientRelation = null
    var relative: Patient = null
    val now = new Date
    if (id > 0) {
      d = getClientRelationById(id)
      relative = d.getRelative
    }
    else {
      d = new ClientRelation
      d.setCreatePerson(sessionUser)
      d.setCreateDatetime(now)
    }

    //распарсить и сохранить контакты родственника:
    //1. создаем/обновляем родственника
    relative = dbPatient.insertOrUpdatePatient(
      if (id < 1) {
        -1
      } else {
        d.getRelative().getId().intValue()
      },
      firstName,
      middleName,
      lastName,
      now, //bd//TODO: надо бы забирать из интерфейса при вводе данных родственника
      "",
      "n/a", //sex//TODO: надо бы забирать из интерфейса при вводе данных родственника
      "", //weight, required
      "", //height, required
      "", //TODO: надо бы забирать из интерфейса при вводе данных родственника
      null, //bloodDate//TODO: надо бы забирать из интерфейса при вводе данных родственника
      1, //blood type//TODO: надо бы забирать из интерфейса при вводе данных родственника
      "", //bloodnotes required
      "", //notes, required
      null, //sessionUser - AuthData.user
      0
    )
    //2. обновляем контакты родственника
    var set = Set.empty[Int]
    val clientContacts = contacts
    val patientContacts = relative.getActiveClientContacts() //relativeContacts
    patientContacts.foreach(
      (serverContact) => {
        val result = clientContacts.find {
          element => element.getId() == serverContact.getId().intValue()
        }
        val clientContact = result.getOrElse(null)
        if (clientContact != null) {
          set = set + clientContact.getId()
          var tempServContact = serverContact
          tempServContact = dbClientContact.insertOrUpdateClientContact(
            clientContact.getId(),
            clientContact.getTypeId(),
            clientContact.getNumber(),
            clientContact.getComment(),
            relative,
            null
          )
        } else {
          dbClientContact.deleteClientContact(serverContact.getId().intValue(), null)
        }
      }
    )
    clientContacts.foreach((clientContact) => {
      var j = 0
      breakable {
        set.foreach(i => {
          if (i == clientContact.getId()) {
            j = j + 1
            break
          }
        })
      }
      if (j == 0) {
        dbClientContact.insertOrUpdateClientContact(
          clientContact.getId(),
          clientContact.getTypeId(),
          clientContact.getNumber(),
          clientContact.getComment(),
          relative,
          null
        )
      }
    })
    //

    if (rbRelationTypeId > 0) {
      d.setRelativeType(dbRbRelationType.getRbRelationTypeById(rbRelationTypeId))
    }

    if (relative != null) {
      //(d.getRelative() == null) {
      d.setRelative(relative)
    }
    if (d.getPatient() == null) {
      d.setPatient(patient)
    }

    d.setDeleted(false)
    d.setModifyPerson(sessionUser)
    d.setModifyDatetime(now)

    d
  }

  def insertOrUpdateClientRelationByRelativePerson( id: Int,
                                                    rbRelationTypeId: Int,
                                                    relative: Patient,
                                                    patient: Patient,
                                                    sessionUser: Staff): ClientRelation = {
    var d: ClientRelation = null
    val now = new Date
    if (id > 0) {
      d = getClientRelationById(id)
    }
    else {
      d = new ClientRelation
      d.setCreatePerson(sessionUser)
      d.setCreateDatetime(now)
    }


    if (rbRelationTypeId > 0) {
      d.setRelativeType(dbRbRelationType.getRbRelationTypeById(rbRelationTypeId))
    }

    if (relative != null) {
      d.setRelative(relative)
    }

    if (d.getPatient() == null) {
      d.setPatient(patient)
    }

    d.setDeleted(false)
    d.setModifyPerson(sessionUser)
    d.setModifyDatetime(now)

    d
  }

  def deleteClientRelation(id: Int,
                           sessionUser: Staff) = {
    val d = getClientRelationById(id)
    val now = new Date
    d.setDeleted(true)
    d.setModifyPerson(sessionUser)
    d.setModifyDatetime(now)
  }
}