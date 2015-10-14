package ru.korus.tmis.ws.webmis.rest;

import com.sun.jersey.api.json.JSONWithPadding;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.data.*;
import ru.korus.tmis.core.database.DbStaffBeanLocal;
import ru.korus.tmis.core.entity.model.OrgStructure;
import ru.korus.tmis.core.entity.model.RbHospitalBedProfile;
import ru.korus.tmis.core.entity.model.RbLaboratory;
import ru.korus.tmis.core.entity.model.Staff;
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
 * Список REST-сервисов для получения данных из справочников
 * Author: idmitriev Systema-Soft
 * Date: 4/3/13 2:06 PM
 * Since: 1.0.0.81
 */
@Stateless

public class CustomInfoRESTImpl {

    @EJB private WebMisREST wsImpl;

    @EJB private OrganizationImpl organizationImpl;

    @EJB private PrintTemplateImpl printTemplateImpl;

    @EJB private DbStaffBeanLocal dbStaffBeanLocal;


    //__________________________________________________________________________________________________________________
    //***********************************   НЕФОРМАЛИЗОВАННЫЕ МЕТОДЫ И ЗАПРОСЫ  ***********************************
    //__________________________________________________________________________________________________________________

    /**
     * Форма 007
     * Specification: https://docs.google.com/document/d/1a0AYF8QVpEMl_pKRcFDnP2vQzRmO-IkcG5JNStEcjMI/edit#heading=h.6ll2qz4wdcfr
     * URL: .../reports/f007
     * Since: ver 1.0.0.57
     * @param departmentId Идентификатор отделения, для которого отчет (задавать в url как QueryParam: "filter[departmentId]=...")
     * @param beginDate Дата и время начала выборки (задавать в url как QueryParam: "filter[beginDate]=...")
     *                Если не задан, то по умолчанию начало текущих медицинских суток (вчера 8:00) или,
     *                если задан endDate, то endDate минус сутки.
     * @param endDate Дата и время конца выборки (задавать в url как QueryParam: "filter[endDate]=...").
     *                Если не задан, то по умолчанию конец текущих медицинских суток (сегодня 7:59) или,
     *                если задан beginDate, то beginDate плюс сутки.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws ru.korus.tmis.core.exception.CoreException
     * @see ru.korus.tmis.core.exception.CoreException
     */
    @GET
    @Path("/reports/f007")
    @Produces({"application/javascript", "application/x-javascript"})
    public Object getForm007(@Context HttpServletRequest servRequest,
                             @QueryParam("callback") String callback,
                             @QueryParam("limit") int limit,
                             @QueryParam("page") int page,
                             @QueryParam("sortingField") String sortingField,
                             @QueryParam("sortingMethod") String sortingMethod,
                             @QueryParam("filter[departmentId]") int departmentId,
                             @QueryParam("filter[beginDate]")long beginDate,
                             @QueryParam("filter[endDate]")long endDate,
                             @QueryParam("filter[profileBed]")List<Integer> profileBeds ) throws CoreException {
        //Отделение обязательное поле, если не задано в запросе, то берем из роли специалиста
        AuthData authData = mkAuth(servRequest);
        Staff staff = authData == null ? null : dbStaffBeanLocal.getStaffById(authData.getUserId());
        final int depId = getCurDepartamentOrDefault(departmentId, staff);
        if(profileBeds.isEmpty()) { // если профили коек не заданы, то строим для всех
            Iterable<RbHospitalBedProfile> list = wsImpl.getAllAvailableBedProfiles();
            for(RbHospitalBedProfile curProfileBed : list) {
                profileBeds.add(curProfileBed.getId());
            }
        }
        return new JSONWithPadding(wsImpl.getForm007(depId, beginDate, endDate, profileBeds, authData),callback);
    }

    /**
     * Проверка на наличие номера в базе данных.
     * Используется для проверки номера обращения по госпитализации, полиса или СНИЛС.
     * @param name Имя проверяемого номера.<pre>
     * &#15; Возможные значения:
     * &#15; appealNumber - номер госпитализации;
     * &#15; SNILS - СНИЛС;
     * &#15; policy - мед. полис;</pre>
     * @param number Номер документа.
     * @param serial Серия документа.<pre>
     * &#15; Внимание! Параметр используется только для name = policy</pre>
     * @param typeId Тип документа.<pre>
     * &#15; Внимание! Параметр используется только для name = policy</pre>
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws ru.korus.tmis.core.exception.CoreException
     * @see ru.korus.tmis.core.exception.CoreException
     */
    @GET
    @Path("/checkExistance/{name}")
    @Produces({"application/javascript", "application/x-javascript"})
    public Object checkAppealNumber(@Context HttpServletRequest servRequest,
                                    @QueryParam("callback") String callback,
                                    @QueryParam("limit") int limit,
                                    @QueryParam("page") int page,
                                    @QueryParam("sortingField") String sortingField,
                                    @QueryParam("sortingMethod") String sortingMethod,
                                    @PathParam("name") String name,
                                    @QueryParam("typeId")int typeId,
                                    @QueryParam("number")String number,
                                    @QueryParam("serial")String serial) throws CoreException {
        return new JSONWithPadding(wsImpl.checkExistanceNumber(name, typeId, number, serial),callback);
    }

