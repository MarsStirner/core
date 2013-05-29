package ru.korus.tmis.ws.laboratory.bulk;

import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.ws.laboratory.bulk.model.ResultAnalyze;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import static ru.korus.tmis.util.CompileTimeConfigManager.Laboratory.Namespace;

/**
 * @author anosov@outlook.com
 */
@WebService(
        targetNamespace = Namespace,
        name = "bulk-lis")
public interface IBulkLISWebService {

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
            ) throws CoreException;
}
