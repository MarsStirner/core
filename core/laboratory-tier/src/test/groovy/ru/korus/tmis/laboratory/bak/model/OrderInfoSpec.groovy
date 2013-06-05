package ru.korus.tmis.laboratory.bak.model

import spock.lang.Shared
import spock.lang.Specification

/**
 * Тесты для модели @see ru.korus.tmis.laboratory.bak.model.OrderInfo
 *
 * @author anosov@outlook.com 
 * date: 5/26/13
 */
class OrderInfoSpec extends Specification {

    @Shared OrderInfo pieceOrderInfo = new OrderInfo();

    @Shared String DIAGNOSTIC_CODE= "x101"
    @Shared String DIAGNOSTIC_NAME = "просп. Добролюбова, 18, 501, Санкт-Петербург"


    def setup() {
        pieceOrderInfo.diagnosticCode = DIAGNOSTIC_CODE
        pieceOrderInfo.diagnosticName = DIAGNOSTIC_NAME
    }

    def "проверка обязательности полей"() {
        expect:
        pieceOrderInfo.hasErrors()
    }

}
