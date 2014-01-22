package ru.korus.tmis.ws

import impl.WebMisRESTImpl
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito._
import org.mockito.Matchers._
import org.mockito.runners.MockitoJUnitRunner
import org.mockito.Mockito._
import ru.korus.tmis.core.patient.{DirectionBeanLocal, HospitalBedBeanLocal, PatientBeanLocal, AppealBeanLocal}
import ru.korus.tmis.core.exception.CoreException
import org.slf4j.{LoggerFactory, Logger}
import org.codehaus.jackson.map.ObjectMapper
import ru.korus.tmis.core.data.{PatientsListRequestData, JobTicketStatusDataList, TakingOfBiomaterialRequesData, TakingOfBiomaterialRequesDataFilter}
import ru.korus.tmis.core.database.{DbJobTicketBeanLocal}
import ru.korus.tmis.core.entity.model._
import java.util
import java.util.Date
import ru.korus.tmis.test.data.{TestDataEntity, TestDataEntityImpl}
import ru.korus.tmis.core.database.common.DbActionPropertyBeanLocal

/**
 * Author:      Ivan Dmitriev <br>
 * Date:        01.08.13, 11:00  <br>
 * Company:     Systema-Soft<br>
 * Description: Тесты для REST-сервисов  <br>
 */
@RunWith(classOf[MockitoJUnitRunner])
class CustomInfoRESTTest {

  @InjectMocks
  var wsImpl = new WebMisRESTImpl()

  @Mock var appealBean: AppealBeanLocal = _

  @Mock var patientBean: PatientBeanLocal = _

  @Mock var dbJobTicketBean: DbJobTicketBeanLocal = _

  @Mock var hospitalBedBean: HospitalBedBeanLocal = _

  @Mock var actionPropertyBean: DbActionPropertyBeanLocal = _

  @Mock var directionBean: DirectionBeanLocal = _

  final val logger: Logger = LoggerFactory.getLogger(classOf[CustomInfoRESTTest])

  val mapper: ObjectMapper = new ObjectMapper()

  final val testData: TestDataEntity = new TestDataEntityImpl()

  @Before
  def init() {
  }

  @Test
  def testGetForm007 = {   //TODO: !!
    /*int depId = (departmentId>0) ? departmentId : this.auth.getUser().getOrgStructure().getId().intValue();
    return new JSONWithPadding(wsImpl.getForm007(depId, beginDate, endDate, this.auth),this.callback);
    seventhFormBean.getForm007LinearView(departmentId, beginDate, endDate)
    */
  }


  //______________________________________________________________________________________________________
  //****************************************   CheckAppealNumber Tests   ********************************
  //______________________________________________________________________________________________________

  @Test
  def testCheckAppealNumber = {

    logger.warn("Start of CheckAppealNumber test:")

    val number1 = "test_true_number"
    val number2 = "test_false_number"

    try {
      when(appealBean.checkAppealNumber(number1)).thenReturn(true)
      when(appealBean.checkAppealNumber(number2)).thenReturn(false)
      when(patientBean.checkSNILSNumber(number1)).thenReturn(true)
      when(patientBean.checkSNILSNumber(number2)).thenReturn(false)
      when(patientBean.checkPolicyNumber(number1, "", -1)).thenReturn(true)
      when(patientBean.checkPolicyNumber(number2, "", -1)).thenReturn(false)


      this.subTestCheckAppealNumber("appealNumber", -1, number1, "", true)
      verify(appealBean).checkAppealNumber(number1)
      this.subTestCheckAppealNumber("appealNumber", -1, number2, "", false)
      verify(appealBean).checkAppealNumber(number2)

      this.subTestCheckAppealNumber("SNILS", -1, number1, "", true)
      verify(patientBean).checkSNILSNumber(number1)
      this.subTestCheckAppealNumber("SNILS", -1, number2, "", false)
      verify(patientBean).checkSNILSNumber(number2)

      this.subTestCheckAppealNumber("policy", -1, number1, "", true)
      verify(patientBean).checkPolicyNumber(number1, "", -1)
      this.subTestCheckAppealNumber("policy", -1, number2, "", false)
      verify(patientBean).checkPolicyNumber(number2, "", -1)

      this.subTestCheckAppealNumber("", -1, number1, "", null)
      this.subTestCheckAppealNumber("", -1, number2, "", null)

      validateMockitoUsage()

      logger.warn("Successful end of CheckAppealNumber test")
    } catch {
      case ex: CoreException => {
        logger.error("GetEventTypes test failed with error: {}", ex.getMessage + " " + ex.getStackTrace)
      }
    }
  }

