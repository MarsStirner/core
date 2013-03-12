package ru.korus.tmis.core.database

import ru.korus.tmis.core.logging.LoggingInterceptor
import ru.korus.tmis.util.{ConfigManager, I18nable}

import grizzled.slf4j.Logging
import java.lang.Iterable
import javax.interceptor.Interceptors
import ru.korus.tmis.core.exception.NoSuchPatientException
import ru.korus.tmis.core.entity.model.{Staff, Patient}
import javax.persistence.{TypedQuery, EntityManager, PersistenceContext}
import ru.korus.tmis.core.data.PatientRequestData
import javax.ejb.{EJB, Stateless}
import scala.collection.JavaConversions._
import ru.korus.tmis.core.pharmacy.DbUUIDBeanLocal
import java.util
import org.slf4j.{LoggerFactory, Logger}
import util.{Date, Calendar, GregorianCalendar}


@Interceptors(Array(classOf[LoggingInterceptor]))
@Stateless
class DbPatientBean
  extends DbPatientBeanLocal
  with Logging
  with I18nable {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _
  private final val commlogger: Logger = LoggerFactory.getLogger("ru.korus.tmis.communication");

  @EJB
  var dbRbBloodType: DbRbBloodTypeBeanLocal = _

  @EJB
  var customQueryBean: DbCustomQueryLocal = _

  @EJB
  private var dbUUIDBeanLocal: DbUUIDBeanLocal = _

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
          exists (
            SELECT d FROM ClientDocument d
            WHERE upper(d.serial) LIKE upper(:documentPattern) OR upper(d.number) LIKE upper(:documentPattern)
            AND d.patient = p)
        AND
          p.deleted = 0
        ORDER BY p.%s %s
                                                """

  //TODO: решить, как лучше искать по ФИО
  val PatientFindActiveByFullNamePatternQuery = """
      SELECT %s
      FROM
        Patient p
      WHERE
        upper(p.lastName) LIKE upper(:fullNamePattern)
      AND
        p.deleted = 0
      ORDER BY p.%s %s
                                                """

  /*
  NOT exists (
         SELECT r
           FROM ClientRelation r
           WHERE r.relative.id = p.id
         )
      AND
   */

  val PatientFindActiveByBirthDateQuery = """
      SELECT %s
      FROM
        Patient p
      WHERE
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
    val query = if (!requestData.filter.withRelations) {
      " AND NOT exists (SELECT r FROM ClientRelation r WHERE r.relative.id != '0' AND r.relative.id = p.id)"
    } else ""
    requestData.setRecordsCount(getCountRecordsOrPagesQuery(PatientFindAllActiveQuery.format("%s", query, "%s", "%s"))
      .getSingleResult
      .toString)
    em.createQuery(PatientFindAllActiveQuery.format("p", query, sortField, sortMethod), classOf[Patient])
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
      p.setUuid(dbUUIDBeanLocal.createUUID())
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

  def findPatient(params: util.Map[String, String]): util.List[Patient] = {
    // def findPatient(lastName: String, firstName: String, patrName: String, birthDate: Long, sex: Int, identifierType: String, identifier: String, omiPolicySerial: String, omiPolicyNumber: String): dbutil.List[Patient] = {
    var findPatientQuery = """
    SELECT DISTINCT patient
    FROM Patient patient
    INNER JOIN patient.clientPolicies policy
    LEFT  JOIN policy.policyType rbtype
    WHERE patient.deleted = 0
                           """
    //TODO SQLInjects
    if (params.contains("lastName")) {
      findPatientQuery += " AND patient.lastName LIKE '" + params("lastName") + "'";
    }
    if (params.contains("firstName")) {
      findPatientQuery += " AND patient.firstName      LIKE       '" + params("firstName") + "'";
    }
    if (params.contains("patrName")) {
      findPatientQuery += " AND patient.patrName      LIKE      '" + params("patrName") + "'";
    }
    if (params.contains("birthDate")) {
      //TODO date fix

      val calendar = new GregorianCalendar();
      // calendar.setTimeInMillis(java.lang.Long.parseLong(params("birthDate")) - calendar.get(Calendar.ZONE_OFFSET));
      // val calendar2=new GregorianCalendar();
      calendar.setTimeInMillis(java.lang.Long.parseLong(params("birthDate")));
      commlogger.info("##FIXDATE LONG=" + java.lang.Long.parseLong(params("birthDate")));
      commlogger.info("##FIXDATE DATE=" + new java.util.Date(java.lang.Long.parseLong(params("birthDate"))));
      commlogger.info("##FIXDATE calendarDate=" + calendar.getTime);
      commlogger.info("##FIXDATE calendarLONG=" + calendar.getTimeInMillis);
      commlogger.info("##FIXDATE calendar YYYY-MM-DD=" + calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DATE));
      //      commlogger.info("##FIXDATE 2calendarDate="+ calendar2.getTime);
      //      commlogger.info("##FIXDATE 2calendarLONG="+ calendar2.getTimeInMillis);
      //      commlogger.info("##FIXDATE2 calendar YYYY-MM-DD="+calendar2.get(Calendar.YEAR)+"-"+(calendar2.get(Calendar.MONTH)+1)+"-"+calendar2.get(Calendar.DATE));


      findPatientQuery += " AND patient.birthDate = '" + calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DATE) + "'";
    }
    if (params.contains("omiNumber")) {
      findPatientQuery += " AND policy.number= '" + params("omiNumber") + "'";
    }
    if (params.contains("omiSerial")) {
      findPatientQuery += " AND policy.serial= '" + params("omiSerial") + "'";
      if (params.contains("omiNumber")) {
        findPatientQuery += " AND rbtype.name LIKE '%ОМС%' AND policy.deleted=0"
      }
    }
    if (params.contains("sex")) {
      findPatientQuery += " AND patient.sex = " + params("sex");
    }
    if (params.contains("identifier") && params.contains("identifierType")) {
      findPatientQuery += """ AND EXISTS(
      SELECT clientident FROM ClientIdentification clientident
      WHERE clientident.client = patient
      AND clientident.accountingSystem = (
      SELECT rbaccount FROM rbAccountingSystem rbaccount WHERE rbaccount.code= '%s'
      )
      AND identifier='%s'
        AND ClientIdentification.deleted=0
      )""".format(params.get("identifierType"), params.get("identifier"));
    }

    commlogger.info(findPatientQuery);
    em.createQuery(findPatientQuery, classOf[Patient]).getResultList
  }

  def savePatientToDataBase(patient: Patient): java.lang.Integer = {
    if (patient != null) {
      em.persist(patient);
      return patient.getId
    }
    else
      return 0;
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
    if (em.createQuery(patientIsAliveQuery, classOf[Long]).setParameter("PATIENT", patient).getSingleResult > 0) {
      false
    }
    else {
      true
    }
  }
}
