package ru.korus.tmis.ws.webmis.rest;

import com.sun.jersey.api.json.JSONWithPadding;
import ru.korus.tmis.auxiliary.AuxiliaryFunctions;
import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.data.*;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.core.logging.slf4j.interceptor.ServicesLoggingInterceptor;
import ru.korus.tmis.ws.impl.WebMisRESTImpl;
import javax.interceptor.Interceptors;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

/**
 * Список REST-сервисов для получения данных из справочников
 * Author: idmitriev Systema-Soft
 * Date: 3/19/13 2:06 PM
 * Since: 1.0.0.74
 */
@Interceptors(ServicesLoggingInterceptor.class)
public class DirectoryInfoRESTImpl {

    //protected static final String PATH = BaseRegistryRESTImpl.PATH;
    private WebMisRESTImpl wsImpl;
    private int limit;
    private int  page;
    private String sortingField;
    private String sortingMethod;
    private AuthData auth;
    private String callback;

    public DirectoryInfoRESTImpl(WebMisRESTImpl wsImpl, String callback,
                                  int limit, int  page, String sortingField, String sortingMethod,
                                  AuthData auth) {
        this.auth = auth;
        this.wsImpl = wsImpl;
        this.callback = callback;
        this.limit = limit;
        this.page = page;
        this.sortingField = sortingField;
        this.sortingMethod = sortingMethod;
    }
    //__________________________________________________________________________________________________________________
    //***********************************   СПРАВОЧНИКИ   ***********************************
    //__________________________________________________________________________________________________________________

    /**
     * Список персонала
     * <pre>
     * &#15; Возможные значения сортировки:
     * &#15; "id" - по идентификатору обращения (значение по умолчанию);</pre>
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws ru.korus.tmis.core.exception.CoreException
     * @see ru.korus.tmis.core.exception.CoreException
     */
    @GET
    @Path("/persons")
    @Produces("application/x-javascript")
    public Object getAllPersons() {
        ListDataRequest request = new ListDataRequest(this.sortingField, this.sortingMethod, this.limit, this.page, null);
        return new JSONWithPadding(wsImpl.getAllPersons(request),this.callback);
    }

    /**
     * Список свободных на выбранное время врачей по специальности
     * <pre>
     * &#15; Внимание! Сортировка выкл.</pre>
     * @param speciality  Фильтр по специальности врача.
     * @param doctorId Фильтр по идентификатору специалиста.
     * @param beginDate Дата начала периода, по которому ищутся свободные специалисты.
     * @param endDate Дата конца периода, по которому ищутся свободные специалисты.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws ru.korus.tmis.core.exception.CoreException
     * @see ru.korus.tmis.core.exception.CoreException
     */
    @GET
    @Path("/persons/free")
    @Produces("application/x-javascript")
    public Object getFreePersons(@QueryParam("filter[speciality]")int speciality,
                                 @QueryParam("filter[doctorId]")int doctorId,
                                 @QueryParam("filter[beginDate]")long beginDate,
                                 @QueryParam("filter[endDate]")long endDate) {

        FreePersonsListDataFilter filter = new FreePersonsListDataFilter(speciality, doctorId, beginDate, endDate);
        ListDataRequest request = new ListDataRequest(this.sortingField, this.sortingMethod, this.limit, this.page, filter);
        return new JSONWithPadding(wsImpl.getFreePersons(request),this.callback);
    }

    /**
     * Список отделений
     * <pre>
     * &#15; Возможные значения сортировки:
     * &#15; "id" - по идентификатору отделения (значение по умолчанию);
     * &#15; "name" - по наименованию отделения </pre>
     * @param hasBeds Фильтр отделений имеющих развернутые койки.("true"/"false")
     * @param callback  callback запроса.
     * @param servRequest Контекст запроса с клиента.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws ru.korus.tmis.core.exception.CoreException
     * @see ru.korus.tmis.core.exception.CoreException
     */
    @GET
    @Path("/departments")
    @Produces("application/x-javascript")
    public Object getAllDepartments(@QueryParam("filter[hasBeds]")String hasBeds,
                                    @QueryParam("callback") String callback,
                                    @Context HttpServletRequest servRequest) {

        Boolean flgBeds =  (hasBeds!=null && hasBeds.indexOf("true")>=0) ? true : false;
        DepartmentsDataFilter filter = new DepartmentsDataFilter(flgBeds);
        ListDataRequest request = new ListDataRequest(this.sortingField, this.sortingMethod, this.limit, this.page, filter);
        return new JSONWithPadding(wsImpl.getAllDepartments(request),this.callback);
    }

