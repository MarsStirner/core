package ru.korus.tmis.ws.webmis.rest.test

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito._
import org.mockito.runners.MockitoJUnitRunner
import ru.korus.tmis.ws.impl.WebMisRESTImpl
import ru.korus.tmis.core.database.DbRbBloodTypeBeanLocal
import ru.korus.tmis.core.exception.CoreException
import ru.korus.tmis.core.data.{AllDepartmentsListData, ListDataRequest, DictionaryListRequestDataFilter}
import org.mockito.Mockito._

/**
 * Created with IntelliJ IDEA.
 * User: idmitriev
 * Date: 7/16/13
 * Time: 2:42 PM
 * To change this template use File | Settings | File Templates.
 */
/**
 * Author:      Ivan Dmitriev <br>
 * Date:        16.07.13, 2:42  <br>
 * Company:     Systema-Soft<br>
 * Description: Тесты для REST-сервисов  <br>
 */
@RunWith(classOf[MockitoJUnitRunner])
class DirectoryInfoRESTTest {

  @InjectMocks
  var wsImpl = new WebMisRESTImpl()

  @Mock
  var dbBloodTypeBean: DbRbBloodTypeBeanLocal = _

  @Before
  def init() {
  }

  @Test
  def testGetDictionary =  {

    //Справочник групп крови
    val filter = new DictionaryListRequestDataFilter("bloodTypes", -1, -1, "", "", "", "", -1)
    val request = new ListDataRequest("id", "asc", 0, 1, filter)

    val list = new java.util.LinkedList[Object]
    list.add((1, "0(I)Rh-"))
    list.add((2, "0(I)Rh+"))
    list.add((3, "A(II)Rh-"))
    list.add((4, "A(II)Rh+"))

    when(dbBloodTypeBean.getCountOfBloodTypesWithFilter(request.filter)).thenReturn(4L)
    when(dbBloodTypeBean.getAllBloodTypesWithFilter(request.page - 1,
                                                    request.limit,
                                                    request.sortingFieldInternal,
                                                    request.filter.unwrap)).thenReturn(list)

    val result = wsImpl.getDictionary(request, "bloodTypes")

    verify(dbBloodTypeBean).getCountOfBloodTypesWithFilter(request.filter)
    verify(dbBloodTypeBean).getAllBloodTypesWithFilter(request.page - 1, request.limit, request.sortingFieldInternal, request.filter.unwrap)

    Assert.assertNotNull(result)
    //проверка требуемого количества
    /*Assert.assertEquals(4L, result.request.recordsCount());
    Assert.assertEquals(3, result.data().size());
    //проверка сохранности сортировки
    Assert.assertEquals(list.get(0).getId().intValue(), result.data().get(0).id());
    Assert.assertEquals(list.get(1).getId().intValue(), result.data().get(1).id());
    Assert.assertEquals(list.get(2).getId().intValue(), result.data().get(2).id());
    validateMockitoUsage();*/
  }

}
