package ru.korus.tmis.laboratory.across;

import ru.korus.tmis.core.exception.CoreException;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * Интерфейс для взаимодействия ТМИС и новой МИС для интерграции с ЛИС.
 */
@WebService(
        targetNamespace = "http://korus.ru/tmis/client-laboratory",
        name = "service-request-across"
)
public interface AcrossRequestService {

    /**
     * Отправить в ЛИС Акросс запрос на анализ.
     * Метод нельзя переименовывать, т.к. в НТК жестко прошит.
     *
     * @param actionId идентификатор действия, соответствующего назначенному анализу
     */
    @WebMethod
    void sendAnalysisRequest(int actionId) throws CoreException;
}