    /**
     * Получение данных из MKB справочников.
     * <pre>
     * &#15; Значения полей для сортировки.
     * &#15; "id" - по идентификатору MKB (значение по умолчанию);
     * &#15; "classId" - по идентификатору класса МКВ (s11r64.MKB.classID);
     * &#15; "blockId" - по идентификатору блока МКВ(s11r64.MKB.blockID);
     * &#15; "code" - по коду МКВ диагноза (s11r64.MKB.diagID);
     * &#15; "diagnosis" - по обозначению МКВ диагнозу(s11r64.MKB.diagName);</pre>
     * @param mkbId  Фильтр по идентификатору диагноза по МКВ. (В url: filter[mkbId]=...)
     * @param classId Фильтр по идентификатору класса диагноза по МКВ. (В url: filter[classId]=...)
     * @param blockId Фильтр по идентификатору блока диагноза по МКВ. (В url: filter[groupId]=...)
     * @param code  Фильтр по коду диагноза по МКВ. (В url: filter[code]=...)
     * @param diagnosis Фильтр по обозначению диагноза по МКВ. (В url: filter[diagnosis]=...)
     * @param view  Уровень визуализации. (В url: filter[view]=...)<pre>
     * &#15; Возможные значения:
     * &#15; Если фильтр не задан или имеет недопустимое значение - вывод МКВ структуры в виде дерева;
     * &#15; Иначе, если имеет следующие ключи:
     * &#15; "class" - вывод плоской структуры уровня класса МКВ;
     * &#15; "group" - вывод плоской структуры уровня блока МКВ;
     * &#15; "subgroup" - вывод плоской структуры уровня подблока МКВ;
     * &#15; "mkb" - вывод плоской структуры нижнего уровня МКВ;</pre>
     * @param display Флаг указывающий отображать или нет свернутые фильтром ветки. Значения: true/false. (В url: filter[display]=...)
     * @param sex Фильтр по половой принадлежности диагноза по МКВ. (В url: filter[sex]=...)
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws ru.korus.tmis.core.exception.CoreException
     * @see ru.korus.tmis.core.exception.CoreException
     */
    @GET
    @Path("/mkbs")
    @Produces("application/x-javascript")
    public Object getAllActionTypeNames(@QueryParam("filter[mkbId]")int mkbId,
                                        @QueryParam("filter[classId]")String classId,
                                        @QueryParam("filter[groupId]")String blockId,
                                        @QueryParam("filter[code]")String code,
                                        @QueryParam("filter[diagnosis]")String diagnosis,
                                        @QueryParam("filter[view]")String view,
                                        @QueryParam("filter[display]")String display,
                                        @QueryParam("filter[sex]")int sex) {

        Boolean flgDisplay =  (display!=null && display.indexOf("true")>=0) ? true : false;
        MKBListRequestDataFilter filter = new MKBListRequestDataFilter(mkbId, classId, blockId, code, diagnosis, view, flgDisplay, sex);
        ListDataRequest request = new ListDataRequest(this.sortingField, this.sortingMethod, this.limit, this.page, filter);
        return new JSONWithPadding(wsImpl.getAllMkbs(request, this.auth),this.callback);
    }

