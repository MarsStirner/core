package ru.korus.tmis.ws.webmis.rest;

import com.sun.jersey.api.json.JSONWithPadding;
import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.data.*;
import ru.korus.tmis.core.logging.slf4j.interceptor.ServicesLoggingInterceptor;
import ru.korus.tmis.ws.impl.WebMisRESTImpl;
import javax.interceptor.Interceptors;
import javax.ws.rs.*;

/**
 * Список REST-сервисов для работы с данными о диагностиках
 * Author: idmitriev Systema-Soft
 * Date: 3/20/13 1:43PM
 * Since: 1.0.0.74
 */
@Interceptors(ServicesLoggingInterceptor.class)
public class DiagnosticsRegistryExRESTImpl {

    //protected static final String PATH = AppealsInfoRESTImpl.PATH + "{eventId}/diagnostics/";
    private WebMisRESTImpl wsImpl;
    private int eventId;
    private AuthData auth;
    private String callback;

    public DiagnosticsRegistryExRESTImpl(WebMisRESTImpl wsImpl, int eventId, String callback, AuthData auth) {
        this.eventId = eventId;
        this.auth = auth;
        this.wsImpl = wsImpl;
        this.callback = callback;
    }

    /**
     * Просмотр списка направлений на {var} исследования
     * var = {laboratory, instrumental, consultations}
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
     * @param date Фильтр по дате исследования.
     * @param diagnosticName Фильтр по наименованию исследования.
     * @param assignPersonId Фильтр по направившему на исследование специалисту.
     * @param execPersonId  Фильтр по проводившему исследование специалисту.
     * @param statusId Фильтр по статусу исследования.
     * @param urgent  Фильтр по срочности исследования.
     * @param office  Фильтр по коду кабинета.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws ru.korus.tmis.core.exception.CoreException
     * @see ru.korus.tmis.core.exception.CoreException
     */
    @GET
    @Path("/{var}")
    @Produces("application/x-javascript")
    public Object getListOfLaboratoryDiagnosticsForPatientByEvent(  @PathParam("var") String var,
                                                                    @QueryParam("limit")int limit,
                                                                    @QueryParam("page")int  page,
                                                                    @QueryParam("sortingField")String sortingField,           //сортировки вкл
                                                                    @QueryParam("sortingMethod")String sortingMethod,
                                                                    @QueryParam("filter[code]")String  diaTypeCode,
                                                                    //@QueryParam("filter[diagnosticDate]")long  diagnosticDate,
                                                                    //@QueryParam("filter[directionDate]")long  directionDate,
                                                                    @QueryParam("filter[date]") long date,
                                                                    @QueryParam("filter[diagnosticName]")String  diagnosticName,
                                                                    @QueryParam("filter[assignPersonId]")int  assignPersonId,
                                                                    @QueryParam("filter[execPersonId]")int  execPersonId,
                                                                    @QueryParam("filter[statusId]")int  statusId,
                                                                    @QueryParam("filter[office]")String  office,
                                                                    @QueryParam("filter[urgent]")Boolean  urgent) {

        DirectoryInfoRESTImpl.ActionTypesSubType atst = DirectoryInfoRESTImpl.ActionTypesSubType.getType(var);
        DiagnosticsListRequestDataFilter filter = new DiagnosticsListRequestDataFilter( diaTypeCode,
                                                                                        this.eventId,
                                                                                        //diagnosticDate,
                                                                                        //directionDate,
                                                                                        date,
                                                                                        diagnosticName,
                                                                                        assignPersonId,
                                                                                        execPersonId,
                                                                                        office,
                                                                                        statusId,
                                                                                        (urgent==null) ? -1 : (urgent) ? 1 : 0,
                                                                                        atst.getSubType(),
                                                                                        atst.getMnemonic());

        DiagnosticsListRequestData requestData = new DiagnosticsListRequestData(sortingField, sortingMethod, limit, page, filter);
        return new JSONWithPadding(wsImpl.getListOfDiagnosticsForPatientByEvent(requestData),this.callback);
    }

    /**
     * Создание направления на лабораторное исследование
     * @param data Json с данными о лабораторном исследовании как CommonData
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws ru.korus.tmis.core.exception.CoreException
     * @see ru.korus.tmis.core.exception.CoreException
     */
    @POST
    @Path("/laboratory")
    @Consumes("application/json")
    @Produces("application/x-javascript")
    public Object insertLaboratoryStudies(JSONCommonData data) {
        //DirectoryInfoRESTImpl.ActionTypesSubType atst = DirectoryInfoRESTImpl.ActionTypesSubType.getType(var);
        CommonData com_data = new CommonData();
        com_data.setEntity(data.getData());
        return new JSONWithPadding(wsImpl.insertLaboratoryStudies(this.eventId, com_data, this.auth), this.callback);
    }

