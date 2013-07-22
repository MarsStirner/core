package ru.korus.tmis.ws.webmis.rest.test

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito._
import org.mockito.Matchers._
import org.mockito.runners.MockitoJUnitRunner
import ru.korus.tmis.ws.impl.WebMisRESTImpl
import ru.korus.tmis.core.database.{DbRbRequestTypeBeanLocal, DbCustomQueryLocal, DbStaffBeanLocal, DbRbBloodTypeBeanLocal}
import ru.korus.tmis.core.exception.CoreException
import ru.korus.tmis.core.data._
import org.mockito.Mockito._
import ru.korus.tmis.core.entity.model.{Mkb, APValueTime, Staff}
import runtime.AbstractFunction1
import java.util
import util.Date
import org.codehaus.jackson.map.ObjectMapper

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

  @Mock var dbBloodTypeBean: DbRbBloodTypeBeanLocal = _

  @Mock var dbRbRequestTypes: DbRbRequestTypeBeanLocal = _

  @Mock var dbCustomQueryBean: DbCustomQueryLocal = _

  @Mock
  var dbStaff: DbStaffBeanLocal = _

  @Before
  def init() {
  }

  @Test
  def testGetDictionaryBloodTypes =  {

    val mapper: ObjectMapper = new ObjectMapper()
    //Справочник групп крови
    val filter = new DictionaryListRequestDataFilter("bloodTypes", -1, -1, "", "", "", "", -1)
    val request = new ListDataRequest("id", "asc", 0, 1, filter)

    val list = new java.util.LinkedList[Object]
    list.add((1, "0(I)Rh-"))
    list.add((2, "0(I)Rh+"))
    list.add((3, "A(II)Rh-"))
    list.add((4, "A(II)Rh+"))

    //TODO: Что делать с маппером?
    //mapper.getSerializationConfig().setSerializationView(classOf[DictionaryDataViews.DefaultView])
    when(dbBloodTypeBean.getCountOfBloodTypesWithFilter(request.filter)).thenReturn(4L)
    when(dbBloodTypeBean.getAllBloodTypesWithFilter(request.page - 1,
                                                    request.limit,
                                                    request.sortingFieldInternal,
                                                    request.filter.unwrap)).thenReturn(list)

    val result = wsImpl.getDictionary(request, "bloodTypes")

    verify(dbBloodTypeBean).getCountOfBloodTypesWithFilter(request.filter)
    verify(dbBloodTypeBean).getAllBloodTypesWithFilter(request.page - 1, request.limit, request.sortingFieldInternal, request.filter.unwrap)

    Assert.assertNotNull(result)
    val json = mapper.readValue(result, classOf[DictionaryListData])
    //проверка требуемого количества
    Assert.assertEquals(4L, json.requestData.recordsCount)
    Assert.assertEquals(4, json.data.size())
    //проверка корректности данных
    Assert.assertEquals(list.get(0).asInstanceOf[(java.lang.Integer, String)]._1.intValue(), json.data.get(0).id)
    Assert.assertEquals(list.get(0).asInstanceOf[(java.lang.Integer, String)]._2, json.data.get(0).value)
    Assert.assertEquals(list.get(1).asInstanceOf[(java.lang.Integer, String)]._1.intValue(), json.data.get(1).id)
    Assert.assertEquals(list.get(1).asInstanceOf[(java.lang.Integer, String)]._2, json.data.get(1).value)
    Assert.assertEquals(list.get(2).asInstanceOf[(java.lang.Integer, String)]._1.intValue(), json.data.get(2).id)
    Assert.assertEquals(list.get(2).asInstanceOf[(java.lang.Integer, String)]._2, json.data.get(2).value)
    Assert.assertEquals(list.get(3).asInstanceOf[(java.lang.Integer, String)]._1.intValue(), json.data.get(3).id)
    Assert.assertEquals(list.get(3).asInstanceOf[(java.lang.Integer, String)]._2, json.data.get(3).value)
    validateMockitoUsage()
  }

  @Test
  def testGetDictionaryRequestTypes = {

    val mapper: ObjectMapper = new ObjectMapper()
    val filter = new DictionaryListRequestDataFilter("requestTypes", -1, -1, "", "", "", "", -1)
    val request = new ListDataRequest("id", "asc", 0, 1, filter)

    val list = new java.util.LinkedList[Object]
    list.add((1, "Дневной стационар", "clinic"))
    list.add((2, "Круглосуточный стационар", "hospital"))
    list.add((3, "Поликлиника", "policlinic"))

    //mapper.getSerializationConfig().setSerializationView(classOf[DictionaryDataViews.DefaultView])
    when(dbRbRequestTypes.getAllRbRequestTypesWithFilter(anyInt(), anyInt(), anyString(), anyObject(), anyObject())).thenReturn(list)

    val result = wsImpl.getDictionary(request, "requestTypes")

    verify(dbRbRequestTypes).getAllRbRequestTypesWithFilter(anyInt(), anyInt(), anyString(), anyObject(), anyObject())

    Assert.assertNotNull(result)
    val json = mapper.readValue(result, classOf[DictionaryListData])
    //проверка требуемого количества
    Assert.assertEquals(3, json.data.size())
    //проверка корректности данных
    Assert.assertEquals(list.get(0).asInstanceOf[(java.lang.Integer, String, String)]._1.intValue(), json.data.get(0).id)
    Assert.assertEquals(list.get(0).asInstanceOf[(java.lang.Integer, String, String)]._3, json.data.get(0).code)
    Assert.assertEquals(list.get(0).asInstanceOf[(java.lang.Integer, String, String)]._2, json.data.get(0).value)
    Assert.assertEquals(list.get(1).asInstanceOf[(java.lang.Integer, String, String)]._1.intValue(), json.data.get(1).id)
    Assert.assertEquals(list.get(1).asInstanceOf[(java.lang.Integer, String, String)]._3, json.data.get(1).code)
    Assert.assertEquals(list.get(1).asInstanceOf[(java.lang.Integer, String, String)]._2, json.data.get(1).value)
    Assert.assertEquals(list.get(2).asInstanceOf[(java.lang.Integer, String, String)]._1.intValue(), json.data.get(2).id)
    Assert.assertEquals(list.get(2).asInstanceOf[(java.lang.Integer, String, String)]._3, json.data.get(2).code)
    Assert.assertEquals(list.get(2).asInstanceOf[(java.lang.Integer, String, String)]._2, json.data.get(2).value)

    validateMockitoUsage()
  }

  //______________________________________________________________________________________________________
  //****************************************   GetAllMkbs Tests   ****************************************
  //______________________________________________________________________________________________________

  //Тестирование списка МКБ в виде дерева без представления свернутых веток (flgDisplay=false)
  //Фильтрация данных на уровне hql запроса к БД (mock)
  @Test
  def testGetAllMkbsTreeWithoutDisplay = {

    val mapper: ObjectMapper = new ObjectMapper()
    val filter = new MKBListRequestDataFilter(-1,  null, null, null, null, "all", false, 0)
    val request = new ListDataRequest("id", "asc", 0, 1, filter)

    val mkbs = this.getAllMkbsMock()

    when(dbCustomQueryBean.getCountOfMkbsWithFilter(request.filter)).thenReturn(mkbs.size())
    when(dbCustomQueryBean.getAllMkbsWithFilter(request.page,
                                                request.limit,
                                                request.sortingFieldInternal,
                                                request.filter.unwrap)).thenReturn(mkbs)
    when(dbCustomQueryBean.getDistinctMkbsWithFilter(request.sortingFieldInternal,
                                                     request.filter.unwrap)).thenReturn(null)

    val result = wsImpl.getAllMkbs(request, null)

    verify(dbCustomQueryBean).getCountOfMkbsWithFilter(request.filter)
    verify(dbCustomQueryBean).getAllMkbsWithFilter(request.page, request.limit, request.sortingFieldInternal, request.filter.unwrap)
    verify(dbCustomQueryBean).getDistinctMkbsWithFilter(request.sortingFieldInternal, request.filter.unwrap)

    Assert.assertNotNull(result)
    val json = mapper.readValue(result, classOf[AllMKBListData])
    //проверка требуемого количества и древовидной структуры
    //дерево две корневые ветки (класса)
    Assert.assertEquals(2, json.data.size())
    //дерево две корневые ветки (группы внутри первого класса)
    /*Assert.assertEquals(2, json.data.get(0).asInstanceOf[ClassMKBContainer]
                               .groups.size())
    //дерево две корневые ветки (две подгруппы внутри первой группы внутри первого класса)
    Assert.assertEquals(2, json.data.get(0).asInstanceOf[ClassMKBContainer]
                               .groups.get(0)
                               .subGroups.size()) */
    //TODO: проверка корректности данных

    //Assert.assertEquals(list.get(0).asInstanceOf[(java.lang.Integer, String, String)]._1.intValue(), json.data.get(0).id)


    validateMockitoUsage()

  }

  //Тестирование списка МКБ в виде дерева без представления свернутых веток (flgDisplay=false)
  //Фильтрация данных на уровне hql запроса к БД (mock)
  @Test
  def testGetAllMkbsFlatStructWithoutDisplay = {

    val mapper: ObjectMapper = new ObjectMapper()
    val filter = new MKBListRequestDataFilter(-1,  null, null, null, null, "mkb", false, 0)
    val request = new ListDataRequest("id", "asc", 0, 1, filter)

    val mkbs = this.getAllMkbsMock()

    when(dbCustomQueryBean.getCountOfMkbsWithFilter(request.filter)).thenReturn(mkbs.size())
    when(dbCustomQueryBean.getAllMkbsWithFilter(request.page,
                                                request.limit,
                                                request.sortingFieldInternal,
                                                request.filter.unwrap)).thenReturn(mkbs)
    when(dbCustomQueryBean.getDistinctMkbsWithFilter(request.sortingFieldInternal,
      request.filter.unwrap)).thenReturn(null)

    val result = wsImpl.getAllMkbs(request, null)

    verify(dbCustomQueryBean).getCountOfMkbsWithFilter(request.filter)
    verify(dbCustomQueryBean).getAllMkbsWithFilter(request.page, request.limit, request.sortingFieldInternal, request.filter.unwrap)
    verify(dbCustomQueryBean).getDistinctMkbsWithFilter(request.sortingFieldInternal, request.filter.unwrap)

    Assert.assertNotNull(result)
    val json = mapper.readValue(result, classOf[AllMKBListData])

    //проверка требуемого количества в плоской структуре
    Assert.assertEquals(mkbs.size(), json.data.size())
    //TODO: <= проверка корректности данных
    validateMockitoUsage()

  }

  private def getAllMkbsMock () = {

    val mkbs = new java.util.LinkedList[Mkb]
    Set(new Mkb(1, "I", "Некоторые инфекционные и паразитарные болезни", "(A00-A09)", "Кишечные инфекции", "A00", "Холера", "", 0, "", 3, 0),
      new Mkb(2, "I", "Некоторые инфекционные и паразитарные болезни", "(A00-A09)", "Кишечные инфекции", "A00.0", "Холера, вызванная вибрионом 01, биовар cholerae", "", 0, "", 1, 0),
      new Mkb(3, "I", "Некоторые инфекционные и паразитарные болезни", "(A00-A09)", "Кишечные инфекции", "A00.1", "Холера, вызванная вибрионом 01, биовар eltor", "", 0, "", 1, 0),
      new Mkb(5, "I", "Некоторые инфекционные и паразитарные болезни", "(A00-A09)", "Кишечные инфекции", "A01", "Тиф и паратиф", "", 0, "", 6, 0),
      new Mkb(6, "I", "Некоторые инфекционные и паразитарные болезни", "(A00-A09)", "Кишечные инфекции", "A01.0", "Брюшной тиф", "", 0, "", 6, 0),
      new Mkb(910, "II", "Новообразования", "(C00-C14)", "Злокачественные новообразования губы, полости рта и глотки", "C00", "Злокачественное новообразование губы", "", 0, "", 5, 0),
      new Mkb(910, "II", "Новообразования", "(C00-C14)", "Злокачественные новообразования губы, полости рта и глотки", "C00.0", "ЗНО наружной поверхности губы", "", 0, "", 5, 0))
      .foreach(f => mkbs.add(f))
    mkbs
  }

  @Test
  def testGetAllPersons = {
    //Справочник персонала
    val filter = new PersonsListDataFilter(18);
    val requestData = new ListDataRequest("id", "asc", 10, 1, filter);

    val list = new java.util.LinkedList[Staff];
    list.add(new Staff(204));
    list.add(new Staff(205));

    when(dbStaff.getAllPersonsByRequest(anyInt(),
                                        anyInt(),
                                        anyString(),
                                        anyObject(),
                                        anyObject())).thenReturn(list);

    val result = wsImpl.getAllPersons(requestData);

    verify(dbStaff).getAllPersonsByRequest( anyInt(),
                                            anyInt(),
                                            anyString(),
                                            anyObject(),
                                            anyObject());

    Assert.assertNotNull(result)
    Assert.assertEquals(2, result.data.size());
    //проверка сохранности сортировки
    Assert.assertEquals(list.get(0).getId().intValue(), result.data.get(0).getId);
    Assert.assertEquals(list.get(1).getId().intValue(), result.data.get(1).getId);
    validateMockitoUsage(); //Диагностика неисправности мокито
  }

  @Test
  def testGetAllPersonsCaseNullResponse = {      //public void testGetAllDepartmentsCaseNullResponse() throws CoreException {
    //Справочник персонала
    val filter = new PersonsListDataFilter(18);
    val requestData = new ListDataRequest("id", "asc", 10, 1, filter);
    val list = new java.util.LinkedList[Staff]

    when(dbStaff.getAllPersonsByRequest(anyInt(),
      anyInt(),
      anyString(),
      anyObject(),
      anyObject())).thenReturn(list);

    var result = wsImpl.getAllPersons(requestData);

    verify(dbStaff).getAllPersonsByRequest( anyInt(),
      anyInt(),
      anyString(),
      anyObject(),
      anyObject());

    Assert.assertNotNull(result)
    Assert.assertNotNull(result.data)
    Assert.assertEquals(0, result.data.size());
    validateMockitoUsage();
  }

  @Test
  def testGetFreePersons = {

    //Справочник персонала
    val filter = new FreePersonsListDataFilter(0, 0, 1465, 1371672000000L, 0);
    val requestData = new ListDataRequest("id", "asc", 10, 1, filter);
    val apvTimeList = new util.LinkedList[APValueTime]()
    var apv1 = new APValueTime(904439, 0)
    apv1.setValue(new Date(32400000))
    var apv2 = new APValueTime(904439, 1)
    apv2.setValue(new Date(36720000))
    apvTimeList.add(apv1)
    apvTimeList.add(apv2)
    val map = new java.util.HashMap[Staff, java.util.LinkedList[APValueTime]];
    map.put(new Staff(956), apvTimeList);

    when(dbStaff.getEmptyPersonsByRequest(requestData.limit,
                                          requestData.page-1,
                                          requestData.sortingFieldInternal,
                                          requestData.filter.unwrap())).thenReturn(map)

    val result = wsImpl.getFreePersons(requestData)

    verify(dbStaff).getEmptyPersonsByRequest( requestData.limit,
                                              requestData.page-1,
                                              requestData.sortingFieldInternal,
                                              requestData.filter.unwrap())

    Assert.assertNotNull(result)
    Assert.assertEquals(1, result.data.size());
    Assert.assertNotNull(result.data.get(0).doctor)
    Assert.assertNotNull(map.get(new Staff(result.data.get(0).doctor.getId)));
    Assert.assertEquals(map.get(new Staff(result.data.get(0).doctor.getId)).size(), result.data.get(0).schedule.size());
    Assert.assertEquals(map.get(new Staff(result.data.get(0).doctor.getId)).get(0).getId.getId, result.data.get(0).schedule.get(0).getId());
    Assert.assertEquals(map.get(new Staff(result.data.get(0).doctor.getId)).get(0).getId.getIndex, result.data.get(0).schedule.get(0).getIndex());
    validateMockitoUsage(); //Диагностика неисправности мокито
  }

  @Test
  def testGetFreePersonsCaseNullResponse = {
    val filter = new FreePersonsListDataFilter(0, 0, 1465, 1371672000000L, 0);
    val requestData = new ListDataRequest("id", "asc", 10, 1, filter);

    val map = new java.util.HashMap[Staff, java.util.LinkedList[APValueTime]];

    when(dbStaff.getEmptyPersonsByRequest(requestData.limit,
      requestData.page-1,
      requestData.sortingFieldInternal,
      requestData.filter.unwrap())).thenReturn(map)

    val result = wsImpl.getFreePersons(requestData)

    verify(dbStaff).getEmptyPersonsByRequest( requestData.limit,
      requestData.page-1,
      requestData.sortingFieldInternal,
      requestData.filter.unwrap())

    Assert.assertNotNull(result)
    Assert.assertNotNull(result.data)
    Assert.assertEquals(0, result.data.size());
    validateMockitoUsage();
  }
}