    /**
     * Получение данных из динамических справочников плоских структур (FlatDirectory)<br>
     * В запросе используется один из фильтров filterRecordId, filter[X]=Y, filterValue
     * @link Подробное описание: https://docs.google.com/folder/d/0B-T1ZKDux1ZPYldTSU9BU2tSR0E/edit?pli=1&docId=1Eak6dN3AbSy1-JzYFD4SzLBpArV-44VqXOUpdqqVHnA
     * @param includeMeta Признак включения метаданных таблицы (FieldDescriptionList). Значения: yes/no
     * @param includeRecordList Признак включения списка записей справочника (RecordList). Значения: yes/no
     * @param includeFDRecord Признак включения метаданных полей в записи (FieldDescription.Name и FieldDescription.Description). Значения: yes/no
     * @param filterValue Фильтр для любого поля. Только один такой параметр в запросе.
     * @param limit Максимальное количество выводимых элементов на странице. (По умолчанию: limit = 10).<br>
     *              Limit общий для всех справочников, но распространяется на каждый справочник отдельно.
     * @param page Номер выводимой страницы. (По умолчанию: page = 1).<br>
     *              Page общий для всех справочников, но распространяется на каждый справочник отдельно.
     * @param info Контекст информации о url-запросе.<pre>
     * &#15; Анализируются следующие параметры:
     * &#15; flatDirectoryId - Идентификатор справочника. Может быть несколько.
     * &#15; Если параметр не указан, выдаются все справочники.
     * &#15; filterRecordId - Идентификатор записи из таблицы FDRecord. Может быть несколько.
     * &#15; filter[X]=Y - Фильтр для поиска (выдаются только те записи, у которых поле с типом X содержит Y).
     * &#15; Таких параметров в одном запросе может быть несколько.
     * &#15; sortingField[X] = Y - Сортировки по полям X с порядком сортировки Y. Может быть несколько.</pre>
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws ru.korus.tmis.core.exception.CoreException
     * @see ru.korus.tmis.core.exception.CoreException
     */
    @GET
    @Path("/flatDirectory")
    @Produces("application/x-javascript")
    public Object getFlatDirectories(@Context UriInfo info,
                                     @QueryParam("includeMeta") String includeMeta,
                                     @QueryParam("includeRecordList") String includeRecordList,
                                     @QueryParam("includeFDRecord") String includeFDRecord,
                                     @QueryParam("filterValue") String filterValue,
                                     @QueryParam("limit")int limit,
                                     @QueryParam("page")int  page) throws CoreException {

        //hand-made url query params parsing
        MultivaluedMap<String, String> queryParams = info.getQueryParameters();
        String fullQueryPath = info.getRequestUri().getQuery();

        java.util.List<Integer> flatDictionaryIds = AuxiliaryFunctions.convertStringListTo(queryParams.get("flatDirectoryId"));
        java.util.List<Integer> filterRecordIds = AuxiliaryFunctions.convertStringListTo(queryParams.get("filterRecordId"));
        java.util.Map<Integer, java.util.List<String>> filterFields = AuxiliaryFunctions.foldFilterValueTo(queryParams, "filter[", "]");
        java.util.LinkedHashMap<Integer, Integer> sortingFieldIds= AuxiliaryFunctions.foldFilterValueToLinkedMapFromQuery(fullQueryPath, "sortingField[", "]=");

        boolean fields = ((filterFields!=null)&&filterFields.size()>0);
        boolean values = ((filterValue!=null)&&(filterValue.isEmpty()!=true));
        boolean recordIds = ((filterRecordIds!=null)&&filterRecordIds.size()>0);

        if((fields&&values)||(fields&&recordIds)||(recordIds&&values)) {
            throw new CoreException("Одновременно в запросе может использоваться только один тип фильтра");
        }
        FlatDirectoryRequestDataListFilter filter = new FlatDirectoryRequestDataListFilter(flatDictionaryIds,
                                                                includeMeta, includeRecordList, includeFDRecord,
                                                                filterFields, filterValue, filterRecordIds);
        FlatDirectoryRequestData request = new FlatDirectoryRequestData(sortingFieldIds, limit, page, filter);

        return new JSONWithPadding(wsImpl.getFlatDirectories(request),this.callback);
    }

