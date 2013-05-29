package ru.korus.tmis.ws.laboratory.bulk;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.util.CompileTimeConfigManager;
import ru.korus.tmis.ws.laboratory.bulk.model.ResultAnalyze;
import ru.korus.tmis.ws.laboratory.bulk.validation.Validator;
import ru.korus.tmis.ws.laboratory.bulk.validation.rules.IRule;

import javax.jws.WebParam;
import javax.jws.WebService;

import java.util.List;

import static ru.korus.tmis.util.CompileTimeConfigManager.Laboratory.Namespace;

/**
 * Веб-сервис для сохранения результатов исследования из лаборатории
 * <p/>
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

    private Integer orderMisId;

    private String orderBarCode;

    private String takenTissueJournal;

    private Boolean referralIsFinished;

    private ResultAnalyze result;

    private String biomaterialDefects;

    private Integer resultDoctorLisId;

    private String resultDoctorLisName;

    private String codeLIS;

    private Validator validator = new Validator();

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

        this.orderMisId = orderMisId;
        this.orderBarCode = orderBarCode;
        this.takenTissueJournal = takenTissueJournal;
        this.referralIsFinished = referralIsFinished;
        this.result = result;
        this.biomaterialDefects = biomaterialDefects;
        this.resultDoctorLisId = resultDoctorLisId;
        this.resultDoctorLisName = resultDoctorLisName;
        this.codeLIS = codeLIS;

        return 0;
    }

    /**
     * Валидация запросов
     * @return
     */
    public boolean validate() {
        validator.validate(orderMisId, new IRule<Integer>() {
            @Override
            public void apply(Integer o) {
                if (o == null) {
                    validator.addError(Validator.Message.REQUIRED.getMessage("orderMisId"));
                }
            }
        });

        validator.validate(orderBarCode, new IRule<String>() {
            @Override
            public void apply(String o) {
                if (StringUtils.isEmpty(orderBarCode)) {
                    validator.addError(Validator.Message.REQUIRED.getMessage("orderBarCode"));
                } else if (orderBarCode.length() > 40) {
                    validator.addError(Validator.Message.UNBOUND_TOP_RANGE.getMessage("orderBarCode", 40));
                }
            }
        });


        validator.validate(resultDoctorLisId, new IRule<Integer>() {
            @Override
            public void apply(Integer o) {
                if (o == null) {
                    validator.addError(Validator.Message.REQUIRED.getMessage("resultDoctorLisId"));
                }
            }
        });

        validator.validate(resultDoctorLisId, new IRule<Integer>() {
            @Override
            public void apply(Integer o) {
                if (o == null) {
                    validator.addError(Validator.Message.REQUIRED.getMessage("resultDoctorLisId"));
                }
            }
        });

        validator.validate(resultDoctorLisName, new IRule<String>() {
            @Override
            public void apply(String o) {
                if (StringUtils.isEmpty(o)) {
                    validator.addError(Validator.Message.REQUIRED.getMessage("resultDoctorLisName"));
                } else if (o.length() > 40) {
                    validator.addError(Validator.Message.UNBOUND_TOP_RANGE.getMessage("resultDoctorLisName", 40));
                }
            }
        });

        validator.validate(biomaterialDefects, new IRule<String>() {
            @Override
            public void apply(String o) {
                if (o.length() > 150) {
                    validator.addError(Validator.Message.UNBOUND_TOP_RANGE.getMessage("biomaterialDefects", 150));
                }
            }
        });

        validator.validate(codeLIS, new IRule<String>() {
            @Override
            public void apply(String o) {
                if (StringUtils.isEmpty(o)) {
                    validator.addError(Validator.Message.REQUIRED.getMessage("codeLIS"));
                } else if (o.length() > 20) {
                    validator.addError(Validator.Message.UNBOUND_TOP_RANGE.getMessage("codeLIS", 20));
                }
            }
        });

        validator.validate(result, new IRule<ResultAnalyze>() {
            @Override
            public void apply(ResultAnalyze o) {
                if (StringUtils.isEmpty(o.getIndicatorCode())) {
                    validator.addError(Validator.Message.REQUIRED.getMessage("result.indicatorCode"));
                } else if (o.getIndicatorCode().length() > 20) {
                    validator.addError(Validator.Message.UNBOUND_TOP_RANGE.getMessage("result.indicatorCode", 20));
                }
                if (StringUtils.isEmpty(o.getIndicatorName())) {
                    validator.addError(Validator.Message.REQUIRED.getMessage("result.indicatorName"));
                } else if (o.getIndicatorName().length() > 40) {
                    validator.addError(Validator.Message.UNBOUND_TOP_RANGE.getMessage("result.indicatorName", 40));
                }
                if (!StringUtils.isEmpty(o.getDeviceName()) && o.getDeviceName().length() > 40) {
                    validator.addError(Validator.Message.UNBOUND_TOP_RANGE.getMessage("result.deviceName", 40));
                }
                if (o.getValueType() == null) {
                    validator.addError(Validator.Message.REQUIRED.getMessage("result.valueType"));
                }
                if (!StringUtils.isEmpty(o.getResultValueText()) && o.getResultValueText().length() > 150) {
                    validator.addError(Validator.Message.UNBOUND_TOP_RANGE.getMessage("result.resultValueText", 150));
                }
            }
        });

        return !validator.hasErrors();
    }

    public List<String> getErrors(){
        return validator.getErrors();
    }
}
