package ru.korus.tmis.laboratory.bak.model

import spock.lang.Shared
import spock.lang.Specification

/**
 * Тесты для модели @see ru.korus.tmis.laboratory.bak.model.DiagnosticRequestInfo
 *
 * @author anosov@outlook.com 
 * date: 5/26/13
 */
class DiagnosticRequestInfoSpec extends Specification {

    @Shared DiagnosticRequestInfo pieceDiagnosticRequestInfo = new DiagnosticRequestInfo();

    @Shared String ORDER_DIAG_CODE = "x101"
    @Shared String ORDER_COMMENT = "x101"
    @Shared String ORDER_DEPARTMENT_MIS_ID = "8e87d8de8"

    def setup() {
        pieceDiagnosticRequestInfo.orderComment = ORDER_COMMENT
        pieceDiagnosticRequestInfo.orderDepartmentMisId = ORDER_DEPARTMENT_MIS_ID
        pieceDiagnosticRequestInfo.orderDiagCode = ORDER_DIAG_CODE
    }

    def "проверка обязательности полей"() {
        expect:
        pieceDiagnosticRequestInfo.hasErrors()
    }
}