  private def subTestCheckAppealNumber (name: String, typeId: Int, number: String, serial: String, valid_result: java.lang.Boolean) {

    val logger_template = " name: %s, typeId: %d, number: %s, serial: %s"
    logger.info("Request data for method is: {}", logger_template.format(name, typeId, number, serial))

    val result = wsImpl.checkExistanceNumber(name, typeId, number, serial)

    if(valid_result!=null) {
      Assert.assertNotNull(result)
      Assert.assertNotNull(result.getTrueFalse)
      Assert.assertEquals(result.getTrueFalse, valid_result)
    }
    else Assert.assertNull(result)

    logger.info("The method has been successfully completed. Result is: {}", mapper.writeValueAsString(result))
  }

  //______________________________________________________________________________________________________
  //****************************************   GetTakingOfBiomaterial Tests   ********************************
  //______________________________________________________________________________________________________

  @Test
  def testGetTakingOfBiomaterial = {

    logger.warn("Start of GetTakingOfBiomaterial test:")
    //test data
    val department = 18
    val status: Short = -1

    val filter = new TakingOfBiomaterialRequesDataFilter(-1, department, 0L, 0L, status, 0)
    val request = new TakingOfBiomaterialRequesData("id", "asc", filter)

    val list = new java.util.LinkedList[(Action, ActionTypeTissueType, JobTicket)]
    list.add((testData.getTestDefaultAction(1), testData.getTestDefaultActionTypeTissueType(1), testData.getTestDefaultJobTicket(1)))
    list.add((testData.getTestDefaultAction(2), testData.getTestDefaultActionTypeTissueType(2), testData.getTestDefaultJobTicket(2)))
    list.add((testData.getTestDefaultAction(3), testData.getTestDefaultActionTypeTissueType(3), testData.getTestDefaultJobTicket(3)))


    val lastMoving = testData.getTestDefaultAction(4)
    val values = new util.LinkedList[APValue]
    values.add(testData.getTestDefaultAPValueHospitalBed(1))
    val apWithValues = testData.getTestDefaultActionPropertyWithValues(1, values).asInstanceOf[(ActionProperty, util.List[APValue])]
    val map = new util.HashMap[ActionProperty, util.List[APValue]]()
    map.put(apWithValues._1, apWithValues._2)

    try{
      logger.info("Request data for method is: {}", mapper.writeValueAsString(request))
      when(dbJobTicketBean.getDirectionsWithJobTicketsBetweenDate(request.sortingFieldInternal, request.filter)).thenReturn(list, Array[Object](): _*)
      when(hospitalBedBean.getLastMovingActionForEventId(anyInt())).thenReturn(lastMoving)
      when(actionPropertyBean.getActionPropertiesByActionIdAndRbCoreActionPropertyIds(anyObject(), anyListOf(classOf[java.lang.Integer]))).thenReturn(map)
      val result = wsImpl.getTakingOfBiomaterial(request, null)

      verify(dbJobTicketBean).getDirectionsWithJobTicketsBetweenDate(request.sortingFieldInternal, request.filter)
      Assert.assertNotNull(result)
      Assert.assertEquals(result.data.size(), list.size())
      for(i <- 0 until list.size()){
        Assert.assertEquals(result.data.get(i).id, list.get(i)._3.getId)
        Assert.assertEquals(result.data.get(i).date, list.get(i)._3.getDatetime)
        Assert.assertEquals(result.data.get(i).status, list.get(i)._3.getStatus)
        Assert.assertEquals(result.data.get(i).note, list.get(i)._3.getNote)
        Assert.assertEquals(result.data.get(i).label, list.get(i)._3.getLabel)
        Assert.assertEquals(result.data.get(i).appealNumber, list.get(i)._1.getEvent.getExternalId)
        Assert.assertEquals(result.data.get(i).department.id, list.get(i)._3.getJob.getOrgStructure.getId)
        Assert.assertEquals(result.data.get(i).department.name, list.get(i)._3.getJob.getOrgStructure.getName)
        Assert.assertEquals(result.data.get(i).biomaterial.id, list.get(i)._2.getId)
        Assert.assertEquals(result.data.get(i).biomaterial.amount, list.get(i)._2.getAmount)
        Assert.assertEquals(result.data.get(i).biomaterial.tissueType.id, list.get(i)._2.getTissueType.getId)
        Assert.assertEquals(result.data.get(i).biomaterial.tissueType.name, list.get(i)._2.getTissueType.getName)
        Assert.assertEquals(result.data.get(i).biomaterial.unit.id, list.get(i)._2.getUnit.getId)
        Assert.assertEquals(result.data.get(i).biomaterial.unit.name, list.get(i)._2.getUnit.getName)
        Assert.assertEquals(result.data.get(i).actions.size, 1)
        Assert.assertEquals(result.data.get(i).actions.get(0).id, list.get(i)._1.getId)
        Assert.assertEquals(result.data.get(i).actions.get(0).actionType.id, list.get(i)._1.getActionType.getId)
        Assert.assertEquals(result.data.get(i).actions.get(0).actionType.name, list.get(i)._1.getActionType.getName)
        Assert.assertEquals(result.data.get(i).actions.get(0).urgent, list.get(i)._1.getIsUrgent)
        Assert.assertEquals(result.data.get(i).actions.get(0).takenTissueJournal, list.get(i)._1.getTakenTissue.getId)
        Assert.assertEquals(result.data.get(i).actions.get(0).tubeType.id, list.get(i)._1.getActionType.getTestTubeType.getId)
        Assert.assertEquals(result.data.get(i).actions.get(0).tubeType.name, list.get(i)._1.getActionType.getTestTubeType.getName)
        Assert.assertEquals(result.data.get(i).actions.get(0).tubeType.volume, list.get(i)._1.getActionType.getTestTubeType.getVolume, 0.0)
        Assert.assertEquals(result.data.get(i).actions.get(0).tubeType.covCol, list.get(i)._1.getActionType.getTestTubeType.getCovCol)
        Assert.assertEquals(result.data.get(i).actions.get(0).tubeType.color, list.get(i)._1.getActionType.getTestTubeType.getColor)
        Assert.assertEquals(result.data.get(i).actions.get(0).patient.id, list.get(i)._1.getEvent.getPatient.getId)
        Assert.assertEquals(result.data.get(i).actions.get(0).patient.name.first, list.get(i)._1.getEvent.getPatient.getFirstName)
        Assert.assertEquals(result.data.get(i).actions.get(0).patient.name.last, list.get(i)._1.getEvent.getPatient.getLastName)
        Assert.assertEquals(result.data.get(i).actions.get(0).patient.name.middle, list.get(i)._1.getEvent.getPatient.getPatrName)
        Assert.assertEquals(result.data.get(i).actions.get(0).patient.birthDate, list.get(i)._1.getEvent.getPatient.getBirthDate)
      }
      logger.info("The method has been successfully completed. Result is: {}", mapper.writeValueAsString(result))
      logger.warn("Successful end of GetTakingOfBiomaterial test")
    }
    catch {
      case ex: CoreException => {
        logger.error("GetTakingOfBiomaterial test failed with error: {}", ex.getMessage + " " + ex.getStackTrace)
      }
    }
  }

