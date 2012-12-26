package ru.korus.tmis.ws.finance;

import javax.annotation.security.RolesAllowed;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * Интерфейс веб-сервиса экономических расчетов
 */
@WebService(targetNamespace = "http://korus.ru/tmis/ws/finance",
        name = "FinanceInfo")
public interface ServiceFinanceInfo {

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
    FinanceBean[] getFinanceInfo(@WebParam(name = "nameOfStructure", targetNamespace = "http://korus.ru/tmis/ws/finance") String nameOfStructure);
}