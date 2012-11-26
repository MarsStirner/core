package ru.korus.tmis.core.database

import javax.interceptor.Interceptors
import javax.ejb.{EJB, Stateless}
import grizzled.slf4j.Logging
import ru.korus.tmis.util.I18nable
import ru.korus.tmis.core.logging.LoggingInterceptor
import javax.persistence.{EntityManager, PersistenceContext}
import java.util.Date

/**
 * Класс с методами для работы с таблицей s11r64.Client_Quoting
 * @author mmakankov
 * @since 1.0.0.48
 * @see DbClientQuotingBeanLocal
 */

@Interceptors(Array(classOf[LoggingInterceptor]))
@Stateless
class DbClientQuotingBean
  extends DbClientQuotingBeanLocal
  with Logging
  with I18nable {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  @EJB
  var dbRbQuotaStatus: DbRbQuotaStatusBeanLocal = _

  val ClientQuotingFindQuery = """
    SELECT cq
    FROM
      ClientQuoting cq
    WHERE
      cq.id = :id
                                """

  def insertOrUpdateClientQuoting(id: Int,
                                  rbDocumentTypeId: Int,
                                  issued: String,
                                  number: String,
                                  serial: String,
                                  startDate: Date,
                                  endDate: Date,
                                  patient: Patient,
                                  sessionUser: Staff): ClientDocument = {
    var d: ClientDocument = null
    val now = new Date
    if (id > 0) {
      d = getClientDocumentById(id)
    }
    else {
      d = new ClientDocument
      d.setCreatePerson(sessionUser)
      d.setCreateDatetime(now)
    }

    d.setDocumentType(dbRbDocumentType.getRbDocumentTypeById(rbDocumentTypeId))
    d.setIssued(issued)
    d.setNumber(number)
    d.setSerial(serial)
    if (startDate != null) {
      d.setDate(startDate)
    } else {
      d.setDate(now)
    }
    d.setEndDate(endDate)
    d.setPatient(patient)

    d.setDeleted(false)
    d.setModifyPerson(sessionUser)
    d.setModifyDatetime(now)

    d
  }

  def deleteClientDocument(id: Int,
                           sessionUser: Staff) = {
    val d = getClientDocumentById(id)
    val now = new Date
    d.setDeleted(true)
    d.setModifyPerson(sessionUser)
    d.setModifyDatetime(now)
  }
}


