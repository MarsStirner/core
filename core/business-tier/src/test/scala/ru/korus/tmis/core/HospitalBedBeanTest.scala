package ru.korus.tmis.core

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
import ru.korus.tmis.core.patient.HospitalBedBean
import org.slf4j.{LoggerFactory, Logger}
import org.junit.{Assert, Test, Before}
import data.{HospitalBedDataListFilter, HospitalBedData}
import ru.korus.tmis.test.data.{TestDataEntity, TestDataEntityImpl}
import org.codehaus.jackson.map.ObjectMapper
import scala.collection.JavaConversions._
import java.util.Date
import javax.persistence.EntityManager
import ru.korus.tmis.scala.util.ConfigManager
import scala.language.reflectiveCalls

/**
 * Author:      Ivan Dmitriev <br>
 * Date:        15.08.13, 11:15  <br>
 * Company:     Systema-Soft<br>
 * Description: Тесты для HospitalBedBean<br>
 */
@RunWith(classOf[MockitoJUnitRunner])
class HospitalBedBeanTest {

  @InjectMocks
  var hospitalBedBean = new HospitalBedBean()

  @Mock var em: EntityManager = _
  @Mock var appLock: AppLockBeanLocal = _
  @Mock var eventBean: DbEventBeanLocal = _
  @Mock var actionBean: DbActionBeanLocal = _
  @Mock var actionPropertyTypeBean: DbActionPropertyTypeBeanLocal = _
  @Mock var actionPropertyBean: DbActionPropertyBeanLocal = _
  @Mock var dbManager: DbManagerBeanLocal = _
  @Mock var dbOrgStructureBean: DbOrgStructureBeanLocal = _
  @Mock var dbRbCoreActionPropertyBean: DbRbCoreActionPropertyBeanLocal = _
  @Mock var commonDataProcessor: CommonDataProcessorBeanLocal = _
  @Mock var dbEventPerson: DbEventPersonBeanLocal = _
  @Mock var dbOrgStructureHospitalBed: DbOrgStructureHospitalBedBeanLocal = _

  final val logger: Logger = LoggerFactory.getLogger(classOf[HospitalBedBeanTest])

  private class IndexOf[T] (seq: Seq[T]) {
    def unapply(pos: T) = seq find (pos ==) map (seq indexOf _)
  }

  private val list = List(ConfigManager.RbCAPIds("db.rbCAP.moving.id.movedFrom").toInt,
                          ConfigManager.RbCAPIds("db.rbCAP.moving.id.beginTime").toInt,
                          ConfigManager.RbCAPIds("db.rbCAP.moving.id.located").toInt,
                          ConfigManager.RbCAPIds("db.rbCAP.moving.id.bed").toInt,
                          ConfigManager.RbCAPIds("db.rbCAP.moving.id.patronage").toInt,
                          ConfigManager.RbCAPIds("db.rbCAP.moving.id.endTime").toInt,
                          ConfigManager.RbCAPIds("db.rbCAP.moving.id.movedIn").toInt)

  final val unknownOperation = 0
  final val directionInDepartment = 1    //Направление в отделение
  final val movingInDepartment = 2       //Перевод в отделение

  val mapper: ObjectMapper = new ObjectMapper()

  final val testData: TestDataEntity = new TestDataEntityImpl()

  final val TEvent_id = 1
  final val TInvalid_event_id = 2
  final val TAction_id = 1
  final val TInvalid_action_id = 2
  final val TFlg_Create = 0
  final val TFlg_Modify = 1

  final val TorgStructDirection = 1
  final val TorgStructTransfer = 2
  final val TtimeArrival = new Date()
  final val Tpatronage = "да"
  final val ThospitalBed = 5
  final val TorgStructReceived = 3
  final val ThospOrgStruct = 4

  final val codes_all = setAsJavaSet(Set[String](ConfigManager.Messages("db.apt.received.codes.orgStructDirection").toString,
                                              ConfigManager.Messages("db.apt.moving.codes.orgStructTransfer").toString,
                                              ConfigManager.Messages("db.apt.moving.codes.timeArrival").toString,
                                              ConfigManager.Messages("db.apt.moving.codes.patronage").toString,
                                              ConfigManager.Messages("db.apt.moving.codes.hospitalBed").toString,
                                              ConfigManager.Messages("db.apt.moving.codes.orgStructReceived").toString,
                                              ConfigManager.Messages("db.apt.moving.codes.hospOrgStruct").toString))