    /**
     * Получение данных из Тезауруса.
     * <pre>
     * &#15; Возможные значения для сортировки:
     * &#15; "id" - по идентификатору записи (значение по умолчанию);
     * &#15; "groupId" - по идентификатору группы тезауруса;
     * &#15; "code" - по коду тезауруса;</pre>
     * @param thesaurusId  Фильтр по идентификатору тезауруса. (В url: filter[id]=...)
     * @param groupId Фильтр по идентификатору группы тезауруса. (В url: filter[groupId]=...)
     * @param code Фильтр по коду тезауруса. (В url: filter[code]=...)
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws CoreException
     * @see CoreException
     */
    @GET
    @Path("/thesaurus")
    @Produces("application/x-javascript")
    public Object getThesaurus(@QueryParam("filter[id]")int thesaurusId,
                               @QueryParam("filter[groupId]")String groupId,
                               @QueryParam("filter[code]")String code) {
        ThesaurusListRequestDataFilter filter = new ThesaurusListRequestDataFilter(thesaurusId, groupId, code);
        ListDataRequest request = new ListDataRequest(this.sortingField, this.sortingMethod, this.limit, this.page, filter);
        return new JSONWithPadding(wsImpl.getThesaurusList(request, this.auth),this.callback);
    }

    /**
     * Получение типов квот.
     * <pre>
     * &#15; Возможные значения для сортировки:
     * &#15; "id" - по идентификатору записи (значение по умолчанию);
     * &#15; "groupId" - по идентификатору группы;
     * &#15; "code" - по коду;</pre>
     * @param typeId Идентификатор типа квоты  (В url: filter[id]=...)
     * @param groupId Фильтр по идентификатору группы типов квот. (В url: filter[groupId]=...)
     * @param code Фильтр по коду типа квоты. (В url: filter[code]=...)
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws CoreException
     * @see CoreException
     */
    @GET
    @Path("/quotaTypes")
    @Produces("application/x-javascript")
    public Object getQuotaTypes(@QueryParam("filter[id]")int typeId,
                                @QueryParam("filter[code]")String code,
                                @QueryParam("filter[groupId]")String groupId) {
        QuotaTypesListRequestDataFilter filter = new QuotaTypesListRequestDataFilter(typeId, code, groupId);
        ListDataRequest request = new ListDataRequest(this.sortingField, this.sortingMethod, this.limit, this.page, filter);
        return new JSONWithPadding(wsImpl.getQuotaTypes(request),this.callback);
    }

