package ru.korus.tmis.ws.laboratory.bak.ws.server;

import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.ws.laboratory.bak.ws.server.model.ResponseHL7;
import ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex.MCCIIN000002UV01;
import ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex.ObjectFactory;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.datatype.XMLGregorianCalendar;

import static ru.korus.tmis.util.CompileTimeConfigManager.Laboratory.Namespace;
import static ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.HL7Specification.NAMESPACE;
import static ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.HL7Specification.SUCCESS_ACCEPT_EVENT;

/**
 * @author anosov@outlook.com
 */
@WebService(
        targetNamespace = Namespace,
        name = IBakMISWebService.PORT_NAME)
@XmlSeeAlso({
    ObjectFactory.class
})
public interface IBakMISWebService {

    /**
     * 
     * @param orderMisId идентификатор направления на анализы
     * @param orderBarCode штрих-код на контейнере c биоматериалом (десятичное представление считанного штрих-кода)
     * @param takenTissueJournal номер заказа TakenTissueJournal.id
     * @param referralIsFinished отметка об окончании исследований по направлению (признак последнего сообщения с результатами для данного направления)
     * @param biomaterialDefects дефекты биоматериала
     * @param resultDoctorLisId уникальный идентификационный номер врача лаборатории подписавшего результаты исследования
     * @param resultDoctorLisName ФИО врача лаборатории подписавшего результаты исследования
     * @param codeLIS Код лаборатории
     * @return
     * @throws CoreException
     */
    @WebMethod(operationName = "LIS_SetAnalysisResults")
    @WebResult(name = SUCCESS_ACCEPT_EVENT, targetNamespace = NAMESPACE, partName = "Body")
    MCCIIN000002UV01 setAnalysisResults(
            @WebParam(name = "ResponseHL7", targetNamespace = NAMESPACE, partName = "Body")
            ResponseHL7 response
    );

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
    );


    static final String PORT_NAME = "IBAK_FNKC_PORT_NAME";
    static final String SERVICE_NAME = "IBAK-FNKC";
}
