package ru.korus.tmis.ws.laboratory.tmis;

import ru.korus.tmis.core.exception.CoreException;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * Интерфейс для взаимодействия ТМИС и новой МИС для интерграции с ЛИС.
 */
@WebService(
        targetNamespace = "http://korus.ru/tmis/client-laboratory",
        name = "client-laboratory2"
)
public interface Laboratory2ClientWebService {

    /**
     * Отправить в ЛИС запрос анализа
     *
     * @param actionId  идентификатор действия, соответствующего назначенному анализу
     */
    @WebMethod
    void sendAnalysisRequest(int actionId) throws CoreException;
}
