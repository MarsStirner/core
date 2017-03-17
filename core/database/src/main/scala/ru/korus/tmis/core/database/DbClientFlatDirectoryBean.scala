package ru.korus.tmis.core.database

import javax.persistence.{PersistenceContext, EntityManager}
import javax.ejb.Stateless
import org.slf4j.{LoggerFactory, Logger}
import ru.korus.tmis.core.entity.model.{Staff, Patient}
import ru.korus.tmis.core.entity.model.fd.{FDRecord, ClientFDProperty, ClientFlatDirectory}
import java.util.Date
import ru.korus.tmis.core.exception.CoreException
import ru.korus.tmis.scala.util.I18nable

@Stateless
class DbClientFlatDirectoryBean
  extends DbClientFlatDirectoryBeanLocal
  with I18nable {
  val logger:Logger = LoggerFactory.getLogger(this.getClass)

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  def insertOrUpdateClientFlatDirectory(fdClientId: Int, fdPropertyId: Int, fdRecordId: Int, patient: Patient, sessionUser: Staff): ClientFlatDirectory = {

    var fdClient: ClientFlatDirectory = null

    try {
      if (fdClientId > 0) {
        //Update
        fdClient = this.getClientFlatDirectoryById(fdClientId)
      }

      if (fdClient != null) {
        fdClient.setModifyPerson(sessionUser)
        fdClient.setModifyDatetime(new Date)
      } else {
        fdClient = new ClientFlatDirectory()
        fdClient.setCreatePerson(sessionUser)
        fdClient.setCreateDatetime(new Date)
      }

      val fdProperty = this.getClientFDPropertyById(fdPropertyId)
      val fdRecord = this.getFDRecordById(fdRecordId)

      fdClient.setClientFDProperty(fdProperty)
      fdClient.setFDRecord(fdRecord)
      fdClient.setBegDate(fdRecord.getBegDate)
      fdClient.setEndDate(fdRecord.getEndDate)

      fdClient.setDeleted(false)
      fdClient.setPatient(patient)
    }
    catch {
      case e: Exception =>
        logger.error("insertOrUpdateClientFlatDirectory >> Ошибка при создании(редактировании) записи в ClientFlatDirectory: %s".format(e.getMessage))
        throw new CoreException("Не могу создать запись в таблице ClientFlatDirectory")
    }
    fdClient
  }

  def deleteClientFlatDirectory(id: Int,
                                sessionUser: Staff) {
    val fdClient = getClientFlatDirectoryById(id)
    fdClient.setDeleted(true)
    fdClient.setModifyPerson(sessionUser)
    fdClient.setModifyDatetime(new Date)
  }

  def getClientFlatDirectoryById(fdClientId: Int): ClientFlatDirectory = {
    val result = em.createQuery(ClientFlatDirectoryByIdQuery,
      classOf[ClientFlatDirectory])
      .setParameter("id", fdClientId)
      .getResultList
    result.size match {
      case 0 => null
      case _ => result.iterator().next()
    }
  }

  def getClientFDPropertyById(fdPropertyId: Int): ClientFDProperty = {
    val result = em.createQuery(ClientFDPropertyByIdQuery,
      classOf[ClientFDProperty])
      .setParameter("id", fdPropertyId)
      .getResultList
    result.size match {
      case 0 => throw new CoreException("getClientFDPropertyById >> Не могу получить ClientFDProperty по id = %s".format(fdPropertyId))
      case _ => result.iterator().next()
    }
  }

  def getFDRecordById(fdRecordId: Int): FDRecord = {
    val result = em.createQuery(FDRecordByIdQuery,
      classOf[FDRecord])
      .setParameter("id", fdRecordId)
      .getResultList
    result.size match {
      case 0 => throw new CoreException("getFDRecordById >> Не могу получить FDRecord по id = %s".format(fdRecordId))
      case _ => result.iterator().next()
    }
  }

  val ClientFDPropertyByIdQuery = """
    SELECT DISTINCT cp
    FROM ClientFDProperty cp
    WHERE cp.id = :id
                                  """

  val FDRecordByIdQuery = """
    SELECT DISTINCT fdr
    FROM FDRecord fdr
    WHERE fdr.id = :id
                          """

  val ClientFlatDirectoryByIdQuery = """
    SELECT DISTINCT cfd
    FROM ClientFlatDirectory cfd
    WHERE cfd.id = :id
    AND cfd.deleted = 0
                                     """
}