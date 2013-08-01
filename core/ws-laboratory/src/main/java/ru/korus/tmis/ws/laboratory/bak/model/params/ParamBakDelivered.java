package ru.korus.tmis.ws.laboratory.bak.model.params;

import org.apache.commons.lang.StringUtils;
import ru.korus.validation.Validator;
import ru.korus.validation.rules.IRule;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.List;

import static ru.korus.validation.Validator.getRequiredMessageError;
import static ru.korus.validation.Validator.getTopRangeMessageError;

/**
 * Модель данных для входных параметров запроса «Биоматериал доставлен»
 *
 * test @see ru.korus.tmis.ws.laboratory.bak.model.params.ParamBakDeliveredSpec
 * @author anosov@outlook.com
 */
public class ParamBakDelivered {

    public Integer orderBarCode;
    public String takenTissueJournal;
    public String orderBiomaterialName;
    public XMLGregorianCalendar getTissueTime;
    public String orderLIS;
    private Validator validator = new Validator();

    public static ParamBakDelivered initInParams(Integer orderBarCode,
                                                 String takenTissueJournal,
                                                 XMLGregorianCalendar getTissueTime,
                                                 String orderBiomaterialName,
                                                 String orderLIS) {
        ParamBakDelivered in = new ParamBakDelivered();

        in.orderBarCode = orderBarCode;
        in.takenTissueJournal = takenTissueJournal;
        in.getTissueTime = getTissueTime;
        in.orderBiomaterialName = orderBiomaterialName;
        in.orderLIS = orderLIS;

        return in;
    }

    /**
     * Валидация данных запроса
     *
     * @return
     */
    public boolean validate() {
        validator.validate(orderBarCode, new IRule<Integer>() {
            @Override
            public void apply(Integer o) {
                if (o == null) {
                    validator.addError(getRequiredMessageError("orderBarCode"));
                }
            }
        });

        validator.validate(takenTissueJournal, new IRule<String>() {
            @Override
            public void apply(String o) {
                if (StringUtils.isEmpty(o)) {
                    validator.addError(getRequiredMessageError("takenTissueJournal"));
                } else if (o.length() > 40) {
                    validator.addError(getTopRangeMessageError("takenTissueJournal", 40));
                }
            }
        });


        validator.validate(getTissueTime, new IRule<XMLGregorianCalendar>() {
            @Override
            public void apply(XMLGregorianCalendar o) {
                if (o == null) {
                    validator.addError(getRequiredMessageError("getTissueTime"));
                }
            }
        });

        validator.validate(orderBiomaterialName, new IRule<String>() {
            @Override
            public void apply(String o) {
                if (o != null && o.length() > 40) {
                    validator.addError(getTopRangeMessageError("orderBiomaterialName", 40));
                }
            }
        });

        validator.validate(orderLIS, new IRule<String>() {
            @Override
            public void apply(String o) {
                if (StringUtils.isEmpty(o)) {
                    validator.addError(getRequiredMessageError("orderLIS"));
                } else if (o.length() > 40) {
                    validator.addError(getTopRangeMessageError("orderLIS", 40));
                }
            }
        });

        return !validator.hasErrors();
    }

    public List<String> getErrors() {
        return validator.getErrors();
    }
}