    /**
     * Забор биоматериала
     * URL: ../biomaterial/info
     * Спецификация: https://docs.google.com/spreadsheet/ccc?key=0AgE0ILPv06JcdEE0ajBZdmk1a29ncjlteUp3VUI2MEE&pli=1#gid=5
     * @since 1.0.0.64
     * @param departmentId Фильтр по идентификатору отделения (В url: filter[departmentId]=...)<pre>
     * &#15; По умолчанию значение достается из авторизационной роли</pre>
     * @param bd Фильтр по дате начала выборки (В url: filter[beginDate]=...)<pre>
     * &#15; По умолчанию - начало текущих суток (Dd.Mm.Year 00:00).</pre>
     * @param ed  Фильтр по дате окончания выборки (В url: filter[endDate]=...)<pre>
     * &#15; По умолчанию - начало текущих суток (Dd.Mm.Year 23:59).</pre>
     * @param status Фильтр по статусу забора (В url: filter[status]=...)<pre>
     * &#15; По умолчанию - 0.</pre>
     * @param biomaterial  Фильтр по типу биоматериала (В url: filter[biomaterial]=...)
     * &#15; По умолчанию - 0.</pre>
     * @param jobTicketId  Фильтр по id жобТикета (В url: filter[jobTicketId]=...)
     * &#15; По умолчанию - 0.</pre>
     * @return
     */
    @GET
    @Path("/biomaterial/info")
    @Produces({"application/javascript", "application/x-javascript"})
    public Object getTakingOfBiomaterial(@Context HttpServletRequest servRequest,
                                         @QueryParam("callback") String callback,
                                         @QueryParam("limit") int limit,
                                         @QueryParam("page") int page,
                                         @QueryParam("sortingField") String sortingField,
                                         @QueryParam("sortingMethod") String sortingMethod,
                                         @QueryParam("filter[jobTicketId]")int jobTicketId,
                                         @QueryParam("filter[departmentId]")int departmentId,
                                         @QueryParam("filter[beginDate]")String bd,
                                         @QueryParam("filter[endDate]")String ed,
                                         @QueryParam("filter[lab]")List<String> labs,
                                         @QueryParam("filter[status]") String status,
                                         @QueryParam("filter[biomaterial]") int biomaterial) throws CoreException {
        AuthData auth = mkAuth(servRequest);
        Date beginDate;
        Date endDate;
        DateTimeFormatter parser = ISODateTimeFormat.dateTimeParser();

        AuthData authData = mkAuth(servRequest);
        Staff staff = authData == null ? null : dbStaffBeanLocal.getStaffById(authData.getUserId());

        try { beginDate = parser.parseDateTime(bd).toDate();
        } catch (RuntimeException e) { beginDate = bd == null ? null : new Date(Long.parseLong(bd)); }

        try { endDate = parser.parseDateTime(ed).toDate(); }
        catch (RuntimeException e) { endDate = ed == null ? null : new Date(Long.parseLong(ed)); }

        //Отделение обязательное поле, если не задано в запросе, то берем из роли специалиста
        final int depId = getCurDepartamentOrDefault(departmentId, staff);
        final short statusS = (status!=null && !status.isEmpty()) ? Short.parseShort(status): -1;

        TakingOfBiomaterialRequesDataFilter filter = new TakingOfBiomaterialRequesDataFilter(jobTicketId,
                                                                                            depId,
                                                                                            beginDate == null ? 0 : beginDate.getTime(),
                                                                                            endDate == null ? 0 : endDate.getTime(),
                                                                                            statusS,
                                                                                            biomaterial, labs);
        TakingOfBiomaterialRequesData request = new TakingOfBiomaterialRequesData(sortingField, sortingMethod, filter);
        return new JSONWithPadding(wsImpl.getTakingOfBiomaterial(request, auth),callback);
    }

    private int getCurDepartamentOrDefault(int departmentId, Staff auth) {
        final OrgStructure orgStructure = auth.getOrgStructure();
        return (departmentId>0) ? departmentId : orgStructure != null? orgStructure.getId() : 1;
    }

