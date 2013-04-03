package ru.korus.tmis.ws.webmis.rest;

import com.sun.jersey.api.json.JSONWithPadding;
import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.data.HospitalBedData;
import ru.korus.tmis.core.data.HospitalBedDataListFilter;
import ru.korus.tmis.core.data.HospitalBedDataRequest;
import ru.korus.tmis.core.logging.slf4j.interceptor.ServicesLoggingInterceptor;
import ru.korus.tmis.ws.impl.WebMisRESTImpl;
import javax.interceptor.Interceptors;
import javax.ws.rs.*;

/**
 * Список REST-сервисов для работы с коечным фондом
 * Author: idmitriev Systema-Soft
 * Date: 3/20/13 5:58 PM
 * Since: 1.0.0.74
 */
@Interceptors(ServicesLoggingInterceptor.class)
public class HospitalBedRegistryRESTImpl {

   // protected static final String PATH = AppealsInfoRESTImpl.PATH + "{eventId}/hospitalbed/";
    private WebMisRESTImpl wsImpl;
    private int eventId;
    private AuthData auth;
    private String callback;

    public HospitalBedRegistryRESTImpl(WebMisRESTImpl wsImpl, int eventId, String callback, AuthData auth) {
       this.eventId = eventId;
       this.auth = auth;
       this.wsImpl = wsImpl;
       this.callback = callback;
    }
    /**
     * Регистрация на койке
     * @param data Json c данными о койке как HospitalBedData
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws ru.korus.tmis.core.exception.CoreException
     * @see ru.korus.tmis.core.exception.CoreException
     * @see ru.korus.tmis.core.data.HospitalBedData
     */
    @POST
    @Consumes("application/json")
    @Produces("application/x-javascript")
    public  Object registryPatientToHospitalBed (HospitalBedData data) {
        return new JSONWithPadding(wsImpl.registryPatientToHospitalBed(this.eventId, data, this.auth), this.callback);
    }

    /**
     * Регистрация на койке (редактирование)
     * @param data Json c данными о койке как HospitalBedData
     * @param actionId Идентификатор редактируемого действия типа 'Движение'.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws ru.korus.tmis.core.exception.CoreException
     * @see ru.korus.tmis.core.exception.CoreException
     * @see HospitalBedData
     */
    @PUT
    @Path("/{actionId}/")
    @Consumes("application/json")
    @Produces("application/x-javascript")
    public  Object modifyPatientToHospitalBed(HospitalBedData data,
                                              @PathParam("actionId") int actionId) {
        return new JSONWithPadding(wsImpl.modifyPatientToHospitalBed(actionId, data, this.auth), this.callback);
    }

    /**
     * Список движения по отделениям
     * @param limit Максимальное количество выводимых элементов в списке.
     * @param page Номер выводимой страницы.
     * @param sortingField Наименование поля для сортировки.<pre>
     * &#15; Возможные значения:
     * &#15; "createDatetime" - по идентификатору осмотра (значение по умолчанию);</pre>
     * @param sortingMethod Метод сортировки.<pre>
     * &#15; Возможные значения:
     * &#15; "asc" - по возрастанию (значение по умолчанию);
     * &#15; "desc" - по убыванию;</pre>
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws ru.korus.tmis.core.exception.CoreException
     * @see ru.korus.tmis.core.exception.CoreException
     */
    @GET
    @Produces("application/x-javascript")
    public Object getMovingListForEvent(
            @QueryParam("limit")int limit,
            @QueryParam("page")int  page,
            @QueryParam("sortingField")String sortingField,   //сортировки выкл.
            @QueryParam("sortingMethod")String sortingMethod) {

        HospitalBedDataListFilter filter = new HospitalBedDataListFilter(this.eventId);
        HospitalBedDataRequest request= new HospitalBedDataRequest(sortingField, sortingMethod, limit, page, filter);
        return new JSONWithPadding(wsImpl.getMovingListForEvent(request, this.auth), this.callback);
    }


    /**
     * Создание направления/перевода в отделение.
     * @param data json данные о движении пациента как HospitalBedData
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws ru.korus.tmis.core.exception.CoreException
     * @see ru.korus.tmis.core.exception.CoreException
     * @see HospitalBedData
     */
    @POST
    @Path("/moving/")
    @Consumes("application/json")
    @Produces("application/x-javascript")
    public  Object movingPatientToDepartment(HospitalBedData data) {
        return new JSONWithPadding(wsImpl.movingPatientToDepartment(this.eventId, data, this.auth), this.callback);
    }

    /**
     * Данные об регистрации на койке
     * @param actionId Идентификатор действия типа 'Движение' для которого отменяется регистрация на койке.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws ru.korus.tmis.core.exception.CoreException
     * @see ru.korus.tmis.core.exception.CoreException
     */
    @GET
    @Path("/{actionId}")
    @Produces("application/x-javascript")
    public Object getInfoHospitalBedForPatient(@PathParam("actionId") int actionId) {
        return new JSONWithPadding(wsImpl.getPatientToHospitalBedById(actionId, this.auth), this.callback);
    }

    /**
     * Отмена регистрациии на койке
     * @param actionId Идентификатор действия типа 'Движение' для которого отменяется регистрация на койке.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws ru.korus.tmis.core.exception.CoreException
     * @see ru.korus.tmis.core.exception.CoreException
     */
    @DELETE
    @Path("/{actionId}")
    @Produces("application/x-javascript")
    public Object callOffHospitalBedForPatient(@PathParam("actionId") int actionId) {
        return new JSONWithPadding(wsImpl.callOffHospitalBedForPatient(actionId, this.auth), this.callback);
    }

    /**
     * Сервис на получение списка коек с меткой свободно/занято.
     * Url: .../hospitalbed/vacant/{departmentId}
     * Since: ver 1.0.0.57
     * @param departmentId Идентификатор отделения.
     * @return Список коек в json-формате.
     */
    @GET
    @Path("/vacant/")
    @Produces("application/x-javascript")
    public Object getVacantHospitalBeds(@QueryParam("filter[departmentId]") int departmentId) {
        //Отделение обязательное поле, если не задано в запросе, то берем из роли специалиста
        int depId = (departmentId>0) ? departmentId : this.auth.getUser().getOrgStructure().getId().intValue();
        return new JSONWithPadding(wsImpl.getVacantHospitalBeds(depId, this.auth), this.callback);
    }
}
