package ru.korus.tmis.ws.webmis.rest;

import com.sun.jersey.api.json.JSONWithPadding;
import ru.korus.tmis.auxiliary.AuxiliaryFunctions;
import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.data.*;
import ru.korus.tmis.core.entity.model.RbPolicyType;
import ru.korus.tmis.core.exception.CoreException;

import ru.korus.tmis.ws.impl.ReferenceBookBean;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Список REST-сервисов для получения данных из справочников
 * Author: idmitriev Systema-Soft
 * Date: 3/19/13 2:06 PM
 * Since: 1.0.0.74
 */
@Stateless

public class DirectoryInfoRESTImpl {

    @EJB
    private WebMisREST wsImpl;

    @EJB
    private ReferenceBookBean referenceBookBean;

    //__________________________________________________________________________________________________________________
    //***********************************   СПРАВОЧНИКИ   ***********************************
    //__________________________________________________________________________________________________________________

    /**
     * Сервис возвращает указанный справочник.
     * <pre>
     * &#15; Возможные значения параметра для сортировки:
     * &#15; "id" - по идентификатору записи (значение по умолчанию);
     * &#15; "groupId" - по идентификатору группы тезауруса;
     * &#15; "code" - по коду тезауруса;</pre>
     *
     * @param dictName Обозначение справочника, в котором идет выборка.<pre>
     *                 &#15; Возможные ключи:
     *                 &#15; "bloodTypes"  - справочник групп крови;
     *                 &#15; "relationships" - справочник типов родственных связей;
     *                 &#15; "citizenships" | "citizenships2" - справочник гражданств;
     *                 &#15; "socStatus"  - справочник социальных статусов;
     *                 &#15; "TFOMS" - справочник ТФОМС;
     *                 &#15; "clientDocument" - справочник типов документов, удостоверяющих личность;
     *                 &#15; "insurance" - справочник страховых компаний;
     *                 &#15; "policyTypes" - справочник видов полисов;
     *                 &#15; "disabilityTypes" - справочник типов инвалидностей;
     *                 &#15; "KLADR" - КЛАДР;
     *                 &#15; "valueDomain" - список возможных значений для ActionProperty;
     *                 &#15; "specialities" - справочник специальностей;
     *                 &#15; "quotaStatus" - Справочник статусов квот;
     *                 &#15; "quotaType" - Справочник типов квот;
     *                 &#15; "contactTypes" - справочник типов контактов;
     *                 &#15; "tissueTypes"  - справочник типов исследования;</pre>
     * @param headId   Фильтр для справочника "insurance". Идентификатор родительской компании. (В url: filter[headId]=...)
     * @param groupId  Фильтр для справочника "clientDocument". Идентификатор группы типов документов. (В url: filter[groupId]=...)
     * @param name     Фильтр для справочника "policyTypes". Идентификатор обозначения полиса. (В url: filter[name]=...)
     * @param level    Фильтр для справочника "KLADR". Уровень кода по КЛАДР. (В url: filter[level]=...)<pre>
     *                 &#15; Может принимать следующие значения:
     *                 &#15; "republic" - код республики по КЛАДР;
     *                 &#15; "district" - код района по КЛАДР;
     *                 &#15; "city" - код города по КЛАДР;
     *                 &#15; "locality" - код населенного пункта по КЛАДР;
     *                 &#15; "street" - код улицы по КЛАДР;</pre>
     * @param parent   Фильтр для справочника "KLADR". (В url: filter[parent]=...)<pre>
     *                 &#15; КЛАДР-код элемента более высокого уровня, для которого происходит выборка дочерних элементов.</pre>
     * @param type     Фильтр для справочника "valueDomain". Идентифиувтор типа действия (s11r64.ActionType.id). (В url: filter[typeIs]=...);
     * @param capId    Фильтр для справочника "valueDomain". Идентификатору записи в s11r64.rbCoreActionPropertyType. (В url: filter[capId]=...)
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws CoreException
     * @see CoreException
     */
    @GET
    @Produces({"application/javascript", "application/x-javascript"})
    public Object getRecordsFromDictionary(@QueryParam("dictName") String dictName,
                                           @QueryParam("sortingField") String sortingField,
                                           @QueryParam("sortingMethod") String sortingMethod,
                                           @QueryParam("limit") int limit,
                                           @QueryParam("page") int page,
                                           @QueryParam("filter[headId]") int headId,
                                           @QueryParam("filter[groupId]") int groupId,
                                           @QueryParam("filter[name]") String name,
                                           @QueryParam("filter[level]") String level,      //KLADR
                                           @QueryParam("filter[parent]") String parent,    //KLADR
                                           @QueryParam("filter[typeIs]") String type,        //valueDomain
                                           @QueryParam("filter[capId]") int capId,  //valueDomain
                                           @QueryParam("callback") String callback,
                                           @QueryParam("eventId") Integer eventId
    ) throws CoreException {
        DictionaryListRequestDataFilter filter = new DictionaryListRequestDataFilter(dictName, headId, groupId, name, level, parent, type, capId);
        ListDataRequest request = new ListDataRequest(sortingField, sortingMethod, limit, page, filter);
        return new JSONWithPadding(wsImpl.getDictionary(request, dictName, eventId), callback);
    }


