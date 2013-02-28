package ru.korus.tmis.ws.webmis.rest;

import java.io.Serializable;
import java.util.*;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.interceptor.Interceptors;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

//import org.json.JSONArray;
//import org.json.JSONObject;
import ru.korus.tmis.auxiliary.AuxiliaryFunctions;
import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.auth.AuthToken;
import ru.korus.tmis.core.data.*;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.core.logging.slf4j.interceptor.ServicesLoggingInterceptor;
import ru.korus.tmis.lis.data.BiomaterialInfo;
import ru.korus.tmis.ws.impl.MedipadWSImpl;

import com.sun.jersey.api.json.JSONWithPadding;

/**
 * Сервисы для работы с ядром TMIS посредством Web-клиента
 */
@Interceptors(ServicesLoggingInterceptor.class)
@Singleton
@Path("/tms-registry/")
@Produces("application/json")
public class PatientRegistryRESTImpl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
    MedipadWSImpl wsImpl;


    /**
     * Получение списка пациентов.
     */
	@GET
    @Path("/patients")
    @Produces("application/x-javascript")
    public Object getAllPatientsP(@QueryParam("limit")String limit,
                                  @QueryParam("page")String  page,
                                  @QueryParam("sortingField")String sortingField,
                                  @QueryParam("sortingMethod")String sortingMethod,
                                  @QueryParam("filter[patientCode]")String  patientCode,
                                  @QueryParam("filter[fullName]")String fullName,
                                  @QueryParam("filter[birthDate]")Long birthDate,
                                  @QueryParam("filter[document]")String document,
                                  @QueryParam("filter[withRelations]")String withRelations,
                                  @QueryParam("callback") String callback,
                                  @Context HttpServletRequest servRequest) {
        AuthData auth = wsImpl.checkTokenCookies(servRequest);

        Date bDate = birthDate == null ? null : new Date(birthDate);
        PatientRequestData requestData = new PatientRequestData(patientCode, fullName, bDate, document, withRelations, sortingField, sortingMethod, limit, page);
        JSONWithPadding returnValue = new JSONWithPadding(wsImpl.getAllPatients(requestData, auth), callback);
    	return returnValue;
    }

    /**
     * Получение данных о пациенте по идентификатору.
     * @param id Идентификатор пациента, данные о котором запрашиваются.
     * @param callback  callback запроса.
     * @param servRequest Контекст запроса.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws CoreException
     * @see CoreException
     * @see PatientCardData
     */
    @GET
    @Path("/patients/{id}")
    @Produces("application/x-javascript")
    public Object getPatientById(@PathParam("id")int id,
                                 @QueryParam("callback") String callback,
                                 @Context HttpServletRequest servRequest) {
        AuthData auth = wsImpl.checkTokenCookies(servRequest);
        JSONWithPadding returnValue = new JSONWithPadding(wsImpl.getPatientById(id, auth), callback);
        return returnValue;
    }

    /**
     * Редактирование данных о пациенте.
     * @param patientData Структура PatientCardData с данными о пациенте.
     * @param id  Идентификатор пациента, данные о котором редактируются.
     * @param callback  callback запроса.
     * @param servRequest Контекст запроса.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws CoreException
     * @see CoreException
     * @see PatientCardData
     */
    @PUT
    @Path("/patients/{id}")
    @Consumes("application/json")
    @Produces("application/x-javascript")
    public Object updatePatient(PatientCardData patientData,
                                 @PathParam("id")int id,
                                 //@QueryParam("token") String token,
                                 @QueryParam("callback") String callback,
                                 @Context HttpServletRequest servRequest) {
        AuthData auth = wsImpl.checkTokenCookies(servRequest);
        //AuthToken authToken = new AuthToken(token);
        //AuthData auth = wsImpl.getStorageAuthData(authToken);
        JSONWithPadding returnValue = new JSONWithPadding(wsImpl.updatePatient(patientData, auth), callback);
        return returnValue;
    }

    /**
     * Запись данных о новом пациенте.
     * @param patientData Структура PatientCardData с данными о пациенте.
     * @param callback  callback запроса.
     * @param servRequest Контекст запроса.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws CoreException
     * @see CoreException
     * @see PatientCardData
     */
    @POST
    @Path("/patients")
    @Consumes("application/json")
    @Produces("application/x-javascript")
    public Object insertPatient(PatientCardData patientData,
                                //@QueryParam("token") String token,
                                @QueryParam("callback") String callback,
                                @Context HttpServletRequest servRequest) {
        AuthData auth = wsImpl.checkTokenCookies(servRequest);
        //AuthToken authToken = new AuthToken(token);
        //AuthData auth = wsImpl.getStorageAuthData(authToken);
        JSONWithPadding returnValue = new JSONWithPadding(wsImpl.insertPatient(patientData, auth), callback);
        return returnValue;
    }

    //Запросы по обращениям

    /**
     * Создание обращения на госпитализацию.
     * @param data структура AppealData c данными о госпитализации.
     * @param patientId Идентификатор пациента.
     * @param callback  callback запроса.
     * @param servRequest Контекст запроса.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws CoreException
     * @see AppealData
     * @see CoreException
     */
    @POST
    @Path("/patients/{id}/appeals")
    @Consumes("application/json")
    @Produces("application/x-javascript")
    public Object insertPatientAppeal(AppealData data,
                                         @PathParam("id")int patientId,
                                         @QueryParam("token") String token,
                                         @QueryParam("callback") String callback,
                                         @Context HttpServletRequest servRequest) {
        //AuthData auth = wsImpl.checkTokenCookies(servRequest);
        AuthToken authToken = new AuthToken(token);
        AuthData auth = wsImpl.getStorageAuthData(authToken);

        String oip = wsImpl.insertAppealForPatient(data, patientId, auth);
        JSONWithPadding returnValue = new JSONWithPadding(oip, callback);
        return returnValue;
    }

    /**
     * Редактирование обращения на госпитализацию
     * @param data структура AppealData c данными о госпитализации.
     * @param patientId Идентификатор пациента.
     * @param callback  callback запроса.
     * @param servRequest Контекст запроса.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws CoreException
     * @see CoreException
     */
    @PUT
    @Path("/patients/{id}/appeals")
    @Consumes("application/json")
    @Produces("application/x-javascript")
    public Object updatePatientAppeal(AppealData data,
                                               @PathParam("id")int patientId,
                                               //@QueryParam("token") String token,
                                               @QueryParam("callback") String callback,
                                               @Context HttpServletRequest servRequest) {
        AuthData auth = wsImpl.checkTokenCookies(servRequest);
        //AuthToken authToken = new AuthToken(token);
        //AuthData auth = wsImpl.getStorageAuthData(authToken);

        String oip = wsImpl.insertAppealForPatient(data, patientId, auth);
        JSONWithPadding returnValue = new JSONWithPadding(oip, callback);
        return returnValue;
    }

    /**
     * Данные об обращения на госпитализацию по идентификатору.
     * @param id Идентификатор обращения на госпитализацию.
     * @param callback  callback запроса.
     * @param servRequest Контекст запроса.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws CoreException
     * @see CoreException
     */
    @GET
    @Path("/appeals/{id}")
    @Produces("application/x-javascript")
    public Object getAppealById(@PathParam("id")int id,
                                @QueryParam("callback") String callback,
                                @Context HttpServletRequest servRequest) {
        AuthData auth = wsImpl.checkTokenCookies(servRequest);

        String oip = wsImpl.getAppealById(id, auth);
        JSONWithPadding returnValue = new JSONWithPadding(oip, callback);
        return returnValue;
    }

    /**
     * Печатная форма обращения на госпитализацию по идентификатору.
     * @param id Идентификатор обращения на госпитализацию.
     * @param callback  callback запроса.
     * @param servRequest Контекст запроса.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws CoreException
     * @see CoreException
     */
    @GET
    @Path("print/appeals/{id}")
    @Produces("application/x-javascript")
    public Object getAppealPrintFormById(@PathParam("id")int id,
                                @QueryParam("callback") String callback,
                                @Context HttpServletRequest servRequest) {
        AuthData auth = wsImpl.checkTokenCookies(servRequest);

        String oip = wsImpl.getAppealPrintFormById(id, auth);
        JSONWithPadding returnValue = new JSONWithPadding(oip, callback);
        return returnValue;
    }

    //Проверка на наличие номера чего-либо (госпитализации, полиса, СНИЛС) в БД
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
     * @param callback  callback запроса.
     * @param servRequest Контекст запроса.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws CoreException
     * @see CoreException
     */
    @GET
    @Path("checkExistance/{name}")
    @Produces("application/x-javascript")
    public Object checkAppealNumber(@PathParam("name") String name,
                                    @QueryParam("typeId")int typeId,
                                    @QueryParam("number")String number,
                                    @QueryParam("serial")String serial,
                                    @QueryParam("callback") String callback,
                                    @Context HttpServletRequest servRequest) {
        wsImpl.checkTokenCookies(servRequest);
        TrueFalseContainer oip = wsImpl.checkExistanceNumber(name, typeId, number, serial);
        JSONWithPadding returnValue = new JSONWithPadding(oip, callback);
        return returnValue;
    }

    /**
     * Запрос перечня обращений по пациенту (История госпитализаций).
     * @param patientId Идентификатор пациента.
     * @param limit Максимальное количество выводимых элементов в списке.
     * @param page Номер выводимой страницы.
     * @param sortingField Наименование поля для сортировки.<pre>
     * &#15; Возможные значения:
     * &#15; "id" - по идентификатору (значение по умолчанию);
     * &#15; "start" | "begDate" - по дате начала госпитализации;
     * &#15; "end" | "endDate" - по дате закрытия(отказа) госпитализации;
     * &#15; "doctor" - по ФИО специалиста;
     * &#15; "department" - по отделению;
     * &#15; "number" - по номеру истории болезни(НИБ);
     * &#15; "diagnosis" - по диагнозу;</pre>
     * @param sortingMethod Метод сортировки.<pre>
     * &#15; Возможные значения:
     * &#15; "asc" - по возрастанию (значение по умолчанию);
     * &#15; "desc" - по убыванию;</pre>
     * @param number  Фильтр значений по НИБ.
     * @param beginDate Фильтр значений по дате начала госпитализации.
     * @param endDate Фильтр значений по дате закрытия госпитализации.
     * @param departmentId Фильтр значений по идентификатору отделения.
     * @param doctorId Фильтр значений по идентификатору доктора.
     * @param mkbCode Фильтр значения по МКБ-коду диагноза.
     * @param callback  callback запроса.
     * @param servRequest Контекст запроса.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws CoreException
     * @see CoreException
     */
    @GET
    @Path("/patients/{id}/appeals")
    @Produces("application/x-javascript")
    public Object getAllAppealsForPatient(@PathParam("id")int patientId,
                                          @QueryParam("limit")int limit,
                                          @QueryParam("page")int  page,
                                          @QueryParam("sortingField")String sortingField,  //сортировки вкл.
                                          @QueryParam("sortingMethod")String sortingMethod,
                                          @QueryParam("filter[number]")String number,
                                          @QueryParam("filter[beginDate]")long beginDate,
                                          @QueryParam("filter[endDate]")long endDate,
                                          @QueryParam("filter[departmentId]") int departmentId,
                                          @QueryParam("filter[doctorId]") int doctorId,
                                          @QueryParam("filter[diagnosis]") String mkbCode,
                                          @QueryParam("callback") String callback,
                                          @Context HttpServletRequest servRequest) {
        AuthData auth = wsImpl.checkTokenCookies(servRequest);

        Set<String> codes = new LinkedHashSet<String>();
        AppealSimplifiedRequestDataFilter filter = new AppealSimplifiedRequestDataFilter(patientId, beginDate, endDate, departmentId, doctorId, mkbCode, number, codes);
        AppealSimplifiedRequestData request= new AppealSimplifiedRequestData(sortingField, sortingMethod, limit, page, filter);

        AppealSimplifiedDataList appealList  = (AppealSimplifiedDataList) wsImpl.getAllAppealsByPatient(request, auth);
        JSONWithPadding returnValue = new JSONWithPadding(appealList, callback);
        return returnValue;
    }
                              /*
    //Основные сведения истории болезни
    /*@GET
    @Path("/patients/{id}/appeals/basicinfo")
    @Produces("application/x-javascript")
    public Object getBasicInfoOfDiseaseHistory(@PathParam("id") int patientId,
                                               @QueryParam("externalId") String externalId,
                                               @QueryParam("callback") String callback){

        return wsImpl.getBasicInfoOfDiseaseHistory(patientId, externalId).toString();
    }  */


    /**
     * Запрос на список поступивших.
     * @param limit Максимальное количество выводимых элементов в списке.
     * @param page Номер выводимой страницы.
     * @param sortingField Наименование поля для сортировки.<pre>
     * &#15; Возможные значения:
     * &#15; "id" - по идентификатору (значение по умолчанию);
     * &#15; "createDatetime" - по дате начала госпитализации;
     * &#15; "birthDate" - по дате рождения пациента;
     * &#15; "fullName" - по ФИО пациента;
     * &#15; "number" - по номеру истории болезни(НИБ);</pre>
     * @param sortingMethod Метод сортировки.<pre>
     * &#15; Возможные значения:
     * &#15; "asc" - по возрастанию (значение по умолчанию);
     * &#15; "desc" - по убыванию;</pre>
     * @param role  Идентификатор роли.
     * @param eventId Фильтр значений по идентификатору обращения.
     * @param fullName Фильтр значений по ФИО пациента.
     * @param birthDate Фильтр значений по дате рождения пациента.
     * @param externalId Фильтр значений по номеру истории болезни (НИБ).
     * @param beginDate Фильтр значений по дате начала госпитализации.
     * @param endDate Фильтр значений по дате закрытия госпитализации.<pre>
     * &#15; Обратите внимание, сервис реализует следующие возможности:
     * &#15; -- поступившие на день: задать только beginDate фильтр
     * &#15; -- за период: задать оба фильтра - beginDate и endDate
     * &#15; -- закр. госпитализация на день: задатиь только endDate фильтр</pre>
     * @param mkbCode Фильтр значения по МКБ-коду направительного диагноза.
     * @param callback  callback запроса.
     * @param servRequest Контекст запроса.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws CoreException
     * @see CoreException
     */
    @GET
    @Path("/appeals")
    @Produces("application/x-javascript")
    public Object getAllAppealsForReceivedPatientByPeriod(@QueryParam("limit")int limit,
                                                          @QueryParam("page")int  page,
                                                          @QueryParam("sortingField")String sortingField,    //сортировки вкл.
                                                          @QueryParam("sortingMethod")String sortingMethod,
                                                          @QueryParam("roleId")int role,                        //Временно, (замена взятия роли из авторизации)
                                                          @QueryParam("filter[eventId]")int  eventId,
                                                          @QueryParam("filter[fullName]")String fullName,
                                                          @QueryParam("filter[birthDate]")Long birthDate,
                                                          @QueryParam("filter[externalId]")String externalId,
                                                          @QueryParam("filter[beginDate]")Long beginDate,
                                                          @QueryParam("filter[endDate]")Long endDate,
                                                          @QueryParam("filter[diagnosis]") String mkbCode,     //фильтруем по коду или наименованию (направительный диагноз в действии - первичный осмотр(при госпитализации))
                                                          @QueryParam("callback") String callback,
                                                          @Context HttpServletRequest servRequest) {
        AuthData auth = wsImpl.checkTokenCookies(servRequest);

        ReceivedRequestDataFilter filter = new ReceivedRequestDataFilter(eventId, fullName, birthDate, externalId, beginDate, endDate, mkbCode, role);
        ReceivedRequestData requestData = new ReceivedRequestData(sortingField, sortingMethod, limit, page, filter);

        Object rpd = wsImpl.getAllAppealsForReceivedPatientByPeriod(requestData, auth);
        JSONWithPadding returnValue = new JSONWithPadding(rpd, callback);
        return returnValue;
    }

    /**
     * Запрос на список обращений пациентов для отделения и/или врача.
     * Роль: врач отделения
     * @param limit Максимальное количество выводимых элементов в списке.
     * @param page Номер выводимой страницы.
     * @param sortingField Наименование поля для сортировки.<pre>
     * &#15; Возможные значения:
     * &#15; "id" - по идентификатору (значение по умолчанию);
     * &#15; "createDatetime" | "start" | "begDate" - по дате начала госпитализации;
     * &#15; "end" | "endDate" - по дате конца госпитализации;
     * &#15; "doctor" - по ФИО доктора;
     * &#15; "department" - по наименованию отделения;
     * &#15; "bed" - по обозначению койки;
     * &#15; "number" - по номеру истории болезни(НИБ);
     * &#15; "fullName" - по ФИО пациента;
     * &#15; "birthDate" - по дате рождения пациента;</pre>
     * @param sortingMethod Метод сортировки.<pre>
     * &#15; Возможные значения:
     * &#15; "asc" - по возрастанию (значение по умолчанию);
     * &#15; "desc" - по убыванию;</pre>
     * @param endDate Фильтр значений по дате закрытия госпитализации.
     * @param departmentId Фильтр значений по отделению (по умолчанию берется из данных авторизации).
     * @param callback  callback запроса.
     * @param servRequest Контекст запроса.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws CoreException
     * @see CoreException
     */
    @GET
    @Path("/departments/patients")
    @Produces("application/x-javascript")
    public Object getAllPatientsForDepartmentOrUserByPeriod(@QueryParam("limit")int limit,
                                                            @QueryParam("page")int  page,
                                                            @QueryParam("sortingField")String sortingField,     //сортировки вкл
                                                            @QueryParam("sortingMethod")String sortingMethod,
                                                            @QueryParam("filter[date]")long endDate,
                                                            @QueryParam("filter[departmentId]") int departmentId,
                                                            @QueryParam("callback") String callback,
                                                            @Context HttpServletRequest servRequest) {

        AuthData auth = wsImpl.checkTokenCookies(servRequest);
        int depId = (departmentId>0) ? departmentId : auth.getUser().getOrgStructure().getId().intValue();

        PatientsListRequestData requestData = new PatientsListRequestData (depId,
                                                                           auth.getUser().getId().intValue(),
                                                                           auth.getUserRole().getId().intValue(),
                                                                           endDate,
                                                                           sortingField,
                                                                           sortingMethod,
                                                                           limit,
                                                                           page);
        Object rpd = wsImpl.getAllPatientsForDepartmentIdAndDoctorIdByPeriod(requestData, auth);
        JSONWithPadding returnValue = new JSONWithPadding(rpd, callback);
        return returnValue;
    }

    /**
     * Запрос на структуру json для первичного осмотра.
     * @param eventId Идентификатор обращения.<pre>
     * &#15; Внимание! В логике сервиса параметр не используется.</pre>
     * @param actionTypeId Идентификатор типа действия
     * @param callback  callback запроса.
     * @param servRequest Контекст запроса.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws CoreException
     * @see CoreException
     */
    @GET
    @Path("appeals/{eventId}/examinations/struct/{actionTypeId}")
    @Produces("application/x-javascript")
    public Object getStructOfPrimaryMedExam(@QueryParam("callback") String callback,
                                            @PathParam("eventId") int eventId,
                                            @PathParam("actionTypeId") int actionTypeId,
                                            @Context HttpServletRequest servRequest) {
        AuthData auth = wsImpl.checkTokenCookies(servRequest);
        JSONWithPadding returnValue = new JSONWithPadding(wsImpl.getStructOfPrimaryMedExam(actionTypeId, auth), callback);
        return returnValue;
    }

    /**
     * Запрос на структуру для первичного осмотра с копированием данных из предыдущего первичного осмотра
     * @param eventId Идентификатор обращения, из которого берется копируемый осмотр.
     * @param callback  callback запроса.
     * @param servRequest Контекст запроса.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws CoreException
     * @see CoreException
     */
    @GET
    @Path("appeals/{eventId}/examinations/structWithCopy/{actionTypeId}")
    @Produces("application/x-javascript")
    public Object getStructOfPrimaryMedExamWithCopy(@QueryParam("callback") String callback,
                                                    @PathParam("eventId") int eventId,
                                                    @PathParam("actionTypeId") int actionTypeId,
                                                    @Context HttpServletRequest servRequest) {
        AuthData auth = wsImpl.checkTokenCookies(servRequest);
        JSONWithPadding returnValue = new JSONWithPadding(wsImpl.getStructOfPrimaryMedExamWithCopy(actionTypeId, auth, eventId), callback);
        return returnValue;
    }

    /**
     * Создание первичного осмотра
     * @param data Json c данными о первичном осмотре как JSONCommonData
     * @param eventId Идентификатор обращения на госпитализацию, для которого создается первичный осмотр.
     * @param callback  callback запроса.
     * @param servRequest Контекст запроса.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws CoreException
     * @see CoreException
     * @see JSONCommonData
     */
    @POST
    @Path("appeals/{eventId}/examinations")
    @Consumes("application/json")
    @Produces("application/x-javascript")
    public  Object insertPrimaryMedExamForPatient(JSONCommonData data,
                                                  @PathParam("eventId") int eventId,
                                                  @QueryParam("callback") String callback,
                                                  //@QueryParam("token") String token,
                                                  @Context HttpServletRequest servRequest) {
        AuthData auth = wsImpl.checkTokenCookies(servRequest);
        //AuthToken authToken = new AuthToken(token);
        //AuthData auth = wsImpl.getStorageAuthData(authToken);

        JSONWithPadding returnValue = new JSONWithPadding(wsImpl.insertPrimaryMedExamForPatient(eventId, data, auth), callback);
        return returnValue;
    }

    /**
     * Редактирование первичного осмотра
     * @param data Json c данными о первичном осмотре как JSONCommonData
     * @param eventId Идентификатор обращения на госпитализацию.<pre>
     * &#15; Внимание! В логике сервиса параметр не используется.</pre>
     * @param actionId Идентификатор редактируемого осмотра.
     * @param callback  callback запроса.
     * @param servRequest Контекст запроса.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws CoreException
     * @see CoreException
     * @see JSONCommonData
     */
    @PUT
    @Path("appeals/{eventId}/examinations/{actionId}")
    @Consumes("application/json")
    @Produces("application/x-javascript")
    public  Object modifyPrimaryMedExamForPatient(JSONCommonData data,
                                                  @PathParam("eventId") int eventId,
                                                  @PathParam("actionId") int actionId,
                                                  @QueryParam("callback") String callback,
                                                  //@QueryParam("token") String token,
                                                  @Context HttpServletRequest servRequest) {
        AuthData auth = wsImpl.checkTokenCookies(servRequest);
        //AuthToken authToken = new AuthToken(token);
        //AuthData auth = wsImpl.getStorageAuthData(authToken);

        JSONWithPadding returnValue = new JSONWithPadding(wsImpl.modifyPrimaryMedExamForPatient(actionId, data, auth), callback);
        return returnValue;
    }

    /**
     * Запрос на получение данных о первичном осмотре
     * @param eventId Идентификатор обращения на госпитализацию.<pre>
     * &#15; Внимание! В логике сервиса параметр не используется.</pre>
     * @param actionId Идентификатор первичного осмотра.
     * @param callback  callback запроса.
     * @param servRequest Контекст запроса.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws CoreException
     * @see CoreException
     */
    @GET
    @Path("appeals/{eventId}/examinations/{actionId}")
    @Produces("application/x-javascript")
    public Object getPrimaryMedExamById(@PathParam("eventId") int eventId,
                                        @PathParam("actionId")int actionId,
                                        @QueryParam("callback") String callback,
                                        @Context HttpServletRequest servRequest) {
        AuthData auth = wsImpl.checkTokenCookies(servRequest);
        JSONWithPadding returnValue = new JSONWithPadding(wsImpl.getPrimaryAssessmentById(actionId, auth), callback);
        return returnValue;
    }

    /**
     * Запрос перечня осмотров по пациенту (просмотр перечня протоколов)
     * @param limit Максимальное количество выводимых элементов в списке.
     * @param page Номер выводимой страницы.
     * @param sortingField Наименование поля для сортировки.<pre>
     * &#15; Возможные значения:
     * &#15; "id" - по идентификатору осмотра (значение по умолчанию);
     * &#15; "assessmentDate"| "start" | "createDatetime" - по дате осмотра;
     * &#15; "doctor" - по ФИО доктора;
     * &#15; "department" - по наименованию отделения врача;
     * &#15; "specs" - по специальности врача;</pre>
     * @param sortingMethod Метод сортировки.<pre>
     * &#15; Возможные значения:
     * &#15; "asc" - по возрастанию (значение по умолчанию);
     * &#15; "desc" - по убыванию;</pre>
     * @param eventId Идентификатор обращения на госпитализацию.
     * @param assessmentTypeCode Фильтр значений по коду типа осмотра.
     * @param assessmentDate Фильтр значений по дате осмотра.
     * @param doctorName Фильтр значений по ФИО врача.
     * @param speciality Фильтр значений по специальности врача.
     * @param assessmentName Фильтр значений по наименованию осмотра.
     * @param departmentName Фильтр значений по наименованию отделения.
     * @param callback  callback запроса.
     * @param servRequest Контекст запроса.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws CoreException
     * @see CoreException
     */
    @GET
    @Path("appeals/{id}/examinations/")
    @Produces("application/x-javascript")
    public Object getListOfAssessmentsForPatientByEvent(@PathParam("id") int eventId,
                                                        @QueryParam("limit")int limit,
                                                        @QueryParam("page")int  page,
                                                        @QueryParam("sortingField")String sortingField,    //сортировки вкл.
                                                        @QueryParam("sortingMethod")String sortingMethod,
                                                        @QueryParam("filter[code]")String  assessmentTypeCode,
                                                        @QueryParam("filter[assessmentDate]") long assessmentDate,
                                                        @QueryParam("filter[doctorName]") String doctorName,
                                                        @QueryParam("filter[speciality]") String speciality,
                                                        @QueryParam("filter[assessmentName]") String assessmentName,
                                                        @QueryParam("filter[departmentName]") String departmentName,
                                                        @QueryParam("callback") String callback,
                                                        @Context HttpServletRequest servRequest) {
        AuthData auth = wsImpl.checkTokenCookies(servRequest);

        AssessmentsListRequestDataFilter filter = new AssessmentsListRequestDataFilter(assessmentTypeCode, assessmentDate, doctorName, speciality, assessmentName, departmentName);
        AssessmentsListRequestData alrd= new AssessmentsListRequestData(sortingField, sortingMethod, limit, page, /*assessmentTypeCode, patientId,*/ eventId, filter);
        AssessmentsListData assList = wsImpl.getListOfAssessmentsForPatientByEvent(alrd, auth);
        JSONWithPadding returnValue = new JSONWithPadding(assList, callback);
        return returnValue;
    }

    /**
     * Получение данных об осмотре (просмотр протокола осмотра)
     * @param actionId Идентификатор осмотра.
     * @param callback  callback запроса.
     * @param servRequest Контекст запроса.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws CoreException
     * @see CoreException
     */
    @GET
    @Path("/examinations/{actionId}")
    @Produces("application/x-javascript")
    public Object getStructOfPrimaryMedExam(@PathParam("actionId")int actionId,
                                            @QueryParam("callback") String callback,
                                            @Context HttpServletRequest servRequest) {
        AuthData auth = wsImpl.checkTokenCookies(servRequest);
        JSONWithPadding returnValue = new JSONWithPadding(wsImpl.getPrimaryAssessmentById(actionId, auth), callback);
        return returnValue;
    }

    //КОЕЧНЫЙ ФОНД

    /**
     * Регистрация на койке
     * @param data Json c данными о койке как HospitalBedData
     * @param eventId Идентификатор обращения на госпитализацию.
     * @param callback  callback запроса.
     * @param servRequest Контекст запроса.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws CoreException
     * @see CoreException
     * @see HospitalBedData
     */
    @POST
    @Path("appeals/{eventId}/hospitalbed/")
    @Consumes("application/json")
    @Produces("application/x-javascript")
    public  Object registryPatientToHospitalBed(  HospitalBedData data,
                                                  @PathParam("eventId") int eventId,
                                                  @QueryParam("callback") String callback,
                                                  //@QueryParam("token") String token,
                                                  @Context HttpServletRequest servRequest) {
        AuthData auth = wsImpl.checkTokenCookies(servRequest);
        //AuthToken authToken = new AuthToken(token);
        //AuthData auth = wsImpl.getStorageAuthData(authToken);

        Object oip = wsImpl.registryPatientToHospitalBed(eventId, data, auth);
        JSONWithPadding returnValue = new JSONWithPadding(oip, callback);
        return returnValue;
    }

    /**
     * Регистрация на койке (редактирование)
     * @param data Json c данными о койке как HospitalBedData
     * @param actionId Идентификатор редактируемого действия типа 'Движение'.
     * @param callback  callback запроса.
     * @param servRequest Контекст запроса.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws CoreException
     * @see CoreException
     * @see HospitalBedData
     */
    @PUT
    @Path("/hospitalbed/{actionId}")
    @Consumes("application/json")
    @Produces("application/x-javascript")
    public  Object modifyPatientToHospitalBed(HospitalBedData data,
                                                @PathParam("actionId")int actionId,
                                                @QueryParam("callback") String callback,
                                                //@QueryParam("token") String token,
                                                @Context HttpServletRequest servRequest) {
        AuthData auth = wsImpl.checkTokenCookies(servRequest);
        //AuthToken authToken = new AuthToken(token);
        //AuthData auth = wsImpl.getStorageAuthData(authToken);

        Object oip = wsImpl.modifyPatientToHospitalBed(actionId, data, auth);
        JSONWithPadding returnValue = new JSONWithPadding(oip, callback);
        return returnValue;
    }


    /**
     * Отмена регистрациии на койке
     * @param actionId Идентификатор действия типа 'Движение' для которого отменяется регистрация на койке.
     * @param callback  callback запроса.
     * @param servRequest Контекст запроса.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws CoreException
     * @see CoreException
     */
    @GET
    @Path("/hospitalbed/{actionId}/calloff")
    @Produces("application/x-javascript")
    public Object callOffHospitalBedForPatient(
                                                @PathParam("actionId") int actionId,
                                                @QueryParam("callback") String callback,
                                                @Context HttpServletRequest servRequest) {
        AuthData auth = wsImpl.checkTokenCookies(servRequest);
        JSONWithPadding returnValue = new JSONWithPadding(wsImpl.callOffHospitalBedForPatient(actionId, auth), callback);
        return returnValue;
    }

    /**
     * Данные об регистрации на койке
     * @param actionId Идентификатор действия типа 'Движение' для которого отменяется регистрация на койке.
     * @param callback  callback запроса.
     * @param servRequest Контекст запроса.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws CoreException
     * @see CoreException
     */
    @GET
    @Path("/hospitalbed/{actionId}")
    @Produces("application/x-javascript")
    public Object getInfoHospitalBedForPatient(
                                                @PathParam("actionId") int actionId,
                                                @QueryParam("callback") String callback,
                                                @Context HttpServletRequest servRequest) {
        AuthData auth = wsImpl.checkTokenCookies(servRequest);
        JSONWithPadding returnValue = new JSONWithPadding(wsImpl.getPatientToHospitalBedById(actionId, auth), callback);
        return returnValue;
    }

    /**
     * Список движения по отделениям
     * @param eventId Идентификатор обращения на госпитализацию, для которого выводится список.
     * @param limit Максимальное количество выводимых элементов в списке.
     * @param page Номер выводимой страницы.
     * @param sortingField Наименование поля для сортировки.<pre>
     * &#15; Возможные значения:
     * &#15; "createDatetime" - по идентификатору осмотра (значение по умолчанию);</pre>
     * @param sortingMethod Метод сортировки.<pre>
     * &#15; Возможные значения:
     * &#15; "asc" - по возрастанию (значение по умолчанию);
     * &#15; "desc" - по убыванию;</pre>
     * @param callback  callback запроса.
     * @param servRequest Контекст запроса.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws CoreException
     * @see CoreException
     */
    @GET
    @Path("appeals/{eventId}/hospitalbed/")
    @Produces("application/x-javascript")
    public Object getMovingListForEvent(
                                            @PathParam("eventId") int eventId,
                                            @QueryParam("limit")int limit,
                                            @QueryParam("page")int  page,
                                            @QueryParam("sortingField")String sortingField,   //сортировки выкл.
                                            @QueryParam("sortingMethod")String sortingMethod,
                                            @QueryParam("callback") String callback,
                                            @Context HttpServletRequest servRequest) {

        AuthData auth = wsImpl.checkTokenCookies(servRequest);

        HospitalBedDataListFilter filter = new HospitalBedDataListFilter(eventId);
        HospitalBedDataRequest request= new HospitalBedDataRequest(sortingField, sortingMethod, limit, page, filter);
        Object oip = wsImpl.getMovingListForEvent(request, auth);
        JSONWithPadding returnValue = new JSONWithPadding(oip, callback);
        return returnValue;
    }

    /**
     * Сервис на получение списка коек с меткой свободно/занято.
     * Url: .../hospitalbed/vacant/{departmentId}
     * Since: ver 1.0.0.57
     * @param departmentId Идентификатор отделения.
     * @param callback callback запроса.
     * @param servRequest Контекст запроса.
     * @return Список коек в json-формате.
     */
    @GET
    @Path("/hospitalbed/vacant")
    @Produces("application/x-javascript")
    public Object getVacantHospitalBeds(@QueryParam("filter[departmentId]") int departmentId,
                                        @QueryParam("callback") String callback,
                                        @Context HttpServletRequest servRequest) {
        AuthData auth = wsImpl.checkTokenCookies(servRequest);
        //Отделение обязательное поле, если не задано в запросе, то берем из роли специалиста
        int depId = (departmentId>0) ? departmentId : auth.getUser().getOrgStructure().getId().intValue();

        Object oip = wsImpl.getVacantHospitalBeds(depId, auth);
        JSONWithPadding returnValue = new JSONWithPadding(oip, callback);
        return returnValue;
    }

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
     * @param callback  callback запроса.
     * @param servRequest Контекст запроса.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws CoreException
     * @see CoreException
     */
    @GET
    @Path("/reports/f007")
    @Produces("application/x-javascript")
    public Object getForm007( @QueryParam("filter[departmentId]") int departmentId,
                              @QueryParam("filter[beginDate]")long beginDate,
                              @QueryParam("filter[endDate]")long endDate,
                              @QueryParam("callback") String callback,
                              @Context HttpServletRequest servRequest) {

        AuthData auth = wsImpl.checkTokenCookies(servRequest);
        //Отделение обязательное поле, если не задано в запросе, то берем из роли специалиста
        int depId = (departmentId>0) ? departmentId : auth.getUser().getOrgStructure().getId().intValue();

        Object oip = wsImpl.getForm007(depId, beginDate, endDate, auth);
        JSONWithPadding returnValue = new JSONWithPadding(oip, callback);
        return returnValue;
    }


    /**
     * Создание направления/перевода в отделение.
     * @param data json данные о движении пациента как HospitalBedData
     * @param eventId Идентификатор обращения на госпитализацию.
     * @param callback  callback запроса.
     * @param servRequest Контекст запроса.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws CoreException
     * @see CoreException
     * @see HospitalBedData
     */
    @POST
    @Path("appeals/{eventId}/moving/")
    @Consumes("application/json")
    @Produces("application/x-javascript")
    public  Object movingPatientToDepartment(  HospitalBedData data,
                                                @PathParam("eventId") int eventId,
                                                @QueryParam("callback") String callback,
                                                //@QueryParam("token") String token,
                                                @Context HttpServletRequest servRequest) {
        AuthData auth = wsImpl.checkTokenCookies(servRequest);
        //AuthToken authToken = new AuthToken(token);
        //AuthData auth = wsImpl.getStorageAuthData(authToken);

        Object oip = wsImpl.movingPatientToDepartment(eventId, data, auth);
        JSONWithPadding returnValue = new JSONWithPadding(oip, callback);
        return returnValue;
    }


    /*@POST
    @Path("/patients/{patientId}/talon")
    @Consumes("application/json")
    @Produces("application/x-javascript")
    public Object insertTalonSPOForPatient( Object data,
                                            @QueryParam("callback") String callback) {

        JSONWithPadding returnValue = new JSONWithPadding(wsImpl.insertTalonSPOForPatient(data), callback);
        return returnValue;
    } */

    /**
     * Получение списка талонов СПО для пациента.
     * @param patientId Идентификатор пациента.
     * @param limit Максимальное количество выводимых элементов в списке.
     * @param page Номер выводимой страницы.
     * @param sortingField Наименование поля для сортировки.<pre>
     * &#15; Возможные значения:
     * &#15; "id" - по идентификатору обращения;
     * &#15; "start" | "begDate" - по дате начала госпитализации;
     * &#15; "end" | "endDate" - по дате окончания госпитализации;
     * &#15; "doctor" - по ФИО специалиста;
     * &#15; "department" - по наименованию отделения;</pre>
     * @param sortingMethod Метод сортировки.<pre>
     * &#15; Возможные значения:
     * &#15; "asc" - по возрастанию (значение по умолчанию);
     * &#15; "desc" - по убыванию;</pre>
     * @param callback  callback запроса.
     * @param servRequest Контекст запроса.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws CoreException
     * @see CoreException
     */
    @GET
    @Path("/patients/{patientId}/talons")
    @Produces("application/x-javascript")
    public Object getAllTalonsForPatient(   @PathParam("patientId") int patientId,
                                            @QueryParam("limit")int limit,
                                            @QueryParam("page")int  page,
                                            @QueryParam("sortingField")String sortingField,   //сортировки вкл.
                                            @QueryParam("sortingMethod")String sortingMethod,
                                            @QueryParam("callback") String callback,
                                            @Context HttpServletRequest servRequest) {
        AuthData auth = wsImpl.checkTokenCookies(servRequest);

        TalonSPODataListFilter filter = new TalonSPODataListFilter(patientId, "33");
        TalonSPOListRequestData request = new TalonSPOListRequestData(sortingField, sortingMethod, limit, page, filter);

        Object oip = wsImpl.getAllTalonsForPatient(request);
        JSONWithPadding returnValue = new JSONWithPadding(oip, callback);
        return returnValue;
    }


    /**
     * Список персонала
     * @param limit Максимальное количество выводимых элементов в списке.
     * @param page Номер выводимой страницы.
     * @param sortingField Наименование поля для сортировки.<pre>
     * &#15; Возможные значения:
     * &#15; "id" - по идентификатору обращения (значение по умолчанию);</pre>
     * @param sortingMethod Метод сортировки.<pre>
     * &#15; Возможные значения:
     * &#15; "asc" - по возрастанию (значение по умолчанию);
     * &#15; "desc" - по убыванию;</pre>
     * @param callback  callback запроса.
     * @param servRequest Контекст запроса.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws CoreException
     * @see CoreException
     */
    @GET
    @Path("/persons")
    @Produces("application/x-javascript")
    public Object getAllPersons(@QueryParam("limit")int limit,
                                @QueryParam("page")int  page,
                                @QueryParam("sortingField")String sortingField,              //сортировки выкл
                                @QueryParam("sortingMethod")String sortingMethod,
                                @QueryParam("callback") String callback,
                                @Context HttpServletRequest servRequest) {
        AuthData auth = wsImpl.checkTokenCookies(servRequest);
        ListDataRequest request = new ListDataRequest(sortingField, sortingMethod, limit, page, null);
        JSONWithPadding returnValue = new JSONWithPadding(wsImpl.getAllPersons(request), callback);
        return returnValue;
    }

    /**
     * Список отделений
     * @param limit Максимальное количество выводимых элементов на странице.
     * @param page Номер выводимой страницы.
     * @param sortingField Наименование поля для сортировки. <pre>
     * &#15; Возможные значения:
     * &#15; "id" - по идентификатору отделения (значение по умолчанию);
     * &#15; "name" - по наименованию отделения </pre>
     * @param sortingMethod Метод сортировки.<pre>
     * &#15; Возможные значения:
     * &#15; "asc" - по возрастанию (значение по умолчанию);
     * &#15; "desc" - по убыванию;</pre>
     * @param hasBeds Фильтр отделений имеющих развернутые койки.("true"/"false")
     * @param callback  callback запроса.
     * @param servRequest Контекст запроса с клиента.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws CoreException
     * @see CoreException
     */
    @GET
    @Path("/departments")
    @Produces("application/x-javascript")
    public Object getAllDepartments(@QueryParam("limit")int limit,
                                    @QueryParam("page")int  page,
                                    @QueryParam("sortingField")String sortingField,            //сортировки выкл
                                    @QueryParam("sortingMethod")String sortingMethod,
                                    @QueryParam("filter[hasBeds]")String hasBeds,
                                    @QueryParam("callback") String callback,
                                    @Context HttpServletRequest servRequest) {
        AuthData auth = wsImpl.checkTokenCookies(servRequest);

        Boolean flgBeds =  (hasBeds!=null && hasBeds.indexOf("true")>=0) ? true : false;
        DepartmentsDataFilter filter = new DepartmentsDataFilter(flgBeds);
        ListDataRequest request = new ListDataRequest(sortingField, sortingMethod, limit, page, filter);
        JSONWithPadding returnValue = new JSONWithPadding(wsImpl.getAllDepartments(request), callback);
        return returnValue;
    }

    /**
     * Просмотр списка направлений на лабораторные исследования
     * @param eventId идентификатор обращения на госпитализацию.
     * @param limit Максимальное количество выводимых элементов на странице.
     * @param page Номер выводимой страницы.
     * @param sortingField Наименование поля для сортировки.<pre>
     * &#15; Возможные значения:
     * &#15; "id" - по идентификатору направления (значение по умолчанию);
     * &#15; "directionDate" - по дате направления;
     * &#15; "diagnosticName" - по наименованию типа направления;
     * &#15; "execPerson" - по ФИО исполнителя;
     * &#15; "assignPerson" - по ФИО специалиста, назначившего исследование;
     * &#15; "office" - по идентификатору кабинета;
     * &#15; "status" - по статусу;
     * &#15; "cito" - по срочности;</pre>
     * @param sortingMethod Метод сортировки.<pre>
     * &#15; Возможные значения:
     * &#15; "asc" - по возрастанию (значение по умолчанию);
     * &#15; "desc" - по убыванию;</pre>
     * @param diaTypeCode Фильтр по коду типа исследования.
     * @param diagnosticDate Фильтр по дате исследования.
     * @param directionDate Фильтр по дате назначения.
     * @param diagnosticName Фильтр по наименованию исследования.
     * @param assignPersonId Фильтр по направившему на исследование специалисту.
     * @param execPersonId  Фильтр по проводившему исследование специалисту.
     * @param statusId Фильтр по статусу исследования.
     * @param urgent  Фильтр по срочности исследования.
     * @param callback  callback запроса.
     * @param servRequest Контекст запроса с клиента.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws CoreException
     * @see CoreException
     */
    @GET
    @Path("/appeals/{eventId}/diagnostics/laboratory")
    @Produces("application/x-javascript")
    public Object getListOfLaboratoryDiagnosticsForPatientByEvent(  @PathParam("eventId")int eventId,
                                                                    @QueryParam("limit")int limit,
                                                                    @QueryParam("page")int  page,
                                                                    @QueryParam("sortingField")String sortingField,           //сортировки вкл
                                                                    @QueryParam("sortingMethod")String sortingMethod,
                                                                    @QueryParam("filter[code]")String  diaTypeCode,
                                                                    @QueryParam("filter[diagnosticDate]")long  diagnosticDate,
                                                                    @QueryParam("filter[directionDate]")long  directionDate,
                                                                    @QueryParam("filter[diagnosticName]")String  diagnosticName,
                                                                    @QueryParam("filter[assignPersonId]")int  assignPersonId,
                                                                    @QueryParam("filter[execPersonId]")int  execPersonId,
                                                                    @QueryParam("filter[statusId]")int  statusId,
                                                                    @QueryParam("filter[urgent]")Boolean  urgent,
                                                                    @QueryParam("callback") String callback,
                                                                    @Context HttpServletRequest servRequest) {
        AuthData auth = wsImpl.checkTokenCookies(servRequest);

        DiagnosticsListRequestDataFilter filter = new DiagnosticsListRequestDataFilter( diaTypeCode,
                                                                                        eventId,
                                                                                        diagnosticDate,
                                                                                        directionDate,
                                                                                        diagnosticName,
                                                                                        assignPersonId,
                                                                                        execPersonId,
                                                                                        "",
                                                                                        statusId,
                                                                                        (urgent==null) ? -1 : (urgent) ? 1 : 0,
                                                                                        "",//"laboratory"  //теперь не ищем по коду!
                                                                                        "LAB");

        DiagnosticsListRequestData requestData = new DiagnosticsListRequestData(sortingField,
                                                                                sortingMethod,
                                                                                limit,
                                                                                page,
                                                                                filter);
        JSONWithPadding returnValue = new JSONWithPadding(wsImpl.getListOfDiagnosticsForPatientByEvent(requestData), callback);
        return returnValue;
    }

    /**
     * Просмотр списка направлений на инструментальные исследования
     * @param eventId идентификатор обращения на госпитализацию.
     * @param limit Максимальное количество выводимых элементов на странице.
     * @param page Номер выводимой страницы.
     * @param sortingField Наименование поля для сортировки.<pre>
     * &#15; Возможные значения:
     * &#15; "id" - по идентификатору направления (значение по умолчанию);
     * &#15; "directionDate" - по дате направления;
     * &#15; "diagnosticName" - по наименованию типа направления;
     * &#15; "execPerson" - по ФИО исполнителя;
     * &#15; "assignPerson" - по ФИО специалиста, назначившего исследование;
     * &#15; "office" - по идентификатору кабинета;
     * &#15; "status" - по статусу;
     * &#15; "cito" - по срочности;</pre>
     * @param sortingMethod Метод сортировки.<pre>
     * &#15; Возможные значения:
     * &#15; "asc" - по возрастанию (значение по умолчанию);
     * &#15; "desc" - по убыванию;</pre>
     * @param diaTypeCode Фильтр по коду типа исследования.
     * @param diagnosticDate Фильтр по дате исследования.
     * @param diagnosticName Фильтр по наименованию исследования.
     * @param assignPersonId Фильтр по направившему на исследование специалисту.
     * @param execPersonId  Фильтр по проводившему исследование специалисту.
     * @param statusId Фильтр по статусу исследования.
     * @param office  Фильтр по коду кабинета.
     * @param callback  callback запроса.
     * @param servRequest Контекст запроса с клиента.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws CoreException
     * @see CoreException
     */
    @GET
    @Path("/appeals/{eventId}/diagnostics/instrumental")
    @Produces("application/x-javascript")
    public Object getListOfInstrumentalDiagnosticsForPatientByEvent(  @PathParam("eventId")int eventId,
                                                                      @QueryParam("limit")int limit,
                                                                      @QueryParam("page")int  page,
                                                                      @QueryParam("sortingField")String sortingField,                  //сортировки вкл
                                                                      @QueryParam("sortingMethod")String sortingMethod,
                                                                      @QueryParam("filter[code]")String  diaTypeCode,
                                                                      @QueryParam("filter[diagnosticDate]")long  diagnosticDate,
                                                                      @QueryParam("filter[diagnosticName]")String  diagnosticName,
                                                                      @QueryParam("filter[assignPersonId]")int  assignPersonId,
                                                                      @QueryParam("filter[execPersonId]")int  execPersonId,
                                                                      @QueryParam("filter[statusId]")int  statusId,
                                                                      @QueryParam("filter[office]")String  office,
                                                                      @QueryParam("callback") String callback,
                                                                      @Context HttpServletRequest servRequest) {
        AuthData auth = wsImpl.checkTokenCookies(servRequest);

        DiagnosticsListRequestDataFilter filter = new DiagnosticsListRequestDataFilter( diaTypeCode,
                                                                                        eventId,
                                                                                        diagnosticDate,
                                                                                        0,
                                                                                        diagnosticName,
                                                                                        assignPersonId,
                                                                                        execPersonId,
                                                                                        office,
                                                                                        statusId,
                                                                                        -1,
                                                                                        "instrumental",
                                                                                        "DI");

        DiagnosticsListRequestData requestData = new DiagnosticsListRequestData(sortingField,
                                                                                sortingMethod,
                                                                                limit,
                                                                                page,
                                                                                filter);
        JSONWithPadding returnValue = new JSONWithPadding(wsImpl.getListOfDiagnosticsForPatientByEvent(requestData), callback);
        return returnValue;
    }

    /**
     * Просмотр списка направлений на консультации к врачу
     * @param eventId идентификатор обращения на госпитализацию.
     * @param limit Максимальное количество выводимых элементов на странице.
     * @param page Номер выводимой страницы.
     * @param sortingField Наименование поля для сортировки.<pre>
     * &#15; Возможные значения:
     * &#15; "id" - по идентификатору направления (значение по умолчанию);
     * &#15; "directionDate" - по дате направления;
     * &#15; "diagnosticName" - по наименованию типа направления;
     * &#15; "execPerson" - по ФИО исполнителя;
     * &#15; "assignPerson" - по ФИО специалиста, назначившего исследование;
     * &#15; "office" - по идентификатору кабинета;
     * &#15; "status" - по статусу;
     * &#15; "cito" - по срочности;</pre>
     * @param sortingMethod Метод сортировки.<pre>
     * &#15; Возможные значения:
     * &#15; "asc" - по возрастанию (значение по умолчанию);
     * &#15; "desc" - по убыванию;</pre>
     * @param diaTypeCode Фильтр по коду типа исследования.
     * @param diagnosticDate Фильтр по дате исследования.
     * @param diagnosticName Фильтр по наименованию исследования.
     * @param assignPersonId Фильтр по направившему на исследование специалисту.
     * @param execPersonId  Фильтр по проводившему исследование специалисту.
     * @param statusId Фильтр по статусу исследования.
     * @param office  Фильтр по коду кабинета.
     * @param callback  callback запроса.
     * @param servRequest Контекст запроса с клиента.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws CoreException
     * @see CoreException
     */
    @GET
    @Path("/appeals/{eventId}/diagnostics/consultations")
    @Produces("application/x-javascript")
    public Object getListOfDoctorConsultationForPatientByEvent(  @PathParam("eventId")int eventId,
                                                                 @QueryParam("limit")int limit,
                                                                 @QueryParam("page")int  page,
                                                                 @QueryParam("sortingField")String sortingField,                //сортировки вкл
                                                                 @QueryParam("sortingMethod")String sortingMethod,
                                                                 @QueryParam("filter[code]")String  diaTypeCode,
                                                                 @QueryParam("filter[diagnosticDate]")long  diagnosticDate,
                                                                 @QueryParam("filter[diagnosticName]")String  diagnosticName,
                                                                 @QueryParam("filter[assignerId]")int  assignPersonId,
                                                                 @QueryParam("filter[executorId]")int  execPersonId,
                                                                 @QueryParam("filter[statusId]")int  statusId,
                                                                 @QueryParam("filter[office]")String  office,
                                                                 @QueryParam("callback") String callback,
                                                                 @Context HttpServletRequest servRequest) {
        AuthData auth = wsImpl.checkTokenCookies(servRequest);

        DiagnosticsListRequestDataFilter filter = new DiagnosticsListRequestDataFilter( diaTypeCode,
                                                                                        eventId,
                                                                                        diagnosticDate,
                                                                                        0,
                                                                                        diagnosticName,
                                                                                        assignPersonId,
                                                                                        execPersonId,
                                                                                        office,
                                                                                        statusId,
                                                                                        -1,
                                                                                        "consultations",
                                                                                        "");

        DiagnosticsListRequestData requestData = new DiagnosticsListRequestData(sortingField,
                                                                                sortingMethod,
                                                                                limit,
                                                                                page,
                                                                                filter);
        JSONWithPadding returnValue = new JSONWithPadding(wsImpl.getListOfDiagnosticsForPatientByEvent(requestData), callback);
        return returnValue;
    }

    /**
     * Просмотр результатов лабораторных исследований
     * @param actionId идентификатор исследования.
     * @param callback  callback запроса.
     * @param servRequest Контекст запроса с клиента.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws CoreException
     * @see CoreException
     */
    @GET
    @Path("diagnostics/laboratory/{actionId}")
    @Produces("application/x-javascript")
    public Object getInfoAboutDiagnosticsForPatientByEvent(@PathParam("actionId")int actionId,
                                                           @QueryParam("callback") String callback,
                                                           @Context HttpServletRequest servRequest) {
        AuthData auth = wsImpl.checkTokenCookies(servRequest);

        JSONWithPadding returnValue = new JSONWithPadding(wsImpl.getInfoAboutDiagnosticsForPatientByEvent(actionId), callback);
        return returnValue;
    }

    /**
     * Список свободных на выбранное время врачей по специальности
     * @param limit Максимальное количество выводимых элементов на странице.
     * @param page Номер выводимой страницы.
     * @param sortingField Наименование поля для сортировки.<pre>
     * &#15; Внимание! Сортировка выкл.</pre>
     * @param sortingMethod Метод сортировки.<pre>
     * &#15; Возможные значения:
     * &#15; "asc" - по возрастанию (значение по умолчанию);
     * &#15; "desc" - по убыванию;</pre>
     * @param speciality  Фильтр по специальности врача.
     * @param doctorId Фильтр по идентификатору специалиста.
     * @param beginDate Дата начала периода, по которому ищутся свободные специалисты.
     * @param endDate Дата конца периода, по которому ищутся свободные специалисты.
     * @param callback  callback запроса.
     * @param servRequest Контекст запроса с клиента.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws CoreException
     * @see CoreException
     */
    @GET
    @Path("/persons/free")
    @Produces("application/x-javascript")
    public Object getFreePersons(@QueryParam("limit")int limit,
                                 @QueryParam("page")int  page,
                                 @QueryParam("sortingField")String sortingField,                //сортировки выкл.
                                 @QueryParam("sortingMethod")String sortingMethod,
                                 @QueryParam("filter[speciality]")int speciality,
                                 @QueryParam("filter[doctorId]")int doctorId,
                                 @QueryParam("filter[beginDate]")long beginDate,
                                 @QueryParam("filter[endDate]")long endDate,
                                 @QueryParam("callback") String callback,
                                 @Context HttpServletRequest servRequest) {
        AuthData auth = wsImpl.checkTokenCookies(servRequest);

        FreePersonsListDataFilter filter = new FreePersonsListDataFilter(speciality, doctorId, beginDate, endDate);
        ListDataRequest request = new ListDataRequest(sortingField, sortingMethod, limit, page, filter);
        JSONWithPadding returnValue = new JSONWithPadding(wsImpl.getFreePersons(request), callback);
        return returnValue;
    }

    /**
     * Создание направления на консультацию к врачу
     * @param data json данные о консультации как ConsultationRequestData.
     * @param callback  callback запроса.
     * @param servRequest Контекст запроса с клиента.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws CoreException
     * @see CoreException
     */
    @POST
    @Path("appeals/{eventId}/diagnostics/consultations")
    @Consumes("application/json")
    @Produces("application/x-javascript")
    public Object insertConsultation(ConsultationRequestData data,
                                     @QueryParam("callback") String callback,
                                     @Context HttpServletRequest servRequest) {
        AuthData auth = wsImpl.checkTokenCookies(servRequest);

        //
        //ConsultationRequestData request = new ConsultationRequestData(eventId, actionTypeId, executorId, patientId, beginDate, endDate, urgent);
        JSONWithPadding returnValue = new JSONWithPadding(wsImpl.insertConsultation(data.rewriteDefault(data)), callback);
        return returnValue;
    }

    /**
     * Получение списка типа действий (ActionType's) по коду и/или идентификатору группы<br>
     * Для лабораторных исследований.
     * @param patientId Идентификатор пациента
     * @param limit Максимальное количество выводимых элементов на странице.
     * @param page Номер выводимой страницы.
     * @param sortingField Наименование поля для сортировки.<pre>
     * &#15; "id" - по идентификатору типа действия;
     * &#15; "groupId" - по идентификатору группы типа действия;
     * &#15; "code" - по коду типа действия;
     * &#15; "name" - по обозначению типа действия;</pre>
     * @param sortingMethod Метод сортировки.<pre>
     * &#15; Возможные значения:
     * &#15; "asc" - по возрастанию (значение по умолчанию);
     * &#15; "desc" - по убыванию;</pre>
     * @param groupId Фильтр по идентификатору группы типа действия (s11r64.ActionType.group_id). (В url: filter[groupId]=...)
     * @param code  Фильтр по коду типа действия (s11r64.ActionType.code). (В url: filter[code]=...)
     * @param view  Фильтр по коду типа отображения информации. (В url: filter[view]=...)
     * &#15; Возможные значения:
     * &#15; "tree" - в виде дерева;
     * &#15; "flat" - в виде плоской структуры;</pre>
     * @param callback  callback запроса.
     * @param servRequest Контекст запроса с клиента.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws CoreException
     * @see CoreException
     */
    @GET
    @Path("/actionTypes/laboratory")
    @Produces("application/x-javascript")
    public Object getActionTypeNamesForLaboratory(@QueryParam("patientId")int patientId,
                                                 @QueryParam("limit")int limit,
                                                 @QueryParam("page")int  page,
                                                 @QueryParam("sortingField")String sortingField,      //сортировки вкл.
                                                 @QueryParam("sortingMethod")String sortingMethod,
                                                 @QueryParam("filter[groupId]")int groupId,
                                                 @QueryParam("filter[code]")String code,
                                                 @QueryParam("filter[view]")String view,
                                                 @QueryParam("callback") String callback,
                                                 @Context HttpServletRequest servRequest) {
        AuthData auth = wsImpl.checkTokenCookies(servRequest);

        ActionTypesListRequestDataFilter filter = new ActionTypesListRequestDataFilter(code, groupId, "laboratory", "LAB", view);
        ListDataRequest request = new ListDataRequest(sortingField, sortingMethod, limit, page, filter);
        Object oip = null;
        if (view != null && view.compareTo("tree") == 0) {
          oip = wsImpl.getListOfActionTypes(request);
        } else {
          oip = wsImpl.getListOfActionTypeIdNames(request, patientId);
        }
        JSONWithPadding returnValue = new JSONWithPadding(oip, callback);
        return returnValue;
    }
    ///laboratory/tests/{testId}?filter[birthDate]={birthDate}

    /**
     * Получение списка типа действий (ActionType's) по коду и/или идентификатору группы<br>
     * Для инструментальных исследований.
     * @param patientId Идентификатор пациента
     * @param limit Максимальное количество выводимых элементов на странице.
     * @param page Номер выводимой страницы.
     * @param sortingField Наименование поля для сортировки.<pre>
     * &#15; "id" - по идентификатору типа действия;
     * &#15; "groupId" - по идентификатору группы типа действия;
     * &#15; "code" - по коду типа действия;
     * &#15; "name" - по обозначению типа действия;</pre>
     * @param sortingMethod Метод сортировки.<pre>
     * &#15; Возможные значения:
     * &#15; "asc" - по возрастанию (значение по умолчанию);
     * &#15; "desc" - по убыванию;</pre>
     * @param groupId Фильтр по идентификатору группы типа действия (s11r64.ActionType.group_id). (В url: filter[groupId]=...)
     * @param code  Фильтр по коду типа действия (s11r64.ActionType.code). (В url: filter[code]=...)
     * @param view  Фильтр по коду типа отображения информации. (В url: filter[view]=...)
     * &#15; Возможные значения:
     * &#15; "tree" - в виде дерева;
     * &#15; "flat" - в виде плоской структуры;</pre>
     * @param callback  callback запроса.
     * @param servRequest Контекст запроса с клиента.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws CoreException
     * @see CoreException
     */
    @GET
    @Path("/actionTypes/instrumental")
    @Produces("application/x-javascript")
    public Object getActionTypeNamesForInstrumentalDiagnostics(@QueryParam("patientId")int patientId,
                                                  @QueryParam("limit")int limit,
                                                  @QueryParam("page")int  page,
                                                  @QueryParam("sortingField")String sortingField,    //сортировки вкл.
                                                  @QueryParam("sortingMethod")String sortingMethod,
                                                  @QueryParam("filter[groupId]")int groupId,
                                                  @QueryParam("filter[code]")String code,
                                                  @QueryParam("filter[view]")String view,
                                                  @QueryParam("callback") String callback,
                                                  @Context HttpServletRequest servRequest) {
        AuthData auth = wsImpl.checkTokenCookies(servRequest);

        ActionTypesListRequestDataFilter filter = new ActionTypesListRequestDataFilter(code, groupId, "instrumental", "DI", view);
        ListDataRequest request = new ListDataRequest(sortingField, sortingMethod, limit, page, filter);

        Object oip = wsImpl.getListOfActionTypeIdNames(request, patientId);
        JSONWithPadding returnValue = new JSONWithPadding(oip, callback);
        return returnValue;
    }

    /**
     * Получение списка типа действий (ActionType's) по коду и/или идентификатору группы</br>
     * Для всех исследований.
     * @param patientId Идентификатор пациента
     * @param limit Максимальное количество выводимых элементов на странице.
     * @param page Номер выводимой страницы.
     * @param sortingField Наименование поля для сортировки.<pre>
     * &#15; "id" - по идентификатору типа действия;
     * &#15; "groupId" - по идентификатору группы типа действия;
     * &#15; "code" - по коду типа действия;
     * &#15; "name" - по обозначению типа действия;</pre>
     * @param sortingMethod Метод сортировки.<pre>
     * &#15; Возможные значения:
     * &#15; "asc" - по возрастанию (значение по умолчанию);
     * &#15; "desc" - по убыванию;</pre>
     * @param groupId Фильтр по идентификатору группы типа действия (s11r64.ActionType.group_id). (В url: filter[groupId]=...)
     * @param code  Фильтр по коду типа действия (s11r64.ActionType.code). (В url: filter[code]=...)
     * @param view  Фильтр по коду типа отображения информации. (В url: filter[view]=...)
     * &#15; Возможные значения:
     * &#15; "tree" - в виде дерева;
     * &#15; "flat" - в виде плоской структуры;</pre>
     * @param callback  callback запроса.
     * @param servRequest Контекст запроса с клиента.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws CoreException
     * @see CoreException
     */
    @GET
    @Path("/actionTypes")
    @Produces("application/x-javascript")
    public Object getAllActionTypeNames(@QueryParam("patientId")int patientId,
                                        @QueryParam("limit")int limit,
                                        @QueryParam("page")int  page,
                                        @QueryParam("sortingField")String sortingField,          //сортировки вкл.
                                        @QueryParam("sortingMethod")String sortingMethod,
                                        @QueryParam("filter[groupId]")int groupId,
                                        @QueryParam("filter[code]")String code,
                                        @QueryParam("filter[view]")String view,
                                        @QueryParam("callback") String callback,
                                        @Context HttpServletRequest servRequest) {
        AuthData auth = wsImpl.checkTokenCookies(servRequest);

        ActionTypesListRequestDataFilter filter = new ActionTypesListRequestDataFilter(code, groupId, "all", "", view);
        ListDataRequest request = new ListDataRequest(sortingField, sortingMethod, limit, page, filter);

        Object oip = wsImpl.getListOfActionTypeIdNames(request, patientId);
        JSONWithPadding returnValue = new JSONWithPadding(oip, callback);
        return returnValue;
    }

    /**
     * Создание направления на лабораторные исследования
     * @param data Json с данными о лабораторном исследовании как CommonData
     * @param eventId Идентификатор обращения на госпитализацию, в рамках которой создается исследование.
     * @param callback  callback запроса.
     * @param servRequest Контекст запроса с клиента.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws CoreException
     * @see CoreException
     */
    @POST
    @Path("/diagnostics/{eventId}/laboratory")
    @Consumes("application/json")
    @Produces("application/x-javascript")
    public Object insertLaboratoryStudies(JSONCommonData data,
                                          @PathParam("eventId")int  eventId,
                                          @QueryParam("callback") String callback,
                                          //@QueryParam("token") String token,
                                          @Context HttpServletRequest servRequest) {
        AuthData auth = wsImpl.checkTokenCookies(servRequest);
        //AuthToken authToken = new AuthToken(token);
        //AuthData auth = wsImpl.getStorageAuthData(authToken);

        CommonData com_data = new CommonData();
        com_data.setEntity(data.getData());

        JSONCommonData oip = wsImpl.insertLaboratoryStudies(eventId, com_data, auth);
        JSONWithPadding returnValue = new JSONWithPadding(oip, callback);
        return returnValue;
    }

    /**
     * Редактирование направления на лабораторные исследования
     * @param data Json с данными о лабораторном исследовании как CommonData
     * @param eventId Идентификатор обращения на госпитализацию, в рамках которой создается исследование.
     * @param callback  callback запроса.
     * @param servRequest Контекст запроса с клиента.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws CoreException
     * @see CoreException
     */
    @PUT
    @Path("/diagnostics/{eventId}/laboratory")
    @Consumes("application/json")
    @Produces("application/x-javascript")
    public Object modifyLaboratoryStudies(JSONCommonData data,
                                          @PathParam("eventId")int  eventId,
                                          @QueryParam("callback") String callback,
                                          //@QueryParam("token") String token,
                                          @Context HttpServletRequest servRequest) {
        AuthData auth = wsImpl.checkTokenCookies(servRequest);
        //AuthToken authToken = new AuthToken(token);
        //AuthData auth = wsImpl.getStorageAuthData(authToken);

        CommonData com_data = new CommonData();
        com_data.setEntity(data.getData());

        JSONCommonData oip = wsImpl.modifyLaboratoryStudies(eventId, com_data, auth);
        JSONWithPadding returnValue = new JSONWithPadding(oip, callback);
        return returnValue;
    }

    /**
     * Создание направления на инструментальную диагностику
     * @param data Json с данными о инструментальном исследовании как CommonData
     * @param eventId Идентификатор обращения на госпитализацию, в рамках которой создается исследование.
     * @param callback  callback запроса.
     * @param servRequest Контекст запроса с клиента.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws CoreException
     * @see CoreException
     */
    @POST
    @Path("/diagnostics/{eventId}/instrumental")
    @Consumes("application/json")
    @Produces("application/x-javascript")
    public Object insertInstrumentalDiagnostic(CommonData data,
                                          @PathParam("eventId")int  eventId,
                                          @QueryParam("callback") String callback,
                                          @Context HttpServletRequest servRequest) {
        AuthData auth = wsImpl.checkTokenCookies(servRequest);

        JSONCommonData oip = wsImpl.insertLaboratoryStudies(eventId, data, auth);
        JSONWithPadding returnValue = new JSONWithPadding(oip, callback);
        return returnValue;
    }

    //
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
     * @param callback  callback запроса.
     * @param info Контекст информации о url-запросе.<pre>
     * &#15; Анализируются следующие параметры:
     * &#15; flatDirectoryId - Идентификатор справочника. Может быть несколько.
     * &#15; Если параметр не указан, выдаются все справочники.
     * &#15; filterRecordId - Идентификатор записи из таблицы FDRecord. Может быть несколько.
     * &#15; filter[X]=Y - Фильтр для поиска (выдаются только те записи, у которых поле с типом X содержит Y).
     * &#15; Таких параметров в одном запросе может быть несколько.
     * &#15; sortingField[X] = Y - Сортировки по полям X с порядком сортировки Y. Может быть несколько.</pre>
     * @param servRequest Контекст запроса с клиента.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws CoreException
     * @see CoreException
     */
    @GET
    @Path("/flatDirectory")
    @Produces("application/x-javascript")
    public Object getFlatDirectories(@Context UriInfo info,
                                     @Context HttpServletRequest servRequest,
                                     @QueryParam("includeMeta") String includeMeta,
                                     @QueryParam("includeRecordList") String includeRecordList,
                                     @QueryParam("includeFDRecord") String includeFDRecord,
                                     @QueryParam("filterValue") String filterValue,
                                     @QueryParam("limit")int limit,
                                     @QueryParam("page")int  page,
                                     @QueryParam("callback") String callback
                                     ) throws CoreException {

       AuthData auth = wsImpl.checkTokenCookies(servRequest);
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

       FlatDirectoryData oip = wsImpl.getFlatDirectories(request);
       JSONWithPadding returnValue = new JSONWithPadding(oip, callback);
       return returnValue;
    }

    /**
     * Получение данных из MKB справочников.
     * @param limit Максимальное количество выводимых элементов на странице.
     * @param page Номер выводимой страницы.
     * @param sortingField Наименование поля для сортировки.<pre>
     * &#15; "id" - по идентификатору MKB (значение по умолчанию);
     * &#15; "classId" - по идентификатору класса МКВ (s11r64.MKB.classID);
     * &#15; "blockId" - по идентификатору блока МКВ(s11r64.MKB.blockID);
     * &#15; "code" - по коду МКВ диагноза (s11r64.MKB.diagID);
     * &#15; "diagnosis" - по обозначению МКВ диагнозу(s11r64.MKB.diagName);</pre>
     * @param sortingMethod Метод сортировки.<pre>
     * &#15; Возможные значения:
     * &#15; "asc" - по возрастанию (значение по умолчанию);
     * &#15; "desc" - по убыванию;</pre>
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
     * @param callback  callback запроса.
     * @param servRequest Контекст запроса с клиента.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws CoreException
     * @see CoreException
     */
    @GET
    @Path("/mkbs")
    @Produces("application/x-javascript")
    public Object getAllActionTypeNames(@QueryParam("limit")int limit,
                                        @QueryParam("page")int  page,
                                        @QueryParam("sortingField")String sortingField,      //сортировки вкл.
                                        @QueryParam("sortingMethod")String sortingMethod,
                                        @QueryParam("filter[mkbId]")int mkbId,
                                        @QueryParam("filter[classId]")String classId,
                                        @QueryParam("filter[groupId]")String blockId,
                                        @QueryParam("filter[code]")String code,
                                        @QueryParam("filter[diagnosis]")String diagnosis,
                                        @QueryParam("filter[view]")String view,
                                        @QueryParam("filter[display]")String display,
                                        @QueryParam("filter[sex]")int sex,
                                        @QueryParam("callback") String callback,
                                        @Context HttpServletRequest servRequest) {

        AuthData auth = wsImpl.checkTokenCookies(servRequest);

        Boolean flgDisplay =  (display!=null && display.indexOf("true")>=0) ? true : false;
        MKBListRequestDataFilter filter = new MKBListRequestDataFilter(mkbId, classId, blockId, code, diagnosis, view, flgDisplay, sex);
        ListDataRequest request = new ListDataRequest(sortingField, sortingMethod, limit, page, filter);


        Object oip = wsImpl.getAllMkbs(request, auth);
        JSONWithPadding returnValue = new JSONWithPadding(oip, callback);
        return returnValue;
    }

    /**
     * Получение данных из Тезауруса.
     * @param limit Максимальное количество выводимых элементов на странице.
     * @param page Номер выводимой страницы.
     * @param sortingField Наименование поля для сортировки.<pre>
     * &#15; Возможные значения:
     * &#15; "id" - по идентификатору записи (значение по умолчанию);
     * &#15; "groupId" - по идентификатору группы тезауруса;
     * &#15; "code" - по коду тезауруса;</pre>
     * @param sortingMethod Метод сортировки.<pre>
     * &#15; Возможные значения:
     * &#15; "asc" - по возрастанию (значение по умолчанию);
     * &#15; "desc" - по убыванию;</pre>
     * @param thesaurusId  Фильтр по идентификатору тезауруса. (В url: filter[id]=...)
     * @param groupId Фильтр по идентификатору группы тезауруса. (В url: filter[groupId]=...)
     * @param code Фильтр по коду тезауруса. (В url: filter[code]=...)
     * @param callback  callback запроса.
     * @param servRequest Контекст запроса с клиента.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws CoreException
     * @see CoreException
     */
    @GET
    @Path("/thesaurus")
    @Produces("application/x-javascript")
    public Object getThesaurus(@QueryParam("limit")int limit,
                                        @QueryParam("page")int  page,
                                        @QueryParam("sortingField")String sortingField,     //сортировки вкл.
                                        @QueryParam("sortingMethod")String sortingMethod,
                                        @QueryParam("filter[id]")int thesaurusId,
                                        @QueryParam("filter[groupId]")String groupId,
                                        @QueryParam("filter[code]")String code,
                                        @QueryParam("callback") String callback,
                                        @Context HttpServletRequest servRequest) {
        AuthData auth = wsImpl.checkTokenCookies(servRequest);

        ThesaurusListRequestDataFilter filter = new ThesaurusListRequestDataFilter(thesaurusId, groupId, code);
        ListDataRequest request = new ListDataRequest(sortingField, sortingMethod, limit, page, filter);

        Object oip = wsImpl.getThesaurusList(request, auth);
        JSONWithPadding returnValue = new JSONWithPadding(oip, callback);
        return returnValue;
    }
    /**
     * Получение типов квот.
     * @param limit Максимальное количество выводимых элементов на странице.
     * @param page Номер выводимой страницы.
     * @param sortingField Наименование поля для сортировки.<pre>
     * &#15; Возможные значения:
     * &#15; "id" - по идентификатору записи (значение по умолчанию);
     * &#15; "groupId" - по идентификатору группы;
     * &#15; "code" - по коду;</pre>
     * @param sortingMethod Метод сортировки.<pre>
     * &#15; Возможные значения:
     * &#15; "asc" - по возрастанию (значение по умолчанию);
     * &#15; "desc" - по убыванию;</pre>
     * @param typeId Идентификатор типа квоты  (В url: filter[id]=...)
     * @param groupId Фильтр по идентификатору группы типов квот. (В url: filter[groupId]=...)
     * @param code Фильтр по коду типа квоты. (В url: filter[code]=...)
     * @param callback  callback запроса.
     * @param servRequest Контекст запроса с клиента.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws CoreException
     * @see CoreException
     */
    @GET
    @Path("/quotaTypes")
    @Produces("application/x-javascript")
    public Object getQuotaTypes(@QueryParam("limit")int limit,
                                @QueryParam("page")int  page,
                                @QueryParam("sortingField")String sortingField,     //сортировки вкл.
                                @QueryParam("sortingMethod")String sortingMethod,
                                @QueryParam("filter[id]")int typeId,
                                @QueryParam("filter[code]")String code,
                                @QueryParam("filter[groupId]")String groupId,
                                @QueryParam("callback") String callback,
                                @Context HttpServletRequest servRequest) {
        AuthData auth = wsImpl.checkTokenCookies(servRequest);

        QuotaTypesListRequestDataFilter filter = new QuotaTypesListRequestDataFilter(typeId, code, groupId);
        ListDataRequest request = new ListDataRequest(sortingField, sortingMethod, limit, page, filter);

        Object oip = wsImpl.getQuotaTypes(request);
        JSONWithPadding returnValue = new JSONWithPadding(oip, callback);
        return returnValue;
    }

    /**
     * Сервис возвращает указанный справочник.
     * @param limit Максимальное количество выводимых элементов на странице.
     * @param page Номер выводимой страницы.
     * @param sortingField Наименование поля для сортировки.<pre>
     * &#15; Возможные значения параметра:
     * &#15; "id" - по идентификатору записи (значение по умолчанию);
     * &#15; "groupId" - по идентификатору группы тезауруса;
     * &#15; "code" - по коду тезауруса;</pre>
     * @param sortingMethod Метод сортировки.<pre>
     * &#15; Возможные значения:
     * &#15; "asc" - по возрастанию (значение по умолчанию);
     * &#15; "desc" - по убыванию;</pre>
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
     * @param callback  callback запроса.
     * @param servRequest Контекст запроса с клиента.
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
                                @QueryParam("filter[capId]")int capId,           //valueDomain
                                @QueryParam("limit")int limit,
                                @QueryParam("page")int  page,
                                @QueryParam("sortingField")String sortingField,               //сортировки вкл.
                                @QueryParam("sortingMethod")String sortingMethod,
                                @QueryParam("callback") String callback,
                                @Context HttpServletRequest servRequest) {
        //AuthData auth = wsImpl.checkTokenCookies(servRequest);
        DictionaryListRequestDataFilter filter = new DictionaryListRequestDataFilter(dictName, headId, groupId, name, level, parent, type, capId);
        ListDataRequest request = new ListDataRequest(sortingField, sortingMethod, limit, page, filter);
        Object oip = wsImpl.getDictionary(request, dictName);
        JSONWithPadding returnValue = new JSONWithPadding(oip, callback);
        return returnValue;
    }

    //Запросы по назначениям

    //
    /**
     * Создание нового назначения
     * @param data Json с данными о назначении как AssignmentData
     * @param eventId Идентификатор обращения на госпитализацию, в рамках которой создается назначение.
     * @param callback  callback запроса.
     * @param servRequest Контекст запроса с клиента.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws CoreException
     * @see CoreException
     * @see AssignmentData
     */
    @POST
    @Path("/appeals/{eventId}/assignment")
    @Consumes("application/json")
    @Produces("application/x-javascript")
    public Object insertAssignment(AssignmentData data,
                                      @PathParam("eventId")int eventId,
                                      //@QueryParam("token") String token,
                                      @QueryParam("callback") String callback,
                                      @Context HttpServletRequest servRequest) {
        AuthData auth = wsImpl.checkTokenCookies(servRequest);
        //AuthToken authToken = new AuthToken(token);
        //AuthData auth = wsImpl.getStorageAuthData(authToken);

        Object oip = wsImpl.insertAssignment(data, eventId, auth);
        JSONWithPadding returnValue = new JSONWithPadding(oip, callback);
        return returnValue;
    }

    /**
     * Редактирование назначения
     * @param data Json с данными о назначении как AssignmentData
     * @param eventId Идентификатор обращения на госпитализацию, в рамках которой создается назначение.
     * @param callback  callback запроса.
     * @param servRequest Контекст запроса с клиента.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws CoreException
     * @see CoreException
     * @see AssignmentData
     */
    @PUT
    @Path("/appeals/{eventId}/assignment")
    @Consumes("application/json")
    @Produces("application/x-javascript")
    public Object modifyAssignment(AssignmentData data,
                                   @PathParam("eventId")int eventId,
                                   //@QueryParam("token") String token,
                                   @QueryParam("callback") String callback,
                                   @Context HttpServletRequest servRequest) {
        AuthData auth = wsImpl.checkTokenCookies(servRequest);
        //AuthToken authToken = new AuthToken(token);
        //AuthData auth = wsImpl.getStorageAuthData(authToken);

        Object oip = wsImpl.insertAssignment(data, eventId, auth);
        JSONWithPadding returnValue = new JSONWithPadding(oip, callback);
        return returnValue;
    }

    //Получение назначения по идентификатору
    /**
     * Получение данных об назначении
     * @param actionId Идентификатор назначения.
     * @param callback  callback запроса.
     * @param servRequest Контекст запроса с клиента.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws CoreException
     * @see CoreException
     */
    @GET
    @Path("/assignment/{actionId}")
    @Produces("application/x-javascript")
    public Object getAssignmentById(@PathParam("actionId")int actionId,
                                    @QueryParam("callback") String callback,
                                    @Context HttpServletRequest servRequest) {
        AuthData auth = wsImpl.checkTokenCookies(servRequest);

        Object oip = wsImpl.getAssignmentById(actionId, auth);
        JSONWithPadding returnValue = new JSONWithPadding(oip, callback);
        return returnValue;

    }

    /**
     * Получение данных из справочника медицинских препоратов (rls).
     * @param limit Максимальное количество выводимых элементов на странице.
     * @param page Номер выводимой страницы.
     * @param sortingField Наименование поля для сортировки.<pre>
     * &#15; Возможные значения:
     * &#15; "name" | "tradeName" - по торговому наименованию препората (значение по умолчанию);
     * &#15; "code" - по коду препората;
     * &#15; "dosage" - по номинальной дозировке препората;
     * &#15; "form" - по форме комплектования препората;</pre>
     * @param sortingMethod Метод сортировки.<pre>
     * &#15; Возможные значения:
     * &#15; "asc" - по возрастанию (значение по умолчанию);
     * &#15; "desc" - по убыванию;</pre>
     * @param name  Фильтр по обозначению препората. (В url: filter[name]=...)<pre>
     * &#15; Проверяется сперва торговое обозначение, после латинское обозначение препората.</pre>
     * @param code Фильтр по коду медицинского препората. (В url: filter[code]=...)
     * @param dosage Фильтр по дозировке. (В url: filter[dosage]=...)
     * @param form Фильтр по форме выпуска препората. (В url: filter[form]=...)
     * @param callback  callback запроса.
     * @param servRequest Контекст запроса с клиента.
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
                              @QueryParam("filter[form]")String form,
                              @QueryParam("limit")int limit,
                              @QueryParam("page")int  page,
                              @QueryParam("sortingField")String sortingField,               //сортировки вкл.
                              @QueryParam("sortingMethod")String sortingMethod,
                              @QueryParam("callback") String callback,
                              @Context HttpServletRequest servRequest) {
        //AuthData auth = wsImpl.checkTokenCookies(servRequest);
        RlsDataListFilter filter = new RlsDataListFilter(code, name, dosage, form);
        RlsDataListRequestData request = new RlsDataListRequestData(sortingField, sortingMethod, limit, page, filter);
        Object oip = wsImpl.getFilteredRlsList(request);
        JSONWithPadding returnValue = new JSONWithPadding(oip, callback);
        return returnValue;
    }

    /**
     * Сервис по получению списка обращений <br>
     * Путь: ../tms-registry/eventTypes
     * @param limit Максимальное количество выводимых элементов на странице.
     * @param page Номер выводимой страницы.
     * @param sortingField Наименование поля для сортировки.<pre>
     * &#15; Возможные значения:
     * &#15; "id" - по торговому наименованию препората (значение по умолчанию);
     * &#15; "name" - по коду препората;</pre>
     * @param sortingMethod Метод сортировки.<pre>
     * &#15; Возможные значения:
     * &#15; "asc" - по возрастанию (значение по умолчанию);
     * &#15; "desc" - по убыванию;</pre>
     * @param requestType Идентификатор типа стационара rbRequestType.id
     * @param finance  Идентификатор типа оплаты rbFinance.id
     * @param callback  callback запроса.
     * @param servRequest Контекст запроса с клиента.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws CoreException
     */
    @GET
    @Path("/eventTypes")
    @Produces("application/x-javascript")
    public Object getEventTypes(@QueryParam("limit")int limit,
                                @QueryParam("page")int  page,
                                @QueryParam("sortingField")String sortingField,      //сортировки вкл.
                                @QueryParam("sortingMethod")String sortingMethod,
                                @QueryParam("filter[requestType]")int requestType,
                                @QueryParam("filter[finance]")int finance,
                                @QueryParam("callback") String callback,
                                @Context HttpServletRequest servRequest) {
        AuthData auth = wsImpl.checkTokenCookies(servRequest);

        EventTypesListRequestDataFilter filter = new EventTypesListRequestDataFilter(finance, requestType);
        ListDataRequest request = new ListDataRequest(sortingField, sortingMethod, limit, page, filter);

        Object oip = wsImpl.getEventTypes(request, auth);
        JSONWithPadding returnValue = new JSONWithPadding(oip, callback);
        return returnValue;
    }

    /**
     * Сервис сохранения квоты <br>
     * url: /appeals/{appealId}/quotes
     * @param data Json с данными о квоте как QuotaData
     * @param appealId Идентификатор госпитализации.
     * @param callback callback запроса.
     * @param servRequest Контекст запроса с клиента.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws CoreException
     */
    @POST
    @Path("/appeals/{appealId}/quotes")
    @Produces("application/x-javascript")
    public Object createQuota(QuotaData data,
                              @PathParam("appealId")int appealId,
                              //@QueryParam("token") String token,
                              @QueryParam("callback") String callback,
                              @Context HttpServletRequest servRequest) {
        AuthData auth = wsImpl.checkTokenCookies(servRequest);
        //AuthToken authToken = new AuthToken(token);
        //AuthData auth = wsImpl.getStorageAuthData(authToken);

        Object oip = wsImpl.insertOrUpdateQuota(data, appealId, auth);
        JSONWithPadding returnValue = new JSONWithPadding(oip, callback);
        return returnValue;
    }

    /**
     * Сервис редактирования квоты <br>
     * url: /appeals/{appealId}/quotes
     * @param data Json с данными о квоте как QuotaData
     * @param appealId Идентификатор госпитализации.
     * @param callback callback запроса.
     * @param servRequest Контекст запроса с клиента.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws CoreException
     */
    @PUT
    @Path("/appeals/{appealId}/quotes")
    @Produces("application/x-javascript")
    public Object modifyQuota(QuotaData data,
                              @PathParam("appealId")int appealId,
                              //@QueryParam("token") String token,
                              @QueryParam("callback") String callback,
                              @Context HttpServletRequest servRequest) {
        AuthData auth = wsImpl.checkTokenCookies(servRequest);
        //AuthToken authToken = new AuthToken(token);
        //AuthData auth = wsImpl.getStorageAuthData(authToken);

        Object oip = wsImpl.insertOrUpdateQuota(data, appealId, auth);
        JSONWithPadding returnValue = new JSONWithPadding(oip, callback);
        return returnValue;
    }

    /**
     * Сервис получения данных о квоте <br>
     * url: /appeals/{appealId}/quotes
     * @param appealId Идентификатор госпитализации.
     * @param callback callback запроса.
     * @param servRequest Контекст запроса с клиента.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws CoreException
     */
    @GET
    @Path("/appeals/{appealId}/quotes")
    @Produces("application/x-javascript")
    public Object getQuotaHistory(@PathParam("appealId") int appealId,
                                  @QueryParam("limit")int limit,
                                  @QueryParam("page")int  page,
                                  @QueryParam("sortingField")String sortingField,      //сортировки вкл.
                                  @QueryParam("sortingMethod")String sortingMethod,
                                  @QueryParam("callback") String callback,
                                  @Context HttpServletRequest servRequest) {
        AuthData auth = wsImpl.checkTokenCookies(servRequest);

        QuotaRequestData request = new QuotaRequestData(null, sortingField, sortingMethod, limit, page);

        Object oip = wsImpl.getQuotaHistory(appealId, request); //request
        JSONWithPadding returnValue = new JSONWithPadding(oip, callback);
        return returnValue;
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
     * @param sortingField
     * @param sortingMethod
     * @param callback
     * @param servRequest
     * @return
     */
    @GET
    @Path("/biomaterial/info")
    @Produces("application/x-javascript")
    public Object getTakingOfBiomaterial(@QueryParam("filter[departmentId]")int departmentId,
                                         @QueryParam("filter[beginDate]")long beginDate,
                                         @QueryParam("filter[endDate]")long endDate,
                                         @QueryParam("filter[status]") String status,
                                         @QueryParam("filter[biomaterial]") int biomaterial,
                                         @QueryParam("sortingField")String sortingField,
                                         @QueryParam("sortingMethod")String sortingMethod,
                                         @QueryParam("callback") String callback,
                                         @Context HttpServletRequest servRequest)   {

        AuthData auth = wsImpl.checkTokenCookies(servRequest);

        //Отделение обязательное поле, если не задано в запросе, то берем из роли специалиста
        int depId = (departmentId>0) ? departmentId : auth.getUser().getOrgStructure().getId().intValue();
        short statusS = (status!=null && !status.isEmpty()) ? Short.parseShort(status): -1;

        TakingOfBiomaterialRequesDataFilter filter = new TakingOfBiomaterialRequesDataFilter(depId,
                                                                                             beginDate,
                                                                                             endDate,
                                                                                             statusS,
                                                                                             biomaterial);
        TakingOfBiomaterialRequesData request = new TakingOfBiomaterialRequesData(sortingField, sortingMethod, filter);
        Object oip = wsImpl.getTakingOfBiomaterial(request, auth);
        JSONWithPadding returnValue = new JSONWithPadding(oip, callback);
        return returnValue;
    }

    /**
     * Метод проставляет статус для тиккетов
     * @param data Список статусов для JobTicket
     * @param callback callback запроса.
     * @param servRequest Контекст запроса с клиента.
     * @return true - завершено успешно, false - завершено с ошибками
     */
    @PUT
    @Path("/jobTickets/status")
    @Produces("application/x-javascript")
    public Object setStatusesForJobTickets(JobTicketStatusDataList data,
                                           //@QueryParam("token") String token,
                                           @QueryParam("callback") String callback,
                                           @Context HttpServletRequest servRequest) {
        AuthData auth = wsImpl.checkTokenCookies(servRequest);
        //AuthToken authToken = new AuthToken(token);
        //AuthData auth = wsImpl.getStorageAuthData(authToken);

        Object oip = wsImpl.updateJobTicketsStatuses(data, auth);
        JSONWithPadding returnValue = new JSONWithPadding(oip, callback);
        return returnValue;
    }
}