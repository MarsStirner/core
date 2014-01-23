package ru.korus.tmis.ws

import impl.WebMisRESTImpl
import org.junit.runner.RunWith
import org.mockito.runners.MockitoJUnitRunner
import org.mockito.{Mock, InjectMocks}
import org.junit.{Assert, Test, Before}
import org.mockito.Mockito._
import org.mockito.Matchers._
import ru.korus.tmis.core.exception.CoreException
import ru.korus.tmis.core.patient.PatientBeanLocal
import ru.korus.tmis.core.entity.model.kladr.{Street, Kladr}
import java.util
import org.slf4j.{LoggerFactory, Logger}
import ru.korus.tmis.test.data.{TestDataEntityImpl, TestDataEntity}
import ru.korus.tmis.core.data.BloodHistoryData
import ru.korus.tmis.core.database.common.DbPatientBeanLocal

/**
 * Author:      mmakankov <br>
 * Date:        26.08.13, 11:00  <br>
 * Company:     Systema-Soft<br>
 * Description: Тесты для REST-сервисов  <br>
 */

@RunWith(classOf[MockitoJUnitRunner])
class PatientRegistryRESTTest {

  @InjectMocks
  var wsImpl = new WebMisRESTImpl()
  @Mock var dbPatient: DbPatientBeanLocal = _
  @Mock var patientBean: PatientBeanLocal = _
  final val logger: Logger = LoggerFactory.getLogger(classOf[PatientRegistryRESTTest])
  final val testData: TestDataEntity = new TestDataEntityImpl()

  @Before
  def init() {
  }

  @Test
  def testGetPatientById() = {
    logger.info("Start of testGetPatientById test: ")

    val person = testData.getTestDefaultStaff
    val patient = testData.getTestDefaultPatient
    val authData = testData.getTestDefaultAuthData(person)

    val kladr1 = testData.getTestDefaultKladr
    val kladr2 = testData.getTestDefaultKladr()
    val list = new util.LinkedList[Kladr]()
    list.add(kladr1)
    list.add(kladr2)
    var map = new util.LinkedHashMap[java.lang.Integer, util.LinkedList[Kladr]]
    map.put(1, list)

    val street1 = testData.getTestDefaultStreet()
    var street = new util.LinkedHashMap[java.lang.Integer, Street]
    street.put(1, street1)

    //LinkedHashMap<Integer, LinkedList<Kladr>> getKLADRAddressMapForPatient(Patient patient) throws CoreException;
    //LinkedHashMap<Integer, Street> getKLADRStreetForPatient(Patient patient) throws CoreException;

    when(patientBean.getPatientById(anyInt())).thenReturn(patient)
    when(patientBean.getKLADRAddressMapForPatient(any())).thenReturn(map);
    when(patientBean.getKLADRStreetForPatient(any())).thenReturn(street)

    try {
      val result = wsImpl.getPatientById(1, authData)

      verify(patientBean).getPatientById(any())
      verify(patientBean).getKLADRAddressMapForPatient(any())
      verify(patientBean).getKLADRStreetForPatient(any())

      Assert.assertNotNull(result)
      Assert.assertEquals(patient.getId.intValue(), result.data.getId());
      Assert.assertEquals(patient.getFirstName, result.data.getName.getFirst());
      Assert.assertEquals(patient.getLastName, result.data.getName.getLast());
      Assert.assertEquals(patient.getPatrName, result.data.getName.getMiddle());
      Assert.assertEquals(patient.getBirthDate, result.data.getBirthDate);
      Assert.assertNotNull(result.getData.getAddress);
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
