package ru.korus.tmis.ws.impl

import java.util._
import javax.annotation.Resource
import javax.ejb.EJB
import javax.inject.Named
import javax.jws.{HandlerChain, WebService}
import javax.xml.ws.WebServiceContext

import grizzled.slf4j.Logging
import org.apache.shiro.SecurityUtils
import ru.korus.tmis.core.assessment.AssessmentBeanLocal
import ru.korus.tmis.core.auth.AuthData
import ru.korus.tmis.core.data._
import ru.korus.tmis.core.database._
import ru.korus.tmis.core.database.common.{DbContractBeanLocal, DbEventPersonBeanLocal, DbOrgStructureBeanLocal}
import ru.korus.tmis.core.diagnostic.DiagnosticBeanLocal
import ru.korus.tmis.core.exception.CoreException
import ru.korus.tmis.core.patient._
import ru.korus.tmis.core.thesaurus.ThesaurusBeanLocal
import ru.korus.tmis.scala.util.{ConfigManager, Defaultible, I18nable}
import ru.korus.tmis.ws.medipad.MedipadWebService

import scala.language.reflectiveCalls

@Named
@WebService(
  endpointInterface = "ru.korus.tmis.ws.medipad.MedipadWebService",
  targetNamespace = "http://korus.ru/tmis/medipad",
  serviceName = "tmis-medipad",
  portName = "medipad",
  name = "medipad")
