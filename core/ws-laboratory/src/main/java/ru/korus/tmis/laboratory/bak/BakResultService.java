package ru.korus.tmis.laboratory.bak;

import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.lis.data.model.hl7.complex.MCCIIN000002UV01;
import ru.korus.tmis.lis.data.model.hl7.complex.POLBIN224100UV01;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import static ru.korus.tmis.lis.data.model.hl7.HL7Specification.NAMESPACE;
import static ru.korus.tmis.lis.data.model.hl7.HL7Specification.SUCCESS_ACCEPT_EVENT;
import static ru.korus.tmis.util.CompileTimeConfigManager.Laboratory.Namespace;

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
     * @param orderBarCode         - GUID сообщения - подставляет ЛИС
     * @param takenTissueJournal       - Время создания события. Значение value кодируется по шаблону: ггггммддччммсс. Подставляет ЛИС
     * @param tissueTime   - идентификатор направления на анализы
     * @param orderBiomaterialName - штрих-код на контейнере c биоматериалом (десятичное представление считанного штрих-кода)
     * @return 0 данные приняты успешно
     * @throws CoreException ошибка формата
     */
    @WebMethod
    int bakDelivered(@WebParam(name = "orderBarCode", targetNamespace = Namespace)
                     Integer orderBarCode,
                     @WebParam(name = "TakenTissueJournal", targetNamespace = Namespace)
                     String takenTissueJournal,
                     @WebParam(name = "getTissueTime", targetNamespace = Namespace)
                     String tissueTime,
                     @WebParam(name = "orderBiomaterialName", targetNamespace = Namespace)
                     String orderBiomaterialName) throws CoreException;


}
