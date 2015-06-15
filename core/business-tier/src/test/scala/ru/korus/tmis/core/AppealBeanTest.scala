package ru.korus.tmis.core

import auth.AuthData
import database.common._
import entity.model._
import exception.CoreException
import org.junit.runner.RunWith
import org.mockito._
import org.mockito.Matchers._
import org.mockito.runners.MockitoJUnitRunner
import org.mockito.Mockito._
import ru.korus.tmis.core.common.CommonDataProcessorBeanLocal
import ru.korus.tmis.core.database._
import patient.{AppealBeanLocal, AppealBean, DiagnosisBeanLocal, HospitalBedBean}
import org.slf4j.{LoggerFactory, Logger}
import org.junit.{Assert, Test, Before}
import data._
import ru.korus.tmis.test.data.{TestDataEntity, TestDataEntityImpl}
import org.codehaus.jackson.map.ObjectMapper
import scala.collection.JavaConversions._
import java.util.Date
import javax.persistence.EntityManager
import java.util
import collection.JavaConversions
import ru.korus.tmis.scala.util.ConfigManager
import scala.language.reflectiveCalls

/**
 * Author:      Ivan Dmitriev <br>
 * Date:        21.08.13, 13:26  <br>
 * Company:     Systema-Soft<br>
 * Description: Тесты для AppealBean<br>
 */
@RunWith(classOf[MockitoJUnitRunner])
class AppealBeanTest {

  @InjectMocks
  var appealBean = new AppealBean()

  @Mock var em: EntityManager = _
  @Mock var organizationBeanLocal: DbOrganizationBeanLocal = _
  @Mock var appLock: AppLockBeanLocal = _
  @Mock var dbEventBean: DbEventBeanLocal = _
  @Mock var actionBean: DbActionBeanLocal = _
  @Mock var actionTypeBean: DbActionTypeBeanLocal = _
  @Mock var actionPropertyBean: DbActionPropertyBeanLocal = _
  @Mock var actionPropertyTypeBean: DbActionPropertyTypeBeanLocal = _
  @Mock var dbManager: DbManagerBeanLocal = _
  @Mock var dbCustomQueryBean: DbCustomQueryLocal = _
  @Mock var dbRbCoreActionPropertyBean: DbRbCoreActionPropertyBeanLocal = _
  @Mock var dbMkbBean: DbMkbBeanLocal = _
  @Mock var dbFDRecordBean: DbFDRecordBeanLocal = _
  @Mock var dbPatientBean: DbPatientBeanLocal = _
  @Mock var dbClientRelation: DbClientRelationBeanLocal = _
  @Mock var dbEventPerson: DbEventPersonBeanLocal = _
  @Mock var dbEventTypeBean: DbEventTypeBeanLocal = _
  @Mock var diagnosisBean: DiagnosisBeanLocal = _
  @Mock var dbStaff: DbStaffBeanLocal = _
  @Mock var dbRbResultBean: DbRbResultBeanLocal = _

  final val logger: Logger = LoggerFactory.getLogger(classOf[HospitalBedBeanTest])
  final val mapper: ObjectMapper = new ObjectMapper()
  final val testData: TestDataEntity = new TestDataEntityImpl()

  final val TEvent_id = 1
  final val TAction_id = 1

  private class IndexOf[T] (seq: Seq[T]) {
    def unapply(pos: T) = seq find (pos == _) map (seq indexOf _)
  }

  @Before
  def init() {
  }


