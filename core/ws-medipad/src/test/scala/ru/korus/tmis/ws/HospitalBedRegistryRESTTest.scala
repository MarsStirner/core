package ru.korus.tmis.ws

import impl.WebMisRESTImpl
import org.junit.runner.RunWith
import org.mockito.runners.MockitoJUnitRunner
import org.mockito.{Mock, InjectMocks}
import ru.korus.tmis.core.patient.HospitalBedBeanLocal
import org.slf4j.{LoggerFactory, Logger}
import org.codehaus.jackson.map.ObjectMapper
import org.junit.{Assert, Test, Before}
import java.util
import ru.korus.tmis.core.entity.model.{Action, OrgStructureHospitalBed}
import org.mockito.Mockito._
import ru.korus.tmis.core.exception.CoreException
import ru.korus.tmis.core.data.{HospitalBedData, HospitalBedDataRequest, HospitalBedDataListFilter}
import ru.korus.tmis.test.data.{TestDataEntityImpl, TestDataEntity}


/**
 * Author:      Ivan Dmitriev <br>
 * Date:        06.08.13, 12:27  <br>
 * Company:     Systema-Soft<br>
 * Description: Тесты для HospitalBedsRegistryREST сервисов.<br>
 */
@RunWith(classOf[MockitoJUnitRunner])
class HospitalBedRegistryRESTTest {

  @InjectMocks
  var wsImpl = new WebMisRESTImpl()

  @Mock
  var hospitalBedBean: HospitalBedBeanLocal = _

  final val logger: Logger = LoggerFactory.getLogger(classOf[CustomInfoRESTTest])

  val mapper: ObjectMapper = new ObjectMapper()

  final val testData: TestDataEntity = new TestDataEntityImpl()

  @Before
  def init() {
  }

  /*
  @Test
  def testGetMovingListForEvent = {

    logger.warn("Start of GetMovingListForEvent test:")
    val filter = new HospitalBedDataListFilter(1);
    val request= new HospitalBedDataRequest("id", "asc", 10, 1, filter);
    val auth = null

    val list = new util.LinkedList[Action]
    list.add(testData.getTestDefaultAction(1))
    list.add(testData.getTestDefaultAction(2))
    list.add(testData.getTestDefaultAction(3))
    val data = new HospitalBedData(list, null, null)

    try{
      logger.info("Request data for method is: {}", mapper.writeValueAsString(request))

      when(hospitalBedBean.getMovingListByEventIdAndFilter(filter, auth)).thenReturn(data)
      val result = wsImpl.getMovingListForEvent(filter, auth)
      verify(hospitalBedBean).getMovingListByEventIdAndFilter(filter, auth)

      Assert.assertNotNull(result)

      logger.info("The method has been successfully completed. Result is: {}", mapper.writeValueAsString(result))
      logger.warn("Successful end of GetMovingListForEvent test")
    }
    catch {
      case ex: CoreException => {
        logger.error("GetMovingListForEvent test failed with error: {}", ex.getMessage + " " + ex.getStackTrace)
      }
    }
  }*/

}
