package ru.korus.tmis.ws.laboratory.bak.model.params;

import org.apache.commons.lang.StringUtils;
import ru.korus.tmis.ws.laboratory.bak.model.ResultAnalyze;
import ru.korus.tmis.ws.laboratory.bak.validation.Validator;
import ru.korus.tmis.ws.laboratory.bak.validation.rules.IRule;

import java.util.List;

import static ru.korus.tmis.ws.laboratory.bak.validation.Validator.getRequiredMessageError;
import static ru.korus.tmis.ws.laboratory.bak.validation.Validator.getTopRangeMessageError;

/**
 *
 */
public class ParamSetResult {

    public Integer orderMisId;
    public String orderBarCode;
    public String takenTissueJournal;
    public Boolean referralIsFinished;
    public ResultAnalyze result;
    public String biomaterialDefects;
    public Integer resultDoctorLisId;
    public String resultDoctorLisName;
    public String codeLIS;
    private Validator validator = new Validator();

    public static ParamSetResult initInParams(Integer orderMisId,
                                        String orderBarCode,
                                        String takenTissueJournal,
                                        Boolean referralIsFinished,
                                        ResultAnalyze result,
                                        String biomaterialDefects,
                                        Integer resultDoctorLisId,
                                        String resultDoctorLisName,
                                        String codeLIS) {
        ParamSetResult in = new ParamSetResult();

        in.orderMisId = orderMisId;
        in.orderBarCode = orderBarCode;
        in.takenTissueJournal = takenTissueJournal;
        in.referralIsFinished = referralIsFinished;
        in.result = result;
        in.biomaterialDefects = biomaterialDefects;
        in.resultDoctorLisId = resultDoctorLisId;
        in.resultDoctorLisName = resultDoctorLisName;
        in.codeLIS = codeLIS;

        return in;
    }



    /**
     * Валидация запросов
     *
     * @return
     */
    public boolean validate() {
        validator.validate(orderMisId, new IRule<Integer>() {
            @Override
            public void apply(Integer o) {
                if (o == null) {
                    validator.addError(getRequiredMessageError("orderMisId"));
                }
            }
        });

        validator.validate(orderBarCode, new IRule<String>() {
            @Override
            public void apply(String o) {
                if (StringUtils.isEmpty(o)) {
                    validator.addError(getRequiredMessageError("orderBarCode"));
                } else if (o.length() > 40) {
                    validator.addError(getTopRangeMessageError("orderBarCode", 40));
                }
            }
        });


        validator.validate(resultDoctorLisId, new IRule<Integer>() {
            @Override
            public void apply(Integer o) {
                if (o == null) {
                    validator.addError(getRequiredMessageError("resultDoctorLisId"));
                }
            }
        });

        validator.validate(resultDoctorLisId, new IRule<Integer>() {
            @Override
            public void apply(Integer o) {
                if (o == null) {
                    validator.addError(getRequiredMessageError("resultDoctorLisId"));
                }
            }
        });

        validator.validate(resultDoctorLisName, new IRule<String>() {
            @Override
            public void apply(String o) {
                if (StringUtils.isEmpty(o)) {
                    validator.addError(getRequiredMessageError("resultDoctorLisName"));
                } else if (o.length() > 40) {
                    validator.addError(getTopRangeMessageError("resultDoctorLisName", 40));
                }
            }
        });

        validator.validate(biomaterialDefects, new IRule<String>() {
            @Override
            public void apply(String o) {
                if (o!=null && o.length() > 150) {
                    validator.addError(getTopRangeMessageError("biomaterialDefects", 150));
                }
            }
        });

        validator.validate(codeLIS, new IRule<String>() {
            @Override
            public void apply(String o) {
                if (StringUtils.isEmpty(o)) {
                    validator.addError(getRequiredMessageError("codeLIS"));
                } else if (o.length() > 20) {
                    validator.addError(getTopRangeMessageError("codeLIS", 20));
                }
            }
        });

        validator.validate(result, new IRule<ResultAnalyze>() {
            @Override
            public void apply(ResultAnalyze o) {
                if (StringUtils.isEmpty(o.getIndicatorCode())) {
                    validator.addError(getRequiredMessageError("result.indicatorCode"));
                } else if (o.getIndicatorCode().length() > 20) {
                    validator.addError(getTopRangeMessageError("result.indicatorCode", 20));
                }
                if (StringUtils.isEmpty(o.getIndicatorName())) {
                    validator.addError(getRequiredMessageError("result.indicatorName"));
                } else if (o.getIndicatorName().length() > 40) {
                    validator.addError(getTopRangeMessageError("result.indicatorName", 40));
                }
                if (!StringUtils.isEmpty(o.getDeviceName()) && o.getDeviceName().length() > 40) {
                    validator.addError(getTopRangeMessageError("result.deviceName", 40));
                }
                if (o.getValueType() == null) {
                    validator.addError(getRequiredMessageError("result.valueType"));
                }
                if (!StringUtils.isEmpty(o.getResultValueText())
                        && o.getResultValueText().length() > 150) {
                    validator.addError(getTopRangeMessageError("result.resultValueText", 150));
                }
            }
        });

        return !validator.hasErrors();
    }

    public List<String> getErrors() {
        return validator.getErrors();
    }
}
