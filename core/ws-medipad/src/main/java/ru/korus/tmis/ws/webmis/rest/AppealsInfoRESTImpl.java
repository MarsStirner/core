package ru.korus.tmis.ws.webmis.rest;

import com.sun.jersey.api.json.JSONWithPadding;
import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.data.AppealData;
import ru.korus.tmis.core.data.IdValueContainer;
import ru.korus.tmis.core.data.ReceivedRequestData;
import ru.korus.tmis.core.data.ReceivedRequestDataFilter;
import ru.korus.tmis.core.exception.CoreException;


import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Список REST-сервисов для получения информации о обращениях на госпитализацию
 * Author: idmitriev Systema-Soft
 * Date: 3/21/13 5:05 PM
 * Since: 1.0.0.74
 */
@Stateless

public class AppealsInfoRESTImpl {

    @EJB
    private WebMisREST wsImpl;

    @EJB
    private ExaminationsRegistryRESTImpl examinationsRegistryREST;

    @EJB
    private HospitalBedRegistryRESTImpl hospitalBedRegistryRESTImpl;

    @EJB
    private AssignmentsRegistryRESTImpl assignmentsRegistryRESTImpl;

    @EJB
    private DiagnosticsRegistryExRESTImpl diagnosticsRegistryExRESTImpl;

    @Path("/{eventId}/hospitalbed/")
    public HospitalBedRegistryRESTImpl getHospitalBedRegistryRESTImpl(@Context HttpServletRequest servRequest,
                                                                      @PathParam("eventId") int eventId,
                                                                      @QueryParam("callback") String callback) {
        return hospitalBedRegistryRESTImpl;
    }

    @Path("/{eventId}/assignments/")
    public AssignmentsRegistryRESTImpl getAssignmentsRegistryRESTImpl(@Context HttpServletRequest servRequest,
                                                                      @PathParam("eventId") int eventId,
                                                                      @QueryParam("callback") String callback) {
        return assignmentsRegistryRESTImpl;
    }

    @Path("/{eventId}/documents/")
    public ExaminationsRegistryRESTImpl getExaminationsRegistryRESTImpl() {
            return examinationsRegistryREST;
    }

