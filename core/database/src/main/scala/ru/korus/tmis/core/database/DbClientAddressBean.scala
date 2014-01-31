package ru.korus.tmis.core.database

import ru.korus.tmis.core.logging.LoggingInterceptor
import javax.interceptor.Interceptors

import java.lang.Iterable
import grizzled.slf4j.Logging
import javax.persistence.{EntityManager, PersistenceContext}
import javax.ejb.{EJB, Stateless}
import ru.korus.tmis.core.exception.NoSuchClientAddressException
import java.util.Date
import ru.korus.tmis.core.data.AddressEntryContainer
import ru.korus.tmis.core.entity.model._
import scala.collection.JavaConversions._
import ru.korus.tmis.scala.util.{I18nable, ConfigManager}

@Interceptors(Array(classOf[LoggingInterceptor]))
@Stateless
class DbClientAddressBean
  extends DbClientAddressBeanLocal
  with Logging
  with I18nable {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  val ClientAddressFindQuery = """
    SELECT d
    FROM
      ClientAddress d
    WHERE
      d.id = :id
                               """

  val ClientAddressFindByClientAndTypeQuery = """
    SELECT d
    FROM
      ClientAddress d
    WHERE
      d.patient.id = :patientId
    AND
      d.addressType = :addressType
    AND
      d.deleted = 0
    ORDER BY d.modifyDatetime DESC
                                              """

  def getAllAddresses(patientId: Int): Iterable[ClientAddress] = {
    em.createNamedQuery("ClientAddress.findAll", classOf[ClientAddress]).getResultList
  }

  def getClientAddressByClientIdAddressType(patientId: Int, addressType: Int): ClientAddress = {
    val result = em.createQuery(ClientAddressFindByClientAndTypeQuery,
      classOf[ClientAddress])
      .setParameter("patientId", patientId)
      .setParameter("addressType", addressType)
      .getResultList

    result.size match {
      case 0 => {
        null
      }
      case size => {
        result.foreach(rbType => {
          em.detach(rbType)
        })
        result(0)
      }
    }
  }

  def getClientAddressById(id: Int): ClientAddress = {
    val result = em.createQuery(ClientAddressFindQuery,
      classOf[ClientAddress])
      .setParameter("id", id)
      .getResultList

    result.size match {
      case 0 => {
        throw new NoSuchClientAddressException(
          ConfigManager.ErrorCodes.ClientAddressNotFound,
          id,
          i18n("error.clientAddressNotFound").format(id))
      }
      case size => {
        result.foreach(rbType => {
          em.detach(rbType)
        })
        result(0)
      }
    }
  }

  def insertOrUpdateClientAddress(addressTypeId: Int,
                                  addressEntry: AddressEntryContainer,
                                  patient: Patient,
                                  sessionUser: Staff): ClientAddress = {
    val now = new Date

    var d: ClientAddress = new ClientAddress()
    var d1: Address = new Address()
    var d2: AddressHouse = new AddressHouse()
    if (patient.getId != null) {
      d = getClientAddressByClientIdAddressType(patient.getId.intValue(), addressTypeId)
    }

    if (d == null || d.getId == null) {
      d = new ClientAddress
      d.setAddressType(addressTypeId.shortValue())
      d.setPatient(patient)
      d.setCreatePerson(sessionUser)
      d.setCreateDatetime(now)

      d1 = new Address()
      d1.setCreatePerson(sessionUser)
      d1.setCreateDatetime(now)

      d2 = new AddressHouse()
      d2.setCreatePerson(sessionUser)
      d2.setCreateDatetime(now)

    }
    else {
      //  d.setPatient(patient)
      //  d.setCreatePerson(sessionUser)
      //  d.setCreateDatetime(now)
      d1 = d.getAddress()
      if (d1 == null) {
        d1 = new Address()
        d1.setCreatePerson(sessionUser)
        d1.setCreateDatetime(now)
      }
      d2 = d1.getHouse()
      if (d2 == null) {
        d2 = new AddressHouse()
        d2.setCreatePerson(sessionUser)
        d2.setCreateDatetime(now)
      }
    }

    //если поля КЛАДР заполнены:
    if (addressEntry.getKladr()) {
      if (addressEntry.getCity() != null && addressEntry.getStreet() != null) {
        d.setAddress(d1)
        d1.setHouse(d2)

        val kladr_code =
          if (addressEntry.getLocality() != null && addressEntry.getLocality().getCode() != null && addressEntry.getLocality().getCode() != "") {
            addressEntry.getLocality().getCode()
          } else if (addressEntry.getCity() != null && addressEntry.getCity().getCode() != null && addressEntry.getCity().getCode() != "") {
            addressEntry.getCity().getCode()
          } else if (addressEntry.getDistrict() != null && addressEntry.getDistrict().getCode() != null && addressEntry.getDistrict().getCode() != "") {
            addressEntry.getDistrict().getCode()
          } else if (addressEntry.getRepublic() != null && addressEntry.getRepublic().getCode() != null && addressEntry.getRepublic().getCode() != "") {
            addressEntry.getRepublic().getCode()
          } else {
            ""
          }
        d2.setKLADRCode(kladr_code)

        if (addressEntry.getStreet().getCode() != null)
          d2.setKLADRStreetCode(addressEntry.getStreet().getCode())
        else
          d2.setKLADRStreetCode("")

        d1.setFlat(addressEntry.getFlat())

        d2.setNumber(addressEntry.getHouse())
        d2.setCorpus(addressEntry.getBuilding())
      }
      d.setFreeInput("")
    }
    else {
      //свободный ввод
      d.setAddress(null)
      d.setFreeInput(addressEntry.getFullAddress())
    }
    d.setLocalityType(addressEntry.getLocalityType().intValue())

    //служебные поля
    d.setDeleted(false)
    d.setModifyPerson(sessionUser)
    d.setModifyDatetime(now)

    d1.setDeleted(false)
    d1.setModifyPerson(sessionUser)
    d1.setModifyDatetime(now)

    d2.setDeleted(false)
    d2.setModifyPerson(sessionUser)
    d2.setModifyDatetime(now)

    d
  }

  def deleteClientAddress(id: Int,
                          sessionUser: Staff) = {
    val now = new Date

    val d = getClientAddressById(id)
    d.setDeleted(true)
    d.setModifyPerson(sessionUser)
    d.setModifyDatetime(now)

    val d1 = d.getAddress()
    if (d1 != null) {
      d1.setDeleted(true)
      d1.setModifyPerson(sessionUser)
      d1.setModifyDatetime(now)

      val d2 = d1.getHouse()
      if (d2 != null) {
        d2.setDeleted(true)
        d2.setModifyPerson(sessionUser)
        d2.setModifyDatetime(now)
      }
    }
  }
}
