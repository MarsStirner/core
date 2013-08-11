package ru.korus.tmis.ws.laboratory.bak.ws.server;

import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex.MCCIIN000002UV01;
import ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex.POLBIN224100UV01;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.datatype.XMLGregorianCalendar;

import static ru.korus.tmis.util.CompileTimeConfigManager.Laboratory.Namespace;
import static ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.HL7Specification.NAMESPACE;
import static ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.HL7Specification.SUCCESS_ACCEPT_EVENT;

/**
 * @author anosov@outlook.com
 */
@WebService(
        targetNamespace = Namespace,
        name = SetAnalysysResultWS.SERVICE_NAME)
public interface SetAnalysysResultWS {

    @WebMethod(operationName = "setAnalysisResults")
    @WebResult(name = SUCCESS_ACCEPT_EVENT, targetNamespace = NAMESPACE, partName = "Body")
    MCCIIN000002UV01 setAnalysisResults(
            @WebParam(name = "POLB_IN224100UV01", targetNamespace = NAMESPACE, partName = "Body")
            POLBIN224100UV01 response
    )  throws CoreException;

    /**
     * Запрос «Биоматериал доставлен»:
     *
     * @param orderMisId - штрих-код на контейнере c биоматериалом (десятичное представление считанного штрих-кода)
     * @param takenTissueJournal - TakenTissueJournal.id – номер заказа
     * @param getTissueTime - Дата и время регистрации биоматериала в лаборатории
     * @param orderBiomaterialName - название  биоматериала из справочника биоматериалов
     * @param orderLIS - Номер заказа в ЛИС
     *
     * @return 0 - все нормально
     *         остальное коды ошибок
     */
    @WebMethod
    int bakDelivered(
            @WebParam (name = "orderBarCode", targetNamespace = Namespace)
            Integer orderMisId,
            @WebParam (name = "TakenTissueJournal", targetNamespace = Namespace)
            String takenTissueJournal,
            @WebParam (name = "getTissueTime", targetNamespace = Namespace)
            XMLGregorianCalendar getTissueTime,
            @WebParam (name = "orderBiomaterialName", targetNamespace = Namespace)
            String orderBiomaterialName,
            @WebParam (name = "orderLIS", targetNamespace = Namespace)
            String orderLIS
    )  throws CoreException ;


    static final String SERVICE_NAME = "setAnalysisResultService";
    static final String PORT_NAME = "setAnalysisResultPortType";
}
