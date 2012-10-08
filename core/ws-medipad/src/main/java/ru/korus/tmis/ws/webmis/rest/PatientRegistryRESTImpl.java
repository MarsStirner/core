package ru.korus.tmis.ws.webmis.rest;

import com.sun.jersey.api.json.JSONWithPadding;
import ru.korus.tmis.auxiliary.AuxiliaryFunctions;
import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.data.*;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.ws.impl.MedipadWSImpl;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

//import org.json.JSONArray;
//import org.json.JSONObject;

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


    //Получение списка пациентов с фильтрацией
    @GET
    @Path("/patients")
    @Produces("application/x-javascript")
    public Object getAllPatientsP(@QueryParam("limit") String limit,
                                  @QueryParam("page") String page,
                                  @QueryParam("sortingField") String sortingField,
                                  @QueryParam("sortingMethod") String sortingMethod,
                                  @QueryParam("filter[patientCode]") String patientCode,
                                  @QueryParam("filter[fullName]") String fullName,
                                  @QueryParam("filter[birthDate]") Long birthDate,
                                  @QueryParam("filter[document]") String document,
                                  @QueryParam("callback") String callback,
                                  @Context HttpServletRequest servRequest) {
        AuthData auth = wsImpl.checkTokenCookies(servRequest);

        Date bDate = birthDate == null ? null : new Date(birthDate);
        PatientRequestData requestData = new PatientRequestData(patientCode, fullName, bDate, document, sortingField, sortingMethod, limit, page);
        JSONWithPadding returnValue = new JSONWithPadding(wsImpl.getAllPatients(requestData, auth), callback);
        return returnValue;
    }

    //Получение пациента по идентификатору
    @GET
    @Path("/patients/{id}")
    @Produces("application/x-javascript")
    public Object getPatientById(@PathParam("id") int id,
                                 @QueryParam("callback") String callback,
                                 @Context HttpServletRequest servRequest) {
        AuthData auth = wsImpl.checkTokenCookies(servRequest);
        JSONWithPadding returnValue = new JSONWithPadding(wsImpl.getPatientById(id, auth), callback);
        return returnValue;
    }

    //Редактирование данных о пациенте
    @PUT
    @Path("/patients/{id}")
    @Consumes("application/json")
    @Produces("application/x-javascript")
    public Object updatePatient(PatientCardData patientData,
                                @PathParam("id") int id,
                                //@QueryParam("token") String token,
                                @QueryParam("callback") String callback,
                                @Context HttpServletRequest servRequest) {
        AuthData auth = wsImpl.checkTokenCookies(servRequest);
        //AuthToken authToken = new AuthToken(token);
        //AuthData auth = wsImpl.getStorageAuthData(authToken);
        JSONWithPadding returnValue = new JSONWithPadding(wsImpl.updatePatient(patientData, auth), callback);
        return returnValue;
    }

    //Запись данных о пациенте
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

    //Создание нового обращения
    @POST
    @Path("/patients/{id}/appeals")
    @Consumes("application/json")
    @Produces("application/x-javascript")
    public Object insertPatientAppeal(AppealData data,
                                      @PathParam("id") int patientId,
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

    //Редактирование обращения
    @PUT
    @Path("/patients/{id}/appeals")
    @Consumes("application/json")
    @Produces("application/x-javascript")
    public Object updatePatientAppeal(AppealData data,
                                      @PathParam("id") int patientId,
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

    //Запрос обращения по id
    @GET
    @Path("/appeals/{id}")
    @Produces("application/x-javascript")
    public Object getAppealById(@PathParam("id") int id,
                                @QueryParam("callback") String callback,
                                @Context HttpServletRequest servRequest) {
        AuthData auth = wsImpl.checkTokenCookies(servRequest);

        String oip = wsImpl.getAppealById(id, auth);
        JSONWithPadding returnValue = new JSONWithPadding(oip, callback);
        return returnValue;
    }

    //Запрос печатной формы обращения по id
    @GET
    @Path("print/appeals/{id}")
    @Produces("application/x-javascript")
    public Object getAppealPrintFormById(@PathParam("id") int id,
                                         @QueryParam("callback") String callback,
                                         @Context HttpServletRequest servRequest) {
        AuthData auth = wsImpl.checkTokenCookies(servRequest);

        String oip = wsImpl.getAppealPrintFormById(id, auth);
        JSONWithPadding returnValue = new JSONWithPadding(oip, callback);
        return returnValue;
    }

    //Проверка на наличие номера чего-либо (госпитализации, полиса, СНИЛС) в БД
    @GET
    @Path("checkExistance/{name}")
    @Produces("application/x-javascript")
    public Object checkAppealNumber(@PathParam("name") String name,
                                    @QueryParam("typeId") int typeId,
                                    @QueryParam("number") String number,
                                    @QueryParam("serial") String serial,
                                    @QueryParam("callback") String callback,
                                    @Context HttpServletRequest servRequest) {
        wsImpl.checkTokenCookies(servRequest);
        TrueFalseContainer oip = wsImpl.checkExistanceNumber(name, typeId, number, serial);
        JSONWithPadding returnValue = new JSONWithPadding(oip, callback);
        return returnValue;
    }

    //Запрос перечня обращений по пациенту (id, number, date, [diagnosis])
    @GET
    @Path("/patients/{id}/appeals")
    @Produces("application/x-javascript")
    public Object getAllAppealsForPatient(@PathParam("id") int patientId,
                                          @QueryParam("limit") int limit,
                                          @QueryParam("page") int page,
                                          @QueryParam("sortingField") String sortingField,  //сортировки вкл.
                                          @QueryParam("sortingMethod") String sortingMethod,
                                          @QueryParam("filter[number]") String number,
                                          @QueryParam("filter[beginDate]") long beginDate,
                                          @QueryParam("filter[endDate]") long endDate,
                                          @QueryParam("filter[departmentId]") int departmentId,
                                          @QueryParam("filter[doctorId]") int doctorId,
                                          @QueryParam("filter[diagnosis]") String mkbCode,
                                          @QueryParam("callback") String callback,
                                          @Context HttpServletRequest servRequest) {
        AuthData auth = wsImpl.checkTokenCookies(servRequest);

        Set<String> codes = new LinkedHashSet<String>();
        codes.add("11");
        codes.add("12");
        AppealSimplifiedRequestDataFilter filter = new AppealSimplifiedRequestDataFilter(patientId, beginDate, endDate, departmentId, doctorId, mkbCode, number, codes);
        AppealSimplifiedRequestData request = new AppealSimplifiedRequestData(sortingField, sortingMethod, limit, page, filter);

        AppealSimplifiedDataList appealList = (AppealSimplifiedDataList) wsImpl.getAllAppealsByPatient(request, auth);
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

    //запрос на список поступивших (!метод расширил - поступившие на день: beginDate,
    //                                                          за период: beginDate и endDate
    //                                       закр. госпитализация на день: endDate)
    //диагноз - направительный
    @GET
    @Path("/appeals")
    @Produces("application/x-javascript")
    public Object getAllAppealsForReceivedPatientByPeriod(@QueryParam("limit") int limit,
                                                          @QueryParam("page") int page,
                                                          @QueryParam("sortingField") String sortingField,    //сортировки вкл.
                                                          @QueryParam("sortingMethod") String sortingMethod,
                                                          @QueryParam("filter[eventId]") int eventId,
                                                          @QueryParam("filter[fullName]") String fullName,
                                                          @QueryParam("filter[birthDate]") Long birthDate,
                                                          @QueryParam("filter[externalId]") String externalId,
                                                          @QueryParam("filter[beginDate]") Long beginDate,
                                                          @QueryParam("filter[endDate]") Long endDate,
                                                          @QueryParam("callback") String callback,
                                                          @Context HttpServletRequest servRequest) {
        AuthData auth = wsImpl.checkTokenCookies(servRequest);

        ReceivedRequestDataFilter filter = new ReceivedRequestDataFilter(eventId, fullName, birthDate, externalId, beginDate, endDate);
        ReceivedRequestData requestData = new ReceivedRequestData(sortingField, sortingMethod, limit, page, filter);

        ReceivedPatientsData rpd = (ReceivedPatientsData) wsImpl.getAllAppealsForReceivedPatientByPeriod(requestData, auth);
        JSONWithPadding returnValue = new JSONWithPadding(rpd, callback);
        return returnValue;
    }

    //запрос на список отделения/врача
    @GET
    @Path("/departments/patients")
    @Produces("application/x-javascript")
    public Object getAllPatientsForDepartmentOrUserByPeriod(@QueryParam("limit") int limit,
                                                            @QueryParam("page") int page,
                                                            @QueryParam("sortingField") String sortingField,     //сортировки вкл
                                                            @QueryParam("sortingMethod") String sortingMethod,
                                                            @QueryParam("filter[beginDate]") long beginDate,
                                                            @QueryParam("filter[endDate]") long endDate,
                                                            @QueryParam("filter[departmentId]") int departmentId,
                                                            @QueryParam("filter[doctorId]") int doctorId,
                                                            @QueryParam("callback") String callback,
                                                            @Context HttpServletRequest servRequest) {
        AuthData auth = wsImpl.checkTokenCookies(servRequest);

        PatientsListRequestData requestData = new PatientsListRequestData(departmentId, doctorId, beginDate, endDate,
                sortingField, sortingMethod, limit, page);
        Object rpd = wsImpl.getAllPatientsForDepartmentIdAndDoctorIdByPeriod(requestData, 0, auth);
        JSONWithPadding returnValue = new JSONWithPadding(rpd, callback);
        return returnValue;
    }

    //TODO: В будущем определять отделение по авторизационной роли
    //запрос на список отделения/сестра отделения
    @GET
    @Path("/departments/patients/nurse")
    @Produces("application/x-javascript")
    public Object getAllPatientsForDepartmentOrUserByPeriodDepartmentNurseRole(
            @QueryParam("limit") int limit,
            @QueryParam("page") int page,
            @QueryParam("sortingField") String sortingField,   //сортировки вкл
            @QueryParam("sortingMethod") String sortingMethod,
            @QueryParam("filter[beginDate]") long beginDate,
            @QueryParam("filter[endDate]") long endDate,
            @QueryParam("filter[departmentId]") int departmentId,
            @QueryParam("filter[doctorId]") int doctorId,
            @QueryParam("callback") String callback,
            @Context HttpServletRequest servRequest) {
        AuthData auth = wsImpl.checkTokenCookies(servRequest);

        PatientsListRequestData requestData = new PatientsListRequestData(departmentId, doctorId, beginDate, endDate,
                sortingField, sortingMethod, limit, page);
        Object rpd = wsImpl.getAllPatientsForDepartmentIdAndDoctorIdByPeriod(requestData, 1, auth);
        JSONWithPadding returnValue = new JSONWithPadding(rpd, callback);
        return returnValue;
    }

    //Запрос на структуру для первичного осмотра
    @GET
    @Path("appeals/{eventId}/examinations/primary/struct")
    @Produces("application/x-javascript")
    public Object getStructOfPrimaryMedExam(@QueryParam("callback") String callback,
                                            @PathParam("eventId") int eventId,
                                            @Context HttpServletRequest servRequest) {
        AuthData auth = wsImpl.checkTokenCookies(servRequest);
        JSONWithPadding returnValue = new JSONWithPadding(wsImpl.getStructOfPrimaryMedExam(auth), callback);
        return returnValue;
    }

    //Создание первичного осмотра
    @POST
    @Path("appeals/{eventId}/examinations/primary")
    @Consumes("application/json")
    @Produces("application/x-javascript")
    public Object insertPrimaryMedExamForPatient(JSONCommonData data,
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

    //Редактирование первичного осмотра
    @PUT
    @Path("appeals/{eventId}/examinations/primary/{actionId}")
    @Consumes("application/json")
    @Produces("application/x-javascript")
    public Object modifyPrimaryMedExamForPatient(JSONCommonData data,
                                                 @PathParam("eventId") int eventId,
                                                 @PathParam("actionId") int actionId,
                                                 @QueryParam("callback") String callback,
                                                 @Context HttpServletRequest servRequest) {
        AuthData auth = wsImpl.checkTokenCookies(servRequest);

        JSONWithPadding returnValue = new JSONWithPadding(wsImpl.modifyPrimaryMedExamForPatient(actionId, data, auth), callback);
        return returnValue;
    }

    //Запрос на первичный осмотр по ид
    @GET
    @Path("appeals/{eventId}/examinations/primary/{actionId}")
    @Produces("application/x-javascript")
    public Object getPrimaryMedExamById(@PathParam("eventId") int eventId,
                                        @PathParam("actionId") int actionId,
                                        @QueryParam("callback") String callback,
                                        @Context HttpServletRequest servRequest) {
        AuthData auth = wsImpl.checkTokenCookies(servRequest);
        JSONWithPadding returnValue = new JSONWithPadding(wsImpl.getPrimaryAssessmentById(actionId, auth), callback);
        return returnValue;
    }

    //Запрос перечня осмотров по пациенту  /*(таск - просмотр перечня протоколов)*/
    @GET
    @Path("appeals/{id}/examinations/")//("/patients/{patientId}/examinations/")
    @Produces("application/x-javascript")
    public Object getListOfAssessmentsForPatientByEvent(@PathParam("id") int eventId,
                                                        @QueryParam("limit") int limit,
                                                        @QueryParam("page") int page,
                                                        @QueryParam("sortingField") String sortingField,    //сортировки вкл.
                                                        @QueryParam("sortingMethod") String sortingMethod,
                                                        @QueryParam("filter[code]") String assessmentTypeCode,
                                                        @QueryParam("filter[assessmentDate]") long assessmentDate,
                                                        @QueryParam("filter[doctorName]") String doctorName,
                                                        @QueryParam("filter[speciality]") String speciality,
                                                        @QueryParam("filter[assessmentName]") String assessmentName,
                                                        @QueryParam("filter[departmentName]") String departmentName,
                                                        @QueryParam("callback") String callback,
                                                        @Context HttpServletRequest servRequest) {
        AuthData auth = wsImpl.checkTokenCookies(servRequest);

        AssessmentsListRequestDataFilter filter = new AssessmentsListRequestDataFilter(assessmentTypeCode, assessmentDate, doctorName, speciality, assessmentName, departmentName);
        AssessmentsListRequestData alrd = new AssessmentsListRequestData(sortingField, sortingMethod, limit, page, /*assessmentTypeCode, patientId,*/ eventId, filter);
        AssessmentsListData assList = wsImpl.getListOfAssessmentsForPatientByEvent(alrd, auth);
        JSONWithPadding returnValue = new JSONWithPadding(assList, callback);
        return returnValue;
    }

    //Запрос данных осмотра по ид /*(таск - просмотр протокола осмотра)*/
    @GET
    @Path("/examinations/{actionId}")
    @Produces("application/x-javascript")
    public Object getStructOfPrimaryMedExam(@PathParam("actionId") int actionId,
                                            @QueryParam("callback") String callback,
                                            @Context HttpServletRequest servRequest) {
        AuthData auth = wsImpl.checkTokenCookies(servRequest);
        JSONWithPadding returnValue = new JSONWithPadding(wsImpl.getPrimaryAssessmentById(actionId, auth), callback);
        return returnValue;
    }

    //КОЕЧНЫЙ ФОНД

    //Регистрация на койке
    @POST
    @Path("appeals/{eventId}/hospitalbed/")
    @Consumes("application/json")
    @Produces("application/x-javascript")
    public Object registryPatientToHospitalBed(HospitalBedData data,
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

    //Регистрация на койке (редактирование)
    @PUT
    @Path("/hospitalbed/{actionId}")
    @Consumes("application/json")
    @Produces("application/x-javascript")
    public Object modifyPatientToHospitalBed(HospitalBedData data,
                                             @PathParam("actionId") int actionId,
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


    //Отмена регистрациии на койке
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

    //Данные об регистрации на койке
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

    //Список движения по отделениям
    @GET
    @Path("appeals/{eventId}/hospitalbed/")
    @Produces("application/x-javascript")
    public Object getMovingListForEvent(
            @PathParam("eventId") int eventId,
            @QueryParam("limit") int limit,
            @QueryParam("page") int page,
            @QueryParam("sortingField") String sortingField,   //сортировки выкл.
            @QueryParam("sortingMethod") String sortingMethod,
            @QueryParam("callback") String callback,
            @Context HttpServletRequest servRequest) {

        AuthData auth = wsImpl.checkTokenCookies(servRequest);

        HospitalBedDataListFilter filter = new HospitalBedDataListFilter(eventId);
        HospitalBedDataRequest request = new HospitalBedDataRequest(sortingField, sortingMethod, limit, page, filter);
        Object oip = wsImpl.getMovingListForEvent(request, auth);
        JSONWithPadding returnValue = new JSONWithPadding(oip, callback);
        return returnValue;
    }

    //Форма 007
    @GET
    @Path("/seventhform/{departmentId}")
    @Produces("application/x-javascript")
    public Object getSeventhFormForDepartment(
            @PathParam("departmentId") int departmentId,
            @QueryParam("callback") String callback,
            @Context HttpServletRequest servRequest) {
        //AuthData auth = wsImpl.checkTokenCookies(servRequest);
        FormOfAccountingMovementOfPatientsData oip = wsImpl.getFormOfAccountingMovementOfPatients(departmentId);
        JSONWithPadding returnValue = new JSONWithPadding(oip, callback);
        return returnValue;
    }

    //Направление/Перевод в отделение
    @POST
    @Path("appeals/{eventId}/moving/")
    @Consumes("application/json")
    @Produces("application/x-javascript")
    public Object movingPatientToDepartment(HospitalBedData data,
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

    //Получение списка талонов СПО для пациента
    @GET
    @Path("/patients/{patientId}/talons")
    @Produces("application/x-javascript")
    public Object getAllTalonsForPatient(@PathParam("patientId") int patientId,
                                         @QueryParam("limit") int limit,
                                         @QueryParam("page") int page,
                                         @QueryParam("sortingField") String sortingField,   //сортировки вкл.
                                         @QueryParam("sortingMethod") String sortingMethod,
                                         @QueryParam("callback") String callback,
                                         @Context HttpServletRequest servRequest) {
        AuthData auth = wsImpl.checkTokenCookies(servRequest);

        TalonSPODataListFilter filter = new TalonSPODataListFilter(patientId, "33");
        TalonSPOListRequestData request = new TalonSPOListRequestData(sortingField, sortingMethod, limit, page, filter);

        Object oip = wsImpl.getAllTalonsForPatient(request);
        JSONWithPadding returnValue = new JSONWithPadding(oip, callback);
        return returnValue;
    }


    //Возвращает список всего персонала
    @GET
    @Path("/persons")
    @Produces("application/x-javascript")
    public Object getAllPersons(@QueryParam("limit") int limit,
                                @QueryParam("page") int page,
                                @QueryParam("sortingField") String sortingField,              //сортировки выкл
                                @QueryParam("sortingMethod") String sortingMethod,
                                @QueryParam("callback") String callback,
                                @Context HttpServletRequest servRequest) {
        AuthData auth = wsImpl.checkTokenCookies(servRequest);
        ListDataRequest request = new ListDataRequest(sortingField, sortingMethod, limit, page, null);
        JSONWithPadding returnValue = new JSONWithPadding(wsImpl.getAllPersons(request), callback);
        return returnValue;
    }

    //Возвращает список отделений
    @GET
    @Path("/departments")
    @Produces("application/x-javascript")
    public Object getAllDepartments(@QueryParam("limit") int limit,
                                    @QueryParam("page") int page,
                                    @QueryParam("sortingField") String sortingField,            //сортировки выкл
                                    @QueryParam("sortingMethod") String sortingMethod,
                                    @QueryParam("callback") String callback,
                                    @Context HttpServletRequest servRequest) {
        AuthData auth = wsImpl.checkTokenCookies(servRequest);
        ListDataRequest request = new ListDataRequest(sortingField, sortingMethod, limit, page, null);
        JSONWithPadding returnValue = new JSONWithPadding(wsImpl.getAllDepartments(request), callback);
        return returnValue;
    }

    //Просмотр списка направлений на лабораторные исследования
    @GET
    @Path("/appeals/{eventId}/diagnostics/laboratory")
    @Produces("application/x-javascript")
    public Object getListOfLaboratoryDiagnosticsForPatientByEvent(@PathParam("eventId") int eventId,
                                                                  @QueryParam("limit") int limit,
                                                                  @QueryParam("page") int page,
                                                                  @QueryParam("sortingField") String sortingField,           //сортировки вкл
                                                                  @QueryParam("sortingMethod") String sortingMethod,
                                                                  @QueryParam("filter[code]") String diaTypeCode,
                                                                  @QueryParam("filter[diagnosticDate]") long diagnosticDate,
                                                                  @QueryParam("filter[directionDate]") long directionDate,
                                                                  @QueryParam("filter[diagnosticName]") String diagnosticName,
                                                                  @QueryParam("filter[assignPersonId]") int assignPersonId,
                                                                  @QueryParam("filter[execPersonId]") int execPersonId,
                                                                  @QueryParam("filter[statusId]") int statusId,
                                                                  @QueryParam("filter[urgent]") Boolean urgent,
                                                                  @QueryParam("callback") String callback,
                                                                  @Context HttpServletRequest servRequest) {
        AuthData auth = wsImpl.checkTokenCookies(servRequest);

        DiagnosticsListRequestDataFilter filter = new DiagnosticsListRequestDataFilter(diaTypeCode,
                eventId,
                diagnosticDate,
                directionDate,
                diagnosticName,
                assignPersonId,
                execPersonId,
                "",
                statusId,
                (urgent == null) ? -1 : (urgent) ? 1 : 0,
                "laboratory");

        DiagnosticsListRequestData requestData = new DiagnosticsListRequestData(sortingField,
                sortingMethod,
                limit,
                page,
                filter);
        JSONWithPadding returnValue = new JSONWithPadding(wsImpl.getListOfDiagnosticsForPatientByEvent(requestData), callback);
        return returnValue;
    }

    //Просмотр списка направлений на инструментальные исследования
    @GET
    @Path("/appeals/{eventId}/diagnostics/instrumental")
    @Produces("application/x-javascript")
    public Object getListOfInstrumentalDiagnosticsForPatientByEvent(@PathParam("eventId") int eventId,
                                                                    @QueryParam("limit") int limit,
                                                                    @QueryParam("page") int page,
                                                                    @QueryParam("sortingField") String sortingField,                  //сортировки вкл
                                                                    @QueryParam("sortingMethod") String sortingMethod,
                                                                    @QueryParam("filter[code]") String diaTypeCode,
                                                                    @QueryParam("filter[diagnosticDate]") long diagnosticDate,
                                                                    @QueryParam("filter[diagnosticName]") String diagnosticName,
                                                                    @QueryParam("filter[assignPersonId]") int assignPersonId,
                                                                    @QueryParam("filter[execPersonId]") int execPersonId,
                                                                    @QueryParam("filter[statusId]") int statusId,
                                                                    @QueryParam("filter[office]") String office,
                                                                    @QueryParam("callback") String callback,
                                                                    @Context HttpServletRequest servRequest) {
        AuthData auth = wsImpl.checkTokenCookies(servRequest);

        DiagnosticsListRequestDataFilter filter = new DiagnosticsListRequestDataFilter(diaTypeCode,
                eventId,
                diagnosticDate,
                0,
                diagnosticName,
                assignPersonId,
                execPersonId,
                office,
                statusId,
                -1,
                "instrumental");

        DiagnosticsListRequestData requestData = new DiagnosticsListRequestData(sortingField,
                sortingMethod,
                limit,
                page,
                filter);
        JSONWithPadding returnValue = new JSONWithPadding(wsImpl.getListOfDiagnosticsForPatientByEvent(requestData), callback);
        return returnValue;
    }

    //Просмотр списка направлений на консультации к врачу
    @GET
    @Path("/appeals/{eventId}/diagnostics/consultations")
    @Produces("application/x-javascript")
    public Object getListOfDoctorConsultationForPatientByEvent(@PathParam("eventId") int eventId,
                                                               @QueryParam("limit") int limit,
                                                               @QueryParam("page") int page,
                                                               @QueryParam("sortingField") String sortingField,                //сортировки вкл
                                                               @QueryParam("sortingMethod") String sortingMethod,
                                                               @QueryParam("filter[code]") String diaTypeCode,
                                                               @QueryParam("filter[diagnosticDate]") long diagnosticDate,
                                                               @QueryParam("filter[diagnosticName]") String diagnosticName,
                                                               @QueryParam("filter[assignerId]") int assignPersonId,
                                                               @QueryParam("filter[executorId]") int execPersonId,
                                                               @QueryParam("filter[statusId]") int statusId,
                                                               @QueryParam("filter[office]") String office,
                                                               @QueryParam("callback") String callback,
                                                               @Context HttpServletRequest servRequest) {
        AuthData auth = wsImpl.checkTokenCookies(servRequest);

        DiagnosticsListRequestDataFilter filter = new DiagnosticsListRequestDataFilter(diaTypeCode,
                eventId,
                diagnosticDate,
                0,
                diagnosticName,
                assignPersonId,
                execPersonId,
                office,
                statusId,
                -1,
                "consultations");

        DiagnosticsListRequestData requestData = new DiagnosticsListRequestData(sortingField,
                sortingMethod,
                limit,
                page,
                filter);
        JSONWithPadding returnValue = new JSONWithPadding(wsImpl.getListOfDiagnosticsForPatientByEvent(requestData), callback);
        return returnValue;
    }

    //Просмотр результатов лабораторных исследований
    @GET
    @Path("diagnostics/laboratory/{actionId}")
    @Produces("application/x-javascript")
    public Object getInfoAboutDiagnosticsForPatientByEvent(@PathParam("actionId") int actionId,
                                                           @QueryParam("callback") String callback,
                                                           @Context HttpServletRequest servRequest) {
        AuthData auth = wsImpl.checkTokenCookies(servRequest);

        JSONWithPadding returnValue = new JSONWithPadding(wsImpl.getInfoAboutDiagnosticsForPatientByEvent(actionId), callback);
        return returnValue;
    }

    //Получение списка свободных на выбранное время врачей по специальности
    @GET
    @Path("/persons/free")
    @Produces("application/x-javascript")
    public Object getFreePersons(@QueryParam("limit") int limit,
                                 @QueryParam("page") int page,
                                 @QueryParam("sortingField") String sortingField,                //сортировки выкл.
                                 @QueryParam("sortingMethod") String sortingMethod,
                                 @QueryParam("filter[speciality]") int speciality,
                                 @QueryParam("filter[doctorId]") int doctorId,
                                 @QueryParam("filter[beginDate]") long beginDate,
                                 @QueryParam("filter[endDate]") long endDate,
                                 @QueryParam("callback") String callback,
                                 @Context HttpServletRequest servRequest) {
        AuthData auth = wsImpl.checkTokenCookies(servRequest);

        FreePersonsListDataFilter filter = new FreePersonsListDataFilter(speciality, doctorId, beginDate, endDate);
        ListDataRequest request = new ListDataRequest(sortingField, sortingMethod, limit, page, filter);

        JSONWithPadding returnValue = new JSONWithPadding(wsImpl.getFreePersons(request), callback);
        return returnValue;
    }

    //Создание направления на консультацию
    @POST
    @Path("appeals/{eventId}/diagnostics/consultations")
    @Consumes("application/json")
    @Produces("application/x-javascript")
    public Object insertConsultation(ConsultationRequestData request,
                                     @QueryParam("callback") String callback,
                                     @Context HttpServletRequest servRequest) {
        AuthData auth = wsImpl.checkTokenCookies(servRequest);

        //
        //ConsultationRequestData request = new ConsultationRequestData(eventId, actionTypeId, executorId, patientId, beginDate, endDate, urgent);
        JSONWithPadding returnValue = new JSONWithPadding(wsImpl.insertConsultation(request.rewriteDefault(request)), callback);
        return returnValue;
    }

    //Получение списка типа действий по коду/ид. группы
    @GET
    @Path("/actionTypes/laboratory")
    @Produces("application/x-javascript")
    public Object getActionTypeNamesForLaboratory(@QueryParam("limit") int limit,
                                                  @QueryParam("page") int page,
                                                  @QueryParam("sortingField") String sortingField,      //сортировки вкл.
                                                  @QueryParam("sortingMethod") String sortingMethod,
                                                  @QueryParam("filter[groupId]") int groupId,
                                                  @QueryParam("filter[code]") String code,
                                                  @QueryParam("callback") String callback,
                                                  @Context HttpServletRequest servRequest) {
        AuthData auth = wsImpl.checkTokenCookies(servRequest);

        ActionTypesListRequestDataFilter filter = new ActionTypesListRequestDataFilter(code, groupId, "laboratory");
        ListDataRequest request = new ListDataRequest(sortingField, sortingMethod, limit, page, filter);

        Object oip = wsImpl.getListOfActionTypeIdNames(request);
        JSONWithPadding returnValue = new JSONWithPadding(oip, callback);
        return returnValue;
    }

    //Получение списка типа действий по коду/ид. группы
    @GET
    @Path("/actionTypes/instrumental")
    @Produces("application/x-javascript")
    public Object getActionTypeNamesForInstrumentalDiagnostics(@QueryParam("limit") int limit,
                                                               @QueryParam("page") int page,
                                                               @QueryParam("sortingField") String sortingField,    //сортировки вкл.
                                                               @QueryParam("sortingMethod") String sortingMethod,
                                                               @QueryParam("filter[groupId]") int groupId,
                                                               @QueryParam("filter[code]") String code,
                                                               @QueryParam("callback") String callback,
                                                               @Context HttpServletRequest servRequest) {
        AuthData auth = wsImpl.checkTokenCookies(servRequest);

        ActionTypesListRequestDataFilter filter = new ActionTypesListRequestDataFilter(code, groupId, "instrumental");
        ListDataRequest request = new ListDataRequest(sortingField, sortingMethod, limit, page, filter);

        Object oip = wsImpl.getListOfActionTypeIdNames(request);
        JSONWithPadding returnValue = new JSONWithPadding(oip, callback);
        return returnValue;
    }

    //Получение списка типа действий по коду/ид. группы
    @GET
    @Path("/actionTypes")
    @Produces("application/x-javascript")
    public Object getAllActionTypeNames(@QueryParam("limit") int limit,
                                        @QueryParam("page") int page,
                                        @QueryParam("sortingField") String sortingField,          //сортировки вкл.
                                        @QueryParam("sortingMethod") String sortingMethod,
                                        @QueryParam("filter[groupId]") int groupId,
                                        @QueryParam("filter[code]") String code,
                                        @QueryParam("callback") String callback,
                                        @Context HttpServletRequest servRequest) {
        AuthData auth = wsImpl.checkTokenCookies(servRequest);

        ActionTypesListRequestDataFilter filter = new ActionTypesListRequestDataFilter(code, groupId, "all");
        ListDataRequest request = new ListDataRequest(sortingField, sortingMethod, limit, page, filter);

        Object oip = wsImpl.getListOfActionTypeIdNames(request);
        JSONWithPadding returnValue = new JSONWithPadding(oip, callback);
        return returnValue;
    }

    //Создание направления на лабораторные исследования
    @POST
    @Path("/diagnostics/{eventId}/laboratory")
    @Consumes("application/json")
    @Produces("application/x-javascript")
    public Object insertLaboratoryStudies(CommonData data,
                                          @PathParam("eventId") int eventId,
                                          @QueryParam("callback") String callback,
                                          @Context HttpServletRequest servRequest) {
        AuthData auth = wsImpl.checkTokenCookies(servRequest);

        JSONCommonData oip = wsImpl.insertLaboratoryStudies(eventId, data);
        JSONWithPadding returnValue = new JSONWithPadding(oip, callback);
        return returnValue;
    }

    //Создание направления на инструментальную диагностику
    @POST
    @Path("/diagnostics/{eventId}/instrumental")
    @Consumes("application/json")
    @Produces("application/x-javascript")
    public Object insertInstrumentalDiagnostic(CommonData data,
                                               @PathParam("eventId") int eventId,
                                               @QueryParam("callback") String callback,
                                               @Context HttpServletRequest servRequest) {
        AuthData auth = wsImpl.checkTokenCookies(servRequest);

        JSONCommonData oip = wsImpl.insertLaboratoryStudies(eventId, data);
        JSONWithPadding returnValue = new JSONWithPadding(oip, callback);
        return returnValue;
    }

    //Получение данных из динамических справочников плоских структур
    @GET
    @Path("/flatDirectory")
    @Produces("application/x-javascript")
    public Object getFlatDirectories(@Context UriInfo info,
                                     @Context HttpServletRequest servRequest,
                                     @QueryParam("includeMeta") String includeMeta,
                                     @QueryParam("includeRecordList") String includeRecordList,
                                     @QueryParam("includeFDRecord") String includeFDRecord,
                                     @QueryParam("filterValue") String filterValue,
                                     @QueryParam("limit") int limit,
                                     @QueryParam("page") int page,
                                     @QueryParam("callback") String callback
    ) throws CoreException {

        AuthData auth = wsImpl.checkTokenCookies(servRequest);
        //hand-made url query params parsing
        MultivaluedMap<String, String> queryParams = info.getQueryParameters();
        String fullQueryPath = info.getRequestUri().getQuery();

        java.util.List<Integer> flatDictionaryIds = AuxiliaryFunctions.convertStringListTo(queryParams.get("flatDirectoryId"));
        java.util.List<Integer> filterRecordIds = AuxiliaryFunctions.convertStringListTo(queryParams.get("filterRecordId"));
        java.util.Map<Integer, java.util.List<String>> filterFields = AuxiliaryFunctions.foldFilterValueTo(queryParams, "filter[", "]");
        java.util.LinkedHashMap<Integer, Integer> sortingFieldIds = AuxiliaryFunctions.foldFilterValueToLinkedMapFromQuery(fullQueryPath, "sortingField[", "]=");

        boolean fields = ((filterFields != null) && filterFields.size() > 0);
        boolean values = ((filterValue != null) && (filterValue.isEmpty() != true));
        boolean recordIds = ((filterRecordIds != null) && filterRecordIds.size() > 0);

        if ((fields && values) || (fields && recordIds) || (recordIds && values)) {
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

    //Запрос на MKB справочники
    @GET
    @Path("/mkbs")
    @Produces("application/x-javascript")
    public Object getAllActionTypeNames(@QueryParam("limit") int limit,
                                        @QueryParam("page") int page,
                                        @QueryParam("sortingField") String sortingField,      //сортировки вкл.
                                        @QueryParam("sortingMethod") String sortingMethod,
                                        @QueryParam("filter[mkbId]") int mkbId,
                                        @QueryParam("filter[classId]") String classId,
                                        @QueryParam("filter[groupId]") String blockId,
                                        @QueryParam("filter[code]") String code,
                                        @QueryParam("filter[diagnosis]") String diagnosis,
                                        @QueryParam("filter[view]") String view,
                                        @QueryParam("filter[display]") String display,
                                        @QueryParam("filter[sex]") int sex,
                                        @QueryParam("callback") String callback,
                                        @Context HttpServletRequest servRequest) {

        AuthData auth = wsImpl.checkTokenCookies(servRequest);

        Boolean flgDisplay = (display != null && display.indexOf("true") >= 0) ? true : false;
        MKBListRequestDataFilter filter = new MKBListRequestDataFilter(mkbId, classId, blockId, code, diagnosis, view, flgDisplay, sex);
        ListDataRequest request = new ListDataRequest(sortingField, sortingMethod, limit, page, filter);


        Object oip = wsImpl.getAllMkbs(request, auth);
        JSONWithPadding returnValue = new JSONWithPadding(oip, callback);
        return returnValue;
    }

    //Запрос на Тезаурус
    @GET
    @Path("/thesaurus")
    @Produces("application/x-javascript")
    public Object getThesaurus(@QueryParam("limit") int limit,
                               @QueryParam("page") int page,
                               @QueryParam("sortingField") String sortingField,     //сортировки вкл.
                               @QueryParam("sortingMethod") String sortingMethod,
                               @QueryParam("filter[id]") int thesaurusId,
                               @QueryParam("filter[groupId]") String groupId,
                               @QueryParam("filter[code]") String code,
                               @QueryParam("callback") String callback,
                               @Context HttpServletRequest servRequest) {
        AuthData auth = wsImpl.checkTokenCookies(servRequest);

        ThesaurusListRequestDataFilter filter = new ThesaurusListRequestDataFilter(thesaurusId, groupId, code);
        ListDataRequest request = new ListDataRequest(sortingField, sortingMethod, limit, page, filter);

        Object oip = wsImpl.getThesaurusList(request, auth);
        JSONWithPadding returnValue = new JSONWithPadding(oip, callback);
        return returnValue;
    }

    //Возвращает указанный справочник
    @GET
    @Path("/dictionary")
    @Produces("application/x-javascript")
    public Object getRecordsFromDictionary(@QueryParam("dictName") String dictName,
                                           @QueryParam("filter[headId]") int headId,
                                           @QueryParam("filter[groupId]") int groupId,
                                           @QueryParam("filter[name]") String name,
                                           @QueryParam("filter[level]") String level,      //KLADR
                                           @QueryParam("filter[parent]") String parent,    //KLADR
                                           @QueryParam("filter[typeIs]") String type,        //valueDomain
                                           @QueryParam("filter[capId]") int capId,           //valueDomain
                                           @QueryParam("limit") int limit,
                                           @QueryParam("page") int page,
                                           @QueryParam("sortingField") String sortingField,               //сортировки вкл.
                                           @QueryParam("sortingMethod") String sortingMethod,
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

    //Создание нового назначения
    @POST
    @Path("/appeals/{eventId}/assignment")
    @Consumes("application/json")
    @Produces("application/x-javascript")
    public Object insertAssignment(AssignmentData data,
                                   @PathParam("eventId") int eventId,
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

    //Редактирование нового назначения
    @PUT
    @Path("/appeals/{eventId}/assignment")
    @Consumes("application/json")
    @Produces("application/x-javascript")
    public Object modifyAssignment(AssignmentData data,
                                   @PathParam("eventId") int eventId,
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
    @GET
    @Path("/assignment/{actionId}")
    @Produces("application/x-javascript")
    public Object getAssignmentById(@PathParam("actionId") int actionId,
                                    @QueryParam("callback") String callback,
                                    @Context HttpServletRequest servRequest) {
        AuthData auth = wsImpl.checkTokenCookies(servRequest);

        Object oip = wsImpl.getAssignmentById(actionId, auth);
        JSONWithPadding returnValue = new JSONWithPadding(oip, callback);
        return returnValue;

    }

    @GET
    @Path("/rls")
    @Produces("application/x-javascript")
    public Object getRlsList(@QueryParam("filter[name]") String name,
                             @QueryParam("filter[code]") int code,
                             @QueryParam("filter[dosage]") String dosage,
                             @QueryParam("filter[form]") String form,
                             @QueryParam("limit") int limit,
                             @QueryParam("page") int page,
                             @QueryParam("sortingField") String sortingField,               //сортировки вкл.
                             @QueryParam("sortingMethod") String sortingMethod,
                             @QueryParam("callback") String callback,
                             @Context HttpServletRequest servRequest) {
        //AuthData auth = wsImpl.checkTokenCookies(servRequest);
        RlsDataListFilter filter = new RlsDataListFilter(code, name, dosage, form);
        RlsDataListRequestData request = new RlsDataListRequestData(sortingField, sortingMethod, limit, page, filter);
        Object oip = wsImpl.getFilteredRlsList(request);
        JSONWithPadding returnValue = new JSONWithPadding(oip, callback);
        return returnValue;
    }
}