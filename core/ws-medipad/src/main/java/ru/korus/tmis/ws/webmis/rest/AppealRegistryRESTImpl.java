package ru.korus.tmis.ws.webmis.rest;

import com.sun.jersey.api.json.JSONWithPadding;
import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.data.AppealData;
import ru.korus.tmis.core.data.AppealSimplifiedRequestData;
import ru.korus.tmis.core.data.AppealSimplifiedRequestDataFilter;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.core.logging.slf4j.interceptor.ServicesLoggingInterceptor;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Список REST-сервисов для работы с обращениями на госпитализацию
 * Author: idmitriev Systema-Soft
 * Date: 3/21/13 12:05 PM
 * Since: 1.0.0.74
 */
@Stateless
@Interceptors(ServicesLoggingInterceptor.class)
public class AppealRegistryRESTImpl {

    @EJB
    private WebMisREST wsImpl;


    /**
     * Создание обращения на госпитализацию.
     * @param data структура AppealData c данными о госпитализации.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws ru.korus.tmis.core.exception.CoreException
     * @see ru.korus.tmis.core.data.AppealData
     * @see ru.korus.tmis.core.exception.CoreException
     */
    @POST
    @Consumes("application/json")
    @Produces("application/x-javascript")
    public Object insertPatientAppeal(@Context HttpServletRequest servRequest,
                                      @QueryParam("callback") String callback,
                                      @PathParam("patientId") int patientId,
                                      AppealData data) throws CoreException {
        return new JSONWithPadding(wsImpl.insertAppealForPatient(data, patientId, mkAuth(servRequest)), callback);
    }

    /**
     * Запрос перечня обращений по пациенту (История госпитализаций).
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
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws ru.korus.tmis.core.exception.CoreException
     * @see ru.korus.tmis.core.exception.CoreException
     */
    @GET
    @Produces({"application/x-javascript", "application/xml"})
    public Object getAllAppealsForPatient(@Context HttpServletRequest servRequest,
                                          @QueryParam("callback") String callback,
                                          @PathParam("patientId") int patientId,
                                          @QueryParam("limit")int limit,
                                          @QueryParam("page")int  page,
                                          @QueryParam("sortingField")String sortingField,  //сортировки вкл.
                                          @QueryParam("sortingMethod")String sortingMethod,
                                          @QueryParam("filter[number]")String number,
                                          @QueryParam("filter[beginDate]")long beginDate,
                                          @QueryParam("filter[endDate]")long endDate,
                                          @QueryParam("filter[departmentId]") int departmentId,
                                          @QueryParam("filter[doctorId]") int doctorId,
                                          @QueryParam("filter[diagnosis]") String mkbCode) throws CoreException {
        Set<String> codes = new LinkedHashSet<String>();
        AppealSimplifiedRequestDataFilter filter = new AppealSimplifiedRequestDataFilter(patientId, beginDate, endDate, departmentId, doctorId, mkbCode, number, codes);
        AppealSimplifiedRequestData request= new AppealSimplifiedRequestData(sortingField, sortingMethod, limit, page, filter);
        return new JSONWithPadding(wsImpl.getAllAppealsByPatient(request, mkAuth(servRequest)), callback);
    }

    //Основные сведения истории болезни
    /*@GET
    @Path("/basicinfo")
    @Produces("application/x-javascript")
    public Object getBasicInfoOfDiseaseHistory(@QueryParam("externalId") String externalId){
          return wsImpl.getBasicInfoOfDiseaseHistory(patientId, externalId).toString();
    }*/

    private AuthData mkAuth(HttpServletRequest servRequest) {
        return wsImpl.checkTokenCookies(Arrays.asList(servRequest.getCookies()));
    }
}
