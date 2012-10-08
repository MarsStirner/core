package ru.korus.tmis.core.database

import ru.korus.tmis.core.logging.LoggingInterceptor
import ru.korus.tmis.util.{ConfigManager, I18nable}

import grizzled.slf4j.Logging
import java.lang.Iterable
import javax.interceptor.Interceptors
import ru.korus.tmis.core.exception.NoSuchPatientException
import ru.korus.tmis.core.entity.model.{Staff, Patient}
import java.util.Date
import scala.Predef._
import javax.persistence.{TypedQuery, EntityManager, PersistenceContext}
import ru.korus.tmis.core.data.PatientRequestData
import javax.ejb.{EJB, Stateless}

@Interceptors(Array(classOf[LoggingInterceptor]))
@Stateless
class DbPatientBean
  extends DbPatientBeanLocal
  with Logging
  with I18nable {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  @EJB
  var dbRbBloodType: DbRbBloodTypeBeanLocal = _

  @EJB
  var customQueryBean: DbCustomQueryLocal = _

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
   NOT exists (
     SELECT r
       FROM ClientRelation r
       WHERE r.relative.id != '0'
       AND r.relative.id = p.id
     )
   AND
     p.deleted = 0
   ORDER BY p.%s %s
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

  //TODO: в таблице Client нет поля "код пациента"
  val PatientFindActiveByCodeQuery = """
    SELECT %s
    FROM
      Patient p
    WHERE
      p.id = :patientCode
    AND
      p.deleted = 0
    ORDER BY p.%s %s
                                     """

  val PatientFindActiveByDocumentPatternQuery = """
    SELECT %s
    FROM
      Patient p
    WHERE
      NOT exists (
       SELECT r
         FROM ClientRelation r
         WHERE r.relative.id = p.id
       )
     AND
       p.deleted = 0
     ORDER BY p.%s %s
                                                """

  //    //TODO: в таблице Client нет поля "код пациента"
  //    val PatientFindActiveByCodeQuery = """
  //      SELECT %s
  //      FROM
  //        Patient p
  //      WHERE
  //        p.id = :patientCode
  //      AND
  //        p.deleted = 0
  //      ORDER BY p.%s %s
  //    """
  //
  //    val PatientFindActiveByDocumentPatternQuery = """
  //      SELECT %s
  //      FROM
  //        Patient p
  //      WHERE
  //        NOT exists (
  //         SELECT r
  //           FROM ClientRelation r
  //           WHERE r.relative.id = p.id
  //         )
  //      AND
  //        exists (
  //          SELECT d FROM ClientDocument d
  //          WHERE upper(d.serial) LIKE upper(:documentPattern) OR upper(d.number) LIKE upper(:documentPattern)
  //          AND d.patient = p)
  //      AND
  //        p.deleted = 0
  //      ORDER BY p.%s %s
  //    """

  //TODO: решить, как лучше искать по ФИО
  val PatientFindActiveByFullNamePatternQuery = """
      SELECT %s
      FROM
        Patient p
      WHERE
        NOT exists (
         SELECT r
           FROM ClientRelation r
           WHERE r.relative.id = p.id
         )
      AND
        upper(p.lastName) LIKE upper(:fullNamePattern)
      AND
        p.deleted = 0
      ORDER BY p.%s %s
                                                """

  val PatientFindActiveByBirthDateQuery = """
      SELECT %s
      FROM
        Patient p
      WHERE
        NOT exists (
         SELECT r
           FROM ClientRelation r
           WHERE r.relative.id = p.id
         )
      AND
        p.birthDate = :birthDate
      AND
        p.deleted = 0
      ORDER BY p.%s %s
                                          """

  //TODO: решить, как лучше искать по ФИО
  val PatientFindActiveByBirthDateAndFullNamePatternQuery = """
      SELECT %s
      FROM
        Patient p
      WHERE
        NOT exists (
         SELECT r
           FROM ClientRelation r
           WHERE r.relative.id = p.id
         )
      AND
        p.birthDate = :birthDate AND upper(p.lastName) LIKE upper(:fullNamePattern)
      AND
        p.deleted = 0
      ORDER BY p.%s %s
                                                            """

  val PatientGetCountRecords = """
    SELECT count(p)
    FROM
      Patient p
    WHERE
      NOT exists (
         SELECT r
           FROM ClientRelation r
           WHERE r.relative.id = p.id
         )
     AND
      %s
                               """

  val PatientFindAllBetweenDate = """
      SELECT %s
      FROM
        Patient p
      WHERE
        NOT exists (
         SELECT r
           FROM ClientRelation r
           WHERE r.relative.id = p.id
         )
      AND
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
        NOT exists (
         SELECT r
           FROM ClientRelation r
           WHERE r.relative.id = p.id
         )
      AND
        p.events = :events
      AND
        p.deleted = 0
      ORDER BY p.%s %s
                                           """

  /*
     lastName
     firstName
     middleName
     sex
     patientCode
     birthDate
 * */
  def getCountRecordsOrPagesQuery(enterPosition: String): TypedQuery[Long] = {

    val cntMacroStr = "count(p)"
    val sortField = ""
    val sortMethod = ""
    //выберем нужный запрос

    var curentRequest = enterPosition.format(cntMacroStr, sortField, sortMethod)

    //уберем из запроса фильтрацию
    val index = curentRequest.indexOf("ORDER BY")
    if (index > 0) {
      curentRequest = curentRequest.substring(0, index)
    }
    em.createQuery(curentRequest.toString(), classOf[Long])
  }

  def getAllPatients(): Iterable[Patient] = {
    em.createNamedQuery("Patient.findAll", classOf[Patient]).getResultList
  }

  def getAllPatients(limit: Int, page: Int, sortField: String, sortMethod: String,
                     requestData: PatientRequestData): Iterable[Patient] = {
    requestData.setRecordsCount(getCountRecordsOrPagesQuery(PatientFindAllActiveQuery)
      .getSingleResult
      .toString)
    em.createQuery(PatientFindAllActiveQuery.format("p", sortField, sortMethod), classOf[Patient])
      .setMaxResults(limit)
      .setFirstResult(limit * page)
      .getResultList
  }

  def getPatientsWithCode(limit: Int, page: Int, sortField: String, sortMethod: String, patientCode: Int,
                          requestData: PatientRequestData): Iterable[Patient] = {
    requestData.setRecordsCount(getCountRecordsOrPagesQuery(PatientFindActiveByCodeQuery)
      .setParameter("patientCode", patientCode)
      .getSingleResult
      .toString)
    em.createQuery(PatientFindActiveByCodeQuery.format("p", sortField, sortMethod), classOf[Patient])
      .setMaxResults(limit)
      .setFirstResult(limit * page)
      .setParameter("patientCode", patientCode)
      .getResultList
  }

  def getPatientsWithDocumentPattern(limit: Int, page: Int, sortField: String, sortMethod: String, documentPattern: String,
                                     requestData: PatientRequestData): Iterable[Patient] = {
    requestData.setRecordsCount(getCountRecordsOrPagesQuery(PatientFindActiveByDocumentPatternQuery)
      .setParameter("documentPattern", "%" + documentPattern + "%")
      .getSingleResult
      .toString)
    em.createQuery(PatientFindActiveByDocumentPatternQuery.format("p", sortField, sortMethod), classOf[Patient])
      .setMaxResults(limit)
      .setFirstResult(limit * page)
      .setParameter("documentPattern", "%" + documentPattern + "%")
      .getResultList
  }

  def getPatientsWithFullNamePattern(limit: Int, page: Int, sortField: String, sortMethod: String, fullNamePattern: String,
                                     requestData: PatientRequestData): Iterable[Patient] = {
    requestData.setRecordsCount(getCountRecordsOrPagesQuery(PatientFindActiveByFullNamePatternQuery)
      .setParameter("fullNamePattern", "%" + fullNamePattern + "%")
      .getSingleResult
      .toString)
    em.createQuery(PatientFindActiveByFullNamePatternQuery.format("p", sortField, sortMethod), classOf[Patient])
      .setMaxResults(limit)
      .setFirstResult(limit * page)
      .setParameter("fullNamePattern", "%" + fullNamePattern + "%")
      .getResultList
  }

  def getPatientsWithBirthDate(limit: Int, page: Int, sortField: String, sortMethod: String, birthDate: Date,
                               requestData: PatientRequestData): Iterable[Patient] = {
    requestData.setRecordsCount(getCountRecordsOrPagesQuery(PatientFindActiveByBirthDateQuery)
      .setParameter("birthDate", birthDate)
      .getSingleResult
      .toString)
    em.createQuery(PatientFindActiveByBirthDateQuery.format("p", sortField, sortMethod), classOf[Patient])
      .setMaxResults(limit)
      .setFirstResult(limit * page)
      .setParameter("birthDate", birthDate)
      .getResultList
  }

  def getPatientsWithBirthDateAndFullNamePattern(limit: Int, page: Int, sortField: String, sortMethod: String, birthDate: Date, fullNamePattern: String,
                                                 requestData: PatientRequestData): Iterable[Patient] = {
    requestData.setRecordsCount(getCountRecordsOrPagesQuery(PatientFindActiveByBirthDateAndFullNamePatternQuery)
      .setParameter("birthDate", birthDate)
      .setParameter("fullNamePattern", "%" + fullNamePattern + "%")
      .getSingleResult
      .toString)
    em.createQuery(PatientFindActiveByBirthDateAndFullNamePatternQuery.format("p", sortField, sortMethod), classOf[Patient])
      .setMaxResults(limit)
      .setFirstResult(limit * page)
      .setParameter("birthDate", birthDate)
      .setParameter("fullNamePattern", "%" + fullNamePattern + "%")
      .getResultList
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
    }
    else {
      p = new Patient
      p.setCreatePerson(sessionUser)
      p.setCreateDatetime(now)
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

    p.setDeleted(false)
    p.setModifyPerson(sessionUser)
    p.setModifyDatetime(now)


    p
  }

  def checkSNILSNumber(number: String) = {
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
}
