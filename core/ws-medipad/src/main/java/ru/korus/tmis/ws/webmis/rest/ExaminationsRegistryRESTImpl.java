package ru.korus.tmis.ws.webmis.rest;

import com.sun.jersey.api.json.JSONWithPadding;
import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.data.*;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.core.logging.slf4j.interceptor.ServicesLoggingInterceptor;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.UriInfo;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Список REST-сервисов для работы с осмотрами
 * Author: idmitriev Systema-Soft
 * Date: 3/20/13 6:34 PM
 * Since: 1.0.0.74
 */
@Stateless
@Interceptors(ServicesLoggingInterceptor.class)
public class ExaminationsRegistryRESTImpl {

    @EJB
    private WebMisREST wsImpl;


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
    @Produces({"application/javascript", "application/x-javascript"})
    public  Object insertPrimaryMedExamForPatient(@Context HttpServletRequest servRequest,
                                                  @PathParam("eventId") int eventId,
                                                  @QueryParam("callback") String callback,
                                                  @PathParam("patientId") int patientId,
                                                  @Context UriInfo uri, JSONCommonData data) throws CoreException {
        if(eventId < 1)
            throw new CoreException("This service cannot be used without Event id");
        return new JSONWithPadding(wsImpl.insertPrimaryMedExamForPatient(eventId, data, mkAuth(servRequest), uri == null ? null : uri.getBaseUri()), callback);
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
    @Produces({"application/javascript", "application/x-javascript"})
    public  Object modifyPrimaryMedExamForPatient(@Context HttpServletRequest servRequest,
                                                  @PathParam("eventId") int eventId,
                                                  @QueryParam("callback") String callback,
                                                  @PathParam("patientId") int patientId,
                                                  @Context UriInfo uri, JSONCommonData data,
                                                  @PathParam("actionId") int actionId) throws CoreException {

        return new JSONWithPadding(wsImpl.modifyPrimaryMedExamForPatient(actionId, data, mkAuth(servRequest), uri == null ? null : uri.getBaseUri()), callback);
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
     * @param actionTypeId Фильтр значений по типу осмотра.
     * @param begDate Фильтр значений по дате осмотра.
     * @param endDate Фильтр значений по дате осмотра.
     * @param doctorName Фильтр значений по ФИО врача.
     * @param speciality Фильтр значений по специальности врача.
     * @param assessmentName Фильтр значений по наименованию осмотра.
     * @param departmentName Фильтр значений по наименованию отделения.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws ru.korus.tmis.core.exception.CoreException
     * @see ru.korus.tmis.core.exception.CoreException
     */
    @GET
    @Produces({"application/javascript", "application/x-javascript", "application/xml"})
    public Object getListOfAssessmentsForPatientByEvent(@Context HttpServletRequest servRequest,
                                                        @PathParam("eventId") int eventId,
                                                        @QueryParam("callback") String callback,
                                                        @PathParam("patientId") int patientId,
                                                        @Context UriInfo info,
                                                        @QueryParam("limit")int limit,
                                                        @QueryParam("page")int  page,
                                                        @QueryParam("sortingField")String sortingField,    //сортировки вкл.
                                                        @QueryParam("sortingMethod")String sortingMethod,
                                                        @QueryParam("filter[actionTypeId]")int actionTypeId,
                                                        @QueryParam("filter[begDate]")long begDate,
                                                        @QueryParam("filter[endDate]")long endDate,
                                                        @QueryParam("filter[actionTypeCode]")Set<String> assessmentTypeCode,
                                                        @QueryParam("filter[doctorId]") int doctorId,
                                                        @QueryParam("filter[doctorName]") String doctorName,
                                                        @QueryParam("filter[speciality]") String speciality,
                                                        @QueryParam("filter[assessmentName]") String assessmentName,
                                                        @QueryParam("filter[departmentName]") String departmentName) throws CoreException {

        List<String> mnems = info.getQueryParameters().get("filter[mnem]");
        List<String> flatCodes = info.getQueryParameters().get("filter[flatCode]");
        if(flatCodes == null) flatCodes = new LinkedList<String>();
        List<String> mnemonics = new LinkedList<String>();
        if(mnems!=null && mnems.size()>0) {
            for(String mnem: mnems) {
                DirectoryInfoRESTImpl.ActionTypesSubType atst = (mnem!=null && !mnem.isEmpty()) ? DirectoryInfoRESTImpl.ActionTypesSubType.getType(mnem.toLowerCase())
                                                                                                : DirectoryInfoRESTImpl.ActionTypesSubType.getType("");
                mnemonics.add(atst.getMnemonic());
            }
        }
        AssessmentsListRequestDataFilter filter = new AssessmentsListRequestDataFilter(eventId, patientId, actionTypeId, assessmentTypeCode, begDate, endDate, doctorId, doctorName, speciality, assessmentName, departmentName, mnemonics, flatCodes);
        AssessmentsListRequestData alrd= new AssessmentsListRequestData(sortingField, sortingMethod, limit, page, filter);
        return new JSONWithPadding(this.wsImpl.getListOfAssessmentsForPatientByEvent(alrd, mkAuth(servRequest)), callback);
    }

    /**
     * Запрос на получение данных об осмотре
     * @param actionId Идентификатор первичного осмотра.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws ru.korus.tmis.core.exception.CoreException
     * @see ru.korus.tmis.core.exception.CoreException
     */
    @GET
    @Path("/{actionId}")
    @Produces({"application/javascript", "application/x-javascript", "application/xml"})
    public Object getPrimaryMedExamById(@Context HttpServletRequest servRequest,
                                        @PathParam("eventId") int eventId,
                                        @QueryParam("callback") String callback,
                                        @PathParam("patientId") int patientId,
                                        @PathParam("actionId")int actionId) throws CoreException {
        return new JSONWithPadding(wsImpl.getPrimaryAssessmentById(actionId,mkAuth(servRequest)), callback);
    }

    @DELETE
    @Path("/{actionId}")
    public void removeAction(@Context HttpServletRequest servRequest,
                             @PathParam("eventId") int eventId,
                             @QueryParam("callback") String callback,
                             @PathParam("patientId") int patientId,
                             @PathParam("actionId")int actionId) throws CoreException {
        wsImpl.removeAction(actionId);
    }

    @GET
    @Path("/{actionId}/lock")
    @Produces({"application/javascript", "application/x-javascript", "application/xml"})
    public Object lockAction(@Context HttpServletRequest servRequest,
                             @PathParam("eventId") int eventId,
                             @QueryParam("callback") String callback,
                             @PathParam("patientId") int patientId,
                             @PathParam("actionId")int actionId) throws CoreException {
        return new JSONWithPadding(wsImpl.lock(actionId, mkAuth(servRequest)), callback);
    }

    @PUT
    @Path("/{actionId}/lock")
    @Produces({"application/javascript", "application/x-javascript", "application/xml"})
    public Object prolongLockAction(@Context HttpServletRequest servRequest,
                                    @PathParam("eventId") int eventId,
                                    @QueryParam("callback") String callback,
                                    @PathParam("patientId") int patientId,
                                    @PathParam("actionId")int actionId) throws CoreException {
        return new JSONWithPadding(wsImpl.prolongLock(actionId, mkAuth(servRequest)), callback);
    }

    @DELETE
    @Path("/{actionId}/lock")
    public void releaseLockAction(@Context HttpServletRequest servRequest,
                                  @PathParam("eventId") int eventId,
                                  @QueryParam("callback") String callback,
                                  @PathParam("patientId") int patientId,
                                  @PathParam("actionId")int actionId) throws CoreException {
        wsImpl.releaseLock(actionId, mkAuth(servRequest));
    }



    /**
     * Запрос на структуру для первичного осмотра с копированием данных из предыдущего первичного осмотра ПРЕДЫДУЩЕЙ госпитализации
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws ru.korus.tmis.core.exception.CoreException
     * @see ru.korus.tmis.core.exception.CoreException
     */
    @GET
    @Path("/lastByType/{actionTypeId}")
    @Produces({"application/javascript", "application/x-javascript"})
    public Object getStructOfPrimaryMedExamWithCopy(@Context HttpServletRequest servRequest,
                                                    @PathParam("eventId") int eventId,
                                                    @QueryParam("callback") String callback,
                                                    @PathParam("patientId") int patientId,
                                                    @PathParam("actionTypeId") int actionTypeId) throws CoreException {
        if(eventId < 1)
            throw new CoreException("This service cannot be used without Event id");

        return new JSONWithPadding(wsImpl.getStructOfPrimaryMedExamWithCopy(actionTypeId, mkAuth(servRequest), eventId), callback);
    }

    /**
     * Кастомный веб-сервис для решения задачи http://helpdesk.korusconsulting.ru/browse/WEBMIS-367
     * Не стоит его нигде описывать и использовать. Следует удалить, если он больше не используется webmis-ом.
     * Отличается от обычного поиска документов соединением параметров mnem и assessmentTypeCode через OR
     */
    @GET
    @Path("/custom1")
    @Produces({"application/javascript", "application/x-javascript", "application/xml"})
    public Object custom1(@Context HttpServletRequest servRequest,
                          @PathParam("eventId") int eventId,
                          @QueryParam("callback") String callback,
                          @PathParam("patientId") int patientId,
                          @Context UriInfo info,
                          @QueryParam("limit")int limit,
                          @QueryParam("page")int  page,
                          @QueryParam("sortingField")String sortingField,    //сортировки вкл.
                          @QueryParam("sortingMethod")String sortingMethod,
                          @QueryParam("filter[actionTypeId]")int actionTypeId,
                          @QueryParam("filter[begDate]")long begDate,
                          @QueryParam("filter[endDate]")long endDate,
                          @QueryParam("filter[actionTypeCode]")Set<String> assessmentTypeCode,
                          @QueryParam("filter[doctorId]") int doctorId,
                          @QueryParam("filter[doctorName]") String doctorName,
                          @QueryParam("filter[speciality]") String speciality,
                          @QueryParam("filter[assessmentName]") String assessmentName,
                          @QueryParam("filter[departmentName]") String departmentName) throws CoreException {

        List<String> mnems = info.getQueryParameters().get("filter[mnem]");
        List<String> flatCodes = info.getQueryParameters().get("filter[flatCode]");
        if(flatCodes == null) flatCodes = new LinkedList<String>();

        List<String> mnemonics = new LinkedList<String>();
        if(mnems!=null)
            for(String mnem: mnems)
                if(mnem != null && !mnem.equals("")) mnemonics.add(mnem);

        AssessmentsListRequestDataFilter filter = new AssessmentsListRequestDataFilter(eventId, patientId, actionTypeId, assessmentTypeCode, begDate, endDate, doctorId, doctorName, speciality, assessmentName, departmentName, mnemonics, flatCodes){
            @Override
            public QueryDataStructure toQueryStructure() {
                QueryDataStructure qs = new QueryDataStructure();
                if(this.eventId()>0) {
                    qs.query_$eq(qs.query() + "AND a.event.id = :eventId\n");
                    qs.add("eventId", this.eventId());
                }
                if(this.patientId() > 0) {
                    qs.query_$eq(qs.query() + "AND a.event.patient.id = :patientId\n");
                    qs.add("patientId", this.patientId());
                }
                if (this.actionTypeId() > 0) {
                    qs.query_$eq(qs.query() + "AND a.actionType.id = :actionTypeId\n");
                    qs.add("actionTypeId", this.actionTypeId());
                }
                // Если заданы и коды и мнемоники - то соединяем по OR
                if((this.codes() != null && !this.codes().isEmpty()) && (this.mnemonics()!=null && !this.mnemonics().isEmpty())) {
                    qs.query_$eq(qs.query() + "AND (a.actionType.code IN  :code OR a.actionType.mnemonic IN  :mnemonic)\n");
                    qs.add("code", this.codes());
                    qs.add("mnemonic", this.mnemonics());

                } else if (this.mnemonics()!=null && !this.mnemonics().isEmpty()) {
                    qs.query_$eq(qs.query() + "AND a.actionType.mnemonic IN  :mnemonic\n");
                    qs.add("mnemonic",this.mnemonics());

                } else if (this.codes() != null && !this.codes().isEmpty()) {
                    qs.query_$eq(qs.query() + "AND a.actionType.code IN  :code\n");
                    qs.add("code",this.codes());
                }

                if (this.doctorId() > 0) {
                    qs.query_$eq(qs.query() + "AND a.createPerson.id = :doctorId\n");
                    qs.add("doctorId", this.doctorId());
                }
                if (this.doctorName() != null && !this.doctorName().isEmpty()) {
                    qs.query_$eq(qs.query() + "AND upper(a.createPerson.lastName) LIKE upper(:doctorName)\n");
                    qs.add("doctorName", "%" + this.doctorName() + "%");
                }
                if (this.begDate() != null) {
                    qs.query_$eq(qs.query() + "AND a.createDatetime >= :begDate\n");
                    qs.add("begDate", this.begDate());
                }
                if (this.endDate() != null) {
                    qs.query_$eq(qs.query() + "AND a.createDatetime <= :endDate\n");
                    qs.add("endDate", this.endDate());
                }
                if (this.speciality() != null && !this.speciality().isEmpty()) {
                    qs.query_$eq(qs.query() + "AND upper(a.createPerson.speciality.name) LIKE upper(:speciality)\n");
                    qs.add("speciality", "%" + this.speciality() + "%");
                }
                if (this.assessmentName() != null && !this.assessmentName().isEmpty()) {
                    qs.query_$eq(qs.query() + "AND upper(a.actionType.name) LIKE upper(:assessmentName)\n");
                    qs.add("assessmentName", "%" + this.assessmentName() + "%");
                }
                if (this.departmentName() != null && !this.departmentName().isEmpty()) {
                    qs.query_$eq(qs.query() + "AND upper(a.createPerson.orgStructure.name) LIKE upper(:departmentName)\n");
                    qs.add("departmentName", "%" + this.departmentName() + "%");
                }
                if (this.flatCodes()!=null && this.flatCodes().size() > 0) {
                    qs.query_$eq(qs.query() + "AND a.actionType.flatCode IN  :flatCodes\n");
                    qs.add("flatCodes",this.flatCodes());
                }
                return qs;
            }
        };
        AssessmentsListRequestData alrd= new AssessmentsListRequestData(sortingField, sortingMethod, limit, page, filter);
        return new JSONWithPadding(this.wsImpl.getListOfAssessmentsForPatientByEvent(alrd, mkAuth(servRequest)), callback);
    }

    @GET
    @Path("/therapies")
    @Produces({"application/javascript", "application/x-javascript", "application/xml"})
    public Object calculate(@Context HttpServletRequest servRequest,
                            @PathParam("eventId") int eventId,
                            @PathParam("patientId") int patientId,
                            @QueryParam("callback") String callback,
                            @QueryParam("query") String query) throws CoreException {
        if(patientId < 1)
            throw new IllegalArgumentException("Path param 'patientId' cannot be less than 1");

        ListContainer l = new ListContainer();
        l.setData(wsImpl.getTherapiesInfo(patientId));
        return new JSONWithPadding(l, callback);
    }

    private AuthData mkAuth(HttpServletRequest servRequest) {
        return wsImpl.checkTokenCookies(Arrays.asList(servRequest.getCookies()));
    }
}
