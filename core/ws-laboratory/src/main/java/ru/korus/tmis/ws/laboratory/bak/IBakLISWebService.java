package ru.korus.tmis.ws.laboratory.bak;

import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.ws.laboratory.bak.model.ResultAnalyze;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.datatype.XMLGregorianCalendar;

import static ru.korus.tmis.util.CompileTimeConfigManager.Laboratory.Namespace;

/**
 * @author anosov@outlook.com
 */
@WebService(
        targetNamespace = Namespace,
        name = "bak-lis")
public interface IBakLISWebService {

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
    @WebMethod
    int setAnalysisResults(
            @WebParam (name = "orderMisId", targetNamespace = Namespace)
            Integer orderMisId,
            @WebParam (name = "orderBarCode", targetNamespace = Namespace)
            String orderBarCode,
            @WebParam (name = "takenTissueJournal", targetNamespace = Namespace)
            String takenTissueJournal,
            @WebParam (name = "referralIsFinished", targetNamespace = Namespace)
            Boolean referralIsFinished,
            @WebParam (name = "result", targetNamespace = Namespace)
            ResultAnalyze result,
            @WebParam (name = "biomaterialDefects", targetNamespace = Namespace)
            String biomaterialDefects,
            @WebParam (name = "ResultDoctorLisId", targetNamespace = Namespace)
            Integer resultDoctorLisId,
            @WebParam (name = "ResultDoctorLisName", targetNamespace = Namespace)
            String resultDoctorLisName,
            @WebParam (name = "CodeLIS", targetNamespace = Namespace)
            String codeLIS
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


    static final String PORT_NAME = "IBAK_FNKC";
    static final String SERVICE_NAME = "IBAK_FNKC";
}
