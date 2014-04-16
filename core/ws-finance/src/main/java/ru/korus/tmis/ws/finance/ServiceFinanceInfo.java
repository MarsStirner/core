package ru.korus.tmis.ws.finance;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        25.12.2012, 11:00:00 <br>
 * Company:     Korus Consulting IT<br>
 * Description: Интерфейс веб-сервиса экономических расчетов <br>
 */

import ru.korus.tmis.core.database.finance.PersonName;
import ru.korus.tmis.core.database.finance.ServicePaidInfo;
import ru.korus.tmis.core.exception.CoreException;

import javax.annotation.security.RolesAllowed;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.Date;
import java.util.List;

@WebService(targetNamespace = "http://korus.ru/tmis/ws/finance",
        name = "FinanceInfo")
interface ServiceFinanceInfo {

    /**
     * Имя параметра запроса, содержащего наименование подразделения
     */
    String WEB_PARAM_STRUCT = "struct";

    /**
     * Получить информацию по платным услугам в разрезе подразделений
     *
     * @param nameOfStructure - наименование подразделения; если равен пустой строке "", то
     *                        выдается информация по всем подразделениям; если равен null или отсутсвует в БД,
     *                        то выдается сообщение об ошибки "illegal name of structure"
     * @return в случае успеха, возвращает запаршиваемую финансовую информацию
     */
    @WebMethod()
    @RolesAllowed("FinanceUser")
    FinanceBean[] getFinanceInfo(@WebParam(name = WEB_PARAM_STRUCT, targetNamespace = "http://korus.ru/tmis/ws/finance")final String nameOfStructure);

    /**
     * Web-сервис «Информация о заказанных услугах»
     *
     * @param idTreatment - ID обращения в МИС (в БД МИС  - Event.id)
     * @return ServiceInfo -  информациz о заказанных платных услугах
     */
    ServiceListResult getServiceList(@WebParam(targetNamespace = "http://korus.ru/tmis/ws/finance") final Integer idTreatment) throws CoreException;

    /**
     * Web-сервис «Информация об оплаченных услугах»
     *
     * @param datePaid - дата и время оплаты/возврата (в БД МИС  - EventPayment.date)
     * @param codeContract -  номер договора (в БД МИС – Event. EventLocalContract.numberContract)
     * @param idTreatment - ID обращения в МИС (в БД МИС  - Event.id)
     * @param paidName - ФИО платильщика
     * @param listService - массив с информацией об оплаченных услугах
     * @return
     */
     Integer setPaymentInfo(@WebParam(targetNamespace = "http://korus.ru/tmis/ws/finance") final Date datePaid,
                            @WebParam(targetNamespace = "http://korus.ru/tmis/ws/finance") final String codeContract,
                            @WebParam(targetNamespace = "http://korus.ru/tmis/ws/finance") final Integer idTreatment,
                            @WebParam(targetNamespace = "http://korus.ru/tmis/ws/finance") final PersonName paidName,
                            @WebParam(targetNamespace = "http://korus.ru/tmis/ws/finance") final List<ServicePaidInfo> listService) throws CoreException;

}