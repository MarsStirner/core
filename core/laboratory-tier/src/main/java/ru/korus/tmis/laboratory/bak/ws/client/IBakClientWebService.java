package ru.korus.tmis.laboratory.bak.ws.client;

import ru.korus.tmis.core.exception.CoreException;

import javax.jws.WebMethod;
import javax.jws.WebService;


/**
 * Интерфейс для взаимодействия ТМИС и новой МИС для интерграции с ЛИС.
 *
 *
 * @author anosov
 *         Date: 18.06.13 18:10
 */
@WebService(
        targetNamespace = "http://korus.ru/tmis/client-laboratory",
        name = "client-bak"
)
public interface IBakClientWebService {

    /**
     * Отправить в ЛИС запрос анализа
     *
     * @param actionId идентификатор действия, соответствующего назначенному анализу
     */
    @WebMethod
    void sendAnalysisRequest(int actionId) throws CoreException;
}
