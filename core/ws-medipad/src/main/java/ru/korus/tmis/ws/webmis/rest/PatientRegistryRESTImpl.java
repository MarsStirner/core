package ru.korus.tmis.ws.webmis.rest;

import java.util.*;
import javax.ejb.EJB;
import javax.ejb.Stateless;
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

import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.data.*;
import ru.korus.tmis.core.exception.CoreException;

import com.sun.jersey.api.json.JSONWithPadding;

/**
 * Сервисы для работы с ядром TMIS посредством Web-клиента
 */
@Stateless

public class PatientRegistryRESTImpl {

    @EJB
    private WebMisREST wsImpl;

    @EJB
    ExaminationsRegistryRESTImpl examinationsRegistryREST;

    @EJB AppealRegistryRESTImpl appealRegistryREST;

    @Path("/{patientId}/appeals/")
    public AppealRegistryRESTImpl getAppealRegistryRESTImpl(@Context HttpServletRequest servRequest,
                                                            @QueryParam("callback") String callback,
                                                            @PathParam("patientId") int patientId){
        return appealRegistryREST;
    }


    /**
     * Запись данных о новом пациенте.
     * @param patientData Структура PatientCardData с данными о пациенте.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws CoreException
     * @see CoreException
     * @see PatientCardData
     */
    @POST
    @Consumes("application/json")
    @Produces({"application/javascript", "application/x-javascript"})
    public Object insertPatient(@Context HttpServletRequest servRequest,
                                @QueryParam("callback") String callback,
                                PatientCardData patientData) throws CoreException {
        return new JSONWithPadding(wsImpl.insertPatient(patientData,  mkAuth(servRequest)), callback);
    }


    /**
     * Редактирование данных о пациенте.
     * @param patientData Структура PatientCardData с данными о пациенте.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws CoreException
     * @see CoreException
     * @see PatientCardData
     */
    @PUT
    @Path("/{patientId}")
    @Consumes("application/json")
    @Produces({"application/javascript", "application/x-javascript"})
    public Object updatePatient(@Context HttpServletRequest servRequest,
                                @QueryParam("callback") String callback,
                                PatientCardData patientData,
                                @PathParam("patientId")int patientId) throws CoreException {
        return new JSONWithPadding(wsImpl.updatePatient(patientId, patientData, mkAuth(servRequest)), callback);
    }


    /**
     * Получение списка пациентов.
     */
    @GET
    @Produces({"application/javascript", "application/x-javascript", "application/xml"})
    public Object getAllPatientsP(@Context HttpServletRequest servRequest,
                                  @QueryParam("callback") String callback,
                                  @QueryParam("limit")int limit,
                                  @QueryParam("page")int  page,
                                  @QueryParam("sortingField")String sortingField,
                                  @QueryParam("sortingMethod")String sortingMethod,
                                  @QueryParam("filter[patientCode]")int patientCode,
                                  @QueryParam("filter[fullName]")String fullName,
                                  @QueryParam("filter[birthDate]")Long birthDate,
                                  @QueryParam("filter[document]")String document,
                                  @QueryParam("filter[withRelations]")String withRelations) throws CoreException {
        Date bDate = (birthDate == null) ? null : new Date(birthDate);
        PatientRequestData requestData = new PatientRequestData(patientCode, fullName, bDate, document, withRelations, sortingField, sortingMethod, limit, page);
        return new JSONWithPadding(wsImpl.getAllPatients(requestData, mkAuth(servRequest)), callback);
    }


    /**
     * Получение данных о пациенте по идентификатору.
     * @param patientId Идентификатор пациента, данные о котором запрашиваются.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws CoreException
     * @see CoreException
     * @see PatientCardData
     */
    @GET
    @Path("/{patientId}")
    @Produces({"application/javascript", "application/x-javascript"})
    public Object getPatientById(@Context HttpServletRequest servRequest,
                                 @QueryParam("callback") String callback,
                                 @PathParam("patientId")int patientId) throws CoreException {
        return new JSONWithPadding(wsImpl.getPatientById(patientId, mkAuth(servRequest)), callback);
    }



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
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws CoreException
     * @see CoreException
     */
    @GET
    @Path("/{patientId}/talons")
    @Produces({"application/javascript", "application/x-javascript"})
    public Object getAllTalonsForPatient(@Context HttpServletRequest servRequest,
                                         @QueryParam("callback") String callback,
                                         @PathParam("patientId") int patientId,
                                         @QueryParam("limit")int limit,
                                         @QueryParam("page")int  page,
                                         @QueryParam("sortingField")String sortingField,   //сортировки вкл.
                                         @QueryParam("sortingMethod")String sortingMethod) throws CoreException {
        TalonSPODataListFilter filter = new TalonSPODataListFilter(patientId, "33");
        TalonSPOListRequestData request = new TalonSPOListRequestData(sortingField, sortingMethod, limit, page, filter);
        return new JSONWithPadding(wsImpl.getAllTalonsForPatient(request), callback);
    }


    /**
     * Добавить запись о группе крови
     * @param data данные о группе крови
     * @param patientId Идентификатор пациента
     * @return Историю изменений группы крови для пациента в json-формате
     */
    @POST
    @Path("/{patientId}/bloodtypes")
    @Consumes("application/json")
    @Produces({"application/javascript", "application/x-javascript"})
    public Object addBloodType(@Context HttpServletRequest servRequest,
                               @QueryParam("callback") String callback,
                               BloodHistoryData data,
                               @PathParam("patientId") int patientId) throws CoreException {
        return new JSONWithPadding(wsImpl.insertBloodTypeForPatient(patientId, data, mkAuth(servRequest)), callback);
    }


    /**
     * Запрос на историю изменений групп крови
     * @param patientId Идентификатор пациента
     * @return Историю изменений группы крови для пациента в json-формате
     */
    @GET
    @Path("/{patientId}/bloodtypes")
    @Produces({"application/javascript", "application/x-javascript"})
    public Object getBloodTypesHistory(@Context HttpServletRequest servRequest,
                                       @QueryParam("callback") String callback,
                                       @PathParam("patientId") int patientId) throws CoreException {
        return new JSONWithPadding(wsImpl.getBloodTypesHistory(patientId, mkAuth(servRequest)), callback);
    }

    @Path("/{patientId}/documents")
    public ExaminationsRegistryRESTImpl getExaminationsRegistryRESTImpl(@Context HttpServletRequest servRequest,
                                                                        @QueryParam("callback") String callback,
                                                                        @PathParam("patientId") int patientId) {
        return examinationsRegistryREST;
    }

    private AuthData mkAuth(HttpServletRequest servRequest) {
        return wsImpl.checkTokenCookies(Arrays.asList(servRequest.getCookies()));
    }

}