    @GET
    @Path("/policyTypes")
    @Produces({"application/javascript", "application/x-javascript", "application/xml"})
    public JSONWithPadding getPolicyTypes(@Context HttpServletRequest servRequest,
                                          @QueryParam("callback") String callback) {
        List<RbPolicyType> r = referenceBookBean.getPolicyTypes(mkAuth(servRequest));
        return new JSONWithPadding(new GenericEntity<List<RbPolicyType>>(r) {
        }, callback);
    }

    /**
     * Список персонала
     * <pre>
     * &#15; Возможные значения сортировки:
     * &#15; "id" - по идентификатору обращения (значение по умолчанию);</pre>
     *
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws ru.korus.tmis.core.exception.CoreException
     * @see ru.korus.tmis.core.exception.CoreException
     */
    @GET
    @Path("/persons")
    @Produces({"application/javascript", "application/x-javascript"})
    public Object getAllPersons(@QueryParam("sortingField") String sortingField,
                                @QueryParam("sortingMethod") String sortingMethod,
                                @QueryParam("limit") int limit,
                                @QueryParam("page") int page,
                                @QueryParam("filter[departmentId]") int departmentId,
                                @QueryParam("filter[roleCode]") List<String> roleCodeList,
                                @QueryParam("callback") String callback) throws CoreException {
        PersonsListDataFilter filter = new PersonsListDataFilter(departmentId, roleCodeList);
        ListDataRequest request = new ListDataRequest(sortingField, sortingMethod, limit, page, filter);
        return new JSONWithPadding(wsImpl.getAllPersons(request), callback);
    }

    /**
     * Список свободных на выбранное время врачей по специальности
     * <pre>
     * &#15; Внимание! Сортировка выкл.</pre>
     *
     * @param actionType Фильтр по типу действия.
     * @param speciality Фильтр по специальности врача.
     * @param doctorId   Фильтр по идентификатору специалиста.
     * @param beginDate  Дата начала периода, по которому ищутся свободные специалисты.
     * @param endDate    Дата конца периода, по которому ищутся свободные специалисты.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws ru.korus.tmis.core.exception.CoreException
     * @see ru.korus.tmis.core.exception.CoreException
     */
    @GET
    @Path("/persons/free")
    @Produces({"application/javascript", "application/x-javascript"})
    public Object getFreePersons(@QueryParam("sortingField") String sortingField,
                                 @QueryParam("sortingMethod") String sortingMethod,
                                 @QueryParam("limit") int limit,
                                 @QueryParam("page") int page,
                                 @QueryParam("filter[actionType]") int actionType,
                                 @QueryParam("filter[speciality]") int speciality,
                                 @QueryParam("filter[doctorId]") int doctorId,
                                 @QueryParam("filter[beginDate]") long beginDate,
                                 @QueryParam("filter[endDate]") long endDate,
                                 @QueryParam("callback") String callback) throws CoreException {

        FreePersonsListDataFilter filter = new FreePersonsListDataFilter(speciality, doctorId, actionType, beginDate, endDate);
        ListDataRequest request = new ListDataRequest(sortingField, sortingMethod, limit, page, filter);
        return new JSONWithPadding(wsImpl.getFreePersons(request, beginDate), callback);
    }