@HandlerChain(file = "tmis-ws-auth-handlers.xml")
class MedipadWSImpl
  extends MedipadWebService

  with Logging
  with I18nable {

  @Resource
  var ctx: WebServiceContext = _

  @EJB
  private var dbVersionBean: DbVersionBeanLocal = _

  @EJB
  private var patientBean: PatientBeanLocal = _

  @EJB
  private var assessmentBean: AssessmentBeanLocal = _

  @EJB
  private var diagnosticBean: DiagnosticBeanLocal = _

  @EJB
  private var thesaurusBean: ThesaurusBeanLocal = _

  @EJB
  var dbEventPerson: DbEventPersonBeanLocal = _

  @EJB
  var dbEventTypeBean: DbEventTypeBeanLocal = _

  @EJB
  var dbContractBean: DbContractBeanLocal = _

  @EJB
  var dbJobTicketBean: DbJobTicketBeanLocal = _

  @EJB
  var dbRbTissueType: DbRbTissueTypeBeanLocal = _

  @EJB
  var directionBean: DirectionBeanLocal = _

  @EJB
  var dbDiagnosticBean: DbDiagnosticBeanLocal = _

  @EJB
  var dbOrgStructureBean: DbOrgStructureBeanLocal = _

  @EJB
  var dbStaff: DbStaffBeanLocal = _
  //////////////////////////////////////////////////////////////////////////////

  def currentAuthData() = {
    val mc = ctx.getMessageContext()
    val ff = mc.get(ConfigManager.TmisAuth.AuthDataPropertyName)
    ff.asInstanceOf[AuthData]
  }

  def currentUser() = {
    SecurityUtils.getSubject
  }

  def requiresPermissions(permissions: Array[String]) = {
    if (!currentUser.isPermitted("adm")) {
      permissions.foreach(p => {
        if (!currentUser.isPermitted(p)) {
          throw new CoreException(
            ConfigManager.TmisAuth.ErrorCodes.PermissionNotAllowed,
            i18n("error.permissionNotAllowed").format(p))
        }
      })
    }
  }

  //////////////////////////////////////////////////////////////////////////////

  def getCurrentPatients: CommonData = {
    requiresPermissions(Array("clientEventRead", "clientActionRead"))

    if (currentUser.isPermitted("existsSeesStructure")
      || currentUser().isPermitted("inflowSeesStructure")) {
      return patientBean.getCurrentPatientsForDepartment(dbStaff.getStaffById(currentAuthData.getUserId))
    } else if (currentUser.isPermitted("existsSeesSelf")
      || currentUser().isPermitted("inflowSeesSelf")) {
      return patientBean.getCurrentPatientsForDoctor(currentAuthData)
    }

    throw new CoreException(
      i18n("error.invalidRole").format(currentAuthData.getUserRole.getCode)
    )
  }

  //////////////////////////////////////////////////////////////////////////////

  import Defaultible._

  private def checkingVersion[A: Defaultible](globalVersion: String)(td: => A) = {
    if (globalVersion == dbVersionBean.getGlobalVersion) defaultValue[A]
    else td
  }

  private implicit val commonDataDefault = setDefault(new CommonData)
  private implicit val thesaurusDefault = setDefault(new ThesaurusData)

  //////////////////////////////////////////////////////////////////////////////

  def getAssessmentTypes(globalVersion: String,
                         eventId: Int): CommonData = checkingVersion(globalVersion) {
    assessmentBean.getAssessmentTypes(eventId, dbStaff.getStaffById(currentAuthData.getUserId))
  }

  def getAllAssessmentTypes(globalVersion: String) = checkingVersion(globalVersion) {
    assessmentBean.getAllAssessmentTypes
  }

  def getAllAssessmentsForPatient(eventId: Int) = {
    requiresPermissions(Array("clientAssessmentRead"))
    assessmentBean.getAllAssessmentsByEventId(eventId)
  }

  def getAssessmentForPatient(eventId: Int, assessmentId: Int) = {
    requiresPermissions(Array("clientAssessmentRead"))
    assessmentBean.getAssessmentById(assessmentId)
  }

  def getIndicators(eventId: Int, beginDate: Date, endDate: Date) = {
    requiresPermissions(Array("clientAssessmentRead"))
    assessmentBean.getIndicators(eventId, beginDate, endDate)
  }

  def createAssessmentForPatient(eventId: Int,
                                 assessment: CommonData) = {
    requiresPermissions(Array("clientAssessmentCreate"))
    assessmentBean.createAssessmentForEventId(eventId,
      assessment,
      currentAuthData,
      dbStaff.getStaffById(currentAuthData.getUserId))
  }

  def modifyAssessmentForPatient(eventId: Int,
                                 assessmentId: Int,
                                 assessment: CommonData) = {
    requiresPermissions(Array("clientAssessmentUpdate"))
    assessmentBean.modifyAssessmentById(assessmentId,
      assessment,
      currentAuthData,
      dbStaff.getStaffById(currentAuthData.getUserId))
  }

  //////////////////////////////////////////////////////////////////////////////

  def getDiagnosticTypes(globalVersion: String,
                         eventId: Int) = checkingVersion(globalVersion) {
    diagnosticBean.getDiagnosticTypes(eventId,  dbStaff.getStaffById(currentAuthData.getUserId))
  }

  def getAllDiagnosticTypes(globalVersion: String) = checkingVersion(globalVersion) {
    diagnosticBean.getAllDiagnosticTypes
  }

  def getAllDiagnosticsForPatient(eventId: Int) = {
    requiresPermissions(Array("clientDiagnosticRead"))
    diagnosticBean.getAllDiagnosticsByEventId(eventId)
  }

  def getDiagnosticForPatient(eventId: Int, diagnosticId: Int) = {
    requiresPermissions(Array("clientDiagnosticRead"))
    diagnosticBean.getDiagnosticById(diagnosticId)
  }

  def createDiagnosticForPatient(eventId: Int,
                                 diagnostic: CommonData) = {
    requiresPermissions(Array("clientDiagnosticCreate"))
    val auth = currentAuthData
    diagnosticBean.createDiagnosticForEventId(eventId,
      diagnostic,
      auth,
      dbStaff.getStaffById(currentAuthData.getUserId))
  }

  def modifyDiagnosticForPatient(eventId: Int,
                                 diagnosticId: Int,
                                 diagnostic: CommonData) = {
    requiresPermissions(Array("clientDiagnosticUpdate"))
    diagnosticBean.modifyDiagnosticById(diagnosticId,
      diagnostic,
      currentAuthData,
      dbStaff.getStaffById(currentAuthData.getUserId))
  }

  def callOffDiagnosticForPatient(eventId: Int,
                                  diagnosticId: Int) = {
    requiresPermissions(Array("clientDiagnosticUpdate"))
    diagnosticBean.updateDiagnosticStatusById(eventId,
      diagnosticId,
      ConfigManager.ActionStatus.Canceled)
  }

  //////////////////////////////////////////////////////////////////////////////

  def getThesaurus(globalVersion: String) = checkingVersion(globalVersion) {
    thesaurusBean.getThesaurus
  }

  def getThesaurusByCode(globalVersion: String, code: Int) = checkingVersion(globalVersion) {
    thesaurusBean.getThesaurusByCode(code)
  }

  def getMkb(globalVersion: String) = checkingVersion(globalVersion) {
    thesaurusBean.getMkb
  }

  def getAllDepartmentsByHasBeds(hasBeds: String, hasPatients: String) = {
    //Для медипада
    val flgBeds = hasBeds.toLowerCase.compareTo("true") == 0 || hasBeds.toLowerCase.compareTo("yes") == 0
    val flgPatients = hasPatients.toLowerCase.compareTo("true") == 0 || hasPatients.toLowerCase.compareTo("yes") == 0
    val filter = new DepartmentsDataFilter(flgBeds, flgPatients)
    new AllDepartmentsListDataMP(dbOrgStructureBean.getAllOrgStructuresByRequest(0, 0, "", filter.unwrap()), null)
  }

  def getPatientsFromOpenAppealsWhatHasBedsByDepartmentId(departmentId: Int) = {
    patientBean.getCurrentPatientsByDepartmentId(departmentId)
  }
}