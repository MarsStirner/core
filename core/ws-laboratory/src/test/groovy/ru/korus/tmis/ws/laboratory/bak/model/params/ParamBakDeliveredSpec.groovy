package ru.korus.tmis.ws.laboratory.bak.model.params

import ru.korus.tmis.ws.laboratory.bak.validation.Validator
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

import static ru.korus.tmis.ws.laboratory.bak.model.params.ParamBakDelivered.initInParams
import static ru.korus.tmis.ws.laboratory.bak.utils.DateUtils.getXMLGregorianCalendarNow
import static ru.korus.tmis.ws.laboratory.bak.validation.Validator.getRequiredMessageError
import static ru.korus.tmis.ws.laboratory.bak.validation.Validator.getTopRangeMessageError
/**
 * Тест для модели данных входных параметров запроса «Биоматериал доставлен»
 *
 * class @see ru.korus.tmis.ws.laboratory.bak.model.params.ParamBakDelivered
 * @author anosov@outlook.com
 */
class ParamBakDeliveredSpec extends Specification {

    @Shared
    String MORE_40_STRING = "a" * 42;

    def "проверка валидации 'правильного' объекта"() {
        when:
        ParamBakDelivered o = new ParamBakDelivered()
        o.orderBarCode = 10
        o.orderBiomaterialName = "xxx"
        o.orderLIS = "xsd"
        o.getTissueTime = XMLGregorianCalendarNow
        o.takenTissueJournal = "xsd"

        then:
        o.validate()
    }

    @Unroll("не соответствует поле #field объекта")
    def "проверка валидации 'НЕ правильного' объекта"() {

        expect:
        !o.validate()

        and:
        o.errors.size() == 1

        and:
        o.errors.first() == message

        where:
        o                                                                        | field                  | message
        initInParams(null, "xxx", XMLGregorianCalendarNow, "xxx", "xxx")         | "orderBarCode"         | getRequiredMessageError("orderBarCode")
        initInParams(100, "", XMLGregorianCalendarNow, "xxx", "xxx")             | "takenTissueJournal"   | getRequiredMessageError("takenTissueJournal")
        initInParams(100, "xxx", null, "xxx", "xxx")                             | "getTissueTime"        | getRequiredMessageError("getTissueTime")
        initInParams(100, "xxx", XMLGregorianCalendarNow, "xxx", "")             | "orderLIS"             | getRequiredMessageError("orderLIS")

        initInParams(100, MORE_40_STRING, XMLGregorianCalendarNow, "xxx", "xxx") | "takenTissueJournal"   | getTopRangeMessageError("takenTissueJournal", 40)
        initInParams(100, "xxx", XMLGregorianCalendarNow, MORE_40_STRING, "xxx") | "orderBiomaterialName" | getTopRangeMessageError("orderBiomaterialName", 40)
        initInParams(100, "xxx", XMLGregorianCalendarNow, "xxx", MORE_40_STRING) | "orderLIS"             | getTopRangeMessageError("orderLIS", 40)

    }
}
