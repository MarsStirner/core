package ru.korus.tmis.laboratory.bak;

import ru.korus.tmis.core.exception.CoreException;

import javax.jws.WebMethod;
import javax.jws.WebService;


/**
 * Интерфейс для взаимодействия ТМИС и новой МИС для интерграции с ЛИС.
 *
 * @author anosov
 *         Date: 18.06.13 18:10
 */
@WebService(
        targetNamespace = "http://korus.ru/tmis/client-bak",
        name = "service-bak-request"
)
public interface BakRequestService {

    /**
     * Отправить в ЛИС запрос анализа
     * Метод нельзя переименовывать, т.к. в НТК жестко прошит.
     *
     * @param actionId идентификатор действия, соответствующего назначенному анализу
     */
    @WebMethod
    void sendAnalysisRequest(int actionId) throws CoreException;
}