    /**
     * Сервис возвращает указанный справочник.
     * <pre>
     * &#15; Возможные значения параметра для сортировки:
     * &#15; "id" - по идентификатору записи (значение по умолчанию);
     * &#15; "groupId" - по идентификатору группы тезауруса;
     * &#15; "code" - по коду тезауруса;</pre>
     * @param dictName Обозначение справочника, в котором идет выборка.<pre>
     * &#15; Возможные ключи:
     * &#15; "bloodTypes"  - справочник групп крови;
     * &#15; "relationships" - справочник типов родственных связей;
     * &#15; "citizenships" | "citizenships2" - справочник гражданств;
     * &#15; "socStatus"  - справочник социальных статусов;
     * &#15; "TFOMS" - справочник ТФОМС;
     * &#15; "clientDocument" - справочник типов документов, удостоверяющих личность;
     * &#15; "insurance" - справочник страховых компаний;
     * &#15; "policyTypes" - справочник видов полисов;
     * &#15; "disabilityTypes" - справочник типов инвалидностей;
     * &#15; "KLADR" - КЛАДР;
     * &#15; "valueDomain" - список возможных значений для ActionProperty;
     * &#15; "specialities" - справочник специальностей;
     * &#15; "quotaStatus" - Справочник статусов квот;
     * &#15; "quotaType" - Справочник типов квот;
     * &#15; "contactTypes" - справочник типов контактов;
     * &#15; "tissueTypes"  - справочник типов исследования;</pre>
     * @param headId   Фильтр для справочника "insurance". Идентификатор родительской компании. (В url: filter[headId]=...)
     * @param groupId  Фильтр для справочника "clientDocument". Идентификатор группы типов документов. (В url: filter[groupId]=...)
     * @param name     Фильтр для справочника "policyTypes". Идентификатор обозначения полиса. (В url: filter[name]=...)
     * @param level    Фильтр для справочника "KLADR". Уровень кода по КЛАДР. (В url: filter[level]=...)<pre>
     * &#15; Может принимать следующие значения:
     * &#15; "republic" - код республики по КЛАДР;
     * &#15; "district" - код района по КЛАДР;
     * &#15; "city" - код города по КЛАДР;
     * &#15; "locality" - код населенного пункта по КЛАДР;
     * &#15; "street" - код улицы по КЛАДР;</pre>
     * @param parent   Фильтр для справочника "KLADR". (В url: filter[parent]=...)<pre>
     * &#15; КЛАДР-код элемента более высокого уровня, для которого происходит выборка дочерних элементов.</pre>
     * @param type     Фильтр для справочника "valueDomain". Идентифиувтор типа действия (s11r64.ActionType.id). (В url: filter[typeIs]=...);
     * @param capId    Фильтр для справочника "valueDomain". Идентификатору записи в s11r64.rbCoreActionPropertyType. (В url: filter[capId]=...)
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws CoreException
     * @see CoreException
     */
    @GET
    @Path("/dictionary")
    @Produces("application/x-javascript")
    public Object getRecordsFromDictionary(@QueryParam("dictName")String dictName,
                                           @QueryParam("filter[headId]")int headId,
                                           @QueryParam("filter[groupId]")int groupId,
                                           @QueryParam("filter[name]")String name,
                                           @QueryParam("filter[level]")String level,      //KLADR
                                           @QueryParam("filter[parent]")String parent,    //KLADR
                                           @QueryParam("filter[typeIs]")String type,        //valueDomain
                                           @QueryParam("filter[capId]")int capId  //valueDomain
                                          ) {
        DictionaryListRequestDataFilter filter = new DictionaryListRequestDataFilter(dictName, headId, groupId, name, level, parent, type, capId);
        ListDataRequest request = new ListDataRequest(this.sortingField, this.sortingMethod, this.limit, this.page, filter);
        return new JSONWithPadding(wsImpl.getDictionary(request, dictName),this.callback);
    }

    /**
     * Получение данных из справочника медицинских препоратов (rls).
     * <pre>
     * &#15; Возможные значения для сортировки:
     * &#15; "name" | "tradeName" - по торговому наименованию препората (значение по умолчанию);
     * &#15; "code" - по коду препората;
     * &#15; "dosage" - по номинальной дозировке препората;
     * &#15; "form" - по форме комплектования препората;</pre>
     * @param name  Фильтр по обозначению препората. (В url: filter[name]=...)<pre>
     * &#15; Проверяется сперва торговое обозначение, после латинское обозначение препората.</pre>
     * @param code Фильтр по коду медицинского препората. (В url: filter[code]=...)
     * @param dosage Фильтр по дозировке. (В url: filter[dosage]=...)
     * @param form Фильтр по форме выпуска препората. (В url: filter[form]=...)
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws CoreException
     * @see CoreException
     */
    @GET
    @Path("/rls")
    @Produces("application/x-javascript")
    public Object getRlsList( @QueryParam("filter[name]")String name,
                              @QueryParam("filter[code]")int code,
                              @QueryParam("filter[dosage]")String dosage,
                              @QueryParam("filter[form]")String form) {
        RlsDataListFilter filter = new RlsDataListFilter(code, name, dosage, form);
        RlsDataListRequestData request = new RlsDataListRequestData(this.sortingField, this.sortingMethod, this.limit, this.page, filter);
        return new JSONWithPadding(wsImpl.getFilteredRlsList(request),this.callback);
    }

