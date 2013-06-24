package ru.korus.tmis.ws.laboratory.bak.ws.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.util.CompileTimeConfigManager;
import ru.korus.tmis.ws.laboratory.bak.model.params.ParamBakDelivered;
import ru.korus.tmis.ws.laboratory.bak.ws.server.model.ResponseHL7;
import ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex.MCCIIN000002UV01;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.datatype.XMLGregorianCalendar;

import static ru.korus.tmis.util.CompileTimeConfigManager.Laboratory.Namespace;
import static ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.HL7Specification.NAMESPACE;
import static ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.HL7Specification.SUCCESS_ACCEPT_EVENT;

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
        serviceName = IBakMISWebService.SERVICE_NAME,
        portName = IBakMISWebService.PORT_NAME,
        name = IBakMISWebService.PORT_NAME)
public class BakMISWebService implements IBakMISWebService {

    private static final Logger log = LoggerFactory.getLogger(BakMISWebService.class);
//
//    @Override
//    public int setAnalysisResults(
//          ) {
//
//        log.info("\n========QUERY setAnalysisResults============\n" +
//                "orderMisId          [" + orderMisId + "]\n" +
//                "orderBarCode        [" + orderBarCode + "]\n" +
//                "takenTissueJournal  [" + takenTissueJournal + "]\n" +
//                "referralIsFinished  [" + referralIsFinished + "]\n" +
//                "result              [" + result + "]\n" +
//                "biomaterialDefects  [" + biomaterialDefects + "]\n" +
//                "ResultDoctorLisId   [" + resultDoctorLisId + "]\n" +
//                "resultDoctorLisName [" + resultDoctorLisName + "]\n" +
//                "codeLIS             [" + codeLIS + "]\n");
//
//        ParamSetResult in = initInParams(orderMisId,
//                orderBarCode,
//                takenTissueJournal,
//                referralIsFinished,
//                result,
//                biomaterialDefects,
//                resultDoctorLisId,
//                resultDoctorLisName,
//                codeLIS);
//        boolean valid = in.validate();
//        if (!valid) {
//            // do
//        }
//        return 0;
//    }

    @Override
    @WebMethod(operationName = "LIS_SetAnalysisResults")
    @WebResult(name = SUCCESS_ACCEPT_EVENT, targetNamespace = NAMESPACE, partName = "Body")
    public MCCIIN000002UV01 setAnalysisResults(
            @WebParam(name = "RESPONSE", targetNamespace = NAMESPACE, partName = "Body")
            ResponseHL7 response) {
        return new MCCIIN000002UV01();
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