    @Path("/{eventId}/diagnostics/")
    public DiagnosticsRegistryExRESTImpl getDiagnosticsRegistryRESTImpl(@Context HttpServletRequest servRequest,
                                                                        @PathParam("eventId") int eventId,
                                                                        @QueryParam("callback") String callback) {
        return diagnosticsRegistryExRESTImpl;
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
    @Produces({"application/javascript", "application/x-javascript"})
    public Object getAllAppealsForReceivedPatientByPeriod(@Context HttpServletRequest servRequest,
                                                          @QueryParam("limit")int limit,
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
                                                          @QueryParam("filter[diagnosis]") String mkbCode,
                                                          @QueryParam("callback") String callback) throws CoreException {

        ReceivedRequestDataFilter filter = new ReceivedRequestDataFilter(eventId, fullName, birthDate, externalId, beginDate, endDate, mkbCode, role);
        ReceivedRequestData requestData = new ReceivedRequestData(sortingField, sortingMethod, limit, page, filter);
        return new JSONWithPadding(wsImpl.getAllAppealsForReceivedPatientByPeriod(requestData, mkAuth(servRequest)), callback);
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
    @Produces({"application/javascript", "application/x-javascript"})
    public Object updatePatientAppeal(@Context HttpServletRequest servRequest,
                                      @PathParam("eventId")int eventId,
                                      @QueryParam("callback") String callback,
                                      AppealData data) throws CoreException {
        return new JSONWithPadding(wsImpl.updateAppeal(data, eventId, mkAuth(servRequest)), callback);
    }

    /**
     * Закрытие последнего движения пациента. Применяется для снятия пациента с койки
     * при выписке. Требует наличия у истории болезни документа "выписка" (с flatcode "leaved").
     * @return Json-представление последнего движения, которое было закрыто.
     * @throws ru.korus.tmis.core.exception.CoreException при отсутствии выписки у пациента
     * @see ru.korus.tmis.core.exception.CoreException
     */
    @POST
    @Path("{eventId}/closemove")
    @Consumes("application/json")
    @Produces({"application/javascript", "application/x-javascript"})
    public Object closeLastMovingAtAppeal(@Context HttpServletRequest servRequest,
                                          @PathParam(("eventId"))int eventId,
                                          @QueryParam("callback") String callback,
                                          @QueryParam("date")long timestamp) throws CoreException {
        Date date;
        if(timestamp < 1)
            date = new Date();
        else
            date = new Date(timestamp);

        return new JSONWithPadding(wsImpl.closeLastMovingOfAppeal(eventId, mkAuth(servRequest), date), callback);
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
    @Produces({"application/javascript", "application/x-javascript"})
    public Object getAppealById(@Context HttpServletRequest servRequest,
                                @PathParam("eventId")int eventId,
                                @QueryParam("callback") String callback) throws CoreException {
        return new JSONWithPadding(wsImpl.getAppealById(eventId, mkAuth(servRequest)), callback);
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
    @Produces({"application/javascript", "application/x-javascript"})
    public Object getAppealPrintFormById(@Context HttpServletRequest servRequest,
                                         @PathParam("eventId")int eventId,
                                         @QueryParam("callback") String callback) throws CoreException {
        return new JSONWithPadding(wsImpl.getAppealPrintFormById(eventId, mkAuth(servRequest)), callback);
    }

    @GET
    @Path("{eventId}/diagnoses")
    @Produces({"application/javascript", "application/x-javascript"})
    public Object getDiagnosesByAppeal(@Context HttpServletRequest servRequest,
                                       @PathParam("eventId")int eventId,
                                       @QueryParam("callback") String callback) throws CoreException {
        return new JSONWithPadding(wsImpl.getDiagnosesByAppeal(eventId, mkAuth(servRequest)), callback);
    }

    @GET
    @Path("{eventId}/monitoring")
    @Produces({"application/javascript", "application/x-javascript"})
    public Object getMonitoringInfoByAppeal(@Context HttpServletRequest servRequest,
                                            @PathParam("eventId")int eventId,
                                            @QueryParam("callback") String callback) throws CoreException {
        return new JSONWithPadding(wsImpl.getMonitoringInfoByAppeal(eventId, 0, mkAuth(servRequest)), callback);
    }

    @GET
    @Path("{eventId}/infection-monitoring")
    @Produces({"application/javascript", "application/x-javascript"})
    public Object getInfectionMonitoringInfoByAppeal(@Context HttpServletRequest servRequest,
                                            @PathParam("eventId")int eventId,
                                            @QueryParam("callback") String callback) throws CoreException {
        return new JSONWithPadding(new GenericEntity<List<List<Object>>>(wsImpl.getInfectionMonitoring(eventId, mkAuth(servRequest))) {}, callback);
    }

    @GET
    @Path("{eventId}/infection-drug-monitoring")
    @Produces({"application/javascript", "application/x-javascript"})
    public Object getInfectionDrugMonitoringInfoByAppeal(@Context HttpServletRequest servRequest,
                                                        @PathParam("eventId")int eventId,
                                                        @QueryParam("callback") String callback) throws CoreException {
        return new JSONWithPadding(wsImpl.getInfectionDrugMonitoringList(eventId), callback);
    }

    @GET
    @Path("{eventId}/surgical")
    @Produces({"application/javascript", "application/x-javascript"})
    public Object getSurgicalOperationsByAppeal(@Context HttpServletRequest servRequest,
                                                @PathParam("eventId")int eventId,
                                                @QueryParam("callback") String callback) throws CoreException {
        return new JSONWithPadding(wsImpl.getSurgicalOperationsByAppeal(eventId, mkAuth(servRequest)), callback);
    }

    @GET
    @Path("{eventId}/analyzes")
    @Produces({"application/javascript", "application/x-javascript"})
    public Object getExpressAnalyzesInfoByAppeal(@Context HttpServletRequest servRequest,
                                                 @PathParam("eventId")int eventId,
                                                 @QueryParam("callback") String callback) throws CoreException {
        return new JSONWithPadding(wsImpl.getMonitoringInfoByAppeal(eventId, 1, mkAuth(servRequest)), callback);
    }

    @PUT
    @Path("{eventId}/execPerson")
    @Consumes("application/json")
    @Produces({"application/javascript", "application/x-javascript"})
    public Object setExecPersonForAppeal(@Context HttpServletRequest servRequest,
                                         IdValueContainer data,
                                         @PathParam("eventId")int eventId,
                                         @QueryParam("callback") String callback) throws CoreException {
        return new JSONWithPadding(wsImpl.setExecPersonForAppeal(eventId, Integer.valueOf(data.getId()), mkAuth(servRequest)), callback);
    }

    public WebMisREST getWsImpl() {
        return wsImpl;
    }

    public void setWsImpl(WebMisREST wsImpl) {
        this.wsImpl = wsImpl;
    }

    private AuthData mkAuth(HttpServletRequest servRequest) {
        return wsImpl.checkTokenCookies(Arrays.asList(servRequest.getCookies()));
    }

}