  //______________________________________________________________________________________________________
  //****************************************   insertOrModifyAppeal Tests   ********************************
  //______________________________________________________________________________________________________

/*  @Test
  def getInsertOrModifyAppeal = {

    //test input data
    val appealData = new AppealData()
    val testEvent = testData.getTestDefaultEvent(TEvent_id)
    val flgCreate = true
    val authData: AuthData = null

    //mock database response data
    val testLockId = 1
    val actionType_primary = testData.getTestDefaultActionType(ConfigManager.Messages("db.actionType.hospitalization.primary").toInt, ConfigManager.Messages("db.action.admissionFlatCode"))
    val lastAction = testData.getTestDefaultAction(TAction_id, actionType_primary)
    val action = testData.getTestDefaultAction(TAction_id+1, actionType_primary)

    val apt_1 = testData.getTestDefaultActionPropertyType(1, "code_1")
    val apt_2 = testData.getTestDefaultActionPropertyType(2, "code_2")
    val apt_3 = testData.getTestDefaultActionPropertyType(3, "code_3")
    val apt_list = new java.util.LinkedList[ActionPropertyType]()
    apt_list.addAll(asJavaCollection(Set(apt_1, apt_2, apt_3)))
    val ap_1 = testData.getTestDefaultActionProperty(1, apt_1)
    val ap_2 = testData.getTestDefaultActionProperty(2, apt_2)
    val ap_3 = testData.getTestDefaultActionProperty(3, apt_3)

    try{
      //appealData : AppealData, event: Event, flgCreate: Boolean, authData: AuthData
      logger.info("Request data for method is: {}", "appealData: %s, event_id=%d, flgCreate=%s, auth: %s"
        .format(mapper.writeValueAsString(appealData), TEvent_id, flgCreate.toString, mapper.writeValueAsString(authData)))

      when(actionBean.getAppealActionByEventId(testEvent.getId.intValue(),
                                               ConfigManager.Messages("db.actionType.hospitalization.primary").toInt)).thenReturn(/*lastAction*/ null)
      //when(actionPropertyBean.getActionPropertiesByActionId(lastAction.getId.intValue)).thenReturn()   //!!!
      when(appLock.acquireLock(anyString(), anyInt(), anyInt(), anyObject())).thenReturn(testLockId)
      when(actionTypeBean.getActionTypeById(ConfigManager.Messages("db.actionType.hospitalization.primary").toInt)).thenReturn(actionType_primary)
      when(actionBean.createAction(testEvent.getId.intValue(), actionType_primary.getId.intValue(),authData)).thenReturn(action)
      when(actionPropertyTypeBean.getActionPropertyTypesByActionTypeId(ConfigManager.Messages("db.actionType.hospitalization.primary").toInt)).thenReturn(apt_list)
      when(actionBean.updateAction(lastAction.getId.intValue(), lastAction.getVersion.intValue, authData)).thenReturn(lastAction)
      when(actionPropertyBean.createActionProperty(action, 1, authData)).thenReturn(ap_1)
      when(actionPropertyBean.createActionProperty(action, 2, authData)).thenReturn(ap_2)
      when(actionPropertyBean.createActionProperty(action, 3, authData)).thenReturn(ap_3)
      when(actionPropertyBean.updateActionProperty(ap_1.getId.intValue,ap_1.getVersion.intValue,authData)).thenReturn(ap_1)
      when(actionPropertyBean.updateActionProperty(ap_2.getId.intValue,ap_2.getVersion.intValue,authData)).thenReturn(ap_2)
      when(actionPropertyBean.updateActionProperty(ap_3.getId.intValue,ap_3.getVersion.intValue,authData)).thenReturn(ap_3)
      when(actionPropertyBean.setActionPropertyValue(ap_1, "test", 0)).thenReturn(testData.getTestDefaultAPValueOrgStructure)
      when(actionPropertyBean.setActionPropertyValue(ap_2, "test", 0)).thenReturn(testData.getTestDefaultAPValueString())
      when(actionPropertyBean.setActionPropertyValue(ap_3, "test", 0)).thenReturn(testData.getTestDefaultAPValueHospitalBed())
      when(actionPropertyBean.getActionPropertyValue(ap_1)).thenReturn(List(testData.getTestDefaultAPValueOrgStructure()))
      when(actionPropertyBean.getActionPropertyValue(ap_2)).thenReturn(List(testData.getTestDefaultAPValueString()))
      when(actionPropertyBean.getActionPropertyValue(ap_3)).thenReturn(List(testData.getTestDefaultAPValueHospitalBed()))
      when(dbRbCoreActionPropertyBean.getRbCoreActionPropertiesByActionPropertyTypeId(apt_1.getId.intValue())).thenReturn(new RbCoreActionProperty(1))
      when(dbRbCoreActionPropertyBean.getRbCoreActionPropertiesByActionPropertyTypeId(apt_2.getId.intValue())).thenReturn(new RbCoreActionProperty(2))
      when(dbRbCoreActionPropertyBean.getRbCoreActionPropertiesByActionPropertyTypeId(apt_3.getId.intValue())).thenReturn(new RbCoreActionProperty(3))

      val result = appealBean.insertOrModifyAppeal(appealData, testEvent, true, authData)

      Assert.assertNotNull(result)

      logger.info("The method has been successfully completed. Result is: {}", mapper.writeValueAsString(result))
      logger.warn("Successful end of getInsertOrModifyAppeal test")
    }
    catch {
      case ex: CoreException => {
        logger.error("getInsertOrModifyAppeal test failed with error: {}", ex.getMessage + " " + ex.getStackTrace)
        Assert.fail()
      }
    }
  }*/

  //______________________________________________________________________________________________________
  //****************************************   getAppealById Tests   ********************************
  //______________________________________________________________________________________________________

  @Test
  def testGetAppealByIdSuccess = {

    logger.warn("Start of getAppealById test:")

    val testEventId = TEvent_id
    val testEvent = testData.getTestDefaultEvent(testEventId)
    val testAction = testData.getTestDefaultAction(TAction_id,
      testData.getTestDefaultActionType(ConfigManager.Messages("db.actionType.hospitalization.primary").toInt,
                                        ConfigManager.Messages("db.action.admissionFlatCode")))

    var apt = List.empty[ActionPropertyType]
    var ap = List.empty[ActionProperty]
    val apvalues = new java.util.HashMap[ActionProperty, java.util.List[APValue]]()
    val apv = List[APValue](testData.getTestDefaultAPValueOrgStructure(1),
                            testData.getTestDefaultAPValueTime(new Date()),
                            testData.getTestDefaultAPValueString("test"))

    for(i <- 0 until 3) {
       apt :+= testData.getTestDefaultActionPropertyType(i+1, "testCode_%d".format(i+1))
       ap :+= testData.getTestDefaultActionProperty(i+1, apt.get(i))
       apvalues.put(ap.get(i), seqAsJavaList(List[APValue](apv.get(i))))
    }

    try{
      //appealData : AppealData, event: Event, flgCreate: Boolean, authData: AuthData
      logger.info("Request data for method is: {}", "event_id=%d".format(TEvent_id))

      when(dbEventBean.getEventById(testEventId)).thenReturn(testEvent)
      when(actionBean.getAppealActionByEventId(testEvent.getId.intValue(),
        ConfigManager.Messages("db.actionType.hospitalization.primary").toInt)).thenReturn(testAction)
      when(actionPropertyBean.getActionPropertiesByActionId(testAction.getId.intValue)).thenReturn(apvalues)
      for(i <- 0 until 3) {
        when(dbRbCoreActionPropertyBean.getRbCoreActionPropertiesByActionPropertyTypeId(apt.get(i).getId.intValue())).thenReturn(new RbCoreActionProperty(i+1))
      }

      val result = appealBean.getAppealById(testEventId)

      Assert.assertNotNull(result)
      Assert.assertEquals(result.size(), 1)
      val posE = result.iterator.next()
      Assert.assertEquals(posE._1.getId.intValue(), testEventId)
      Assert.assertEquals(posE._2.size(), 1)
      val posA = posE._2.iterator.next()
      Assert.assertEquals(posA._1.getId.intValue(), 1)
      Assert.assertEquals(posA._1.getId.intValue(), testAction.getId.intValue())
      Assert.assertEquals(posA._2.size(), apvalues.size())
      for(i <- 0 until 3) {
        Assert.assertNotNull(posA._2.get((ap.get(i).getId, ap.get(i))))
        Assert.assertEquals(posA._2.get((ap.get(i).getId, ap.get(i))).get(0), apv.get(i).getValue)
      }
      logger.info("The method has been successfully completed. Result is: {}", mapper.writeValueAsString(result))
      logger.warn("Successful end of getAppealById test")
    }
    catch {
      case ex: CoreException => {
        logger.error("getAppealById test failed with error: {}", ex.getMessage + " " + ex.getStackTrace)
        Assert.fail()
      }
    }
  }

  @Test
  def testGetAppealByIdBadEventException = {
    logger.warn("Start of getAppealById test with bad event's data:")
    when(dbEventBean.getEventById(TEvent_id)).thenReturn(null)
    this.testGetAppealByIdFail(TEvent_id, new CoreException("Обращение с id=%d не найдено в БД".format(TEvent_id)))
    logger.warn("Successful end of getAppealById test with bad events's data")
  }

  @Test
  def testGetAppealByIdBadActionException = {
    logger.warn("Start of getAppealById test without primary assesments data:")

    val testEvent = testData.getTestDefaultEvent(TEvent_id)
    when(dbEventBean.getEventById(TEvent_id)).thenReturn(testEvent)
    when(actionBean.getAppealActionByEventId(testEvent.getId.intValue(),
      ConfigManager.Messages("db.actionType.hospitalization.primary").toInt)).thenReturn(null)
    this.testGetAppealByIdFail(TEvent_id, new CoreException("Первичный осмотр для обращения с id=%d не найден в БД".format(TEvent_id)))

    logger.warn("Successful end of getAppealById test without primary assesments data")
  }

  private def testGetAppealByIdFail(event_id: Int, error: CoreException) = {

    logger.info("Request data for method is: {}", "event_id=%d".format(event_id))

    try{
      val result = appealBean.getAppealById(event_id)
      logger.info("The method has been successfully completed. Result is: {}", mapper.writeValueAsString(result))
    }
    catch {
      case ex: CoreException => {
        logger.info("The method has been completed with named error: {}", ex.getMessage + " " + ex.getStackTrace)
        if(ex.getMessage.compareTo(error.getMessage)!=0)
          Assert.fail("Not under test error")
      }
    }
  }

  //______________________________________________________________________________________________________
  //****************************************   getAllAppealsByPatient Tests   ********************************
  //______________________________________________________________________________________________________

  @Test
  def testGetAllAppealsByPatientSuccess = {

    logger.warn("Start of getAllAppealsByPatient test:")

    val filter = new AppealSimplifiedRequestDataFilter(1, 0, (new Date()).getTime, 0, 2, null, null, asJavaCollection(Set("12")));
    val request = new AppealSimplifiedRequestData("id", "asc", 10, 1, filter)
    val authData: AuthData = null

    val diagnoses = new java.util.HashMap[Object, Object]()
    val map = new java.util.HashMap[Event, Object]()

    val event = List(testData.getTestDefaultEvent(1), testData.getTestDefaultEvent(2))
    val dep1 = testData.getTestDefaultOrgStructure(1)
    val dep2 = testData.getTestDefaultOrgStructure(2)
    val dia1 = testData.getTestDefaultDiagnostic(1)
    val dia2 = testData.getTestDefaultActionProperty(1, testData.getTestDefaultActionPropertyType(1, "testCode_%d".format(1)))
    val mkb1 = null
    val mkb2 = testData.getTestDefaultMkb(2)
    diagnoses.put(dia1, mkb1)
    diagnoses.put(dia2, mkb2)

    map.put(event.get(0), (diagnoses, dep1))
    map.put(event.get(1), (diagnoses, dep2))

    try{
      logger.info("Request data for method is: {}", "request=%s, auth=%s".format(mapper.writeValueAsString(request), mapper.writeValueAsString(authData)))

      when(dbCustomQueryBean.getAllAppealsWithFilter(anyInt(), anyInt(), anyString(), anyString(), anyObject(), anyObject())).thenReturn(map)

      val result = appealBean.getAllAppealsByPatient(request, authData)

      Assert.assertNotNull(result)
      Assert.assertNotNull(result.data)
      Assert.assertEquals(result.data.size(), map.size())
      for(i <- 0 until result.data.size()) {
        val pos = result.data.get(i)
        Assert.assertEquals(pos.getId, event.get(i).getId.intValue())
        Assert.assertEquals(pos.getNumber, event.get(i).getExternalId)
        Assert.assertEquals(pos.getRangeAppealDateTime.getStart, event.get(i).getSetDate)
        Assert.assertEquals(pos.getRangeAppealDateTime.getEnd, event.get(i).getExecDate)
        val value0 = map.get(event.get(i))
        Assert.assertTrue(value0.isInstanceOf[(java.util.Map[Object,  Object],OrgStructure)])
        val value = value0.asInstanceOf[(java.util.Map[Object,  Object],OrgStructure)]
        Assert.assertEquals(pos.getDepartment.getId, value._2.getId.intValue())
        Assert.assertEquals(pos.getDepartment.getName, value._2.getName)
        Assert.assertEquals(pos.getDiagnoses.size(), value._1.size())

        val it = value._1.iterator
        for(j <- 0 until pos.getDiagnoses.size()) {
          val diag = it.next()
          if(diag._1.isInstanceOf[Diagnostic]){
            Assert.assertEquals(pos.getDiagnoses.get(j).getDiagnosticId, diag._1.asInstanceOf[Diagnostic].getId.intValue())
            Assert.assertEquals(pos.getDiagnoses.get(j).getDiagnosisKind, diag._1.asInstanceOf[Diagnostic].getDiagnosisType.getFlatCode)
            Assert.assertEquals(pos.getDiagnoses.get(j).getDescription, diag._1.asInstanceOf[Diagnostic].getNotes)
            Assert.assertEquals(pos.getDiagnoses.get(j).getInjury, diag._1.asInstanceOf[Diagnostic].getTraumaType.getName)
            Assert.assertEquals(pos.getDiagnoses.get(j).getMkb.getId, diag._1.asInstanceOf[Diagnostic].getDiagnosis.getMkb.getId.intValue())
            Assert.assertEquals(pos.getDiagnoses.get(j).getMkb.getCode, diag._1.asInstanceOf[Diagnostic].getDiagnosis.getMkb.getDiagID)
            Assert.assertEquals(pos.getDiagnoses.get(j).getMkb.getDiagnosis, diag._1.asInstanceOf[Diagnostic].getDiagnosis.getMkb.getDiagName)
          }
          else if(diag._1.isInstanceOf[ActionProperty]) {
            Assert.assertEquals(pos.getDiagnoses.get(j).getDiagnosisKind, diag._1.asInstanceOf[ActionProperty].getType.getCode)
            Assert.assertEquals(pos.getDiagnoses.get(j).getMkb.getId, diag._2.asInstanceOf[Mkb].getId.intValue())
            Assert.assertEquals(pos.getDiagnoses.get(j).getMkb.getCode, diag._2.asInstanceOf[Mkb].getDiagID)
            Assert.assertEquals(pos.getDiagnoses.get(j).getMkb.getDiagnosis, diag._2.asInstanceOf[Mkb].getDiagName)
          }
        }
      }
      logger.info("The method has been successfully completed. Result is: {}", mapper.writeValueAsString(result))
      logger.warn("Successful end of getAllAppealsByPatient test")
    }
    catch {
      case ex: CoreException => {
        logger.error("getAllAppealsByPatient test failed with error: {}", ex.getMessage + " " + ex.getStackTrace)
        Assert.fail()
      }
    }
  }

  //______________________________________________________________________________________________________
  //****************************************   checkAppealNumber Tests   ********************************
  //______________________________________________________________________________________________________

  @Test
  def testCheckAppealNumberTrue = {
    val number = "testNumber_new"
    logger.warn("Start of checkAppealNumber test:")
    when(dbEventBean.getAllAppealsForReceivedPatientByPeriod(anyInt(), anyInt(), anyString(), anyString(), anyObject())).thenReturn(null)
    try{
      logger.info("Request data for method is: {}", "number=%s".format(number))
      val result = appealBean.checkAppealNumber(number)
      Assert.assertTrue(result.booleanValue())
      logger.info("The method has been successfully completed. Result is: {}", mapper.writeValueAsString(result))
      logger.warn("Successful end of checkAppealNumber test")
    }
    catch {
      case ex: CoreException => {
        logger.error("checkAppealNumber test failed with error: {}", ex.getMessage + " " + ex.getStackTrace)
        Assert.fail()
      }
    }
  }

  @Test
  def testCheckAppealNumberFalse = {
    val number = "testNumber_existed"
    val list = seqAsJavaList(List(testData.getTestDefaultEvent))
    logger.warn("Start of checkAppealNumber test:")
    when(dbEventBean.getAllAppealsForReceivedPatientByPeriod(anyInt(), anyInt(), anyString(), anyString(), anyObject())).thenReturn(list)
    try{
      logger.info("Request data for method is: {}", "number=%s".format(number))
      val result = appealBean.checkAppealNumber(number)
      Assert.assertFalse(result.booleanValue())
      validateMockitoUsage()
      logger.info("The method has been successfully completed. Result is: {}", mapper.writeValueAsString(result))
      logger.warn("Successful end of checkAppealNumber test")
    }
    catch {
      case ex: CoreException => {
        logger.error("checkAppealNumber test failed with error: {}", ex.getMessage + " " + ex.getStackTrace)
        Assert.fail()
      }
    }
  }

  //______________________________________________________________________________________________________
  //****************************************   revokeAppealById Tests   ********************************
  //______________________________________________________________________________________________________

  @Test
  def testRevokeAppealByIdSuccess = {

    val event =  testData.getTestDefaultEvent(TEvent_id)
    val resultId = 1
    val authData = testData.getTestDefaultAuthData(testData.getTestDefaultStaff)

    logger.warn("Start of revokeAppealById test:")
    when(dbRbResultBean.getRbResultById(resultId)).thenReturn(new RbResult(resultId))

    try{
      logger.info("Request data for method is: {}", "event_id=%d, result_id=%d, auth=%s".format(TEvent_id, resultId, mapper.writeValueAsString(authData)))
      val result = appealBean.revokeAppealById(event, resultId, authData)
      Assert.assertNotNull(result)
      Assert.assertEquals(result.getModifyPerson, authData.user)
      Assert.assertEquals(result.getResult.getId.intValue(), resultId)
      validateMockitoUsage()
      logger.info("The method has been successfully completed. Result is: {}", result.toString)
      logger.warn("Successful end of revokeAppealById test")
    }
    catch {
      case ex: CoreException => {
        logger.error("revokeAppealById test failed with error: {}", ex.getMessage + " " + ex.getStackTrace)
        Assert.fail()
      }
    }
  }

  //______________________________________________________________________________________________________
  //****************************************   getPatientsHospitalizedStatus Tests   ********************************
  //______________________________________________________________________________________________________

  @Test
  def testGetPatientsHospitalizedStatusSuccess = {

    val event =  testData.getTestDefaultEvent(TEvent_id)
    val setATIdsPrimary = JavaConversions.setAsJavaSet(Set(ConfigManager.Messages("db.actionType.hospitalization.primary").toInt :java.lang.Integer))
    val setATIdsMoving = JavaConversions.setAsJavaSet(Set(ConfigManager.Messages("db.actionType.moving").toInt :java.lang.Integer))
    val setATIdsPrimarySecondary =JavaConversions.setAsJavaSet(Set(ConfigManager.Messages("db.actionType.primary").toInt :java.lang.Integer, ConfigManager.Messages("db.actionType.secondary").toInt :java.lang.Integer))
    val lstSentToIds = JavaConversions.seqAsJavaList(List(ConfigManager.Messages("db.rbCAP.hosp.primary.id.sentTo").toInt :java.lang.Integer))
    val lstCancelIds = JavaConversions.seqAsJavaList(List(ConfigManager.Messages("db.rbCAP.hosp.primary.id.cancel").toInt :java.lang.Integer))

    var apt = List.empty[ActionPropertyType]
    var ap = List.empty[ActionProperty]
    val apvalues = new java.util.HashMap[ActionProperty, java.util.List[APValue]]()
    val apv = List[APValue](testData.getTestDefaultAPValueOrgStructure(1),
      testData.getTestDefaultAPValueTime(new Date()),
      testData.getTestDefaultAPValueString("test"))

    for(i <- 0 until 3) {
      apt :+= testData.getTestDefaultActionPropertyType(i+1, "testCode_%d".format(i+1))
      ap :+= testData.getTestDefaultActionProperty(i+1, apt.get(i))
      apvalues.put(ap.get(i), seqAsJavaList(List[APValue](apv.get(i))))
    }

    logger.warn("Start of getPatientsHospitalizedStatus test:")
    when(dbEventBean.getEventById(TEvent_id)).thenReturn(event)
    when(actionBean.getLastActionByActionTypeIdAndEventId (TEvent_id, setATIdsPrimary)).thenReturn(TAction_id)
    when(actionPropertyBean.getActionPropertiesByActionIdAndRbCoreActionPropertyIds(TAction_id, lstSentToIds)).thenReturn(apvalues)
    when(actionPropertyBean.getActionPropertiesByActionIdAndRbCoreActionPropertyIds(TAction_id, lstCancelIds)).thenReturn(apvalues)
    when(actionBean.getLastActionByActionTypeIdAndEventId(TEvent_id, setATIdsMoving)).thenReturn(TAction_id+1)
    when(actionBean.getLastActionByActionTypeIdAndEventId(TEvent_id, setATIdsPrimarySecondary)).thenReturn(TAction_id)

    try{
      logger.info("Request data for method is: {}", "event_id=%d".format(TEvent_id))
      val result = appealBean.getPatientsHospitalizedStatus(TEvent_id)
      Assert.assertNotNull(result)
      Assert.assertEquals(ConfigManager.Messages("patient.status.regToBed").toString, result)
      validateMockitoUsage()
      logger.info("The method has been successfully completed. Result is: {}", result.toString)
      logger.warn("Successful end of getPatientsHospitalizedStatus test")
    }
    catch {
      case ex: CoreException => {
        logger.error("getPatientsHospitalizedStatus test failed with error: {}", ex.getMessage + " " + ex.getStackTrace)
        Assert.fail()
      }
    }
  }

  //______________________________________________________________________________________________________
  //****************************************   getDiagnosisListByAppealId Tests   ********************************
  //______________________________________________________________________________________________________

  @Test
  def testGetDiagnosisListByAppealIdSuccess = {

    logger.warn("Start of getDiagnosisListByAppealId test:")
    val filter = new ReceivedRequestDataFilter(TEvent_id, "", 0, "", 0, (new Date()).getTime, "", 1)

    val map = new java.util.HashMap[ActionProperty, java.util.List[APValue]]()
    val apt1 = testData.getTestDefaultActionPropertyType(1, "testCode_1")
    apt1.setName("Диагноз направившего учреждения")
    val apt2 = testData.getTestDefaultActionPropertyType(2, "testCode_2")
    apt2.setName("Test property")
    val ap1 = testData.getTestDefaultActionProperty(1, apt1)
    val ap2 = testData.getTestDefaultActionProperty(2, apt2)
    val apv1 = List[APValue](testData.getTestDefaultAPValueMkb(1))
    val apv2 = List[APValue](testData.getTestDefaultAPValueString("test"))
    map.put(ap1, apv1)
    map.put(ap2, apv2)

    when(actionBean.getLastActionByActionTypeIdAndEventId (anyInt(), anyObject())).thenReturn(TAction_id)
    when(actionPropertyBean.getActionPropertiesByActionId(TAction_id)).thenReturn(map)

    try{
      logger.info("Request data for method is: {}", "event_id=%d, filter=%s".format(TEvent_id, mapper.writeValueAsString(filter)))
      val result = appealBean.getDiagnosisListByAppealId(TEvent_id, filter.diagnosis)
      Assert.assertNotNull(result)
      Assert.assertEquals(result.size(), 3)
      Assert.assertEquals(result.get("assignment").size(), 1)
      Assert.assertEquals(result.get("attendant").size(), 0)
      Assert.assertEquals(result.get("aftereffect").size(), 0)

      validateMockitoUsage()
      logger.info("The method has been successfully completed. Result is: {}", result.toString)
      logger.warn("Successful end of getDiagnosisListByAppealId test")
    }
    catch {
      case ex: CoreException => {
        logger.error("getDiagnosisListByAppealId test failed with error: {}", ex.getMessage + " " + ex.getStackTrace)
        Assert.fail()
      }
    }
  }
}