    /**
     * Создание направления на инструментальное исследование
     * @param data json данные о инструментальном исследовании как JSONCommonData.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws ru.korus.tmis.core.exception.CoreException
     * @see ru.korus.tmis.core.exception.CoreException
     */

    @POST
    @Path("/instrumental")
    @Consumes("application/json")
    @Produces("application/x-javascript")
    public Object insertInstrumental(JSONCommonData data) {
        CommonData com_data = new CommonData();
        com_data.setEntity(data.getData());
        return new JSONWithPadding(wsImpl.insertInstrumentalStudies(this.eventId, com_data, this.auth), this.callback);
    }

    /**
     * Создание направления на консультацию к врачу
     * @param data json данные о консультации как ConsultationRequestData.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws ru.korus.tmis.core.exception.CoreException
     * @see ru.korus.tmis.core.exception.CoreException
     */
     /*
    @POST
    @Path("/consultations")
    @Consumes("application/json")
    @Produces("application/x-javascript")
    public Object insertConsultation(ConsultationRequestData data) {
        //ConsultationRequestData request = new ConsultationRequestData(eventId, actionTypeId, executorId, patientId, beginDate, endDate, urgent);
        return new JSONWithPadding(wsImpl.insertConsultation(data.rewriteDefault(data), this.auth), callback);
    }
       */
    /**
     * Редактирование списка направлений на лабораторные исследования
     * @param data Json с данными о лабораторном исследовании как CommonData
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws ru.korus.tmis.core.exception.CoreException
     * @see ru.korus.tmis.core.exception.CoreException
     */
    @PUT
    @Path("/{var}/")
    @Consumes("application/json")
    @Produces("application/x-javascript")
    public Object modifyLaboratoryStudies(JSONCommonData data,
                                          @PathParam("var") String var) {

        DirectoryInfoRESTImpl.ActionTypesSubType atst = DirectoryInfoRESTImpl.ActionTypesSubType.getType(var);
        switch (atst){
            case LABORATORY: {
                CommonData com_data = new CommonData();
                com_data.setEntity(data.getData());
                return new JSONWithPadding(wsImpl.modifyLaboratoryStudies(eventId, com_data, this.auth), this.callback);
            }
            case INSTRUMENTAL: {
                return null;
            }
            case CONSULTATIONS: {
                return null;
                //ConsultationRequestData request = new ConsultationRequestData(eventId, actionTypeId, executorId, patientId, beginDate, endDate, urgent);
                //JSONWithPadding returnValue = new JSONWithPadding(wsImpl.insertConsultation(data.rewriteDefault(data)), callback);
            }
            default: {
                return null;
            }
        }
    }



    /**
     * Редактирование направления на лабораторные исследование
     * @param data Json с данными о лабораторном исследовании как CommonData
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws ru.korus.tmis.core.exception.CoreException
     * @see ru.korus.tmis.core.exception.CoreException
     */
    @PUT
    @Path("/laboratory/{actionId}")
    @Consumes("application/json")
    @Produces("application/x-javascript")
    public Object modifyLaboratoryStudy(JSONCommonData data,
                                          @PathParam("actionId")int actionId) {   //TODO: insert actionId (сейчас из коммондаты)

        CommonData com_data = new CommonData();
        com_data.setEntity(data.getData());
        return new JSONWithPadding(wsImpl.modifyLaboratoryStudies(eventId, com_data, this.auth), this.callback);
    }

    /**
     * Редактирование направления на инструментальное исследование
     * @param data Json с данными о инструментальном исследовании как CommonData
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws ru.korus.tmis.core.exception.CoreException
     * @see ru.korus.tmis.core.exception.CoreException
     */
    @PUT
    @Path("/instrumental/{actionId}")
    @Consumes("application/json")
    @Produces("application/x-javascript")
    public Object modifyInstrumentalStudy(JSONCommonData data,
                                          @PathParam("actionId")int actionId) {

        CommonData com_data = new CommonData();
        com_data.setEntity(data.getData());
        return new JSONWithPadding(wsImpl.modifyInstrumentalStudies(eventId, com_data, this.auth), this.callback);
    }

    /**
     * Редактирование направления на констультацию
     * @param data Json с данными о консультации как CommonData
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws ru.korus.tmis.core.exception.CoreException
     * @see ru.korus.tmis.core.exception.CoreException
     */
    @PUT
    @Path("/consultations/{actionId}")
    @Consumes("application/json")
    @Produces("application/x-javascript")
    public Object modifyConsultationStudy(JSONCommonData data,
                                          @PathParam("actionId")int actionId) {

        CommonData com_data = new CommonData();
        com_data.setEntity(data.getData());
        return new JSONWithPadding(wsImpl.modifyInstrumentalStudies(eventId, com_data, this.auth), this.callback);
    }