  val codes_move = setAsJavaSet(Set[String]( ConfigManager.Messages("db.apt.moving.codes.hospitalBed"),
                                          ConfigManager.Messages("db.apt.moving.codes.orgStructTransfer"),
                                          ConfigManager.Messages("db.apt.moving.codes.timeArrival"),
                                          ConfigManager.Messages("db.apt.moving.codes.timeLeaved")))

  val codes_receive = setAsJavaSet(Set(ConfigManager.Messages("db.apt.received.codes.orgStructDirection")))

  @Before
  def init() {
  }

  //______________________________________________________________________________________________________
  //****************************************   verificationData Tests   ********************************
  //______________________________________________________________________________________________________

  @Test
  def testVerificationDataSuccessfully= {

    logger.warn("Start of verificationData test:")

    val hbData: HospitalBedData = new HospitalBedData(testData.getTestDefaultAction(TAction_id), null, null, null)
    val flgParent = 0

    val testEvent =  testData.getTestDefaultEvent(TEvent_id)
    val testAction = testData.getTestDefaultAction(TAction_id)
    testAction.getActionType().setCode("4202")

    try{
      logger.info("Request data for method is: {}", "event_id=%d, action_id=%d, %s, flgParent=%d"
               .format(TEvent_id, TAction_id, mapper.writeValueAsString(hbData), flgParent))

      when(eventBean.getEventById(TEvent_id)).thenReturn(testEvent)
      when(actionBean.getActionById(TAction_id)).thenReturn(testAction)

      val result = hospitalBedBean.verificationData(TEvent_id, TAction_id, hbData, 0)
      Assert.assertTrue(result)

      logger.info("The method has been successfully completed. Result is: {}", mapper.writeValueAsString(result))
      logger.warn("Successful end of verificationData test")
    }
    catch {
      case ex: CoreException => {
        logger.error("verificationData test failed with error: {}", ex.getMessage + " " + ex.getStackTrace)
      }
    }
  }

  @Test
  def testVerificationDataBadHBData = {
    logger.warn("Start of verificationData test with bad hospitalbed's data:")
    this.testVerificationDataBadData(TEvent_id, TAction_id, null, TFlg_Create, new CoreException("Некорректные данные в HospitalBedData"))
    logger.warn("Successful end of verificationData test with bad hospitalbed's data")
  }

  @Test
  def testVerificationDataBadEvent = {
    val hbData = new HospitalBedData(testData.getTestDefaultAction(TAction_id), null, null, null)
    logger.warn("Start of verificationData test with bad event's data:")
    this.testVerificationDataBadData(TInvalid_event_id, TAction_id, hbData, TFlg_Create, new CoreException("В таблице Event БД нету записи с заданным в запросе id"))
    logger.warn("Successful end of verificationData test with bad event's data")
  }

  @Test
  def testVerificationDataBadAction = {
    val hbData = new HospitalBedData(testData.getTestDefaultAction(TInvalid_action_id), null, null, null)
    logger.warn("Start of verificationData test with bad action's data:")
    this.testVerificationDataBadData(TEvent_id, TInvalid_action_id, hbData, TFlg_Modify, new CoreException("Некорректный Action id в запросе. Тип Action не 'Движение'"))
    logger.warn("Successful end of verificationData test with bad action's data")
  }

  private def testVerificationDataBadData(event_id: Int, action_id:Int, hbData: HospitalBedData, flgParent:Int, error: CoreException) = {

    logger.info("Request data for method is: {}", "event_id=%d, action_id=%d, %s, flgParent=%d"
          .format(event_id, action_id, mapper.writeValueAsString(hbData), flgParent))

    val testEvent =  testData.getTestDefaultEvent(event_id)
    val testAction = testData.getTestDefaultAction(action_id)
    testAction.getActionType().setCode("4202")
    val invalidTestAction = testData.getTestDefaultAction(action_id)

    when(eventBean.getEventById(TEvent_id)).thenReturn(testEvent)
    when(eventBean.getEventById(TInvalid_event_id)).thenReturn(null)
    when(actionBean.getActionById(TAction_id)).thenReturn(testAction)
    when(actionBean.getActionById(TInvalid_action_id)).thenReturn(invalidTestAction)

    try{
      val result = hospitalBedBean.verificationData(event_id, action_id, hbData, flgParent)
      Assert.assertFalse(result)
      logger.info("The method has been successfully completed. Result is: {}", mapper.writeValueAsString(result))
    }
    catch {
      case ex: CoreException => {
        logger.info("verificationData test completed with named error: {}", ex.getMessage + " " + ex.getStackTrace)
        if(ex.getMessage.compareTo(error.getMessage)!=0)
          Assert.fail("Not under test error")
      }
    }
  }

