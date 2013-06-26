package ru.korus.tmis.ws.webmis.rest;

import com.sun.jersey.api.json.JSONWithPadding;
import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.data.AssessmentsListRequestData;
import ru.korus.tmis.core.data.AssessmentsListRequestDataFilter;
import ru.korus.tmis.core.data.JSONCommonData;
import ru.korus.tmis.core.logging.slf4j.interceptor.ServicesLoggingInterceptor;
import ru.korus.tmis.ws.impl.WebMisRESTImpl;
import javax.interceptor.Interceptors;
import javax.ws.rs.*;

/**
 * Список REST-сервисов для работы с осмотрами
 * Author: idmitriev Systema-Soft
 * Date: 3/20/13 6:34 PM
 * Since: 1.0.0.74
 */
@Interceptors(ServicesLoggingInterceptor.class)
public class ExaminationsRegistryRESTImpl {

    //protected static final String PATH = AppealsInfoRESTImpl.PATH + "{eventId}/examinations/";
    private WebMisRESTImpl wsImpl;
    private int eventId;
    private AuthData auth;
    private String callback;

    public ExaminationsRegistryRESTImpl(WebMisRESTImpl wsImpl, int eventId, String callback, AuthData auth) {
        this.eventId = eventId;
        this.auth = auth;
        this.wsImpl = wsImpl;
        this.callback = callback;
    }

    /**
     * Создание первичного осмотра
     * @param data Json c данными о первичном осмотре как JSONCommonData
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws ru.korus.tmis.core.exception.CoreException
     * @see ru.korus.tmis.core.exception.CoreException
     * @see ru.korus.tmis.core.data.JSONCommonData
     */
    @POST
    @Consumes("application/json")
    @Produces("application/x-javascript")
    public  Object insertPrimaryMedExamForPatient(JSONCommonData data) {
        return new JSONWithPadding(wsImpl.insertPrimaryMedExamForPatient(this.eventId, data, this.auth), this.callback);
    }

    /**
     * Редактирование первичного осмотра
     * @param data Json c данными о первичном осмотре как JSONCommonData
     * &#15; Внимание! В логике сервиса параметр не используется.</pre>
     * @param actionId Идентификатор редактируемого осмотра.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws ru.korus.tmis.core.exception.CoreException
     * @see ru.korus.tmis.core.exception.CoreException
     * @see JSONCommonData
     */
    @PUT
    @Path("/{actionId}")
    @Consumes("application/json")
    @Produces("application/x-javascript")
    public  Object modifyPrimaryMedExamForPatient(JSONCommonData data,
                                                  @PathParam("actionId") int actionId) {
        return new JSONWithPadding(wsImpl.modifyPrimaryMedExamForPatient(actionId, data, this.auth), this.callback);
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
     * @param assessmentTypeCode Фильтр значений по коду типа осмотра.
     * @param assessmentDate Фильтр значений по дате осмотра.
     * @param doctorName Фильтр значений по ФИО врача.
     * @param speciality Фильтр значений по специальности врача.
     * @param assessmentName Фильтр значений по наименованию осмотра.
     * @param departmentName Фильтр значений по наименованию отделения.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws ru.korus.tmis.core.exception.CoreException
     * @see ru.korus.tmis.core.exception.CoreException
     */
    @GET
    @Produces("application/x-javascript")
    public Object getListOfAssessmentsForPatientByEvent(@QueryParam("limit")int limit,
                                                        @QueryParam("page")int  page,
                                                        @QueryParam("sortingField")String sortingField,    //сортировки вкл.
                                                        @QueryParam("sortingMethod")String sortingMethod,
                                                        @QueryParam("filter[code]")String  assessmentTypeCode,
                                                        @QueryParam("filter[assessmentDate]") long assessmentDate,
                                                        @QueryParam("filter[doctorName]") String doctorName,
                                                        @QueryParam("filter[speciality]") String speciality,
                                                        @QueryParam("filter[assessmentName]") String assessmentName,
                                                        @QueryParam("filter[departmentName]") String departmentName) {
        AssessmentsListRequestDataFilter filter = new AssessmentsListRequestDataFilter(this.eventId, assessmentTypeCode, assessmentDate, doctorName, speciality, assessmentName, departmentName);
        AssessmentsListRequestData alrd= new AssessmentsListRequestData(sortingField, sortingMethod, limit, page, filter);
        return new JSONWithPadding(wsImpl.getListOfAssessmentsForPatientByEvent(alrd, this.auth), this.callback);
    }

    /**
     * Запрос на получение данных о первичном осмотре
     * &#15; Внимание! В логике сервиса параметр не используется.</pre>
     * @param actionId Идентификатор первичного осмотра.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws ru.korus.tmis.core.exception.CoreException
     * @see ru.korus.tmis.core.exception.CoreException
     */
    @GET
    @Path("/{actionId}")
    @Produces("application/x-javascript")
    public Object getPrimaryMedExamById(@PathParam("actionId")int actionId) {
        return new JSONWithPadding(wsImpl.getPrimaryAssessmentById(actionId, this.auth), this.callback);
    }

    /**
     * Запрос на структуру json для первичного осмотра.
     * &#15; Внимание! В логике сервиса параметр не используется.</pre>
     * @param actionTypeId Идентификатор типа действия
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws ru.korus.tmis.core.exception.CoreException
     * @see ru.korus.tmis.core.exception.CoreException
     */
    @GET
    @Path("/struct/{actionTypeId}")
    @Produces("application/x-javascript")
    public Object getStructOfPrimaryMedExam(@PathParam("actionTypeId") int actionTypeId) {
        return new JSONWithPadding(wsImpl.getStructOfPrimaryMedExam(actionTypeId, this.auth), this.callback);
    }

    /**
     * Запрос на структуру для первичного осмотра с копированием данных из предыдущего первичного осмотра
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws ru.korus.tmis.core.exception.CoreException
     * @see ru.korus.tmis.core.exception.CoreException
     */
    @GET
    @Path("/structWithCopy/{actionTypeId}")
    @Produces("application/x-javascript")
    public Object getStructOfPrimaryMedExamWithCopy(@PathParam("actionTypeId") int actionTypeId) {
        return new JSONWithPadding(wsImpl.getStructOfPrimaryMedExamWithCopy(actionTypeId, this.auth, this.eventId), this.callback);
    }
}
