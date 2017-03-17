package ru.korus.tmis.core.database.common

import java.lang.Iterable
import java.util
import java.util.{Date, TimeZone, UUID}
import javax.ejb.{EJB, Stateless}
import javax.persistence.{EntityManager, PersistenceContext, TemporalType, TypedQuery}

import org.slf4j.{Logger, LoggerFactory}
import ru.korus.tmis.core.entity.model.{BloodKell, Patient, RbBloodPhenotype, Staff}
import ru.korus.tmis.core.exception.{CoreException, NoSuchPatientException}
import ru.korus.tmis.core.filter.ListDataFilter
import ru.korus.tmis.scala.util.{ConfigManager, I18nable}
import ru.korus.tmis.schedule.DateConvertions

import scala.collection.JavaConversions._


@Stateless
class DbPatientBean
  extends DbPatientBeanLocal
  with I18nable {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _
  private final val commlogger: Logger = LoggerFactory.getLogger("ru.korus.tmis.communication")

  @EJB
  var dbRbBloodType: DbRbBloodTypeBeanLocal = _

  val PatientFindQuery = """
      SELECT p
      FROM
        Patient p
      WHERE
        p.id = :id
                         """

  val PatientFindAllActiveQuery = """
   SELECT %s
   FROM
     Patient p
   WHERE
    p.deleted = 0
   %s
   %s
                                  """

  val PatientFindActiveQuery = """
    SELECT p
    FROM
      Patient p
    WHERE
      p.id = :id
    AND
      p.deleted = 0
                               """

  val PatientGetCountRecords = """
    SELECT count(p)
    FROM
      Patient p
    WHERE
      %s
                               """

  val PatientFindAllBetweenDate = """
      SELECT %s
      FROM
        Patient p
      WHERE
        p.createDatetime BETWEEN :beginDate AND :endDate
      AND
        p.deleted = 0
      ORDER BY p.%s %s
                                  """

  val PatientFindAllForEventsBetweenDate = """
      SELECT %s
      FROM
        Patient p
      WHERE
        p.events = :events
      AND
        p.deleted = 0
      ORDER BY p.%s %s
                                           """

  def getAllPatients: Iterable[Patient] = {
    em.createNamedQuery("Patient.findAll", classOf[Patient]).getResultList
  }

  def getAllPatients(page: Int, limit: Int, sorting: String, filter: ListDataFilter, records: (java.lang.Long) => java.lang.Boolean): util.List[Patient] = {

    val queryStr = filter.toQueryStructure

    val countTyped = em.createQuery(PatientFindAllActiveQuery.format("count(p)", queryStr.query, ""), classOf[Long])
    if (queryStr.data.size() > 0)
      queryStr.data.foreach(qdp => countTyped.setParameter(qdp.name, qdp.value))
    if (records != null)
      records(countTyped.getSingleResult)

    val typed = em.createQuery(PatientFindAllActiveQuery.format("p", queryStr.query, sorting), classOf[Patient])
      .setMaxResults(limit)
      .setFirstResult(limit * page)
    if (queryStr.data.size() > 0)
      queryStr.data.foreach(qdp => typed.setParameter(qdp.name, qdp.value))

    val result = typed.getResultList

    result
  }

  def getPatientById(id: Int): Patient = {
    val result = em.createQuery(PatientFindQuery,
      classOf[Patient])
      .setParameter("id", id)
      .getResultList
    result.size match {
      case 0 =>  throw new NoSuchPatientException(ConfigManager.ErrorCodes.PatientNotFound, id, i18n("error.patientNotFound"))
      case size => result.iterator.next()
    }
  }

  def insertOrUpdatePatient(
                             id: Int,
                             firstName: String,
                             middleName: String,
                             lastName: String,
                             birthDate: Date,
                             birthPlace: String,
                             sex: String,
                             weight: String,
                             height: String,
                             snils: String,
                             bloodDate: Date,
                             rbBloodTypeId: Int,
                             rbBloodPhenotype: java.lang.Integer,
                             bloodKell: BloodKell,
                             bloodNotes: String,
                             notes: String,
                             sessionUser: Staff,
                             version: Int): Patient = {
    var p: Patient = null
    val now = new Date
    if (id > 0) {
      p = getPatientById(id)
      if (sessionUser != null) {
        p.setVersion(version)
      }
      p.setModifyPerson(sessionUser)
      p.setModifyDatetime(now)
    } else {
      p = new Patient
      p.setCreatePerson(sessionUser)
      p.setCreateDatetime(now)
      p.setUuid(UUID.randomUUID())
    }

    p.setBirthPlace("")
    p.setFirstName(firstName)
    p.setPatrName(middleName)
    p.setLastName(lastName)
    p.setBirthDate(birthDate)
    if (birthPlace != null) {
      p.setBirthPlace(birthPlace)
    } /* else {
      p.setBirthPlace("")
    }  */

    p.setSex(sex match {
      case "male" => 1
      case "female" => 2
      case _ => 0
    })
    p.setWeight(weight)
    p.setHeight(height)
    p.setSnils(snils)
    p.setBloodDate(bloodDate)
    if (rbBloodTypeId > 0) {
      val bloodType = dbRbBloodType.getRbBloodTypeById(rbBloodTypeId)
      p.setBloodType(bloodType)
    } else {
      p.setBloodType(null)
    }
    p.setBloodNotes(bloodNotes)
    p.setNotes(notes)

    if (rbBloodPhenotype != null) {
      p.setRbBloodPhenotype(em.find(classOf[RbBloodPhenotype], rbBloodPhenotype))
    }
    if(bloodKell != null) {
      p.setBloodKell(bloodKell)
    } else {
      p.setBloodKell(BloodKell.NOT_DEFINED)
    }

    p.setDeleted(false)
    p.setModifyPerson(sessionUser)
    p.setModifyDatetime(now)


    p
  }

  def checkSNILSNumber(number: String): Boolean = {
    var isNumberFree = false
    val result = em.createQuery(PatientBySNILSQuery, classOf[Patient])
      .setParameter("snils", number)
      .getResultList
    if (result == null || result.size() < 1) {
      isNumberFree = true
    }
    isNumberFree
  }

  val PatientBySNILSQuery = """
    SELECT p
    FROM
      Patient p
    WHERE
      p.snils = :snils
    AND
      p.deleted = 0
                            """

  def findPatient(params: util.Map[String, String], clientId: Int): util.List[Patient] = {
    // def findPatient(lastName: String, firstName: String, patrName: String, birthDate: Long,
    // sex: Int, identifierType: String, identifier: String, omiPolicySerial: String,
    // omiPolicyNumber: String): util.List[Patient] = {
    var findPatientQuery = """
    SELECT DISTINCT patient
    FROM Patient patient
    WHERE patient.deleted = 0
    AND patient.lastName LIKE :LASTNAME
    AND patient.firstName LIKE      :FIRSTNAME
    AND patient.patrName      LIKE      :PATRNAME
    AND patient.birthDate =   :BIRTHDATE
    AND patient.id = :CLIENTID
    AND patient.sex = :SEX
                           """

    if (params.contains("identifier") && params.contains("identifierType")) {
      findPatientQuery += """ AND EXISTS(
      SELECT clientident FROM ClientIdentification clientident
      WHERE clientident.client = patient
      AND clientident.accountingSystem = (
      SELECT rbaccount FROM rbAccountingSystem rbaccount WHERE rbaccount.code= '%s'
      )
      AND identifier='%s'
        AND ClientIdentification.deleted=0
      )""".format(params.get("identifierType"), params.get("identifier"))
    }

    val millisecondsCount: java.lang.Long = java.lang.Long.parseLong(params.get("birthDate"))
    val resultQuery = em.createQuery(findPatientQuery, classOf[Patient])
      .setParameter("LASTNAME", params.get("lastName"))
      .setParameter("FIRSTNAME", params.get("firstName"))
      .setParameter("PATRNAME", params.get("patrName"))
      .setParameter("SEX", java.lang.Short.parseShort(params.get("sex")))
      .setParameter("BIRTHDATE", new java.util.Date(
      millisecondsCount.longValue() - TimeZone.getDefault.getOffset(millisecondsCount.longValue())))
      .setParameter("CLIENTID", clientId)
    commlogger.debug("SQL =" + resultQuery.toString)
    resultQuery.getResultList
  }

  def savePatientToDataBase(patient: Patient): java.lang.Integer = {
    if (patient != null) {
      em.persist(patient)
      em.merge(patient).getId
    } else{
      0
    }
  }

  val patientIsAliveQuery =
    """
      SELECT COUNT( attach )
      FROM ClientAttach attach
      JOIN attach.attachType attachtype
      WHERE attachtype.code = 8
      AND attach.deleted = 0
      AND attach.client = :PATIENT
    """

  /**
   * Проверяет жив ли пациент
   * @param patient   Пациент, факт смерти которого проверяется
   * @return   false=мертв, true=жив
   */
  def isAlive(patient: Patient): Boolean = {
    !(em.createQuery(patientIsAliveQuery, classOf[Long]).setParameter("PATIENT", patient).getSingleResult > 0)
  }

  def findPatientByPolicy(params: util.Map[String, String], policySerial: String, policyNumber: String, policyType: String)
  : util.List[Patient] = {
    var findPatientQuery = """
    SELECT DISTINCT patient
    FROM Patient patient
    INNER JOIN patient.clientPolicies policy
    WHERE patient.deleted = 0
    AND patient.lastName LIKE :LASTNAME
    AND patient.firstName LIKE      :FIRSTNAME
    AND patient.patrName      LIKE      :PATRNAME
    AND patient.birthDate =   :BIRTHDATE
    AND patient.sex = :SEX
    AND policy.number = :POLICYNUMBER
    AND policy.serial = :POLICYSERIAL
    AND policy.policyType.code = :POLICYTYPECODE
    AND policy.deleted = 0
                           """

    if (params.contains("identifier") && params.contains("identifierType")) {
      findPatientQuery += """ AND EXISTS(
      SELECT clientident FROM ClientIdentification clientident
        WHERE clientident.client = patient
        AND clientident.accountingSystem = (
        SELECT rbaccount FROM rbAccountingSystem rbaccount WHERE rbaccount.code= '%s'
      )
      AND identifier='%s'
        AND ClientIdentification.deleted=0
      )""".format(params.get("identifierType"), params.get("identifier"))
    }
    val millisecondsCount: java.lang.Long = java.lang.Long.parseLong(params.get("birthDate"))

    em.createQuery(findPatientQuery, classOf[Patient])
      .setParameter("LASTNAME", params.get("lastName"))
      .setParameter("FIRSTNAME", params.get("firstName"))
      .setParameter("PATRNAME", params.get("patrName"))
      .setParameter("SEX", java.lang.Short.parseShort(params.get("sex")))
      .setParameter("BIRTHDATE", new java.util.Date(
      millisecondsCount.longValue() - TimeZone.getDefault.getOffset(millisecondsCount.longValue())))
      .setParameter("POLICYNUMBER", policyNumber)
      .setParameter("POLICYSERIAL", policySerial)
      .setParameter("POLICYTYPECODE", policyType)
      .getResultList
  }

  def findPatientByDocument(params: util.Map[String, String], documentSerial: String, documentNumber: String, documentCode: String): util.List[Patient] = {
    // def findPatient(lastName: String, firstName: String, patrName: String, birthDate: Long, sex: Int, identifierType: String, identifier: String, omiPolicySerial: String, omiPolicyNumber: String): util.List[Patient] = {
    var findPatientQuery = """
    SELECT DISTINCT patient
    FROM Patient patient
    INNER JOIN patient.clientDocuments document
    WHERE patient.deleted = 0
    AND patient.lastName LIKE :LASTNAME
    AND patient.firstName LIKE      :FIRSTNAME
    AND patient.patrName      LIKE      :PATRNAME
    AND patient.birthDate =   :BIRTHDATE
    AND patient.sex = :SEX
    AND document.number = :DOCNUMBER
    AND document.serial = :DOCSERIAL
    AND document.documentType.TFOMSCode = :DOCTYPECODE
    AND document.deleted = 0
                           """
    if (params.contains("identifier") && params.contains("identifierType")) {
      findPatientQuery += """ AND EXISTS(
      SELECT clientident FROM ClientIdentification clientident
        WHERE clientident.client = patient
        AND clientident.accountingSystem = (
        SELECT rbaccount FROM rbAccountingSystem rbaccount WHERE rbaccount.code= '%s'
      )
      AND identifier='%s'
        AND ClientIdentification.deleted=0
      )""".format(params.get("identifierType"), params.get("identifier"))
    }

    val millisecondsCount: java.lang.Long = java.lang.Long.parseLong(params.get("birthDate"))

    em.createQuery(findPatientQuery, classOf[Patient])
      .setParameter("LASTNAME", params.get("lastName"))
      .setParameter("FIRSTNAME", params.get("firstName"))
      .setParameter("PATRNAME", params.get("patrName"))
      .setParameter("SEX", java.lang.Short.parseShort(params.get("sex")))
      .setParameter("BIRTHDATE", new java.util.Date(
      millisecondsCount.longValue() - TimeZone.getDefault.getOffset(millisecondsCount.longValue())))
      .setParameter("DOCNUMBER", documentNumber)
      .setParameter("DOCSERIAL", documentSerial)
      .setParameter("DOCTYPECODE", documentCode.toString)
      .getResultList
  }

  def findPatientsByParams(params: util.Map[String, String], documents: util.Map[String, String]): util.List[Patient] = {
    val query: java.lang.StringBuilder = new java.lang.StringBuilder(
      "SELECT pat FROM Patient pat ")
    //Construct query  string
    //JOIN tables if needed
    if (documents != null && documents.size() > 0) {
      if (documents.containsKey("client_id")) {
        query.append(" WHERE pat.deleted=0 AND pat.id = :CLIENTID")
      }
      else {
        if (documents.containsKey("document_code")) {
          query.append("INNER JOIN pat.clientDocuments doc WHERE pat.deleted=0 AND doc.deleted=0" +
            " AND doc.number = :NUMBER AND doc.serial = :SERIAL AND doc.documentType.TFOMSCode = :DOCTYPECODE"
          )
        }
        else {
          if (documents.containsKey("policy_type")) {
            query.append("INNER JOIN pat.clientPolicies policy WHERE pat.deleted=0 AND policy.deleted = 0" +
              " AND policy.number = :NUMBER AND policy.serial = :SERIAL AND policy.policyType.CODE = :POLICYTYPECODE"
            )
          }
        }
      }
    }
    else {
      query.append(" WHERE pat.deleted=0")
    }
    //End of join tables
    if (params.contains("firstName")) {
      query.append(" AND pat.firstName LIKE :FIRSTNAME")
    }
    if (params.contains("lastName")) {
      query.append(" AND pat.lastName LIKE :LASTNAME")
    }
    if (params.contains("patrName")) {
      query.append(" AND pat.patrName LIKE :PATRNAME")
    }
    if (params.contains("birthDate")) {
      query.append(" AND pat.birthDate = :BIRTHDATE")
    }
    if (params.contains("sex")) {
      query.append(" AND pat.sex = :SEX")
    }
    //End of construct query string
    val typedQuery: TypedQuery[Patient] = em.createQuery(query.toString, classOf[Patient])
    //SetParameters block
    if (documents != null && documents.size() > 0) {
      if (documents.containsKey("client_id")) {
        typedQuery.setParameter("CLIENTID", Integer.parseInt(documents.get("client_id")))
      }
      else {
        if (documents.containsKey("document_code")) {
          typedQuery.setParameter("DOCTYPECODE", documents.get("document_code"))
          .setParameter("SERIAL", documents.get("serial"))
          .setParameter("NUMBER", documents.get("number"))
        }
        else {
          if (documents.containsKey("policy_type")) {
            typedQuery.setParameter("POLICYTYPECODE", documents.get("policy_type"))
            .setParameter("SERIAL", documents.get("serial"))
            .setParameter("NUMBER", documents.get("number"))
          }
        }
      }
    }
    else {
      query.append(" WHERE pat.deleted=0")
    }
    //End of join tables
    if (params.contains("firstName")) {
      typedQuery.setParameter("FIRSTNAME", params.get("firstName"))
    }
    if (params.contains("lastName")) {
      typedQuery.setParameter("LASTNAME", params.get("lastName"))
    }
    if (params.contains("patrName")) {
      typedQuery.setParameter("PATRNAME", params.get("patrName"))
    }
    if (params.contains("birthDate")) {
      val millisecondsCount: java.lang.Long = java.lang.Long.parseLong(params.get("birthDate"))
      typedQuery.setParameter("BIRTHDATE", new java.util.Date(
        millisecondsCount.longValue() - TimeZone.getDefault.getOffset(millisecondsCount.longValue())
      ))
    }
    if (params.contains("sex")) {
      typedQuery.setParameter("SEX", java.lang.Short.parseShort(params.get("sex")))
    }
    //End of set Parameters
    commlogger.debug("JPQL query string is \"" + query.toString + "\"")
    commlogger.debug("SQL query string is \"" + typedQuery.toString + "\"")
    typedQuery.getResultList
  }

  def deletePatient(id: Int): Boolean = {
    try {
      val patient = this.getPatientById(id)
      val merged = em.merge(patient)
      em.remove(merged)
      em.flush()
      true
    }
    catch {
      case e: Exception =>
        throw new CoreException("Ошибка при удалении пациента с id=%d: %s".format(id, e.getMessage))
    }
  }

  def findPatientWithoutDocuments(params: util.Map[String, String]): util.List[Patient] = {
    var findPatientQuery = """
    SELECT DISTINCT patient
    FROM Patient patient
    WHERE patient.deleted = 0
    AND patient.lastName LIKE :LASTNAME
    AND patient.firstName LIKE      :FIRSTNAME
    AND patient.patrName  LIKE      :PATRNAME
    AND patient.birthDate =   :BIRTHDATE
    AND patient.sex = :SEX
                           """

    if (params.contains("identifier") && params.contains("identifierType")) {
      findPatientQuery += """ AND EXISTS(
      SELECT clientident FROM ClientIdentification clientident
      WHERE clientident.client = patient
      AND clientident.accountingSystem = (
      SELECT rbaccount FROM rbAccountingSystem rbaccount WHERE rbaccount.code= '%s'
      )
      AND identifier='%s'
        AND ClientIdentification.deleted=0
      )""".format(params.get("identifierType"), params.get("identifier"))
    }
    val resultQuery = em.createQuery(findPatientQuery, classOf[Patient])
      .setParameter("LASTNAME", params.get("lastName"))
      .setParameter("FIRSTNAME", params.get("firstName"))
      .setParameter("PATRNAME", params.get("patrName"))
      .setParameter("SEX", java.lang.Short.parseShort(params.get("sex")))
      .setParameter("BIRTHDATE", DateConvertions.convertUTCMillisecondsToDate(java.lang.Long.parseLong(params.get("birthDate"))))
    commlogger.debug(resultQuery.toString)
    resultQuery.getResultList
  }

  def findPatientsByPersonalInfo(
                                  lastName: String,
                                  firstName: String,
                                  patrName: String,
                                  sex: Short,
                                  birthDate: Date)
  : util.List[Patient] = {
    em.createNamedQuery("Patient.findByPersonalInfo", classOf[Patient])
      .setParameter("lastName", lastName.toUpperCase)
      .setParameter("firstName", firstName.toUpperCase)
      .setParameter("patrName", patrName.toUpperCase)
      .setParameter("sex", sex)
      .setParameter("birthDate", birthDate, TemporalType.DATE).getResultList
  }

}
