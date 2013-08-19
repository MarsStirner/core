package ru.korus.tmis.ws

import impl.WebMisRESTImpl
import org.junit.runner.RunWith
import org.mockito.runners.MockitoJUnitRunner
import org.mockito.{Mock, InjectMocks}
import org.slf4j.{LoggerFactory, Logger}
import org.codehaus.jackson.map.ObjectMapper
import org.junit.{Assert, Test, Before}
import java.util
import ru.korus.tmis.core.entity.model.{OrgStructureHospitalBed, JobTicket}
import ru.korus.tmis.core.data.JobTicketStatusDataList
import org.mockito.Mockito._
import ru.korus.tmis.core.exception.CoreException
import ru.korus.tmis.core.patient.HospitalBedBeanLocal
import scala.collection.JavaConversions._
import ru.korus.tmis.test.data.{TestDataEntity, TestDataEntityImpl}

/**
 * Author:      Ivan Dmitriev <br>
 * Date:        06.08.13, 8:46  <br>
 * Company:     Systema-Soft<br>
 * Description: Тесты для HospitalBedsInfoREST сервисов.<br>
 */
@RunWith(classOf[MockitoJUnitRunner])
class HospitalBedsInfoRESTTest {

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

  @Test
  def testGetVacantHospitalBeds = {

    logger.warn("Start of GetVacantHospitalBeds test:")
    val department = 18
    val auth = null
    val map = new util.LinkedHashMap[OrgStructureHospitalBed, java.lang.Boolean]
    map.put(testData.getTestDefaultOrgStructureHospitalBed(1), true)
    map.put(testData.getTestDefaultOrgStructureHospitalBed(2), false)
    map.put(testData.getTestDefaultOrgStructureHospitalBed(3), true)

    try{
      logger.info("Request data for method is: {}", "filter[department]=%d".format(department))

      when(hospitalBedBean.getCaseHospitalBedsByDepartmentId(department)).thenReturn(map)
      val result = wsImpl.getVacantHospitalBeds(department, auth)
      verify(hospitalBedBean).getCaseHospitalBedsByDepartmentId(department)

      Assert.assertNotNull(result)
      Assert.assertEquals(result.data.size(), map.size())
      val iterator = map.iterator
      for(i <- 0 until map.size()){
        val current = iterator.next()
        Assert.assertEquals(result.data.get(i).bedId, current._1.getId)
        Assert.assertEquals(result.data.get(i).name, current._1.getName)
        Assert.assertEquals(result.data.get(i).code, current._1.getCode)
        val busy = if(current._2.booleanValue()) "yes" else "no"
        Assert.assertEquals(result.data.get(i).busy, busy)
      }

      logger.info("The method has been successfully completed. Result is: {}", mapper.writeValueAsString(result))
      logger.warn("Successful end of GetVacantHospitalBeds test")
    }
    catch {
      case ex: CoreException => {
        logger.error("GetVacantHospitalBeds test failed with error: {}", ex.getMessage + " " + ex.getStackTrace)
      }
    }
  }
}
