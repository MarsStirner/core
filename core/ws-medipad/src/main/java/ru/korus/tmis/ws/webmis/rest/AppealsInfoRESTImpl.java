package ru.korus.tmis.ws.webmis.rest;

import com.sun.jersey.api.json.JSONWithPadding;
import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.data.AppealData;
import ru.korus.tmis.core.data.IdValueContainer;
import ru.korus.tmis.core.data.ReceivedRequestData;
import ru.korus.tmis.core.data.ReceivedRequestDataFilter;
import ru.korus.tmis.core.logging.slf4j.interceptor.ServicesLoggingInterceptor;
import ru.korus.tmis.ws.impl.WebMisRESTImpl;
import javax.interceptor.Interceptors;
import javax.ws.rs.*;

/**
 * Список REST-сервисов для получения информации о обращениях на госпитализацию
 * Author: idmitriev Systema-Soft
 * Date: 3/21/13 5:05 PM
 * Since: 1.0.0.74
 */
@Interceptors(ServicesLoggingInterceptor.class)
public class AppealsInfoRESTImpl {

    //protected static final String PATH = BaseRegistryRESTImpl.PATH + "appeals/";
    private WebMisRESTImpl wsImpl;
    private AuthData auth;
    private String callback;

    public AppealsInfoRESTImpl(WebMisRESTImpl wsImpl, String callback, AuthData auth) {
        this.auth = auth;
        this.wsImpl = wsImpl;
        this.callback = callback;
    }

    @Path("/{eventId}/hospitalbed/")
    public HospitalBedRegistryRESTImpl getHospitalBedRegistryRESTImpl(@PathParam("eventId") int eventId) {
        return new HospitalBedRegistryRESTImpl(wsImpl, eventId, callback, this.auth) ;
    }

    @Path("/{eventId}/assignments/")
    public AssignmentsRegistryRESTImpl getAssignmentsRegistryRESTImpl(@PathParam("eventId") int eventId) {
        return new AssignmentsRegistryRESTImpl(wsImpl, eventId, callback, this.auth) ;
    }

    @Path("/{eventId}/documents/")
    public ExaminationsRegistryRESTImpl getExaminationsRegistryRESTImpl(@PathParam("eventId") int eventId) {
            return new ExaminationsRegistryRESTImpl(wsImpl, eventId, callback, this.auth) ;
    }

    @Path("/{eventId}/diagnostics/")
    public DiagnosticsRegistryExRESTImpl getDiagnosticsRegistryRESTImpl(@PathParam("eventId") int eventId) {
        return new DiagnosticsRegistryExRESTImpl(wsImpl, eventId, callback, this.auth) ;
    }

    @Path("/{eventId}/quotes/")
    public QuotesRegistryRESTImpl getQuotesRegistryRESTImpl(@PathParam("eventId") int eventId) {
        return new QuotesRegistryRESTImpl(wsImpl, eventId, callback, this.auth) ;
    }

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
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws ru.korus.tmis.core.exception.CoreException
     * @see ru.korus.tmis.core.exception.CoreException
     */
    @GET
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
                                                          @QueryParam("filter[diagnosis]") String mkbCode) {

        ReceivedRequestDataFilter filter = new ReceivedRequestDataFilter(eventId, fullName, birthDate, externalId, beginDate, endDate, mkbCode, role);
        ReceivedRequestData requestData = new ReceivedRequestData(sortingField, sortingMethod, limit, page, filter);
        return new JSONWithPadding(wsImpl.getAllAppealsForReceivedPatientByPeriod(requestData, this.auth), this.callback);
    }

    /**
     * Редактирование обращения на госпитализацию
     * @param data структура AppealData c данными о госпитализации.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws ru.korus.tmis.core.exception.CoreException
     * @see ru.korus.tmis.core.exception.CoreException
     */
    @PUT
    @Path("{eventId}")
    @Consumes("application/json")
    @Produces("application/x-javascript")
    public Object updatePatientAppeal(@PathParam("eventId")int eventId,
                                      AppealData data) {
        return new JSONWithPadding(wsImpl.updateAppeal(data, eventId, this.auth), this.callback);
    }

    /**
     * Данные об обращения на госпитализацию по идентификатору.
     * @param eventId Идентификатор обращения на госпитализацию.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws ru.korus.tmis.core.exception.CoreException
     * @see ru.korus.tmis.core.exception.CoreException
     */
    @GET
    @Path("{eventId}")
    @Produces("application/x-javascript")
    public Object getAppealById(@PathParam("eventId")int eventId) {
        return new JSONWithPadding(wsImpl.getAppealById(eventId, this.auth), this.callback);
    }

    /**
     * Печатная форма обращения на госпитализацию по идентификатору.
     * @param eventId Идентификатор обращения на госпитализацию.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws ru.korus.tmis.core.exception.CoreException
     * @see ru.korus.tmis.core.exception.CoreException
     */
    @GET
    @Path("{eventId}/print")
    @Produces("application/x-javascript")
    public Object getAppealPrintFormById(@PathParam("eventId")int eventId) {
        return new JSONWithPadding(wsImpl.getAppealPrintFormById(eventId, this.auth), this.callback);
    }

    @GET
    @Path("{eventId}/diagnoses")
    @Produces("application/x-javascript")
    public Object getDiagnosesByAppeal(@PathParam("eventId")int eventId) {
        return new JSONWithPadding(wsImpl.getDiagnosesByAppeal(eventId, this.auth), this.callback);
    }

    @GET
    @Path("{eventId}/monitoring")
    @Produces("application/x-javascript")
    public Object getMonitoringInfoByAppeal(@PathParam("eventId")int eventId) {
        return new JSONWithPadding(wsImpl.getMonitoringInfoByAppeal(eventId, 0, this.auth), this.callback);
    }

    @GET
    @Path("{eventId}/surgical")
    @Produces("application/x-javascript")
    public Object getSurgicalOperationsByAppeal(@PathParam("eventId")int eventId) {
        return new JSONWithPadding(wsImpl.getSurgicalOperationsByAppeal(eventId, this.auth), this.callback);
    }

    @GET
    @Path("{eventId}/analyzes")
    @Produces("application/x-javascript")
    public Object getExpressAnalyzesInfoByAppeal(@PathParam("eventId")int eventId) {
        return new JSONWithPadding(wsImpl.getMonitoringInfoByAppeal(eventId, 1, this.auth), this.callback);
    }

    @PUT
    @Path("{eventId}/execPerson")
    @Consumes("application/json")
    @Produces("application/x-javascript")
    public Object setExecPersonForAppeal(IdValueContainer data,
                                         @PathParam("eventId")int eventId) {
        return new JSONWithPadding(wsImpl.setExecPersonForAppeal(eventId, Integer.valueOf(data.getId()).intValue(), this.auth), this.callback);
    }
}