  //______________________________________________________________________________________________________
  //****************************************   registryPatientToHospitalBed Tests   ********************************
  //______________________________________________________________________________________________________

  @Test
  def testRegistryPatientToHospitalBedSuccessfully = {

    logger.warn("Start of registryPatientToHospitalBed test:")

    val hbData: HospitalBedData = new HospitalBedData(testData.getTestDefaultAction(TAction_id), null, null, null)
    val authData = null

    val testEvent =  testData.getTestDefaultEvent(TEvent_id)
    val testLastAction = testData.getTestDefaultAction(TAction_id)
    testLastAction.getActionType().setFlatCode(ConfigManager.Messages("db.action.movingFlatCode"))
    val testAction = testData.getTestDefaultAction(TAction_id+1)
    testAction.getActionType().setCode("4202")
    val list_apt = new java.util.LinkedList[ActionPropertyType]()
    list_apt.add(testData.getTestDefaultActionPropertyType(1, ConfigManager.Messages("db.apt.moving.codes.hospOrgStruct")))
    list_apt.add(testData.getTestDefaultActionPropertyType(2, ConfigManager.Messages("db.apt.moving.codes.orgStructTransfer")))
    list_apt.add(testData.getTestDefaultActionPropertyType(3, ConfigManager.Messages("db.apt.moving.codes.timeArrival")))
    list_apt.add(testData.getTestDefaultActionPropertyType(4, ConfigManager.Messages("db.apt.moving.codes.timeLeaved")))
    list_apt.add(testData.getTestDefaultActionPropertyType(5, ConfigManager.Messages("db.apt.moving.codes.patronage")))
    list_apt.add(testData.getTestDefaultActionPropertyType(6, ConfigManager.Messages("db.apt.moving.codes.orgStructReceived")))
    val department = testData.getTestDefaultOrgStructure(1)
    val codes1 = Set[String](ConfigManager.Messages("db.apt.moving.codes.orgStructTransfer"))
    val codes2 = Set[String](ConfigManager.Messages("db.apt.moving.codes.timeLeaved"))
    val codes3 = Set[String](ConfigManager.Messages("db.apt.moving.codes.hospOrgStruct"))
    val apvalues1 = new java.util.HashMap[ActionProperty, java.util.List[APValue]]()
    apvalues1.put(testData.getTestDefaultActionProperty(1), seqAsJavaList(List[APValue](testData.getTestDefaultAPValueOrgStructure(1))))
    val apvalues2 = new java.util.HashMap[ActionProperty, java.util.List[APValue]]()
    apvalues2.put(testData.getTestDefaultActionProperty(2), seqAsJavaList(List[APValue](testData.getTestDefaultAPValueTime(new Date()))))
    val apvalues3 = new java.util.HashMap[ActionProperty, java.util.List[APValue]]()
    apvalues3.put(testData.getTestDefaultActionProperty(3), seqAsJavaList(List[APValue](testData.getTestDefaultAPValueOrgStructure(2))))
    val testEventPerson = testData.getTestDefaultEventPerson(1, testEvent, testData.getTestDefaultStaff)

    try{
      logger.info("Request data for method is: {}", "event_id=%d, %s, authData=%s"
        .format(TEvent_id, mapper.writeValueAsString(hbData), mapper.writeValueAsString(authData)))

      when(eventBean.getEventById(TEvent_id)).thenReturn(testEvent)
      when(actionBean.getActionsWithFilter(anyInt(), anyInt(), anyString(), anyObject(), anyObject(), anyObject())).thenReturn(seqAsJavaList(List(testLastAction)))
      when(actionBean.createAction(anyInt(), anyInt(), anyObject())).thenReturn(testAction)
      when(actionPropertyTypeBean.getActionPropertyTypesByActionTypeId(ConfigManager.Messages("db.actionType.moving").toInt)).thenReturn(list_apt)
      when(dbOrgStructureBean.getOrgStructureByHospitalBedId(hbData.data.bedRegistration.bedId.intValue())).thenReturn(department)
      when(actionPropertyBean.getActionPropertiesByActionIdAndActionPropertyTypeCodes(testLastAction.getId.intValue, codes1)).thenReturn(apvalues1)
      when(actionPropertyBean.getActionPropertiesByActionIdAndActionPropertyTypeCodes(testLastAction.getId.intValue, codes2)).thenReturn(apvalues2)
      when(actionPropertyBean.getActionPropertiesByActionIdAndActionPropertyTypeCodes(testLastAction.getId.intValue, codes3)).thenReturn(apvalues3)
      when(dbEventPerson.getLastEventPersonForEventId(TEvent_id)).thenReturn(testEventPerson)
      when(actionPropertyBean.createActionProperty(anyObject[Action](), anyInt(), anyObject())).thenReturn(testData.getTestDefaultActionProperty)
      when(actionPropertyBean.setActionPropertyValue(anyObject[ActionProperty](), anyString(), anyInt())).thenReturn(testData.getTestDefaultAPValueOrgStructure(1))
      doReturn(null).when(em).merge(any())
      doNothing().when(em).flush()
      doNothing().when(dbManager).persist(any())
      doNothing().when(dbManager).persistAll(any())

      val result = hospitalBedBean.registryPatientToHospitalBed(TEvent_id, hbData, null)

      Assert.assertNotNull(result)
      Assert.assertNull(testAction.getEndDate)

      logger.info("The method has been successfully completed. Result is: {}", result.toString)
      logger.warn("Successful end of registryPatientToHospitalBed test")
    }
    catch {
      case ex: CoreException => {
        logger.error("registryPatientToHospitalBed test failed with error: {}", ex.getMessage + " " + ex.getStackTrace)
        Assert.fail()
      }
    }
  }

