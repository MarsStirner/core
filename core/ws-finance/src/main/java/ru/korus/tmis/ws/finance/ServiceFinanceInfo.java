package ru.korus.tmis.ws.finance;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        25.12.2012, 11:00:00 <br>
 * Company:     Korus Consulting IT<br>
 * Description: Интерфейс веб-сервиса экономических расчетов <br>
 */

import javax.annotation.security.RolesAllowed;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(targetNamespace = "http://korus.ru/tmis/ws/finance",
        name = "FinanceInfo")
public interface ServiceFinanceInfo {

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
    FinanceBean[] getFinanceInfo(@WebParam(name = WEB_PARAM_STRUCT, targetNamespace = "http://korus.ru/tmis/ws/finance") String nameOfStructure);
}