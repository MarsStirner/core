package ru.korus.tmis.ws.laboratory.bak;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.util.CompileTimeConfigManager;
import ru.korus.tmis.ws.laboratory.bak.model.params.ParamBakDelivered;
import ru.korus.tmis.ws.laboratory.bak.model.params.ParamSetResult;
import ru.korus.tmis.ws.laboratory.bak.model.ResultAnalyze;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.datatype.XMLGregorianCalendar;

import static ru.korus.tmis.util.CompileTimeConfigManager.Laboratory.Namespace;
import static ru.korus.tmis.ws.laboratory.bak.IBakLISWebService.PORT_NAME;
import static ru.korus.tmis.ws.laboratory.bak.IBakLISWebService.SERVICE_NAME;
import static ru.korus.tmis.ws.laboratory.bak.model.params.ParamSetResult.initInParams;

/**
 * Веб-сервис для сохранения результатов исследования из лаборатории
 * <p/>
 * test class @see {{ru.korus.tmis.ws.laboratory.bak.BulkLISWebServiceImplSpec}}
 *
 * @author anosov@outlook.com
 *         date: 5/21/13
 */
@WebService(
        targetNamespace = CompileTimeConfigManager.Laboratory.Namespace,
        serviceName = SERVICE_NAME,
        portName = PORT_NAME,
        name = PORT_NAME)
public class BakLISWebServiceImpl implements IBakLISWebService {

    private static final Logger log = LoggerFactory.getLogger(BakLISWebServiceImpl.class);

    @Override
    public int setAnalysisResults(
            @WebParam(name = "orderMisId", targetNamespace = Namespace)
            Integer orderMisId,
            @WebParam(name = "orderBarCode", targetNamespace = Namespace)
            String orderBarCode,
            @WebParam(name = "TakenTissueJournal", targetNamespace = Namespace)
            String takenTissueJournal,
            @WebParam(name = "referralIsFinished", targetNamespace = Namespace)
            Boolean referralIsFinished,
            @WebParam(name = "result", targetNamespace = Namespace)
            ResultAnalyze result,
            @WebParam(name = "biomaterialDefects", targetNamespace = Namespace)
            String biomaterialDefects,
            @WebParam(name = "ResultDoctorLisId", targetNamespace = Namespace)
            Integer resultDoctorLisId,
            @WebParam(name = "ResultDoctorLisName", targetNamespace = Namespace)
            String resultDoctorLisName,
            @WebParam(name = "CodeLIS", targetNamespace = Namespace)
            String codeLIS) {




        log.info("\n========QUERY setAnalysisResults============\n" +
                "orderMisId          [" + orderMisId + "]\n" +
                "orderBarCode        [" + orderBarCode + "]\n" +
                "takenTissueJournal  [" + takenTissueJournal + "]\n" +
                "referralIsFinished  [" + referralIsFinished + "]\n" +
                "result              [" + result + "]\n" +
                "biomaterialDefects  [" + biomaterialDefects + "]\n" +
                "ResultDoctorLisId   [" + resultDoctorLisId + "]\n" +
                "resultDoctorLisName [" + resultDoctorLisName + "]\n" +
                "codeLIS             [" + codeLIS + "]\n");

        ParamSetResult in = initInParams(orderMisId,
                orderBarCode,
                takenTissueJournal,
                referralIsFinished,
                result,
                biomaterialDefects,
                resultDoctorLisId,
                resultDoctorLisName,
                codeLIS);
        boolean valid = in.validate();
        if (!valid) {
            // do
        }
        return 0;
    }

    @Override
    public int bakDelivered(@WebParam(name = "orderBarCode", targetNamespace = Namespace)
                            Integer orderMisId,
                            @WebParam(name = "TakenTissueJournal", targetNamespace = Namespace)
                            String takenTissueJournal,
                            @WebParam(name = "getTissueTime", targetNamespace = Namespace)
                            XMLGregorianCalendar getTissueTime,
                            @WebParam(name = "orderBiomaterialName", targetNamespace = Namespace)
                            String orderBiomaterialName,
                            @WebParam(name = "orderLIS", targetNamespace = Namespace)
                            String orderLIS) {
        ParamBakDelivered in =
                ParamBakDelivered.initInParams(orderMisId, takenTissueJournal, getTissueTime, orderBiomaterialName, orderLIS);

        boolean valid = in.validate();
        if (valid) {
            // do
        }
        return 0;
    }


}