  //______________________________________________________________________________________________________
  //****************************************   getCaseHospitalBedsByDepartmentId Tests   ********************************
  //______________________________________________________________________________________________________

  @Test
  def testGetCaseHospitalBedsByDepartmentIdSuccessfully = {

    logger.warn("Start of getCaseHospitalBedsByDepartmentId test:")
    //Все койки отделения
    val list_all = new java.util.LinkedList[OrgStructureHospitalBed]()
    list_all.add(testData.getTestDefaultOrgStructureHospitalBed(1))
    list_all.add(testData.getTestDefaultOrgStructureHospitalBed(2))
    list_all.add(testData.getTestDefaultOrgStructureHospitalBed(3))
    list_all.add(testData.getTestDefaultOrgStructureHospitalBed(4))
    list_all.add(testData.getTestDefaultOrgStructureHospitalBed(5))
    //Занятые койки отделения
    val list_busy = new java.util.LinkedList[OrgStructureHospitalBed]()
    list_busy.add(testData.getTestDefaultOrgStructureHospitalBed(1))
    list_busy.add(testData.getTestDefaultOrgStructureHospitalBed(4))
    list_busy.add(testData.getTestDefaultOrgStructureHospitalBed(5))

    try
    {
      logger.info("Request data for method is: {}", "departmentId=%d".format(1))

      when(dbOrgStructureHospitalBed.getHospitalBedByDepartmentId(anyInt())).thenReturn(list_all)
      when(dbOrgStructureHospitalBed.getBusyHospitalBedByIds(anyObject())).thenReturn(list_busy)

      val result = hospitalBedBean.getCaseHospitalBedsByDepartmentId(1)

      verify(dbOrgStructureHospitalBed).getHospitalBedByDepartmentId(anyInt())
      verify(dbOrgStructureHospitalBed).getBusyHospitalBedByIds(anyObject())

      Assert.assertNotNull(result)
      Assert.assertEquals(result.size(), list_all.size())

      for(i <- 0 until list_all.size()){
        if (i==0||i==3||i==4)
          Assert.assertEquals(result.get(list_all.get(i)), true)
        else
          Assert.assertEquals(result.get(list_all.get(i)), false)
      }
      logger.info("The method has been successfully completed. Result is: {}", result.toString)
      logger.warn("Successful end of getCaseHospitalBedsByDepartmentId test")
    }
    catch {
      case ex: CoreException => {
        logger.error("getCaseHospitalBedsByDepartmentId test failed with error: {}", ex.getMessage + " " + ex.getStackTrace)
        Assert.fail()
      }
    }
  }

