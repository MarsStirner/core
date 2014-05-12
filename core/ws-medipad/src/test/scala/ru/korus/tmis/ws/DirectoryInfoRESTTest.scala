package ru.korus.tmis.ws

//package ru.korus.tmis.ws.medipad

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito._
import org.mockito.Matchers._
import org.mockito.runners.MockitoJUnitRunner
import ru.korus.tmis.core.data._
import org.mockito.Mockito._
import ru.korus.tmis.core.entity.model._
import java.util
import util.Date
import org.codehaus.jackson.map.ObjectMapper
import scala.collection.JavaConversions._
import ru.korus.tmis.core.entity.model.fd.{FlatDirectory, FDRecord, FDFieldValue}
import ru.korus.tmis.core.database._
import common.{DbCustomQueryLocal, DbRbBloodTypeBeanLocal}
import ru.korus.tmis.ws.impl.WebMisRESTImpl
import org.slf4j.{Logger, LoggerFactory}
import ru.korus.tmis.core.exception.CoreException
import ru.korus.tmis.core.patient.{PatientBeanLocal, PrimaryAssessmentBeanLocal}
import ru.korus.tmis.core.filter.AbstractListDataFilter
import ru.korus.tmis.scala.util.StringId

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

  @Mock var dbStaff: DbStaffBeanLocal = _

  @Mock var flatDirectoryBean: DbFlatDirectoryBeanLocal = _

  @Mock var dbQuotaTypeBean: DbQuotaTypeBeanLocal = _

  @Mock var dbRlsBean: DbRlsBeanLocal = _

  @Mock var dbEventTypeBean: DbEventTypeBeanLocal = _

  @Mock var actionTypeBean: DbActionTypeBeanLocal = _

  @Mock var primaryAssessmentBean: PrimaryAssessmentBeanLocal = _

  @Mock var patientBean: PatientBeanLocal = _

  final val logger: Logger = LoggerFactory.getLogger(classOf[DirectoryInfoRESTTest])

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

  //______________________________________________________________________________________________________
  //****************************************   GetAllPersons Tests   ****************************************
  //______________________________________________________________________________________________________

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

  //______________________________________________________________________________________________________
  //****************************************   GetFreePersons Tests   ****************************************
  //______________________________________________________________________________________________________

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
      requestData.filter.unwrap(),
      0)).thenReturn(map)

    val result = wsImpl.getFreePersons(requestData, 0)

    verify(dbStaff).getEmptyPersonsByRequest( requestData.limit,
      requestData.page-1,
      requestData.sortingFieldInternal,
      requestData.filter.unwrap(),
      0)

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
      requestData.filter.unwrap(),
      0)).thenReturn(map)

    val result = wsImpl.getFreePersons(requestData, 0)

    verify(dbStaff).getEmptyPersonsByRequest( requestData.limit,
      requestData.page-1,
      requestData.sortingFieldInternal,
      requestData.filter.unwrap(),
      0)

    Assert.assertNotNull(result)
    Assert.assertNotNull(result.data)
    Assert.assertEquals(0, result.data.size());
    validateMockitoUsage();
  }

  //______________________________________________________________________________________________________
  //****************************************   GetFlatDirectories Tests   ********************************
  //______________________________________________________________________________________________________

  @Test
  def testGetFlatDirectories = {

    logger.warn("Start of GetFlatDirectories test:")

    val mapper: ObjectMapper = new ObjectMapper()

    //Тестовые параметры запроса
    //Выбрать справочники с id = {1, 2}
    val flatDictionaryIds = new util.LinkedList[java.lang.Integer]()
    flatDictionaryIds.add(1)
    flatDictionaryIds.add(2)
    //Ситуация №1
    //выбрать записи из FDRecord c id = {1, 2, 9, 10}
    val filterRecordIds = new util.LinkedList[java.lang.Integer]()
    filterRecordIds.add(1)
    filterRecordIds.add(2)
    filterRecordIds.add(9)
    filterRecordIds.add(10)
    //сортировка по двум полям FDField
    val sortingFieldIds = new util.LinkedHashMap[java.lang.Integer,java.lang.Integer]()
    sortingFieldIds.put(1, 0) //fdfield_id = 1 asc
    sortingFieldIds.put(3, 1) //fdfield_id = 3 desc

    val filter = new FlatDirectoryRequestDataListFilter(flatDictionaryIds, "yes", "yes", "yes", null, null, filterRecordIds)
    val request= new FlatDirectoryRequestData(sortingFieldIds, 10, 1, filter)

    logger.info("Request data for method is: {}", mapper.writeValueAsString(request))

    val sorting =
      request.sortingFields.foldLeft(new java.util.LinkedHashMap[java.lang.Integer, java.lang.Integer])(
        (a, b) => { a.put(Integer.valueOf(b._1), Integer.valueOf(b._2))
          a
        })

    //response
    val flatRecords = new java.util.LinkedHashMap[FlatDirectory, java.util.LinkedHashMap[FDRecord, java.util.LinkedList[FDFieldValue]]]

    val fDRecords1 = new java.util.LinkedHashMap[FDRecord, java.util.LinkedList[FDFieldValue]]
    fDRecords1.put(new FDRecord(1), new java.util.LinkedList[FDFieldValue](List(new FDFieldValue(1),new FDFieldValue(2))))
    fDRecords1.put(new FDRecord(2), new java.util.LinkedList[FDFieldValue](List(new FDFieldValue(3),new FDFieldValue(4))))
    val fDRecords2 = new java.util.LinkedHashMap[FDRecord, java.util.LinkedList[FDFieldValue]]
    fDRecords2.put(new FDRecord(9), new java.util.LinkedList[FDFieldValue](List(new FDFieldValue(17),new FDFieldValue(18),new FDFieldValue(19))))
    fDRecords2.put(new FDRecord(10), new java.util.LinkedList[FDFieldValue](List(new FDFieldValue(20),new FDFieldValue(21),new FDFieldValue(22))))

    flatRecords.put(new FlatDirectory(1), fDRecords1)
    flatRecords.put(new FlatDirectory(2), fDRecords2)

    try {
      when(flatDirectoryBean.getFlatDirectoriesWithFilterRecords(request.page,
                                                                request.limit,
                                                                sorting,
                                                                request.filter,
                                                                request,
                                                                null)).thenReturn(flatRecords)
      val result = wsImpl.getFlatDirectories(request)

      verify(flatDirectoryBean).getFlatDirectoriesWithFilterRecords(request.page,
                                                                    request.limit,
                                                                    sorting,
                                                                    request.filter,
                                                                    request,
                                                                    null)
      Assert.assertNotNull(result)
      //Проверка на количества
      Assert.assertEquals(result.data.size(), flatRecords.size())
      Assert.assertEquals(result.data.get(0).getRecordList.size() + result.data.get(1).getRecordList.size(),
        fDRecords1.size() + fDRecords2.size())
      var count = 0
      fDRecords1.foreach(f => count += f._2.size())
      fDRecords2.foreach(f => count += f._2.size())
      Assert.assertEquals(result.data.get(0).getRecordList.get(0).getFieldValueList.size() +
        result.data.get(0).getRecordList.get(1).getFieldValueList.size() +
        result.data.get(1).getRecordList.get(0).getFieldValueList.size() +
        result.data.get(1).getRecordList.get(1).getFieldValueList.size(),
        count)
      //Assert.assertEquals(result.requestData.recordsCount, fDRecords1.size() + fDRecords2.size())
      //TODO: <= проверка коректности записанных данных
      validateMockitoUsage()
      logger.info("The method has been successfully completed. Result is: {}", mapper.writeValueAsString(result))
      logger.warn("Successful end of GetFlatDirectories test")
    } catch {
      case ex: CoreException => {
        logger.error("GetFlatDirectories test failed with error: {}", ex.getMessage + " " +ex.getStackTrace)
      }
    }
  }

  //______________________________________________________________________________________________________
  //****************************************   GetThesaurus Tests   ********************************
  //______________________________________________________________________________________________________

  //Тест: непустая выборка из БД
  @Test
  def testGetThesaurus = {

    //test data
    val list = new util.LinkedList[Thesaurus]()
    list.add(new Thesaurus(1, null, "1", "Осмотр", "Осмотр"))
    list.add(new Thesaurus(2, 1, "1_01", "Анемнез", "Анемнез"))
    list.add(new Thesaurus(3, 2, "1_01_01", "An.vitae", "An.vitae"))
    list.add(new Thesaurus(1471, 1, "1_04", "Жалобы", "Жалобы"))

    logger.warn("Start of GetThesaurus test:")
    this.subTestGetThesaurus(list)
    logger.warn("Successful end of GetThesaurus test")
  }

  //Тест: не найдены записи в БД
  @Test
  def testGetThesaurusCaseNullResponse  = {

    val list = new util.LinkedList[Thesaurus]()
    logger.warn("Start of GetThesaurus with null db response:")
    this.subTestGetThesaurus(list)
    logger.warn("Successful end of GetThesaurus test with null db response")
  }

  private def subTestGetThesaurus (list: util.LinkedList[Thesaurus]) = {

    val mapper: ObjectMapper = new ObjectMapper()
    val filter = new ThesaurusListRequestDataFilter(0, null, null)
    val request = new ListDataRequest("id", "asc", 10, 1, filter)

    logger.info("Request data for method is: {}", mapper.writeValueAsString(request))

    try {
      when(dbCustomQueryBean.getCountOfThesaurusWithFilter(request.filter)).thenReturn(list.size)
      when(dbCustomQueryBean.getAllThesaurusWithFilter(request.page,
                                                       request.limit,
                                                       request.sortingFieldInternal,
                                                       request.filter.unwrap())).thenReturn(list)

      val result = wsImpl.getThesaurusList(request, null)

      verify(dbCustomQueryBean).getCountOfThesaurusWithFilter(request.filter)
      verify(dbCustomQueryBean).getAllThesaurusWithFilter(request.page,
                                                          request.limit,
                                                          request.sortingFieldInternal,
                                                          request.filter.unwrap())

      Assert.assertNotNull(result)
      Assert.assertEquals(result.data.size(), list.size())
      for(i <- 0 until list.size()){
        Assert.assertEquals(result.data.get(i).id, list.get(i).getId)
        Assert.assertEquals(result.data.get(i).name, list.get(i).getName)
        Assert.assertEquals(result.data.get(i).code, list.get(i).getCode)
        Assert.assertEquals(result.data.get(i).groupId, list.get(i).getGroupId)
      }
      validateMockitoUsage()
      logger.info("The method has been successfully completed. Result is: {}", mapper.writeValueAsString(result))

    } catch {
      case ex: CoreException => {
        logger.error("GetThesaurus test failed with error: {}", ex.getMessage + " " + ex.getStackTrace)
      }
    }
  }

  //______________________________________________________________________________________________________
  //****************************************   GetQuotaTypes Tests   ********************************
  //______________________________________________________________________________________________________

  @Test
  def testGetQuotaTypes  = {

    val mapper: ObjectMapper = new ObjectMapper()
    val filter = new QuotaTypesListRequestDataFilter(0, null, null)
    val request = new ListDataRequest("id", "asc", 10, 1, filter)

    val list = new util.LinkedList[QuotaType]()
    list.add(new QuotaType(1, "04", "ГЕМАТОЛОГИЯ"))
    list.add(new QuotaType(2, "04.00.001", "Комплексное лечение, включая полихимиотерапию, иммунотерапию, трансфузионную терапию препаратами крови и плазмы;  методы   экстракорпорального воздействия на кровь, дистанционную лучевую   терапию, хирургические  методы лечения при: апластических   анемиях; апластических, цитопенических и цитолитических синдромах; агранулоцитозе; нарушениях плазменного  и тромбоцитарного гемостаза; острой лучевой болезни"))
    list.add(new QuotaType(3, "04.00.002", "Комплексная консервативная терапия. Реконструктивно-восстановительные операции при деформациях и повреждениях  конечностей с   коррекцией формы и   длины конечностей  у больных с  наследственным  и приобретенным дефицитом VIII,  IX факторов, фактора Виллебранда и других факторов свертывания крови (в том числе с наличием ингибиторов к факторам свертывания)"))

    logger.warn("Start of GetQuotaTypes test:")
    logger.info("Request data for method is: {}", mapper.writeValueAsString(request))

    try {
      when(dbQuotaTypeBean.getAllQuotaTypesWithFilter(anyInt(), anyInt(), anyString(), anyObject(), anyObject())).thenReturn(list)
      val result = wsImpl.getQuotaTypes(request)
      verify(dbQuotaTypeBean).getAllQuotaTypesWithFilter(anyInt(), anyInt(), anyString(), anyObject(), anyObject())

      Assert.assertNotNull(result)
      Assert.assertEquals(result.data.isInstanceOf[util.LinkedList[GroupQuotaTypeContainer]], true)
      Assert.assertEquals(result.data.asInstanceOf[util.LinkedList[GroupQuotaTypeContainer]].size(), list.size())
      for(i <- 0 until list.size()){
        Assert.assertEquals(result.data.asInstanceOf[util.LinkedList[GroupQuotaTypeContainer]].get(i).id, list.get(i).getId)
        Assert.assertEquals(result.data.asInstanceOf[util.LinkedList[GroupQuotaTypeContainer]].get(i).code, list.get(i).getCode)
      }
      validateMockitoUsage()
      logger.info("The method has been successfully completed. Result is: {}", mapper.writeValueAsString(result))
      logger.warn("Successful end of GetQuotaTypes test")
    } catch {
      case ex: CoreException => {
        logger.error("GetQuotaTypes test failed with error: {}", ex.getMessage + " " + ex.getStackTrace)
      }
    }
  }

  //______________________________________________________________________________________________________
  //****************************************   GetRlsList Tests   ********************************
  //______________________________________________________________________________________________________

  //Тест: непустая выборка из БД
  @Test
  def testGetRlsList = {

    val list = new util.LinkedList[Nomenclature]()
    list.add(new Nomenclature(1, 11, "тестовое наименование 1","test trade name 1", "dosage 1", "form 1"))
    list.add(new Nomenclature(2, 21, "тестовое наименование 2","test trade name 2", "dosage 2", "form 2"))
    list.add(new Nomenclature(3, 31, "тестовое наименование 3","test trade name 3", "dosage 3", "form 3"))

    logger.warn("Start of GetRlsList test:")
    this.subTestGetRlsList(list)
    logger.warn("Successful end of GetRlsList test")
  }

  //Тест: не найдены записи в БД
  @Test
  def testGetRlsListCaseNullResponse  = {

    logger.warn("Start of GetRlsList with null db response:")
    this.subTestGetRlsList(null)
    logger.warn("Successful end of GetRlsList test with null db response")
  }

  private def subTestGetRlsList(list: util.LinkedList[Nomenclature])  = {

    val mapper: ObjectMapper = new ObjectMapper()
    val filter = new RlsDataListFilter(0, null, null, null)
    val request = new RlsDataListRequestData("id", "asc", 10, 1, filter)

    logger.info("Request data for method is: {}", mapper.writeValueAsString(request))

    try {
      val size = if(list!=null)list.size() else 0
      when(dbRlsBean.getCountOfRlsRecordsWithFilter(request.filter)).thenReturn(size)
      when(dbRlsBean.getRlsListWithFilter(anyInt(), anyInt(), anyString(), anyObject(), anyObject())).thenReturn(list)
      val result = wsImpl.getFilteredRlsList(request)
      verify(dbRlsBean).getCountOfRlsRecordsWithFilter(request.filter)
      verify(dbRlsBean).getRlsListWithFilter(anyInt(), anyInt(), anyString(), anyObject(), anyObject())

      Assert.assertNotNull(result)
      Assert.assertEquals(result.data.size(), size)
      for(i <- 0 until size){
        Assert.assertEquals(result.data.get(i).id, list.get(i).getId)
        Assert.assertEquals(result.data.get(i).code, list.get(i).getCode)
        Assert.assertEquals(result.data.get(i).tradeName, list.get(i).getTradeName)
        Assert.assertEquals(result.data.get(i).tradeNameLatin, list.get(i).getTradeNameLat)
        Assert.assertEquals(result.data.get(i).dosage, list.get(i).getDosage)
        Assert.assertEquals(result.data.get(i).form, list.get(i).getForm)
      }
      validateMockitoUsage()
      logger.info("The method has been successfully completed. Result is: {}", mapper.writeValueAsString(result))
    } catch {
      case ex: CoreException => {
        logger.error("GetRlsList test failed with error: {}", ex.getMessage + " " + ex.getStackTrace)
      }
    }
  }

  //______________________________________________________________________________________________________
  //****************************************   GetEventTypes Tests   ********************************
  //______________________________________________________________________________________________________

  @Test
  def testGetEventTypes  = {

    val mapper: ObjectMapper = new ObjectMapper()
    val filter = new EventTypesListRequestDataFilter(0, 0)
    val request = new ListDataRequest("id", "asc", 10, 1, filter)

    val list = new util.LinkedList[EventType]()
    list.add(new EventType(1, "Test EventType 1"))
    list.add(new EventType(2, "Test EventType 2"))
    list.add(new EventType(3, "Test EventType 3"))

    logger.warn("Start of GetEventTypes test:")
    logger.info("Request data for method is: {}", mapper.writeValueAsString(request))

    try {
      when(dbEventTypeBean.getEventTypesByRequestTypeIdAndFinanceId(anyInt(), anyInt(), anyString(), anyObject(), anyObject())).thenReturn(list)
      val result = wsImpl.getEventTypes(request, null)
      verify(dbEventTypeBean).getEventTypesByRequestTypeIdAndFinanceId(anyInt(), anyInt(), anyString(), anyObject(), anyObject())

      Assert.assertNotNull(result)
      val json = mapper.readValue(result, classOf[EventTypesListData])
      Assert.assertEquals(json.data.size(), list.size())
      for(i <- 0 until list.size()){
        Assert.assertEquals(json.data.get(i).id, list.get(i).getId)
        Assert.assertEquals(json.data.get(i).value, list.get(i).getName)
      }
      validateMockitoUsage()
      logger.info("The method has been successfully completed. Result is: {}", mapper.writeValueAsString(result))
      logger.warn("Successful end of GetEventTypes test")
    } catch {
      case ex: CoreException => {
        logger.error("GetEventTypes test failed with error: {}", ex.getMessage + " " + ex.getStackTrace)
      }
    }
  }

  //______________________________________________________________________________________________________
  //****************************************   GetActionTypes Tests   ********************************
  //______________________________________________________________________________________________________

  //Ветка 1: Фильтр group_id
  @Test
  def testGetActionTypesByGroupId = {

    val mnemonics = new util.LinkedList[String]
    val filter = new ActionTypesListRequestDataFilter("", 1, null, mnemonics, null, true, null)
    val request = new ListDataRequest("id", "asc", 10, 1, filter)
    val list = new util.LinkedList[ActionType]()

    logger.warn("Start of GetActionTypes with filter by group_id test:")
    val at1 = new ActionType(1)
    val at2 = new ActionType(2)
    val result = this.subTestGetActionTypesFlatView(request, list.size(), at1, at2, new JSONCommonData(), list)

    verify(actionTypeBean).getCountAllActionTypeWithFilter(request.filter)
    verify(actionTypeBean).getActionTypeById(request.filter.asInstanceOf[ActionTypesListRequestDataFilter].getGroupId())
    verify(primaryAssessmentBean).getEmptyStructure(anyInt(), anyString(), anyListOf(classOf[String]), anyListOf(classOf[StringId]),  anyObject(), anyObject(), anyObject())

    Assert.assertNotNull(result)
    /*val json = mapper.readValue(result, classOf[EventTypesListData])
    Assert.assertEquals(json.data.size(), list.size())
    for(i <- 0 until list.size()){
      Assert.assertEquals(json.data.get(i).id, list.get(i).getId)
      Assert.assertEquals(json.data.get(i).value, list.get(i).getName)
    }*/
    validateMockitoUsage()

    logger.warn("Successful end of GetActionTypes with filter by group_id test")
  }

  //Ветка 2: Фильтр code
 @Test
  def testGetActionTypesByCode= {

    val mnemonics = new util.LinkedList[String]
    val filter = new ActionTypesListRequestDataFilter("testCode", 0, null, mnemonics, null, true, null)
    val request = new ListDataRequest("id", "asc", 10, 1, filter)
    val list = new util.LinkedList[ActionType]()

    logger.warn("Start of GetActionTypes with filter by code test:")
    val at1 = new ActionType(1)
    val at2 = new ActionType(2)
    val result = this.subTestGetActionTypesFlatView(request, list.size(), at1, at2, new JSONCommonData(), list)

    verify(actionTypeBean).getCountAllActionTypeWithFilter(request.filter)
    verify(actionTypeBean).getActionTypeByCode(request.filter.asInstanceOf[ActionTypesListRequestDataFilter].getCode())
    verify(primaryAssessmentBean).getEmptyStructure(anyInt(), anyString(), anyListOf(classOf[String]), anyListOf(classOf[StringId]),  anyObject(), anyObject(), anyObject())

    Assert.assertNotNull(result)
    /*val json = mapper.readValue(result, classOf[EventTypesListData])
    Assert.assertEquals(json.data.size(), list.size())
    for(i <- 0 until list.size()){
      Assert.assertEquals(json.data.get(i).id, list.get(i).getId)
      Assert.assertEquals(json.data.get(i).value, list.get(i).getName)
    }*/
    validateMockitoUsage()

    logger.warn("Successful end of GetActionTypes with filter by code test")
  }
  //Ветка 3: Фильтра нет и count=0
  @Test
  def testGetActionTypesWithoutFilters = {

    val mnemonics = new util.LinkedList[String]
    val filter = new ActionTypesListRequestDataFilter("", 0, null, mnemonics, null, true, null)
    val request = new ListDataRequest("id", "asc", 10, 1, filter)
    val list = new util.LinkedList[ActionType]()

    logger.warn("Start of GetActionTypes without filter test:")
    val at1 = new ActionType(1)
    val at2 = new ActionType(2)
    val result = this.subTestGetActionTypesFlatView(request, list.size(), at1, at2, new JSONCommonData(), list)
    verify(actionTypeBean).getCountAllActionTypeWithFilter(request.filter)
    Assert.assertNotNull(result)
    logger.warn("Successful end of GetActionTypes without filter test")
  }

  //Ветка 4: count>0   плоская структура
  @Test
  def testGetActionTypesFlatView = {

    val mnemonics = new util.LinkedList[String]
    val filter = new ActionTypesListRequestDataFilter("", 1, null, mnemonics, "all", true, null)
    val request = new ListDataRequest("id", "asc", 10, 1, filter)
    val list = new util.LinkedList[ActionType]()
    list.add(new ActionType(11, 1, "test code 1", "test flatCode 1", "test name 1"))
    list.add(new ActionType(12, 1, "test code 2", "test flatCode 2", "test name 2"))
    list.add(new ActionType(21, 2, "test code 3", "test flatCode 3", "test name 3"))

    logger.warn("Start of GetActionTypes with flat view test:")
    val at1 = new ActionType(1)
    val at2 = new ActionType(2)

    val result = this.subTestGetActionTypesFlatView(request, list.size(), at1, at2, new JSONCommonData(), list)
    verify(actionTypeBean).getAllActionTypeWithFilter(anyInt(), anyInt(), anyString(), anyObject())

    Assert.assertNotNull(result)
    Assert.assertEquals(result.isInstanceOf[String], true)
    val mapper: ObjectMapper = new ObjectMapper()
    val json = mapper.readValue(result.asInstanceOf[String], classOf[ActionTypesListData])

    Assert.assertEquals(json.data.size(), list.size())
    for(i <- 0 until list.size()){
      Assert.assertEquals(json.data.get(i).id, list.get(i).getId)
      Assert.assertEquals(json.data.get(i).groupId, list.get(i).getGroupId)
      Assert.assertEquals(json.data.get(i).code, list.get(i).getCode)
      Assert.assertEquals(json.data.get(i).flatCode, list.get(i).getFlatCode)
      Assert.assertEquals(json.data.get(i).name, list.get(i).getName)
    }

    logger.warn("Successful end of GetActionTypes with flat view test")
  }

  //Ветка 5: Дерево
 /* @Test
  def testGetActionTypesTreeView = {

    val mnemonics = new util.LinkedList[String]
    val filter = new ActionTypesListRequestDataFilter("", 1, null, mnemonics, "tree")
    val request = new ListDataRequest("id", "asc", 10, 1, filter)
    val list = new util.LinkedList[ActionType]()
    list.add(new ActionType(11, 1, "test code 1", "test flatCode 1", "test name 1"))
    list.add(new ActionType(12, 1, "test code 2", "test flatCode 2", "test name 2"))
    list.add(new ActionType(21, 2, "test code 3", "test flatCode 3", "test name 3"))


    val mapper: ObjectMapper = new ObjectMapper()

    logger.warn("Start of GetActionTypes with tree view test:")
    logger.info("Request data for method is: {}", mapper.writeValueAsString(request))

    try {
      when(actionTypeBean.getAllActionTypeWithFilter(anyInt(), anyInt(), anyString(), anyObject())).thenReturn(list)    //TODO: рекурсивно вызывается с разным group_id... как тестить моком?
      val result = wsImpl.getListOfActionTypes(request)
      verify(actionTypeBean).getAllActionTypeWithFilter(anyInt(), anyInt(), anyString(), anyObject())

      Assert.assertNotNull(result)
      val json = mapper.readValue(result, classOf[ActionTypesListData])

      Assert.assertEquals(json.data.size(), list.size())
      for(i <- 0 until list.size()){
        Assert.assertEquals(json.data.get(i).id, list.get(i).getId)
        Assert.assertEquals(json.data.get(i).groupId, list.get(i).getGroupId)
        Assert.assertEquals(json.data.get(i).code, list.get(i).getCode)
        Assert.assertEquals(json.data.get(i).flatCode, list.get(i).getFlatCode)
        Assert.assertEquals(json.data.get(i).name, list.get(i).getName)
      }

      logger.warn("Successful end of GetActionTypes with tree view test")
      logger.info("The method has been successfully completed. Result is: {}", mapper.writeValueAsString(result))
    } catch {
      case ex: CoreException => {
        logger.error("GetActionTypes failed with error: {}", ex.getMessage + " " + ex.getStackTrace)
      }
    }

  }*/

  private def subTestGetActionTypesFlatView(request: ListDataRequest, count: Long, at1: ActionType, at2: ActionType, emptyJsonCData: JSONCommonData, list: util.LinkedList[ActionType]) = {

    val mapper: ObjectMapper = new ObjectMapper()

    logger.info("Request data for method is: {}", mapper.writeValueAsString(request))

    try {
      when(actionTypeBean.getCountAllActionTypeWithFilter(request.filter)).thenReturn(count)
      when(actionTypeBean.getActionTypeById(request.filter.asInstanceOf[ActionTypesListRequestDataFilter].getGroupId())).thenReturn(at1)
      when(actionTypeBean.getActionTypeByCode(request.filter.asInstanceOf[ActionTypesListRequestDataFilter].getCode())).thenReturn(at2)
      when(primaryAssessmentBean.getEmptyStructure(anyInt(), anyString(), anyListOf(classOf[String]), anyListOf(classOf[StringId]),  anyObject(), anyObject(), anyObject())).thenReturn(emptyJsonCData)
      when(actionTypeBean.getAllActionTypeWithFilter(anyInt(), anyInt(), anyString(), anyObject())).thenReturn(list)

      val result = wsImpl.getListOfActionTypeIdNames(request, 1)

      logger.info("The method has been successfully completed. Result is: {}", mapper.writeValueAsString(result))
      result
    } catch {
      case ex: CoreException => {
        logger.error("GetActionTypes failed with error: {}", ex.getMessage + " " + ex.getStackTrace)
      }
    }
  }
}
