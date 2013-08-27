package ru.korus.tmis.core

import auth.AuthData
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
import ru.korus.tmis.util.ConfigManager
import ru.korus.tmis.test.data.{TestDataEntity, TestDataEntityImpl}
import org.codehaus.jackson.map.ObjectMapper
import scala.collection.JavaConversions._
import java.util.Date
import javax.persistence.EntityManager
import java.util

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
  @Mock var eventBean: DbEventBeanLocal = _
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
  @Mock var dbClientQuoting: DbClientQuotingBeanLocal = _
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
    def unapply(pos: T) = seq find (pos ==) map (seq indexOf _)
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
       apvalues.put(ap.get(i), asJavaList(List[APValue](apv.get(i))))
    }

    try{
      //appealData : AppealData, event: Event, flgCreate: Boolean, authData: AuthData
      logger.info("Request data for method is: {}", "event_id=%d".format(TEvent_id))

      when(eventBean.getEventById(testEventId)).thenReturn(testEvent)
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
    when(eventBean.getEventById(TEvent_id)).thenReturn(null)
    this.testGetAppealByIdFail(TEvent_id, new CoreException("Обращение с id=%d не найдено в БД".format(TEvent_id)))
    logger.warn("Successful end of getAppealById test with bad events's data")
  }

  @Test
  def testGetAppealByIdBadActionException = {
    logger.warn("Start of getAppealById test without primary assesments data:")

    val testEvent = testData.getTestDefaultEvent(TEvent_id)
    when(eventBean.getEventById(TEvent_id)).thenReturn(testEvent)
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

  /*@Test
  def testGetAllAppealsByPatientSuccess = {

    logger.warn("Start of getAllAppealsByPatient test:")

    val filter = new AppealSimplifiedRequestDataFilter(1, 0, (new Date()).getTime, 0, 2, null, null, asJavaCollection(Set("12")));
    val request = new AppealSimplifiedRequestData("id", "asc", 10, 1, filter)
    val authData: AuthData = null

    val diagnoses = new java.util.HashMap[Diagnostic, Mkb]()
    val map = new java.util.HashMap[Event, Object]()

    try{
      //appealData : AppealData, event: Event, flgCreate: Boolean, authData: AuthData
      logger.info("Request data for method is: {}", "request=%s, auth=%s".format(mapper.writeValueAsString(request), mapper.writeValueAsString(authData)))

      when(dbCustomQueryBean.getAllAppealsWithFilter(anyInt(), anyInt(), anyString(), anyString(), anyObject(), anyObject())).thenReturn(map)

      val result = appealBean.getAllAppealsByPatient(request, authData)

      Assert.assertNotNull(result)

      logger.info("The method has been successfully completed. Result is: {}", mapper.writeValueAsString(result))
      logger.warn("Successful end of getAllAppealsByPatient test")
    }
    catch {
      case ex: CoreException => {
        logger.error("getAllAppealsByPatient test failed with error: {}", ex.getMessage + " " + ex.getStackTrace)
        Assert.fail()
      }
    }
  } */
}