    /**
     * Сервис по получению списка обращений <br>
     * Путь: ../tms-registry/eventTypes
     * <pre>
     * &#15; Возможные значения для сортировки:
     * &#15; "id" - по торговому наименованию препората (значение по умолчанию);
     * &#15; "name" - по коду препората;</pre>
     * @param requestType Идентификатор типа стационара rbRequestType.id
     * @param finance  Идентификатор типа оплаты rbFinance.id
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws CoreException
     */
    @GET
    @Path("/eventTypes")
    @Produces("application/x-javascript")
    public Object getEventTypes(@QueryParam("filter[requestType]")int requestType,
                                @QueryParam("filter[finance]")int finance) {
        EventTypesListRequestDataFilter filter = new EventTypesListRequestDataFilter(finance, requestType);
        ListDataRequest request = new ListDataRequest(this.sortingField, this.sortingMethod, this.limit, this.page, filter);
        return new JSONWithPadding(wsImpl.getEventTypes(request, this.auth),this.callback);
    }

    @GET
    @Path("/actionTypes")
    @Produces("application/x-javascript")
    public Object getAllActionTypeNames(@QueryParam("patientId")int patientId,
                                        @QueryParam("filter[groupId]")int groupId,
                                        @QueryParam("filter[code]")String code,
                                        @QueryParam("filter[view]")String view) {
        ActionTypesSubType atst = ActionTypesSubType.getType("");
        return new JSONWithPadding(getAllActionTypeNamesEx(atst, patientId, this.limit, this.page, this.sortingField, this.sortingMethod, groupId, code, view, this.auth),this.callback);
    }

    /**
     * Получение списка типа действий (ActionType's) по коду и/или идентификатору группы<br>
     * @param patientId Идентификатор пациента
     * <pre>
     * &#15; Значения поля для сортировки:
     * &#15; "id" - по идентификатору типа действия;
     * &#15; "groupId" - по идентификатору группы типа действия;
     * &#15; "code" - по коду типа действия;
     * &#15; "name" - по обозначению типа действия;</pre>
     * @param groupId Фильтр по идентификатору группы типа действия (s11r64.ActionType.group_id). (В url: filter[groupId]=...)
     * @param code  Фильтр по коду типа действия (s11r64.ActionType.code). (В url: filter[code]=...)
     * @param view  Фильтр по коду типа отображения информации. (В url: filter[view]=...)
     * &#15; Возможные значения:
     * &#15; "tree" - в виде дерева;
     * &#15; "flat" - в виде плоской структуры;</pre>
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws CoreException
     * @see CoreException
     */
    @GET
    @Path("/actionTypes/{var}")
    @Produces("application/x-javascript")
    public Object getActionTypeNamesBySubTypes(   @PathParam("var") String var,
                                                  @QueryParam("patientId")int patientId,
                                                  @QueryParam("filter[groupId]")int groupId,
                                                  @QueryParam("filter[code]")String code,
                                                  @QueryParam("filter[view]")String view) {
        ActionTypesSubType atst = ActionTypesSubType.getType(var);
        return new JSONWithPadding(getAllActionTypeNamesEx(atst, patientId, this.limit, this.page, this.sortingField, this.sortingMethod, groupId, code, view, this.auth),this.callback);
    }

    //__________________________________________________________________________________________________________________
    //***********************************   НЕФОРМАЛИЗОВАННЫЕ МЕТОДЫ И ЗАПРОСЫ  ***********************************
    //__________________________________________________________________________________________________________________

    /**
     * Форма 007
     * Specification: https://docs.google.com/document/d/1a0AYF8QVpEMl_pKRcFDnP2vQzRmO-IkcG5JNStEcjMI/edit#heading=h.6ll2qz4wdcfr
     * URL: .../reports/f007
     * Since: ver 1.0.0.57
     * @param departmentId Идентификатор отделения, для которого отчет (задавать в url как QueryParam: "filter[departmentId]=...")
     * @param beginDate Дата и время начала выборки (задавать в url как QueryParam: "filter[beginDate]=...")
     *                Если не задан, то по умолчанию начало текущих медицинских суток (вчера 8:00) или,
     *                если задан endDate, то endDate минус сутки.
     * @param endDate Дата и время конца выборки (задавать в url как QueryParam: "filter[endDate]=...").
     *                Если не задан, то по умолчанию конец текущих медицинских суток (сегодня 7:59) или,
     *                если задан beginDate, то beginDate плюс сутки.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws ru.korus.tmis.core.exception.CoreException
     * @see ru.korus.tmis.core.exception.CoreException
     */
    @GET
    @Path("/reports/f007")
    @Produces("application/x-javascript")
    public Object getForm007( @QueryParam("filter[departmentId]") int departmentId,
                              @QueryParam("filter[beginDate]")long beginDate,
                              @QueryParam("filter[endDate]")long endDate) {
        //Отделение обязательное поле, если не задано в запросе, то берем из роли специалиста
        int depId = (departmentId>0) ? departmentId : this.auth.getUser().getOrgStructure().getId().intValue();
        return new JSONWithPadding(wsImpl.getForm007(depId, beginDate, endDate, this.auth),this.callback);
    }