    /**
     * Удаление списка направлений на лабораторные исследования
     * @param data Json с данными о лабораторном исследовании как CommonData
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws ru.korus.tmis.core.exception.CoreException
     * @see ru.korus.tmis.core.exception.CoreException
     */
    @DELETE
    @Path("/{var}/")
    @Consumes("application/json")
    @Produces("application/x-javascript")
    public Object removeLaboratoryStudies(AssignmentsToRemoveDataList data,
                                          @PathParam("var") String var) {

        DirectoryInfoRESTImpl.ActionTypesSubType atst = DirectoryInfoRESTImpl.ActionTypesSubType.getType(var);

        switch (atst){
            case LABORATORY: {
                return new JSONWithPadding(wsImpl.removeDirection(data, "laboratory", this.auth), this.callback);
            }
            case INSTRUMENTAL: {
                return null;
            }
            case CONSULTATIONS: {
                return null;
            }
            default: {
                return null;
            }
        }
    }

    /**
     * Удаление направления на лабораторные исследования
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws ru.korus.tmis.core.exception.CoreException
     * @see ru.korus.tmis.core.exception.CoreException
     */
    @DELETE
    @Path("/laboratory/{actionId}")
    @Produces("application/x-javascript")
    public Object removeLaboratoryStudy(@PathParam("actionId")int actionId) {
        AssignmentsToRemoveDataList data = new AssignmentsToRemoveDataList();
        data.getData().add(new AssignmentToRemoveDataEntry(actionId));
        return new JSONWithPadding(wsImpl.removeDirection(data, "laboratory", this.auth), this.callback);
    }

    /**
     * Удаление направления на инструментальные исследования
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws ru.korus.tmis.core.exception.CoreException
     * @see ru.korus.tmis.core.exception.CoreException
     */
    @DELETE
    @Path("/instrumental/{actionId}")
    @Produces("application/x-javascript")
    public Object removeInstrumentalStudy(@PathParam("actionId")int actionId) {
        AssignmentsToRemoveDataList data = new AssignmentsToRemoveDataList();
        data.getData().add(new AssignmentToRemoveDataEntry(actionId));
        return new JSONWithPadding(wsImpl.removeDirection(data, "instrumental", this.auth), this.callback);
    }

    /**
     * Удаление направления на консультации
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws ru.korus.tmis.core.exception.CoreException
     * @see ru.korus.tmis.core.exception.CoreException
     */
    @DELETE
    @Path("/consultations/{actionId}")
    @Produces("application/x-javascript")
    public Object removeConsultationsStudy(@PathParam("actionId")int actionId) {
        AssignmentsToRemoveDataList data = new AssignmentsToRemoveDataList();
        data.getData().add(new AssignmentToRemoveDataEntry(actionId));
        return new JSONWithPadding(wsImpl.removeDirection(data, "consultations", this.auth), this.callback);
    }

    /**
     * Просмотр результатов лабораторных исследований
     * @param actionId идентификатор исследования.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws ru.korus.tmis.core.exception.CoreException
     * @see ru.korus.tmis.core.exception.CoreException
     */
    @GET
    @Path("/laboratory/{actionId}")
    @Produces("application/x-javascript")
    public Object getInfoAboutDiagnosticsForPatientByEvent(@PathParam("actionId")int actionId) {
        return new JSONWithPadding(wsImpl.getInfoAboutDiagnosticsForPatientByEvent(actionId), this.callback);
    }

    /**
     * Просмотр результатов инструментальных исследований
     * @param actionId идентификатор исследования.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws ru.korus.tmis.core.exception.CoreException
     * @see ru.korus.tmis.core.exception.CoreException
     */
    @GET
    @Path("/instrumental/{actionId}")
    @Produces("application/x-javascript")
    public Object getInfoAboutInstrumentalDiagnosticsForPatientByEvent(@PathParam("actionId")int actionId) {
        return new JSONWithPadding(wsImpl.getInfoAboutDiagnosticsForPatientByEvent(actionId), this.callback);
    }

    /**
     * Просмотр результатов консультаций
     * @param actionId идентификатор исследования.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws ru.korus.tmis.core.exception.CoreException
     * @see ru.korus.tmis.core.exception.CoreException
     */
    @GET
    @Path("/consultations/{actionId}")
    @Produces("application/x-javascript")
    public Object getInfoAboutConsultationDiagnosticsForPatientByEvent(@PathParam("actionId")int actionId) {
        return new JSONWithPadding(wsImpl.getInfoAboutDiagnosticsForPatientByEvent(actionId), this.callback);
    }
}
