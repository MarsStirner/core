package ru.korus.tmis.laboratory.bak;

import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.laboratory.bak.ws.server.model.hl7.complex.MCCIIN000002UV01;
import ru.korus.tmis.laboratory.bak.ws.server.model.hl7.complex.POLBIN224100UV01;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import static ru.korus.tmis.util.CompileTimeConfigManager.Laboratory.Namespace;
import static ru.korus.tmis.laboratory.bak.ws.server.model.hl7.HL7Specification.NAMESPACE;
import static ru.korus.tmis.laboratory.bak.ws.server.model.hl7.HL7Specification.SUCCESS_ACCEPT_EVENT;

/**
 * @author anosov@outlook.com
 */
@WebService(
        targetNamespace = "http://www.korusconsulting.ru",
        name = "service-bak-results")
public interface BakResultService {

    /**
     * Сообщение с результатами из БАК лаборатории
     *
     * @param response результаты в формате HL7 POLB
     * @return результат в формате HL7 MCCIIN000002UV01
     * @throws CoreException ошибка формата
     */
    @WebMethod(operationName = "setAnalysisResults")
    @WebResult(name = SUCCESS_ACCEPT_EVENT, targetNamespace = NAMESPACE, partName = "Body")
    MCCIIN000002UV01 setAnalysisResults(
            @WebParam(name = "POLB_IN224100UV01", targetNamespace = NAMESPACE, partName = "Body")
            POLBIN224100UV01 response
    ) throws CoreException;


    /**
     * Сообщение от ЛИС о доставке материала. Факт завершения забора биоматериала.
     *
     * @param GUID         - GUID сообщения - подставляет ЛИС
     * @param DtTime       - Время создания события. Значение value кодируется по шаблону: ггггммддччммсс. Подставляет ЛИС
     * @param orderMisId   - идентификатор направления на анализы
     * @param orderBarCode - штрих-код на контейнере c биоматериалом (десятичное представление считанного штрих-кода)
     * @return 0 данные приняты успешно
     * @throws CoreException ошибка формата
     */
    @WebMethod
    int bakDelivered(@WebParam(name = "GUID ", targetNamespace = Namespace)
                     String GUID,
                     @WebParam(name = "DtTime", targetNamespace = Namespace)
                     String DtTime,
                     @WebParam(name = "orderMisId", targetNamespace = Namespace)
                     Integer orderMisId,
                     @WebParam(name = "orderBiomaterialName", targetNamespace = Namespace)
                     Integer orderBarCode) throws CoreException;


}