    /**
     * Метод проставляет статус для тиккетов
     * @param data Список статусов для JobTicket
     * @return true - завершено успешно, false - завершено с ошибками
     */
    @PUT
    @Path("/jobTickets/status")
    @Produces({"application/javascript", "application/x-javascript"})
    public Object setStatusesForJobTickets(@Context HttpServletRequest servRequest,
                                           @QueryParam("callback") String callback,
                                           @QueryParam("limit") int limit,
                                           @QueryParam("page") int page,
                                           @QueryParam("sortingField") String sortingField,
                                           @QueryParam("sortingMethod") String sortingMethod,
                                           JobTicketStatusDataList data) throws CoreException {
        return new JSONWithPadding(wsImpl.updateJobTicketsStatuses(data, mkAuth(servRequest)),callback);
    }

    /**
     * Метод проставляет статус для тиккетов
     * @param data Список статусов для JobTicket
     * @return true - завершено успешно, false - завершено с ошибками
     */
    @PUT
    @Path("/sendActionsToLaboratory")
    @Produces({"application/javascript", "application/x-javascript"})
    public Object sendActionsToLaboratory(@Context HttpServletRequest servRequest,
                                            @QueryParam("callback") String callback,
                                            SendActionsToLaboratoryDataList data) throws CoreException{
       return new JSONWithPadding(wsImpl.sendActionsToLaboratory(data, mkAuth(servRequest)), callback );
    }

    /**
     * Запрос на список обращений пациентов для отделения и/или врача.
     * Роль: врач отделения
     * &#15; Возможные значения сортировки:
     * &#15; "id" - по идентификатору (значение по умолчанию);
     * &#15; "createDatetime" | "start" | "begDate" - по дате начала госпитализации;
     * &#15; "end" | "endDate" - по дате конца госпитализации;
     * &#15; "doctor" - по ФИО доктора;
     * &#15; "department" - по наименованию отделения;
     * &#15; "bed" - по обозначению койки;
     * &#15; "number" - по номеру истории болезни(НИБ);
     * &#15; "fullName" - по ФИО пациента;
     * &#15; "birthDate" - по дате рождения пациента;</pre>
     * &#15; Возможные значения:
     * &#15; "asc" - по возрастанию (значение по умолчанию);
     * &#15; "desc" - по убыванию;</pre>
     * @param endDate Фильтр значений по дате закрытия госпитализации.
     * @param departmentId Фильтр значений по отделению (по умолчанию берется из данных авторизации).
     * @param doctorId Фильтр значений по врачу (по умолчанию берется из данных авторизации).
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws ru.korus.tmis.core.exception.CoreException
     * @see ru.korus.tmis.core.exception.CoreException
     */
    @GET
    @Path("/departments/patients")
    @Produces({"application/javascript", "application/x-javascript"})
    public Object getAllPatientsForDepartmentOrUserByPeriod(@Context HttpServletRequest servRequest,
                                                            @QueryParam("callback") String callback,
                                                            @QueryParam("limit") int limit,
                                                            @QueryParam("page") int page,
                                                            @QueryParam("sortingField") String sortingField,
                                                            @QueryParam("sortingMethod") String sortingMethod,
                                                            @QueryParam("filter[date]")long endDate,
                                                            @QueryParam("filter[departmentId]") int departmentId,
                                                            @QueryParam("filter[doctorId]") int doctorId) throws CoreException {
        AuthData authData = mkAuth(servRequest);
        Staff staff = authData == null ? null : dbStaffBeanLocal.getStaffById(authData.getUserId());
        final int depId = getCurDepartamentOrDefault(departmentId, staff);
        PatientsListRequestData requestData = new PatientsListRequestData ( depId,
                                                                            doctorId,//auth.getUser().getId().intValue(),           //WEBMIS-809: Если параметр doctorId не указан, то ищем всех пациентов отделения.
                                                                            doctorId>0 ? 0 : authData.getUserRole().getId(),            //Если указан доктор, то ищем пациентов доктора.
                                                                            endDate,
                                                                            sortingField,
                                                                            sortingMethod,
                                                                            limit,
                                                                            page);
        return new JSONWithPadding(wsImpl.getAllPatientsForDepartmentIdAndDoctorIdByPeriod(requestData, authData),callback);
    }


    /**
     * Получение версии сборки jenkins
     * @return Нет фиксированного формата возвращаемой версии - следует выводить пользователю как plain text.
     */
    @GET
    @Path("/build")
    @Produces("text/plain")
    public String getBuildVersion() {
        return wsImpl.getBuildVersion();
    }


    @Path("/printTemplate")
    public Object getPrintTemplate() { return printTemplateImpl; }


    @Path("/organization")
    public Object getOrganizationById() { return organizationImpl; }


    @GET
    @Path("/labs")
    @Produces({"application/javascript", "application/xml"})
    public Object getLabs(@QueryParam("callback") String callback) {
        return new JSONWithPadding(new GenericEntity<List<RbLaboratory>>(wsImpl.getLabs()) {}, callback);
    }


    private AuthData mkAuth(HttpServletRequest servRequest) {
        return wsImpl.checkTokenCookies(Arrays.asList(servRequest.getCookies()));
    }
}
