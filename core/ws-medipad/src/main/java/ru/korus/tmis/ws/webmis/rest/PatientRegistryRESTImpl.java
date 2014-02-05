package ru.korus.tmis.ws.webmis.rest;

import java.util.*;
import javax.interceptor.Interceptors;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.data.*;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.core.logging.slf4j.interceptor.ServicesLoggingInterceptor;
import com.sun.jersey.api.json.JSONWithPadding;
import ru.korus.tmis.ws.impl.WebMisRESTImpl;

/**
 * Сервисы для работы с ядром TMIS посредством Web-клиента
 */
@Interceptors(ServicesLoggingInterceptor.class)
public class PatientRegistryRESTImpl {

    //public static final String PATH = BaseRegistryRESTImpl.PATH + "patients/";
    private WebMisRESTImpl wsImpl;
    private AuthData auth;
    private String callback;

    public PatientRegistryRESTImpl(WebMisRESTImpl wsImpl, String callback, AuthData auth) {
        this.auth = auth;
        this.wsImpl = wsImpl;
        this.callback = callback;
    }

    @Path("/{patientId}/appeals/")
    public AppealRegistryRESTImpl getAppealRegistryRESTImpl(@PathParam("patientId") int patientId){
        return new AppealRegistryRESTImpl(wsImpl, patientId, callback, this.auth);
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
    @Produces("application/x-javascript")
    public Object insertPatient(PatientCardData patientData) {
        return new JSONWithPadding(wsImpl.insertPatient(patientData,  this.auth), this.callback);
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
    @Produces("application/x-javascript")
    public Object updatePatient(PatientCardData patientData,
                                @PathParam("patientId")int patientId) {
        return new JSONWithPadding(wsImpl.updatePatient(patientId, patientData, this.auth), this.callback);
    }

    /**
     * Получение списка пациентов.
     */
    @GET
    @Produces({"application/x-javascript", "application/xml"})
    public Object getAllPatientsP(@QueryParam("limit")int limit,
                                  @QueryParam("page")int  page,
                                  @QueryParam("sortingField")String sortingField,
                                  @QueryParam("sortingMethod")String sortingMethod,
                                  @QueryParam("filter[patientCode]")int patientCode,
                                  @QueryParam("filter[fullName]")String fullName,
                                  @QueryParam("filter[birthDate]")Long birthDate,
                                  @QueryParam("filter[document]")String document,
                                  @QueryParam("filter[withRelations]")String withRelations) {
        Date bDate = (birthDate == null) ? null : new Date(birthDate);
        PatientRequestData requestData = new PatientRequestData(patientCode, fullName, bDate, document, withRelations, sortingField, sortingMethod, limit, page);
        return new JSONWithPadding(wsImpl.getAllPatients(requestData, this.auth), this.callback);
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
    @Produces("application/x-javascript")
    public Object getPatientById(@PathParam("patientId")int patientId) {
        return new JSONWithPadding(wsImpl.getPatientById(patientId, this.auth), this.callback);
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
    @Produces("application/x-javascript")
    public Object getAllTalonsForPatient(   @PathParam("patientId") int patientId,
                                            @QueryParam("limit")int limit,
                                            @QueryParam("page")int  page,
                                            @QueryParam("sortingField")String sortingField,   //сортировки вкл.
                                            @QueryParam("sortingMethod")String sortingMethod) {
        TalonSPODataListFilter filter = new TalonSPODataListFilter(patientId, "33");
        TalonSPOListRequestData request = new TalonSPOListRequestData(sortingField, sortingMethod, limit, page, filter);
        return new JSONWithPadding(wsImpl.getAllTalonsForPatient(request), this.callback);
    }

    /*
    @DELETE
    @Path("/{patientId}/delete")
    @Produces("application/x-javascript")
    public Object deletePatientInfo(@PathParam("patientId") int id,
                                    @QueryParam("callback") String callback,
                                    @Context HttpServletRequest servRequest)   {
        Object oip = wsImpl.deletePatientInfo(id);
        JSONWithPadding returnValue = new JSONWithPadding(oip, callback);
        return returnValue;
    }
    */

    /*@POST
    @Path("/{patientId}/talon")
    @Consumes("application/json")
    @Produces("application/x-javascript")
    public Object insertTalonSPOForPatient(Object data,
                                           @QueryParam("callback") String callback) {

       JSONWithPadding returnValue = new JSONWithPadding(wsImpl.insertTalonSPOForPatient(data), callback);
       return returnValue;
    } */

    /**
     * Добавить запись о группе крови
     * @param data данные о группе крови
     * @param patientId Идентификатор пациента
     * @return Историю изменений группы крови для пациента в json-формате
     */
    @POST
    @Path("/{patientId}/bloodtypes")
    @Consumes("application/json")
    @Produces("application/x-javascript")
    public Object addBloodType( BloodHistoryData data,
                                @PathParam("patientId") int patientId)   {
        return new JSONWithPadding(wsImpl.insertBloodTypeForPatient(patientId, data, this.auth), callback);
    }

    /**
     * Запрос на историю изменений групп крови
     * @param patientId Идентификатор пациента
     * @return Историю изменений группы крови для пациента в json-формате
     */
    @GET
    @Path("/{patientId}/bloodtypes")
    @Produces("application/x-javascript")
    public Object getBloodTypesHistory(@PathParam("patientId") int patientId)   {
        return new JSONWithPadding(wsImpl.getBloodTypesHistory(patientId, this.auth), callback);
    }
}