    /**
     * Проверка на наличие номера в базе данных.
     * Используется для проверки номера обращения по госпитализации, полиса или СНИЛС.
     * @param name Имя проверяемого номера.<pre>
     * &#15; Возможные значения:
     * &#15; appealNumber - номер госпитализации;
     * &#15; SNILS - СНИЛС;
     * &#15; policy - мед. полис;</pre>
     * @param number Номер документа.
     * @param serial Серия документа.<pre>
     * &#15; Внимание! Параметр используется только для name = policy</pre>
     * @param typeId Тип документа.<pre>
     * &#15; Внимание! Параметр используется только для name = policy</pre>
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws ru.korus.tmis.core.exception.CoreException
     * @see ru.korus.tmis.core.exception.CoreException
     */
    @GET
    @Path("/checkExistance/{name}")
    @Produces("application/x-javascript")
    public Object checkAppealNumber(@PathParam("name") String name,
                                    @QueryParam("typeId")int typeId,
                                    @QueryParam("number")String number,
                                    @QueryParam("serial")String serial) {
        return new JSONWithPadding(wsImpl.checkExistanceNumber(name, typeId, number, serial),this.callback);
    }

    /**
     * Забор биоматериала
     * URL: ../biomaterial/info
     * Спецификация: https://docs.google.com/spreadsheet/ccc?key=0AgE0ILPv06JcdEE0ajBZdmk1a29ncjlteUp3VUI2MEE&pli=1#gid=5
     * @since 1.0.0.64
     * @param departmentId Фильтр по идентификатору отделения (В url: filter[departmentId]=...)<pre>
     * &#15; По умолчанию значение достается из авторизационной роли</pre>
     * @param beginDate Фильтр по дате начала выборки (В url: filter[beginDate]=...)<pre>
     * &#15; По умолчанию - начало текущих суток (Dd.Mm.Year 00:00).</pre>
     * @param endDate  Фильтр по дате окончания выборки (В url: filter[endDate]=...)<pre>
     * &#15; По умолчанию - начало текущих суток (Dd.Mm.Year 23:59).</pre>
     * @param status Фильтр по статусу забора (В url: filter[status]=...)<pre>
     * &#15; По умолчанию - 0.</pre>
     * @param biomaterial  Фильтр по статусу забора (В url: filter[status]=...)
     * @return
     */
    @GET
    @Path("/biomaterial/info")
    @Produces("application/x-javascript")
    public Object getTakingOfBiomaterial(@QueryParam("filter[departmentId]")int departmentId,
                                         @QueryParam("filter[beginDate]")long beginDate,
                                         @QueryParam("filter[endDate]")long endDate,
                                         @QueryParam("filter[status]") String status,
                                         @QueryParam("filter[biomaterial]") int biomaterial)   {

        //Отделение обязательное поле, если не задано в запросе, то берем из роли специалиста
        int depId = (departmentId>0) ? departmentId : this.auth.getUser().getOrgStructure().getId().intValue();
        short statusS = (status!=null && !status.isEmpty()) ? Short.parseShort(status): -1;

        TakingOfBiomaterialRequesDataFilter filter = new TakingOfBiomaterialRequesDataFilter(depId,
                                                                                            beginDate,
                                                                                            endDate,
                                                                                            statusS,
                                                                                            biomaterial);
        TakingOfBiomaterialRequesData request = new TakingOfBiomaterialRequesData(this.sortingField, this.sortingMethod, filter);
        return new JSONWithPadding(wsImpl.getTakingOfBiomaterial(request, this.auth),this.callback);
    }