    /**
     * Список отделений
     * <pre>
     * &#15; Возможные значения сортировки:
     * &#15; "id" - по идентификатору отделения (значение по умолчанию);
     * &#15; "name" - по наименованию отделения </pre>
     *
     * @param hasBeds         Фильтр отделений имеющих развернутые койки.("true"/"false")
     * @param withoutChildren Выбрать только элементы без дочерних элементов
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws ru.korus.tmis.core.exception.CoreException
     * @see ru.korus.tmis.core.exception.CoreException
     */
    @GET
    @Path("/departments")
    @Produces({"application/javascript", "application/x-javascript"})
    public Object getAllDepartments(@QueryParam("sortingField") String sortingField,
                                    @QueryParam("sortingMethod") String sortingMethod,
                                    @QueryParam("limit") int limit,
                                    @QueryParam("page") int page,
                                    @QueryParam("filter[hasBeds]") String hasBeds,
                                    @QueryParam("withoutChildren") Boolean withoutChildren,
                                    @QueryParam("callback") String callback) throws CoreException {
        Boolean flgBeds = hasBeds != null && hasBeds.contains("true");
        boolean withoutChildrenFlg = withoutChildren != null ? withoutChildren : false;
        DepartmentsDataFilter filter = new DepartmentsDataFilter(flgBeds, false, withoutChildrenFlg);
        ListDataRequest request = new ListDataRequest(sortingField, sortingMethod, limit, page, filter);
        AllDepartmentsListData data = wsImpl.getAllDepartments(request);
        return new JSONWithPadding(data, callback);
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
     *
     * @param mkbId     Фильтр по идентификатору диагноза по МКВ. (В url: filter[mkbId]=...)
     * @param classId   Фильтр по идентификатору класса диагноза по МКВ. (В url: filter[classId]=...)
     * @param blockId   Фильтр по идентификатору блока диагноза по МКВ. (В url: filter[groupId]=...)
     * @param code      Фильтр по коду диагноза по МКВ. (В url: filter[code]=...)
     * @param diagnosis Фильтр по обозначению диагноза по МКВ. (В url: filter[diagnosis]=...)
     * @param view      Уровень визуализации. (В url: filter[view]=...)<pre>
     *                  &#15; Возможные значения:
     *                  &#15; Если фильтр не задан или имеет недопустимое значение - вывод МКВ структуры в виде дерева;
     *                  &#15; Иначе, если имеет следующие ключи:
     *                  &#15; "class" - вывод плоской структуры уровня класса МКВ;
     *                  &#15; "group" - вывод плоской структуры уровня блока МКВ;
     *                  &#15; "subgroup" - вывод плоской структуры уровня подблока МКВ;
     *                  &#15; "mkb" - вывод плоской структуры нижнего уровня МКВ;</pre>
     * @param display   Флаг указывающий отображать или нет свернутые фильтром ветки. Значения: true/false. (В url: filter[display]=...)
     * @param sex       Фильтр по половой принадлежности диагноза по МКВ. (В url: filter[sex]=...)
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws ru.korus.tmis.core.exception.CoreException
     * @see ru.korus.tmis.core.exception.CoreException
     */
    @GET
    @Path("/mkbs")
    @Produces({"application/javascript", "application/x-javascript"})
    public Object getAllActionTypeNames(@Context HttpServletRequest servRequest,
                                        @QueryParam("sortingField") String sortingField,
                                        @QueryParam("sortingMethod") String sortingMethod,
                                        @QueryParam("limit") int limit,
                                        @QueryParam("page") int page,
                                        @QueryParam("filter[mkbId]") int mkbId,
                                        @QueryParam("filter[classId]") String classId,
                                        @QueryParam("filter[groupId]") String blockId,
                                        @QueryParam("filter[code]") String code,
                                        @QueryParam("filter[diagnosis]") String diagnosis,
                                        @QueryParam("filter[view]") String view,
                                        @QueryParam("filter[display]") String display,
                                        @QueryParam("filter[sex]") int sex,
                                        @QueryParam("callback") String callback) throws CoreException {

        Boolean flgDisplay = display != null && display.contains("true");
        MKBListRequestDataFilter filter = new MKBListRequestDataFilter(mkbId, classId, blockId, code, diagnosis, view, flgDisplay, sex);
        ListDataRequest request = new ListDataRequest(sortingField, sortingMethod, limit, page, filter);
        return new JSONWithPadding(wsImpl.getAllMkbs(request, mkAuth(servRequest)), callback);
    }

    /**
     * Получение данных из динамических справочников плоских структур (FlatDirectory)<br>
     * В запросе используется один из фильтров filterRecordId, filter[X]=Y, filterValue
     *
     * @param includeMeta       Признак включения метаданных таблицы (FieldDescriptionList). Значения: yes/no
     * @param includeRecordList Признак включения списка записей справочника (RecordList). Значения: yes/no
     * @param includeFDRecord   Признак включения метаданных полей в записи (FieldDescription.Name и FieldDescription.Description). Значения: yes/no
     * @param filterValue       Фильтр для любого поля. Только один такой параметр в запросе.
     * @param limit             Максимальное количество выводимых элементов на странице. (По умолчанию: limit = 10).<br>
     *                          Limit общий для всех справочников, но распространяется на каждый справочник отдельно.
     * @param page              Номер выводимой страницы. (По умолчанию: page = 1).<br>
     *                          Page общий для всех справочников, но распространяется на каждый справочник отдельно.
     * @param info              Контекст информации о url-запросе.<pre>
     *                          &#15; Анализируются следующие параметры:
     *                          &#15; flatDirectoryId - Идентификатор справочника. Может быть несколько.
     *                          &#15; Если параметр не указан, выдаются все справочники.
     *                          &#15; filterRecordId - Идентификатор записи из таблицы FDRecord. Может быть несколько.
     *                          &#15; filter[X]=Y - Фильтр для поиска (выдаются только те записи, у которых поле с типом X содержит Y).
     *                          &#15; Таких параметров в одном запросе может быть несколько.
     *                          &#15; sortingField[X] = Y - Сортировки по полям X с порядком сортировки Y. Может быть несколько.</pre>
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws ru.korus.tmis.core.exception.CoreException
     * @link Подробное описание: https://docs.google.com/folder/d/0B-T1ZKDux1ZPYldTSU9BU2tSR0E/edit?pli=1&docId=1Eak6dN3AbSy1-JzYFD4SzLBpArV-44VqXOUpdqqVHnA
     * @see ru.korus.tmis.core.exception.CoreException
     */
    @GET
    @Path("/flatDirectory")
    @Produces({"application/javascript", "application/x-javascript"})
    public Object getFlatDirectories(@Context HttpServletRequest servRequest,
                                     @Context UriInfo info,
                                     @QueryParam("includeMeta") String includeMeta,
                                     @QueryParam("includeRecordList") String includeRecordList,
                                     @QueryParam("includeFDRecord") String includeFDRecord,
                                     @QueryParam("filterValue") String filterValue,
                                     @QueryParam("limit") int limit,
                                     @QueryParam("page") int page,
                                     @QueryParam("callback") String callback) throws CoreException {

        //hand-made url query params parsing
        MultivaluedMap<String, String> queryParams = info.getQueryParameters();
        String fullQueryPath = info.getRequestUri().getQuery();

        java.util.List<Integer> flatDictionaryIds = AuxiliaryFunctions.convertStringListTo(queryParams.get("flatDirectoryId"));
        java.util.List<Integer> filterRecordIds = AuxiliaryFunctions.convertStringListTo(queryParams.get("filterRecordId"));
        java.util.Map<Integer, java.util.List<String>> filterFields = AuxiliaryFunctions.foldFilterValueTo(queryParams, "filter[", "]");
        java.util.LinkedHashMap<Integer, Integer> sortingFieldIds = AuxiliaryFunctions.foldFilterValueToLinkedMapFromQuery(fullQueryPath, "sortingField[", "]=");

        boolean fields = ((filterFields != null) && filterFields.size() > 0);
        boolean values = ((filterValue != null) && (!filterValue.isEmpty()));
        boolean recordIds = ((filterRecordIds != null) && filterRecordIds.size() > 0);

        if ((fields && values) || (fields && recordIds) || (recordIds && values)) {
            throw new CoreException("Одновременно в запросе может использоваться только один тип фильтра");
        }
        FlatDirectoryRequestDataListFilter filter = new FlatDirectoryRequestDataListFilter(flatDictionaryIds,
                includeMeta, includeRecordList, includeFDRecord,
                filterFields, filterValue, filterRecordIds);
        FlatDirectoryRequestData request = new FlatDirectoryRequestData(sortingFieldIds, limit, page, filter);

        return new JSONWithPadding(wsImpl.getFlatDirectories(request), callback);
    }

    /**
     * Получение данных из Тезауруса.
     * <pre>
     * &#15; Возможные значения для сортировки:
     * &#15; "id" - по идентификатору записи (значение по умолчанию);
     * &#15; "groupId" - по идентификатору группы тезауруса;
     * &#15; "code" - по коду тезауруса;</pre>
     *
     * @param thesaurusId Фильтр по идентификатору тезауруса. (В url: filter[id]=...)
     * @param groupId     Фильтр по идентификатору группы тезауруса. (В url: filter[groupId]=...)
     * @param code        Фильтр по коду тезауруса. (В url: filter[code]=...)
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws CoreException
     * @see CoreException
     */
    @GET
    @Path("/thesaurus")
    @Produces({"application/javascript", "application/x-javascript"})
    public Object getThesaurus(@Context HttpServletRequest servRequest,
                               @QueryParam("sortingField") String sortingField,
                               @QueryParam("sortingMethod") String sortingMethod,
                               @QueryParam("limit") int limit,
                               @QueryParam("page") int page,
                               @QueryParam("filter[id]") int thesaurusId,
                               @QueryParam("filter[groupId]") String groupId,
                               @QueryParam("filter[code]") String code,
                               @QueryParam("callback") String callback) throws CoreException {
        ThesaurusListRequestDataFilter filter = new ThesaurusListRequestDataFilter(thesaurusId, groupId, code);
        ListDataRequest request = new ListDataRequest(sortingField, sortingMethod, limit, page, filter);
        return new JSONWithPadding(wsImpl.getThesaurusList(request, mkAuth(servRequest)), callback);
    }

    /**
     * Получение типов квот.
     * <pre>
     * &#15; Возможные значения для сортировки:
     * &#15; "id" - по идентификатору записи (значение по умолчанию);
     * &#15; "groupId" - по идентификатору группы;
     * &#15; "code" - по коду;</pre>
     *
     * @param typeId  Идентификатор типа квоты  (В url: filter[id]=...)
     * @param groupId Фильтр по идентификатору группы типов квот. (В url: filter[groupId]=...)
     * @param code    Фильтр по коду типа квоты. (В url: filter[code]=...)
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws CoreException
     * @see CoreException
     */
    @GET
    @Path("/quotaTypes")
    @Produces({"application/javascript", "application/x-javascript"})
    public Object getQuotaTypes(@QueryParam("sortingField") String sortingField,
                                @QueryParam("sortingMethod") String sortingMethod,
                                @QueryParam("limit") int limit,
                                @QueryParam("page") int page,
                                @QueryParam("filter[id]") int typeId,
                                @QueryParam("filter[code]") String code,
                                @QueryParam("filter[groupId]") String groupId,
                                @QueryParam("callback") String callback) throws CoreException {
        QuotaTypesListRequestDataFilter filter = new QuotaTypesListRequestDataFilter(typeId, code, groupId);
        ListDataRequest request = new ListDataRequest(sortingField, sortingMethod, limit, page, filter);
        return new JSONWithPadding(wsImpl.getQuotaTypes(request), callback);
    }

    /**
     * Сервис по получению списка обращений <br>
     * Путь: ../tms-registry/eventTypes
     * <pre>
     * &#15; Возможные значения для сортировки:
     * &#15; "id" - по торговому наименованию препората (значение по умолчанию);
     * &#15; "name" - по коду препората;</pre>
     *
     * @param requestType Идентификатор типа стационара rbRequestType.id
     * @param finance     Идентификатор типа оплаты rbFinance.id
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws CoreException
     */
    @GET
    @Path("/eventTypes")
    @Produces({"application/javascript", "application/x-javascript"})
    public Object getEventTypes(@Context HttpServletRequest servRequest,
                                @QueryParam("sortingField") String sortingField,
                                @QueryParam("sortingMethod") String sortingMethod,
                                @QueryParam("limit") int limit,
                                @QueryParam("page") int page,
                                @QueryParam("filter[requestType]") int requestType,
                                @QueryParam("filter[finance]") int finance,
                                @QueryParam("callback") String callback) throws CoreException {
        EventTypesListRequestDataFilter filter = new EventTypesListRequestDataFilter(finance, requestType);
        ListDataRequest request = new ListDataRequest(sortingField, sortingMethod, limit, page, filter);
        return new JSONWithPadding(wsImpl.getEventTypes(request, mkAuth(servRequest)), callback);
    }

    /**
     * Сервис получения данных о договорах, доступных в системе
     *
     * @param eventTypeId Если задан данный параметр, то будут возвращены доступные договора для данного типа событий
     * @param showDeleted Если если данный параметр задан как true, то в список будут включены и ранее удаленные договора
     * @param showExpired Если если данный параметр задан как true, то в список будут включены просроченные договора договора
     * @return
     */
    @GET
    @Path("/contracts")
    @Produces("application/x-javascript")
    public Object getContracts(
            @QueryParam("eventTypeId") int eventTypeId,
            @QueryParam("eventTypeCode") String eventTypeCode,
            @DefaultValue("false") @QueryParam("showDeleted") boolean showDeleted,
            @DefaultValue("false") @QueryParam("showExpired") boolean showExpired,
            @QueryParam("callback") String callback) {
        return new JSONWithPadding(wsImpl.getContracts(eventTypeId, eventTypeCode, showDeleted, showExpired), callback);
    }

    /**
     * Получение списка типа действий (ActionType's) по коду и/или идентификатору группы<br>
     *
     * @param patientId Идентификатор пациента
     *                  <pre>
     *                  &#15; Значения поля для сортировки:
     *                  &#15; "id" - по идентификатору типа действия;
     *                  &#15; "groupId" - по идентификатору группы типа действия;
     *                  &#15; "code" - по коду типа действия;
     *                  &#15; "name" - по обозначению типа действия;</pre>
     * @param groupId   Фильтр по идентификатору группы типа действия (s11r64.ActionType.group_id). (В url: filter[groupId]=...)
     * @param code      Фильтр по коду типа действия (s11r64.ActionType.code). (В url: filter[code]=...)
     * @param view      Фильтр по коду типа отображения информации. (В url: filter[view]=...)
     *                  &#15; Возможные значения:
     *                  &#15; "tree" - в виде дерева;
     *                  &#15; "flat" - в виде плоской структуры;</pre>
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws CoreException
     * @see CoreException
     */
    @GET
    @Path("/actionTypes")
    @Produces({"application/javascript", "application/x-javascript"})
    public Object getAllActionTypeNames(@Context HttpServletRequest servRequest,
                                        @Context UriInfo info,
                                        @QueryParam("sortingField") String sortingField,
                                        @QueryParam("sortingMethod") String sortingMethod,
                                        @QueryParam("limit") int limit,
                                        @QueryParam("page") int page,
                                        @QueryParam("patientId") int patientId,
                                        @QueryParam("filter[groupId]") int groupId,
                                        @QueryParam("filter[code]") String code,
                                        @QueryParam("filter[view]") String view,
                                        @QueryParam("showHidden") int showHidden,
                                        @QueryParam("filter[orgStruct]") int orgStructFilterEnable,
                                        @QueryParam("callback") String callback) throws CoreException {

        if (patientId < 1 && (view == null || !view.equals("tree")))
            throw new CoreException("GET-параметр patientId обязателен и не может быть меньше 1 если используется не filter[view]=tree");

        java.util.List<String> mnems = info.getQueryParameters().get("filter[mnem]");
        java.util.List<String> flatCodes = info.getQueryParameters().get("filter[flatCode]");

        java.util.List<String> mnemonics = new LinkedList<String>();

        if (mnems != null)
            for (String mnem : mnems)
                if (mnem != null && !mnem.equals("")) mnemonics.add(mnem);

        AuthData auth = mkAuth(servRequest);

        Integer orgStructId = auth.getUser() == null || auth.getUser().getOrgStructure() == null || orgStructFilterEnable == 0 ? null : auth.getUser().getOrgStructure().getId();

        ActionTypesListRequestDataFilter filter = new ActionTypesListRequestDataFilter(code, groupId, flatCodes, mnemonics, view, showHidden == 1, orgStructId);

        ListDataRequest request = new ListDataRequest(sortingField, sortingMethod, limit, page, filter);
        final Object res = (view != null && view.equals("tree")) ? wsImpl.getListOfActionTypes(request)
                : wsImpl.getListOfActionTypeIdNames(request, patientId);
        return new JSONWithPadding(res, callback);
    }

    /**
     * Запрос на структуру json для первичного осмотра.
     * &#15; Внимание! В логике сервиса параметр не используется.</pre>
     *
     * @param actionTypeId Идентификатор типа действия
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws ru.korus.tmis.core.exception.CoreException
     * @see ru.korus.tmis.core.exception.CoreException
     */
    @GET
    @Path("/actionTypes/{id}")
    @Produces({"application/javascript", "application/x-javascript"})
    public Object getStructOfPrimaryMedExam(@Context HttpServletRequest servRequest,
                                            @PathParam("id") int actionTypeId,
                                            @QueryParam("actionId") Integer actionId,
                                            @QueryParam("eventId") int eventId,
                                            @QueryParam("callback") String callback) throws CoreException {
        return new JSONWithPadding(wsImpl.getStructOfPrimaryMedExam(actionTypeId, actionId, eventId, mkAuth(servRequest)), callback);
    }

    /**
     * Запрос на получение справочника разметки полей медицинского документа
     *
     * @return Справочник в json-формате
     */
    @GET
    @Path("/layoutAttributes/")
    @Produces({"application/javascript", "application/x-javascript"})
    public Object getLayoutAttributes(@QueryParam("callback") String callback) throws CoreException {
        return new JSONWithPadding(wsImpl.getLayoutAttributes(), callback);
    }

    /**
     * Метод получения действия по условию
     */
    @GET
    @Path("/actionsByParams")
    @Produces({"application/javascript", "application/x-javascript"})
    public Object getActionByParams(@Context HttpServletRequest servRequest,
                                    @QueryParam("sortingField") String sortingField,
                                    @QueryParam("sortingMethod") String sortingMethod,
                                    @QueryParam("limit") int limit,
                                    @QueryParam("page") int page,
                                    @QueryParam("filter[mnem]") List<String> mnem,
                                    @QueryParam("eventId") int eventId,
                                    @QueryParam("callback") String callback) throws CoreException {
        DiagnosticsListRequestDataFilter filter = new DiagnosticsListRequestDataFilter(null,
                eventId,
                0,
                null,
                0,
                0,
                null,
                0,
                (short) -1,
                null,
                mnem,
                (short) -1);

        DiagnosticsListRequestData requestData = new DiagnosticsListRequestData(sortingField, sortingMethod, limit, page, filter);
        return new JSONWithPadding(wsImpl.getListOfDiagnosticsForPatientByEvent(requestData, mkAuth(servRequest)), callback);
    }


    //__________________________________________________________________________________________________________________
    //***********************************   ВСПОМОГАТЕЛЬНЫЕ МЕТОДЫ   ***********************************
    //__________________________________________________________________________________________________________________

    /**
     * Функциональность данного класса под сомнением, мнемоники должны быть оторваны от ядра
     * (за исключением редких случаев и в этих случаях имена мнемоник должны храниться в файлах *.properties)
     * Настоятельно не рекомендую пользоваться этим классом. По мере исправления исходного кода, его использующего,
     * он будет удален.
     */
    @Deprecated
    public enum ActionTypesSubType {

        ALL("") {
            public String getSubType() {
                return "all";
            }

            public String getMnemonic() {
                return "";
            }
        },
        LABORATORY("laboratory") {
            public String getSubType() {
                return "laboratory";
            }

            public String getMnemonic() {
                return "LAB";
            }
        },
        INSTRUMENTAL("instrumental") {
            public String getSubType() {
                return "instrumental";
            }

            public String getMnemonic() {
                return "DIAG";
            }
        },
        CONSULTATIONS("consultations") {
            public String getSubType() {
                return "consultations";
            }

            public String getMnemonic() {
                return "CONS";
            }
        },
        CONSULTATIONS_POLY("consultations_poly") {
            public String getSubType() {
                return "consultations_poly";
            }

            public String getMnemonic() {
                return "CONS_POLY";
            }
        },
        LAB("lab") {
            public String getSubType() {
                return "laboratory";
            }

            public String getMnemonic() {
                return "LAB";
            }
        },
        BAK_LABORATORY("bak_lab") {
            public String getSubType() {
                return "laboratory";
            }

            public String getMnemonic() {
                return "BAK_LAB";
            }
        },
        DIAG("diag") {
            public String getSubType() {
                return "instrumental";
            }

            public String getMnemonic() {
                return "DIAG";
            }
        },
        CONS("cons") {
            public String getSubType() {
                return "consultations";
            }

            public String getMnemonic() {
                return "CONS";
            }
        },
        EXAM("exam") {
            public String getSubType() {
                return "examinations";
            }

            public String getMnemonic() {
                return "EXAM";
            }
        },
        JOUR_OLD("jour_old") {
            public String getSubType() {
                return "journal old";
            }

            public String getMnemonic() {
                return "JOUR_OLD";
            }
        },
        EXAM_OLD("exam_old") {
            public String getSubType() {
                return "examinations old";
            }

            public String getMnemonic() {
                return "EXAM_OLD";
            }
        },
        JOUR("jour") {
            public String getSubType() {
                return "journal";
            }

            public String getMnemonic() {
                return "JOUR";
            }
        },
        ORD("ord") {
            public String getSubType() {
                return "ordering";
            }

            public String getMnemonic() {
                return "ORD";
            }
        },
        EPI("epi") {
            public String getSubType() {
                return "epicrisis";
            }

            public String getMnemonic() {
                return "EPI";
            }
        },
        OTH("oth") {
            public String getSubType() {
                return "OTH";
            }   //хз че это

            public String getMnemonic() {
                return "OTH";
            }
        },
        NOT("not") {
            public String getSubType() {
                return "NOT";
            }

            public String getMnemonic() {
                return "NOT";
            }
        },
        THER("ther") {
            public String getSubType() {
                return "THER";
            }

            public String getMnemonic() {
                return "THER";
            }
        },
        ANEST("anest") {
            public String getSubType() {
                return "ANEST";
            }

            public String getMnemonic() {
                return "ANEST";
            }
        };

        public abstract String getSubType();

        public abstract String getMnemonic();

        private String typeValue;

        private ActionTypesSubType(String type) {
            typeValue = type;
        }

        static public ActionTypesSubType getType(String pType) {
            for (ActionTypesSubType type : ActionTypesSubType.values()) {
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

    private AuthData mkAuth(HttpServletRequest servRequest) {
        return wsImpl.checkTokenCookies(Arrays.asList(servRequest.getCookies()));
    }

}
