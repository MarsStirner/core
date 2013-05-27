package ru.korus.tmis.ws.laboratory.bulk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.util.CompileTimeConfigManager;
import ru.korus.tmis.ws.laboratory.bulk.model.ResultAnalyze;

import javax.jws.WebParam;
import javax.jws.WebService;

import static ru.korus.tmis.util.CompileTimeConfigManager.Laboratory.Namespace;

/**
 * Веб-сервис для сохранения результатов исследования из лаборатории
 *
 * test class @see {{ru.korus.tmis.ws.laboratory.bulk.BulkLISWebServiceImplSpec}}
 *
 * @author anosov@outlook.com
 *         date: 5/21/13
 */
@WebService (
        targetNamespace = CompileTimeConfigManager.Laboratory.Namespace,
        serviceName = "bulk-tmis-laboratory-integration",
        portName = "bulk-lis",
        name = "bulk-lis")
public class BulkLISWebServiceImpl implements IBulkLISWebService {

    private static final Logger log = LoggerFactory.getLogger(BulkLISWebServiceImpl.class);

    @Override
    public int setAnalysisResults(
            @WebParam (name = "orderMisId", targetNamespace = Namespace)
            Integer orderMisId,
            @WebParam (name = "orderBarCode", targetNamespace = Namespace)
            String orderBarCode,
            @WebParam (name = "TakenTissueJournal", targetNamespace = Namespace)
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
            String codeLIS) throws CoreException {

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
        return 0;
    }
}