    /**
     * Метод проставляет статус для тиккетов
     * @param data Список статусов для JobTicket
     * @return true - завершено успешно, false - завершено с ошибками
     */
    @PUT
    @Path("/jobTickets/status")
    @Produces("application/x-javascript")
    public Object setStatusesForJobTickets(JobTicketStatusDataList data) {
        return new JSONWithPadding(wsImpl.updateJobTicketsStatuses(data, this.auth),this.callback);
    }

    /**
     * Запрос на список пациентов для отделения.
     * Роль: врач отделения, сестра отделения
     * @param endDate Фильтр значений по дате закрытия госпитализации (В url: filter[date]=...)<pre>.
     * @param departmentId Фильтр значений по отделению (по умолчанию берется из данных авторизации) (В url: filter[departmentId]=...)<pre>.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws ru.korus.tmis.core.exception.CoreException
     * @see ru.korus.tmis.core.exception.CoreException
     */
    @GET
    @Path("/departments/patients")
    @Produces("application/x-javascript")
    public Object getAllPatientsForDepartmentOrUserByPeriod(@QueryParam("filter[date]")long endDate,
                                                            @QueryParam("filter[departmentId]") int departmentId) {

        int depId = (departmentId>0) ? departmentId : auth.getUser().getOrgStructure().getId().intValue();
        PatientsListRequestData requestData = new PatientsListRequestData ( depId,
                                                                            auth.getUser().getId().intValue(),
                                                                            auth.getUserRole().getId().intValue(),
                                                                            endDate,
                                                                            sortingField,
                                                                            sortingMethod,
                                                                            limit,
                                                                            page);
        return new JSONWithPadding(wsImpl.getAllPatientsForDepartmentIdAndDoctorIdByPeriod(requestData, this.auth),this.callback);
    }

    //__________________________________________________________________________________________________________________
    //***********************************   ВСПОМОГАТЕЛЬНЫЕ МЕТОДЫ   ***********************************
    //__________________________________________________________________________________________________________________

    private Object getAllActionTypeNamesEx(ActionTypesSubType val,
                                           int patientId,
                                           int limit,
                                           int  page,
                                           String sortingField,
                                           String sortingMethod,
                                           int groupId,
                                           String code,
                                           String view,
                                           AuthData auth) {
        ActionTypesListRequestDataFilter filter = new ActionTypesListRequestDataFilter(code, groupId, val.getSubType(), val.getMnemonic(), view);
        ListDataRequest request = new ListDataRequest(sortingField, sortingMethod, limit, page, filter);

        Object oip = (view != null && view.compareTo("tree") == 0) ? wsImpl.getListOfActionTypes(request)
                                                                   : wsImpl.getListOfActionTypeIdNames(request, patientId);
        return oip;
    }


    public enum ActionTypesSubType  {

        ALL(""){
            public String getSubType() { return "all";}
            public String getMnemonic() { return "";}
        },
        LABORATORY("laboratory"){
            public String getSubType() { return "laboratory";}
            public String getMnemonic() { return "LAB";}
        },
        INSTRUMENTAL("instrumental"){
            public String getSubType() { return "instrumental";}
            public String getMnemonic() { return "DI";}
        },
        CONSULTATIONS("consultations"){
            public String getSubType() { return "consultations";}
            public String getMnemonic() { return "";}
        };

        public abstract String getSubType();
        public abstract String getMnemonic();

        private String typeValue;

        private ActionTypesSubType(String type) {
            typeValue = type;
        }

        static public ActionTypesSubType getType(String pType) {
            for (ActionTypesSubType type: ActionTypesSubType.values()) {
                if (type.getTypeValue().equals(pType)) {
                    return type;
                }
            }
            throw new RuntimeException("Неизвестный тип для enum ActionTypesSubType");
        }

        public String getTypeValue() {
            return typeValue;
        }
    }
}