  @Test
  def testGetRegistryOriginalFormSuccessfully = {

    logger.warn("Start of getRegistryOriginalForm test:")

    val testAction = testData.getTestDefaultAction(TAction_id)
    val auth = null

    try
    {
      logger.info("Request data for method is: {}", "action=%s, auth=%s".format(testAction.toString, auth))

      when(actionPropertyBean.getActionPropertiesByActionIdAndActionPropertyTypeCodes(anyInt(), anyObject())).thenReturn(this.getDefaultHospitalBedsValues(codes_all))
      val result = hospitalBedBean.getRegistryOriginalForm(testAction, auth)
      verify(actionPropertyBean).getActionPropertiesByActionIdAndActionPropertyTypeCodes(anyInt(), anyObject())

      Assert.assertNotNull(result)
      Assert.assertNotNull(result.data)
      Assert.assertNotNull(result.data.bedRegistration)
      val testing = result.data.bedRegistration
      Assert.assertEquals(testing.getClientId, testAction.getEvent.getPatient.getId.intValue())
      Assert.assertEquals(testing.getBedId, ThospitalBed)
      Assert.assertEquals(testing.getMovedFromUnitId, TorgStructReceived)
      Assert.assertEquals(testing.getPatronage, Tpatronage)

      logger.info("The method has been successfully completed. Result is: {}", result.toString)
      logger.warn("Successful end of getRegistryOriginalForm test")
    }
    catch {
      case ex: CoreException => {
        logger.error("getRegistryOriginalForm test failed with error: {}", ex.getMessage + " " + ex.getStackTrace)
        Assert.fail()
      }
    }
  }

  @Test
  def testGetRegistryFormWithChamberListSuccessfully = {

    logger.warn("Start of getRegistryFormWithChamberList test:")

    val testAction = testData.getTestDefaultAction(TAction_id)
    val auth = null

    //Все койки отделения
    val list_all = new java.util.LinkedList[OrgStructureHospitalBed]()
    list_all.add(testData.getTestDefaultOrgStructureHospitalBed(1))
    list_all.add(testData.getTestDefaultOrgStructureHospitalBed(2))
    list_all.add(testData.getTestDefaultOrgStructureHospitalBed(3))
    list_all.add(testData.getTestDefaultOrgStructureHospitalBed(4))
    list_all.add(testData.getTestDefaultOrgStructureHospitalBed(5))
    //Занятые койки отделения
    val list_busy = new java.util.LinkedList[OrgStructureHospitalBed]()
    list_busy.add(testData.getTestDefaultOrgStructureHospitalBed(1))
    list_busy.add(testData.getTestDefaultOrgStructureHospitalBed(4))
    list_busy.add(testData.getTestDefaultOrgStructureHospitalBed(5))

    try
    {
      logger.info("Request data for method is: {}", "action=%s, auth=%s".format(testAction.toString, auth))

      when(dbOrgStructureHospitalBed.getHospitalBedByDepartmentId(anyInt())).thenReturn(list_all)
      when(dbOrgStructureHospitalBed.getBusyHospitalBedByIds(anyObject())).thenReturn(list_busy)
      when(actionPropertyBean.getActionPropertiesByActionIdAndActionPropertyTypeCodes(anyInt(), anyObject())).thenReturn(this.getDefaultHospitalBedsValues(codes_all))
      val result = hospitalBedBean.getRegistryFormWithChamberList(testAction, auth)
      verify(actionPropertyBean).getActionPropertiesByActionIdAndActionPropertyTypeCodes(anyInt(), anyObject())

      Assert.assertNotNull(result)
      Assert.assertNotNull(result.data)
      Assert.assertNotNull(result.data.registrationForm)
      val testing = result.data.registrationForm
      Assert.assertEquals(testing.getClientId, testAction.getEvent.getPatient.getId.intValue())
      Assert.assertEquals(testing.getBedId, ThospitalBed)
      Assert.assertEquals(testing.getMovedFromUnitId, TorgStructReceived)
      Assert.assertEquals(testing.getPatronage, Tpatronage)
      Assert.assertEquals(testing.getChamberList.size(), 1)
      Assert.assertEquals(testing.getChamberList.get(0).getBedList.size(), list_all.size())
      for(i <- 0 until testing.getChamberList.get(0).getBedList.size()){
        val bed0 = testing.getChamberList.get(0).getBedList.get(i)
        Assert.assertEquals(bed0.getBedId, list_all.get(i).getId.intValue())
        Assert.assertEquals(bed0.getCode, list_all.get(i).getCode)
        Assert.assertEquals(bed0.getName, list_all.get(i).getName)
        if (i==0||i==3||i==4)
          Assert.assertEquals(bed0.getBusy, "yes")
        else
          Assert.assertEquals(bed0.getBusy, "no")
      }

      logger.info("The method has been successfully completed. Result is: {}", result.toString)
      logger.warn("Successful end of getRegistryFormWithChamberList test")
    }
    catch {
      case ex: CoreException => {
        logger.error("getRegistryFormWithChamberList test failed with error: {}", ex.getMessage + " " + ex.getStackTrace)
        Assert.fail()
      }
    }
  }