  //______________________________________________________________________________________________________
  //****************************************   SetStatusesForJobTickets Tests   ********************************
  //______________________________________________________________________________________________________

  @Test
  def testSetStatusesForJobTickets = {   //TODO: ???

    logger.warn("Start of SetStatusesForJobTickets test:")

    val list = new util.LinkedList[JobTicket]
    list.add(testData.getTestDefaultJobTicket(1))
    list.add(testData.getTestDefaultJobTicket(2))
    list.add(testData.getTestDefaultJobTicket(3))
    val data = new JobTicketStatusDataList(list)

    val auth = null

    try{
      logger.info("Request data for method is: {}", mapper.writeValueAsString(data))
      when(directionBean.updateJobTicketsStatuses(data, auth)).thenReturn(true)
      val result = wsImpl.updateJobTicketsStatuses(data, auth)
      verify(directionBean).updateJobTicketsStatuses(data, auth)
      Assert.assertEquals(result, true)
      logger.info("The method has been successfully completed. Result is: {}", mapper.writeValueAsString(result))
      logger.warn("Successful end of SetStatusesForJobTickets test")
    }
    catch {
      case ex: CoreException => {
        logger.error("SetStatusesForJobTickets test failed with error: {}", ex.getMessage + " " + ex.getStackTrace)
      }
    }
  }
}
