package ru.korus.tmis.ws.webmis.rest;

import com.sun.jersey.api.json.JSONWithPadding;
import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.data.JobTicketStatusDataList;
import ru.korus.tmis.core.data.PatientsListRequestData;
import ru.korus.tmis.core.data.TakingOfBiomaterialRequesData;
import ru.korus.tmis.core.data.TakingOfBiomaterialRequesDataFilter;
import ru.korus.tmis.core.logging.slf4j.interceptor.ServicesLoggingInterceptor;
import ru.korus.tmis.ws.impl.WebMisRESTImpl;

import javax.interceptor.Interceptors;
import javax.ws.rs.*;

/**
 * Список REST-сервисов для получения данных из справочников
 * Author: idmitriev Systema-Soft
 * Date: 4/3/13 2:06 PM
 * Since: 1.0.0.81
 */
@Interceptors(ServicesLoggingInterceptor.class)
public class CustomInfoRESTImpl {

    private WebMisRESTImpl wsImpl;
    private int limit;
    private int  page;
    private String sortingField;
    private String sortingMethod;
    private AuthData auth;
    private String callback;

    public CustomInfoRESTImpl(WebMisRESTImpl wsImpl, String callback,
                                 int limit, int  page, String sortingField, String sortingMethod,
                                 AuthData auth) {
        this.auth = auth;
        this.wsImpl = wsImpl;
        this.callback = callback;
        this.limit = limit;
        this.page = page;
        this.sortingField = sortingField;
        this.sortingMethod = sortingMethod;
    }

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
    @Produces("application/x-javascript")
    public Object getForm007( @QueryParam("filter[departmentId]") int departmentId,
                              @QueryParam("filter[beginDate]")long beginDate,
                              @QueryParam("filter[endDate]")long endDate) {
        //Отделение обязательное поле, если не задано в запросе, то берем из роли специалиста
        int depId = (departmentId>0) ? departmentId : this.auth.getUser().getOrgStructure().getId().intValue();
        return new JSONWithPadding(wsImpl.getForm007(depId, beginDate, endDate, this.auth),this.callback);
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
    @Produces("application/x-javascript")
    public Object checkAppealNumber(@PathParam("name") String name,
                                    @QueryParam("typeId")int typeId,
                                    @QueryParam("number")String number,
                                    @QueryParam("serial")String serial) {
        return new JSONWithPadding(wsImpl.checkExistanceNumber(name, typeId, number, serial),this.callback);
    }

    /**
     * Забор биоматериала
     * URL: ../biomaterial/info
     * Спецификация: https://docs.google.com/spreadsheet/ccc?key=0AgE0ILPv06JcdEE0ajBZdmk1a29ncjlteUp3VUI2MEE&pli=1#gid=5
     * @since 1.0.0.64
     * @param departmentId Фильтр по идентификатору отделения (В url: filter[departmentId]=...)<pre>
     * &#15; По умолчанию значение достается из авторизационной роли</pre>
     * @param beginDate Фильтр по дате начала выборки (В url: filter[beginDate]=...)<pre>
     * &#15; По умолчанию - начало текущих суток (Dd.Mm.Year 00:00).</pre>
     * @param endDate  Фильтр по дате окончания выборки (В url: filter[endDate]=...)<pre>
     * &#15; По умолчанию - начало текущих суток (Dd.Mm.Year 23:59).</pre>
     * @param status Фильтр по статусу забора (В url: filter[status]=...)<pre>
     * &#15; По умолчанию - 0.</pre>
     * @param biomaterial  Фильтр по статусу забора (В url: filter[status]=...)
     * @return
     */
    @GET
    @Path("/biomaterial/info")
    @Produces("application/x-javascript")
    public Object getTakingOfBiomaterial(@QueryParam("filter[departmentId]")int departmentId,
                                         @QueryParam("filter[beginDate]")long beginDate,
                                         @QueryParam("filter[endDate]")long endDate,
                                         @QueryParam("filter[status]") String status,
                                         @QueryParam("filter[biomaterial]") int biomaterial)   {

        //Отделение обязательное поле, если не задано в запросе, то берем из роли специалиста
        int depId = (departmentId>0) ? departmentId : this.auth.getUser().getOrgStructure().getId().intValue();
        short statusS = (status!=null && !status.isEmpty()) ? Short.parseShort(status): -1;

        TakingOfBiomaterialRequesDataFilter filter = new TakingOfBiomaterialRequesDataFilter(depId,
                beginDate,
                endDate,
                statusS,
                biomaterial);
        TakingOfBiomaterialRequesData request = new TakingOfBiomaterialRequesData(this.sortingField, this.sortingMethod, filter);
        return new JSONWithPadding(wsImpl.getTakingOfBiomaterial(request, this.auth),this.callback);
    }

    /**
     * Метод проставляет статус для тиккетов
     * @param data Список статусов для JobTicket
     * @return true - завершено успешно, false - завершено с ошибками
     */
    @PUT
    @Path("/jobTickets/status")
    @Produces("application/x-javascript")
    public Object setStatusesForJobTickets(JobTicketStatusDataList data) {
        return new JSONWithPadding(wsImpl.updateJobTicketsStatuses(data, this.auth),this.callback);
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
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws ru.korus.tmis.core.exception.CoreException
     * @see ru.korus.tmis.core.exception.CoreException
     */
    @GET
    @Path("/departments/patients")
    @Produces("application/x-javascript")
    public Object getAllPatientsForDepartmentOrUserByPeriod(@QueryParam("filter[date]")long endDate,
                                                            @QueryParam("filter[departmentId]") int departmentId) {

        int depId = (departmentId>0) ? departmentId : auth.getUser().getOrgStructure().getId().intValue();
        PatientsListRequestData requestData = new PatientsListRequestData ( depId,
                auth.getUser().getId().intValue(),
                auth.getUserRole().getId().intValue(),
                endDate,
                sortingField,
                sortingMethod,
                limit,
                page);
        return new JSONWithPadding(wsImpl.getAllPatientsForDepartmentIdAndDoctorIdByPeriod(requestData, this.auth),this.callback);
    }
}
