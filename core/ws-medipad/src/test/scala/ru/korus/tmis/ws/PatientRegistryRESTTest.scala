package ru.korus.tmis.ws

import impl.WebMisRESTImpl
import org.junit.runner.RunWith
import org.mockito.runners.MockitoJUnitRunner
import org.mockito.InjectMocks
import org.junit.Before

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



  @Before
  def init() {
  }

}