  @Test
  def testGetMovingListByEventIdAndFilterSuccessfully = {

    logger.warn("Start of getMovingListByEventIdAndFilter test:")

    val filter = new HospitalBedDataListFilter(TEvent_id)
    val auth = null

    val actions = new java.util.LinkedList[Action]()
    actions.add(testData.getTestDefaultAction(1, testData.getTestDefaultActionType(1, ConfigManager.Messages("db.action.admissionFlatCode"))))
    actions.add(testData.getTestDefaultAction(2, testData.getTestDefaultActionType(2, ConfigManager.Messages("db.action.movingFlatCode"))))
    actions.add(testData.getTestDefaultAction(3, testData.getTestDefaultActionType(3, ConfigManager.Messages("db.action.movingFlatCode"))))

    val data_receive = this.getDefaultHospitalBedsValues(codes_receive)
    val data_move = this.getDefaultHospitalBedsValues(codes_move)

    try
    {
      logger.info("Request data for method is: {}", "filter=%s, auth=%s".format(mapper.writeValueAsString(filter), mapper.writeValueAsString(auth)))

      when(actionBean.getActionsWithFilter(0, 0, filter.toSortingString("createDatetime", "asc"), filter.unwrap, null, auth)).thenReturn(actions)
      when(actionPropertyBean.getActionPropertiesByActionIdAndActionPropertyTypeCodes(1, codes_receive)).thenReturn(data_receive)
      when(actionPropertyBean.getActionPropertiesByActionIdAndActionPropertyTypeCodes(1, codes_move)).thenReturn(data_move)
      when(actionPropertyBean.getActionPropertiesByActionIdAndActionPropertyTypeCodes(2, codes_receive)).thenReturn(data_receive)
      when(actionPropertyBean.getActionPropertiesByActionIdAndActionPropertyTypeCodes(2, codes_move)).thenReturn(data_move)
      when(actionPropertyBean.getActionPropertiesByActionIdAndActionPropertyTypeCodes(3, codes_receive)).thenReturn(data_receive)
      when(actionPropertyBean.getActionPropertiesByActionIdAndActionPropertyTypeCodes(3, codes_move)).thenReturn(data_move)
      val result = hospitalBedBean.getMovingListByEventIdAndFilter(filter, auth)
      verify(actionBean).getActionsWithFilter(0, 0, filter.toSortingString("createDatetime", "asc"), filter.unwrap, null, auth)
      //verify(actionPropertyBean).getActionPropertiesByActionIdAndActionPropertyTypeCodes(anyInt(), anyObject())

      Assert.assertNotNull(result)
      Assert.assertNotNull(result.data)
      Assert.assertNotNull(result.data.moves)
      Assert.assertEquals(result.data.moves.size(), actions.size()+1)
      val testing =
      for(i <- 0 until result.data.moves.size()){
        val move0 = result.data.moves.get(i)

        if (i==result.data.moves.size()-1) {  //Последний
          Assert.assertEquals(move0.getUnitId, TorgStructTransfer)
          Assert.assertEquals(move0.getUnit.contains("Направлен в "), true)
          Assert.assertEquals(move0.getBed, "Положить на койку")
        }
        else {
          if (i==0) {                             //Первый
            Assert.assertEquals(move0.getUnitId, 28)
            Assert.assertEquals(move0.getUnit, "Приемное отделение")
          }
          else {
            Assert.assertEquals(move0.getUnitId, TorgStructDirection)
            Assert.assertEquals(move0.getBed.isEmpty, false)
          }
          Assert.assertEquals(move0.getId, actions.get(i).getId.intValue())
          Assert.assertEquals(move0.getDoctorCode, actions.get(i).getAssigner.getId.toString)
          Assert.assertEquals(move0.getAdmission, actions.get(i).getBegDate)
        }

      }

      logger.info("The method has been successfully completed. Result is: {}", result.toString)
      logger.warn("Successful end of getMovingListByEventIdAndFilter test")
    }
    catch {
      case ex: CoreException => {
        logger.error("getMovingListByEventIdAndFilter test failed with error: {}", ex.getMessage + " " + ex.getStackTrace)
        Assert.fail()
      }
    }
  }

