package ru.korus.tmis.core.database

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
import scala.language.reflectiveCalls

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

        })
        result(0)
      }
    }
  }

  def insertClientAddress(addressTypeId: Int,
                                  addressEntry: AddressEntryContainer,
                                  patient: Patient,
                                  sessionUser: Staff): ClientAddress = {
    if(!addressEntry.getKladr() && "".equals(addressEntry.getFullAddress().trim)) {
      return null
    }
    val now = new Date

    var clientAddress: ClientAddress = new ClientAddress()
    var address: Address = new Address()
    var addressHouse: AddressHouse = new AddressHouse()

    if (clientAddress == null || clientAddress.getId == null) {
      clientAddress = new ClientAddress
      clientAddress.setAddressType(addressTypeId.shortValue())
      clientAddress.setPatient(patient)
      clientAddress.setCreatePerson(sessionUser)
      clientAddress.setCreateDatetime(now)

      address = new Address()
      address.setCreatePerson(sessionUser)
      address.setCreateDatetime(now)

      addressHouse = new AddressHouse()
      addressHouse.setCreatePerson(sessionUser)
      addressHouse.setCreateDatetime(now)

    }

    //если поля КЛАДР заполнены:
    if (addressEntry.getKladr()) {
      if (addressEntry.getCity() != null && addressEntry.getStreet() != null) {
        clientAddress.setAddress(address)
        address.setHouse(addressHouse)

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
        addressHouse.setKLADRCode(kladr_code)

        if (addressEntry.getStreet().getCode() != null)
          addressHouse.setKLADRStreetCode(addressEntry.getStreet().getCode())
        else
          addressHouse.setKLADRStreetCode("")

        address.setFlat(addressEntry.getFlat())

        addressHouse.setNumber(addressEntry.getHouse())
        addressHouse.setCorpus(addressEntry.getBuilding())
      }
      clientAddress.setFreeInput("")
    }
    else {
      //свободный ввод
      clientAddress.setAddress(null)
      clientAddress.setFreeInput(addressEntry.getFullAddress())
    }
    clientAddress.setLocalityType(addressEntry.getLocalityType().intValue())

    //служебные поля
    clientAddress.setDeleted(false)
    clientAddress.setModifyPerson(sessionUser)
    clientAddress.setModifyDatetime(now)

    address.setDeleted(false)
    address.setModifyPerson(sessionUser)
    address.setModifyDatetime(now)

    addressHouse.setDeleted(false)
    addressHouse.setModifyPerson(sessionUser)
    addressHouse.setModifyDatetime(now)

    clientAddress
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
