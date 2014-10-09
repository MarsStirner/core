package ru.korus.tmis.ws.webmis.rest;

import com.sun.jersey.api.json.JSONWithPadding;
import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.data.*;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.core.logging.slf4j.interceptor.ServicesLoggingInterceptor;
import javax.interceptor.Interceptors;
import javax.ws.rs.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Список REST-сервисов для работы с данными о диагностиках
 * Author: idmitriev Systema-Soft
 * Date: 3/20/13 1:43PM
 * Since: 1.0.0.74
 */
@Interceptors(ServicesLoggingInterceptor.class)
public class DiagnosticsRegistryExRESTImpl {

    //protected static final String PATH = AppealsInfoRESTImpl.PATH + "{eventId}/diagnostics/";
    private WebMisREST wsImpl;
    private int eventId;
    private AuthData auth;
    private String callback;

    public DiagnosticsRegistryExRESTImpl(WebMisREST wsImpl, int eventId, String callback, AuthData auth) {
        this.eventId = eventId;
        this.auth = auth;
        this.wsImpl = wsImpl;
        this.callback = callback;
    }

    /**
     * Просмотр списка направлений на лабораторные исследования
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
    @Path("/laboratory")
    @Produces({"application/javascript", "application/x-javascript"})
    public Object getListOfLaboratoryDiagnosticsForPatientByEvent(   @QueryParam("limit")int limit,
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
                                                                     @QueryParam("filter[urgent]")Boolean  urgent,
                                                                     @QueryParam("filter[class]")Short  clazz) throws CoreException {


        DirectoryInfoRESTImpl.ActionTypesSubType atst_lab = DirectoryInfoRESTImpl.ActionTypesSubType.getType("laboratory");
        DirectoryInfoRESTImpl.ActionTypesSubType atst_bak = DirectoryInfoRESTImpl.ActionTypesSubType.getType("bak_lab");

        List mnemonics = new ArrayList<String>();
        mnemonics.add(atst_lab.getMnemonic());
        mnemonics.add(atst_bak.getMnemonic());

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
                atst_lab.getSubType(),
                mnemonics,
                (clazz==null) ? -1 : clazz);

        DiagnosticsListRequestData requestData = new DiagnosticsListRequestData(sortingField, sortingMethod, limit, page, filter);
        return new JSONWithPadding(wsImpl.getListOfDiagnosticsForPatientByEvent(requestData, this.auth),this.callback);
    }

    /**
     * Просмотр списка направлений на инструментальные исследования
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
    @Path("/instrumental")
    @Produces({"application/javascript", "application/x-javascript"})
    public Object getListOfInstrumentalDiagnosticsForPatientByEvent(@QueryParam("limit")int limit,
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
                                                             @QueryParam("filter[urgent]")Boolean  urgent,
                                                             @QueryParam("filter[class]")Short  clazz) throws CoreException {

        final DirectoryInfoRESTImpl.ActionTypesSubType atst = DirectoryInfoRESTImpl.ActionTypesSubType.getType("instrumental");
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
                new ArrayList<String>(){{this.add(atst.getMnemonic());}},
                (clazz==null) ? -1 : clazz);

        DiagnosticsListRequestData requestData = new DiagnosticsListRequestData(sortingField, sortingMethod, limit, page, filter);
        return new JSONWithPadding(wsImpl.getListOfDiagnosticsForPatientByEvent(requestData, this.auth),this.callback);
    }

    /**
     * Просмотр списка направлений на консультации исследования
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
    @Path("/consultations")
    @Produces({"application/javascript", "application/x-javascript"})
    public Object getListOfConsultationDiagnosticsForPatientByEvent(@QueryParam("limit")int limit,
                                                                    @QueryParam("page")int  page,
                                                                    @QueryParam("sortingField")String sortingField,           //сортировки вкл
                                                                    @QueryParam("sortingMethod")String sortingMethod,
                                                                    @QueryParam("filter[code]")String  diaTypeCode,
                                                                    @QueryParam("filter[date]") long date,
                                                                    @QueryParam("filter[diagnosticName]")String  diagnosticName,
                                                                    @QueryParam("filter[assignPersonId]")int  assignPersonId,
                                                                    @QueryParam("filter[execPersonId]")int  execPersonId,
                                                                    @QueryParam("filter[statusId]")int  statusId,
                                                                    @QueryParam("filter[office]")String  office,
                                                                    @QueryParam("filter[urgent]")Boolean  urgent,
                                                                    @QueryParam("filter[class]")Short  clazz,
                                                                    @QueryParam("filter[mnem]") List<String> mnems) throws CoreException {

        final DirectoryInfoRESTImpl.ActionTypesSubType atst = DirectoryInfoRESTImpl.ActionTypesSubType.getType("consultations");
        final DirectoryInfoRESTImpl.ActionTypesSubType atst_poly = DirectoryInfoRESTImpl.ActionTypesSubType.getType("consultations_poly");

        List<String> mnemonics;

        if(mnems == null || mnems.isEmpty()) { // Старое поведение
            mnemonics = new ArrayList<String>(){{this.add(atst.getMnemonic());this.add(atst_poly.getMnemonic());}};
        } else { // Новое поведение
            mnemonics = mnems;
        }

        DiagnosticsListRequestDataFilter filter = new DiagnosticsListRequestDataFilter( diaTypeCode,
                this.eventId,
                date,
                diagnosticName,
                assignPersonId,
                execPersonId,
                office,
                statusId,
                (urgent==null) ? -1 : (urgent) ? 1 : 0,
                "consultations",
                mnemonics,
                (clazz==null) ? -1 : clazz);

        DiagnosticsListRequestData requestData = new DiagnosticsListRequestData(sortingField, sortingMethod, limit, page, filter);
        return new JSONWithPadding(wsImpl.getListOfDiagnosticsForPatientByEvent(requestData, this.auth),this.callback);
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
    @Produces({"application/javascript", "application/x-javascript"})
    public Object insertLaboratoryStudies(JSONCommonData data) throws CoreException {
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
    @Produces({"application/javascript", "application/x-javascript"})
    public Object insertInstrumental(JSONCommonData data) throws CoreException {
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

    @POST
    @Path("/consultations")
    @Consumes("application/json")
    @Produces({"application/javascript", "application/x-javascript"})
    public Object insertConsultation(ConsultationRequestData data) throws CoreException {
        //ConsultationRequestData request = new ConsultationRequestData(eventId, actionTypeId, executorId, patientId, beginDate, endDate, urgent);
        return new JSONWithPadding(wsImpl.insertConsultation(data.rewriteDefault(data), this.auth), callback);
    }

    /**
     * Редактирование списка направлений на лабораторные исследования
     * @param data Json с данными о лабораторном исследовании как CommonData
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws ru.korus.tmis.core.exception.CoreException
     * @see ru.korus.tmis.core.exception.CoreException
     */       /*
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

              */

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
    @Produces({"application/javascript", "application/x-javascript"})
    public Object modifyLaboratoryStudy(JSONCommonData data,
                                          @PathParam("actionId")int actionId) throws CoreException {   //TODO: insert actionId (сейчас из коммондаты)

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
    @Produces({"application/javascript", "application/x-javascript"})
    public Object modifyInstrumentalStudy(JSONCommonData data,
                                          @PathParam("actionId")int actionId) throws CoreException {

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
    @Produces({"application/javascript", "application/x-javascript"})
    public Object modifyConsultationStudy(ConsultationRequestData data,
                                          @PathParam("actionId")int actionId) throws CoreException {
        /*
        CommonData com_data = new CommonData();
        com_data.setEntity(data.getData());
        return new JSONWithPadding(wsImpl.modifyInstrumentalStudies(eventId, com_data, this.auth), this.callback);
        */
        if (data.getPacientInQueue() > 0) {
            wsImpl.checkCountOfConsultations(data.getEventId(), data.getPacientInQueue(), data.getExecutorId(), data.getPlannedEndDate().getTime());
        }
        if (data.getPacientInQueue() == 0 && (data.getPlannedTime() == null || data.getPlannedTime().getId() == 0)) {
            ScheduleContainer timeToCreate = wsImpl.getPlannedTime(actionId);
            data.setPlannedTime(timeToCreate);
        }
        AssignmentsToRemoveDataList dataToRemove = new AssignmentsToRemoveDataList();
        dataToRemove.getData().add(new AssignmentToRemoveDataEntry(actionId));
        JSONWithPadding removed = new JSONWithPadding(wsImpl.removeDirection(dataToRemove, "consultations", this.auth), this.callback);
        return new JSONWithPadding(wsImpl.modifyConsultation(data.rewriteDefault(data), this.auth), callback);
    }

    /**
     * Удаление списка направлений на лабораторные исследования
     * @param data Json с данными о лабораторном исследовании как CommonData
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws ru.korus.tmis.core.exception.CoreException
     * @see ru.korus.tmis.core.exception.CoreException
     */
    @DELETE
    @Path("/laboratory")
    @Consumes("application/json")
    @Produces({"application/javascript", "application/x-javascript"})
    public Object removeLaboratoryStudies(AssignmentsToRemoveDataList data) throws CoreException {
        return new JSONWithPadding(wsImpl.removeDirection(data, "laboratory", this.auth), this.callback);
    }

    /**
     * Удаление списка направлений на инструментальные исследования
     * @param data Json с данными о лабораторном исследовании как CommonData
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws ru.korus.tmis.core.exception.CoreException
     * @see ru.korus.tmis.core.exception.CoreException
     */
    @DELETE
    @Path("/instrumental")
    @Consumes("application/json")
    @Produces({"application/javascript", "application/x-javascript"})
    public Object removeInstrumentalStudies(AssignmentsToRemoveDataList data) throws CoreException {
        return new JSONWithPadding(wsImpl.removeDirection(data, "instrumental", this.auth), this.callback);
    }

    /**
     * Удаление списка направлений на консультации
     * @param data Json с данными о лабораторном исследовании как CommonData
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws ru.korus.tmis.core.exception.CoreException
     * @see ru.korus.tmis.core.exception.CoreException
     */
    @DELETE
    @Path("/consultations")
    @Consumes("application/json")
    @Produces({"application/javascript", "application/x-javascript"})
    public Object removeConsultationsStudies(AssignmentsToRemoveDataList data) throws CoreException {
        return new JSONWithPadding(wsImpl.removeDirection(data, "consultations", this.auth), this.callback);
    }

    /**
     * Удаление направления на лабораторные исследования
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws ru.korus.tmis.core.exception.CoreException
     * @see ru.korus.tmis.core.exception.CoreException
     */
    @DELETE
    @Path("/laboratory/{actionId}")
    @Produces({"application/javascript", "application/x-javascript"})
    public Object removeLaboratoryStudy(@PathParam("actionId")int actionId) throws CoreException {
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
    @Produces({"application/javascript", "application/x-javascript"})
    public Object removeInstrumentalStudy(@PathParam("actionId")int actionId) throws CoreException {
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
    @Produces({"application/javascript", "application/x-javascript"})
    public Object removeConsultationsStudy(@PathParam("actionId")int actionId) throws CoreException {
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
    @Produces({"application/javascript", "application/x-javascript"})
    public Object getInfoAboutDiagnosticsForPatientByEvent(@PathParam("actionId")int actionId) throws CoreException {
        return new JSONWithPadding(wsImpl.getInfoAboutDiagnosticsForPatientByEvent(actionId, this.auth), this.callback);
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
    @Produces({"application/javascript", "application/x-javascript"})
    public Object getInfoAboutInstrumentalDiagnosticsForPatientByEvent(@PathParam("actionId")int actionId) throws CoreException {
        return new JSONWithPadding(wsImpl.getInfoAboutDiagnosticsForPatientByEvent(actionId, this.auth), this.callback);
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
    @Produces({"application/javascript", "application/x-javascript"})
    public Object getInfoAboutConsultationDiagnosticsForPatientByEvent(@PathParam("actionId")int actionId) throws CoreException {
        return new JSONWithPadding(wsImpl.getInfoAboutDiagnosticsForPatientByEvent(actionId, this.auth), this.callback);
    }

}