  private def getDefaultHospitalBedsValues(codes: java.util.Set[String]) =  {

    val now = new Date()
    val map = new java.util.HashMap[ActionProperty, java.util.List[APValue]]()

    codes.foreach(code => {
      if(code.compareTo(ConfigManager.Messages("db.apt.received.codes.orgStructDirection").toString)==0){
        map.put(testData.getTestDefaultActionProperty(1,
          testData.getTestDefaultActionPropertyType(1, ConfigManager.Messages("db.apt.received.codes.orgStructDirection"))),
          seqAsJavaList(List[APValue](testData.getTestDefaultAPValueOrgStructure(TorgStructDirection))))
      }
      else if(code.compareTo(ConfigManager.Messages("db.apt.moving.codes.orgStructTransfer").toString)==0){
        map.put(testData.getTestDefaultActionProperty(2,
          testData.getTestDefaultActionPropertyType(2, ConfigManager.Messages("db.apt.moving.codes.orgStructTransfer"))),
          seqAsJavaList(List[APValue](testData.getTestDefaultAPValueOrgStructure(TorgStructTransfer))))
      }
      else if(code.compareTo(ConfigManager.Messages("db.apt.moving.codes.timeArrival").toString)==0){
        map.put(testData.getTestDefaultActionProperty(3,
          testData.getTestDefaultActionPropertyType(3, ConfigManager.Messages("db.apt.moving.codes.timeArrival"))),
          seqAsJavaList(List[APValue](testData.getTestDefaultAPValueTime(TtimeArrival))))
      }
      else if(code.compareTo(ConfigManager.Messages("db.apt.moving.codes.patronage").toString)==0){
        map.put(testData.getTestDefaultActionProperty(4,
          testData.getTestDefaultActionPropertyType(4, ConfigManager.Messages("db.apt.moving.codes.patronage"))),
          seqAsJavaList(List[APValue](testData.getTestDefaultAPValueString(Tpatronage))))
      }
      else if(code.compareTo(ConfigManager.Messages("db.apt.moving.codes.hospitalBed").toString)==0){
        map.put(testData.getTestDefaultActionProperty(5,
          testData.getTestDefaultActionPropertyType(5, ConfigManager.Messages("db.apt.moving.codes.hospitalBed"))),
          seqAsJavaList(List[APValue](testData.getTestDefaultAPValueHospitalBed(ThospitalBed))))
      }
      else if(code.compareTo(ConfigManager.Messages("db.apt.moving.codes.orgStructReceived").toString)==0){
        map.put(testData.getTestDefaultActionProperty(6,
          testData.getTestDefaultActionPropertyType(6, ConfigManager.Messages("db.apt.moving.codes.orgStructReceived"))),
          seqAsJavaList(List[APValue](testData.getTestDefaultAPValueOrgStructure(TorgStructReceived))))
      }
      else if(code.compareTo(ConfigManager.Messages("db.apt.moving.codes.hospOrgStruct").toString)==0){
        map.put(testData.getTestDefaultActionProperty(7,
          testData.getTestDefaultActionPropertyType(7, ConfigManager.Messages("db.apt.moving.codes.hospOrgStruct"))),
          seqAsJavaList(List[APValue](testData.getTestDefaultAPValueOrgStructure(ThospOrgStruct))))
      }
    })

    map
  }
}
