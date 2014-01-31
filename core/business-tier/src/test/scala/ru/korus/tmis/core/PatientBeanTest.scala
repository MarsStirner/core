import java.util.Date
import javax.persistence.EntityManager
import org.junit.{Assert, Test, Before}
import org.junit.runner.RunWith
import org.mockito.Mockito._
import org.mockito._
import org.mockito.Matchers._
import org.mockito.{Mock, InjectMocks}
import org.mockito.runners.MockitoJUnitRunner
import org.slf4j.{LoggerFactory, Logger}
import ru.korus.tmis.core.data.{BloodHistoryData, BloodHistoryListData}
import ru.korus.tmis.core.database.common.{DbManagerBeanLocal, DbPatientBeanLocal}
import ru.korus.tmis.core.database.{AppLockBeanLocal, DbBloodHistoryBeanLocal}
import ru.korus.tmis.core.entity.model.{Staff, RbBloodType, Patient, BloodHistory}
import ru.korus.tmis.core.exception.CoreException
import ru.korus.tmis.core.patient.PatientBean
import ru.korus.tmis.test.data.{TestDataEntity, TestDataEntityImpl}

/**
 * Created with IntelliJ IDEA.
 * User: mmakankov
 * Date: 26.08.13
 * Time: 16:17
 * To change this template use File | Settings | File Templates.
 */

@RunWith(classOf[MockitoJUnitRunner])
class PatientBeanTest {

  @InjectMocks
  var patientBean = new PatientBean()

  @Mock var em: EntityManager = _
  @Mock var appLock: AppLockBeanLocal = _
  @Mock var dbManager: DbManagerBeanLocal = _
  @Mock var dbBloodHistoryBean: DbBloodHistoryBeanLocal = _
  @Mock var dbPatient: DbPatientBeanLocal = _

  final val logger: Logger = LoggerFactory.getLogger(classOf[PatientBeanTest])
  final val testData: TestDataEntity = new TestDataEntityImpl()

  @Before
  def init() {
  }

  //def getBloodHistory(id: Int) = new BloodHistoryListData(dbBloodHistoryBean.getBloodHistoryByPatient(id))

  @Test
  def testGetBloodHistory() = {
    logger.info("Start of testGetBloodHistory test: ")

    val bt = testData.getTestDefaultRbBloodType(1)
    val person = testData.getTestDefaultStaff
    val list = new java.util.LinkedList[BloodHistory];
    val testBH1 = testData.getTestDefaultBloodHistory(1, bt, person)
    val testBH2 = testData.getTestDefaultBloodHistory(2, bt, person)
    list.add(testBH1);
    list.add(testBH2);

    val history = new BloodHistoryListData(list);
    //val requestData = new ListDataRequest("id", "asc", 10, 1, filter);

    when(dbBloodHistoryBean.getBloodHistoryByPatient(anyInt())).thenReturn(list);
    try{
      val result = patientBean.getBloodHistory(57);

      verify(dbBloodHistoryBean).getBloodHistoryByPatient(anyInt());
      Assert.assertNotNull(result)
      Assert.assertEquals(2, result.data.size());
      Assert.assertEquals(list.get(0).getId.intValue(), result.data.get(0).getId());
      Assert.assertEquals(list.get(1).getId.intValue(), result.data.get(1).getId());
      validateMockitoUsage();
      logger.info("The method has been successfully completed. Result is: {}", result.toString)
    }
    catch {
      case ex: CoreException => {
        logger.error("verificationData test completed with named error: {}", ex.getMessage + " " + ex.getStackTrace)
        Assert.fail()
      }
    }
  }

  @Test
  def testInsertBloodTypeForPatient() = {
    logger.info("Start of testInsertBloodTypeForPatient test: ")

    val bt = testData.getTestDefaultRbBloodType(1)
    val person = testData.getTestDefaultStaff
    val bloodHistory = testData.getTestDefaultBloodHistory(1, bt, person)
    var data = new BloodHistoryData(bloodHistory)
    val patient = testData.getTestDefaultPatient
    val authData = testData.getTestDefaultAuthData(person)

    doNothing().when(dbManager).persist(any())
    doReturn(null).when(dbManager).merge(any())
    doReturn(true).when(appLock).releaseLock(any())
    when(dbBloodHistoryBean.createBloodHistoryRecord(anyInt(), anyInt(), any(), any())).thenReturn(bloodHistory);
    when(dbPatient.getPatientById(anyInt())).thenReturn(patient)
    //when(Patient.clone(patient)).thenReturn(testData.getTestDefaultPatient)
    when(appLock.acquireLock(anyString(), anyInt(), anyInt(), any())).thenReturn(patient.getId.intValue())

    try {
      val result = patientBean.insertBloodTypeForPatient(57, data, authData)

      verify(dbBloodHistoryBean).createBloodHistoryRecord(anyInt(), anyInt(), any(), any())
      verify(dbPatient).getPatientById(anyInt())
      verify(appLock).acquireLock(anyString(), anyInt(), anyInt(), any())

      Assert.assertNotNull(result)
      Assert.assertEquals(bloodHistory.getId.intValue(), result.data.getId());
      Assert.assertEquals(bloodHistory.getBloodType.getId.intValue(), result.data.getBloodType().getId());
      validateMockitoUsage();
      logger.info("The method has been successfully completed. Result is: {}", result.toString)
    }
    catch {
      case ex: CoreException => {
        logger.error("verificationData test completed with named error: {}", ex.getMessage + " " + ex.getStackTrace)
        Assert.fail()
      }
    }
  }


}
